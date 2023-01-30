package com.remoc.sync.scheduld;

import com.remoc.sync.configuration.properties.MinioProperties;
import com.remoc.sync.domain.aonebakery.*;
import com.remoc.sync.domain.posvm.RefundTime;
import com.remoc.sync.domain.posvm.VoidTime;
import com.remoc.sync.repository.aonebakery.*;
import com.remoc.sync.repository.posvm.PosvmRefundTimeRepository;
import com.remoc.sync.repository.posvm.PosvmVoidTimeRepository;
import com.remoc.sync.utils.FFmpegFrameUtils;
import com.remoc.sync.utils.HikApiCommonUtils;
import com.remoc.sync.utils.ImeiUtils;
import com.remoc.sync.utils.SearchMatchItem;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.apache.logging.log4j.util.Strings;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class VoidRefundSyncTask {

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioProperties minioProperties;
    @Autowired
    private PosvmVoidTimeRepository posvmVoidTimeRepository;
    @Autowired
    private PosvmRefundTimeRepository posvmRefundTimeRepository;
    @Autowired
    private AonebakeryStoreRepository aonebakeryStoreRepository;
    @Autowired
    private AonebakeryEquipmentRepository equipmentRepository;
    @Autowired
    private RefundVoidInfoRepository refundVoidInfoRepository;
    @Autowired
    private RefundVoidSublistRepository refundVoidSublistRepository;
    @Autowired
    private ParamSettingRepository paramSettingRepository;


//    @Scheduled(initialDelay = 1000, fixedRate = 600000)
    @Scheduled(initialDelay = 100, fixedRate = 600)
    public void voidSync() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        List<Store> allStore = aonebakeryStoreRepository.findAll();

        for (Store store : allStore) {
            String laTelephone1 = "%" + store.getName() + "%";

            // VOID
            List<VoidTime> voidTimes = posvmVoidTimeRepository.findAllByLaTelephone1LikeOrderByVoidTimeDesc(laTelephone1);
            System.out.println("VOID: " + store.getName() + "个数" + voidTimes.size());
            for (VoidTime voidTime : voidTimes) {
                if (!refundVoidInfoRepository.existsBySalesDateAndAndRefundVoidType(voidTime.getVoidTime(),0)) {
                    List<String> fChannelNums = new ArrayList<>();
                    switch (voidTime.getPosNum()) {
                        case "01":
                            fChannelNums = Arrays.asList("101", "201");
                            break;
                        case "02":
                            fChannelNums = Arrays.asList("501", "601");
                            break;
                    }


                    List<RefundVoidSublist> refundVoidSublistList = new LinkedList<>();

                    for (String item : fChannelNums) {
                        String eqName = "P0" + item.charAt(0);
                        Equipment equipment = equipmentRepository.findAllByNameAndStoreId(eqName, store.getId());

                        if (equipment != null) {
                            if (Strings.isBlank(equipment.getEquipmentNumber())){
                                return;
                            }
                            String ip = ImeiUtils.getIp(equipment.getEquipmentNumber());
                            String port = ImeiUtils.getPort(equipment.getEquipmentNumber());

                            String url = ip + ":" + port;
                            List<SearchMatchItem> list = HikApiCommonUtils.search("http://" + url, item, voidTime.getVoidTime().toLocalDate());
                            if (list != null) {
                                System.out.println(eqName + "-" + voidTime.getVoidTime() + "不存在-下载");
                                String fileName = String.valueOf(UUID.randomUUID());
                                download(voidTime.getVoidTime(), Long.parseLong("21"), equipment.getEquipmentNumber(), fileName);
                                System.out.println(eqName + "-" + voidTime.getVoidTime() + "下载-完成");
                                new File(fileName).deleteOnExit();//删除

                                RefundVoidSublist refundVoidSublist = new RefundVoidSublist();
                                refundVoidSublist.setEquipmentNumber(equipment.getEquipmentNumber());//A1 设备IMEI
                                refundVoidSublist.setSurveillanceUrl(minioProperties.getExtranet() + "video/" + fileName + ".mp4");//视频信息
                                refundVoidSublist.setScreenshotUrl(minioProperties.getExtranet() + "video/" + fileName + ".jpg");//封面地址
                                refundVoidSublist.setDecisionOutcome(0);//判定结果:初始化
                                refundVoidSublist.setFileName(fileName);//视频url名
                                refundVoidSublistList.add(refundVoidSublist);
                            }
                        }
                    }

                    //获取主表名(refund/void)信息
                    RefundVoidInfo refundVoidInfo = new RefundVoidInfo();
                    refundVoidInfo.setStoreId(store.getId());//商店id
                    refundVoidInfo.setRefundVoidType(0);//(refund/void)类型
                    refundVoidInfo.setDecisionOutcome(0);///判定结果:初始化
                    refundVoidInfo.setPosNum(voidTime.getPosNum());//
                    refundVoidInfo.setSalesDate(voidTime.getVoidTime());//警报创建时间
                    if (!refundVoidSublistList.isEmpty()){
                        RefundVoidInfo info = refundVoidInfoRepository.save(refundVoidInfo);
                        System.out.println(store.getName() + "-" + voidTime.getVoidTime() + "插入数据库");

                        refundVoidSublistRepository.saveAll(refundVoidSublistList.stream().peek(item -> item.setRefundVoidInfoId(info.getId())).collect(Collectors.toList()));

                    }

                }
            }

            // REFUND
            List<RefundTime> refundTimes = posvmRefundTimeRepository.findAllByLaTelephone1LikeOrderByRefundTimeDesc(laTelephone1);
            System.out.println("REFUND: " + store.getName() + "个数" + voidTimes.size());
            for (RefundTime refundTime : refundTimes) {
                if (!refundVoidInfoRepository.existsBySalesDateAndAndRefundVoidType(refundTime.getRefundTime(),1)) {
                    List<String> fChannelNums = new ArrayList<>();
                    switch (refundTime.getPosNum()) {
                        case "01":
                            fChannelNums = Arrays.asList("101", "201");
                            break;
                        case "02":
                            fChannelNums = Arrays.asList("501", "601");
                            break;
                    }

                    List<RefundVoidSublist> refundVoidSublistList = new LinkedList<>();

                    for (String item : fChannelNums) {
                        String eqName = "P0" + item.charAt(0);
                        Equipment equipment = equipmentRepository.findAllByNameAndStoreId(eqName, store.getId());

                        if (equipment != null) {
                            if (Strings.isBlank(equipment.getEquipmentNumber())){
                                return;
                            }
                            String ip = ImeiUtils.getIp(equipment.getEquipmentNumber());
                            String port = ImeiUtils.getPort(equipment.getEquipmentNumber());

                            String url = ip + ":" + port;
                            List<SearchMatchItem> list = HikApiCommonUtils.search("http://" + url, item, refundTime.getRefundTime().toLocalDate());
                            if (list != null) {
                                System.out.println(eqName + "-" + refundTime.getRefundTime() + "不存在-下载");
                                String fileName = String.valueOf(UUID.randomUUID());
                                download(refundTime.getRefundTime(),Long.parseLong("23"), equipment.getEquipmentNumber(), fileName);
                                System.out.println(eqName + "-" + refundTime.getRefundTime() + "下载-完成");
                                new File(fileName).deleteOnExit();//删除

                                RefundVoidSublist refundVoidSublist = new RefundVoidSublist();
                                refundVoidSublist.setEquipmentNumber(equipment.getEquipmentNumber());//A1 设备IMEI
                                refundVoidSublist.setSurveillanceUrl(minioProperties.getExtranet() + "video/" + fileName + ".mp4");//视频信息
                                refundVoidSublist.setScreenshotUrl(minioProperties.getExtranet() + "video/" + fileName + ".jpg");//封面地址
                                refundVoidSublist.setDecisionOutcome(0);//判定结果:初始化
                                refundVoidSublist.setFileName(fileName);//视频url名
                                refundVoidSublistList.add(refundVoidSublist);
                            }
                        }
                    }

                    //获取主表名(refund/void)信息
                    RefundVoidInfo refundVoidInfo = new RefundVoidInfo();
                    refundVoidInfo.setStoreId(store.getId());//商店id
                    refundVoidInfo.setRefundVoidType(1);//(refund/void)类型
                    refundVoidInfo.setDecisionOutcome(0);///判定结果:初始化
                    refundVoidInfo.setSalesDate(refundTime.getRefundTime());//警报创建时间

                    if (!refundVoidSublistList.isEmpty()){
                        RefundVoidInfo info = refundVoidInfoRepository.save(refundVoidInfo);
                        System.out.println(store.getName() + "-" + refundTime.getRefundTime() + "插入数据库");

                        refundVoidSublistRepository.saveAll(refundVoidSublistList.stream().peek(item -> item.setRefundVoidInfoId(info.getId())).collect(Collectors.toList()));

                    }

                }
            }
        }

    }

    public void download(LocalDateTime time, Long param, String imei, String fileName) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMdd't'HHmmss'z'");

        //视频保存前后时长
        String[] split = paramSettingRepository.findById(param).orElse(new ParamSetting()).getValue().split(",");
        LocalDateTime start = time.minusMinutes(Long.parseLong(String.valueOf(split[0]).substring(0, 1)));
        LocalDateTime end = time.plusMinutes(Long.parseLong(String.valueOf(split[1]).substring(0, 1)));

        String playbackStream = "rtsp://%s:%s@%s:5544/Streaming/tracks/%s?starttime=%s";
        String rtsp = String.format(playbackStream, ImeiUtils.getUsername(imei), ImeiUtils.getPassword(imei), ImeiUtils.getIp(imei), ImeiUtils.getChannelNum(imei), start.format(pattern));

        long seconds = Duration.between(start, end).getSeconds();

        String videoName = fileName + ".mp4";
        String imageName = fileName + ".jpg";
        FFmpegFrameUtils.process(rtsp, videoName, seconds);
        UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder().bucket("video").object(videoName).filename(videoName).build();
        minioClient.uploadObject(uploadObjectArgs);

        BufferedImage bufferedImage = FFmpegFrameUtils.screenshotByLastFrame(videoName);
        ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "JPEG", imageOutputStream);
        InputStream inputStream = new ByteArrayInputStream(imageOutputStream.toByteArray());

        PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket("video").object(imageName).stream(inputStream, inputStream.available(), -1).build();
        minioClient.putObject(putObjectArgs);
    }

}

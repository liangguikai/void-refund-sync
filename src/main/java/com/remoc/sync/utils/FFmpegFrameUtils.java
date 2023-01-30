package com.remoc.sync.utils;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FFmpegFrameUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FFmpegFrameUtils.class);


    public static void process(String url, String videoName, Long time) throws IOException {

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(url);
        // rtsp传输方式 tcp
        grabber.setOption("rtsp_transport", "tcp");
        grabber.start();


        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(videoName, grabber.getImageWidth(), grabber.getImageHeight());

        // 视频帧数
        int frameRate = (int) grabber.getVideoFrameRate();

        recorder.setFrameRate(frameRate);
        recorder.setGopSize(frameRate);
        recorder.setSampleRate(grabber.getSampleRate());

        // 可能出现声道为空的情况，在此处补充声道
        recorder.setAudioChannels(1);
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);

        // ffmpeg 预设 超快
        recorder.setVideoOption("preset", "ultrafast");

        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFormat("MP4");
        recorder.start();

        Frame frame;
        while ((frame = grabber.grab()) != null && recorder.getTimestamp() <= time * 1000000) {
            recorder.record(frame);
            LOGGER.info("裁剪进度(帧):" + recorder.getTimestamp() / 1000000);
        }
        grabber.close();

        recorder.close();

    }

    public static void process(List<String> filenames, File file, Long startSecond, Long totalSecond) throws FrameGrabber.Exception, FrameRecorder.Exception {
        FFmpegFrameRecorder recorder = null;

        for (int i = 0; i < filenames.size(); i++) {
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(filenames.get(i));
            grabber.start();
            if (i == 0) {
                grabber.setTimestamp(startSecond * 1000000);
                // 视频帧数
                int frameRate = (int) grabber.getVideoFrameRate();
                recorder = new FFmpegFrameRecorder(file, grabber.getImageWidth(), grabber.getImageHeight());

                recorder.setFrameRate(frameRate);
                recorder.setGopSize(frameRate);
                recorder.setSampleRate(grabber.getSampleRate());

                // 可能出现声道为空的情况，在此处补充声道
                recorder.setAudioChannels(1);
                recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);

                // ffmpeg 预设 超快
                recorder.setVideoOption("preset", "ultrafast");
                recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
                recorder.setFormat("MP4");
                recorder.start();
            }

            Frame frame;
            while ((frame = grabber.grab()) != null && recorder.getTimestamp() < totalSecond * 1000000) {
                recorder.record(frame);
                LOGGER.info("裁剪进度(帧):" + recorder.getTimestamp() / 1000000);
            }
            grabber.close();

        }

        Objects.requireNonNull(recorder).close();
    }

    public static BufferedImage screenshotByLastFrame(String filename) throws FrameGrabber.Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(filename);
        grabber.start();
        grabber.setTimestamp(grabber.getLengthInFrames());
        Java2DFrameConverter converter = new Java2DFrameConverter();
        Frame frame = grabber.grabImage();
        BufferedImage image = converter.convert(frame);
        grabber.close();
        return image;
    }

}




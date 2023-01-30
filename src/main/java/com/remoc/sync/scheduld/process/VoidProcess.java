package com.remoc.sync.scheduld.process;

import com.remoc.sync.domain.aonebakery.Equipment;
import com.remoc.sync.domain.aonebakery.VoidInfo;
import com.remoc.sync.domain.posvm.VoidTime;
import com.remoc.sync.utils.HikApiCommonUtils;
import com.remoc.sync.utils.SearchMatchItem;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class VoidProcess {

    public static void process(String url, Equipment equipment, List<SearchMatchItem> list, VoidTime voidTime) {
//        LocalDateTime start = voidTime.getVoidTime().minusMinutes(5);
//        LocalDateTime end = voidTime.getVoidTime();
//        List<SearchMatchItem> intercept = intercept(list, start, end);
//        long seconds = Duration.between(intercept.get(0).getTimeSpan().getStartTime(), start).getSeconds();
//
//        List<String> strings = intercept.stream().map(searchMatchItem -> {
//            String playbackURI = searchMatchItem.getMediaSegmentDescriptor().getPlaybackURI();
//            return HikApiCommonUtils.download(url, playbackURI, String.valueOf(UUID.randomUUID()));
//        }).toList();
//        VoidInfo voidInfo = new VoidInfo(voidTime.getVoidTime(), equipment.getEquipmentNumber(), equipment.getStoreId(), null, null, null);
//        aonebakeryVoidTimeRepository.save(voidInfo);
//        System.out.println(voidTime);
    }


    public static List<SearchMatchItem> intercept(List<SearchMatchItem> list, LocalDateTime start, LocalDateTime end) {

        Set<SearchMatchItem> startSet = list.stream().filter(searchMatchItem -> {
            boolean before = searchMatchItem.getTimeSpan().getStartTime().isBefore(start);
            boolean after = searchMatchItem.getTimeSpan().getEndTime().isAfter(start);
            return before && after;
        }).collect(Collectors.toSet());

        Set<SearchMatchItem> endSet = list.stream().filter(searchMatchItem -> {
            boolean before = searchMatchItem.getTimeSpan().getStartTime().isBefore(end);
            boolean after = searchMatchItem.getTimeSpan().getEndTime().isAfter(end);
            return before && after;
        }).collect(Collectors.toSet());
        startSet.addAll(endSet);

        return new ArrayList<>(startSet);
    }



}

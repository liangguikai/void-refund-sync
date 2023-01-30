package com.remoc.sync.utils;


import java.time.LocalDateTime;

public class SearchMatchItem {

    private String sourceID;

    private MediaSegmentDescriptor mediaSegmentDescriptor;

    private MetadataMatches metadataMatches;

    private String trackID;

    private TimeSpan timeSpan;

    public static class MediaSegmentDescriptor {

        private String playbackURI;

        private String contentType;

        private String codecType;


        public String getPlaybackURI() {
            return playbackURI;
        }

        public void setPlaybackURI(String playbackURI) {
            this.playbackURI = playbackURI;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getCodecType() {
            return codecType;
        }

        public void setCodecType(String codecType) {
            this.codecType = codecType;
        }

    }

    static class MetadataMatches {

        private String metadataDescriptor;


        public String getMetadataDescriptor() {
            return metadataDescriptor;
        }

        public void setMetadataDescriptor(String metadataDescriptor) {
            this.metadataDescriptor = metadataDescriptor;
        }

    }

   public static class TimeSpan {

        private LocalDateTime startTime;

        private LocalDateTime endTime;


        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public MediaSegmentDescriptor getMediaSegmentDescriptor() {
        return mediaSegmentDescriptor;
    }

    public void setMediaSegmentDescriptor(MediaSegmentDescriptor mediaSegmentDescriptor) {
        this.mediaSegmentDescriptor = mediaSegmentDescriptor;
    }

    public MetadataMatches getMetadataMatches() {
        return metadataMatches;
    }

    public void setMetadataMatches(MetadataMatches metadataMatches) {
        this.metadataMatches = metadataMatches;
    }

    public String getTrackID() {
        return trackID;
    }

    public void setTrackID(String trackID) {
        this.trackID = trackID;
    }

    public TimeSpan getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(TimeSpan timeSpan) {
        this.timeSpan = timeSpan;
    }


    @Override
    public String toString() {
        return "SearchMatchItem{" +
                "sourceID='" + sourceID + '\'' +
                ", mediaSegmentDescriptor=" + mediaSegmentDescriptor +
                ", metadataMatches=" + metadataMatches +
                ", trackID='" + trackID + '\'' +
                ", timeSpan=" + timeSpan +
                '}';
    }

}
package com.remoc.sync.domain.posvm;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "v_ai_void_time")
public class VoidTime {
    private String sdLocation;

    private String laTelephone1;

    private String posNum;

    @Id
    private LocalDateTime voidTime;

    private LocalDateTime newTime;

    private LocalDateTime originalTime;


    public String getSdLocation() {
        return sdLocation;
    }

    public void setSdLocation(String sdLocation) {
        this.sdLocation = sdLocation;
    }

    public String getLaTelephone1() {
        return laTelephone1;
    }

    public void setLaTelephone1(String laTelephone1) {
        this.laTelephone1 = laTelephone1;
    }

    public String getPosNum() {
        return posNum;
    }

    public void setPosNum(String posNum) {
        this.posNum = posNum;
    }

    public LocalDateTime getVoidTime() {
        return voidTime;
    }

    public void setVoidTime(LocalDateTime voidTime) {
        this.voidTime = voidTime;
    }

    public LocalDateTime getNewTime() {
        return newTime;
    }

    public void setNewTime(LocalDateTime newTime) {
        this.newTime = newTime;
    }

    public LocalDateTime getOriginalTime() {
        return originalTime;
    }

    public void setOriginalTime(LocalDateTime originalTime) {
        this.originalTime = originalTime;
    }

    @Override
    public String toString() {
        return "VoidTime{" +
                "sdLocation='" + sdLocation + '\'' +
                ", laTelephone1='" + laTelephone1 + '\'' +
                ", posNum='" + posNum + '\'' +
                ", voidTime=" + voidTime +
                ", newTime=" + newTime +
                ", originalTime=" + originalTime +
                '}';
    }
}

package com.remoc.sync.domain.posvm;

import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "v_ai_refund_time")
public class RefundTime  {
    private String sdLocation;

    private String laTelephone1;

    private String posNum;

    @Id
    private LocalDateTime refundTime;

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

    public LocalDateTime getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(LocalDateTime refundTime) {
        this.refundTime = refundTime;
    }
}

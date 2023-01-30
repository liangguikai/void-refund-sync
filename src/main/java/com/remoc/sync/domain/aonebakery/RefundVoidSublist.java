package com.remoc.sync.domain.aonebakery;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 子表(Refund/Void)
 */
@Entity
public class RefundVoidSublist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        //主键id
    private String equipmentNumber;//A1 设备IMEI
    private String surveillanceUrl;//视频信息
    private String screenshotUrl;//封面地址
    private Integer decisionOutcome;//判定结果
    private String fileName;//视频url名
    private String confirmingPerson;//确定者
    private LocalDateTime updateTime;//修改时间
    private Long refundVoidInfoId;//主表id(refund/void)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getSurveillanceUrl() {
        return surveillanceUrl;
    }

    public void setSurveillanceUrl(String surveillanceUrl) {
        this.surveillanceUrl = surveillanceUrl;
    }

    public String getScreenshotUrl() {
        return screenshotUrl;
    }

    public void setScreenshotUrl(String screenshotUrl) {
        this.screenshotUrl = screenshotUrl;
    }

    public Integer getDecisionOutcome() {
        return decisionOutcome;
    }

    public void setDecisionOutcome(Integer decisionOutcome) {
        this.decisionOutcome = decisionOutcome;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getConfirmingPerson() {
        return confirmingPerson;
    }

    public void setConfirmingPerson(String confirmingPerson) {
        this.confirmingPerson = confirmingPerson;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getRefundVoidInfoId() {
        return refundVoidInfoId;
    }

    public void setRefundVoidInfoId(Long refundVoidInfoId) {
        this.refundVoidInfoId = refundVoidInfoId;
    }
}

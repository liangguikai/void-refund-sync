package com.remoc.sync.domain.aonebakery;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class VoidInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime voidTime;

    private Integer decisionOutcome;

    private Integer equipmentManufacturer;

    private String equipmentNumber;

    private Long storeId;

    private LocalDateTime updateTime;

    private String fileName;

    private String surveillanceUrl;

    private String screenshotUrl;


    public VoidInfo() {
    }

    public VoidInfo( LocalDateTime voidTime, String equipmentNumber, Long storeId, String fileName, String surveillanceUrl, String screenshotUrl) {
        this.voidTime = voidTime;
        this.decisionOutcome = 0;
        this.equipmentManufacturer = 1;
        this.equipmentNumber = equipmentNumber;
        this.storeId = storeId;
        this.updateTime = LocalDateTime.now();
        this.fileName = fileName;
        this.surveillanceUrl = surveillanceUrl;
        this.screenshotUrl = screenshotUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getVoidTime() {
        return voidTime;
    }

    public void setVoidTime(LocalDateTime voidTime) {
        this.voidTime = voidTime;
    }

    public Integer getDecisionOutcome() {
        return decisionOutcome;
    }

    public void setDecisionOutcome(Integer decisionOutcome) {
        this.decisionOutcome = decisionOutcome;
    }


    public Integer getEquipmentManufacturer() {
        return equipmentManufacturer;
    }

    public void setEquipmentManufacturer(Integer equipmentManufacturer) {
        this.equipmentManufacturer = equipmentManufacturer;
    }

    public String getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(String equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
}

package com.remoc.sync.domain.aonebakery;


import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 主表名(refund/void)
 */
@Entity
public class RefundVoidInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        //主键id
    private LocalDateTime salesDate;//警报创建时间
    private Long storeId;//商店id
    private String PosNum;//
    private Integer refundVoidType;//(refund/void)类型
    private Integer decisionOutcome;//判定结果

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(LocalDateTime salesDate) {
        this.salesDate = salesDate;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getRefundVoidType() {
        return refundVoidType;
    }

    public void setRefundVoidType(Integer refundVoidType) {
        this.refundVoidType = refundVoidType;
    }

    public Integer getDecisionOutcome() {
        return decisionOutcome;
    }

    public String getPosNum() {
        return PosNum;
    }

    public void setPosNum(String posNum) {
        PosNum = posNum;
    }

    public void setDecisionOutcome(Integer decisionOutcome) {
        this.decisionOutcome = decisionOutcome;
    }
}

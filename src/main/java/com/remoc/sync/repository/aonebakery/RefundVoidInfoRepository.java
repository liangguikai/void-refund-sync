package com.remoc.sync.repository.aonebakery;

import com.remoc.sync.domain.aonebakery.RefundVoidInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RefundVoidInfoRepository  extends JpaRepository<RefundVoidInfo, Long>, JpaSpecificationExecutor<RefundVoidInfo> {

    List<RefundVoidInfo> findAllByStoreId(Long storeId);

    Boolean existsBySalesDateAndAndRefundVoidType(LocalDateTime salesDate,Integer refundVoidType);

}

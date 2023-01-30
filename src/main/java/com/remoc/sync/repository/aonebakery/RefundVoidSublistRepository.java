package com.remoc.sync.repository.aonebakery;

import com.remoc.sync.domain.aonebakery.RefundVoidSublist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundVoidSublistRepository  extends JpaRepository<RefundVoidSublist, Long>, JpaSpecificationExecutor<RefundVoidSublist> {
    List<RefundVoidSublist> findByRefundVoidInfoId(Long id);

}

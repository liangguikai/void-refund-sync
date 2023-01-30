package com.remoc.sync.repository.posvm;

import com.remoc.sync.domain.posvm.RefundTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosvmRefundTimeRepository extends JpaRepository<RefundTime, Long> {

    List<RefundTime> findAllByLaTelephone1LikeOrderByRefundTimeDesc(String laTelephone1);

}

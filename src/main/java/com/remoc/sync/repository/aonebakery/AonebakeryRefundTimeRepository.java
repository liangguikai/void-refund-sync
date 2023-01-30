package com.remoc.sync.repository.aonebakery;

import com.remoc.sync.domain.aonebakery.RefundInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AonebakeryRefundTimeRepository extends JpaRepository<RefundInfo, Long> {
}

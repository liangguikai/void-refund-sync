package com.remoc.sync.repository.aonebakery;

import com.remoc.sync.domain.aonebakery.VoidInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AonebakeryVoidTimeRepository  extends JpaRepository<VoidInfo, Long> {
}

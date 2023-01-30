package com.remoc.sync.repository.aonebakery;

import com.remoc.sync.domain.aonebakery.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AonebakeryStoreRepository extends JpaRepository<Store, Long> {
}

package com.remoc.sync.repository.aonebakery;

import com.remoc.sync.domain.aonebakery.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AonebakeryEquipmentRepository extends JpaRepository<Equipment, Long> {

    List<Equipment> findAllByName(String name);

    Equipment findAllByNameAndStoreId(String name, Long storeId);

    List<Equipment> findAllByNameIn(Collection<String> name);

    Equipment findByEquipmentNumber(String equipmentNumber);
}

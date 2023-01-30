package com.remoc.sync.repository.aonebakery;

import com.remoc.sync.domain.aonebakery.ParamSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;



@Repository
public interface ParamSettingRepository extends JpaRepository<ParamSetting, Long>, JpaSpecificationExecutor<ParamSetting>{

}

package com.remoc.sync.repository.posvm;

import com.remoc.sync.domain.posvm.VoidTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosvmVoidTimeRepository extends JpaRepository<VoidTime, Long> {

    List<VoidTime> findAllByLaTelephone1LikeOrderByVoidTimeDesc(String laTelephone1);
    List<VoidTime> findAllByPosNumAndLaTelephone1LikeOrderByVoidTimeDesc(String posNum, String laTelephone1);

}

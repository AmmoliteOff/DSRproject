package org.dsr.practice.repos;

import org.dsr.practice.models.Bill;
import org.dsr.practice.models.BillInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BillInfoRepository extends CrudRepository<BillInfo,Long> {
    List<BillInfo> findTopByOrderByBillInfoIdDesc();
}
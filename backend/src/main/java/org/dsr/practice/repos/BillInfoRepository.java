package org.dsr.practice.repos;

import org.dsr.practice.models.Bill;
import org.dsr.practice.models.BillInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillInfoRepository extends CrudRepository<BillInfo,Long> {
    List<BillInfo> findTopByOrderByBillInfoIdDesc();
}
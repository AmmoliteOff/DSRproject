package org.dsr.practice.repos;

import org.dsr.practice.models.Bill;
import org.dsr.practice.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillsRepository extends CrudRepository<Bill,Long> {
    List<Bill> findTopByOrderByBillIdDesc();
}

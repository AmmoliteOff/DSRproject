package org.dsr.practice.repos;

import org.dsr.practice.models.Account;
import org.dsr.practice.models.Bill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BillRepository extends CrudRepository<Bill,Long> {
    List<Bill> findTopByOrderByBillIdDesc();
    Bill findByBillId(Long billId);
}

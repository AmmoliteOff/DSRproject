package org.dsr.practice.dao;

import org.dsr.practice.models.Bill;
import org.dsr.practice.repos.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillsDAO {
    BillRepository billRepository;

    public BillsDAO(@Autowired BillRepository billRepository){
        this.billRepository = billRepository;
    }

    public Bill Add(Bill bill){
        billRepository.save(bill);
       return billRepository.findTopByOrderByBillIdDesc().get(0);
    }
}

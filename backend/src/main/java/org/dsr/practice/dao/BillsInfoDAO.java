package org.dsr.practice.dao;

import org.dsr.practice.models.BillInfo;
import org.dsr.practice.repos.BillInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillsInfoDAO {

    BillInfoRepository billInfoRepository;

    public BillsInfoDAO(@Autowired BillInfoRepository billInfoRepository) {
        this.billInfoRepository = billInfoRepository;
    }

    public void Add(BillInfo billInfo){
        billInfoRepository.save(billInfo);
    }
}

package org.dsr.practice.services;

import org.dsr.practice.models.BillInfo;
import org.dsr.practice.repos.BillInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillInfoService {
    private BillInfoRepository billInfoRepository;

    public BillInfoService(@Autowired BillInfoRepository billInfoRepository){
        this.billInfoRepository = billInfoRepository;
    }

    public void Add(BillInfo billInfo){
        billInfoRepository.save(billInfo);
    }
}

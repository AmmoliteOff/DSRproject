package org.dsr.practice.dao;

import org.dsr.practice.models.Account;
import org.dsr.practice.models.Bill;
import org.dsr.practice.models.User;
import org.dsr.practice.repos.BillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillsDAO {
    @Autowired
    BillsRepository billsRepository;


    public void addBill(String title, String description, double debt, Account account, User user){
        billsRepository.save(new Bill(account, user, debt, title, description));
    }

    public void update(Bill bill){
        billsRepository.save(bill);
    }
    public Bill getLastBill(){
        return billsRepository.findTopByOrderByBillIdDesc().get(0);
    }
}

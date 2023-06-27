package org.dsr.practice.controllers;

import org.dsr.practice.dao.AccountsDAO;
import org.dsr.practice.dao.BillsDAO;
import org.dsr.practice.dao.BillsInfoDAO;
import org.dsr.practice.dao.UsersDAO;
import org.dsr.practice.models.Bill;
import org.dsr.practice.models.BillInfo;
import org.dsr.practice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@RestController
public class BillController {
    UsersDAO usersDAO;

    BillsDAO billsDAO;

    AccountsDAO accountsDAO;

    BillsInfoDAO billsInfoDAO;


    public BillController(@Autowired UsersDAO usersDAO, @Autowired BillsDAO billsDAO, @Autowired AccountsDAO accountsDAO, @Autowired BillsInfoDAO billsInfoDAO) {
        this.usersDAO = usersDAO;
        this.billsDAO = billsDAO;
        this.accountsDAO = accountsDAO;
        this.billsInfoDAO = billsInfoDAO;
    }

    @PostMapping("/api/createBill")
    public ResponseEntity createBill(@RequestParam(name = "title") String title,
                                        @RequestParam(name = "description") String description,
                                        @RequestParam(name = "type") String type,
                                        @RequestParam(name = "accountId") Long accountId,
                                        @RequestParam(required = false, name="manualPricesAndUsers") String[] manualPricesAndUsers,
                                        @RequestParam(required = false, name="percentsAndUsers") String[] percentsAndUsers,
                                        @RequestParam(name="fullPrice") Long fullPrice) {
        var account = accountsDAO.FindById(accountId); //�������� ����, � ������� ��������� �����
        switch (type){
            case "Manual"->{//���� ����������� ��� ������� ������������ ��������
                HashMap<User, Long> usersIn = new HashMap<User, Long>();
                for (String str:manualPricesAndUsers) {
                    var c = str.split(";");
                    var usr = usersDAO.getUserById(Long.parseLong(c[0]));
                    usersIn.put(usr, Long.parseLong(c[1])); //������ ����� ������������� ����� �������
                }

                Bill bl = new Bill(account, title, description); //OneToMany, ����� ������ ������� bill ��� billInfo
                var bill = billsDAO.Add(bl); //������� ������ ��� �������� �����

                for(User usr: usersIn.keySet()){
                    BillInfo b = new BillInfo(bill, usr, usersIn.get(usr)); //������ ��������� ������ ������ �����
                    billsInfoDAO.Add(b);
                }
                return new ResponseEntity(HttpStatus.OK);
            }
            case "Percents"-> {//���� ����������� ��� ������� ������������ � ��������� �� ����� �����
                HashMap<User, Integer> usersIn = new HashMap<User, Integer>();
                for (String str : percentsAndUsers) {
                    var c = str.split(";");
                    var usr = usersDAO.getUserById(Long.parseLong(c[0]));
                    usersIn.put(usr, Integer.parseInt(c[1])); //������ ����� ������������� ����� �� ���������
                }

                Bill bl = new Bill(account, title, description);
                var bill = billsDAO.Add(bl); //������� ������ ��� �������� �����

                for (User usr : usersIn.keySet()) {
                    BillInfo b = new BillInfo(bill, usr, fullPrice*usersIn.get(usr)/100); //������ ��������� ������ ������ ����� c ������ ���������
                    billsInfoDAO.Add(b);
                }
                return new ResponseEntity(HttpStatus.OK);
            }
            case "Auto"->{
                Bill bl = new Bill(account, title, description);
                var bill = billsDAO.Add(bl); //������� ������ ��� �������� �����

                for (User usr : account.getUsers()) {
                    BillInfo b = new BillInfo(bill, usr, fullPrice/account.getUsers().size()); //������ ��������� ������ ������ ����� c ������ ���������
                    billsInfoDAO.Add(b);
                }
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
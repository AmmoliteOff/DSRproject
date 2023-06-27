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
        var account = accountsDAO.FindById(accountId); //Получаем счёт, в котором находится трата
        switch (type){
            case "Manual"->{//Цены указываются для каждого пользователя отдельно
                HashMap<User, Long> usersIn = new HashMap<User, Long>();
                for (String str:manualPricesAndUsers) {
                    var c = str.split(";");
                    var usr = usersDAO.getUserById(Long.parseLong(c[0]));
                    usersIn.put(usr, Long.parseLong(c[1])); //Создаём карту распределения долга вручную
                }

                Bill bl = new Bill(account, title, description); //OneToMany, можно просто указать bill для billInfo
                var bill = billsDAO.Add(bl); //Находим только что созданую трату

                for(User usr: usersIn.keySet()){
                    BillInfo b = new BillInfo(bill, usr, usersIn.get(usr)); //Создаём отдельный объект нового долга
                    billsInfoDAO.Add(b);
                }
                return new ResponseEntity(HttpStatus.OK);
            }
            case "Percents"-> {//Цены указываются для каждого пользователя в процентах от общей суммы
                HashMap<User, Integer> usersIn = new HashMap<User, Integer>();
                for (String str : percentsAndUsers) {
                    var c = str.split(";");
                    var usr = usersDAO.getUserById(Long.parseLong(c[0]));
                    usersIn.put(usr, Integer.parseInt(c[1])); //Создаём карту распределения долга по процентам
                }

                Bill bl = new Bill(account, title, description);
                var bill = billsDAO.Add(bl); //Находим только что созданую трату

                for (User usr : usersIn.keySet()) {
                    BillInfo b = new BillInfo(bill, usr, fullPrice*usersIn.get(usr)/100); //Создаём отдельный объект нового долга c учётом процентов
                    billsInfoDAO.Add(b);
                }
                return new ResponseEntity(HttpStatus.OK);
            }
            case "Auto"->{
                Bill bl = new Bill(account, title, description);
                var bill = billsDAO.Add(bl); //Находим только что созданую трату

                for (User usr : account.getUsers()) {
                    BillInfo b = new BillInfo(bill, usr, fullPrice/account.getUsers().size()); //Создаём отдельный объект нового долга c учётом процентов
                    billsInfoDAO.Add(b);
                }
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
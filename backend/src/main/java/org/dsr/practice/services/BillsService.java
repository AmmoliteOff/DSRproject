package org.dsr.practice.services;

import org.dsr.practice.models.Account;
import org.dsr.practice.models.Bill;
import org.dsr.practice.models.User;
import org.dsr.practice.repos.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillsService {
    private AccountsService accountsService;
    private BillRepository billRepository;
    private BillInfoService billInfoService;

    private UsersService usersService;

    public BillsService(@Autowired AccountsService accountsService,
                        @Autowired BillRepository billRepository,
                        @Autowired BillInfoService billInfoService,
                        @Autowired UsersService usersService){
        this.accountsService = accountsService;
        this.billInfoService = billInfoService;
        this.billRepository = billRepository;
        this.usersService = usersService;
    }

    public Account getAccount(Long accountId){
        return accountsService.getAccount(accountId);
    }
    public Bill AddBill(Bill bill){
        return billRepository.save(bill);
    }

    public Bill getBill(String userPr, Long accountId, Long billId){
        User user = usersService.getUser(userPr);
        try {
            return user.getAccount(accountId).getBill(billId);
        }
        catch (Exception e){
            return null;
        }
    }

    public BillInfoService getBillInfoService() {
        return billInfoService;
    }

    public AccountsService getAccountsService() {
        return accountsService;
    }

    public void setAccountsService(AccountsService accountsService) {
        this.accountsService = accountsService;
    }


    public UsersService getUsersService() {
        return usersService;
    }

}

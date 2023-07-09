package org.dsr.practice.services;

import org.dsr.practice.models.Account;
import org.dsr.practice.models.Bill;
import org.dsr.practice.models.User;
import org.dsr.practice.repos.BillRepository;
import org.dsr.practice.utils.PriceConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@Transactional
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

    public boolean Repayment(String pr, Long accountId, Long billId, BigDecimal value){
        try {
            var billInfo = usersService
                    .getUser(pr)
                    .getBillInfos()
                    .stream()
                    .filter(billInfoObj -> billInfoObj.getBill().getBillId() == billId && billInfoObj.getBill().getAccount().getAccountId() == accountId)
                    .collect(Collectors.toList())
                    .get(0);
            billInfo.setDebt(billInfo.getRawDebt() - PriceConverter.convert(value));
            if(billInfo.getRawDebt()<0){
                billInfo.setDebt(0L);
            }
            billInfoService.Add(billInfo);
        }
        catch (Exception e){
            return false;
        }
        return true;
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

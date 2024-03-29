package org.dsr.practice.services.billspliter;

import org.dsr.practice.models.Bill;
import org.dsr.practice.models.BillInfo;
import org.dsr.practice.models.BillSplitData;
import org.dsr.practice.models.User;
import org.dsr.practice.services.AccountsService;
import org.dsr.practice.services.BillInfoService;
import org.dsr.practice.services.BillsService;
import org.dsr.practice.services.UsersService;
import org.dsr.practice.utils.PriceConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoBillSplitter implements BillSplitter {

    private AccountsService accountsService;
    private BillsService billsService;
    private BillInfoService billInfoService;
    private UsersService usersService;

    public AutoBillSplitter(@Autowired AccountsService accountsService,
                            @Autowired BillsService billsService,
                            @Autowired BillInfoService billInfoService,
                            @Autowired UsersService usersService) {
        this.accountsService = accountsService;
        this.billsService = billsService;
        this.billInfoService = billInfoService;
        this.usersService = usersService;
    }

    @Override
    public boolean createBill(BillSplitData billSplitData){
        var accountId = billSplitData.getAccountId();
        var title = billSplitData.getTitle();
        var description = billSplitData.getDescription();
        var usersIn = billSplitData.getUsersMap();
        var fullPrice = billSplitData.getFullPrice();


        try {
            var account = accountsService.getAccount(accountId);
            Bill bl = new Bill(account, title, description, fullPrice);

            var wholePart = fullPrice/(100*account.getUsers().size());
            var secondaryPart = (fullPrice%100) / account.getUsers().size();
            var debt = wholePart*100 + secondaryPart;
            var fullPriceCopy = fullPrice;
            int counter = 1;

            var bill = billsService.AddBill(bl); //������� ������ ��� �������� �����
            for (User usr : account.getUsers()) {
                BillInfo b;
                if(counter != account.getUsers().size()) {
                    b = new BillInfo(bill, usr, debt);
                }
                else{
                    b = new BillInfo(bill, usr, fullPriceCopy);
                }
                counter++;
                billInfoService.Add(b);
                fullPriceCopy -= debt;
            }
        }
        catch (Exception e){
            return false;
            //throw
        }
        return true;
    }
}

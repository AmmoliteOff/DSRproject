package UnitTests;

import org.dsr.practice.Application;
import org.dsr.practice.models.Account;
import org.dsr.practice.models.Bill;
import org.dsr.practice.models.BillInfo;
import org.dsr.practice.models.User;
import org.dsr.practice.repos.BillRepository;
import org.dsr.practice.services.AccountsService;
import org.dsr.practice.services.BillInfoService;
import org.dsr.practice.services.BillsService;
import org.dsr.practice.services.UsersService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class})
public class BillsServiceTests {

    @Autowired
    BillsService billsService;

    @MockBean
    AccountsService accountsService;

    @MockBean
    UsersService usersService;

    @MockBean
    BillInfoService billInfoService;

    @MockBean
    BillRepository billRepository;

    @Test
    public void repayment_test(){
        Account account = new Account();
        account.setAccountId(1l);

        BillInfo billInfo1 = new BillInfo();
        BillInfo billInfo2 = new BillInfo();

        Bill bill1 = new Bill();
        Bill bill2 = new Bill();

        bill1.setAccount(account);
        bill1.setBillId(1l);
        bill2.setAccount(account);
        bill2.setBillId(2l);

        billInfo1.setBill(bill1);
        billInfo2.setBill(bill2);
        billInfo1.setDebt(50000l);
        billInfo2.setDebt(70000l);

        var biL = new ArrayList<BillInfo>();
        biL.add(billInfo1);
        biL.add(billInfo2);

        User user = new User();
        user.setEmail("some@mail.ru");
        user.setBillInfos(biL);

        Mockito.doReturn(user).when(usersService).getUser("some@mail.ru");

        String title = "Example";
        String description = "Test";

        boolean res1 = billsService.Repayment("some@mail.ru", 1l, 1l, new BigDecimal("5000.00"));
        boolean res2 = billsService.Repayment("some@mail.ru", 1l, 2l, new BigDecimal("500.00"));

        Assertions.assertTrue(res1);
        Assertions.assertTrue(res2);
        Assertions.assertEquals(0l, billInfo1.getRawDebt());
        Assertions.assertEquals(20000l, billInfo2.getRawDebt());
        Mockito.verify(usersService, Mockito.times(2)).getUser("some@mail.ru");
        Mockito.verify(billInfoService, Mockito.times(2)).Add(ArgumentMatchers.any(BillInfo.class));
    }
}

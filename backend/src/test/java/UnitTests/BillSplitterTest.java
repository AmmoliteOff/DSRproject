package UnitTests;

import org.checkerframework.checker.units.qual.A;
import org.dsr.practice.Application;
import org.dsr.practice.models.*;

import org.dsr.practice.services.AccountsService;
import org.dsr.practice.services.BillInfoService;
import org.dsr.practice.services.BillsService;
import org.dsr.practice.services.UsersService;
import org.dsr.practice.services.billspliter.BillSpliterProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BillSplitterTest {

    @MockBean
    AccountsService accountsService;
    @MockBean
    BillsService billsService;
    @MockBean
    BillInfoService billInfoService;
    @MockBean
    UsersService usersService;
    @Autowired
    BillSpliterProvider billSpliterProvider;

    private User user1;
    private User user2;
    
    private Account account;
    
    private Bill bill;
    private BillSplitData billSplitData;

    @BeforeAll
    public void init(){
        user1 = new User();
        user1.setUserId(1L);
        user2 = new User();
        user2.setUserId(2L);

        billSplitData = new BillSplitData();
        billSplitData.setAccountId(1l);
        billSplitData.setTitle("Новая трата");
        billSplitData.setDescription("Проверка");
        billSplitData.setFullPrice(new BigDecimal("5000.00"));


        account = new Account();
        var users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        account.setUsers(users);
        bill = new Bill();
        bill.setAccount(account);
    }

    @Test
    public void auto_splitter_test(){
        billSplitData.setType(0);

        Mockito.doReturn(account).when(accountsService).getAccount(ArgumentMatchers.anyLong());
        Mockito.doReturn(user1).when(usersService).getUser(1l);
        Mockito.doReturn(user2).when(usersService).getUser(2l);
        Mockito.doReturn(bill).when(billsService).AddBill(ArgumentMatchers.any(Bill.class));

        var billSpliter = billSpliterProvider.getBillSpliter(billSplitData.getType());
        var res1 = billSpliter.createBill(billSplitData);

        assertTrue(res1);
        Mockito.verify(billInfoService, Mockito.times(2)).Add(ArgumentMatchers.any(BillInfo.class));
    }

    @Test
    public void percent_splitter_test(){
        billSplitData.setType(1);
        Pairs pairs1 = new Pairs();
        Pairs pairs2 = new Pairs();
        pairs1.setUserId(1l);
        pairs1.setDebt(new BigDecimal("73.52"));
        pairs2.setUserId(2l);
        pairs2.setDebt(new BigDecimal("26.48"));
        List<Pairs> map = new ArrayList<Pairs>();
        map.add(pairs1);
        map.add(pairs2);
        billSplitData.setUsersMap(map);
        Mockito.doReturn(account).when(accountsService).getAccount(ArgumentMatchers.anyLong());
        Mockito.doReturn(user1).when(usersService).getUser(1l);
        Mockito.doReturn(user2).when(usersService).getUser(2l);
        Mockito.doReturn(bill).when(billsService).AddBill(ArgumentMatchers.any(Bill.class));

        var billSpliter = billSpliterProvider.getBillSpliter(billSplitData.getType());
        var res1 = billSpliter.createBill(billSplitData);

        assertTrue(res1);
        Mockito.verify(billInfoService, Mockito.times(2)).Add(ArgumentMatchers.any(BillInfo.class));
    }
    
    @Test
    public void manual_splitter_test(){
        billSplitData.setType(2);
        Pairs pairs1 = new Pairs();
        Pairs pairs2 = new Pairs();
        pairs1.setUserId(1l);
        pairs1.setDebt(new BigDecimal("4312.42"));
        pairs2.setUserId(2l);
        pairs2.setDebt(new BigDecimal("687.58"));
        List<Pairs> map = new ArrayList<Pairs>();
        map.add(pairs1);
        map.add(pairs2);
        billSplitData.setUsersMap(map);
        Mockito.doReturn(account).when(accountsService).getAccount(ArgumentMatchers.anyLong());
        Mockito.doReturn(user1).when(usersService).getUser(1l);
        Mockito.doReturn(user2).when(usersService).getUser(2l);
        Mockito.doReturn(bill).when(billsService).AddBill(ArgumentMatchers.any(Bill.class));


        var billSpliter = billSpliterProvider.getBillSpliter(billSplitData.getType());
        var res1 = billSpliter.createBill(billSplitData);

        assertTrue(res1);
        Mockito.verify(billInfoService, Mockito.times(2)).Add(ArgumentMatchers.any(BillInfo.class));
    }
}

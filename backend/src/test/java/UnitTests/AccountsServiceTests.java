package UnitTests;

import org.checkerframework.checker.units.qual.A;
import org.dsr.practice.Application;
import org.dsr.practice.models.Account;
import org.dsr.practice.models.User;
import org.dsr.practice.repos.AccountRepository;
import org.dsr.practice.services.AccountsService;
import org.dsr.practice.services.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class})
public class AccountsServiceTests {

    @Autowired
    private AccountsService accountsService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private UsersService usersService;


    @Test
    public void createAccountTest(){
        String[] users1 = {"1", "2"};
        String[] users2 = {"", "" ,""};

        User user1 = new User();
        user1.setUserId(1L);
        User user2 = new User();
        user2.setUserId(2L);

        Account account = new Account();
        var users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        account.setUsers(users);
        account.setTitle("Проверка");
        account.setTitle("Тест");

        Mockito.doReturn(user1).when(usersService).getUser(1L);
        Mockito.doReturn(user2).when(usersService).getUser(2L);
        Mockito.doReturn(account).when(accountRepository).save(ArgumentMatchers.any(Account.class));

       var result1 = accountsService.createAccount("Проверка", "Тест", users1);
       var result2 = accountsService.createAccount("", "", users2);

        assertTrue(result1);
        assertFalse(result2);
        Mockito.verify(accountRepository, Mockito.times(1)).save(ArgumentMatchers.any(Account.class));
        Mockito.verify(usersService, Mockito.times(2)).Update(ArgumentMatchers.any(User.class));
        assertNotNull(user1.getAccounts().get(0));
        assertNotNull(user2.getAccounts().get(0));
    }
}

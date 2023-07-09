import org.dsr.practice.Application;
import org.dsr.practice.services.AccountsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class})
public class AccountsServiceTests {

    @Autowired
    private AccountsService accountsService;

    @Test
    public void createAccountTest(){
        String[] users = {"1", "2"};

        var result = accountsService.createAccount("Проверка", "Тест", users);

        assertTrue(result);
    }
}

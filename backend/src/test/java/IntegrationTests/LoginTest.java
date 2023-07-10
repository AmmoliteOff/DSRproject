package IntegrationTests;

import org.dsr.practice.Application;
import org.dsr.practice.controllers.AuthController;
import org.dsr.practice.services.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UsersService usersService;

    @Test
    public void successLoginTest() throws Exception{
        var user = usersService.getUser("admin");
        this.mockMvc.perform(formLogin().user("email","admin").password("code", user.getCode()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void failureLoginTest() throws Exception{
        this.mockMvc.perform(formLogin().user("email","admin").password("code", "wrongPassword"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}

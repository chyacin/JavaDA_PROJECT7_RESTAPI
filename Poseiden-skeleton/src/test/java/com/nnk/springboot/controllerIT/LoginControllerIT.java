package com.nnk.springboot.controllerIT;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class LoginControllerIT {


    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webContext;

    @MockBean
    private UserServiceImpl userService;

    @Before
    public void setupMockMvc() {


        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    public void login()throws Exception {

        mockMvc.perform(get("/app/login"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin")
    public void getAllUserArticles()throws Exception {

        User user = new User();
        user.setId(1);
        user.setUsername("userName");
        user.setFullname("John Sam");
        user.setRole("ADMIN");


      when(userService.findUserById(1)).thenReturn(user);

        mockMvc.perform(get("/app/secure/article-details"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin")
    public void error() throws Exception{

        mockMvc.perform(get("/app/error"))
                .andExpect(status().is2xxSuccessful());


    }

}

package com.nnk.springboot.controllerIT;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class UserControllerIT {

    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;


    @Autowired
    WebApplicationContext webContext;

    @Before
    public void setupMockMvc() {


        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithUserDetails("admin")
    public void home()throws Exception {

        mockMvc.perform(get("/user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin")
    public void addUserForm()throws Exception {

        mockMvc.perform(get("/user/add"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin")
    public void validate()throws Exception {

       // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setId(1);
        user.setUsername("userName");
        user.setFullname("John Sam");
      //  user.setPassword(encoder.encode("Oldschool@317"));
        user.setPassword("Oldschool@317");
        user.setRole("ADMIN");

        when(userService.saveUser(user)).thenReturn(user);


        mockMvc.perform(post("/user/validate")
                .param("userName", user.getUsername())
                .param("fullName", user.getFullname())
                .param("password", user.getPassword())
                .param("role",user.getRole()));


        mockMvc.perform(get("/user/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().size(1));
    }



    @Test
    @WithUserDetails("admin")
    public void deleteBid()throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("userName");
        user.setFullname("John Sam");
        user.setRole("ADMIN");

        when(userService.findUserById(1)).thenReturn(user);

        mockMvc.perform(get("/trade/delete/{id}", "1"))
                .andExpect(status().is2xxSuccessful());
    }
}

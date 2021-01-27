package com.nnk.springboot.controllerITest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class HomeControllerITest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webContext;

    @Before
    public void setupMockMvc() {


        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithUserDetails("adminOne")
    public void home()throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithUserDetails("adminOne")
    public void adminHome()throws Exception {

        mockMvc.perform(get("/admin/home"))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(status().is3xxRedirection());
    }

}

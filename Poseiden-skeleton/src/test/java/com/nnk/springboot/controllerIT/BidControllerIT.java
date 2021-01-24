package com.nnk.springboot.controllerIT;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListServiceImpl;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.context.support.WithUserDetails;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest()
public class BidControllerIT {

    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private BidListServiceImpl bidListService;

    @Autowired
    WebApplicationContext webContext;

    @Before
    public void setupMockMvc() {


        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithUserDetails("admin")
    public void home()throws Exception {

        mockMvc.perform(get("/bidList/list"))
                .andExpect(model().attributeExists("bidList"))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithUserDetails("admin")
    public void addBidForm()throws Exception {

        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin")
    public void validate()throws Exception {

        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("Account");
        bidList.setType("Type");
        bidList.setBidQuantity(20.00);

        when(bidListService.saveBidList(bidList)).thenReturn(bidList);


        mockMvc.perform(post("/bidList/validate")
                .param("account", bidList.getAccount())
                .param("Type", bidList.getType())
                .param("bidQuantity",bidList.getBidQuantity().toString()))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("bidList"))
                .andExpect(model().size(1));

    }

    @Test
    @WithUserDetails("admin")
    public void showUpdateForm()throws Exception {

        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("Account");
        bidList.setType("Type");
        bidList.setBidQuantity(20.00);

        when(bidListService.findBidListById(1)).thenReturn(bidList);

        mockMvc.perform(get("/bidList/update/{id}", "1"))
                .andExpect(model().attributeExists("bidList"))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithUserDetails("admin")
    public void updateBid()throws Exception{

        BidList bidList = new BidList();
        bidList.setBidListId(5);
        bidList.setAccount("Account");
        bidList.setType("Type");
        bidList.setBidQuantity(40.00);

        when(bidListService.findBidListById(bidList.getBidListId())).thenReturn(bidList);


        mockMvc.perform(post("/bidList/update/{id}", "5")
                .param("account", bidList.getAccount())
                .param("Type", bidList.getType())
                .param("bidQuantity",bidList.getBidQuantity().toString()))
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("bidList"))
                .andExpect(model().size(1));

    }

    @Test
    @WithUserDetails("admin")
    public void deleteBid()throws Exception {

        BidList bidList = new BidList();
        bidList.setBidListId(5);
        bidList.setAccount("Account");
        bidList.setType("Type");
        bidList.setBidQuantity(40.00);

        when(bidListService.findBidListById(bidList.getBidListId())).thenReturn(bidList);

        mockMvc.perform(get("/bidList/delete/{id}", "5"))
                .andExpect(status().is3xxRedirection());

    }

}

package com.nnk.springboot.controllerIT;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeServiceImpl;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class TradeControllerIT {

    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private TradeServiceImpl tradeService;

    @Autowired
    WebApplicationContext webContext;

    @Before
    public void setupMockMvc() {


        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithUserDetails("admin")
    public void home()throws Exception {

        mockMvc.perform(get("/trade/list"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin")
    public void addTradeForm()throws Exception {

        mockMvc.perform(get("/trade/add"))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    @WithUserDetails("admin")
    public void validate()throws Exception{

        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("account");
        trade.setType("type");
        trade.setBuyQuantity(20.00);

        when(tradeService.saveTrade(trade)).thenReturn(trade);

        mockMvc.perform(post("/trade/validate")
                .param("account", trade.getAccount())
                .param("Type", trade.getType())
                .param("buyQuantity",trade.getBuyQuantity().toString()))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("trade"))
                .andExpect(model().size(1));
    }

    @Test
    @WithUserDetails("admin")
    public void showUpdateForm()throws Exception {

        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("account");
        trade.setType("type");
        trade.setBuyQuantity(20.00);

        when(tradeService.findTradeById(1)).thenReturn(trade);


        mockMvc.perform(get("/trade/update/{id}", "1"))
                .andExpect(model().attributeExists("trade"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin")
    public void updateBid()throws Exception{

        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("account");
        trade.setType("type");
        trade.setBuyQuantity(20.00);

        when(tradeService.findTradeById(1)).thenReturn(trade);


        mockMvc.perform(post("/trade/update/{id}", "1")
                .param("account", trade.getAccount())
                .param("Type", trade.getType())
                .param("buyQuantity",trade.getBuyQuantity().toString()))
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("trade"))
                .andExpect(model().size(1));
    }

    @Test
    @WithUserDetails("admin")
    public void deleteBid()throws Exception {

        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("account");
        trade.setType("type");
        trade.setBuyQuantity(20.00);

        when(tradeService.findTradeById(1)).thenReturn(trade);

        mockMvc.perform(get("/trade/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection());
    }

}

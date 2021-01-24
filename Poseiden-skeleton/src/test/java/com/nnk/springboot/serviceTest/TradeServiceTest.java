package com.nnk.springboot.serviceTest;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceTest {

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Mock
    TradeRepository tradeRepository;

    @Test
    public void saveTrade_returnTrade(){

        //arrange
        Trade trade = new Trade("Trade Account", "Type");
        trade.setTradeId(1);

        when(tradeRepository.save(trade)).thenReturn(trade);

        //act
        Trade saveTrade = tradeService.saveTrade(trade);

        //assert
        assertNotNull(saveTrade);
        assertEquals(trade.getTradeId(), saveTrade.getTradeId(),0);
        verify(tradeRepository, times(1)).save(any(Trade.class));

    }

    @Test
    public void findTradeById_returnId(){

        //arrange
        Trade trade = new Trade("Trade Account", "Type");
        trade.setTradeId(1);

        when(tradeRepository.findById(1)).thenReturn(java.util.Optional.of(trade));

        //act
        Trade findTradeById = tradeService.findTradeById(1);

        //assert
        assertEquals(trade.getTradeId(), findTradeById.getTradeId(), 0);
    }

    @Test
    public void findAllTrades_returnFindAllTrades(){

        //arrange
        Trade trade = new Trade("Trade Account", "Type");
        trade.setTradeId(1);

        Trade trade1 = new Trade("Trade Account1", "Type1");
        trade1.setTradeId(2);

        when(tradeRepository.findAll()).thenReturn(Arrays.asList(trade, trade1));

        //act
        List<Trade> trades = tradeService.findAllTrade();

        //assert
        assertEquals(2, trades.size());
        assertEquals(1,trades.get(0).getTradeId(),0);
        assertEquals(2,trades.get(1).getTradeId(),0);
        Assert.assertTrue(trades.size() > 0);

    }

    @Test
    public void updateTrade_updateTrade(){

        //arrange
        Trade trade = new Trade("Trade Account", "Type");
        trade.setTradeId(1);

        when(tradeRepository.findById(1)).thenReturn(java.util.Optional.of(trade));

        //act
        tradeService.updateTrade(trade);

        //assert
        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    public void deleteTrade_deleteTrade(){

        //act
        tradeService.deleteTrade(1);

        //assert
        Optional<Trade> tradeList = tradeRepository.findById(1);
        Assert.assertFalse(tradeList.isPresent());
    }


}

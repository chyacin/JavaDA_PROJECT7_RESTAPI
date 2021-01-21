package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface TradeService {

    public Trade saveTrade(Trade trade);
    public Trade findTradeById(int id);
    List<Trade> findAllTrade();
    public void updateTrade(Trade trade);
    public void deleteTrade(int id);


}

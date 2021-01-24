package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService{

    @Autowired
    TradeRepository tradeRepository;

    /**
     * The service method which saves the trade to the database
     * @param trade to be saved
     * @return the saved trade object
     */
    @Override
    public Trade saveTrade(Trade trade) {
        return tradeRepository.save(trade);

    }

    /**
     * The service method which finds the respective trade by their corresponding id
     * @param id of the trade
     * @return  the found trade
     */
    @Override
    public Trade findTradeById(int id) {
        Optional<Trade> tradeOptional = tradeRepository.findById(id);
        if(tradeOptional.isPresent()){
            Trade trade = tradeOptional.get();
            return trade;
        }
        return null;
    }

    /**
     *The service method which finds and returns all the trade in a list
     * @return the list of all the trades
     */
    @Override
    public List<Trade> findAllTrade() {

        List<Trade> trades = tradeRepository.findAll();
        return trades;
    }

    /**
     *  The service method which updates the user's trade
     * @param trade to be updated
     */
    @Override
    public void updateTrade(Trade trade) {
        Trade updateTrade =findTradeById(trade.getTradeId());
        if(updateTrade != null) {
            updateTrade.setAccount(trade.getAccount());
            updateTrade.setType(trade.getType());
            updateTrade.setBuyQuantity(trade.getBuyQuantity());
            updateTrade.setSellQuantity(trade.getSellQuantity());
            updateTrade.setBuyPrice(trade.getBuyPrice());
            updateTrade.setSellPrice(trade.getSellPrice());
            updateTrade.setTradeDate(trade.getTradeDate());
            updateTrade.setSecurity(trade.getSecurity());
            updateTrade.setStatus(trade.getStatus());
            updateTrade.setTrader(trade.getTrader());
            updateTrade.setBenchmark(trade.getBenchmark());
            updateTrade.setBook(trade.getBook());
            updateTrade.setCreationName(trade.getCreationName());
            updateTrade.setCreationDate(trade.getCreationDate());
            updateTrade.setRevisionName(trade.getRevisionName());
            updateTrade.setRevisionDate(trade.getRevisionDate());
            updateTrade.setDealName(trade.getDealName());
            updateTrade.setDealType(trade.getDealType());
            updateTrade.setSourceListId(trade.getSourceListId());
            updateTrade.setSide(trade.getSide());
            tradeRepository.save(updateTrade);
        }
    }

    /**
     * The service method which deletes the user's trade
     * @param id of the trade
     */
    @Override
    public void deleteTrade(int id) {
        tradeRepository.deleteById(id);

    }
}

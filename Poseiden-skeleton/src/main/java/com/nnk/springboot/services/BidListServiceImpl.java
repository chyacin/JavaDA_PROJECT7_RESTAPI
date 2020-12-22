package com.nnk.springboot.services;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListServiceImpl implements  BidListService{

    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public BidList createBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    @Override
    public BidList findBidListById(int id) {
       Optional<BidList> bidListOptional = bidListRepository.findById(id);
       if(bidListOptional.isPresent()){
           BidList bidList = bidListOptional.get();
           return bidList;
       }
        return null;
    }

    @Override
    public List<BidList> findAllBidList() {

        List<BidList> bidLists = bidListRepository.findAll();
        return bidLists;
    }



    @Override
    public void updateBidList(BidList bidList) {
        BidList updateBidList = findBidListById(bidList.getBidListId());
        updateBidList.setBid(bidList.getBid());
        updateBidList.setBidListId(bidList.getBidListId());
        updateBidList.setBidListDate(bidList.getBidListDate());
        updateBidList.setAccount(bidList.getAccount());
        updateBidList.setBidQuantity(bidList.getBidQuantity());
        updateBidList.setType(bidList.getType());
        updateBidList.setAskQuantity(bidList.getAskQuantity());
        updateBidList.setAsk(bidList.getAsk());
        updateBidList.setBenchmark(bidList.getBenchmark());
        updateBidList.setCommentary(bidList.getCommentary());
        updateBidList.setSecurity(bidList.getSecurity());
        updateBidList.setStatus(bidList.getStatus());
        updateBidList.setTrader(bidList.getTrader());
        updateBidList.setBook(bidList.getBook());
        updateBidList.setCreationName(bidList.getCreationName());
        updateBidList.setCreationDate(bidList.getCreationDate());
        updateBidList.setRevisionName(bidList.getRevisionName());
        updateBidList.setRevisionDate(bidList.getRevisionDate());
        updateBidList.setDealName(bidList.getDealName());
        updateBidList.setDealType(bidList.getDealType());
        updateBidList.setSourceListId(bidList.getSourceListId());
        updateBidList.setSide(bidList.getSide());
        bidListRepository.save(updateBidList);


    }

    public void deleteBidList(int id){
        bidListRepository.deleteById(id);
    }
}

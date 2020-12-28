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
    public BidList saveBidList(BidList bidList) {
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
        bidListRepository.save(updateBidList);


    }

    public void deleteBidList(int id){
        bidListRepository.deleteById(id);
    }
}

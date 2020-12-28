package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface BidListService {


    public BidList saveBidList(BidList bidList);
    public BidList findBidListById(int id);
    List<BidList> findAllBidList();
    public void updateBidList(BidList bidList);
    public void deleteBidList(int id);
}

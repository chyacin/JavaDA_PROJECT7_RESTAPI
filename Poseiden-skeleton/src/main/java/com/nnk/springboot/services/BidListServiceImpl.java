package com.nnk.springboot.services;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListServiceImpl implements BidListService{

    @Autowired
    private BidListRepository bidListRepository;

    /**
     * The service method which saves the bid to the database
     * @param bidList the bid to be saved
     * @return the object which is the save bidList
     */
    @Override
    public BidList saveBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    /**
     * The service method which finds the respective bid by their corresponding id
     * @param id the id of the bid
     * @return the found bid
     */
    @Override
    public BidList findBidListById(int id) {
       Optional<BidList> bidListOptional = bidListRepository.findById(id);
       if(bidListOptional.isPresent()){
           BidList bidList = bidListOptional.get();
           return bidList;
       }
        return null;
    }


    /**
     * The service method which finds and returns all the bids and present it in a list
     * @return the list of all the bids
     */
    @Override
    public List<BidList> findAllBidList() {

        List<BidList> bidLists = bidListRepository.findAll();
        return bidLists;
    }


    /**
     * The service method which updates the user's bid
     * @param bidList the bid to be updated
     */
    @Override
    public void updateBidList(BidList bidList) {
        BidList updateBidList = findBidListById(bidList.getBidListId());
        if(updateBidList != null) {
            updateBidList.setBid(bidList.getBid());
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
    }

    /**
     * The service method which deletes the user's bid
     * @param id the id of the bid
     */
    public void deleteBidList(int id){
        bidListRepository.deleteById(id);
    }


}

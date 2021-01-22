package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListServiceImpl;
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

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;



@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BidServiceTest {

    @InjectMocks
    private BidListServiceImpl bidListService;

    @Mock
    private BidListRepository bidListRepository;


    @Test
    public void saveBidList_returnSavedBidList(){

        //arrange
        BidList bidList = new BidList("Account", "Type", 20.0);
        bidList.setBidListId(1);

        when(bidListRepository.save(bidList)).thenReturn(bidList);

        //act
        BidList saveBidList = bidListService.saveBidList(bidList);

        //assert
        assertNotNull(saveBidList);
        assertEquals(bidList.getBidListId(), saveBidList.getBidListId(), 0);
        verify(bidListRepository, times(1)).save(any(BidList.class));

    }

    @Test
    public void findBidListById_returnId(){

        //arrange
        BidList bidList = new BidList("Account", "Type", 10.0);
        bidList.setBidListId(45);

        when(bidListRepository.findById(45)).thenReturn(java.util.Optional.of(bidList));

        //act
        BidList findBidListById = bidListService.findBidListById(45);

        //assert
        assertEquals(bidList.getBidListId(), findBidListById.getBidListId(), 0);

    }

    @Test
    public void findAllBidList_returnFindAllBidList(){

        //arrange
        BidList bidList = new BidList("Account", "Type", 10.0);
        bidList.setBidListId(23);


        BidList bidList2 = new BidList("Account1", "Type1", 20.0);
        bidList2.setBidListId(46);

        when(bidListRepository.findAll()).thenReturn(Arrays.asList(bidList, bidList2));

        //act
        List<BidList> bidLists = bidListService.findAllBidList();

        //assert
        assertEquals(2, bidLists.size());
        assertEquals(23, bidLists.get(0).getBidListId(),0);
        assertEquals(46,bidLists.get(1).getBidListId(),0);
        Assert.assertTrue(bidLists.size() > 1);

    }

    @Test
    public void updateBidList_updateBidList(){

        //arrange
        BidList bidList = new BidList("Account", "Type", 10.0);
        bidList.setBidListId(1);

        when(bidListRepository.findById(1)).thenReturn(java.util.Optional.of(bidList));

        //act
        bidListService.updateBidList(bidList);

        //assert
        verify(bidListRepository, times(1)).save(any(BidList.class));

    }

    @Test
    public void deleteBidList_deleteBidList(){

        //act
        bidListService.deleteBidList(1);

        //assert
        Optional<BidList> bidList = bidListRepository.findById(1);
        Assert.assertFalse(bidList.isPresent());
    }
}

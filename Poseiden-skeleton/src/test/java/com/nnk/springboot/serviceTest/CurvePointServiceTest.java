package com.nnk.springboot.serviceTest;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurveServiceImpl;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CurvePointServiceTest {

    @InjectMocks
    private CurveServiceImpl curveService;

    @Mock
    private CurvePointRepository curvePointRepository;

    @Test
    public void saveCurve_returnCurve(){

        //arrange
        CurvePoint curvePoint = new CurvePoint(1,2.0,25.0);
        curvePoint.setId(1);

        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        //act
        CurvePoint saveCurve = curveService.saveCurvePoint(curvePoint);

        //assert
        assertNotNull(saveCurve);
        assertEquals(curvePoint.getId(), saveCurve.getId(), 0);
        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));
    }

    @Test
    public void findCurveById_returnId(){

        //arrange
        CurvePoint curvePoint = new CurvePoint(1,2.0,25.0);
        curvePoint.setId(25);

        when(curvePointRepository.findById(25)).thenReturn(java.util.Optional.of(curvePoint));

        //act
        CurvePoint findCurveId = curveService.findCurvePointById(25);

        //assert
        assertEquals(curvePoint.getId(), findCurveId.getId(),0);
    }

    @Test
    public void findAllCurve_returnFindAllCurve(){

        //arrange
        CurvePoint curvePoint = new CurvePoint(1,2.0,25.0);
        curvePoint.setId(1);

        CurvePoint curvePoint2 = new CurvePoint(2,4.0,50.0);
        curvePoint2.setId(2);

        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(curvePoint, curvePoint2));

        //act
        List<CurvePoint> curvePoints = curveService.findAllCurvePoint();

        //assert
        assertEquals(2, curvePoints.size());
        assertEquals(1, curvePoints.get(0).getId(),0);
        assertEquals(2, curvePoints.get(1).getId(),0);
        Assert.assertTrue(curvePoints.size() > 1);
    }

    @Test
    public void updateCurve_updateCurve(){
        //arrange
        CurvePoint curvePoint = new CurvePoint(1,2.0,27.0);
        curvePoint.setId(1);

        when(curvePointRepository.findById(1)).thenReturn(java.util.Optional.of(curvePoint));

        //act
        curveService.updateCurvePoint(curvePoint);

        //assert
        verify(curvePointRepository, times(1)).save(any(CurvePoint.class));

    }

    @Test
    public void deleteCurve_deleteCurve(){

        //act
        curveService.deleteCurvePoint(1);

        //assert
        Optional<CurvePoint> curvePoint = curvePointRepository.findById(1);
        Assert.assertFalse(curvePoint.isPresent());
    }

}

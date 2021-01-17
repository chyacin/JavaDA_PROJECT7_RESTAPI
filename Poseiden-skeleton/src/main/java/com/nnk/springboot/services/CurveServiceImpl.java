package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurveServiceImpl implements CurveService{

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * The service method which saves the curve point to the database
     * @param curvePoint the curve point to be saved
     * @return the saved curve point object
     */
    @Override
    public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    /**
     * The service method which finds the respective curve point by their corresponding id
     * @param id the id of the curve point
     * @return the found curve point
     */
    @Override
    public CurvePoint findCurvePointById(int id) {
        Optional<CurvePoint> curvePointOptional = curvePointRepository.findById(id);
        if(curvePointOptional.isPresent()){
            CurvePoint curvePoint = curvePointOptional.get();
            return curvePoint;
        }
        return null;
    }

    /**
     * The service method which finds and returns all the curve points and present it in a list
     * @return the list of all the curve points
     */
    @Override
    public List<CurvePoint> findAllCurvePoint() {
        List<CurvePoint> curvePoints = curvePointRepository.findAll();
        return curvePoints;
    }

    /**
     * The service method which updates the user's curve point
     * @param curvePoint the curve point to be updated
     */
    @Override
    public void updateCurvePoint(CurvePoint curvePoint) {
      CurvePoint updatedCurvePoint = findCurvePointById(curvePoint.getId());
      if(updatedCurvePoint != null) {
          updatedCurvePoint.setCurveId(curvePoint.getCurveId());
          updatedCurvePoint.setTerm(curvePoint.getTerm());
          updatedCurvePoint.setValue(curvePoint.getValue());
          updatedCurvePoint.setCreationDate(curvePoint.getCreationDate());
          updatedCurvePoint.setAsOfDate(curvePoint.getAsOfDate());
          curvePointRepository.save(updatedCurvePoint);

      }
    }

    /**
     * The service method which deletes the user's curve point
     * @param id the id of the curve point
     */
    @Override
    public void deleteCurvePoint(int id) {
       curvePointRepository.deleteById(id);
    }
}

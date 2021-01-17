package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface CurveService {

    public CurvePoint saveCurvePoint(CurvePoint curvePoint);
    public CurvePoint findCurvePointById(int id);
    List<CurvePoint> findAllCurvePoint();
    public void updateCurvePoint(CurvePoint curvePoint);
    public void deleteCurvePoint(int id);
}

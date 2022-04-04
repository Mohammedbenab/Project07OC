package com.nnk.springboot.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exception.CurvePointNotFoundException;
import com.nnk.springboot.repository.CurvePointRepository;

@Service
public class CurvePointService {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

	@Autowired
	private CurvePointRepository curvePointRepository;
	
	/**
	 * 
	 * @param curvePoint
	 * @return
	 * @throws CurvePointNotFoundException
	 */
	public CurvePoint saveCurvePoint(CurvePoint curvePoint) throws CurvePointNotFoundException {
//		CurvePoint curveDb = curvePointRepository.getById(curvePoint.getId());
		Integer curveDb = curvePoint.getId();
		if(curveDb == null) {
			CurvePoint curveNew = new CurvePoint();
			curveNew.setCurve(curvePoint.getCurve());
			curveNew.setTerm(curvePoint.getTerm());
			curveNew.setValue(curvePoint.getValue());
			curvePointRepository.save(curveNew);
			logger.info("CurvePoint saved");
			return curveNew;
		}else {
			logger.error("CurvePoint not be saved");
			throw new CurvePointNotFoundException("CurvePoint not saved");	
		}
	}
	
	/**
	 * 
	 * @param curvePoint
	 * @throws CurvePointNotFoundException
	 */
	public void deleteCurvePoint(CurvePoint curvePoint) throws CurvePointNotFoundException{
		CurvePoint curveExiste = curvePointRepository.findById(curvePoint.getId()).orElse(null);
		if(curveExiste != null) {
			curvePointRepository.delete(curvePoint);
			logger.info("CurvePoint deleted successfully");
		}else {
			logger.error("CurvePoint not delete because entity not found");
			throw new CurvePointNotFoundException("CurvePoint not deleted because entity not found");
		}
	}
	
	/**
	 * 
	 * @param curvePoint
	 * @return
	 * @throws CurvePointNotFoundException
	 */
	public CurvePoint updateCurvePoint(CurvePoint curvePoint) throws CurvePointNotFoundException {
//		CurvePoint curve = curvePointRepository.findById(curvePoint.getId()).orElse(null);
		if(curvePoint.getId() != null) {
			curvePoint.setCurve(curvePoint.getCurve());
			curvePoint.setTerm(curvePoint.getTerm());
			curvePoint.setValue(curvePoint.getValue());
			curvePointRepository.save(curvePoint);
			logger.info("CurvePoint updated successfully");
			return curvePoint;
		}else {
			logger.error("CurvePoint not updated because entity not found");
			throw new CurvePointNotFoundException("CurvePoint not updated because entity not found");
		}
	}
			

	/**
	 * 
	 * @param id
	 * @return
	 */
	public CurvePoint getCurvePointById(Integer id) {
		return curvePointRepository.getById(id);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<CurvePoint> getAllCurvePoint(){
		return curvePointRepository.findAll();
	}
}

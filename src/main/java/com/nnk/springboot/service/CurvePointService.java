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
	 * This method allows adding a new curvePoint if not exist in the data base
	 * @param curvePoint is the object who to will be saved
	 * @return curvePoint added in the data base
	 * @throws CurvePointNotFoundException is called when curvePoint not found
	 */
	public CurvePoint saveCurvePoint(CurvePoint curvePoint) throws CurvePointNotFoundException {
		Integer curveDb = curvePoint.getId();
		if(curveDb == null) {
			curvePointRepository.save(curvePoint);
			logger.info("CurvePoint saved");
			return curvePoint;
		}else {
			logger.error("CurvePoint not be saved");
			throw new CurvePointNotFoundException("CurvePoint not saved");	
		}
	}
	
	/**
	 * This method allows to delete curvPoint if exist in the data base
	 * @param curvPoint who will be deleted
	 * @throws CurvePointNotFoundException is called when curvePoint not found
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
	 * This method allows to update object if exist
	 * @param curvePoint is the object who to will be updated
	 * @return curvePoint updated 
	 * @throws CurvePointNotFoundException is called when curvePoint not found
	 */
	public CurvePoint updateCurvePoint(CurvePoint curvePoint) throws CurvePointNotFoundException {
		CurvePoint curve = curvePointRepository.findById(curvePoint.getId()).orElse(null);
		if(curve != null) {
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
	  * this method allows to fetch CurvePoint by reference id if it exist in the data base
	 * @param id use to fetch CurvePoint saved in the data base
	 * @return CurvePoint found by id if exist
	 */
	public CurvePoint getCurvePointById(Integer id) {
		CurvePoint curve = curvePointRepository.findById(id).orElse(null);
		return curve;
	}
	
	/**
	  * This method allows to fetch all CurvePoint saved in the data base
	 * @return List of CurvePoint
	 */
	public List<CurvePoint> getAllCurvePoint(){
		return curvePointRepository.findAll();
	}
}

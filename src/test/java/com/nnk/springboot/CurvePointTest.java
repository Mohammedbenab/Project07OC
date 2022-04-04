package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exception.CurvePointNotFoundException;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;

@SpringBootTest
public class CurvePointTest {
	
	// Revoir les fonction de test avec les exception
	// Utiliser les exceptions class créées
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	@Autowired
	private CurvePointService curvePointService;
	
	public CurvePoint curvePoint;
	
	@AfterEach
	void clean() throws CurvePointNotFoundException {
		this.curvePointRepository.deleteAll();
	}

	@BeforeEach
	void setUp() throws Exception {
		curvePoint = new CurvePoint("1", 5.0, 6.0);
		curvePointService.saveCurvePoint(curvePoint);
	}

	@Test
	void saveCurvePointWhenIsNotExistInDB() throws CurvePointNotFoundException{
		CurvePoint curvePointFound = curvePointRepository.findAll().get(0);
		assertEquals(1, curvePointRepository.findAll().size());
		assertEquals("1", curvePointFound.getCurve());
	}
//
	@Test
	void saveCurvePointWhenIstExist() throws CurvePointNotFoundException {
		CurvePoint curveDb = curvePointRepository.findAll().get(0);
		assertThrows(CurvePointNotFoundException.class, ()->{
			curvePointService.saveCurvePoint(curveDb);
		});
	}
//
	@Test
	void updateCurvePoint() throws Exception {
		CurvePoint curvePoint = curvePointRepository.findAll().get(0);
		double curveValuedBefore = curvePoint.getValue();
		curvePoint.setValue(3.0);
		curvePointService.updateCurvePoint(curvePoint);
		double curveValueAfter = curvePointRepository.findAll().get(0).getValue();
		assertEquals(1, curvePointRepository.findAll().size());
		assertNotNull(curvePoint);
		assertEquals(6.0, curveValuedBefore);
		assertEquals(3.0, curveValueAfter);
	}
//
	@Test
	void deleteCurvePoint() throws Exception {
		CurvePoint curveExiste = curvePointRepository.findAll().get(0);
		int beforeDelete = curvePointRepository.findAll().size();
		curvePointService.deleteCurvePoint(curveExiste);
		int afterDelete = curvePointRepository.findAll().size();
		assertEquals(1, beforeDelete);
		assertEquals(0, afterDelete);
	}

	@Test
	void getCurvePointList() throws Exception {
		CurvePoint curve2 = new CurvePoint("2", 2.0, 5.0);
		curvePointService.saveCurvePoint(curve2);
		List<CurvePoint> list = curvePointService.getAllCurvePoint();
		assertNotNull(list);
		assertEquals(2, list.size());
	}

	@Test
	void getCurvePointById() throws Exception {
		CurvePoint curveDb = curvePointRepository.findAll().get(0);
		CurvePoint curveById = curvePointService.getCurvePointById(curveDb.getId());
		assertEquals(1, curvePointRepository.findAll().size());
		assertNotNull(curveById);
	}

}

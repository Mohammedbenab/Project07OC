package com.nnk.springboot.unitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exception.CurvePointNotFoundException;
import com.nnk.springboot.repository.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;

@SpringBootTest
public class CurvePointServiceUnitTest {
	
	@MockBean
	CurvePointRepository curvePointRepository;
	
	@Autowired
	CurvePointService curvePointService;
	
	CurvePoint curvePoint;
	
	@BeforeEach
	void setUp() {
		curvePoint = new CurvePoint("cuvePoint", 3.0, 4.0);
	}
	
	@Test
	void save() throws CurvePointNotFoundException {
		Mockito.when(curvePointRepository.save(ArgumentMatchers.any(CurvePoint.class))).thenReturn(curvePoint);
		CurvePoint curveCreated = curvePointService.saveCurvePoint(curvePoint);
		assertEquals(curvePoint.getCurve(), curveCreated.getCurve());
		verify(curvePointRepository, Mockito.times(1)).save(curvePoint);
	}
	
	@Test
	void update() throws CurvePointNotFoundException {
		curvePoint.setId(1);
		Mockito.when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
		curvePoint.setValue(44.0);
		curvePointService.updateCurvePoint(curvePoint);
		verify(curvePointRepository, Mockito.times(1)).save(curvePoint);
		verify(curvePointRepository, Mockito.times(1)).findById(1);
	}
	
	@Test
	void delete() throws CurvePointNotFoundException {
		curvePoint.setId(1);
		Mockito.when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
		curvePointService.deleteCurvePoint(curvePoint);
		verify(curvePointRepository, Mockito.times(1)).delete(curvePoint);
	}
	
	@Test
	void findById() {
		 Mockito.when(curvePointRepository.findById(1)).thenReturn(java.util.Optional.of(curvePoint));
		 curvePointService.getCurvePointById(1);
	        verify(curvePointRepository, Mockito.times(1)).findById(1);
	        Assertions.assertEquals(curvePoint, curvePointService.getCurvePointById(1));
	}
	
	@Test
	void findAll() {
		curvePoint.setId(1);
    	List<CurvePoint> list = new ArrayList<>();
    	list.add(curvePoint);
    	Mockito.when(curvePointRepository.findAll()).thenReturn(list);
        List<CurvePoint> expected =  curvePointService.getAllCurvePoint();
        assertEquals(list,expected);
        verify(curvePointRepository, Mockito.times(1)).findAll();
		
	}
}

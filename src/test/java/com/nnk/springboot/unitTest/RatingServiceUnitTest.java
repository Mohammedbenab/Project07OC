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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repository.RatingRepository;
import com.nnk.springboot.service.RatingService;

@SpringBootTest
public class RatingServiceUnitTest {
	
	@MockBean
	RatingRepository ratingRepository;
	
	@Autowired
	RatingService ratingService;
	
	Rating rating;
	
	@BeforeEach
	void setUp() {
		rating = new Rating("moody","sand","fich",3);
	}
	
	@Test
	void save() throws Exception {
		Mockito.when(ratingRepository.save(ArgumentMatchers.any(Rating.class))).thenReturn(rating);
		ratingService.add(rating);
		verify(ratingRepository,Mockito.times(1)).save(rating);
	}
	
	@Test
	void update() throws Exception {
		rating.setId(1);
		Mockito.when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
		rating.setMoodysRating("moody change");
		ratingService.update(rating);
		verify(ratingRepository, Mockito.times(1)).save(rating);
		verify(ratingRepository, Mockito.times(1)).findById(1);
	}
	
	@Test
	void delete() {
		rating.setId(1);
		Mockito.when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
		ratingService.delete(rating);
		verify(ratingRepository, Mockito.times(1)).delete(rating);
	}
	
	@Test
	void findById() throws Exception {
		 Mockito.when(ratingRepository.findById(1)).thenReturn(java.util.Optional.of(rating));
		 ratingService.getById(1);
	        verify(ratingRepository, Mockito.times(1)).findById(1);
	        Assertions.assertEquals(rating, ratingService.getById(1));
	}
	
	@Test
	void findAll() {
		rating.setId(1);
    	List<Rating> list = new ArrayList<>();
    	list.add(rating);
    	Mockito.when(ratingRepository.findAll()).thenReturn(list);
        List<Rating> expected =  ratingService.getList();
        assertEquals(list,expected);
        verify(ratingRepository, Mockito.times(1)).findAll();
		
	}
}

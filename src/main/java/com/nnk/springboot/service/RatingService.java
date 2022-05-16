package com.nnk.springboot.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repository.RatingRepository;

@Service
public class RatingService {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(RatingService.class);

	
	@Autowired
	private RatingRepository ratingRepository;
	
	/**
	 * 
	 * @param rating
	 * @return Rating created
	 * @throws Exception
	 */
	public Rating add(Rating rating) throws Exception {
		if(rating.getId() == null) {
			logger.info("Rating saved successfully");
			return ratingRepository.save(rating);
		}else {
			logger.error("Rating not saved");
			throw new Exception();
		}
	}
	
	/**
	 * 
	 * @param rating
	 * @return Rating updated
	 * @throws Exception
	 */
	public Rating update(Rating rating) throws Exception {
		Rating ratingDB = ratingRepository.findById(rating.getId()).orElse(null);
		if(ratingDB.getId() != null) {
			ratingDB.setFitchRating(rating.getFitchRating());
			ratingDB.setMoodysRating(rating.getMoodysRating());
			ratingDB.setOrderNumber(rating.getOrderNumber());
			ratingDB.setSandPRating(rating.getSandPRating());
			logger.info("Rating update successfully");
			return ratingRepository.save(ratingDB);
		}else {
			logger.error("Rating not update");
			throw new Exception();
		}
		
	}
	
	/**
	 * 
	 * @param rating
	 */
	public void delete(Rating rating) {
		ratingRepository.delete(rating);
		logger.info("Rating delete successfully");
	}
	
	/**
	 * 
	 * @return List of Rating
	 */
	public List<Rating> getList() {
		List<Rating> list = ratingRepository.findAll();
		return list;
		
		
	}
	
	/**
	 * 
	 * @param id
	 * @return Rating
	 * @throws Exception
	 */
	public Rating getById(Integer id) throws Exception {
		Rating verify = ratingRepository.findById(id).orElse(null);
		if(verify != null) {
			return verify;
		}else {
			logger.error("Rating not found");
			throw new Exception();
		}
	}

}

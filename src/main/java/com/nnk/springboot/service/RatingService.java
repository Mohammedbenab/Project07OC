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
	 * This method allows adding a new rating if not exist in the data base
	 * @param rating is the object who to will be saved
	 * @return rating added in the data base
	 * @throws Exception if the object rating exist in the data base
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
	 * This method allows to update object if exist
	 * @param rating is the object who to will be updated
	 * @return rating updated 
	 * @throws Exception if the object rating exist in the data base
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
	 * This method allows to delete rating if exist in the data base
	 * @param rating who will be deleted
	 * @throws Exception if the object rating exist in the data base
	 */
	public void delete(Rating rating) {
		ratingRepository.delete(rating);
		logger.info("Rating delete successfully");
	}
	
	/**
	 * This method allows to fetch all rating saved in the data base
	 * @return List of rating
	 */
	public List<Rating> getList() {
		List<Rating> list = ratingRepository.findAll();
		return list;
		
		
	}
	
	/**
	 * this method allows to fetch rating by reference id if it exist in the data base
	 * @param id use to fetch rating saved in the data base
	 * @return rating found by id if exist
	 * @throws Exception if the object rating exist in the data base
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

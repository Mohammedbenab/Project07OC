package com.nnk.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer>{

}

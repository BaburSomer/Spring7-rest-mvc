package com.babsom.spring7restmvc.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.babsom.spring7restmvc.entity.Beer;
import com.babsom.spring7restmvc.model.BeerStyle;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
	
	List<Beer> findAllByNameIsLikeIgnoreCase(String beerName);
	
	List<Beer> findAllByStyle(BeerStyle style);
	
	List<Beer> findAllByNameIsLikeIgnoreCaseAndStyle(String beerName, BeerStyle style);

}

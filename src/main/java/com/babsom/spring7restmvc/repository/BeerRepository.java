package com.babsom.spring7restmvc.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.babsom.spring7restmvc.entity.Beer;
import com.babsom.spring7restmvc.model.BeerStyle;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
	
	Page<Beer> findAllByNameIsLikeIgnoreCase(String beerName, Pageable pageable);
	
	Page<Beer> findAllByStyle(BeerStyle style, Pageable pageable);
	
	Page<Beer> findAllByNameIsLikeIgnoreCaseAndStyle(String beerName, BeerStyle style, Pageable pageable);

}

package com.babsom.spring7restmvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.babsom.spring7restmvc.model.BeerDTO;

public interface BeerService {

	Optional<BeerDTO> getBeerByOid(UUID oid);

	List<BeerDTO> listBeers();

	BeerDTO insert(BeerDTO beer);

	void update(UUID beerId, BeerDTO beer);

	void deleteById(UUID beerId);

	void patchById(UUID beerId, BeerDTO beer);

}
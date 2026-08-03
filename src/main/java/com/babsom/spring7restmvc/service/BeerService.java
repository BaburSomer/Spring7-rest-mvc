package com.babsom.spring7restmvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.babsom.spring7restmvc.model.BeerDTO;
import com.babsom.spring7restmvc.model.BeerStyle;

public interface BeerService {

	Optional<BeerDTO> getBeerByOid(UUID oid);

	List<BeerDTO> listBeers(String beerName, BeerStyle style, Boolean showInventory);

	BeerDTO insert(BeerDTO beer);

	Optional<BeerDTO> update(UUID beerId, BeerDTO beer);

	Boolean deleteById(UUID beerId);

	Optional<BeerDTO> patchById(UUID beerId, BeerDTO beer);

}
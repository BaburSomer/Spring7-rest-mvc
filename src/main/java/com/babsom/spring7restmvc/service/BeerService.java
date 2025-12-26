package com.babsom.spring7restmvc.service;

import java.util.List;
import java.util.UUID;

import com.babsom.spring7restmvc.model.Beer;

public interface BeerService {

	Beer getBeerByOid(UUID oid);

	List<Beer> listBeers();

	Beer insert(Beer beer);

	void update(UUID beerId, Beer beer);

	void deleteById(UUID beerId);

	void patchById(UUID beerId, Beer beer);

}
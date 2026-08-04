package com.babsom.spring7restmvc.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.babsom.spring7restmvc.model.BeerDTO;
import com.babsom.spring7restmvc.model.BeerStyle;

public interface BeerService {
	static final int DEFAULT_PAGE_NUMBER = 0;
	static final int DEFAULT_PAGE_SIZE = 25;

	Optional<BeerDTO> getBeerByOid(UUID oid);

	Page<BeerDTO> listBeers(String beerName, BeerStyle style, Boolean showInventory, Integer pageNumber, Integer pageSize);

	BeerDTO insert(BeerDTO beer);

	Optional<BeerDTO> update(UUID beerId, BeerDTO beer);

	Boolean deleteById(UUID beerId);

	Optional<BeerDTO> patchById(UUID beerId, BeerDTO beer);
	
	default public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
		int queryPageNumber;
		int queryPageSize;
		
		if (pageNumber != null && pageNumber > 0) {
			queryPageNumber = pageNumber - 1;  // çünkü indeksler 0'dan başlar ama ön yüzde 1'den başlıyoruz
		}
		else {
			queryPageNumber = DEFAULT_PAGE_NUMBER;
		}
		
		if (pageSize == null) {
			queryPageSize = DEFAULT_PAGE_SIZE;
		}
		else {
			if (pageSize > 1000) {
				pageSize = 1000;
			}
			queryPageSize = pageSize;
		}

		return PageRequest.of(queryPageNumber, queryPageSize);
	}


}
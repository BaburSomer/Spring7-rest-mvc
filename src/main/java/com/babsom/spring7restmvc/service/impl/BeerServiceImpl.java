package com.babsom.spring7restmvc.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.babsom.spring7restmvc.model.Beer;
import com.babsom.spring7restmvc.model.BeerStyle;
import com.babsom.spring7restmvc.service.BeerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

	@Override
	public Beer getBeerByOid(UUID oid) {
		log.debug("Get Beer by oid is called in service");
		return Beer.builder()
				.oid(oid)
				.name("Efes")
				.version(1)
				.style(BeerStyle.PILSNER)
				.upc("211654")
				.price(new BigDecimal("70.99"))
				.created(LocalDateTime.now())
				.build();
	}

}

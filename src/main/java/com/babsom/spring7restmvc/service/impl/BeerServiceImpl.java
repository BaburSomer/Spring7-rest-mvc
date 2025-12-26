package com.babsom.spring7restmvc.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.babsom.spring7restmvc.model.Beer;
import com.babsom.spring7restmvc.model.BeerStyle;
import com.babsom.spring7restmvc.service.BeerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

	private Map<UUID, Beer>      beers;

	public BeerServiceImpl() {
		this.beers = new HashMap<>();

		Beer beer = Beer.builder().oid(UUID.randomUUID()).name("Efes Pilsen").version(1).style(BeerStyle.PILSNER).upc("211654")
				.price(new BigDecimal("70.99")).quantityOnHand(122).created(LocalDateTime.now()).build();
		this.beers.put(beer.getOid(), beer);

		beer = Beer.builder().oid(UUID.randomUUID()).name("Bomonti").version(1).style(BeerStyle.WHEAT).upc("568994").price(new BigDecimal("75.49"))
				.quantityOnHand(500).created(LocalDateTime.now()).build();
		this.beers.put(beer.getOid(), beer);

		beer = Beer.builder().oid(UUID.randomUUID()).name("Zıkkım").version(1).style(BeerStyle.IPA).upc("123789").price(new BigDecimal("58.44"))
				.quantityOnHand(25).created(LocalDateTime.now()).build();
		this.beers.put(beer.getOid(), beer);
	}

	@Override
	public List<Beer> listBeers() {
		return new ArrayList<>(beers.values());
	}

	@Override
	public Beer getBeerByOid(UUID oid) {
		log.debug("Get Beer by oid is called in service");
		return beers.get(oid);
	}

	@Override
	public Beer insert(Beer beer) {
		beer.setOid(UUID.randomUUID());
		beer.setCreated(LocalDateTime.now());
		this.beers.put(beer.getOid(), beer);
		return beer;
	}

	@Override
	public void update(UUID beerId, Beer beer) {
		Beer b = beers.get(beerId);
		b.setName(beer.getName());
		b.setPrice(beer.getPrice());
		b.setQuantityOnHand(beer.getQuantityOnHand());
		b.setStyle(beer.getStyle());
		b.setUpc(beer.getUpc());
		b.setUpdated(LocalDateTime.now());
		b.setVersion(beer.getVersion());
		this.beers.put(beer.getOid(), beer);
	}

	@Override
	public void deleteById(UUID beerId) {
		this.beers.remove(beerId);
	}

	@Override
	public void patchById(UUID beerId, Beer beer) {
		Beer b = beers.get(beerId);
		
		if (StringUtils.hasText(beer.getName())) {
			b.setName(beer.getName());
		}
		
		if(beer.getPrice() != null) {
			b.setPrice(beer.getPrice());
		}
		
		if(beer.getQuantityOnHand() != null) {
			b.setQuantityOnHand(beer.getQuantityOnHand());
		}
		
		if(beer.getStyle() != null) {
			b.setStyle(beer.getStyle());
		}
		
		if (StringUtils.hasText(beer.getUpc())) {
			b.setUpc(beer.getUpc());
		}
		
		b.setUpdated(LocalDateTime.now());
		
		if(beer.getVersion() != null) {
			b.setVersion(beer.getVersion());
		}
	}
}
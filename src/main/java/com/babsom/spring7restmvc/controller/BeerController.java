package com.babsom.spring7restmvc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.babsom.spring7restmvc.model.Beer;
import com.babsom.spring7restmvc.service.BeerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {
	static final String BEER_PATH = "/api/v1/beer";
	static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

	private final BeerService service;
	
	@GetMapping(BEER_PATH)
	public List<Beer> listBeers() {
		return service.listBeers();
	}
	
	@GetMapping(BEER_PATH_ID)
	public Beer getByOid(@PathVariable("beerId") UUID beerId) { // eğer hem requestmapping'deki isim hem de parametre ismi aynı olursa aslında gerek yokmuş buna. 
		log.debug("Beer saerching by id: " + beerId);				// denedim çalışmadı
		return service.getBeerByOid(beerId);
	}
	
	@PostMapping(BEER_PATH)
	public ResponseEntity<HttpStatus> handlePost(@RequestBody Beer beer) {
		Beer newBeer = service.insert(beer);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", BEER_PATH + "/" + newBeer.getOid().toString());
		
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping(BEER_PATH_ID)
	public ResponseEntity<HttpStatus> updateById(@PathVariable("beerId")UUID beerId,  @RequestBody Beer beer) {
		service.update(beerId, beer);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(BEER_PATH_ID)
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("beerId")UUID beerId) {
		service.deleteById(beerId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping(BEER_PATH_ID)
	public ResponseEntity<HttpStatus> patchById(@PathVariable("beerId")UUID beerId,  @RequestBody Beer beer) {
		service.patchById(beerId, beer);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

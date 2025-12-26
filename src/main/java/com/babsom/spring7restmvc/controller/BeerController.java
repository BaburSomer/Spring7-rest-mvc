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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.babsom.spring7restmvc.model.Beer;
import com.babsom.spring7restmvc.service.BeerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
	private final BeerService service;
	
	@GetMapping
	public List<Beer> listBeers() {
		return service.listBeers();
	}
	
	@GetMapping("/{beerId}")
	public Beer getByOid(@PathVariable("beerId") UUID beerId) { // eğer hem requestmapping'deki isim hem de parametre ismi aynı olursa aslında gerek yokmuş buna. 
		log.debug("Beer saerching by id: " + beerId);				// denedim çalışmadı
		return service.getBeerByOid(beerId);
	}
	
	@PostMapping
	public ResponseEntity<HttpStatus> handlePost(@RequestBody Beer beer) {
		Beer newBeer = service.insert(beer);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/beer/" + newBeer.getOid().toString());
		
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/{beerId}")
	public ResponseEntity<HttpStatus> updateById(@PathVariable("beerId")UUID beerId,  @RequestBody Beer beer) {
		service.update(beerId, beer);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{beerId}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("beerId")UUID beerId) {
		service.deleteById(beerId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/{beerId}")
	public ResponseEntity<HttpStatus> patchById(@PathVariable("beerId")UUID beerId,  @RequestBody Beer beer) {
		service.patchById(beerId, beer);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

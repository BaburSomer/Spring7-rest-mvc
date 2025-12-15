package com.babsom.spring7restmvc.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;

import com.babsom.spring7restmvc.model.Beer;
import com.babsom.spring7restmvc.service.BeerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {
	private final BeerService service;
	
	public Beer getByOid(UUID oid) {
		log.debug("Bear saerching by id");
		return service.getBeerByOid(oid);
	}
}

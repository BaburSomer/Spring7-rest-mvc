package com.babsom.spring7restmvc.service;

import java.util.UUID;

import com.babsom.spring7restmvc.model.Beer;

public interface BeerService {

	Beer getBeerByOid(UUID oid);
}

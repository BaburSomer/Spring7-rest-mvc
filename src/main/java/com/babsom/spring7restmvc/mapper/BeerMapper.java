package com.babsom.spring7restmvc.mapper;

import org.mapstruct.Mapper;

import com.babsom.spring7restmvc.entity.Beer;
import com.babsom.spring7restmvc.model.BeerDTO;

@Mapper
public interface BeerMapper {
	
	Beer beerDTO2Beer(BeerDTO dto);
	
	BeerDTO beer2BeerDTO(Beer entity);
}

package com.babsom.spring7restmvc.mapper;

import org.mapstruct.Mapper;

import com.babsom.spring7restmvc.entity.Beer;
import com.babsom.spring7restmvc.model.BeerDTO;

@Mapper
public interface BeerMapper {
 
	Beer dtoToEntity(BeerDTO dto);

	BeerDTO entityToDto(Beer entity);
}

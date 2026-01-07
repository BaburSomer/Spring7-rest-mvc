package com.babsom.spring7restmvc.mapper;

import org.mapstruct.Mapper;

import com.babsom.spring7restmvc.entity.Customer;
import com.babsom.spring7restmvc.model.CustomerDTO;

@Mapper
public interface CustomerMapper {

	Customer dtoToEntity(CustomerDTO dto);
	
	CustomerDTO entityToDto(Customer entity);

}

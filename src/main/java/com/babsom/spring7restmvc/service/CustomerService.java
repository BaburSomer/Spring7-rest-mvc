package com.babsom.spring7restmvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.babsom.spring7restmvc.model.CustomerDTO;

public interface CustomerService {

	Optional<CustomerDTO> getCustomerByOid(UUID oid);

	Optional<CustomerDTO> getCustomerByFirstName(String firstName);

	List<CustomerDTO> listCustomers();

	CustomerDTO insert(CustomerDTO customer);

	Optional<CustomerDTO> update(UUID customerId, CustomerDTO customer);

	Boolean deleteByOid(UUID customerId);

	Optional<CustomerDTO> patchById(UUID customerId, CustomerDTO customer);
}
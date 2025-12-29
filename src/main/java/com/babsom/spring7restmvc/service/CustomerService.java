package com.babsom.spring7restmvc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.babsom.spring7restmvc.model.CustomerDTO;

public interface CustomerService {

	Optional<CustomerDTO> getCustomerByOid(UUID oid);

	Optional<CustomerDTO> getCustomerByFirstName(String firstName);

	List<CustomerDTO> listCustomers();

	CustomerDTO create(CustomerDTO customer);

	void update(UUID customerId, CustomerDTO customer);

	void deleteByOid(UUID customerId);

	void patchById(UUID customerId, CustomerDTO customer);
}
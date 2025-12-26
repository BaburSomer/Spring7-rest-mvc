package com.babsom.spring7restmvc.service;

import java.util.List;
import java.util.UUID;

import com.babsom.spring7restmvc.model.Customer;

public interface CustomerService {

	Customer getCustomerByOid(UUID oid);

	Customer getCustomerByFirstName(String firstName);

	List<Customer> listCustomers();

	Customer create(Customer customer);

	void update(UUID customerId, Customer customer);

	void deleteByOid(UUID customerId);

	void patchById(UUID customerId, Customer customer);
}
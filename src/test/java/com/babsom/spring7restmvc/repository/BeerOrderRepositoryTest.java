package com.babsom.spring7restmvc.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.babsom.spring7restmvc.entity.Beer;
import com.babsom.spring7restmvc.entity.Customer;

@SpringBootTest
class BeerOrderRepositoryTest {
	
	@Autowired
	BeerOrderRepository orderRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	BeerRepository beerRepository;

	Customer testCustomer;
	Beer testBeer;
	
	
	@BeforeEach
	void setUp() throws Exception {
		testCustomer = customerRepository.findAll().get(0);
		testBeer = beerRepository.findAll().get(3);
	}

	@Test
	void testBeerOrders() {
		System.out.println(orderRepository.count());
		System.out.println(customerRepository.count());
		System.out.println(beerRepository.count());
		System.out.println(testCustomer.getFirstName());
		System.out.println(testBeer.getName());
	}

}

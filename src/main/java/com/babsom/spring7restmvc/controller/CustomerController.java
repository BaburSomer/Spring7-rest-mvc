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

import com.babsom.spring7restmvc.model.Customer;
import com.babsom.spring7restmvc.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

	private final CustomerService service;

	@GetMapping
	public List<Customer> listCustomers() {
		return service.listCustomers();
	}

	@GetMapping("{customerId}")
	public Customer getByOid(@PathVariable("customerId") UUID customerId) { 
		log.debug("Customer saerching by id: " + customerId); 
		return service.getCustomerByOid(customerId);
	}

	@GetMapping("name/{firstName}")
	public Customer getCustomerByFirstName(@PathVariable("firstName") String firstName) {
		log.debug("Customer searching by firstName: " + firstName);
		return service.getCustomerByFirstName(firstName);
	}
	
	@PostMapping
	public ResponseEntity<HttpStatus> handlePost(@RequestBody Customer customer) {
		Customer newCustomer = service.create(customer);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/customer/" + newCustomer.getOid().toString());
		
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@PutMapping("/{customerId}")
	public ResponseEntity<HttpStatus> updateById(@PathVariable("customerId")UUID customerId,  @RequestBody Customer customer) {
		service.update(customerId, customer);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{customerId}")
	public ResponseEntity<HttpStatus>  deleteById(@PathVariable("customerId") UUID customerId) { 
		service.deleteByOid(customerId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/{customerId}")
	public ResponseEntity<HttpStatus> patchById(@PathVariable("customerId")UUID customerId,  @RequestBody Customer customer) {
		service.patchById(customerId, customer);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}

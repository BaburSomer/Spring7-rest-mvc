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
import org.springframework.web.bind.annotation.RestController;

import com.babsom.spring7restmvc.model.CustomerDTO;
import com.babsom.spring7restmvc.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomerController {

	public static final String CUSTOMER_PATH    = "/api/v1/customer";
	public static final String CUSTOMER_PATH_ID = "/api/v1/customer" + "/{customerId}";
	public static final String CUSTOMER_PATH_FN = "/api/v1/customer" + "/name/{firstName}";

	private final CustomerService service;

	@GetMapping(CUSTOMER_PATH)
	public List<CustomerDTO> listCustomers() {
		return service.listCustomers();
	}

	@GetMapping(CUSTOMER_PATH_ID)
	public CustomerDTO getByOid(@PathVariable("customerId") UUID customerId) {
		log.debug("CustomerDTO saerching by id: " + customerId);
		return service.getCustomerByOid(customerId).orElseThrow(NotFoundException::new);
	}

	@GetMapping(CUSTOMER_PATH_FN)
	public CustomerDTO getCustomerByFirstName(@PathVariable("firstName") String firstName) {
		log.debug("CustomerDTO searching by firstName: " + firstName);
		return service.getCustomerByFirstName(firstName).orElseThrow(NotFoundException::new);
	}

	@PostMapping(CUSTOMER_PATH)
	public ResponseEntity<HttpStatus> handlePost(@RequestBody CustomerDTO customer) {
		CustomerDTO newCustomer = service.insert(customer);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", CustomerController.CUSTOMER_PATH + "/" + newCustomer.getOid().toString());

		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@PutMapping(CUSTOMER_PATH_ID)
	public ResponseEntity<HttpStatus> updateById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customer) {
		if (service.update(customerId, customer).isEmpty()) {
			throw new NotFoundException("Customer with id <<< " + customerId + " >>> not found");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(CUSTOMER_PATH_ID)
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("customerId") UUID customerId) {
		if (!service.deleteByOid(customerId)) {
			throw new NotFoundException("Customer with id <<< " + customerId + " >>> not found");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PatchMapping(CUSTOMER_PATH_ID)
	public ResponseEntity<HttpStatus> patchById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customer) {
		if (service.patchById(customerId, customer).isEmpty()) {
			throw new NotFoundException("Customer with id <<< " + customerId + " >>> not found");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}

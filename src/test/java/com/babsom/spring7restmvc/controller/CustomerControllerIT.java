package com.babsom.spring7restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.babsom.spring7restmvc.entity.Customer;
import com.babsom.spring7restmvc.mapper.CustomerMapper;
import com.babsom.spring7restmvc.model.CustomerDTO;
import com.babsom.spring7restmvc.repository.CustomerRepository;

@SpringBootTest
class CustomerControllerIT {

	@Autowired
	CustomerController controller;
	@Autowired
	CustomerRepository repository;
	@Autowired
	CustomerMapper     mapper;

	@Test
	void testDeleteByNotExistingId() {
		assertThrows(NotFoundException.class, () -> {
			controller.deleteById(UUID.randomUUID());
		});
	}
	
	@Rollback
	@Transactional
	@Test
	void testDeleteById() {
		Customer entity = repository.findAll().get(0);

		ResponseEntity<HttpStatus> response = controller.deleteById(entity.getOid());
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()));
		
		assertThat(repository.findById(entity.getOid()).isEmpty());
	}
	
	@Test
	void testUpdateCustomerByNotExistingId() {
		assertThrows(NotFoundException.class, () -> {
			controller.updateById(UUID.randomUUID(), CustomerDTO.builder().build());
		});
	}
	
	@Rollback
	@Transactional
	@Test
	void testUpdateCustomer() {
		Customer entity = repository.findAll().get(0);
		CustomerDTO dto = mapper.entityToDto(entity);
		dto.setOid(null);
		dto.setVersion(null);
		final String firstName = "UPDATED";
		dto.setFirstName(firstName);
		
		ResponseEntity<HttpStatus> response = controller.updateById(entity.getOid(), dto);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()));
		
		Customer customer = repository.findById(entity.getOid()).get();
		assertThat(customer.getFirstName()).isEqualTo(firstName);
	}

	@Rollback
	@Transactional
	@Test
	void testCreateNewCustomer() {
		CustomerDTO                    dto      = CustomerDTO.builder().firstName("FIRST").lastName("LAST").build();
		ResponseEntity<HttpStatus> response = controller.handlePost(dto);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
		assertThat(response.getHeaders().getLocation()).isNotNull();

		String[] locationPath = response.getHeaders().getLocation().getPath().split("/");
		UUID     uuid         = UUID.fromString(locationPath[4]);

		Customer entity = repository.findById(uuid).get();
		assertThat(entity).isNotNull();
	}	

	@Test
	void testListCustomers() {
		List<CustomerDTO> customers = controller.listCustomers();
		
		assertThat(customers.size()).isEqualTo(4);
	}
	
	@Test
	void testGetCustomerById() {
		Customer customer = repository.findAll().get(0);
		CustomerDTO customerDTO = controller.getByOid(customer.getOid());
		
		assertThat(customerDTO).isNotNull();
	}
	
	@Test
	void testGetCustomerByNonExistingId() {
		assertThrows(NotFoundException.class, () -> {
				controller.getByOid(UUID.randomUUID());
			});
	}
	
	@Rollback
	@Transactional
	@Test
	void testEmptyList() {
		repository.deleteAll();
		List<CustomerDTO> customers = controller.listCustomers();
		
		assertThat(customers.size()).isEqualTo(0);
	}
}
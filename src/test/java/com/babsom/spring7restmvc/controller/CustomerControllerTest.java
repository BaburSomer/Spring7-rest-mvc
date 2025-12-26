package com.babsom.spring7restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.babsom.spring7restmvc.model.Customer;
import com.babsom.spring7restmvc.service.CustomerService;
import com.babsom.spring7restmvc.service.impl.CustomerServiceImpl;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
//@ExtendWith(MockitoExtension.class) gerekli olabilirmiş SpringBoot 4 and Spirng 7'de. Bende çaıştı ama
class CustomerControllerTest {

	@Autowired
	MockMvc      mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@Captor
	ArgumentCaptor<UUID>     uuidCaptor;
	@Captor
	ArgumentCaptor<Customer> customerCaptor;

	@MockitoBean
	CustomerService service;

	CustomerServiceImpl serviceImpl;
	List<Customer>      customers;

	@BeforeEach
	void setUp() throws Exception {
		serviceImpl    = new CustomerServiceImpl();
		customers      = serviceImpl.listCustomers();
		uuidCaptor     = ArgumentCaptor.forClass(UUID.class);
		customerCaptor = ArgumentCaptor.forClass(Customer.class);
	}

	@Test
	void testPatch() throws Exception {
		Customer testObject = customers.get(0);

		Map<String, Object> customerMap = new HashMap<>();
		customerMap.put("firstName", "New Firstname");
		customerMap.put("lastName", "New Lastname");

		mockMvc.perform(patch(CustomerController.CUSTOMER_PATH + "/" + testObject.getOid()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customerMap))).andExpect(status().isNoContent());

		verify(service).patchById(uuidCaptor.capture(), customerCaptor.capture());
		assertThat(testObject.getOid()).isEqualTo(uuidCaptor.getValue());
		assertThat(customerMap.get("firstName")).isEqualTo(customerCaptor.getValue().getFirstName());
		assertThat(customerMap.get("lastName")).isEqualTo(customerCaptor.getValue().getLastName());
	}

	@Test
	void testDelete() throws Exception {
		Customer testObject = customers.get(0);

		mockMvc.perform(delete(CustomerController.CUSTOMER_PATH + "/" + testObject.getOid()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		verify(service).deleteByOid(uuidCaptor.capture());
		assertThat(testObject.getOid()).isEqualTo(uuidCaptor.getValue());
	}

	@Test
	void testUpdate() throws Exception {
		Customer testObject = customers.get(0);

		mockMvc.perform(put(CustomerController.CUSTOMER_PATH + "/" + testObject.getOid()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(testObject))).andExpect(status().isNoContent());

		verify(service).update(any(UUID.class), any(Customer.class));
	}

	@Test
	void testCreateNew() throws Exception {
		Customer testObject = customers.get(0).clone();
		testObject.setOid(null);
		testObject.setCreated(null);
		testObject.setUpdated(null);
		testObject.setFirstName(testObject.getFirstName() + " - CREATED");

		given(service.create(any(Customer.class))).willReturn(customers.get(1));
		mockMvc.perform(post(CustomerController.CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(testObject))).andExpect(status().isCreated()).andExpect(header().exists("Location"));
	}

	@Test
	void testListCustomers() throws Exception {
		given(service.listCustomers()).willReturn(customers);

		mockMvc.perform(get(CustomerController.CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", is(customers.size())));
	}

	@Test
	void testGetByOid() throws Exception {
		Customer testObject = customers.get(0);

		given(service.getCustomerByOid(testObject.getOid())).willReturn(testObject);

		mockMvc.perform(get(CustomerController.CUSTOMER_PATH + "/" + testObject.getOid()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.oid", is(testObject.getOid().toString()))).andExpect(jsonPath("$.firstName", is(testObject.getFirstName())));
	}

	@Test
	void testGetCustomerByFirstName() throws Exception {
		Customer testObject = customers.get(0);

		given(service.getCustomerByFirstName(testObject.getFirstName())).willReturn(testObject);

		mockMvc.perform(get(CustomerController.CUSTOMER_PATH + "/name/" + testObject.getFirstName()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.oid", is(testObject.getOid().toString()))).andExpect(jsonPath("$.firstName", is(testObject.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(testObject.getLastName())));
	}
}
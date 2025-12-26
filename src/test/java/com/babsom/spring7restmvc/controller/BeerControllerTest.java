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

import com.babsom.spring7restmvc.model.Beer;
import com.babsom.spring7restmvc.service.BeerService;
import com.babsom.spring7restmvc.service.impl.BeerServiceImpl;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(BeerController.class)
// @ExtendWith(MockitoExtension.class) gerekli olabilirmiş SpringBoot 4 and Spirng 7'de. Bende çaıştı ama
class BeerControllerTest {

	@Autowired
	MockMvc      mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@MockitoBean
	BeerService service;

	@Captor
	ArgumentCaptor<UUID> uuidCaptor;
	@Captor
	ArgumentCaptor<Beer> beerCaptor;

	BeerServiceImpl serviceImpl;
	List<Beer>      beers;

	@BeforeEach
	void setUp() {
		serviceImpl = new BeerServiceImpl();
		beers       = serviceImpl.listBeers();
		uuidCaptor  = ArgumentCaptor.forClass(UUID.class);
		beerCaptor  = ArgumentCaptor.forClass(Beer.class);
	}

	@Test
	void testPatch() throws Exception {
		Beer testObject = beers.get(0);

		Map<String, Object> beerMap = new HashMap<>();
		beerMap.put("name", "New Name");

		mockMvc.perform(patch(BeerController.BEER_PATH + "/" + testObject.getOid()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(beerMap))).andExpect(status().isNoContent());

		verify(service).patchById(uuidCaptor.capture(), beerCaptor.capture());
		assertThat(testObject.getOid()).isEqualTo(uuidCaptor.getValue());
		assertThat(beerMap.get("name")).isEqualTo(beerCaptor.getValue().getName());
	}

	@Test
	void testDelete() throws Exception {
		Beer testObject = beers.get(0);

		mockMvc.perform(delete(BeerController.BEER_PATH + "/" + testObject.getOid()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		verify(service).deleteById(uuidCaptor.capture());
		assertThat(testObject.getOid()).isEqualTo(uuidCaptor.getValue());
	}

	@Test
	void testUpdate() throws Exception {
		Beer testObject = beers.get(0);

		mockMvc.perform(put(BeerController.BEER_PATH + "/" + testObject.getOid()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(testObject))).andExpect(status().isNoContent());

		verify(service).update(any(UUID.class), any(Beer.class));
	}

	@Test
	void testCreateNewBeer() throws Exception { // tam doğru değil çünkü insert'e gitmedi.
		Beer testObject = beers.get(0).clone();

		System.out.println(objectMapper.writeValueAsString(testObject));
		testObject.setOid(null);
		testObject.setCreated(null);
		testObject.setUpdated(null);
		testObject.setName(testObject.getName() + " - CREATED");

		given(service.insert(any(Beer.class))).willReturn(beers.get(1));
//		given(service.insert(any(Beer.class))).willReturn(testObject);

		mockMvc.perform(post(BeerController.BEER_PATH).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(testObject))).andExpect(status().isCreated()).andExpect(header().exists("Location"))

//				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.length()", is(beers.size())))
		;
		System.out.println();
	}

	@Test
	void testListBeers() throws Exception {
		given(service.listBeers()).willReturn(beers);

		mockMvc.perform(get(BeerController.BEER_PATH).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.length()", is(beers.size())));
	}

	@Test
	void testGetBeerByOid() throws Exception {
		Beer testBeer = beers.get(0);

		given(service.getBeerByOid(testBeer.getOid())).willReturn(testBeer);

		mockMvc.perform(get(BeerController.BEER_PATH + "/" + testBeer.getOid()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.oid", is(testBeer.getOid().toString()))).andExpect(jsonPath("$.name", is(testBeer.getName())));
	}
}
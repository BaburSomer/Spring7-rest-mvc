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
import java.util.Optional;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.babsom.spring7restmvc.model.BeerDTO;
import com.babsom.spring7restmvc.service.BeerService;
import com.babsom.spring7restmvc.service.impl.BeerServiceImpl;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(BeerController.class)
// @ExtendWith(MockitoExtension.class) gerekli olabilirmiş SpringBoot 4 and Spirng 7'de. Bende çaıştı ama
class BeerControllerTest {

	@Autowired
	MockMvc      mockMvc;
	@Autowired
	ObjectMapper mapper;

	@MockitoBean
	BeerService service;

	@Captor
	ArgumentCaptor<UUID>    uuidCaptor;
	@Captor
	ArgumentCaptor<BeerDTO> beerCaptor;

	BeerServiceImpl serviceImpl;
	List<BeerDTO>   beers;

	@BeforeEach
	void setUp() {
		serviceImpl = new BeerServiceImpl();
		beers       = serviceImpl.listBeers();
		uuidCaptor  = ArgumentCaptor.forClass(UUID.class);
		beerCaptor  = ArgumentCaptor.forClass(BeerDTO.class);
	}

	@Test
	void testUpdateBeerWithBeerNameNull() throws Exception {
		BeerDTO dto = beers.get(1);
		dto.setName("");
		
		given(service.update(any(), any())).willReturn(Optional.of(dto));
		
		MvcResult mvcResult =  mockMvc.perform(put(BeerController.BEER_PATH_ID, dto.getOid()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.length()", is(1)))
		.andReturn();
		
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

	@Test
	void testCreateBeerWithBeerNameNull() throws Exception {
		BeerDTO dto = BeerDTO.builder().build();
		
		given(service.insert(any(BeerDTO.class))).willReturn(beers.get(1));
		
		MvcResult mvcResult =  mockMvc.perform(post(BeerController.BEER_PATH)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(dto)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.length()", is(6)))
				.andReturn();
		
		System.out.println(mvcResult.getResponse().getContentAsString());
	}

	@Test
	void testGetBeerByOidNotFound() throws Exception {

		given(service.getBeerByOid(any(UUID.class))).willReturn(Optional.empty()); // bu Optional ile çalışırken
//		given(service.getBeerByOid(any(UUID.class))).willThrow(NotFoundException.class); bu exception handler'ler ile çalışırken

		mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	void testPatch() throws Exception {
		BeerDTO testObject = beers.get(0);

		Map<String, Object> beerMap = new HashMap<>();
		beerMap.put("name", "New Name");
		beerMap.put("upc", testObject.getUpc());
		beerMap.put("price", testObject.getPrice());
		beerMap.put("style", testObject.getStyle());

		mockMvc.perform(patch(BeerController.BEER_PATH_ID, testObject.getOid()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(beerMap))).andExpect(status().isNoContent());

		verify(service).patchById(uuidCaptor.capture(), beerCaptor.capture());
		assertThat(testObject.getOid()).isEqualTo(uuidCaptor.getValue());
		assertThat(beerMap.get("name")).isEqualTo(beerCaptor.getValue().getName());
	}

	@Test
	void testDelete() throws Exception {
		BeerDTO dto = beers.get(0);

		given(service.deleteById(any())).willReturn(true);
		mockMvc.perform(delete(BeerController.BEER_PATH_ID, dto.getOid()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		verify(service).deleteById(uuidCaptor.capture());
		assertThat(dto.getOid()).isEqualTo(uuidCaptor.getValue());
	}

	@Test
	void testUpdate() throws Exception {
		BeerDTO dto = beers.get(0);

		given(service.update(any(), any())).willReturn(Optional.of(dto));
		mockMvc.perform(put(BeerController.BEER_PATH_ID, dto.getOid()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto))).andExpect(status().isNoContent());

		verify(service).update(any(UUID.class), any(BeerDTO.class));
	}

	@Test
	void testCreateNewBeer() throws Exception { // tam doğru değil çünkü insert'e gitmedi.
		BeerDTO testObject = beers.get(0).clone();

		System.out.println(mapper.writeValueAsString(testObject));
		testObject.setOid(null);
		testObject.setCreated(null);
		testObject.setUpdated(null);
		testObject.setName(testObject.getName() + " - CREATED");

		given(service.insert(any(BeerDTO.class))).willReturn(beers.get(1));
//		given(service.insert(any(BeerDTO.class))).willReturn(testObject);

		mockMvc.perform(post(BeerController.BEER_PATH).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testObject))).andExpect(status().isCreated()).andExpect(header().exists("Location"))

//				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.length()", is(beers.size())))
		;
	}

	@Test
	void testListBeers() throws Exception {
		given(service.listBeers()).willReturn(beers);

		mockMvc.perform(get(BeerController.BEER_PATH).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.length()", is(beers.size())));
	}

	@Test
	void testGetBeerByOid() throws Exception {
		BeerDTO testBeer = beers.get(0);

		given(service.getBeerByOid(testBeer.getOid())).willReturn(Optional.of(testBeer));

		mockMvc.perform(get(BeerController.BEER_PATH_ID, testBeer.getOid()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.oid", is(testBeer.getOid().toString()))).andExpect(jsonPath("$.name", is(testBeer.getName())));
	}
}
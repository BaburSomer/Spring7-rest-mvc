package com.babsom.spring7restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.babsom.spring7restmvc.entity.Beer;
import com.babsom.spring7restmvc.mapper.BeerMapper;
import com.babsom.spring7restmvc.model.BeerDTO;
import com.babsom.spring7restmvc.model.BeerStyle;
import com.babsom.spring7restmvc.repository.BeerRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
class BeerControllerIT {

	@Autowired
	BeerController        controller;
	@Autowired
	BeerRepository        repository;
	@Autowired
	BeerMapper            mapper;
	@Autowired
	WebApplicationContext wac;
	@Autowired
	ObjectMapper          objectMapper;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
   @Disabled // just for demo purposes
   @Test
   void testUpdateBeerBadVersion() throws Exception {
       Beer beer = repository.findAll().get(0);

       BeerDTO beerDTO = mapper.entityToDto(beer);

       beerDTO.setName("Updated Name");

       MvcResult result = mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getOid())
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(beerDTO)))
               .andExpect(status().isNoContent())
               .andReturn();

       System.out.println(result.getResponse().getContentAsString());

       beerDTO.setName("Updated Name 2");

       MvcResult result2 = mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getOid())
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(beerDTO)))
               .andExpect(status().isNoContent())
               .andReturn();

       System.out.println(result2.getResponse().getStatus());
   }

	
	
	
	@Test
	void testListBeersByStyleAndNameShowInventoryTruePage2() throws Exception {
		mockMvc.perform(get(BeerController.BEER_PATH)
				.queryParam("beerName", "IPA")
				.queryParam("beerStyle", BeerStyle.IPA.name())
				.queryParam("showInventory", "true")
				.queryParam("pageNumber", "2")
				.queryParam("pageSize", "50"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(11)))
		.andExpect(jsonPath("$.content.[0].quantityOnHand").value(IsNull.notNullValue()));
	}
	
   @Test
   void testListBeersByStyleAndNameShowInventoryTrue() throws Exception {
       mockMvc.perform(get(BeerController.BEER_PATH)
                       .queryParam("beerName", "IPA")
                       .queryParam("beerStyle", BeerStyle.IPA.name())
                       .queryParam("showInventory", "true"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()", is(11)))
               .andExpect(jsonPath("$.content.[0].quantityOnHand").value(IsNull.notNullValue()));
   }

   @Test
   void testListBeersByStyleAndNameShowInventoryFalse() throws Exception {
       mockMvc.perform(get(BeerController.BEER_PATH)
                       .queryParam("beerName", "IPA")
                       .queryParam("beerStyle", BeerStyle.IPA.name())
                       .queryParam("showInventory", "false"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()", is(11)))
               .andExpect(jsonPath("$.content.[0].quantityOnHand").value(IsNull.nullValue()));
   }

   @Test
   void testListBeersByStyle() throws Exception {
       mockMvc.perform(get(BeerController.BEER_PATH)
                       .queryParam("beerStyle", BeerStyle.IPA.name()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content.size()", is(25)));
   }

	@Test
	void testListBeersByName() throws Exception {
		mockMvc.perform(get(BeerController.BEER_PATH)
				.queryParam("beerName", "IPA"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(11)));
	}
	
	@Test
	void testListBeersByStyleAndName() throws Exception {
		mockMvc.perform(get(BeerController.BEER_PATH)
				.queryParam("beerStyle", "PORTER"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(11)));
	}
	
	@Test
	void testPatchBeerWithBadName() throws Exception {
		Beer testObject = repository.findAll().get(0);

		Map<String, Object> beerMap = new HashMap<>();
		beerMap.put("name", "Bomonti-Bomonti-Bomonti-Bomonti-Bomonti-Bomonti-Bomonti-");

		mockMvc.perform(patch(BeerController.BEER_PATH_ID, testObject.getOid())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(beerMap)))
				.andExpect(jsonPath("$.length()", is(4)))
				.andExpect(status().isBadRequest());

	}


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
		Beer entity = repository.findAll().get(0);

		ResponseEntity<HttpStatus> response = controller.deleteById(entity.getOid());

		assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()));

		assertThat(repository.findById(entity.getOid()).isEmpty());
	}

	@Test
	void testUpdateBeerByNotExistingId() {
		assertThrows(NotFoundException.class, () -> {
			controller.updateById(UUID.randomUUID(), BeerDTO.builder().build());
		});
	}

	@Rollback
	@Transactional
	@Test
	void testUpdateBeer() {
		Beer    entity = repository.findAll().get(0);
		BeerDTO dto    = mapper.entityToDto(entity);
		dto.setOid(null);
		dto.setVersion(null);
		final String beerName = "UPDATED";
		dto.setName(beerName);

		ResponseEntity<HttpStatus> response = controller.updateById(entity.getOid(), dto);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.NO_CONTENT.value()));

		Beer beer = repository.findById(entity.getOid()).get();
		assertThat(beer.getName()).isEqualTo(beerName);
	}

	@Rollback
	@Transactional
	@Test
	void testCreateNewBeer() {
		BeerDTO                    dto      = BeerDTO.builder().name("Tuborg").style(BeerStyle.PILSNER).price(new BigDecimal("72.50"))
				.quantityOnHand(55).build();
		ResponseEntity<HttpStatus> response = controller.handlePost(dto);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(HttpStatus.CREATED.value()));
		assertThat(response.getHeaders().getLocation()).isNotNull();

		String[] locationPath = response.getHeaders().getLocation().getPath().split("/");
		UUID     uuid         = UUID.fromString(locationPath[4]);

		Beer beer = repository.findById(uuid).get();
		assertThat(beer).isNotNull();
	}

	@Test
	void testListBeers() {
//		List<BeerDTO> beers = controller.listBeers(null, null, null);
		Page<BeerDTO> beers = controller.listBeers(any(), any(), any(), any(), any());

		assertThat(beers.getContent().size()).isEqualTo(25);
	}

	@Test
	void testGetBeerById() {
		Beer    beer    = repository.findAll().get(0);
		BeerDTO beerDTO = controller.getByOid(beer.getOid());

		assertThat(beerDTO).isNotNull();
	}

	@Test
	void testGetBeerByNonExistingId() {
		assertThrows(NotFoundException.class, () -> {
			controller.getByOid(UUID.randomUUID());
		});
	}

	@Rollback
	@Transactional
	@Test
	void testEmptyList() {
		repository.deleteAll();
		Page<BeerDTO> beers = controller.listBeers(null, null, null, null, null);

		assertThat(beers.getContent().size()).isEqualTo(0);
	}
}
package com.babsom.spring7restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
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

import com.babsom.spring7restmvc.entity.Beer;
import com.babsom.spring7restmvc.mapper.BeerMapper;
import com.babsom.spring7restmvc.model.BeerDTO;
import com.babsom.spring7restmvc.model.BeerStyle;
import com.babsom.spring7restmvc.repository.BeerRepository;

@SpringBootTest
class BeerControllerIT {

	@Autowired
	BeerController controller;
	@Autowired
	BeerRepository repository;
	@Autowired
	BeerMapper     mapper;

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
		Beer entity = repository.findAll().get(0);
		BeerDTO dto = mapper.entityToDto(entity);
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
		List<BeerDTO> beers = controller.listBeers();

		assertThat(beers.size()).isEqualTo(3);
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
		List<BeerDTO> beers = controller.listBeers();

		assertThat(beers.size()).isEqualTo(0);
	}
}
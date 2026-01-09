package com.babsom.spring7restmvc.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.babsom.spring7restmvc.entity.Beer;
import com.babsom.spring7restmvc.model.BeerStyle;

import jakarta.validation.ConstraintViolationException;

@DataJpaTest
class BeerRepositoryTest {

	@Autowired
	BeerRepository repository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSaveBeerWithTooLongBeerName() {

		assertThrows(ConstraintViolationException.class, () -> {
			repository.save(Beer.builder()
					.name("Bomonti-Bomonti-Bomonti-Bomonti-Bomonti-Bomonti-Bomonti-")
					.style(BeerStyle.LAGER)
					.upc("dgdfgdfg63456337457")
					.price(new BigDecimal("15.50"))
					.build());
			
			repository.flush();
		});

	}
	
	@Test
	void testSaveBeer() {
		Beer saved = repository.save(Beer.builder()
				.name("Bomonti")
				.style(BeerStyle.LAGER)
				.upc("dgdfgdfg63456337457")
				.price(new BigDecimal("15.50"))
				.build());
		
		repository.flush();
		
		assertThat(saved).isNotNull();
		assertThat(saved.getOid()).isNotNull();
		assertThat(saved.getVersion()).isNotNull();
	}
}

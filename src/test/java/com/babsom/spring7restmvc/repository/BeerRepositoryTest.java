package com.babsom.spring7restmvc.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.babsom.spring7restmvc.entity.Beer;

@DataJpaTest
class BeerRepositoryTest {

	@Autowired
	BeerRepository repository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSaveBeer() {
		Beer saved = repository.save(Beer.builder().name("Bomonti").build());
		assertThat(saved).isNotNull();
		assertThat(saved.getOid()).isNotNull();
		assertThat(saved.getVersion()).isNotNull();
	}
}

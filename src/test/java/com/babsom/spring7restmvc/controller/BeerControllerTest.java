package com.babsom.spring7restmvc.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerControllerTest {

	@Autowired
	BeerController controller;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetByOid() {
		System.out.println(controller.getByOid(UUID.randomUUID()));
	}

}

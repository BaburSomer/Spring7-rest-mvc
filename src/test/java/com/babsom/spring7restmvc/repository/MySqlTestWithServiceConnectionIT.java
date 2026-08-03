package com.babsom.spring7restmvc.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.babsom.spring7restmvc.entity.Beer;

//@Disabled
@Testcontainers
@SpringBootTest
@ActiveProfiles("localmysql")
public class MySqlTestWithServiceConnectionIT {

	@Container
	@ServiceConnection
	static private MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8");
	
	@Autowired
	BeerRepository repository;
	
	@Test
	void listBeers() {
		List<Beer> beers = repository.findAll();
		
		assertThat(beers.size()).isGreaterThan(0);
				
	}
}

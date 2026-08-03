package com.babsom.spring7restmvc.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.babsom.spring7restmvc.entity.Beer;
@Disabled
@Testcontainers
@SpringBootTest
@ActiveProfiles("localmysql")
public class MySqlIT {

	@Container
	static private MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8");
	
	
	/*
	 * Buna aslında ihtiyacım olmadı. eclipse'ten mi yoksa kullandığım versiyonlardan mı bilmiyorum. ama 
	 * sicher ist sicher
	 * bunu kullanmadığım zaman gerçek veritabanına bağlandı. ve oradan verileri okudu.
	 * bunu kullandığım zaman sanırım kendisi bir veritabanı yarattı ve oradan test etti. Çünkü kullanmadığım zaman sql'de select 
	 * yaptığım gibi 20 küsur veri okudu. Kullandığım zaman sadece 3 adet okudu. 
	 */
	@DynamicPropertySource
	static void mySqlProterties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.username", mySQLContainer::getUsername);
		registry.add("spring.datasource.password", mySQLContainer::getPassword);
		registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
	}
	
	/* bu da gereksiz. sadece datasource'a erişim nasıl sağlanır görmek için */
	@Autowired
	DataSource dataSource;
	
	@Autowired
	BeerRepository repository;
	
	@Test
	void listBeers() {
		List<Beer> beers = repository.findAll();
		
		assertThat(beers.size()).isGreaterThan(0);
				
	}
}

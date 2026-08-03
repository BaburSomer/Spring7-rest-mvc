package com.babsom.spring7restmvc.bootstrap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.babsom.spring7restmvc.repository.BeerRepository;
import com.babsom.spring7restmvc.repository.CustomerRepository;
import com.babsom.spring7restmvc.service.BeerCSVService;
import com.babsom.spring7restmvc.service.impl.BeerCSVServiceImpl;

@DataJpaTest
@Import(BeerCSVServiceImpl.class)
class BootstrapDataTest {


   @Autowired
   BeerRepository beerRepository;

   @Autowired
   CustomerRepository customerRepository;

   @Autowired
   BeerCSVService csvService;

   BootstrapData bootstrapData;

   @BeforeEach
   void setUp() {
       bootstrapData = new BootstrapData(beerRepository, customerRepository, csvService);
   }

   @Test
   void Testrun() throws Exception {
       bootstrapData.run(null);

       assertThat(beerRepository.count()).isEqualTo(2413);
       assertThat(customerRepository.count()).isEqualTo(4);
   }
}

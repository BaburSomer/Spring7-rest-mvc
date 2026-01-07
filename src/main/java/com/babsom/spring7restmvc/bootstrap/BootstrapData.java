package com.babsom.spring7restmvc.bootstrap;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.babsom.spring7restmvc.entity.Beer;
import com.babsom.spring7restmvc.entity.Customer;
import com.babsom.spring7restmvc.model.BeerStyle;
import com.babsom.spring7restmvc.repository.BeerRepository;
import com.babsom.spring7restmvc.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

	private final BeerRepository     beerRepository;
	private final CustomerRepository customerRepository;

	@Override
	public void run(String... args) throws Exception {
		loadBeerData();
		loadCustomerData();
	}

	private void loadCustomerData() {
		Customer customer = Customer.builder().firstName("Babur").lastName("Somer").created(LocalDateTime.now()).build();
		customerRepository.save(customer);

		customer = Customer.builder().firstName("Elif").lastName("Somer").created(LocalDateTime.now()).build();
		customerRepository.save(customer);

		customer = Customer.builder().firstName("Ayse").lastName("Emiroğlu").created(LocalDateTime.now()).build();
		customerRepository.save(customer);

		customer = Customer.builder().firstName("Giray").lastName("Emiroğlu").created(LocalDateTime.now()).build();
		customerRepository.save(customer);
	}

	private void loadBeerData() {
		Beer beer = Beer.builder().name("Efes Pilsen").style(BeerStyle.PILSNER).upc("211654").price(new BigDecimal("70.99")).quantityOnHand(122)
				.created(LocalDateTime.now()).build();
		beerRepository.save(beer);

		beer = Beer.builder().name("Bomonti").style(BeerStyle.WHEAT).upc("568994").price(new BigDecimal("75.49")).quantityOnHand(500)
				.created(LocalDateTime.now()).build();
		beerRepository.save(beer);

		beer = Beer.builder().name("Zıkkım").style(BeerStyle.IPA).upc("123789").price(new BigDecimal("58.44")).quantityOnHand(25)
				.created(LocalDateTime.now()).build();
		beerRepository.save(beer);
	}
}
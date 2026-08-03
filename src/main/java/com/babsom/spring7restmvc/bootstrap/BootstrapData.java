package com.babsom.spring7restmvc.bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.babsom.spring7restmvc.entity.Beer;
import com.babsom.spring7restmvc.entity.Customer;
import com.babsom.spring7restmvc.model.BeerCSVRecord;
import com.babsom.spring7restmvc.model.BeerStyle;
import com.babsom.spring7restmvc.repository.BeerRepository;
import com.babsom.spring7restmvc.repository.CustomerRepository;
import com.babsom.spring7restmvc.service.BeerCSVService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

	private final BeerRepository     beerRepository;
	private final CustomerRepository customerRepository;
	private final BeerCSVService     service;

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		loadBeerData();
		loadCSVData();
		loadCustomerData();
	}

	private void loadCSVData() throws FileNotFoundException {
		if (beerRepository.count() < 10) {
			File file;
			file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
			List<BeerCSVRecord> recs = service.convertCSV(file);
			recs.forEach(rec -> {
				BeerStyle beerStyle = switch (rec.getStyle()) {
               case "American Pale Lager" -> BeerStyle.LAGER;
               case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                       BeerStyle.ALE;
               case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
               case "American Porter" -> BeerStyle.PORTER;
               case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
               case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
               case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
               case "English Pale Ale" -> BeerStyle.PALE_ALE;
               default -> BeerStyle.PILSNER;
           };
           
           beerRepository.save(Beer.builder()
                 .name(StringUtils.abbreviate(rec.getBeer(), 50))
                 .style(beerStyle)
                 .price(BigDecimal.TEN)
                 .upc(rec.getRow().toString())
                 .quantityOnHand(rec.getCount())
                 .build());
			});
		}
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
package com.babsom.spring7restmvc.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import com.babsom.spring7restmvc.model.BeerCSVRecord;
import com.babsom.spring7restmvc.service.impl.BeerCSVServiceImpl;

public class BeerCSVServiceImplTest {

	BeerCSVService service = new BeerCSVServiceImpl();

	@Test
	void convertCSV() throws FileNotFoundException {
		File                file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
		List<BeerCSVRecord> recs = service.convertCSV(file);
		assertThat(recs.size()).isGreaterThan(0);
	}
}

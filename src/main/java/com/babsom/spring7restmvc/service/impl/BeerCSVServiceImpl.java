package com.babsom.spring7restmvc.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.springframework.stereotype.Service;

import com.babsom.spring7restmvc.model.BeerCSVRecord;
import com.babsom.spring7restmvc.service.BeerCSVService;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class BeerCSVServiceImpl implements BeerCSVService {

	@Override
	public List<BeerCSVRecord> convertCSV(File file) {
		try {
			List<BeerCSVRecord> recs = new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(file))
					.withType(BeerCSVRecord.class)
					.build()
					.parse();
			return recs;
		} catch (IllegalStateException | FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
package com.babsom.spring7restmvc.service;

import java.io.File;
import java.util.List;

import com.babsom.spring7restmvc.model.BeerCSVRecord;

public interface BeerCSVService {

	List<BeerCSVRecord> convertCSV(File file);
}

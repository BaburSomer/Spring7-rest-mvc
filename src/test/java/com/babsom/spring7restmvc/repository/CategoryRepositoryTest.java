package com.babsom.spring7restmvc.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.babsom.spring7restmvc.entity.Beer;
import com.babsom.spring7restmvc.entity.Category;

@SpringBootTest
class CategoryRepositoryTest {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	BeerRepository beerRepository;

	Beer testBeer;
	
	@BeforeEach
	void setUp() throws Exception {
		testBeer = beerRepository.findAll().get(3);
	}

	@Transactional
	@Test
	void testAddCategory() {
		Category savedCategory = categoryRepository.save(Category.builder().name("Ales").build());
		System.out.println(savedCategory.getName());
		
		testBeer.addCategory(savedCategory);
		Beer savedBeer = beerRepository.save(testBeer);
		System.out.println(savedBeer.getName());
	}
}
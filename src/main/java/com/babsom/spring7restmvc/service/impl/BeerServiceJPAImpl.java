package com.babsom.spring7restmvc.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.babsom.spring7restmvc.entity.Beer;
import com.babsom.spring7restmvc.mapper.BeerMapper;
import com.babsom.spring7restmvc.model.BeerDTO;
import com.babsom.spring7restmvc.model.BeerStyle;
import com.babsom.spring7restmvc.repository.BeerRepository;
import com.babsom.spring7restmvc.service.BeerService;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPAImpl implements BeerService {

	private final BeerRepository repository;
	private final BeerMapper     mapper;

	@Override
	public Optional<BeerDTO> getBeerByOid(UUID oid) {
		return Optional.ofNullable(mapper.entityToDto(repository.findById(oid).orElse(null)));
	}

	@Override
	public Page<BeerDTO> listBeers(String beerName, BeerStyle style, Boolean showInventory, Integer pageNumber, Integer pageSize) {
		
		Page<Beer> beerPages;
		
		PageRequest pageRequest = this.buildPageRequest(pageNumber, pageSize);
		
		if (StringUtils.hasText(beerName) && style == null) {
			beerPages = listBeersByName(beerName, pageRequest);
		}
		else if (!StringUtils.hasText(beerName) && style != null) {
			beerPages = listBeersByStyle(style, pageRequest);
		}
		else if (StringUtils.hasText(beerName) && style != null) {
			beerPages = listBeersByNameAndStyle(beerName, style, pageRequest);
		}
		else {
			beerPages = repository.findAll(pageRequest);
		}
		
		if (showInventory != null && !showInventory) {
			beerPages.forEach(beer -> beer.setQuantityOnHand(null));
		}
		
//		return beerPages.stream()
//				.map(mapper::entityToDto)
//				.collect(Collectors.toList());
		return beerPages.map(mapper::entityToDto);
	}
	
	private Page<Beer> listBeersByNameAndStyle(String beerName, BeerStyle style, Pageable pageable) {
		return repository.findAllByNameIsLikeIgnoreCaseAndStyle("%" + beerName + "%", style, pageable);
	}

	public Page<Beer> listBeersByStyle(BeerStyle style, Pageable pageable) {
		return repository.findAllByStyle(style, pageable);	
	}

	public Page<Beer> listBeersByName(String beerName, Pageable pageable) {
		return repository.findAllByNameIsLikeIgnoreCase("%" + beerName + "%", pageable);
	}

	@Override
	public BeerDTO insert(BeerDTO dto) {
		return mapper.entityToDto(repository.save(mapper.dtoToEntity(dto)));
	}

	@Override
	public Optional<BeerDTO>  update(UUID beerId, BeerDTO dto) {
		AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();
		
		repository.findById(beerId).ifPresentOrElse(foundBeer -> {
			foundBeer.setName(dto.getName());
			foundBeer.setStyle(dto.getStyle());
			foundBeer.setUpc(dto.getUpc());
			foundBeer.setPrice(dto.getPrice());
			foundBeer.setQuantityOnHand(dto.getQuantityOnHand());
			foundBeer.setModified(LocalDateTime.now());
			atomicReference.set(Optional.of(mapper.entityToDto(repository.save(foundBeer))));
		}, () -> { 
			atomicReference.set(Optional.empty()); 
		});
		
		return atomicReference.get();
	}
	
	@Override
	public Boolean deleteById(UUID beerId) {
		if (repository.existsById(beerId)) {
			repository.deleteById(beerId);
			return true;
		}
		
		return false;
	}

	@Override
	public Optional<BeerDTO>  patchById(UUID beerId, BeerDTO beer) {
		return Optional.empty();
	}
}

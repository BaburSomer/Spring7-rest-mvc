package com.babsom.spring7restmvc.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.babsom.spring7restmvc.mapper.CustomerMapper;
import com.babsom.spring7restmvc.model.CustomerDTO;
import com.babsom.spring7restmvc.repository.CustomerRepository;
import com.babsom.spring7restmvc.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPAImpl implements CustomerService {

	private final CustomerRepository repository;
	private final CustomerMapper     mapper;

	@Override
	public Optional<CustomerDTO> getCustomerByOid(UUID oid) {
		return Optional.ofNullable(mapper.entityToDto(repository.findById(oid).orElse(null)));
	}

	@Override
	public Optional<CustomerDTO> getCustomerByFirstName(String firstName) {
		//TODO orderRepository'i genişletmek gerekli
		return null;
	}

	@Override
	public List<CustomerDTO> listCustomers() {
		return repository.findAll().stream().map(mapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public CustomerDTO insert(CustomerDTO dto) {
		return mapper.entityToDto(repository.save(mapper.dtoToEntity(dto)));	}

	@Override
	public Optional<CustomerDTO> update(UUID customerId, CustomerDTO customer) {
		AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
		
		repository.findById(customerId).ifPresentOrElse(found -> {
			found.setFirstName(customer.getFirstName());
			found.setLastName(customer.getLastName());
			found.setModified(LocalDateTime.now());
			atomicReference.set(Optional.of(mapper.entityToDto(repository.save(found))));
		}, () -> {
					atomicReference.set(Optional.empty());
		});
		
		return atomicReference.get();
	}

	@Override
	public Boolean deleteByOid(UUID customerId) {
		if (repository.existsById(customerId)) {
			repository.deleteById(customerId);
			return true;
		}
		
		return false;
	}

	@Override
	public Optional<CustomerDTO> patchById(UUID customerId, CustomerDTO customer) {
		AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

		repository.findById(customerId).ifPresentOrElse(found -> {
			if (StringUtils.hasText(customer.getFirstName())) {
				found.setFirstName(customer.getFirstName());
			}
			if (StringUtils.hasText(customer.getLastName())) {
				found.setLastName(customer.getLastName());
			}
			found.setModified(LocalDateTime.now());
			atomicReference.set(Optional.of(mapper.entityToDto(repository.save(found))));
		}, () -> {
					atomicReference.set(Optional.empty());
		});
		
		return atomicReference.get();

	}

}

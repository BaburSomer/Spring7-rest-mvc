package com.babsom.spring7restmvc.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.babsom.spring7restmvc.model.CustomerDTO;
import com.babsom.spring7restmvc.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	private Map<UUID, CustomerDTO>   customersByOid;
	private Map<String, CustomerDTO> customersByFirstName;

	public CustomerServiceImpl() {
		this.customersByFirstName = new HashMap<>();
		this.customersByOid       = new HashMap<>();

		CustomerDTO customer = CustomerDTO.builder().oid(UUID.randomUUID()).firstName("Babur").lastName("Somer").version(1).created(LocalDateTime.now())
				.build();
		this.customersByFirstName.put(customer.getFirstName(), customer);
		this.customersByOid.put(customer.getOid(), customer);

		customer = CustomerDTO.builder().oid(UUID.randomUUID()).firstName("Elif").lastName("Somer").version(2).created(LocalDateTime.now()).build();
		this.customersByFirstName.put(customer.getFirstName(), customer);
		this.customersByOid.put(customer.getOid(), customer);

		customer = CustomerDTO.builder().oid(UUID.randomUUID()).firstName("Ayse").lastName("Emiroğlu").version(3).created(LocalDateTime.now()).build();
		this.customersByFirstName.put(customer.getFirstName(), customer);
		this.customersByOid.put(customer.getOid(), customer);

		customer = CustomerDTO.builder().oid(UUID.randomUUID()).firstName("Giray").lastName("Emiroğlu").version(4).created(LocalDateTime.now()).build();
		this.customersByFirstName.put(customer.getFirstName(), customer);
		this.customersByOid.put(customer.getOid(), customer);
	}

	@Override
	public Optional<CustomerDTO> getCustomerByOid(UUID oid) {
		return Optional.of(this.customersByOid.get(oid));
	}

	@Override
	public Optional<CustomerDTO> getCustomerByFirstName(String firstName) {
		return Optional.of(this.customersByFirstName.get(firstName));
	}

	@Override
	public List<CustomerDTO> listCustomers() {
		return new ArrayList<>(customersByOid.values());
	}

	@Override
	public CustomerDTO create(CustomerDTO customer) {
		customer.setOid(UUID.randomUUID());
		customer.setCreated(LocalDateTime.now());
		this.customersByOid.put(customer.getOid(), customer);
		this.customersByFirstName.put(customer.getFirstName(), customer);
		return customer;
	}

	@Override
	public void update(UUID customerId, CustomerDTO customer) {
		CustomerDTO cust = this.customersByOid.get(customerId);
		this.customersByFirstName.remove(cust.getFirstName());
		cust.setFirstName(customer.getFirstName());
		cust.setLastName(customer.getLastName());
		cust.setUpdated(LocalDateTime.now());
		cust.setVersion(customer.getVersion());
		this.customersByFirstName.put(customer.getFirstName(), cust);
	}

	@Override
	public void deleteByOid(UUID customerId) {
		CustomerDTO cust = this.customersByOid.remove(customerId);
		this.customersByFirstName.remove(cust.getFirstName());
	}

	@Override
	public void patchById(UUID customerId, CustomerDTO customer) {
		CustomerDTO cust = this.customersByOid.get(customerId);
		this.customersByFirstName.remove(cust.getFirstName());

		if (StringUtils.hasText(customer.getFirstName())) {
			cust.setFirstName(customer.getFirstName());
		}

		if (StringUtils.hasText(customer.getLastName())) {
			cust.setLastName(customer.getLastName());
		}
		if (customer.getVersion() != null) {
			cust.setVersion(customer.getVersion());
		}
		cust.setUpdated(LocalDateTime.now());

		this.customersByFirstName.put(customer.getFirstName(), cust);
	}
}
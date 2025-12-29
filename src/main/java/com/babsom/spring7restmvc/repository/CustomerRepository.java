package com.babsom.spring7restmvc.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.babsom.spring7restmvc.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}

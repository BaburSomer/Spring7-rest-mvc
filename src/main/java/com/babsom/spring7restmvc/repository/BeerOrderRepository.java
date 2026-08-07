package com.babsom.spring7restmvc.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.babsom.spring7restmvc.entity.BeerOrder;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {

}

package com.babsom.spring7restmvc.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.babsom.spring7restmvc.entity.Beer;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

}

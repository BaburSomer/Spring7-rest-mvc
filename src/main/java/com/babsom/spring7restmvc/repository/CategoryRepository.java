package com.babsom.spring7restmvc.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.babsom.spring7restmvc.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}

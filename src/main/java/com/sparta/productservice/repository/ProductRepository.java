package com.sparta.productservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.productservice.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}

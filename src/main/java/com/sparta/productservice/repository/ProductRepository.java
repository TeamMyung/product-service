package com.sparta.productservice.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.query.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.productservice.dto.response.ProductListResponseDto;
import com.sparta.productservice.entity.ProductEntity;
import com.sparta.productservice.entity.enums.ProductStatus;

import lombok.RequiredArgsConstructor;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID>, ProductRepositoryCustom {
}
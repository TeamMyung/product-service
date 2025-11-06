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
/*
public interface ProductRepositoryCustom {
	Page<ProductListResponseDto> search(UUID hubId,
		ProductStatus status,
		String nameContains,
		Pageable pageable);
}

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory query;

	@Override
	public Page<ProductListResponseDto> search(UUID hubId,
		ProductStatus status,
		String nameContains,
		Pageable pageable) {

		BooleanBuilder where = new BooleanBuilder();
		if (hubId != null) where.and(productEntity.hubId.eq(hubId));
		if (status != null) where.and(productEntity.productStatus.eq(status));
		if (nameContains != null && !nameContains.isBlank())
			where.and(productEntity.productName.containsIgnoreCase(nameContains));

		List<ProductListResponseDto> content = query
			.select(Projections.constructor(
				ProductListResponseDto.class,
				productEntity.id,
				productEntity.hubId,
				productEntity.vendorName,
				productEntity.productName,
				productEntity.productStatus,
				productEntity.productPrice,
				productEntity.stock,
				productEntity.createdAt
			))
			.from(productEntity)
			.where(where)
			.orderBy(productEntity.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = query
			.select(productEntity.count())
			.from(productEntity)
			.where(where)
			.fetchOne();

		return new PageImpl<>(content, pageable, total == null ? 0 : total);
	}
}
*/
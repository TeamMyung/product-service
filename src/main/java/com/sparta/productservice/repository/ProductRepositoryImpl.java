package com.sparta.productservice.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.productservice.dto.response.ProductListResponseDto;
import com.sparta.productservice.dto.response.QProductListResponseDto;

import com.sparta.productservice.entity.enums.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.sparta.productservice.entity.QProductEntity.productEntity;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory query;

	@Override
	public Page<ProductListResponseDto> search(UUID hubId, ProductStatus status, Pageable pageable) {
		BooleanBuilder where = new BooleanBuilder();
		if (hubId != null) where.and(productEntity.hubId.eq(hubId));
		if (status != null) where.and(productEntity.productStatus.eq(status));

		List<ProductListResponseDto> content = query
			.select(new QProductListResponseDto(
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
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(productEntity.createdAt.desc())
			.fetch();

		long total = query.select(productEntity.count())
			.from(productEntity)
			.where(where)
			.fetchOne();

		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public Page<ProductListResponseDto> searchByVendor(UUID vendorId, ProductStatus status, Pageable pageable) {
		BooleanBuilder where = new BooleanBuilder();
		if (vendorId != null) where.and(productEntity.vendorId.eq(vendorId)); // vendor 기준 필터
		if (status != null) where.and(productEntity.productStatus.eq(status));

		List<ProductListResponseDto> content = query
			.select(new QProductListResponseDto(
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
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(productEntity.createdAt.desc())
			.fetch();

		long total = query.select(productEntity.count())
			.from(productEntity)
			.where(where)
			.fetchOne();

		return new PageImpl<>(content, pageable, total);
	}
}

package com.sparta.productservice.repository;

import com.sparta.productservice.dto.response.ProductListResponseDto;
import com.sparta.productservice.entity.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductRepositoryCustom {
	Page<ProductListResponseDto> search(UUID hubId, ProductStatus status, Pageable pageable);
}

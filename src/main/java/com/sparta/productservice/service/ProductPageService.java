package com.sparta.productservice.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sparta.productservice.dto.response.ProductListResponseDto;
import com.sparta.productservice.entity.enums.ProductStatus;
import com.sparta.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductPageService {

	private final ProductRepository productRepository;

	public Page<ProductListResponseDto> getAllProducts(UUID hubId, String status, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		ProductStatus productStatus = null;
		if (status != null && !status.isBlank()) {
			try {
				productStatus = ProductStatus.valueOf(status.toUpperCase());
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("잘못된 상품 상태 값입니다. (허용: PENDING, APPROVED, DENIED)");
			}
		}

		return productRepository.search(hubId, productStatus, pageable);
	}

	public Page<ProductListResponseDto> getHubProducts(UUID hubId, String status, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		ProductStatus productStatus = null;
		if (status != null && !status.isBlank()) {
			try {
				productStatus = ProductStatus.valueOf(status.toUpperCase());
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("잘못된 상품 상태 값입니다. (허용: PENDING, APPROVED, DENIED)");
			}
		}

		return productRepository.search(hubId, productStatus, pageable);
	}
}

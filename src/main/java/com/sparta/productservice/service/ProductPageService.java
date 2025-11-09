package com.sparta.productservice.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sparta.productservice.dto.response.ProductListResponseDto;
import com.sparta.productservice.entity.enums.ProductStatus;
import com.sparta.productservice.global.exception.CustomException;
import com.sparta.productservice.global.exception.ErrorCode;
import com.sparta.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductPageService {

	private final ProductRepository productRepository;

	public Page<ProductListResponseDto> getAllProducts(UUID hubId, String status, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		ProductStatus productStatus = parseStatus(status);

		Page<ProductListResponseDto> products = productRepository.search(hubId, productStatus, pageable);
		if (products.isEmpty()) {
			throw new CustomException(ErrorCode.PRODUCT_LIST_EMPTY);
		}

		return products;
	}

	public Page<ProductListResponseDto> getHubProducts(UUID hubId, String status, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		ProductStatus productStatus = parseStatus(status);

		Page<ProductListResponseDto> products = productRepository.search(hubId, productStatus, pageable);
		if (products.isEmpty()) {
			throw new CustomException(ErrorCode.PRODUCT_LIST_EMPTY);
		}

		return products;
	}

	public Page<ProductListResponseDto> getVendorProducts(UUID vendorId, String status, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		ProductStatus productStatus = parseStatus(status);

		Page<ProductListResponseDto> products = productRepository.searchByVendor(vendorId, productStatus, pageable);
		if (products.isEmpty()) {
			throw new CustomException(ErrorCode.PRODUCT_LIST_EMPTY);
		}

		return products;
	}

	// 에러코드: 상태값
	private ProductStatus parseStatus(String status) {
		if (status == null || status.isBlank()) return null;

		try {
			return ProductStatus.valueOf(status.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new CustomException(ErrorCode.INVALID_PRODUCT_STATUS);
		}
	}
}
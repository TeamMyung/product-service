package com.sparta.productservice.service;

import com.sparta.productservice.dto.request.ProductRequestDto;
import com.sparta.productservice.dto.request.ProductUpdateRequestDto;
import com.sparta.productservice.dto.response.ProductResponseDto;
import com.sparta.productservice.entity.ProductEntity;
import com.sparta.productservice.entity.enums.ProductStatus;
import com.sparta.productservice.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	@Transactional
	public ProductResponseDto createProduct(ProductRequestDto requestDto) {
		ProductEntity product = ProductEntity.builder()
			.productName(requestDto.getName())
			.vendorId(UUID.fromString(requestDto.getVendorId()))
			.vendorName(requestDto.getVendorName())
			.hubId(UUID.fromString(requestDto.getHubId()))
			.stock(requestDto.getStock())
			.productPrice(requestDto.getProductPrice())
			.description(requestDto.getDescription())
			.productStatus(ProductStatus.PENDING)
			.build();

		ProductEntity saved = productRepository.save(product);

		return ProductResponseDto.builder()
			.productId(saved.getId().toString())
			.status(saved.getProductStatus().name())
			.message("상품 등록 완료, 승인 대기 중입니다.")
			.createdAt(saved.getCreatedAt())
			.build();
	}

	@Transactional
	public void updateProduct(UUID productId, ProductUpdateRequestDto requestDto) {
		ProductEntity product = productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

		// 값 변경
		product.updateProduct(
			requestDto.getProductName(),
			requestDto.getStock(),
			requestDto.getProductPrice(),
			UUID.fromString(requestDto.getHubId()),
			requestDto.getDescription()
		);

		productRepository.save(product);
	}
}


package com.sparta.productservice.service;

import com.sparta.productservice.dto.request.ProductRequestDto;
import com.sparta.productservice.dto.request.ProductUpdateRequestDto;
import com.sparta.productservice.dto.response.ProductDetailResponseDto;
import com.sparta.productservice.dto.response.ProductResponseDto;
import com.sparta.productservice.entity.ProductEntity;
import com.sparta.productservice.entity.enums.ProductStatus;
import com.sparta.productservice.global.exception.CustomException;
import com.sparta.productservice.global.exception.ErrorCode;
import com.sparta.productservice.repository.ProductRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public ProductResponseDto createProduct(ProductRequestDto requestDto) {
		try {
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

		} catch (Exception e) {
			throw new CustomException(ErrorCode.PRODUCT_CREATION_FAILED);
		}
	}

	@Transactional
	public void updateProduct(UUID productId, ProductUpdateRequestDto requestDto) {
		ProductEntity product = productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_FOUND.getDetails()));

		try {
			product.updateProduct(
				requestDto.getProductName(),
				requestDto.getStock(),
				requestDto.getProductPrice(),
				UUID.fromString(requestDto.getHubId()),
				requestDto.getDescription()
			);
			productRepository.save(product);
		} catch (Exception e) {
			throw new CustomException(ErrorCode.PRODUCT_UPDATE_FAILED);
		}
	}

	@Transactional(readOnly = true)
	public ProductDetailResponseDto getProductDetail(UUID productId) {
		ProductEntity product = productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_FOUND.getDetails()));

		return ProductDetailResponseDto.builder()
			.productId(product.getId())
			.productName(product.getProductName())
			.price(product.getProductPrice())
			.stock(product.getStock())
			.status(product.getProductStatus().name())
			.hubId(product.getHubId())
			.vendorId(product.getVendorId())
			.vendorName(product.getVendorName())
			.description(product.getDescription())
			.createdAt(product.getCreatedAt())
			.approvedAt(product.getApprovedAt())
			.updatedAt(product.getUpdatedAt())
			.build();
	}

	@Transactional
	public String approveProduct(UUID productId, BigInteger userId) { // JWT 되면 변경
		ProductEntity product = productRepository.findById(productId)
			.orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

		if (product.getProductStatus() == ProductStatus.APPROVED) {
			throw new CustomException(ErrorCode.PRODUCT_ALREADY_APPROVED);
		}

		product.approve(userId);
		product.updateStatus(ProductStatus.APPROVED);
		productRepository.saveAndFlush(product);

		return "상품 승인 완료 (" + product.getProductName() + ")";
	}

	@Transactional
	public ProductResponseDto denyProduct(UUID productId) {
		ProductEntity product = productRepository.findById(productId)
			.orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

		if (product.getProductStatus() == ProductStatus.DENIED) {
			throw new CustomException(ErrorCode.PRODUCT_ALREADY_DENIED);
		}

		product.updateStatus(ProductStatus.DENIED);
		productRepository.saveAndFlush(product);

		return ProductResponseDto.builder()
			.productId(product.getId().toString())
			.status(product.getProductStatus().name())
			.message("상품이 거절되었습니다.")
			.build();
	}

	@Transactional
	public void deleteProduct(UUID productId, BigInteger userId, String role, UUID hubId) {
		ProductEntity product = productRepository.findById(productId)
			.orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
		//권한 검증
		boolean isAdmin = "MASTER".equalsIgnoreCase(role);
		boolean isHubManager = "HUB_MANAGER".equalsIgnoreCase(role);

		if (!isAdmin && !isHubManager) {
			throw new CustomException(ErrorCode.PRODUCT_DELETE_FORBIDDEN);
		}

		if (isHubManager && !product.getHubId().equals(hubId)) {
			throw new CustomException(ErrorCode.PRODUCT_DELETE_FORBIDDEN);
		}

		if (product.getDeletedAt() != null) {
			throw new CustomException(ErrorCode.PRODUCT_ALREADY_DELETED);
		}

		// 논리 삭제 처리
		try {
			product.softDelete(userId.longValue());
			productRepository.save(product);
		} catch (Exception e) {
			throw new CustomException(ErrorCode.PRODUCT_DELETE_FAILED);
		}
	}

	@Transactional
	public void decreaseStock(UUID productId, int quantity) {
		ProductEntity product = productRepository.findById(productId)
			.orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

		if (product.getStock() < quantity) {
			throw new IllegalStateException("재고가 부족합니다.");
		}

		product.decreaseStock(quantity);
		productRepository.save(product);
	}
}
package com.sparta.productservice.controller;

import java.math.BigInteger;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sparta.productservice.dto.request.ProductRequestDto;
import com.sparta.productservice.dto.request.ProductUpdateRequestDto;
import com.sparta.productservice.dto.response.ProductDetailResponseDto;
import com.sparta.productservice.dto.response.ProductResponseDto;
import com.sparta.productservice.global.dto.ApiResponse;
import com.sparta.productservice.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@Operation(summary = "상품 등록 API", description = "새로운 상품을 등록합니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
		@RequestBody ProductRequestDto requestDto) {
		ProductResponseDto response = productService.createProduct(requestDto);
		return ResponseEntity.ok(new ApiResponse<>(response));
	}

	@Operation(summary = "상품 수정 API", description = "기존 상품 정보를 수정합니다.")
	@PatchMapping("/{productId}")
	public ResponseEntity<ApiResponse<String>> updateProduct(
		@Parameter(description = "상품 UUID") @PathVariable UUID productId,
		@RequestBody ProductUpdateRequestDto requestDto) {
		productService.updateProduct(productId, requestDto);
		return ResponseEntity.ok(new ApiResponse<>("상품 수정 완료"));
	}

	@Operation(summary = "상품 상세 조회 API", description = "상품의 상세 정보를 조회합니다.")
	@GetMapping("/{productId}")
	public ResponseEntity<ApiResponse<ProductDetailResponseDto>> getProductDetail(
		@Parameter(description = "상품 UUID") @PathVariable UUID productId) {
		ProductDetailResponseDto response = productService.getProductDetail(productId);
		return ResponseEntity.ok(new ApiResponse<>(response));
	}

	@Operation(summary = "상품 승인 API", description = "관리자가 상품을 승인 처리합니다.")
	@PatchMapping("/{productId}/approve")
	public ResponseEntity<ApiResponse<String>> approveProduct(
		@Parameter(description = "상품 UUID") @PathVariable UUID productId,
		@RequestParam BigInteger userId) {
		String result = productService.approveProduct(productId, userId);
		return ResponseEntity.ok(new ApiResponse<>(result));
	}

	@Operation(summary = "상품 거절 API", description = "관리자가 상품을 거절 처리합니다.")
	@PatchMapping("/{productId}/denied")
	public ResponseEntity<ApiResponse<ProductResponseDto>> denyProduct(
		@Parameter(description = "상품 UUID") @PathVariable UUID productId,
		@RequestParam BigInteger userId) {
		ProductResponseDto response = productService.denyProduct(productId);
		return ResponseEntity.ok(new ApiResponse<>(response));
	}
}

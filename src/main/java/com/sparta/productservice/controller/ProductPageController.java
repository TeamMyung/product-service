package com.sparta.productservice.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.productservice.dto.response.ProductListResponseDto;
import com.sparta.productservice.service.ProductPageService;
import com.sparta.productservice.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductPageController {

	private final ProductPageService productPageService;

	@Operation(summary = "관리자 상품 조회 API", description = "관리자가 모든 상품을 조회합니다.")
	@GetMapping("/admin")
	public ResponseEntity<Page<ProductListResponseDto>> getAllProducts(
		@RequestParam(required = false) UUID hubId,
		@RequestParam(required =false) String status,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return ResponseEntity.ok(productPageService.getAllProducts(hubId, status, page, size));
	}

	@Operation(summary = "허브 상품 조회 API", description = "허브관리자가 허브 상품을 조회합니다.")
	@GetMapping("/hub")
	public ResponseEntity<Page<ProductListResponseDto>> getHubProducts(
		@RequestParam UUID hubId,
		@RequestParam(required = false) String status,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return ResponseEntity.ok(productPageService.getHubProducts(hubId, status, page, size));
	}

	@Operation(summary = "업체 상품 조회 API", description = "업체가 상품을 조회합니다.")
	@GetMapping("/vendor")
	public ResponseEntity<Page<ProductListResponseDto>> getVendorProducts(
		@RequestParam UUID vendorId,
		@RequestParam(required = false) String status,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return ResponseEntity.ok(productPageService.getVendorProducts(vendorId, status, page, size));
	}
}
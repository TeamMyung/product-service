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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ProductPageController {

	private final ProductPageService productPageService;

	@GetMapping("/admin/products")
	public ResponseEntity<Page<ProductListResponseDto>> getAllProducts(
		@RequestParam(required = false) UUID hubId,
		@RequestParam(required =false) String status,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return ResponseEntity.ok(productPageService.getAllProducts(hubId, status, page, size));
	}

	@GetMapping("/hub/products")
	public ResponseEntity<Page<ProductListResponseDto>> getHubProducts(
		@RequestParam UUID hubId,
		@RequestParam(required = false) String status,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return ResponseEntity.ok(productPageService.getHubProducts(hubId, status, page, size));
	}
}
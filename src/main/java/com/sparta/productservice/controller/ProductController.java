package com.sparta.productservice.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.productservice.dto.request.ProductRequestDto;
import com.sparta.productservice.dto.request.ProductUpdateRequestDto;
import com.sparta.productservice.dto.response.ProductResponseDto;
import com.sparta.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto requestDto) {
		ProductResponseDto response = productService.createProduct(requestDto);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{productId}")
	public ResponseEntity<?> updateProduct(
		@PathVariable UUID productId,
		@RequestBody ProductUpdateRequestDto requestDto
	) {
		productService.updateProduct(productId, requestDto);
		return ResponseEntity.ok().body(
			new Object() {
				public final String message = "업데이트 완료";
			}
		);
	}
}

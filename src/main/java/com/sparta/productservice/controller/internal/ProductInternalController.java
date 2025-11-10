package com.sparta.productservice.controller.internal;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.productservice.dto.response.ProductDetailResponseDto;
import com.sparta.productservice.global.dto.ApiResponse;
import com.sparta.productservice.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/internal/products")
@RequiredArgsConstructor
public class ProductInternalController {

	private final ProductService productService;

	// Feign 전용 엔드포인트
	@Operation(hidden = true) // Swagger 문서에 노출 안 됨
	@GetMapping("/{productId}")
	public ProductDetailResponseDto getProductDetailInternal(@PathVariable UUID productId) {
		return productService.getProductDetail(productId);
	}

	@Operation(hidden = true)
	@PutMapping("/{productId}/decrease-stock")
	public void decreaseStock(@PathVariable UUID productId, @RequestParam int quantity) {
		productService.decreaseStock(productId, quantity);
	}

	@PutMapping("/{productId}/increase-stock")
	public ResponseEntity<ApiResponse<String>> increaseStock(
		@PathVariable UUID productId,
		@RequestParam int quantity) {
		productService.increaseStock(productId, quantity);
		return ResponseEntity.ok(new ApiResponse<>("재고 복구 완료"));
	}
}

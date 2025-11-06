package com.sparta.productservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.querydsl.core.annotations.QueryProjection;
import com.sparta.productservice.entity.ProductEntity;
import com.sparta.productservice.entity.enums.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ProductListResponseDto {

	private UUID productId;
	//private String hubName;
	private UUID hubId;
	private String vendorName;
	private String productName;
	private ProductStatus status;
	private int price;
	private int stock;
	private LocalDateTime createdAt;

	@Builder
	@QueryProjection
	public ProductListResponseDto(UUID productId,
			UUID hubId,
			String vendorName,
			String productName,
			ProductStatus status,
			int price,
			int stock,
			LocalDateTime createdAt) {
		this.productId = productId;
		this.hubId = hubId;
		this.vendorName = vendorName;
		this.productName = productName;
		this.status = status;
		this.price = price;
		this.stock = stock;
		this.createdAt = createdAt;
	}

	public static ProductListResponseDto fromEntity(com.sparta.productservice.entity.ProductEntity product) {
		return new ProductListResponseDto(
			product.getId(),
			product.getHubId(),
			product.getVendorName(),
			product.getProductName(),
			product.getProductStatus(),
			product.getProductPrice(),
			product.getStock(),
			product.getCreatedAt()
		);
	}
}
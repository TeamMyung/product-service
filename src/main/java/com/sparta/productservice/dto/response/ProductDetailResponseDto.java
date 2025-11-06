package com.sparta.productservice.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponseDto {

	private String productName;
	private int price;
	private int stock;
	private String status;
	private String hubName;
	private String vendorName;
	private String description;
	private LocalDateTime createdAt;
	private LocalDateTime approvedAt;
	private LocalDateTime updatedAt;
}

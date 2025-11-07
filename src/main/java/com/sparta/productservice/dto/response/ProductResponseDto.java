package com.sparta.productservice.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponseDto {
	private String productId;
	private String status;
	private String message;
	private LocalDateTime createdAt;
	private LocalDateTime approvedAt;
	private String approvedBy;
}

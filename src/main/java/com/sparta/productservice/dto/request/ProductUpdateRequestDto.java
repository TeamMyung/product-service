package com.sparta.productservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdateRequestDto {

	private String productName;
	private int stock;
	private int productPrice;
	private String hubId;
	private String description;
}


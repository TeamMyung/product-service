package com.sparta.productservice.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequestDto {
	private String name;
	private String vendorId;
	private String vendorName;
	private String hubId;
	private int stock;
	private int productPrice;
	private String description;
}


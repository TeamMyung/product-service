package com.sparta.productservice.entity;

import java.util.UUID;

import com.sparta.productservice.entity.enums.ProductStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "p_product")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 50)
	private String productName;

	@Column(nullable = false)
	private int productPrice;

	@Column(nullable = false)
	private int stock;

	@Column(nullable = false)
	private UUID hubId;

	@Column(nullable = false)
	private UUID vendorId;

	@Column(nullable = false, length = 100)
	private String vendorName;

	@Column(nullable = true, length = 225)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ProductStatus productStatus;

}

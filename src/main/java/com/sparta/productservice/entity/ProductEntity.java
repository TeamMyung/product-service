package com.sparta.productservice.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.sparta.productservice.entity.enums.ProductStatus;
import com.sparta.productservice.global.entity.BaseEntity;

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
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "p_product")
@SQLDelete(sql = "UPDATE p_product SET deleted_at = NOW() WHERE id = ?") // ✅ JPA delete 호출 시 soft delete 수행
@Where(clause = "deleted_at IS NULL")
public class ProductEntity extends BaseEntity {

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

	private LocalDateTime approvedAt;
	private BigInteger approvedBy;

	public void approve(BigInteger userId) {
		this.approvedAt = LocalDateTime.now();
		this.approvedBy = userId;
	}

	public void updateProduct(String productName, int stock, int productPrice, UUID hubId, String description) {
		if (productName != null && !productName.isBlank()) {
			this.productName = productName;
		}
		this.stock = stock;
		this.productPrice = productPrice;
		this.hubId = hubId;
		this.description = description;
	}

	public void updateStatus(ProductStatus status) {
		this.productStatus = status;
	}

	public void softDelete(Long deletedBy) {
		this.setDeletedAt(LocalDateTime.now());
		this.setDeletedBy(deletedBy);
	}

	public void decreaseStock(int quantity) {
		this.stock -= quantity;
	}

	public void increaseStock(int quantity) {
		this.stock += quantity;
	}
}
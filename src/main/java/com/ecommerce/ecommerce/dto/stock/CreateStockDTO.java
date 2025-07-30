package com.ecommerce.ecommerce.dto.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateStockDTO 
{
    @NotNull(message = "The product id is required")
	private Long productId;
	
    @Min(value = 1, message = "The available amount must be greater than zero")
    @NotNull(message = "The available amount is required")
	private Long availableQuantity;
	
	public CreateStockDTO() {
	}
	
	public CreateStockDTO(Long productId, Long availableQuantity) {
		super();
		this.productId = productId;
		this.availableQuantity = availableQuantity;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(Long availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	
	
}

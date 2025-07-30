package com.ecommerce.ecommerce.dto.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateStockDTO
{
    @Min(value = 1, message = "The available quantity must be greater than zero")
    @NotNull(message = "The available quantity is required")
	private Long availableQuantity;

	public UpdateStockDTO() {
	}

	public UpdateStockDTO(Long productId, Long availableQuantity) {
		super();
		this.availableQuantity = availableQuantity;
	}

	public Long getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(Long availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
}

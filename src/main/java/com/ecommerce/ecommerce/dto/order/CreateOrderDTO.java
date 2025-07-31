package com.ecommerce.ecommerce.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateOrderDTO
{
    @Min(value = 1, message = "The quantity must be greater than zero")
    @NotNull(message = "The quantity is required")
    private Integer quantity;
    @Min(value = 1, message = "The product id must be greater than zero")
    @NotNull(message = "The product id is required")
    private Long productId;

    public CreateOrderDTO() {
    }

    public CreateOrderDTO(Integer quantity, Long productId) {
        this.quantity = quantity;
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}

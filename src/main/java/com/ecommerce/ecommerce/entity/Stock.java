package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stocks")
public class Stock
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @Column(name = "available_quantity")
    private Long availableQuantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
        name = "product_id",
        referencedColumnName = "product_id",
        nullable = false
    )
    private Product product;

    public Stock() {
    }

    public Stock(Long id, Long availableQuantity, Product product) {
        this.id = id;
        this.availableQuantity = availableQuantity;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Long availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

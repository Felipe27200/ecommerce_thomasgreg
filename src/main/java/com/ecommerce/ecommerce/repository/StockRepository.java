package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
	@Query("SELECT s FROM Stock s WHERE s.product.id = :productId")
	Stock findByProductId(@Param("productId") Long id);
}

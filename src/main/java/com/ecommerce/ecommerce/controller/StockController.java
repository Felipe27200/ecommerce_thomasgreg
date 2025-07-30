package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.stock.CreateStockDTO;
import com.ecommerce.ecommerce.entity.Product;
import com.ecommerce.ecommerce.entity.Stock;
import com.ecommerce.ecommerce.service.ProductService;
import com.ecommerce.ecommerce.service.StockService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/stocks")
public class StockController
{
    private final ProductService productService;
    private final StockService stockService;

    @Autowired
    public StockController(ProductService productService, StockService stockService) {
        this.productService = productService;
        this.stockService = stockService;
    }

    @PostMapping("/create")
    public ResponseEntity<Stock> createStock(@Valid @RequestBody CreateStockDTO stock)
    {
    	Stock newStock = new Stock();
    	
    	newStock.setAvailableQuantity(stock.getAvailableQuantity());
    	Product product = new Product(stock.getProductId());
    	
    	newStock.setProduct(product);

        return new ResponseEntity<>(this.stockService.save(newStock), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getById(@PathVariable Long id)
    {
        return new ResponseEntity<>(this.stockService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> update(@Valid @RequestBody Stock stock, @PathVariable Long id)
    {
        return new ResponseEntity<>(this.stockService.update(stock, id), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Stock>> getAllStocks()
    {
        return new ResponseEntity<>(this.stockService.findAll(), HttpStatus.OK);
    }

}

package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.stock.CreateStockDTO;
import com.ecommerce.ecommerce.dto.stock.UpdateStockDTO;
import com.ecommerce.ecommerce.entity.Product;
import com.ecommerce.ecommerce.entity.Stock;
import com.ecommerce.ecommerce.enums.audit.Action;
import com.ecommerce.ecommerce.enums.audit.Entity;
import com.ecommerce.ecommerce.service.AuditService;
import com.ecommerce.ecommerce.service.ProductService;
import com.ecommerce.ecommerce.service.StockService;

import com.ecommerce.ecommerce.service.UserService;
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
    private final UserService userService;
    private final AuditService auditService;

    @Autowired
    public StockController(ProductService productService, StockService stockService, UserService userService, AuditService auditService) {
        this.productService = productService;
        this.stockService = stockService;
        this.userService = userService;
        this.auditService = auditService;
    }

    @PostMapping("/create")
    public ResponseEntity<Stock> createStock(@Valid @RequestBody CreateStockDTO stock)
    {
    	Stock newStock = new Stock();
    	
    	newStock.setAvailableQuantity(stock.getAvailableQuantity());
    	Product product = new Product(stock.getProductId());
    	
    	newStock.setProduct(product);

        newStock = this.stockService.save(newStock);

        auditService.create(Action.CREATE, Entity.STOCK, newStock.getId(), userService.findByUsername(userService.getAuthUsername()));

        return new ResponseEntity<>(newStock, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getById(@PathVariable Long id)
    {
        return new ResponseEntity<>(this.stockService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Stock> getByProductId(@PathVariable Long id)
    {
        return new ResponseEntity<>(this.stockService.findByProductId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> update(@Valid @RequestBody UpdateStockDTO stockUpdate, @PathVariable Long id)
    {
        Stock stock = new Stock();

        stock.setAvailableQuantity(stockUpdate.getAvailableQuantity());

        stock =  this.stockService.update(stock, id);

        auditService.create(Action.UPDATE, Entity.STOCK, stock.getId(), userService.findByUsername(userService.getAuthUsername()));

        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Stock>> getAllStocks()
    {
        return new ResponseEntity<>(this.stockService.findAll(), HttpStatus.OK);
    }

}

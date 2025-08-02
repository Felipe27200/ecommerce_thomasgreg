package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.entity.Product;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.enums.audit.Action;
import com.ecommerce.ecommerce.enums.audit.Entity;
import com.ecommerce.ecommerce.error_handling.exception.GeneralException;
import com.ecommerce.ecommerce.response.BasicResponse;
import com.ecommerce.ecommerce.service.AuditService;
import com.ecommerce.ecommerce.service.ProductService;
import com.ecommerce.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/products")
public class ProductController
{
    private final AuditService auditService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public ProductController(AuditService auditService, ProductService productService, UserService userService) {
        this.auditService = auditService;
        this.productService = productService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product)
    {
    	this.validateProduct(product);

        Product newProduct = this.productService.save(product);

        User user = this.userService.findByUsername(this.userService.getAuthUsername());

        this.auditService.create(Action.CREATE, Entity.PRODUCT, newProduct.getId(), user);

        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id)
    {
        return new ResponseEntity<>(this.productService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@Valid @RequestBody Product product, @PathVariable Long id)
    {
    	this.validateProduct(product);

        Product productUpdate = this.productService.update(product, id);

        User user = this.userService.findByUsername(this.userService.getAuthUsername());
        this.auditService.create(Action.UPDATE, Entity.PRODUCT, productUpdate.getId(), user);

        return new ResponseEntity<>(productUpdate, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        return new ResponseEntity<>(this.productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/find-active")
    public ResponseEntity<List<Product>> getAllActiveProducts()
    {
        return new ResponseEntity<>(this.productService.findByIsActive(true), HttpStatus.OK);
    }

    @PutMapping("/change-state/{id}")
    public ResponseEntity<BasicResponse> changeState(@PathVariable Long id)
    {
        Product product = this.productService.findById(id);

        product.setActive(!product.isActive());

        product = this.productService.update(product, id);

        User user = this.userService.findByUsername(this.userService.getAuthUsername());
        this.auditService.create(Action.CHANGE_STATE, Entity.PRODUCT, product.getId(), user);

        BasicResponse basicResponse = new BasicResponse("The product with name '"
                + product.getName() + "' has been changed successfully to "
                + (product.isActive() ? "active" : "inactive") + ".", "successful");

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
    
    private void validateProduct(Product product)
    {
        if (product.getName().trim().isEmpty())
            throw new GeneralException("Product name cannot be empty");

        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            throw new GeneralException("Price cannot be zero");

    }

}

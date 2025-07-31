package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.order.CreateOrderDTO;
import com.ecommerce.ecommerce.entity.Order;
import com.ecommerce.ecommerce.entity.Product;
import com.ecommerce.ecommerce.error_handling.exception.GeneralException;
import com.ecommerce.ecommerce.service.OrderService;
import com.ecommerce.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("${apiPrefix}/orders")
public class OrderController
{
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody CreateOrderDTO createOrderDTO)
    {
        Order newOrder =  new Order();

        Product product = productService.findById(createOrderDTO.getProductId());

        if (!product.isActive())
            throw new GeneralException("This product is not available");

        newOrder.setProduct(product);
        newOrder.setQuantity(createOrderDTO.getQuantity());
        newOrder.setOrderDate(LocalDate.now());

        return new ResponseEntity<>(this.orderService.create(newOrder, this.getAuthUsername()), HttpStatus.OK);
    }

    private String getAuthUsername()
    {
        Authentication authentication = this.getAuthentication();

        return authentication.getName();
    }

    private Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}

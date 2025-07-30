package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.entity.Order;
import com.ecommerce.ecommerce.entity.Product;
import com.ecommerce.ecommerce.entity.Stock;
import com.ecommerce.ecommerce.entity.User;
import com.ecommerce.ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderService
{
    private UserService userService;
    private StockService stockService;
    private OrderRepository orderRepository;

    @Autowired
    public OrderService(UserService userService, StockService stockService, OrderRepository orderRepository) {
        this.userService = userService;
        this.stockService = stockService;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(Order order, String username)
    {
        if (order.getQuantity() <= 0)
            throw new IllegalArgumentException("Order quantity must be greater than 0");
        if (order.getOrderDate().isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Order date cannot be before current date");

        Stock stock = this.stockService.findByProductId(order.getProduct().getId());

        if (stock.getAvailableQuantity() < order.getQuantity())
            throw new IllegalArgumentException("There is not enough product for your order");

        User user = this.userService.findByUsername(username);

        order.setUser(user);

        return this.orderRepository.save(order);
    }
}

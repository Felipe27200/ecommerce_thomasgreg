package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.entity.Product;
import com.ecommerce.ecommerce.entity.Stock;
import com.ecommerce.ecommerce.error_handling.exception.GeneralException;
import com.ecommerce.ecommerce.error_handling.exception.NotFoundException;
import com.ecommerce.ecommerce.repository.StockRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService
{
    private StockRepository stockRepository;
    private ProductService productService;

    @Autowired
    public void setProductRepository(StockRepository stockRepository, ProductService productService)
    {
        this.stockRepository = stockRepository;
        this.productService = productService;
    }

    @Transactional
    public Stock save(Stock newStock)
    {
    	if (newStock.getAvailableQuantity() <= 0)
    		throw new GeneralException("The available quantity must be greater than zero");
    	
    	Product product = this.productService.findById(newStock.getProduct().getId());
    	
    	Stock oldStock = this.stockRepository.findByProductId(product.getId());
    	
    	if (oldStock != null)
    		throw new GeneralException("There is a Stock already created for the Product '" + oldStock.getProduct().getName() + "'");
    	
    	newStock.setProduct(product);

        return stockRepository.save(newStock);
    }

    public Stock findById(Long id)
    {
        if (id == null || id <= 0L)
            throw new GeneralException("The product id must be greater than zero");

        Optional<Stock> stock = this.stockRepository.findById(id);
        
        if (stock.isEmpty())
            throw new NotFoundException("Stock with id " + id + " not found");

        return stock.get();
    }

    public List<Stock> findAll()
    {
        return this.stockRepository.findAll();
    }

    @Transactional
    public Stock update (Stock stock, Long id)
    {
        Stock oldStock = this.findById(id);
        
        if (stock.getAvailableQuantity() == null || stock.getAvailableQuantity() <= 0)
        	throw new GeneralException("The available quantity must be greater than zero");

        if (oldStock == null)
            throw new NotFoundException("Stock with id " + id + " not found");
        
       Product product = this.productService.findById(oldStock.getProduct().getId());
       
       if (!product.getId().equals(oldStock.getProduct().getId()))
    	   throw new GeneralException("The stock does not belong to product '" + product.getName() + "'");

        oldStock.setAvailableQuantity(stock.getAvailableQuantity());

        return stockRepository.save(oldStock);
    }

//    @Transactional
//    public String deleteById(Long id)
//    {
//        Stock oldStock = this.findById(id);
//
//        if (oldStock == null)
//            throw new NotFoundException("Stock with id " + id + " not found");
//
//        
//        productRepository.delete(oldStock);
//
//        return oldStock.getName();
//    }
}

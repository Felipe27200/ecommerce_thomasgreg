package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.entity.Product;
import com.ecommerce.ecommerce.error_handling.exception.GeneralException;
import com.ecommerce.ecommerce.error_handling.exception.NotFoundException;
import com.ecommerce.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService
{
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product save(Product product)
    {
        Product oldProduct = this.productRepository.findByName(product.getName());

        if (oldProduct != null)
            throw new DataIntegrityViolationException("Product with name " + product.getName() + " already exists");

        return productRepository.save(product);
    }

    public Product findById(Long id)
    {
        if (id == null || id <= 0L)
            throw new GeneralException("The product id must be greater than zero");

        Optional<Product> product = this.productRepository.findById(id);

        if (product.isEmpty())
            throw new NotFoundException("Product with id " + id + " not found");

        return product.get();
    }

    public List<Product> findAll()
    {
        return this.productRepository.findAll();
    }

    @Transactional
    public Product update (Product product, Long id)
    {
        Product oldProduct = this.findById(id);

        if (oldProduct == null)
            throw new NotFoundException("Product with id " + id + " not found");

        Product checkName = this.productRepository.findByName(product.getName());

        if (checkName != null && !checkName.getId().equals(oldProduct.getId()))
            throw new GeneralException("There is another Product with name '" + product.getName() + "'");

        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());

        return productRepository.save(oldProduct);
    }

    @Transactional
    public String deleteById(Long id)
    {
        Product oldProduct = this.findById(id);

        if (oldProduct == null)
            throw new NotFoundException("Product with id " + id + " not found");

        productRepository.delete(oldProduct);

        return oldProduct.getName();
    }

    public Product findByName(String name)
    {
        return this.productRepository.findByName(name);
    }

    public List<Product> findByIsActive(boolean isActive)
    {
        return this.productRepository.findByIsActive(isActive);
    }
}

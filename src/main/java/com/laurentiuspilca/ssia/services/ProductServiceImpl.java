package com.laurentiuspilca.ssia.services;

import com.laurentiuspilca.ssia.entities.Product;
import com.laurentiuspilca.ssia.reposetories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

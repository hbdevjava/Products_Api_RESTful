package com.hbdev.springbootproducts.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.hbdev.springbootproducts.models.ProductModel;
import com.hbdev.springbootproducts.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	@Transactional
	public ProductModel save(ProductModel productModel) {
		return productRepository.save(productModel);
	}

	public List<ProductModel> findAll() {
		return productRepository.findAll();
	}

	public Optional<ProductModel> findById(UUID id) {
		return productRepository.findById(id);
	}
	
	
	@Transactional
	public void deleteById(UUID id) {
		productRepository.deleteById(id);
	}

}

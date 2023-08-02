package com.hbdev.springbootproducts.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbdev.springbootproducts.models.ProductModel;
import com.hbdev.springbootproducts.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	ProductService productService;

	@PostMapping
	public Object saveProduct() {

	}

	@GetMapping
	public List<ProductModel> getAllParkingSpots() {

	}

	@GetMapping("/{id}")
	public Object getOneProduct() {
	}
	
	@PutMapping("/{id}")
	public Object saveProduct() {
		
	}
	
	@DeleteMapping("/{id}")
	public Object deleteProdu() {
		
	}
	
	
	
}

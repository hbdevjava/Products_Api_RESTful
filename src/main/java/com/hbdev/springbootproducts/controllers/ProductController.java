package com.hbdev.springbootproducts.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbdev.springbootproducts.RecordDto.ProductRecordDto;
import com.hbdev.springbootproducts.models.ProductModel;
import com.hbdev.springbootproducts.repositories.ProductRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import jakarta.validation.Valid;

/*
 * import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
 * import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
 *
 * PROBLEMA PARA IMPORTAR 
 * */

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	@PostMapping
	public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
		var productModel = new ProductModel();
		BeanUtils.copyProperties(productRecordDto, productModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
	}

	@GetMapping
	public ResponseEntity<List<ProductModel>> getAllProduct() {
		List<ProductModel> productList = productRepository.findAll();
		if (!productList.isEmpty()) {
			for (ProductModel product : productList) {
				// -> PRA CADA PRODUTO ENCONTRADO EM List<ProductModel> OBTINHA O ID
				// product.getIdProduct() E ATRIBUA UMA POR UM A UUID id
				UUID id = product.getIdProduct();
				product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(productList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
		Optional<ProductModel> optional = productRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		}
		
		optional.get().add(linkTo(methodOn(ProductController.class).getAllProduct()).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(optional.get());

	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> upDateProduct(@PathVariable(value = "id") UUID id,
			@RequestBody @Valid ProductRecordDto productRecordDto) {
		Optional<ProductModel> optional = productRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		}
		var productModel = optional.get();
		BeanUtils.copyProperties(productRecordDto, productModel);
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
		Optional<ProductModel> optional = productRepository.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		}
		productRepository.delete(optional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully...");
	}

}

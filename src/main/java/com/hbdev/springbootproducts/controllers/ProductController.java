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
import com.hbdev.springbootproducts.services.ProductService;

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
	ProductService productService;

	@PostMapping
	public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
		var productModel = new ProductModel();
		BeanUtils.copyProperties(productRecordDto, productModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productModel));
	}

	@GetMapping
	public ResponseEntity<List<ProductModel>> getAllProduct() {
		List<ProductModel> productList = productService.findAll();
		if (!productList.isEmpty()) {
			for (ProductModel product : productList) {
				//PRECISO PASSAR POR CADA UM DELES PRA CRIAR UM LINK HATEOS DE FORMA DINAMICA
				// -> PRA CADA PRODUTO ENCONTRADO EM List<ProductModel> OBTINHA O ID
				// product.getIdProduct() E ATRIBUA UMA POR UM A UUID id
				UUID id = product.getIdProduct();
				//CRIA O LINK DE FORMA DINAMICA
				product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withRel("Link do Produto"));
				//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
				//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
				//linkTo e o methodOn FAZEM PARTE DO PROJETO WebMvcLinkBuilder ELES QUE VÃO CONSTRUIR 
				//ESSA URI PARA SATISFAZER O PRINCIPIO HATEOAS
				//withRel("Link do Produto"))
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(productList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
		Optional<ProductModel> optional = productService.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		}
		//get()-> EXTRAI O DADO DE DENTRO DA VARIAVEL optional
		optional.get().add(linkTo(methodOn(ProductController.class).getAllProduct()).withRel("Link para Lista de Produtos"));
		return ResponseEntity.status(HttpStatus.OK).body(optional.get());

	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> upDateProduct(@PathVariable(value = "id") UUID id,
			@RequestBody @Valid ProductRecordDto productRecordDto) {
		Optional<ProductModel> optional = productService.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		}
		var productModel = optional.get();
		BeanUtils.copyProperties(productRecordDto, productModel);
		return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
		Optional<ProductModel> optional = productService.findById(id);
		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		}
		productService.deleteById(id);            
		return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully...");
	}

}

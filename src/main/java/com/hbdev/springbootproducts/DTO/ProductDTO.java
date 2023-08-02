package com.hbdev.springbootproducts.DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;



public class ProductDTO implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private UUID idProduct;
	private String name;
	private BigDecimal value;
	
	public ProductDTO() {
	
	}

	public ProductDTO(UUID idProduct, String name, BigDecimal value) {
		
		this.idProduct = idProduct;
		this.name = name;
		this.value = value;
	}

	public UUID getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(UUID idProduct) {
		this.idProduct = idProduct;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idProduct);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDTO other = (ProductDTO) obj;
		return Objects.equals(idProduct, other.idProduct);
	}
}

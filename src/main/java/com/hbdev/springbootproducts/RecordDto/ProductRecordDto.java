package com.hbdev.springbootproducts.RecordDto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal value) {

	// -> DEFAULT ELE JA TRAS O GET, CONTRUTORES, HASHCOD, TOSTRING
	// -> SAO IMUTAVEIS E PRIVADOS

	// -> NÃO POSSUEM METODOS SET SOMENTE GET (IMUTAVEIS)

}
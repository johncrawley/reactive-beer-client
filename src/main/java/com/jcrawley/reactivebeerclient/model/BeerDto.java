package com.jcrawley.reactivebeerclient.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BeerDto {
	
	// need to add spring-boot-starter-validation to pom.xml
	@Null
	private UUID id;
	@NotBlank
	private String beerName;
	@NotBlank
	private String beerStyle;
	private String upc;
	private BigDecimal price;
	private int quantityOnHand;
	private int version;
	private OffsetDateTime createdDate;
	private OffsetDateTime lastModifiedDate;

}

package com.babsom.spring7restmvc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Beer {

	private UUID          oid;
	private Integer       version;
	private String        name;
	private String        upc;
	private BeerStyle		 style;
	private Integer       quantityOnHand;
	private BigDecimal    price;
	private LocalDateTime created;
	private LocalDateTime updated;
}

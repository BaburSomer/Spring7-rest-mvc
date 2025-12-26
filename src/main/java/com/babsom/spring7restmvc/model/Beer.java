package com.babsom.spring7restmvc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Beer.BeerBuilder.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beer {

	@JsonProperty("oid")
	private UUID          oid;
	@JsonProperty("version")
	private Integer       version;
	@JsonProperty("name")
	private String        name;
	@JsonProperty("upc")
	private String        upc;
	@JsonProperty("style")
	private BeerStyle     style;
	@JsonProperty("quantityOnHand")
	private Integer       quantityOnHand;
	@JsonProperty("price")
	private BigDecimal    price;
	@JsonProperty("created")
	private LocalDateTime created;
	@JsonProperty("updated")
	private LocalDateTime updated;

	@Override
	public Beer clone() throws CloneNotSupportedException {
		Beer cloned = new Beer();

		cloned.setCreated(created);
		cloned.setName(name);
		cloned.setOid(oid);
		cloned.setPrice(price);
		cloned.setQuantityOnHand(quantityOnHand);
		cloned.setStyle(style);
		cloned.setUpc(upc);
		cloned.setUpdated(updated);
		cloned.setVersion(version);
		return cloned;
	}
}

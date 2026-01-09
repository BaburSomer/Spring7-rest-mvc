package com.babsom.spring7restmvc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = BeerDTO.BeerDTOBuilder.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDTO {

	@JsonProperty("oid")
	private UUID          oid;

	@JsonProperty("version")
	private Integer       version;

	@JsonProperty("name")
	@NotNull
	@NotBlank
	private String        name;

	@JsonProperty("upc")
	@NotNull
	@NotBlank
	private String        upc;

	@JsonProperty("style")
	@NotNull
	private BeerStyle     style;

	@JsonProperty("quantityOnHand")
	private Integer       quantityOnHand;

	@JsonProperty("price")
	@NotNull
	private BigDecimal    price;

	@JsonProperty("created")
	private LocalDateTime created;
	
	@JsonProperty("updated")
	private LocalDateTime updated;

	@Override
	public BeerDTO clone() throws CloneNotSupportedException {
		BeerDTO cloned = new BeerDTO();

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

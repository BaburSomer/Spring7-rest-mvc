package com.babsom.spring7restmvc.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.babsom.spring7restmvc.model.BeerStyle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Beer {
	@Id
	@GeneratedValue(generator = "UUID")
	@UuidGenerator
	@Column(name = "oid", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
	private UUID          oid;

	@Version
	private Integer       version;

	private String        name;

	private String        upc;

	private BeerStyle     style;

	private Integer       quantityOnHand;

	private BigDecimal    price;

	private LocalDateTime created;

	private LocalDateTime updated;
}
package com.babsom.spring7restmvc.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.babsom.spring7restmvc.model.BeerStyle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Entity(name = "beers")
public class Beer {
	@Id
	@GeneratedValue(generator = "UUID")
	@UuidGenerator
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(name = "oid", length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
	private UUID          oid;

	@Version
	private Integer       version;

	@NotNull
	@NotBlank
	@Size(max = 50)
	@Column(length = 50)
	private String        name;

	@NotNull
	@NotBlank
	@Size(max = 255)
	private String        upc;

	@NotNull
	private BeerStyle     style;

	private Integer       quantityOnHand;

	@NotNull
	private BigDecimal    price;

	private LocalDateTime created;

	private LocalDateTime updated;
}
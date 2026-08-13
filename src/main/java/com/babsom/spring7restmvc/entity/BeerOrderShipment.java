package com.babsom.spring7restmvc.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "beer_order_shipments")
public class BeerOrderShipment {

	@Id
	@GeneratedValue(generator = "UUID")
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
	private UUID oid;

	@Column(length = 50, columnDefinition = "varchar(50)", updatable = true, nullable = true)
	private String trackingNumber;

	@OneToOne
	@JoinColumn(name = "bo_oid", referencedColumnName = "oid", nullable = false, unique = true)
	private BeerOrder order;

	@Version
	private Long version;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime created;

	@UpdateTimestamp
	private LocalDateTime modified;

	public boolean isNew() {
		return this.oid == null;
	}
}
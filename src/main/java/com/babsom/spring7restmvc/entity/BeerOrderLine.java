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
import jakarta.persistence.ManyToOne;
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
@Entity(name = "beer_order_lines")
public class BeerOrderLine {
	@Id
	@GeneratedValue(generator="UUID")
//	@GenericGenerator(name="UUID", strategy="org.hibernate.id.UUIDGenerator")
	@UuidGenerator(style = UuidGenerator.Style.RANDOM)
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(length=36, columnDefinition = "varchar(36)", updatable=false, nullable=false)
	private UUID oid;
	
	@Builder.Default
	private Integer quantity = 0;
	
	@Builder.Default
	private Integer allocatedQuantity = 0;
	
   @ManyToOne
   private BeerOrder beerOrder;

   @ManyToOne
   private Beer beer;

	@Version
	private Long       version;
	
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime created;
	
	@UpdateTimestamp
	private LocalDateTime modified;

	public boolean isNew() {
		return this.oid == null;
	}
}
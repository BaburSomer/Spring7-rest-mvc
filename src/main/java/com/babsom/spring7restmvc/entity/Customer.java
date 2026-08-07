package com.babsom.spring7restmvc.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
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
@EnableJpaRepositories
@Entity(name = "customers")
public class Customer {

	@Id
	@GeneratedValue(generator = "UUID")
	@UuidGenerator
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(name = "oid", length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
	private UUID          oid;

	private String        firstName;

	private String        lastName;

	@Size(max = 255)
	@Column(name= "email", length = 255)	
	private String        eMail;
	
	@Version
	private Integer       version;

	private LocalDateTime created;

	private LocalDateTime modified;
	
	@OneToMany(mappedBy = "customer")
	private Set<BeerOrder> orders;
	
	public Set<BeerOrder> getOrders() {
		if (this.orders == null) {
			this.orders = new HashSet<>();
		}
		return this.orders;
	}
}
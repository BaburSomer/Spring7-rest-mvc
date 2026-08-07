package com.babsom.spring7restmvc.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Entity(name = "categories")
public class Category {
	@Id
	@GeneratedValue(generator = "UUID")
	@UuidGenerator
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(name = "oid", length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
	private UUID          oid;

	@NotNull
	@NotBlank
	@Size(max = 50)
	@Column(length = 50)
	private String        name;
	
	@ManyToMany
	@JoinTable(name="beers_categories", joinColumns = @JoinColumn(name="category_oid"), inverseJoinColumns = @JoinColumn(name="beer_oid"))
	private Set<Beer> beers;

	public Set<Beer> getBeers() {
		if (this.beers == null) {
			this.beers = new HashSet<>();
		}
		return this.beers;
	}
	
	@Version
	private Integer       version;

	@CreationTimestamp
	private LocalDateTime created;
	
	@UpdateTimestamp
	private LocalDateTime modified;
}
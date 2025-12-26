package com.babsom.spring7restmvc.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//Beer de Json - Annotationlar ile bir çözüm var. Ben de her ikisi de sorunsun çalışıyor
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	private UUID          oid;
	private String        firstName;
	private String        lastName;
	private Integer       version;
	private LocalDateTime created;
	private LocalDateTime updated;

	@Override
	public Customer clone() throws CloneNotSupportedException {
		Customer cloned = new Customer();
		cloned.setCreated(created);
		cloned.setFirstName(firstName);
		cloned.setLastName(lastName);
		cloned.setOid(oid);
		cloned.setUpdated(updated);
		cloned.setVersion(version);
		return cloned;
	}
}
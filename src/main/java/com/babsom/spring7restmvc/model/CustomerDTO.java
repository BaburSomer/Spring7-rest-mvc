package com.babsom.spring7restmvc.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//BeerDTO de Json - Annotationlar ile bir çözüm var. Ben de her ikisi de sorunsuz çalışıyor
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private UUID          oid;
	private String        firstName;
	private String        lastName;
	private String        eMail;
	private Integer       version;
	private LocalDateTime created;
	private LocalDateTime updated;

	@Override
	public CustomerDTO clone() throws CloneNotSupportedException {
		CustomerDTO cloned = new CustomerDTO();
		cloned.setOid(oid);
		cloned.setFirstName(firstName);
		cloned.setLastName(lastName);
		cloned.setEMail(eMail);
		cloned.setVersion(version);
		cloned.setCreated(created);
		cloned.setUpdated(updated);
		return cloned;
	}
}
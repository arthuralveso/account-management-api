package com.alves.arthur.desafio.contasbancariasapi.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
@Table(name="persons")
public class Person {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long personId;
	
	private String name;
	
	private String cpf;
	
	private Date birthDate;
	
	@OneToMany(mappedBy = "personId")
	private List<Account> accounts;
}

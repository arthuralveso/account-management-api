package com.alves.arthur.desafio.contasbancariasapi.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
@Table(name="persons")
public class Person {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long personId;
	
	private String name;
	
	private String cpf;
	
	private Date birthDate;
	
}

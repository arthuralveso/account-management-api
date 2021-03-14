package com.alves.arthur.desafio.contasbancariasapi.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@Table(name = "persons")
@Entity
public class Person {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPessoa;
	
	private String name;
	
	private String cpf;
	
	private Date birthDate;
	
}

package com.alves.arthur.desafio.contasbancariasapi.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@Table(name = "accounts")
@Entity
public class Account implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;
	
	private Long PersonId;
	
	private float accounteBalance;
	
	private float dailyWithdrawalLimit;
	
	private boolean isActive;
	
	private Integer accounteType;
	
	private Date createdDate;
	
	
	
}

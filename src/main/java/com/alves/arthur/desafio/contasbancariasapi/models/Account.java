package com.alves.arthur.desafio.contasbancariasapi.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="accounts")
public class Account implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;
	
	private Long personId;
	
	private float accounteBalance;
	
	private float dailyWithdrawalLimit;
	
	private boolean active;
	
	private Integer accounteType;
	
	private Date createdDate;
	
	
	
}

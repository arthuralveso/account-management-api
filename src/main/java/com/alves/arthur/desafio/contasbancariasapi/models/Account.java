package com.alves.arthur.desafio.contasbancariasapi.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	private double accountBalance;
	
	private double dailyWithdrawalLimit;
	
	private boolean active;
	
	private Integer accountType;
	
	private Date createdDate;
	
	private Long personId;
	
	@OneToMany(mappedBy = "accountId")	
	private List<Transaction> transactions;
	
	
}

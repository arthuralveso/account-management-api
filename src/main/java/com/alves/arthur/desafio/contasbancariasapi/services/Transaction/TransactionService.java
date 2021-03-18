package com.alves.arthur.desafio.contasbancariasapi.services.Transaction;

import java.util.List;

import com.alves.arthur.desafio.contasbancariasapi.models.Transaction;

public interface TransactionService {

	public Transaction create(Transaction transaction) throws Exception;
	public List<Transaction> findAllTransactions(); 
	
}

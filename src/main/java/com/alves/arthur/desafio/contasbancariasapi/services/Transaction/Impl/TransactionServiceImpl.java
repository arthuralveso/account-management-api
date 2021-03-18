package com.alves.arthur.desafio.contasbancariasapi.services.Transaction.Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.alves.arthur.desafio.contasbancariasapi.exceptions.AccountDoesNotExistsException;
import com.alves.arthur.desafio.contasbancariasapi.models.Account;
import com.alves.arthur.desafio.contasbancariasapi.models.Transaction;
import com.alves.arthur.desafio.contasbancariasapi.repositories.AccountRepository;
import com.alves.arthur.desafio.contasbancariasapi.repositories.TransactionRepository;
import com.alves.arthur.desafio.contasbancariasapi.services.Transaction.TransactionService;

@Service
@Validated
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	@Transactional
	@Override
	public Transaction create(Transaction transaction) throws Exception {
		accountRepository.findById(transaction.getAccountId()).orElseThrow(() -> 
		new AccountDoesNotExistsException("Account does not exists"));
	
		transaction.setTransactionDate(new Date(System.currentTimeMillis()));
		
		return transactionRepository.save(transaction);

	}


	@Override
	public List<Transaction> findAllTransactions() {
		return transactionRepository.findAll();
	}
	
	
}

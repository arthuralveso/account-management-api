package com.alves.arthur.desafio.contasbancariasapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.alves.arthur.desafio.contasbancariasapi.models.Account;
import com.alves.arthur.desafio.contasbancariasapi.repositories.AccountRepository;

@Service
@Validated
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	
	@Override
	public List<Account> listAllAccounts() {
		return accountRepository.findAll();
	}
	
	@Transactional
	@Override
	public Account create(Account account) throws Exception {
		
		Account existentAccount = accountRepository.findByPersonId(account.getPersonId());
		
		if(existentAccount != null) {			
			throw new Exception("Account already registered for a person");
		}
		
		return accountRepository.save(account);
	}
}

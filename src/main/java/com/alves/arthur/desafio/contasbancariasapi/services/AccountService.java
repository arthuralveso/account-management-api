package com.alves.arthur.desafio.contasbancariasapi.services;

import java.util.List;

import com.alves.arthur.desafio.contasbancariasapi.models.Account;

public interface AccountService {

	
	public List<Account> listAllAccounts();
	public Account create(Account account) throws Exception;
}

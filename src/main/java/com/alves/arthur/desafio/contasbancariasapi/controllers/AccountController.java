package com.alves.arthur.desafio.contasbancariasapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alves.arthur.desafio.contasbancariasapi.models.Account;
import com.alves.arthur.desafio.contasbancariasapi.services.AccountService;

@RestController
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/contas", method = RequestMethod.GET)
	public ResponseEntity<List<Account>> listAccounts() {
		
		List<Account> accountList =  accountService.listAllAccounts();
		
		
		return new ResponseEntity<List<Account>>(accountList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contas", method = RequestMethod.POST)
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		
		try {
			Account newAccount = accountService.create(account);
			
			return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
			
		} 
	}
	
}

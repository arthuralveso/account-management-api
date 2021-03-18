package com.alves.arthur.desafio.contasbancariasapi.controllers.Account;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alves.arthur.desafio.contasbancariasapi.exceptions.AccountDoesNotExistsException;
import com.alves.arthur.desafio.contasbancariasapi.exceptions.OperationNotAllowedException;
import com.alves.arthur.desafio.contasbancariasapi.models.Account;
import com.alves.arthur.desafio.contasbancariasapi.models.Transaction;
import com.alves.arthur.desafio.contasbancariasapi.models.dtos.DateInterval;
import com.alves.arthur.desafio.contasbancariasapi.services.Account.AccountService;
import com.alves.arthur.desafio.contasbancariasapi.services.Transaction.TransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "API Account Menegement")
@CrossOrigin(origins = "*")
@RequestMapping(value= "/api")
public class AccountController {

	AccountService accountService;
	TransactionService transactionService;

	@Autowired
	public AccountController(AccountService accountService, TransactionService transactionService) {
		this.accountService = accountService;
		this.transactionService = transactionService;
	}

	@GetMapping("/contas")
	@ApiOperation(value = "Retorna uma lista de contas")
	public ResponseEntity<List<Account>> listAccounts() {

		List<Account> accountList = accountService.listAllAccounts();

		return new ResponseEntity<List<Account>>(accountList, HttpStatus.OK);
	}

	@PostMapping("/contas")
	@ApiOperation(value = "Cria uma conta e retorna as informações da conta criada")
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {

		try {
			Account newAccount = accountService.create(account);

			return new ResponseEntity<Account>(newAccount, HttpStatus.CREATED);

		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);

		}
	}

	@PatchMapping("/contas/{id}/bloquear-conta")
	@ApiOperation(value = "Bloqueia uma conta a partir do ID passado nos params")
	public ResponseEntity<Account> blocksAccountById(@PathVariable Long id) {

		try {
			Account account = accountService.blocksAccount(id);

			return new ResponseEntity<Account>(account, HttpStatus.OK);

		} catch (AccountDoesNotExistsException e) {
			e.printStackTrace();
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		}

		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/contas/{id}/saldo")
	@ApiOperation(value = "Retorna o saldo de uma conta")
	public ResponseEntity<Double> deposit(@PathVariable Long id) {

		try {

			double balance = accountService.showBalance(id);

			return new ResponseEntity<Double>(balance, HttpStatus.OK);

		} catch (AccountDoesNotExistsException e) {
			e.printStackTrace();
			return new ResponseEntity<Double>(HttpStatus.NOT_FOUND);
		}

		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Double>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/contas/{id}/transacoes")
	@ApiOperation(value = "Retorna uma lista transações de uma conta")
	public ResponseEntity<List<Transaction>> listTransactions(@PathVariable Long id) {

		try {

			List<Transaction> transactions = accountService.listAllTransactions(id);

			return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);

		} catch (AccountDoesNotExistsException e) {
			e.printStackTrace();
			return new ResponseEntity<List<Transaction>>(HttpStatus.NOT_FOUND);
		}

		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/contas/{id}/transacoes-por-data")
	@ApiOperation(value = "Retorna uma lista transações de uma conta para um periodo informado")
	public ResponseEntity<List<Transaction>> listTransactionsByDate(@PathVariable Long id,
			@RequestBody DateInterval dateInterval) {

		try {

			List<Transaction> transactions = accountService.listTransactionsByDate(id, dateInterval.getCurrentDate(),
					dateInterval.getPastDate());

			return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Transaction>>(HttpStatus.NOT_FOUND);
		}

	}

	@PatchMapping("/contas/{id}/depositar")
	@ApiOperation(value = "Acrescenta um valor passado no body para o saldo da conta - Deposita o valor passado numa conta")
	public ResponseEntity<Account> depositValueOnAccount(@PathVariable Long id, @RequestBody Transaction transaction) {

		try {
			Account account = accountService.createDeposit(id, transaction);
			
			transaction.setAccountId(id);
			
			transactionService.create(transaction);

			return new ResponseEntity<Account>(account, HttpStatus.OK);

		} catch (AccountDoesNotExistsException e) {
			e.printStackTrace();
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		} catch (OperationNotAllowedException e) {
			e.printStackTrace();
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping("/contas/{id}/sacar")
	@ApiOperation(value = "Realiza um saque em uma conta - retira do saldo da conta um valor conforme passado pelo body")
	public ResponseEntity<Account> withdrawValueOnAccount(@PathVariable Long id, @RequestBody Transaction transaction) {

		try {
			Account account = accountService.createWithdraw(id, transaction);
			
			transaction.setAccountId(id);
			
			transactionService.create(transaction);

			return new ResponseEntity<Account>(account, HttpStatus.OK);

		} catch (AccountDoesNotExistsException e) {
			e.printStackTrace();
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		} catch (OperationNotAllowedException e) {
			e.printStackTrace();
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Account>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

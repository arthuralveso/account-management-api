package com.alves.arthur.desafio.contasbancariasapi.controllers.Account;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alves.arthur.desafio.contasbancariasapi.exceptions.AccountDoesNotExistsException;
import com.alves.arthur.desafio.contasbancariasapi.exceptions.OperationNotAllowedException;
import com.alves.arthur.desafio.contasbancariasapi.models.Account;
import com.alves.arthur.desafio.contasbancariasapi.models.DateInterval;
import com.alves.arthur.desafio.contasbancariasapi.models.Transaction;
import com.alves.arthur.desafio.contasbancariasapi.services.Account.AccountService;
import com.alves.arthur.desafio.contasbancariasapi.services.Transaction.TransactionService;

@RestController
public class AccountController {

	AccountService accountService;
	TransactionService transactionService;

	@Autowired
	public AccountController(AccountService accountService, TransactionService transactionService) {
		this.accountService = accountService;
		this.transactionService = transactionService;
	}

	@RequestMapping(value = "/contas", method = RequestMethod.GET)
	public ResponseEntity<List<Account>> listAccounts() {

		List<Account> accountList = accountService.listAllAccounts();

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

	@RequestMapping(value = "/contas/{id}/bloquear-conta", method = RequestMethod.PATCH)
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

	@RequestMapping(value = "/contas/{id}/saldo", method = RequestMethod.GET)
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

	@RequestMapping(value = "/contas/{id}/transacoes", method = RequestMethod.GET)
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

	@RequestMapping(value = "/contas/{id}/transacoes-por-data", method = RequestMethod.GET)
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

	@RequestMapping(value = "/contas/{id}/depositar", method = RequestMethod.PATCH)
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
	
	@RequestMapping(value = "/contas/{id}/sacar", method = RequestMethod.PATCH)
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

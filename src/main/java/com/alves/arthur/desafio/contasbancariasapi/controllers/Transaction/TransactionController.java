package com.alves.arthur.desafio.contasbancariasapi.controllers.Transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alves.arthur.desafio.contasbancariasapi.exceptions.AccountDoesNotExistsException;
import com.alves.arthur.desafio.contasbancariasapi.models.Transaction;
import com.alves.arthur.desafio.contasbancariasapi.services.Account.AccountService;
import com.alves.arthur.desafio.contasbancariasapi.services.Transaction.TransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "API Account Menegement")
@CrossOrigin(origins = "*")
@RequestMapping(value= "/api")
public class TransactionController {

	TransactionService transactionService;
	AccountService accountService;

	@Autowired
	public TransactionController(TransactionService transactionService, AccountService accountService) {
		this.transactionService = transactionService;
		this.accountService = accountService;
	}

	@GetMapping("/transacoes")
	@ApiOperation(value = "Retorna uma lista de transações")
	public ResponseEntity<List<Transaction>> listTransactions() {

		List<Transaction> transactions = transactionService.findAllTransactions();

		return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);

	}

	@PostMapping("/transacoes")
	@ApiOperation(value = "Cria uma transação contas")
	public ResponseEntity<Transaction> createTransactions(@RequestBody Transaction transaction) {

		try {

			Transaction newTransaction = transactionService.create(transaction);

			return new ResponseEntity<Transaction>(newTransaction, HttpStatus.CREATED);

		} catch (AccountDoesNotExistsException e) {
			e.printStackTrace();
			return new ResponseEntity<Transaction>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<Transaction>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}

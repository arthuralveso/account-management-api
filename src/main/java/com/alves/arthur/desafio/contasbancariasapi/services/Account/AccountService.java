package com.alves.arthur.desafio.contasbancariasapi.services.Account;

import java.util.Date;
import java.util.List;

import com.alves.arthur.desafio.contasbancariasapi.models.Account;
import com.alves.arthur.desafio.contasbancariasapi.models.Transaction;

public interface AccountService {

	
	public List<Account> listAllAccounts();
	public Account create(Account account) throws Exception;
	public Account blocksAccount(Long id) throws Exception;
	public Account save(Account account) throws Exception;
	public Account findById(Long id) throws Exception;
	public double showBalance(Long id) throws Exception;
	public List<Transaction> listAllTransactions(Long id) throws Exception;
	public List<Transaction> listTransactionsByDate(Long id, Date currentDate, Date pastDate) throws Exception;
	public Account createDeposit(Long id, Transaction transaction) throws Exception;
	public Account createWithdraw (Long id, Transaction transaction) throws Exception;
}

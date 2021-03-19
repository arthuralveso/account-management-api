package com.alves.arthur.desafio.contasbancariasapi.services.Account.Impl;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.alves.arthur.desafio.contasbancariasapi.exceptions.AccountDoesNotExistsException;
import com.alves.arthur.desafio.contasbancariasapi.exceptions.OperationNotAllowedException;
import com.alves.arthur.desafio.contasbancariasapi.models.Account;
import com.alves.arthur.desafio.contasbancariasapi.models.Transaction;
import com.alves.arthur.desafio.contasbancariasapi.repositories.AccountRepository;
import com.alves.arthur.desafio.contasbancariasapi.services.Account.AccountService;

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

		Account existingAccount = accountRepository.findByPersonId(account.getPersonId());

		if(existingAccount != null && existingAccount.getAccountType() == account.getAccountType()) {			
			throw new Exception("Account already registered for a person");
		}

		return accountRepository.save(account);
	}

	@Transactional
	@Override
	public Account blocksAccount(Long id) throws AccountDoesNotExistsException {
		Account existingAccount = accountRepository.findById(id)
				.orElseThrow(() -> new AccountDoesNotExistsException("Account does not exists"));

		existingAccount.setActive(false);

		return accountRepository.save(existingAccount);

	}

	@Transactional
	@Override
	public Account save(Account account) throws AccountDoesNotExistsException {
		return accountRepository.save(account);
	}

	@Override
	public Account findById(Long id) throws AccountDoesNotExistsException {
		return accountRepository.findById(id)
				.orElseThrow(() -> new AccountDoesNotExistsException("Account does not exists"));
	}

	@Override
	public double showBalance(Long id) throws AccountDoesNotExistsException {
		Account existingAccount = accountRepository.findById(id)
				.orElseThrow(() -> new AccountDoesNotExistsException("Account does not exists"));

		return existingAccount.getAccountBalance();
	}

	@Override
	public List<Transaction> listAllTransactions(Long id) throws AccountDoesNotExistsException {

		Account existingAccount = accountRepository.findById(id)
				.orElseThrow(() -> new AccountDoesNotExistsException("Account does not exists"));

		return existingAccount.getTransactions();

	}

	@Override
	public List<Transaction> listTransactionsByDate(Long id, Date currentDate, Date pastDate)
			throws AccountDoesNotExistsException {
		Account existingAccount = accountRepository.findById(id)
				.orElseThrow(() -> new AccountDoesNotExistsException("Account does not exists"));

		List<Transaction> transactions = existingAccount.getTransactions().stream()
				.filter(transaction -> transaction.getTransactionDate().after(pastDate)
						&& transaction.getTransactionDate().before(currentDate))
				.collect(Collectors.toList());

		return transactions;
	}

	@Transactional
	@Override
	public Account createDeposit(Long id, Transaction transaction) throws Exception {
		Account existingAccount = accountRepository.findById(id)
				.orElseThrow(() -> new AccountDoesNotExistsException("Account does not exists"));

		if (transaction.isCretid() == false) {
			throw new OperationNotAllowedException("This operation is not allowed. Must be a withdraw");
		}

		double totalBalance = existingAccount.getAccountBalance();

		existingAccount.setAccountBalance(totalBalance + transaction.getValue());

		return accountRepository.save(existingAccount);
	}

	@Transactional
	@Override
	public Account createWithdraw(Long id, Transaction transaction) throws Exception {
		Account existingAccount = accountRepository.findById(id)
				.orElseThrow(() -> new AccountDoesNotExistsException("Account does not exists"));

		if (transaction.isCretid() == true) {
			throw new OperationNotAllowedException("This operation is not allowed. Must be a deposit");
		}

		List<Transaction> transactions = existingAccount.getTransactions().stream()
				.filter(pastTransaction -> pastTransaction.getTransactionDate().before(new Date())
						&& pastTransaction.isCretid() == false)
				.collect(Collectors.toList());

		double totalWithdraw = 0;
		
		Calendar calendar = Calendar.getInstance();
		Calendar newCalendar = Calendar.getInstance();
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		int transactionDay;

		for (Transaction currentTransaction : transactions) {
			
			newCalendar.setTime(currentTransaction.getTransactionDate());
			
			transactionDay = newCalendar.get(Calendar.DAY_OF_MONTH);
			
			if (transactionDay == today) {
				totalWithdraw += currentTransaction.getValue();
			}
			
		}

		if (totalWithdraw >= existingAccount.getDailyWithdrawalLimit()) {
			throw new OperationNotAllowedException("This operation is not allowed. Withdraw limit reached");
		}

		if (transaction.getValue() > existingAccount.getDailyWithdrawalLimit()) {
			throw new OperationNotAllowedException("This operation is not allowed. Withdraw daily limit is "
					+ existingAccount.getDailyWithdrawalLimit());
		}

		double withdrawValueAllowed = existingAccount.getDailyWithdrawalLimit() - totalWithdraw;

		if (transaction.getValue() > withdrawValueAllowed) {
			throw new OperationNotAllowedException(
					"This operation is not allowed. Cannot withdraw this value " + transaction.getValue());
		}

		double totalBalance = existingAccount.getAccountBalance();

		existingAccount.setAccountBalance(totalBalance - transaction.getValue());

		return accountRepository.save(existingAccount);
	}

}

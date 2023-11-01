package com.coderscampus.assignment13.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepo;

	public Account findById(Long accountId) {
		Optional<Account> accountOpt = accountRepo.findById(accountId);
		return accountOpt.orElse(null);
	}

	public Account saveAccount(Account account) {
		return accountRepo.save(account);
		
	}

	public Long addAccount(User user) {
		Account account = new Account();
		Integer accountNumber = user.getAccounts().size() + 1;
		account.setAccountName("Account # " + accountNumber);
		account.getUsers().add(user);
		user.getAccounts().add(account);
		accountRepo.save(account);
		return account.getAccountId();
	}

}

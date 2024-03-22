package com.coderscampus.assignment13.service;

import java.util.List;
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

	public Account addAccount(User user) {
		Account account = new Account();
		Integer accountNumber = user.getAccounts().size() + 1;
		account.setAccountName("Bank Account " + accountNumber);
		List<User> users = account.getUsers();
		user.getAccounts().add(account);
		users.add(user);
		account.setUsers(users);

		return account;
	}

	public void deleteAccount(Account account) {
		accountRepo.delete(account);
		
	}

}

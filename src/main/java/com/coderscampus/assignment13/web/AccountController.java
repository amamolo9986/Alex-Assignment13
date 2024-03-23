package com.coderscampus.assignment13.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class AccountController {

	private AccountService accountService;
	private UserService userService;

	public AccountController(AccountService accountService, UserService userService) {
		super();
		this.accountService = accountService;
		this.userService = userService;
	}

	@GetMapping("/users/{userId}/accounts")
	public String getAccount(@PathVariable Long userId, ModelMap model) {
		User user = userService.findById(userId);
		Account account = accountService.addAccount(user);
		model.put("account", account);
		model.put("user", user);
		return "account";
	}

	@PostMapping("/users/{userId}/accounts")
	public String postAccount(@PathVariable Long userId, Account account) {
		User user = userService.findById(userId);
		Account savedAccount = accountService.saveAccount(account);
		List<Account> accounts = user.getAccounts();
		accounts.add(savedAccount);
		user.setAccounts(accounts);
		userService.saveUser(user);
		return "redirect:/users/{userId}";
	}

	@GetMapping("/users/{userId}/accounts/{accountId}")
	public String getOneAccount(ModelMap model, @PathVariable Long accountId, @PathVariable Long userId) {
		User user = userService.findById(userId);
		Account account = accountService.findById(accountId);
		model.put("account", account);
		model.put("user", user);
		return "account";
	}

	@PostMapping("/users/{userId}/accounts/{accountId}")
	public String saveAccount(@PathVariable Long userId, @PathVariable Long accountId, Account account) {
		User user = userService.findById(userId);
		 userService.saveUser(user);
		 accountService.saveAccount(account);
		return "redirect:/users/{userId}";
	}
	
	@PostMapping("/users/{userId}/accounts/{accountId}/delete")
	public String deleteAccount(@PathVariable Long accountId) {
	    Account account = accountService.findById(accountId);
	    // 1. Remove account from all associated users
	    for (User user : account.getUsers()) {
	        user.getAccounts().remove(account); // Bidirectional relationship management
	    }
	    // 2. Delete the account (JPA will handle foreign key in join table)
	    accountService.deleteAccount(account); 

	    return "redirect:/users/{userId}";
	}
}

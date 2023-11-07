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

	@Autowired
	private AccountService accountService;
	@Autowired
	private UserService userService;


	@GetMapping("/users/{userId}/accounts")
	public String getAccount(@PathVariable Long userId, ModelMap model) {
		User user = userService.findById(userId);
		Account account = accountService.addAccount(user);
		model.put("account", account);
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
		return "redirect:/users/" + userId + "/accounts";
	}

	@GetMapping("/users/{userId}/accounts/{accountId}")
	public String getOneAccount(ModelMap model, @PathVariable Long accountId) {
		Account account = accountService.findById(accountId);
		model.put("account", account);
		return "account";
	}

	@PostMapping("/users/{userId}/accounts/{accountId}")
	public String saveAccount(@PathVariable Long userId, Account account) {
		User user = userService.findById(userId);
		 userService.saveUser(user);
		 accountService.saveAccount(account);
		return "redirect:/users/{userId}/accounts/{accountId}";
	}

}

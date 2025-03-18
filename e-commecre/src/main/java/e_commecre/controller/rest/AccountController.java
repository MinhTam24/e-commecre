package e_commecre.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import e_commecre.entity.Account;
import e_commecre.repository.AccountRepository;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import e_commecre.dto.AccountDto;
import e_commecre.service.AccountService;

@RestController
public class AccountController {
	
	@Autowired 
	AccountService accountService;
	
	@GetMapping("/api/account/{email}")
	public AccountDto getAccountByEmail(@PathVariable("email") String email) throws AccountNotFoundException {
		return accountService.getAccountByEmail(email);
	}
	
	
	
}

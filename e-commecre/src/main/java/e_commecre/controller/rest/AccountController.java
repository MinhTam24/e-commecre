package e_commecre.controller.rest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import e_commecre.entity.Account;
import e_commecre.entity.Role;
import e_commecre.repository.AccountRepository;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import e_commecre.dto.AccountDto;
import e_commecre.dto.LoginDto;
import e_commecre.service.AccountService;

@RestController
public class AccountController {
	
	@Autowired 
	AccountService accountService;
	
	@Autowired
	AccountRepository accountRepository;
	
	@GetMapping("/api/account/{email}")
	public AccountDto getAccountByEmail(@PathVariable("email") String email) throws AccountNotFoundException {
		return accountService.getAccountByEmail(email);
	}
	
	@GetMapping("/api/account/role/{email}")
	public String getRoleAccount(@PathVariable("email") String email) throws AccountNotFoundException {
		Optional<Account> accountDto = accountRepository.findAccountByEmail(email);
		if(accountDto.isPresent()) {
			return accountDto.get().getRoles().toString();
		}
		return null;
	}
	
	@PostMapping("/api/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		try {
			String token = accountService.login(loginDto);
			return ResponseEntity.ok(Map.of("token", token));
		}  catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("message", "An error occurred during login" + e.getMessage()));
		}
	}
	
	
	
}

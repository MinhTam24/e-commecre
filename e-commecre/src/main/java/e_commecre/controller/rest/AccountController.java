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
import e_commecre.dto.RegisterDto;
import e_commecre.service.AccountService;

@RestController
public class AccountController {
	
	@Autowired 
	AccountService accountService;
	
	@Autowired
	AccountRepository accountRepository;
	
	@GetMapping("/api/account/{email}")
	public ResponseEntity<?> getAccountByEmail(@PathVariable("email") String email) throws AccountNotFoundException {
		return ResponseEntity.ok(accountService.getAccountByEmail(email));
	}
	
	@GetMapping("/api/account/{phone}")
	public ResponseEntity<?> getAccountByphone(@PathVariable("phone") String phone) throws AccountNotFoundException {
		return ResponseEntity.ok(accountService.getAccountByPhoneNumber(phone));
	}
	
	
	@PostMapping("/api/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
			String token = accountService.login(loginDto);
			return ResponseEntity.ok(Map.of("token", token));
	}
	
	@PostMapping("/api/register")
	public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){
			accountService.signUp(registerDto);
	        return ResponseEntity.status(HttpStatus.CREATED).body("Đăng ký thành công!");
	}
	
	
	
}

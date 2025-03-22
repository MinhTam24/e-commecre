package e_commecre.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import e_commecre.dto.AccountDto;
import e_commecre.dto.LoginDto;
import e_commecre.dto.RegisterDto;
import e_commecre.entity.Account;
import e_commecre.entity.Role;
import e_commecre.exception.AccountAlreadyExistsException;
import e_commecre.exception.EmailAlreadyExistsException;
import e_commecre.exception.PhoneNumberAlreadyExistsException;
import e_commecre.exception.ResouceNotFoundException;
import e_commecre.repository.AccountRepository;
import e_commecre.repository.RoleRepository;
import e_commecre.security.CustomUserDetailService;
import e_commecre.security.CustomUserDetails;
import e_commecre.security.jwtService;
import e_commecre.service.AccountService;
import e_commecre.ultil.ConstUltil;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	jwtService jwtService;

	@Override
	public String login(LoginDto loginDto) {
	    try {
	        Authentication authentication = authenticationManager
	                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

	        if (authentication.isAuthenticated()) {
	            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
	            String token = jwtService.createToken(customUserDetails.getUsername(), customUserDetails.getUserId());
	            return token;

	        } else {
	            return null;
	        }
	    } catch (BadCredentialsException e) {
	        System.out.println("Invalid credentials: " + e.getMessage());
	        throw new RuntimeException("Invalid credentials, please check your email and password");
	    } catch (Exception e) {
	        System.out.println("Error during login: " + e.getMessage());
	        throw new RuntimeException("An error occurred during login: " + e.getMessage());
	    }
	}

	@Override
	public AccountDto getAccountByEmail(String email) throws AccountNotFoundException {
		Account account = accountRepository.findAccountByEmail(email)
				.orElseThrow(() -> new AccountNotFoundException("Account not found for email: " + email));
		return contvertToAccountDto(account);
	}

	@Override
	public AccountDto getAccountByPhoneNumber(String phone) throws AccountNotFoundException {
		Account account = accountRepository.findAccountByPhoneNumber(phone)
				.orElseThrow(() -> new AccountNotFoundException("Account not found for phone: " + phone));
		return contvertToAccountDto(account);
	}

	private AccountDto contvertToAccountDto(Account account) {
		AccountDto accountDto = new AccountDto();
		accountDto.setId(account.getId());
		accountDto.setEmail(account.getEmail());
		accountDto.setFirstName(account.getFirstName());
		accountDto.setFullName(account.getFullName());
		accountDto.setAddress(account.getAddress());
		accountDto.setPhoneNumber(account.getPhoneNumber());
		accountDto.setCreateAt(account.getCreateAt().format(ConstUltil.DATE_TIME_FORMATTER));
		return accountDto;
	}

	@Override
	public void signUp(RegisterDto registerDto) {
		
	    if (accountRepository.existsByEmail(registerDto.getEmail())) {
	        throw new EmailAlreadyExistsException("Email đã tồn tại: " + registerDto.getEmail());
	    }
	    if (accountRepository.existsByPhoneNumber(registerDto.getPhoneNumber())) {
	        throw new PhoneNumberAlreadyExistsException("Số điện thoại đã tồn tại: " + registerDto.getPhoneNumber());
	    }
	    
	    String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

	    Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new ResouceNotFoundException("Not found role"));
	    
	    Account account = new Account();
	    account.setEmail(registerDto.getEmail());
	    account.setPhoneNumber(registerDto.getPhoneNumber());
	    account.setAddress(registerDto.getAddress());
	    account.setFirstName(registerDto.getFirstName());
	    account.setFullName(registerDto.getFullName());
	    account.setPassword(encodedPassword);
	    account.setRoles(Collections.singletonList(role));

	    accountRepository.saveAndFlush(account);
	}


}

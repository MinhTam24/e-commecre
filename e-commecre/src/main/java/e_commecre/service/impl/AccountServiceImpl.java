package e_commecre.service.impl;

import java.time.format.DateTimeFormatter;

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
import e_commecre.entity.Account;
import e_commecre.repository.AccountRepository;
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
				System.out.println("Chưa đăng nhập");
				return null;
			}
		} catch (BadCredentialsException e) {
			throw new RuntimeException("An error occurred during login: " + e.getMessage());
		}
	}

	@Override
	public AccountDto getAccountByEmail(String email) throws AccountNotFoundException {
		Account account = accountRepository.findAccountByEmail(email)
				.orElseThrow(() -> new AccountNotFoundException("Account not found for email: " + email));
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		accountRepository.save(account);
		return contvertToAccountDto(account);
	}

	@Override
	public AccountDto getAccountByPhoneNumber(String phone) throws AccountNotFoundException {
		// TODO Auto-generated method stub
		return null;
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

}

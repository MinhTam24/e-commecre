package e_commecre.service;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.stereotype.Service;

import e_commecre.dto.AccountDto;
import e_commecre.dto.LoginDto;
import e_commecre.dto.RegisterDto;
import e_commecre.entity.Account;

@Service
public interface AccountService {
	String login(LoginDto loginDto);
	AccountDto getAccountByEmail(String email) throws AccountNotFoundException ;
	AccountDto getAccountByPhoneNumber(String phone) throws AccountNotFoundException ;
	AccountDto getAccountByEmailOrPhoneNumber(String userName);
	void signUp(RegisterDto registerDto);
}

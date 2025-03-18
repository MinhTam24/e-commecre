package e_commecre.service;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.stereotype.Service;

import e_commecre.dto.AccountDto;
import e_commecre.entity.Account;

@Service
public interface AccountService {
	AccountDto getAccountByEmail(String email) throws AccountNotFoundException ;
	AccountDto getAccountByPhoneNumber(String phone) throws AccountNotFoundException ;
}

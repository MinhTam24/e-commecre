package e_commecre.service.impl;

import java.time.format.DateTimeFormatter;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import e_commecre.dto.AccountDto;
import e_commecre.entity.Account;
import e_commecre.repository.AccountRepository;
import e_commecre.service.AccountService;
import e_commecre.ultil.ConstUltil;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	AccountRepository accountRepository;

	@Override
	public AccountDto getAccountByEmail(String email) throws AccountNotFoundException {
		  Account account = accountRepository.findAccountByEmail(email)
		            .orElseThrow(() -> new AccountNotFoundException("Account not found for email: " + email));
		   return contvertToAccountDto(account);
	}

	@Override
	public AccountDto getAccountByPhoneNumber(String phone) throws AccountNotFoundException  {
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

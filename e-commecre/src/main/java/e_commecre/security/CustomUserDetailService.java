package e_commecre.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import e_commecre.entity.Account;
import e_commecre.entity.Role;
import e_commecre.repository.AccountRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> account = accountRepository.findByEmailOrPhone(username);
		if(account.isPresent()) {
			Account acc = account.get();
			List<Role> roles = acc.getRoles();
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			roles.stream().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
			CustomUserDetails userDetails = new CustomUserDetails(acc.getId(), acc.getEmail(), acc.getPassword(), grantedAuthorities);
			return userDetails;
		}else {
			 throw new UsernameNotFoundException("Not found Account!");
		}
	}

}

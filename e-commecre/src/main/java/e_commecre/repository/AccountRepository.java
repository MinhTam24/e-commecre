package e_commecre.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import e_commecre.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
		Optional<Account> findAccountByEmail(String email);
		Optional<Account> findAccountByPhoneNumber(String phone);
		boolean existsByEmail(String email);
		boolean existsByPhoneNumber(String phone);
}

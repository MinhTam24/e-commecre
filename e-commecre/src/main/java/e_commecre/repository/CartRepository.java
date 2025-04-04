package e_commecre.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import e_commecre.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
		Optional<Cart> findByAccountId(long id);
}

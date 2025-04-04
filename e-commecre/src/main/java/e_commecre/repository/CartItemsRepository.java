package e_commecre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commecre.entity.CartItems;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
	void deleteByCartId(long id);
}

package e_commecre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commecre.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

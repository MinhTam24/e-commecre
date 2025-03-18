package e_commecre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commecre.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}

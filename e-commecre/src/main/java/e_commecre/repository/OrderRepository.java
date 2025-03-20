package e_commecre.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import e_commecre.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Sửa tên phương thức tìm theo customerId
    Page<Order> findOrderByCustomerIdId(long customerId, Pageable pageable);

    // Sửa phương thức tìm theo trạng thái đơn hàng (OrderStatus)
    Page<Order> findByStatus(String status, Pageable pageable);

    // Tìm đơn hàng theo khoảng thời gian
    Page<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // Tìm đơn hàng theo giá trị tổng đơn hàng
    Page<Order> findByTotalAmountBetween(double minPrice, double maxPrice, Pageable pageable);

   
}

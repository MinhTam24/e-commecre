package e_commecre.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import e_commecre.dto.OrderDto;

@Service
public interface OrderService {
    OrderDto getOrderById(long id); // Tìm theo mã đơn hàng
    Page<OrderDto> getAllOrder(PageRequest pageRequest); // Tìm tất cả đơn hàng với phân trang
    Page<OrderDto> getOrderByCustomerId(long id, PageRequest pageRequest); // Tìm theo mã khách hàng với phân trang
    Page<OrderDto> getAllOrderByStatus(String status, PageRequest pageRequest); // Tìm theo trạng thái với phân trang
    OrderDto createOrder(OrderDto orderDto);  // Tạo đơn hàng mới
    OrderDto updateOrder(long id, OrderDto orderDto);  // Cập nhật thông tin đơn hàng
    void deleteOrder(long id);  // Xóa đơn hàng
    OrderDto updateOrderStatus(long id, boolean status);  // Thay đổi trạng thái đơn hàng
    Page<OrderDto> getOrderByDateRange(LocalDate startDate, LocalDate endDate, PageRequest pageRequest);  // Tìm theo khoảng thời gian với phân trang
    Page<OrderDto> getOrderByTotalPrice(double minPrice, double maxPrice, PageRequest pageRequest);  // Tìm theo giá trị đơn hàng với phân trang
    Page<OrderDto> getOrderByPaymentMethod(String paymentMethod, PageRequest pageRequest);  // Tìm theo phương thức thanh toán với phân trang
    boolean checkOrderExists(long id);  // Kiểm tra sự tồn tại của đơn hàng
    Page<OrderDto> getOrderByProductId(long productId, PageRequest pageRequest);  // Lọc theo sản phẩm trong đơn hàng với phân trang
}

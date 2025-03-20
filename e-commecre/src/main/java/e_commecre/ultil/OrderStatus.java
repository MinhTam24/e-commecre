package e_commecre.ultil;

public enum OrderStatus {
    PENDING("Chưa xử lý"),      // Đơn hàng mới tạo, chưa được xử lý
    PROCESSING("Đang xử lý"),   // Đơn hàng đang được xử lý
    SHIPPED("Đã giao"),         // Đơn hàng đã được giao
    CANCELLED("Đã hủy"),        // Đơn hàng bị hủy
    COMPLETED("Hoàn thành");    // Đơn hàng đã hoàn thành

    private final String statusDescription;

    // Constructor
    OrderStatus(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getStatusDescription() {
        return statusDescription;
    }
    
    @Override
    public String toString() {
        return statusDescription;
    }

    public static OrderStatus fromString(String status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.name().equalsIgnoreCase(status)) {
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}


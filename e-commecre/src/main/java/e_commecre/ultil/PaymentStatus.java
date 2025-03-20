package e_commecre.ultil;

public enum PaymentStatus {
    PAID("Đã thanh toán"),
    UNPAID("Chưa thanh toán"),
    FAILED("Thanh toán thất bại");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

    public static PaymentStatus fromString(String status) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.name().equalsIgnoreCase(status)) {
                return paymentStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant " + PaymentStatus.class.getCanonicalName() + "." + status);
    }
}


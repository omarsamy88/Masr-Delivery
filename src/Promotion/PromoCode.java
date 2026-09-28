package Promotion;
import java.time.LocalDate;

public record PromoCode(String code, Promotion promotion, double minimumSubtotal, LocalDate expiryDate) {

    public PromoCode {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code cannot be empty.");
        }
        if (promotion == null) {
            throw new IllegalArgumentException("Promotion cannot be null.");
        }
        if (minimumSubtotal < 0) {
            throw new IllegalArgumentException("Minimum subtotal cannot be negative.");
        }
        if (expiryDate == null) {
            throw new IllegalArgumentException("Expiry date cannot be null.");
        }
        code = code.toUpperCase();
    }

    public boolean isValid(double subtotal, LocalDate date) {
        return subtotal >= minimumSubtotal && !date.isAfter(expiryDate) && promotion.isApplicable(subtotal);
    }

    public double calculateDiscount(double subtotal) {
        return promotion.calculateDiscount(subtotal);
    }

    public boolean isFreeDelivery() {
        return promotion.isFreeDelivery();
    }
}

package Promotion;

public class FixedPromotion implements Promotion {
    private final double amount;

    public FixedPromotion(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Discount amount must be greater than 0.");
        }
        this.amount = amount;
    }

    @Override
    public double calculateDiscount(double subtotal) {
        return Math.min(amount, subtotal);
    }

    @Override
    public boolean isApplicable(double subtotal) {
        return subtotal > 0;
    }

    @Override
    public boolean isFreeDelivery() {
        return false;
    }
}
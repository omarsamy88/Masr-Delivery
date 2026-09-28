package Promotion;

public class PercentagePromotion implements Promotion {
    private final double percentage;
    private final double maxDiscount;

    public PercentagePromotion(double percentage, double maxDiscount) {
        if (percentage <= 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100.");
        }
        if (maxDiscount < 0) {
            throw new IllegalArgumentException("Maximum discount cannot be negative.");
        }
        this.percentage = percentage;
        this.maxDiscount = maxDiscount;
    }

    @Override
    public double calculateDiscount(double subtotal) {
        double discount = subtotal * percentage / 100;
        return Math.min(discount, maxDiscount);
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

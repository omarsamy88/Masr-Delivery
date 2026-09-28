package Promotion;

public class FreeDeliveryPromotion implements Promotion {
    @Override
    public double calculateDiscount(double subtotal) {
        return 0;
    }

    @Override
    public boolean isApplicable(double subtotal) {
        return subtotal > 0;
    }

    @Override
    public boolean isFreeDelivery() {
        return true;
    }
}

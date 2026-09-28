package Promotion;

public interface Promotion {

    double calculateDiscount(double subtotal);

    boolean isApplicable(double subtotal);

    boolean isFreeDelivery();
}

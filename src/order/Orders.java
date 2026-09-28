package order;

import Promotion.PromoCode;
import customer.Address;
import customer.Customers;
import customer.LoyaltyTier;
import restaurant.Restaurants;
import rider.Riders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class Orders {
    private final int id;
    private Customers customer;
    private Restaurants restaurant;
    private Address deliveryAddress;
    private List<OrderLine> items;
    private OrderStatus status= OrderStatus.PLACED;
    private final LocalDateTime placedAt;
    private Riders rider;
    private PromoCode promoCode;
    private double deliveryDistanceKm;

    public Orders(int id, Customers customer, Restaurants restaurant, Address deliveryAddress, List<OrderLine> items, LocalDateTime placedAt,Riders rider, double deliveryDistanceKm) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.placedAt = placedAt;
        setDeliveryAddress(deliveryAddress);
        setItems(items);
        this.rider=rider;
        this.deliveryDistanceKm=deliveryDistanceKm;
    }

    public int getId() {
        return id;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public Restaurants getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurants restaurant) {
        this.restaurant = restaurant;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        if(customer.getAddresses().contains(deliveryAddress)){
            this.deliveryAddress=deliveryAddress;
        }else {
            throw new RuntimeException("wrong address");
        }
    }

    public double getDeliveryDistanceKm() {
        return deliveryDistanceKm;
    }

    public List<OrderLine> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(List<OrderLine> items) {
        if(items==null || items.isEmpty()){
            throw new RuntimeException("at least 1 item");
        }else {
            this.items = items;
        }
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        if(this.status== OrderStatus.PLACED && status== OrderStatus.ACCEPTED){
            this.status=status;
        }
        else if(this.status== OrderStatus.ACCEPTED && status== OrderStatus.PREPARING){
            this.status=status;
        }
        else if(this.status== OrderStatus.PREPARING && status== OrderStatus.READY){
            this.status=status;
        }
        else if(this.status== OrderStatus.READY && status== OrderStatus.ASSIGNED){
            this.status=status;
        }
        else if(this.status== OrderStatus.ASSIGNED && status== OrderStatus.OUT_FOR_DELIVERY){
            this.status=status;
        }
        else if(this.status== OrderStatus.OUT_FOR_DELIVERY && status== OrderStatus.DELIVERED){
            this.status=status;
        }
        //CANCELLED

        else if(this.status== OrderStatus.PLACED && status== OrderStatus.CANCELLED){
            this.status=status;
        }
        else if(this.status== OrderStatus.ACCEPTED && status== OrderStatus.CANCELLED){
            this.status=status;
        }
        else if(this.status== OrderStatus.PREPARING && status== OrderStatus.CANCELLED){
            this.status=status;
        }
        else if(this.status== OrderStatus.READY && status== OrderStatus.CANCELLED){
            this.status=status;
        }
        else if(this.status== OrderStatus.ASSIGNED && status== OrderStatus.CANCELLED){
            this.status=status;
        }
        else {
            throw new RuntimeException("Invalid order status transition");
        }
    }

    public LocalDateTime getPlacedAt() {
        return placedAt;
    }

    public Riders getRider() {
        return rider;
    }

    public void setRider(Riders rider) {
        this.rider = rider;
    }

    public double calculateSubtotal(){
        double subtotal = 0;
        for (OrderLine line : items) {
            subtotal += line.item().getPrice() * line.quantity();
        }
        return subtotal;
    }


    public void applyPromo(PromoCode promoCode) {
        if (promoCode == null) {
            throw new RuntimeException("Promo code can't be null.");
        }

        if (this.promoCode != null) {
            throw new RuntimeException("Only one promo code can be used.");
        }

        if (!promoCode.isValid(calculateSubtotal(), LocalDate.now())) {
            throw new RuntimeException("Invalid promo code.");
        }

        this.promoCode = promoCode;
    }

    public double calculateDeliveryFee() {
        double fee = 15;

        if (deliveryDistanceKm > 3) {
            fee += (deliveryDistanceKm - 3) * 3;
        }
        if (promoCode != null && promoCode.isFreeDelivery()) {
            return 0;
        }
        if (customer.getTier() == LoyaltyTier.SILVER) {
            fee *= 0.90;
        } else if (customer.getTier() == LoyaltyTier.GOLD) {
            fee = 0;
        }
        return fee;
    }

    public double calculateServiceFee() {
        return Math.round(calculateSubtotal() * 0.10 * 100.0) / 100.0;
    }

    public double calculateTotal() {
        double subtotal = calculateSubtotal();
        double deliveryFee = calculateDeliveryFee();
        double serviceFee = calculateServiceFee();
        double promotionDiscount = 0;
        if (promoCode != null) {
            promotionDiscount = promoCode.calculateDiscount(subtotal);
        }
        double total = subtotal + deliveryFee + serviceFee - promotionDiscount;
        return Math.max(total, 0);
    }




}

package order;
import customer.Address;
import customer.Customers;
import restaurant.Restaurants;
import rider.Riders;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private int id;
    private Customers customer;
    private Restaurants restaurant;
    private Address deliveryAddress;
    private List<OrderLine> items;
    private LocalDateTime placedAt;
    private Riders rider;
    private double deliveryDistanceKm;

    public OrderBuilder(int id, Customers customer, Restaurants restaurant) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = new ArrayList<>();
        this.placedAt = LocalDateTime.now();
    }

    public OrderBuilder deliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public OrderBuilder addItem(OrderLine item) {
        this.items.add(item);
        return this;
    }

    public OrderBuilder rider(Riders rider) {
        this.rider = rider;
        return this;
    }

    public Orders build() {
        return new Orders(id, customer, restaurant, deliveryAddress, items, placedAt, rider,deliveryDistanceKm);
    }
}
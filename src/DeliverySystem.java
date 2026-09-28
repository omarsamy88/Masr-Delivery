import customer.Customers;
import order.Orders;
import restaurant.Restaurants;
import rider.Riders;
import java.util.*;
import java.util.function.Predicate;

public class DeliverySystem {
    private Map<Integer, Restaurants> restaurants;
    private Map<Integer, Customers> customers;
    private Map<Integer, Riders> riders;
    private Map<Integer, Orders> orders;

    public DeliverySystem() {
        restaurants = new HashMap<>();
        customers = new HashMap<>();
        riders = new HashMap<>();
        orders = new HashMap<>();
    }

    public Map<Integer, Restaurants> getRestaurants() {
        return Collections.unmodifiableMap(restaurants);
    }

    public Map<Integer, Customers> getCustomers() {
        return Collections.unmodifiableMap(customers);
    }

    public Map<Integer, Riders> getRiders() {
        return Collections.unmodifiableMap(riders);
    }

    public Map<Integer, Orders> getOrders() {
        return Collections.unmodifiableMap(orders);
    }

    public void addRestaurant( Restaurants restaurant){
        if (restaurants.containsKey(restaurant.getId())) {
            throw new RuntimeException("id already taken");
        }
        else {
            restaurants.put(restaurant.getId(), restaurant);
        }
    }
    public void addCustomer( Customers customer) {
        if (customers.containsKey(customer.getId())) {
            throw new RuntimeException("id already taken");
        }else {
            customers.put(customer.getId(), customer);
        }
    }
    public void addRider( Riders rider) {

        if (riders.containsKey(rider.getId())) {
            throw new RuntimeException("id already taken");
        }else {
            riders.put(rider.getId(), rider);
        }
    }
    public void addOrder( Orders order) {

        if (orders.containsKey(order.getId())) {
            throw new RuntimeException("id already taken");
        }else {
            orders.put(order.getId(), order);
        }
    }

    public Restaurants getRestaurantById(int id) {
         return restaurants.get(id);
    }
    public Customers getCustomerById(int id) {
        return customers.get(id);
    }

    public Riders getRiderById(int id) {
        return riders.get(id);
    }

    public Orders getOrderById(int id) {
        return orders.get(id);
    }

    public void removeRestaurant(int id) {
        restaurants.remove(id);
    }

    public void removeCustomer(int id) {
        customers.remove(id);
    }

    public void removeRider(int id) {
        riders.remove(id);
    }

    public void removeOrder(int id) {
        orders.remove(id);
    }

    public List<Restaurants> getRestaurantsByDistrict(String district) {
        List<Restaurants> result = new ArrayList<>();
        for (Restaurants restaurant : restaurants.values()) {
            if (restaurant.getDistrict().equals(district)){
                result.add(restaurant);
            }
        }
        return result;

    }

    public List<Restaurants> getRestaurantsSorted() {
        List<Restaurants> result = new ArrayList<>(restaurants.values());
        Comparator<Restaurants> byRating =Comparator.comparing(Restaurants::getRating).reversed().thenComparing(Restaurants::getName);
        Collections.sort(result,byRating);
        return result;
    }

    public List<Restaurants> searchRestaurants(Predicate<Restaurants> condition) {

        return restaurants.values()
                .stream()
                .filter(condition)
                .toList();
    }

    public Set<String> getAllCuisineCategories() {

        return restaurants.values()
                .stream()
                .flatMap(r -> r.getCategories().stream())
                .collect(java.util.stream.Collectors.toSet());
    }


}

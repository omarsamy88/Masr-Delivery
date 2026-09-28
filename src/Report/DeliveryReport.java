package Report;
import customer.Customers;
import order.Orders;
import order.OrderStatus;
import restaurant.Restaurants;
import rider.Riders;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DeliveryReport {

    public static double totalRevenue(Collection<Orders> orders, LocalDateTime from, LocalDateTime to) {
        return orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .filter(o -> !o.getPlacedAt().isBefore(from))
                .filter(o -> !o.getPlacedAt().isAfter(to))
                .mapToDouble(Orders::calculateTotal)
                .sum();
    }

    public static List<Map.Entry<Restaurants, Double>> topFiveRestaurantsByRevenue(Collection<Orders> orders, LocalDate month) {
        return orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .filter(o -> o.getPlacedAt().getYear() == month.getYear())
                .filter(o -> o.getPlacedAt().getMonth() == month.getMonth())
                .collect(Collectors.groupingBy(Orders::getRestaurant, Collectors.summingDouble(Orders::calculateTotal)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Restaurants, Double>comparingByValue().reversed())
                .limit(5)
                .toList();
    }

    public static Map<String, Double> averageOrderValuePerDistrict(Collection<Orders> orders) {

        return orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .collect(Collectors.groupingBy(o -> o.getDeliveryAddress().district(), Collectors.averagingDouble(Orders::calculateTotal)));
    }

    public static Map<OrderStatus, Long>
    ordersByStatus(Collection<Orders> orders) {

        return orders.stream()
                .collect(Collectors.groupingBy(Orders::getStatus, Collectors.counting()
                ));
    }

    public static List<Orders> customerHistory(Collection<Orders> orders, Customers customer) {
        return orders.stream()
                .filter(o -> o.getCustomer().equals(customer))
                .sorted(Comparator.comparing(Orders::getPlacedAt).reversed())
                .toList();
    }
}

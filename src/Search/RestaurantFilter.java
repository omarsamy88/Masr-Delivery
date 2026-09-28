package Search;
import restaurant.Restaurants;

@FunctionalInterface
public interface RestaurantFilter {
    boolean test(Restaurants restaurant);
}

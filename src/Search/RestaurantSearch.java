package Search;
import restaurant.Restaurants;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RestaurantSearch {

    public static List<Restaurants> search(List<Restaurants> restaurants, Predicate<Restaurants> condition) {
        return restaurants.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }
}
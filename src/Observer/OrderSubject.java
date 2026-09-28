package Observer;
import order.Orders;
import java.util.ArrayList;
import java.util.List;

public class OrderSubject {

    private final List<OrderObserver> observers =
            new ArrayList<>();

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(Orders order) {
        for (OrderObserver observer : observers) {
            observer.update(order);
        }
    }
}
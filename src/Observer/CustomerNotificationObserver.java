package Observer;
import order.Orders;

public class CustomerNotificationObserver implements OrderObserver {

    @Override
    public void update(Orders order) {
        System.out.println("Customer notified: Order " + order.getId() + " is now " + order.getStatus());
    }
}
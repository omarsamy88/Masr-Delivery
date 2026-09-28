package Observer;
import order.Orders;

public class AuditLogObserver implements OrderObserver {

    @Override
    public void update(Orders order) {
        System.out.println("Audit: Order " + order.getId() + " changed to " + order.getStatus());
    }
}

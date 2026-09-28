package Dispatch;
import order.Orders;
import rider.Riders;

public interface DispatchStrategy {
    boolean canAccept(Riders rider, Orders order);
    double estimatedDuration(Orders order);
}
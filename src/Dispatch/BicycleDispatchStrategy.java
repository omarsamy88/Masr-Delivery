package Dispatch;

import order.Orders;
import rider.Riders;

public class BicycleDispatchStrategy
        implements DispatchStrategy {

    @Override
    public boolean canAccept(Riders rider, Orders order) {
        return rider.isAvailability()
                && order.getRider() == null;
    }

    @Override
    public double estimatedDuration(Orders order) {
        return order.getDeliveryDistanceKm() / 15.0;
    }
}
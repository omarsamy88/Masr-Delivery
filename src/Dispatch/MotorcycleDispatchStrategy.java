package Dispatch;

import order.Orders;
import rider.Riders;

public class MotorcycleDispatchStrategy
        implements DispatchStrategy {

    @Override
    public boolean canAccept(Riders rider, Orders order) {
        return rider.isAvailability()
                && order.getRider() == null;
    }

    @Override
    public double estimatedDuration(Orders order) {
        return order.getDeliveryDistanceKm() / 40.0;
    }
}
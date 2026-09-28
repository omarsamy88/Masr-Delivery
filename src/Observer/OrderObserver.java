package Observer;

import order.Orders;

public interface OrderObserver {
    void update(Orders order);
}

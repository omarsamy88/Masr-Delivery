package order;
import customer.LoyaltyTier;
import java.util.Comparator;
import java.util.PriorityQueue;

public class ReadyOrderQueue {
    private final PriorityQueue<Orders> queue;
    public ReadyOrderQueue() {

        Comparator<Orders> comparator = Comparator.comparing((Orders order) ->order.getCustomer().getTier() == LoyaltyTier.GOLD)
                        .reversed()
                        .thenComparing(Orders::getPlacedAt);
        queue = new PriorityQueue<>(comparator);
    }

    public void add(Orders order) {
        queue.offer(order);
    }

    public Orders poll() {
        return queue.poll();
    }

    public Orders peek() {
        return queue.peek();
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
package restaurant;

public class StandardItem extends MenuItems {

    public StandardItem(int id, String name, double price, String category, int timeInMinuts, boolean availability) {
        super(id, name, price, category, timeInMinuts, availability);
    }

    @Override
    public double getPrice() {
        return price;
    }
}

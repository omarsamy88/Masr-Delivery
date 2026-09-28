package restaurant;

public class Combo extends MenuItems {
    private double discount;

    public Combo(int id, String name, double price, String category, int timeInMinuts, boolean availability, double discount) {
        super(id, name, price, category, timeInMinuts, availability);
        this.discount = discount;
    }

    @Override
    public double getPrice() {
        double newPrice=price-(price*(discount/100));
        return newPrice;
    }
}

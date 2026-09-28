package restaurant;

public class WeightedItem extends MenuItems {
    private double weight;

    public WeightedItem(int id, String name, double price, String category, int timeInMinutes, boolean availability, double weight) {
        super(id, name, price, category, timeInMinutes, availability);
        if(weight<=0){
            throw new RuntimeException("weight must be greater than 0");
        }else{
            this.weight = weight;
        }
    }

    @Override
    public double getPrice() {
        return price*weight;
    }
}

package restaurant;

public abstract class MenuItems {
    private final int id;
    private final String name;
    protected double price;
    private String category;
    private int timeInMinutes;
    private boolean availability;

    public MenuItems(int id, String name, double price, String category, int timeInMinutes, boolean availability) {
        this.id = id;
        this.name = name;
        setPrice(price);
        this.category = category;
        this.timeInMinutes = timeInMinutes;
        this.availability = availability;
    }

    public abstract double getPrice();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
       if(price>0){
           this.price = price;
       }else {
           throw new RuntimeException("must be greater than 0.");
       }

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(int timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}

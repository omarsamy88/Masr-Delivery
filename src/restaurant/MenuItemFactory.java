package restaurant;

public class MenuItemFactory {

    public static MenuItems createItem(String type, int id, String name, double price, String category, int timeInMinutes, boolean availability, double extraValue) {

        if (type.equalsIgnoreCase("standard")) {

            return new StandardItem(id, name, price, category, timeInMinutes, availability);

        } else if (type.equalsIgnoreCase("combo")) {

            return new Combo(id, name, price, category, timeInMinutes, availability, extraValue);

        } else if (type.equalsIgnoreCase("weighted")) {

            return new WeightedItem(id, name, price, category, timeInMinutes, availability, extraValue);
        }

        throw new IllegalArgumentException("Unknown menu item type.");
    }
}
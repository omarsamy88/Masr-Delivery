package restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Restaurants {
    private final int id;
    private final String name;
    private String district;
    private List<MenuItems> items;
    private Set<String> categories;
    private  double rating;
    private RestaurantStatus status;

    public Restaurants(int id, String name, String district, List<MenuItems> items, Set<String> categories, double rating, RestaurantStatus status) {
        this.id = id;
        this.name = name;
        this.district = district;
        setItems(items);
        setCategories(categories);
        setRating(rating);
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<MenuItems> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(List<MenuItems> items) {
        if(items==null){
            throw new RuntimeException("items can't be null");
        }else{
            this.items = new ArrayList<>(items);
        }
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if(rating>=0 && rating<=5) {
            this.rating = rating;
        }else {
            throw new RuntimeException("must be between 0 and 5.");
        }
    }

    public RestaurantStatus getStatus() {
        return status;
    }

    public void setStatus(RestaurantStatus status) {
        this.status = status;
    }

    public Set<String> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public void setCategories(Set<String> categories) {
        if(categories==null || categories.isEmpty()){
            throw new RuntimeException("category is empty");
        }else{
            this.categories = categories;
        }
    }

    public void addMenuItem(MenuItems item) {
        if (item == null) {
            throw new RuntimeException("Menu item can't be null.");
        }

        for (MenuItems existingItem : items) {
            if (existingItem.getId() == item.getId()) {
                throw new RuntimeException("Menu item ID already exists.");
            }
        }

        items.add(item);
        categories.add(item.getCategory());
    }
}

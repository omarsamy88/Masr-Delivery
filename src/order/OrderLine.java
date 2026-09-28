package order;

import restaurant.MenuItems;
public record OrderLine(MenuItems item, int quantity) {
    public OrderLine {
        if(item==null){
            throw new RuntimeException("can't be null.");
        }
        if(quantity<=0){
            throw new RuntimeException("must be greater than 0.");
        }
    }
}

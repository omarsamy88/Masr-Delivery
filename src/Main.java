import customer.Address;
import customer.Customers;
import order.OrderLine;
import order.OrderBuilder;
import order.Orders;
import order.OrderStatus;
import restaurant.MenuItems;
import restaurant.MenuItemFactory;
import restaurant.RestaurantStatus;
import restaurant.Restaurants;
import rider.Riders;
import rider.VehicleType;
import java.util.*;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final DeliverySystem system = new DeliverySystem();
    private static int nextOrderId = 1;
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n=================================");
            System.out.println("        MASR DELIVERY");
            System.out.println("=================================");
            System.out.println("1. Customer");
            System.out.println("2. Restaurant");
            System.out.println("3. Rider");
            System.out.println("4. Admin & Reports");
            System.out.println("5. Exit");
            System.out.println("=================================");
            choice = readInt("Choose: ");
            switch (choice) {
                case 1:
                    customerMenu();
                    break;

                case 2:
                    restaurantMenu();
                    break;

                case 3:
                    riderMenu();
                    break;

                case 4:
                    adminMenu();
                    break;

                case 5:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);

        input.close();
    }

    private static void customerMenu() {
        int choice;
        do {
            System.out.println("\n========== CUSTOMER ==========");
            System.out.println("1. Add Customer");
            System.out.println("2. Browse Restaurants");
            System.out.println("3. Search Restaurants");
            System.out.println("4. View Restaurant Menu");
            System.out.println("5. Place Order");
            System.out.println("6. Track Order");
            System.out.println("7. Cancel Order");
            System.out.println("8. View Order History");
            System.out.println("9. View Search History");
            System.out.println("10. Back");
            choice = readInt("Choose: ");
            switch (choice) {
                case 1:
                    addCustomer();
                    break;

                case 2:
                    browseRestaurants();
                    break;

                case 3:
                    searchRestaurants();
                    break;

                case 4:
                    viewMenu();
                    break;

                case 5:
                    placeOrder();
                    break;

                case 6:
                    trackOrder();
                    break;

                case 7:
                    cancelOrder();
                    break;

                case 8:
                    viewOrderHistory();
                    break;

                case 9:
                    viewSearchHistory();
                    break;

                case 10:
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 10);
    }

    private static void addCustomer() {
        System.out.println("\n------ Add Customer ------");
        int id = readInt("Customer ID: ");
        if (system.getCustomerById(id) != null) {
            System.out.println("This ID already exists.");
            return;
        }

        String name = readString("Name: ");
        String phone = readString("Egyptian mobile number: ");
        int wallet = readInt("Wallet balance: ");
        int addressCount = readInt("Number of addresses: ");
        Set<Address> addresses = new HashSet<>();
        for (int i = 0; i < addressCount; i++) {
            System.out.println("\nAddress " + (i + 1));
            String district = readString("District: ");
            String details = readString("Details: ");
            addresses.add(new Address(district, details));
        }
        try {
            Customers customer = new Customers(id, name, phone, addresses, wallet);
            system.addCustomer(customer);
            System.out.println("Customer added successfully.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void browseRestaurants() {
        System.out.println("\n------ Restaurants ------");
        if (system.getRestaurants().isEmpty()) {
            System.out.println("No restaurants available.");
            return;
        }

        for (Restaurants restaurant :
                system.getRestaurantsSorted()) {
            System.out.println("ID: " + restaurant.getId()
                            + " | Name: " + restaurant.getName()
                            + " | District: " + restaurant.getDistrict()
                            + " | Rating: " + restaurant.getRating()
                            + " | Status: " + restaurant.getStatus());
        }
    }

    private static void searchRestaurants() {
        System.out.println("\n------ Search ------");
        String search = readString("Search by name/category/district: ");
        boolean found = false;
        for (Restaurants restaurant : system.getRestaurants().values()) {
            boolean matchName = restaurant.getName().toLowerCase().contains(search.toLowerCase());
            boolean matchDistrict = restaurant.getDistrict().toLowerCase().contains(search.toLowerCase());
            boolean matchCategory = restaurant.getCategories().stream().anyMatch(category ->
                                    category.toLowerCase().contains(search.toLowerCase()));
            if (matchName || matchDistrict || matchCategory) {
                System.out.println(restaurant.getId()
                                + " | "
                                + restaurant.getName()
                                + " | "
                                + restaurant.getDistrict());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No restaurant found.");
        }
    }

    private static void viewMenu() {
        System.out.println("\n------ Restaurant Menu ------");
        int restaurantId = readInt("Restaurant ID: ");
        Restaurants restaurant = system.getRestaurantById(restaurantId);
        if (restaurant == null) {
            System.out.println("Restaurant not found.");
            return;
        }
        System.out.println("\n" + restaurant.getName());
        for (MenuItems item : restaurant.getItems()) {
            System.out.println("ID: " + item.getId()
                            + " | " + item.getName()
                            + " | Price: " + item.getPrice() + " EGP"
                            + " | Category: " + item.getCategory()
                            + " | Available: " + item.isAvailability());
        }
    }

    private static void placeOrder() {
        System.out.println("\n------ Place Order ------");
        int customerId = readInt("Customer ID: ");
        Customers customer = system.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        int restaurantId =
                readInt("Restaurant ID: ");

        Restaurants restaurant =
                system.getRestaurantById(restaurantId);

        if (restaurant == null) {

            System.out.println(
                    "Restaurant not found."
            );

            return;
        }

        if (restaurant.getStatus()
                != RestaurantStatus.OPEN) {

            System.out.println(
                    "Restaurant is closed."
            );

            return;
        }

        System.out.println("\nAvailable addresses:");

        List<Address> addresses =
                new ArrayList<>(
                        customer.getAddresses()
                );

        for (int i = 0; i < addresses.size(); i++) {

            System.out.println(
                    (i + 1)
                            + ". "
                            + addresses.get(i).district()
                            + " - "
                            + addresses.get(i).details()
            );
        }

        int addressChoice =
                readInt("Choose address: ");

        if (addressChoice < 1 ||
                addressChoice > addresses.size()) {

            System.out.println(
                    "Invalid address."
            );

            return;
        }

        Address address =
                addresses.get(addressChoice - 1);

        OrderBuilder builder =
                new OrderBuilder(
                        nextOrderId,
                        customer,
                        restaurant
                );

        builder.deliveryAddress(address);

        int itemCount =
                readInt("Number of different items: ");

        if (itemCount <= 0) {

            System.out.println(
                    "Order must contain at least one item."
            );

            return;
        }

        for (int i = 0; i < itemCount; i++) {

            int itemId =
                    readInt("Item ID: ");

            MenuItems selectedItem = null;

            for (MenuItems item :
                    restaurant.getItems()) {

                if (item.getId() == itemId) {

                    selectedItem = item;
                    break;
                }
            }

            if (selectedItem == null) {

                System.out.println(
                        "Item not found."
                );

                return;
            }

            if (!selectedItem.isAvailability()) {

                System.out.println(
                        "Item is not available."
                );

                return;
            }

            int quantity =
                    readInt("Quantity: ");

            if (quantity <= 0) {

                System.out.println(
                        "Quantity must be greater than 0."
                );

                return;
            }

            builder.addItem(
                    new OrderLine(
                            selectedItem,
                            quantity
                    )
            );
        }

        try {

            Orders order =
                    builder.build();

            system.addOrder(order);

            nextOrderId++;

            System.out.println(
                    "\nOrder created successfully!"
            );

            System.out.println(
                    "Order ID: "
                            + order.getId()
            );

            System.out.println(
                    "Subtotal: "
                            + order.calculateSubtotal()
                            + " EGP"
            );

        } catch (RuntimeException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void trackOrder() {

        int orderId =
                readInt("Order ID: ");

        Orders order =
                system.getOrderById(orderId);

        if (order == null) {

            System.out.println(
                    "Order not found."
            );

            return;
        }

        System.out.println(
                "Order ID: " + order.getId()
        );

        System.out.println(
                "Status: " + order.getStatus()
        );

        System.out.println(
                "Placed at: " + order.getPlacedAt()
        );

        System.out.println(
                "Subtotal: "
                        + order.calculateSubtotal()
                        + " EGP"
        );
    }

    private static void cancelOrder() {

        int orderId =
                readInt("Order ID: ");

        Orders order =
                system.getOrderById(orderId);

        if (order == null) {

            System.out.println(
                    "Order not found."
            );

            return;
        }

        try {

            order.setStatus(
                    OrderStatus.CANCELLED
            );

            System.out.println(
                    "Order cancelled."
            );

        } catch (RuntimeException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void viewOrderHistory() {

        int customerId =
                readInt("Customer ID: ");

        Customers customer =
                system.getCustomerById(customerId);

        if (customer == null) {

            System.out.println(
                    "Customer not found."
            );

            return;
        }

        boolean found = false;

        for (Orders order :
                system.getOrders().values()) {

            if (order.getCustomer()
                    .equals(customer)) {

                System.out.println(
                        "Order ID: "
                                + order.getId()
                                + " | Status: "
                                + order.getStatus()
                                + " | Subtotal: "
                                + order.calculateSubtotal()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    "No orders found."
            );
        }
    }

    private static void viewSearchHistory() {

        int customerId =
                readInt("Customer ID: ");

        Customers customer =
                system.getCustomerById(customerId);

        if (customer == null) {

            System.out.println(
                    "Customer not found."
            );

            return;
        }

        System.out.println(
                "\nSearch history:"
        );

        for (String search :
                customer.getSearchHistory()) {

            System.out.println(search);
        }
    }

    private static void restaurantMenu() {

        int choice;

        do {

            System.out.println("\n========== RESTAURANT ==========");
            System.out.println("1. Add Restaurant");
            System.out.println("2. Add Menu Item");
            System.out.println("3. Change Restaurant Status");
            System.out.println("4. Accept Order");
            System.out.println("5. Mark Preparing");
            System.out.println("6. Mark Ready");
            System.out.println("7. Toggle Item Availability");
            System.out.println("8. Back");

            choice = readInt("Choose: ");

            switch (choice) {

                case 1:
                    addRestaurant();
                    break;

                case 2:
                    addMenuItem();
                    break;

                case 3:
                    changeRestaurantStatus();
                    break;

                case 4:
                    changeOrderStatus(
                            OrderStatus.ACCEPTED
                    );
                    break;

                case 5:
                    changeOrderStatus(
                            OrderStatus.PREPARING
                    );
                    break;

                case 6:
                    changeOrderStatus(
                            OrderStatus.READY
                    );
                    break;

                case 7:
                    toggleItemAvailability();
                    break;

                case 8:
                    break;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }

        } while (choice != 8);
    }

    private static void addRestaurant() {

        System.out.println("\n------ Add Restaurant ------");

        int id =
                readInt("Restaurant ID: ");

        if (system.getRestaurantById(id) != null) {

            System.out.println(
                    "This ID already exists."
            );

            return;
        }

        String name =
                readString("Name: ");

        String district =
                readString("District: ");

        int categoryCount =
                readInt("Number of categories: ");

        Set<String> categories =
                new HashSet<>();

        for (int i = 0; i < categoryCount; i++) {

            categories.add(
                    readString(
                            "Category "
                                    + (i + 1)
                                    + ": "
                    )
            );
        }

        double rating =
                readDouble("Rating: ");

        Restaurants restaurant =
                new Restaurants(
                        id,
                        name,
                        district,
                        new ArrayList<>(),
                        categories,
                        rating,
                        RestaurantStatus.OPEN
                );
        system.addRestaurant(restaurant);

        System.out.println(
                "Restaurant added."
        );
    }


    private static void addMenuItem() {

        int restaurantId =
                readInt("Restaurant ID: ");

        Restaurants restaurant =
                system.getRestaurantById(restaurantId);

        if (restaurant == null) {

            System.out.println(
                    "Restaurant not found."
            );

            return;
        }

        int id =
                readInt("Item ID: ");

        String type =
                readString(
                        "Type (standard/combo/weighted): "
                );

        String name =
                readString("Name: ");

        double price =
                readDouble("Price: ");

        String category =
                readString("Category: ");

        int time =
                readInt("Preparation time: ");

        boolean available =
                readBoolean("Available? (true/false): ");

        double extraValue = 0;

        if (type.equalsIgnoreCase("combo")) {

            extraValue =
                    readDouble("Discount percentage: ");

        } else if (
                type.equalsIgnoreCase("weighted")
        ) {

            extraValue =
                    readDouble("Weight: ");
        }

        try {

            MenuItems item =
                    MenuItemFactory.createItem(
                            type,
                            id,
                            name,
                            price,
                            category,
                            time,
                            available,
                            extraValue
                    );

            restaurant.addMenuItem(item);

            System.out.println(
                    "Menu item added."
            );

        } catch (RuntimeException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void changeRestaurantStatus() {

        int id =
                readInt("Restaurant ID: ");

        Restaurants restaurant =
                system.getRestaurantById(id);

        if (restaurant == null) {

            System.out.println(
                    "Restaurant not found."
            );

            return;
        }

        String status =
                readString(
                        "Enter OPEN or CLOSED: "
                );

        try {

            restaurant.setStatus(
                    RestaurantStatus.valueOf(
                            status.toUpperCase()
                    )
            );

            System.out.println(
                    "Status updated."
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Invalid status."
            );
        }
    }

    private static void changeOrderStatus(
            OrderStatus newStatus) {

        int orderId =
                readInt("Order ID: ");

        Orders order =
                system.getOrderById(orderId);

        if (order == null) {

            System.out.println(
                    "Order not found."
            );

            return;
        }

        try {

            order.setStatus(newStatus);

            System.out.println(
                    "Order status changed to "
                            + newStatus
            );

        } catch (RuntimeException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    private static void toggleItemAvailability() {

        int restaurantId =
                readInt("Restaurant ID: ");

        Restaurants restaurant =
                system.getRestaurantById(
                        restaurantId
                );

        if (restaurant == null) {

            System.out.println(
                    "Restaurant not found."
            );

            return;
        }

        int itemId =
                readInt("Item ID: ");

        for (MenuItems item :
                restaurant.getItems()) {

            if (item.getId() == itemId) {

                item.setAvailability(
                        !item.isAvailability()
                );

                System.out.println(
                        "Availability is now: "
                                + item.isAvailability()
                );

                return;
            }
        }

        System.out.println(
                "Item not found."
        );
    }

    private static void riderMenu() {

        int choice;

        do {

            System.out.println("\n========== RIDER ==========");
            System.out.println("1. Add Rider");
            System.out.println("2. Go On Duty");
            System.out.println("3. Go Off Duty");
            System.out.println("4. View Riders");
            System.out.println("5. Update District");
            System.out.println("6. Back");

            choice = readInt("Choose: ");

            switch (choice) {

                case 1:
                    addRider();
                    break;

                case 2:
                    changeRiderAvailability(true);
                    break;

                case 3:
                    changeRiderAvailability(false);
                    break;

                case 4:
                    viewRiders();
                    break;

                case 5:
                    updateRiderDistrict();
                    break;

                case 6:
                    break;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }

        } while (choice != 6);
    }

    private static void addRider() {

        int id =
                readInt("Rider ID: ");

        if (system.getRiderById(id) != null) {

            System.out.println(
                    "This ID already exists."
            );

            return;
        }

        String name =
                readString("Name: ");

        String type =
                readString(
                        "Vehicle (MOTORCYCLE/BICYCLE/CAR): "
                );

        VehicleType vehicleType;

        try {

            vehicleType =
                    VehicleType.valueOf(
                            type.toUpperCase()
                    );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Invalid vehicle type."
            );

            return;
        }

        String district =
                readString("Current district: ");

        Riders rider =
                new Riders(
                        id,
                        name,
                        vehicleType,
                        district,
                        true,
                        0
                );

        system.addRider(rider);

        System.out.println(
                "Rider added."
        );
    }

    private static void changeRiderAvailability(
            boolean availability) {

        int id =
                readInt("Rider ID: ");

        Riders rider =
                system.getRiderById(id);

        if (rider == null) {

            System.out.println(
                    "Rider not found."
            );

            return;
        }

        rider.setAvailability(
                availability
        );

        System.out.println(
                "Availability: "
                        + availability
        );
    }

    private static void viewRiders() {

        System.out.println("\n------ Riders ------");

        for (Riders rider :
                system.getRiders().values()) {

            System.out.println(
                    "ID: " + rider.getId()
                            + " | "
                            + rider.getName()
                            + " | Vehicle: "
                            + rider.getType()
                            + " | District: "
                            + rider.getCurrentDistrict()
                            + " | Available: "
                            + rider.isAvailability()
                            + " | Deliveries: "
                            + rider.getCountOfRides()
            );
        }
    }

    private static void updateRiderDistrict() {

        int id =
                readInt("Rider ID: ");

        Riders rider =
                system.getRiderById(id);

        if (rider == null) {

            System.out.println(
                    "Rider not found."
            );

            return;
        }

        String district =
                readString("New district: ");

        rider.setCurrentDistrict(
                district
        );

        System.out.println(
                "District updated."
        );
    }

    private static void adminMenu() {

        int choice;

        do {

            System.out.println("\n========== ADMIN & REPORTS ==========");
            System.out.println("1. View Restaurants");
            System.out.println("2. View Customers");
            System.out.println("3. View Riders");
            System.out.println("4. View Orders");
            System.out.println("5. Platform Statistics");
            System.out.println("6. Back");

            choice = readInt("Choose: ");

            switch (choice) {

                case 1:
                    browseRestaurants();
                    break;

                case 2:
                    viewCustomers();
                    break;

                case 3:
                    viewRiders();
                    break;

                case 4:
                    viewAllOrders();
                    break;

                case 5:
                    platformStatistics();
                    break;

                case 6:
                    break;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }

        } while (choice != 6);
    }


    private static void viewCustomers() {

        System.out.println("\n------ Customers ------");

        for (Customers customer :
                system.getCustomers().values()) {

            System.out.println(
                    "ID: " + customer.getId()
                            + " | "
                            + customer.getName()
                            + " | Wallet: "
                            + customer.getWalletBalance()
            );
        }
    }


    private static void viewAllOrders() {

        System.out.println("\n------ Orders ------");

        for (Orders order :
                system.getOrders().values()) {

            System.out.println(
                    "ID: " + order.getId()
                            + " | Status: "
                            + order.getStatus()
                            + " | Subtotal: "
                            + order.calculateSubtotal()
            );
        }
    }


    private static void platformStatistics() {

        System.out.println("\n------ Statistics ------");

        System.out.println(
                "Restaurants: "
                        + system.getRestaurants().size()
        );

        System.out.println(
                "Customers: "
                        + system.getCustomers().size()
        );

        System.out.println(
                "Riders: "
                        + system.getRiders().size()
        );

        System.out.println(
                "Orders: "
                        + system.getOrders().size()
        );
    }

    private static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        input.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Enter a valid number."
                );
            }
        }
    }


    private static double readDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Double.parseDouble(
                        input.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Enter a valid number."
                );
            }
        }
    }


    private static String readString(String message) {

        System.out.print(message);

        return input.nextLine().trim();
    }


    private static boolean readBoolean(String message) {

        while (true) {

            String value =
                    readString(message);

            if (value.equalsIgnoreCase("true")) {
                return true;
            }

            if (value.equalsIgnoreCase("false")) {
                return false;
            }

            System.out.println(
                    "Enter true or false."
            );
        }
    }
}
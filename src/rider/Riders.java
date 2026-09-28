package rider;

import Exceptions.RiderBusyException;
import order.Orders;

public class Riders {
    private final int id;
    private final String name;
    private VehicleType type;
    private String currentDistrict;
    private boolean availability;
    private int countOfRides;
    private Orders activeOrder;

    public Riders(int id, String name, VehicleType type, String currentDistrict, boolean availability, int countOfRides) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.currentDistrict = currentDistrict;
        this.availability = availability;
        setCountOfRides(countOfRides);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public String getCurrentDistrict() {
        return currentDistrict;
    }

    public void setCurrentDistrict(String currentDistrict) {
        this.currentDistrict = currentDistrict;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public int getCountOfRides() {
        return countOfRides;
    }

    public void setCountOfRides(int countOfRides) {
        if (countOfRides < 0) {
            throw new RuntimeException("can't be negative.");
        }else{
            this.countOfRides=countOfRides;
        }
    }

    public Orders getActiveOrder() {
        return activeOrder;
    }

    public void assignOrder(Orders order) {

        if (!availability || activeOrder != null) {
            throw new RiderBusyException("Rider is not available.");
        }
        activeOrder = order;
        availability = false;
    }

    public void completeDelivery() {

        if (activeOrder == null) {
            throw new IllegalStateException("Rider has no active order.");
        }
        activeOrder = null;
        availability = true;
        countOfRides++;
    }


}

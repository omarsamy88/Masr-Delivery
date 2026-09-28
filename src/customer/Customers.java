package customer;

import java.util.*;

public class Customers {
    private final int id;
    private final String name;
    private final String egyptionMobileNumber;
    private Set<Address> addresses;
    private double walletBalance;
    private LoyaltyTier tier= LoyaltyTier.BRONZE;
    private final Deque<String> searchHistory;
    private int completedOrders;

    public Customers(int id, String name, String egyptionMobileNumber, Set<Address> addresses, double walletBalance) {
        this.id = id;
        this.name = name;
        if(egyptionMobileNumber.length()==11 && (egyptionMobileNumber.substring(0, 3).equals("010") || egyptionMobileNumber.substring(0, 3).equals("011") ||egyptionMobileNumber.substring(0,3).equals("012")|| egyptionMobileNumber.substring(0, 3).equals("015")) ){
            this.egyptionMobileNumber = egyptionMobileNumber;
        }else {
            throw new RuntimeException("must be 11 numbers and begin with (010,011,012,015).");
        }
        setAddresses(addresses);
        setWalletBalance(walletBalance);
        searchHistory = new ArrayDeque<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEgyptionMobileNumber() {
        return egyptionMobileNumber;
    }

    public Set<Address> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    public void setAddresses(Set<Address> addresses) {
        if (addresses == null || addresses.isEmpty()) {
            throw new RuntimeException("Customer must have at least one address.");
        } else {
            this.addresses = addresses;
        }
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        if(walletBalance>=0){
            this.walletBalance = walletBalance;
        }else{
            throw new RuntimeException("can't be negative.");
        }
    }

    public LoyaltyTier getTier() {
        return tier;
    }

    public void updateTier(int completedOrder) {
        if(completedOrder>=0&&completedOrder<=9){
            this.tier= LoyaltyTier.BRONZE;
        } else if (completedOrder>=10&&completedOrder<=29) {
            this.tier= LoyaltyTier.SILVER;
        } else if (completedOrder>=30) {
            this.tier= LoyaltyTier.GOLD;
        }
    }

    public void addSearch(String search) {
        searchHistory.addFirst(search);

        if (searchHistory.size() > 5) {
            searchHistory.removeLast();
        }
    }

    public List<String> getSearchHistory() {
        return List.copyOf(searchHistory);
    }

    public int getCompletedOrders() {
        return completedOrders;
    }

    public void completedOrder() {

        completedOrders++;

        updateTier(completedOrders);
    }


}

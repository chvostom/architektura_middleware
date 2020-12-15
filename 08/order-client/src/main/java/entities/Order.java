package entities;

public class Order {

    public Order(int id, String type, String description, String customer, int price) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.customer = customer;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getCustomer() {
        return customer;
    }

    public int getPrice() {
        return price;
    }

    private final int id;
    private final String type;
    private final String description;
    private final String customer;
    private final int price;

}

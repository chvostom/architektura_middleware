package entities;

public class Order {

    public Order(int id, Object type, Object description, Object customer, int price) {
        this.id = id;
        this.type = (String) type;
        this.description = (String) description;
        this.customer = (String) customer;
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

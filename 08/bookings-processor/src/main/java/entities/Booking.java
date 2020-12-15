package entities;

public class Booking {

    public Booking(int id, Object description, Object customer, int price) {
        this.id = id;
        this.description = (String) description;
        this.customer = (String) customer;
        this.price = price;
    }

    public int getId() {
        return id;
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

    public String getSummary( ){
        return "Booking with id: " +
                this.id +
                " description: " +
                this.description +
                " customer: " +
                this.customer +
                " price: " +
                this.price ;
    }

    private final int id;
    private final String description;
    private final String customer;
    private final int price;
}

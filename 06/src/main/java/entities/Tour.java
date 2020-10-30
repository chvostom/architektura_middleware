package entities;

public class Tour {

    public Tour(int id, String location, String startDate, String endDate, String transport) {
        this.id = id;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.transport = transport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    private int id;
    private String location;
    private String startDate;
    private String endDate;
    private String transport;

}

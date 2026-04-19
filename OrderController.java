package BusinessLayer;

public class Contact {
    private int id;
    private String name;
    private Integer phoneNumber;
    private String address;

    public Contact(int id, String name, Integer phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void display() {
        System.out.printf(
                "Contact: %s (ID: %d)%nPhone: %s%nAddress: %s%n",
                name, id,
                phoneNumber != null ? phoneNumber.toString() : "N/A",
                address != null ? address : "N/A"
        );
    }
}




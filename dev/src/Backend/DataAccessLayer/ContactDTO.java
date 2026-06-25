package Backend.DataAccessLayer;

import Backend.BusinessLayer.Stock.objects.*;

public class ContactDTO {
    private int contact_id;
    private String name;
    private String Phone_number;
    private String email;
    private int supid;

    public ContactDTO(int supllierid,int contact_id, String name, String phone_number, String email) {
        this.contact_id = contact_id;
        this.name = name;
        Phone_number = phone_number;
        this.email = email;
        this.supid=supllierid;
        }

    @Override
    public String toString() {
        return "Contact{" +
                "contact_id=" + contact_id +
                ", name='" + name + '\'' +
                ", Phone_number='" + Phone_number + '\'' +
                ", email='" + email + '\'' +
                ", supid=" + supid +
                '}';
    }

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Contact objectDTOContact(){
        return new Contact(contact_id, name,Phone_number, email);
    }

    public int getSupid() {
        return supid;
    }

    public void setSupid(int supid) {
        this.supid = supid;
    }
}

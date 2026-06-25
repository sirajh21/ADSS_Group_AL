package Backend.DataAccessLayer;

import Backend.BusinessLayer.Stock.objects.*;

public class Product1DTO {
    private int product_id;
    private int price;
    private String product_name;

    public Product1DTO(int product_id, int price, String product_name) {
        this.product_id = product_id;
        this.price = price;
        this.product_name = product_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getList_price() {
        return price;
    }

    public void setList_price(int list_price) {
        this.price = list_price;
    }

    public Product1 objectDTOProduct(){
        return new Product1(product_id, price,product_name);
    }
}

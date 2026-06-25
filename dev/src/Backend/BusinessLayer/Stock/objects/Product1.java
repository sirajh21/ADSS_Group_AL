package Backend.BusinessLayer.Stock.objects;

import Backend.DataAccessLayer.*;


public class Product1 {
    private int product_id;
    private int price;
    private String product_name;

    public Product1(int product_id, int price, String product_name) {
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

    @Override
    public String toString() {
        return product_name +"{" +
                "ID=" + product_id +
                ", price=" + price +
                " }";
    }

    public Product1DTO objectDTOProduct(){
        return new Product1DTO(product_id, price, product_name);
    }

}

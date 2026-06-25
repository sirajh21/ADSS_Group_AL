package Backend.DataAccessLayer;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContractDTO {
    private int supplier_id;
    private String contract_name;
    private boolean scheduled;
    private String productsNumber ;
    private String productQuantityDiscount;
    private String productQuantityDiscount2;


    public ContractDTO(String contract_name, int supplier_id, boolean scheduled, String productsNumber , String productQuantityDiscount, String productQuantityDiscount2)
     {
        this.contract_name = contract_name;
        this.supplier_id = supplier_id;
        this.scheduled = scheduled;
        this.productsNumber = productsNumber;
        this.productQuantityDiscount = productQuantityDiscount;
        this.productQuantityDiscount2 = productQuantityDiscount2;

    }


    public int getSupplier_id() {
        return supplier_id;
    }

    public String getContract_name() {
        return contract_name;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public String getProductsNumber() {
        return productsNumber;
    }


    public  String getProductQuantityDiscount() {
        return productQuantityDiscount;
    }


    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }


    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }


    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }


    public void setProductsNumber(String productsNumber) {
        this.productsNumber = productsNumber;
    }


    public void setProductQuantityDiscount(String productQuantityDiscount) {
        this.productQuantityDiscount = productQuantityDiscount;
    }


    public String getProductQuantityDiscount2() {
        return productQuantityDiscount2;
    }


    public void setProductQuantityDiscount2(String productQuantityDiscount2) {
        this.productQuantityDiscount2 = productQuantityDiscount2;
    }

}

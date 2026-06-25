package Backend.BusinessLayer.Stock.objects;

import Backend.DataAccessLayer.*;

import java.util.*;

public class Contract {
    private int supplier_id;
    private String contract_name;
    private boolean scheduled;
    private ArrayList<Product1> productsNumber = new ArrayList<>();
    private String productsNumberString;
    private HashMap<Product1, HashMap<Integer, Double>> productQuantityDiscount= new HashMap<>();
    private String productQuantityDiscountString;

    public Contract(String contract_name, int supplier_id, boolean scheduled, ArrayList<Product1> productsNumber , HashMap<Product1, HashMap<Integer, Double>> productQuantityDiscount) {
        this.contract_name = contract_name;
        this.supplier_id = supplier_id;
        this.scheduled = scheduled;
        this.productsNumber = productsNumber;
        this.productQuantityDiscount = productQuantityDiscount;
    }

    public Contract(String contract_name, int supplier_id, boolean scheduled, String productsNumber , String productQuantityDiscount) {
        this.contract_name = contract_name;
        this.supplier_id = supplier_id;
        this.scheduled = scheduled;
        this.productsNumberString = productsNumber;
        this.productQuantityDiscountString = productQuantityDiscount;
    }

    public double checkPrice(HashMap<Integer, Integer> productAndQuantity) {
        double price = 0;
        for (Integer product_id : productAndQuantity.keySet()) {
            for (Product1 p : productQuantityDiscount.keySet()) {
                if (p.getProduct_id() == product_id) {
                    int amount_product = productAndQuantity.get(product_id);
                    Map<Integer, Double> quantityDiscountPerProduct = productQuantityDiscount.get(p);
                    if (quantityDiscountPerProduct.isEmpty()) {
                        price += (double) amount_product * p.getList_price();
                    } else {
                        double discount;
                        if (!quantityDiscountPerProduct.containsKey(amount_product)){
                            discount = findNearestDiscount(quantityDiscountPerProduct.keySet(), amount_product, quantityDiscountPerProduct);


                        }else{
                            discount = quantityDiscountPerProduct.get(amount_product);

                        }
                         price += ((100-discount)/100) * amount_product * p.getList_price();

                    }

                }
            }
        }
        return price;
    }
    private Double findNearestDiscount(Set<Integer> availableQuantities, int amount, Map<Integer,Double> quantityDiscountPerProduct) {
        Double nearestDiscount = null;
        int minDifference = Integer.MAX_VALUE;

        for (Integer quantity : availableQuantities) {
            int difference = Math.abs(quantity - amount);
            if (difference < minDifference) {
                minDifference = difference;
                nearestDiscount = quantityDiscountPerProduct.get(quantity);
            }
        }

        return nearestDiscount;
    }
    @Override
    public String toString() {
        String result = "Contract<"+ contract_name +">{" +
                "supplier_id=" + supplier_id +
                ", is scheduled=" + scheduled +
                ", Number Of Products=" + productsNumber.size() +"}->\n"+
                "Discount for each product -> {\n  "  ;

        for (Map.Entry<Product1, HashMap<Integer, Double>> entry : productQuantityDiscount.entrySet()) {
            Product1 product = entry.getKey();
            Map<Integer, Double> quantityDiscountMap = entry.getValue();


            for (Map.Entry<Integer, Double> discountEntry : quantityDiscountMap.entrySet()) {
                Integer quantity = discountEntry.getKey();
                Double discount = discountEntry.getValue();

                result += product.toString()  + " with discount of " + discount + "% when buying " + quantity + " items or more\n";
            }
        }

        result += "}\n";
        return result;
    }


    public void AddProducts(Product1 p, HashMap <Integer,Double> quantityDiscount){
        if (!productsNumber.contains(p)){
            productsNumber.add(p);
            productQuantityDiscount.put(p, quantityDiscount);
        }
    }

    public  Product1 getProduct( int product_id){
        for(Product1 p: productsNumber){
            if (p.getProduct_id() == product_id){
                return p;
            }
        }
        return null;
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

    public ArrayList<Product1> getProductsNumber() {
        return productsNumber;
    }

    public  Map<Integer, Double> getProductQuantityDiscount(Product1 p) {
        return productQuantityDiscount.get(p);
    }
    public  HashMap<Product1, HashMap<Integer, Double>> getProductQuantityDiscount() {
        return productQuantityDiscount;
    }

    public ContractDTO objectDTOContract(){
        List<Double> a=new LinkedList<>();
        List<Integer> b=new LinkedList<>();

        for (HashMap<Integer,Double> double1 : productQuantityDiscount.values()) {
            for (Double d : double1.values()) {
                a.add(d);
            
        }}
        for (HashMap<Integer,Double> double1 : productQuantityDiscount.values()) {
            for (Integer i : double1.keySet()) {
                b.add(i);
            
        }}
        return new ContractDTO(contract_name,supplier_id,scheduled,productsNumber.toString(),a.toString(),b.toString());
    }
    }


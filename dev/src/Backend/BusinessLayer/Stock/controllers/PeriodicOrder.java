package Backend.BusinessLayer.Stock.controllers;

import Backend.BusinessLayer.Stock.objects.Order;
import Backend.BusinessLayer.Stock.objects.Supplier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PeriodicOrder extends Order {
    int  arrival_day; ///day of the week

    public PeriodicOrder(int id, List<Integer> products_id, Supplier supplier, HashMap<Integer, Integer> productsAndAmount, LocalDate date, int  arrivalday) {
        super(id,products_id, supplier, productsAndAmount, date);
        this.arrival_day = arrivalday;
    }

    public  PeriodicOrder(PeriodicOrder po){
        super(po);
        this.arrival_day = po.arrival_day;

    }


    public int getArrival_day() {
        return arrival_day;
    }

    public void setArrival_day(int arrival_day) {
        this.arrival_day = arrival_day;
    }

    public boolean isEditable(){
        LocalDate date= LocalDate.now();
        Integer day= date.getDayOfWeek().getValue() + 1;
        if (day.intValue() == arrival_day){

                return false;

        }
        return true;
    }

    public boolean updateArrival_day(int day){
        if(isEditable()){
            this.arrival_day =day;
            return true;
        }
        return false;
    }

    public PeriodicOrder update_periodic_order(HashMap<Integer, Integer> products_And_Amount){
        if (isEditable()){
            List<Integer> product_id= new ArrayList<>();
            for(Integer productId: products_And_Amount.keySet()){
                product_id.add(productId);
            }
            PeriodicOrder po= new PeriodicOrder(this.getOrder_id(),product_id, this.getSupplier(), products_And_Amount, this.getDate(),this.getArrival_day());
          //  System.out.println(po);
            return po;
        }
        return null;
    }

    @Override
    public boolean isPeriodicOrder() {return true;};

}

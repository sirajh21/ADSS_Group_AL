package Backend.DataAccessLayer;

import Backend.BusinessLayer.Stock.objects.*;

public class SuppliersDTO {
    private static int idcounter = 1;
    private int supplier_id;
    private String name;
    private String phoneNumber;
    private SupplierType supplierType;
    private int supplierTypeInt;
    private String BankAccount;
    private String paymentType;



    public SuppliersDTO(String name, String phoneNumber, int intSupplierType, String BankAccount, String paymentType) {
        this.supplier_id = idcounter++;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.supplierType = convert_type(intSupplierType);
        this.supplierTypeInt = intSupplierType;
        this.BankAccount = BankAccount;
        this.paymentType = paymentType;

    }

    @Override
    public String toString(){
        return "Supplier ID:" + supplier_id +"\nSupplier Name:" + name + "\nPhone Number:" + phoneNumber+  "\nBank Account:" +
                BankAccount+ "\nPayment Type:" + paymentType + "\n----------------\n";
    }


    public SupplierType convert_type(int intsuppliertype){
        switch (intsuppliertype){
            case 1:
                return SupplierType.ScheduledSupplier;
            case 2:
                return SupplierType.OnDemandOrdering;
            case 3:
                return SupplierType.PickAndDelivery;
        }
        return null;
    }


    public int getSupplier_id() {
        return supplier_id;
    }

    public String getName() {
        return name;
    }

    public SupplierType getSupplierType() {
        return supplierType;
    }

    public int getSupplierTypeInt(){return this.supplierTypeInt;}
    public String getBankAccount() {
        return BankAccount;
    }

    public String getPaymentType() {
        return paymentType;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }
}

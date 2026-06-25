package Backend.BusinessLayer.Stock.objects;

import Backend.DataAccessLayer.*;

import java.util.ArrayList;

public class Supplier {
    // private static int idcounter = 1;
    private int supplier_id;
    private String name;
    private String phoneNumber;
    private SupplierType supplierType;
    private int supplierTypeInt;
    private String BankAccount;
    private String paymentType;
    //    private Contract contract;
    private ArrayList<Contract> SuppContracts;
    private ArrayList<String> companies = new ArrayList<>();
    private ArrayList<Contact> Contacts = new ArrayList<>();


    public Supplier(int supplier_id, String name, String phoneNumber, int intSupplierType, String BankAccount, String paymentType, ArrayList<String> companies) {
        this.supplier_id = supplier_id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.supplierType = convert_type(intSupplierType);
        this.supplierTypeInt = intSupplierType;
        this.BankAccount = BankAccount;
        this.paymentType = paymentType;
        this.companies = companies;
        this.SuppContracts = new ArrayList<Contract>();
    }

    public Supplier(SuppliersDTO suppliersDTO) {
        this.supplier_id = suppliersDTO.getSupplier_id();
        this.name = suppliersDTO.getName();
        this.phoneNumber = suppliersDTO.getPhoneNumber();
        this.supplierType = suppliersDTO.getSupplierType();
        this.BankAccount = suppliersDTO.getBankAccount();
        this.paymentType = suppliersDTO.getPaymentType();
        this.SuppContracts = new ArrayList<Contract>();
    }

    public String toString() {
        return "Supplier ID:" + supplier_id + "\nSupplier Name:" + name + "\nPhone Number:" + phoneNumber + "\nBank Account:" +
                BankAccount + "\nPayment Type:" + paymentType + "\n----------------\n";
    }
//    public void newContract(Contract contract) {
//        this.contract = contract;
//    }

    public void AddContract(Contract c){
        this.SuppContracts.add(c);
    }
    public Contract getContractByName(String name) {
        for (Contract c : SuppContracts) {
            if (c.getContract_name().equals(name)) {
                return c;
            }
        }
        return null;
    }
    public void deleteContract(Contract contract) {
        this.SuppContracts.remove(contract);
    }

    public SupplierType convert_type(int intsuppliertype) {
        switch (intsuppliertype) {
            case 1:
                return SupplierType.ScheduledSupplier;
            case 2:
                return SupplierType.OnDemandOrdering;
            case 3:
                return SupplierType.PickAndDelivery;
        }
        return null;
    }

    public void addcompany(String company_name) throws IllegalAccessException {
        if (!companies.contains(company_name)) {
            companies.add(company_name);
        } else {
            throw new IllegalAccessException();
        }
    }

    public void deletecompany(String company_name) throws IllegalAccessException {
        if (companies.contains(company_name)) {
            companies.remove(company_name);
        } else {
            throw new IllegalAccessException();
        }
    }

    public void addcontact(int contact_id, String name, String phone_number, String email) {
        this.Contacts.add(new Contact(contact_id, name, phone_number, email));
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

    public String getBankAccount() {
        return BankAccount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public ArrayList<Contract> getContracts() {
        return SuppContracts;
    }

    public ArrayList<String> getCompanies() {
        return companies;
    }

    public ArrayList<Contact> getContacts() {
        return Contacts;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public SuppliersDTO objectDTO() {
        return new SuppliersDTO(name, phoneNumber, supplierTypeInt, BankAccount, paymentType);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }


}

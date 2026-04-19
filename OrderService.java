package BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Supplier {
    private int SupId;
    private String SupplierName;
    private String SupplierAddress;
    private String SupplierPhoneNumber;
    private EnumSuppType SupplierType;
    private BankDetails bankDetails;
    private ArrayList<Integer> contactsPhoneNum;
    private int contractNum;
    private Map<Integer, Product> SuppProducts;
    private ArrayList<Integer> SupplierProductsNumbers;
    private ArrayList<Contract> SuppContracts;
    private ArrayList<String> companies;
    private ArrayList<WorkDays> SuppWorkDays;

    public Supplier(int id, String name, String address, String PhoneNumber, BankDetails BankAccount, ArrayList<Integer> contacts) {
        this.SupId = id;
        this.SupplierName = name;
        this.SupplierAddress = address;
        this.SupplierPhoneNumber = PhoneNumber;
        this.SupplierType = null;
        this.bankDetails = BankAccount;
        this.contactsPhoneNum = contacts;
        this.SuppContracts = new ArrayList<Contract>();
        this.SuppWorkDays = null;
        this.companies = new ArrayList<String>();
        this.SuppProducts = new HashMap<>();
        this.contractNum = 0;
        this.SupplierProductsNumbers = new ArrayList<>();
    }

    public void MakeContract(String ContractName) {
        if (this.SupplierType == EnumSuppType.ScheduledSupplier) {
            Contract newContract = new Contract(this.SupId, ContractName, this.SupplierType, this.SuppWorkDays);
            this.contractNum++;
        }
        if (this.SupplierType == EnumSuppType.OnDemandOrdering) {
            Contract newContract = new Contract(this.SupId, ContractName, this.SupplierType, null);
            contractNum++;
        }
        if (this.SupplierType == EnumSuppType.PickAndDeliver) {
            Contract newContract = new Contract(this.SupId, ContractName, this.SupplierType, null);
            contractNum++;
        }

    }


    public void setSupplierPhoneNumber(String supplierPhoneNumber) {
        SupplierPhoneNumber = supplierPhoneNumber;
    }

    //getters
    public int getSupId() {
        return this.SupId;
    }

    public ArrayList<WorkDays> getSuppWorkDays() {
        return this.SuppWorkDays;
    }


    public String getSupplierName() {
        return this.SupplierName;
    }

    public ArrayList<Contract> getSuppContracts() {
        return this.SuppContracts;
    }

    public ArrayList<String> companies() {
        return this.companies;
    }

    public String getSuppAdress() {
        return this.SupplierAddress;
    }

    public Map<Integer, Product> getSuppProducts() {
        return this.SuppProducts;
    }

    public String getSupplierPhoneNumber() {
        return this.SupplierPhoneNumber;
    }

    public EnumSuppType getSuppType() {
        return this.SupplierType;
    }

    public ArrayList<Integer> getContacts() {
        return this.contactsPhoneNum;
    }

    private BankDetails getBankDetails() {
        return this.bankDetails;
    }

    public Boolean ShceduledSupp() {
        if (SupplierType == EnumSuppType.ScheduledSupplier) {
            return true;
        }
        return false;
    }

    public void addWorkday(int day) {

        if (day == 1) {
            this.SuppWorkDays.add(WorkDays.Sun);
        }
        if (day == 2) {
            this.SuppWorkDays.add(WorkDays.Mon);
        }
        if (day == 3) {
            this.SuppWorkDays.add(WorkDays.Tue);
        }
        if (day == 4) {
            this.SuppWorkDays.add(WorkDays.Wed);
        }
        if (day == 5) {
            this.SuppWorkDays.add(WorkDays.Thu);
        }
        if (day == 6) {
            this.SuppWorkDays.add(WorkDays.Fri);
        }
        if (day == 7) {
            this.SuppWorkDays.add(WorkDays.Sat);
        }

    }
    public void deleteworkday(int day) {

        if (day == 1) {
            this.SuppWorkDays.remove(WorkDays.Sun);
        }
        if (day == 2) {
            this.SuppWorkDays.remove(WorkDays.Mon);
        }
        if (day == 3) {
            this.SuppWorkDays.remove(WorkDays.Tue);
        }
        if (day == 4) {
            this.SuppWorkDays.remove(WorkDays.Wed);
        }
        if (day == 5) {
            this.SuppWorkDays.remove(WorkDays.Thu);
        }
        if (day == 6) {
            this.SuppWorkDays.remove(WorkDays.Fri);
        }
        if (day == 7) {
            this.SuppWorkDays.remove(WorkDays.Sat);
        }

    }
    public void setWorkDays(ArrayList<Integer> days) {
        if (this.SupplierType == EnumSuppType.ScheduledSupplier) {
            this.SuppWorkDays = new ArrayList<>();

            if (days.contains(1)) {
                this.SuppWorkDays.add(WorkDays.Sun);
            }
            if ((days.contains(2))) {
                this.SuppWorkDays.add(WorkDays.Mon);
            }
            if ((days.contains(3))) {
                this.SuppWorkDays.add(WorkDays.Tue);
            }
            if ((days.contains(4))) {
                this.SuppWorkDays.add(WorkDays.Wed);
            }
            if ((days.contains(5))) {
                this.SuppWorkDays.add(WorkDays.Thu);
            }
            if ((days.contains(6))) {
                this.SuppWorkDays.add(WorkDays.Fri);
            }
            if ((days.contains(7))) {
                this.SuppWorkDays.add(WorkDays.Sat);
            }

        } else {
            this.SuppWorkDays = null;
        }
    }

    public void AddCompany(String comp) {
        if (companies.contains(comp)) {
            throw new IllegalArgumentException("the supplier is already in this  company");
        }
        companies.add(comp);
    }

    public void setSupplierAddress(String supplierAddress) {
        SupplierAddress = supplierAddress;
    }

    public void setSupId(int supId) {
        SupId = supId;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public void AddContact(int contactId, String name, Integer phoneNumber, String address) {
        Contact c = new Contact(contactId, name, phoneNumber, address);
        this.contactsPhoneNum.add(c.getPhoneNumber());
    }

    public void DeleteContact(int contactId) {
        this.contactsPhoneNum.remove(contactId);
    }

    public void setSupplierType(int i) {

        if (i == 1) {
            this.SupplierType = EnumSuppType.ScheduledSupplier;
            return;
        }
        if (i == 2) {
            this.SupplierType = EnumSuppType.OnDemandOrdering;
            return;
        }
        if (i == 3) {
            this.SupplierType = EnumSuppType.PickAndDeliver;
            return;
        }


    }

    public void AddProduct(int ProductId, String ProductName, double ProductPrice) {
        Product newProduct = new Product(ProductId, ProductName, ProductPrice);
        SuppProducts.put(newProduct.getProductId(), newProduct);
        SupplierProductsNumbers.add(newProduct.getProductId());
    }

    public void deleteProduct(int ProductId) {
        SuppProducts.remove(ProductId);
        SupplierProductsNumbers.remove(ProductId);

    }

    public ArrayList<Integer> getContactsPhoneNum() {
        return contactsPhoneNum;
    }

    public BankDetails AddBank(String bankname, String BaqnkBranch, int BankNum) {
        BankDetails newBank = new BankDetails(bankname, BaqnkBranch, BankNum);
        return newBank;
    }
    public void addContract(Contract c){
        this.SuppContracts.add(c);
    }
    public void deletContract(Contract c){
        this.SuppContracts.remove(c);
    }
    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }

    public void setCompanies(ArrayList<String> companies) {
        this.companies = companies;
    }

    public void deleteCompany(String companyName) {
        companies.remove(companyName);
    }

    public void display() {

        /* 1. Core profile */
        System.out.printf(
                "🏢 Supplier: %s (ID: %d)%nAddress: %s | Phone: %s | Type: %s%n",
                SupplierName, SupId, SupplierAddress, SupplierPhoneNumber,
                SupplierType != null ? SupplierType : "N/A"
        );

        /* 2. Bank account */
        if (bankDetails != null) {
            System.out.print("Bank Details: ");
            bankDetails.display();
        } else {
            System.out.println("Bank Details: N/A");
        }

        /* 3. Contacts */
        System.out.println("Contacts:");
        if (contactsPhoneNum != null && !contactsPhoneNum.isEmpty()) {
            contactsPhoneNum.forEach(num ->
                    System.out.printf("   • %s%n", num)
            );
        } else {
            System.out.println("   (none)");
        }

        /* 4. Partner companies */
        System.out.println("Companies:");
        if (companies != null && !companies.isEmpty()) {
            companies.forEach(comp -> System.out.printf("   • %s%n", comp));
        } else {
            System.out.println("   (none)");
        }

        /* 5. Work days (scheduled suppliers only) */
        if (SupplierType == EnumSuppType.ScheduledSupplier) {
            System.out.print("Work Days: ");
            if (SuppWorkDays != null && !SuppWorkDays.isEmpty()) {
                for (int i = 0; i < SuppWorkDays.size(); i++) {
                    System.out.print(SuppWorkDays.get(i));
                    if (i < SuppWorkDays.size() - 1) System.out.print(", ");
                }
                System.out.println();
            } else {
                System.out.println("(not set)");
            }
        }

        /* 6. Products */
        System.out.println("Products:");
        if (SuppProducts != null && !SuppProducts.isEmpty()) {
            SuppProducts.values().forEach(Product::display);
        } else {
            System.out.println("   (none)");
        }

        /* 7. Contracts */
        System.out.println("Contracts:");
        if (SuppContracts != null && !SuppContracts.isEmpty()) {
            SuppContracts.forEach(Contract::display);
        } else {
            System.out.println("   (none)");
        }
    }

}

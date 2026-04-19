package BusinessLayer;

public class BankDetails {
    private String BankName;
    private String BankBranch;
    private int BankNum;
    public BankDetails(String BankName,String BankBranch,int BankNum){
        this.BankName = BankName;
        this.BankBranch = BankBranch;
        this.BankNum = BankNum;
    }

    public void setBankBranch(String bankBranch) {
        BankBranch = bankBranch;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public void setBankNum(int bankNum) {
        BankNum = bankNum;
    }
    public void display() {
        System.out.printf(
                "Bank: %s (Branch: %s, Number: %d)%n",
                BankName, BankBranch, BankNum);
    }
}

package src.domain.Objects;

import java.text.SimpleDateFormat;
import java.util.*;

public class Item {

    private static int idCounter = 0;
    private final int ID;
    private final String ItemName;
    private final Date expdate;
    private final int amount;
    private DefectType defectReason;
    private int defectedAmount;

    public Item(String ItemName, Date expdate, int amount) {
        this.ItemName = ItemName;
        this.expdate = expdate;
        this.ID = idCounter++;
        this.amount = amount;
        defectedAmount = 0;
        defectReason = null;
    }

    public int getAmount() {
        return amount;
    }



    public void SetDefectAmount(int amount){
        this.defectReason = DefectType.Dammged;
        this.defectedAmount = amount;
    }
    public Date getExpdate() {
        return this.expdate;
    }
    public DefectType getDefectReason() {
        return this.defectReason;
    }


    @Override
    public String toString() {
        String result = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        result += "item's id is: " + this.ID + "\n";
        result += "item's name is: " + this.ItemName + "\n";
        result += "item's expiration date is: " + dateFormat.format(this.expdate) + "\n";
        result += "amount of defect items: " + this.defectedAmount + "\n";
        if (defectReason != null) {
            result = result + this.defectReason + "\n";
        }
        return result;
    }

}


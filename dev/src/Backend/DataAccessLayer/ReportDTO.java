package Backend.DataAccessLayer;

import java.util.HashMap;

public class ReportDTO {
    private int id;
    private int pid;
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    private int amount;

    public ReportDTO(int id, int PID,int amount) {
        this.id = id;
        this.pid = PID;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

   
}

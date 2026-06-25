package Backend.DataAccessLayer;

public class CategoryDTO {


    private String name;
    private int ID;

    private String faString;

    public String getFaString() {
        return faString;
    }

    public void setFaString(String faString) {
        this.faString = faString;
    }

    public String getName() {
        return name;
    }
    public int getID() {
        return ID;
    }
    public void setName(String name) {
        this.name = name;
    }

    public CategoryDTO(String name,String fString,int id) {
        this.name = name;
        this.faString=fString;
        this.ID=id;
        
    }
}

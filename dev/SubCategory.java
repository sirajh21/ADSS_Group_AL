package src.domain.Objects;

public class SubCategory extends Category {
    private final String upperCat;

    public SubCategory(String name,String upperCat) {
        super(name);
        this.upperCat = upperCat;
    }

    public String getUpperCat() {
        return upperCat;
    }
}

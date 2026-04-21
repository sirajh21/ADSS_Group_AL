public class foodcat extends catogeryCon {

    private final String parentCategoryName;

    public foodcat(String name, String parentCategoryName) {
        super(name);
        this.parentCategoryName = parentCategoryName;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    // compatibility
    public String getUpperCat() {
        return getParentCategoryName();
    }
}
// domain/Store.java
package src.domain.Objects;

import java.util.*;


public class Store {
    private final List<ItemSpecs> items;
    private final List<Category> categories;
    private final List<Item> Defect_items ;
    private final HashMap<Integer,Order> orders;


    public Store() {
        items = new ArrayList<>();
        categories = new ArrayList<>();
        Defect_items = new ArrayList<>();
        orders = new HashMap<>();
    }

    public LinkedList<Category> getsubCategoryNames(String cname) {
        LinkedList<Category> subcategories = null;
        for (Category c : categories) {
            if (c.getCategoryName().equals(cname)) {
                subcategories = c.getSubCategory();
            }
        }
        return subcategories;
    }

    public void addItem(ItemSpecs item) {
        items.add(item);
        getCategoryByName(item.getCname()).addCategoryItem(item);
    }

    public void removeItem(String name) {
        ItemSpecs itemToRemove = getItemByName(name);
        if (itemToRemove != null) {
            Category category = getCategoryByName(itemToRemove.getCname());
            if (category != null) {
                category.removeCategoryItem(name);
            }
            items.remove(itemToRemove);
        } else {
            System.out.println("Item not found.");
        }
    }

    public ItemSpecs getItemByName(String name) {

        for (ItemSpecs item : items) {
            if (item.getIname().equals(name)) {
                return item;
            }
        }
        return null;
    }


    public void addCategory(Category category) {
        categories.add(category);
    }

    public void addSubCategory(String category_name, String sub_category_name) {
        for (Category category : categories) {
            if (category.getCategoryName().equals(category_name)) {
                category.addSubCat(new Category(sub_category_name));
            }
        }

    }



    public void removeCategory(String name) {
        for (Category category : categories) {
            if (category.getCategoryName().equals(name)) {
                for (ItemSpecs item : category.getCategoryItems()) {
                    items.remove(item);
                    item.setAmount(0);
                }
            }
        }
        categories.removeIf(category -> category.GetCategoryName().equals(name));

    }

    public Category getCategoryByName(String name) {
        for (Category category : categories) {
            if (category.GetCategoryName().equals(name)) {
                return category;
            }
        }
        return null;
    }


    public void addCategoryDiscount(String category_name,double discount_percentage) {
      for (Category category : categories) {
          if (category.GetCategoryName().equals(category_name)) {
              category.addCatDiscount(discount_percentage);
          }
      }
    }
    public void add_future_CategoryDiscount(String category_name,Date start,Date end,double discount_percentage) {
        for (Category category : categories) {
            if (category.GetCategoryName().equals(category_name)) {
                category.addCatDiscount_byDate(start,end,discount_percentage);
                return;
            }
        }
    }

    public void addItemDiscount(String item_name,double discount_percentage) {
      for (ItemSpecs item : items) {
          if (item.getIname().equals(item_name)) {
              item.setDiscount(discount_percentage);
          }
      }
    }
    public void add_future_ItemDiscount(String item_name,Date start,Date end,double discount_percentage) {
        for (ItemSpecs item : items) {
            if (item.getIname().equals(item_name)) {
                item.addFutureDiscount(start,end,discount_percentage);
                return;
            }
        }
    }


    public void removeCategoryDiscount(String catrgoryName) {
        for (Category category : categories) {
            if (category.GetCategoryName().equals(catrgoryName)) {
                category.removeCatDiscount();
            }
        }
    }
    public void remove_future_CategoryDiscount(String catrgoryName,Date start,Date end) {
        for (Category category : categories) {
            if (category.GetCategoryName().equals(catrgoryName)) {
                category.remove_future_CatDiscount(start,end,catrgoryName);
            }
        }
    }
    public void remove_future_item_Discount(String item_name) {
        for (ItemSpecs item : items) {
            if (item.getIname().equals(item_name)) {
                item.remove_future_Discount();
            }
        }
    }

    public void removeItemDiscount(String itemName) {
        for (ItemSpecs item : items) {
            if (item.getIname().equals(itemName)) {
                item.removeDiscount();
            }
        }
    }


    public void addDefectiveItem(String itemName, DefectType defectReason, int defectiveAmount) {
        ItemSpecs itemSpecs = getItemByName(itemName);
        if (itemSpecs != null) {
            itemSpecs.addingDefectItem(defectiveAmount, defectReason);
            Defect_items.addAll(itemSpecs.getItemByDate().values());
        }
    }

    public StringBuilder showStatus() {
        StringBuilder status = new StringBuilder();

        for (Category category : categories) {
            status.append(category.toString()).append("\n");
        }
        return status;
    }
    public void show_Cat() {
        for (Category category : categories) {
            System.out.println(category.getCatId()+": "+category.GetCategoryName());
        }
    }


    public void transferItems(String item_name,int amount) {
        for (ItemSpecs item : items) {
            if (item.getIname().equals(item_name)) {
                    item.transfer_item(amount);
            }
        }
    }




    public List<ItemSpecs> getItems() {
        return items;
    }

    public List<Category> getCategories() {
        return categories;
    }


    public void move_item_to_shelf(String item_name, int amount){
        for (ItemSpecs item : items) {
            if (item.getIname().equals(item_name)) {
                item.transfer_item(amount);

            }
        }
    }



    public void addMinimumAttributeItem(String itemName, int minimumAmount) {
        this.getItemByName(itemName).setMinAmount(minimumAmount);
    }

    public ArrayList<Item> ItemsUntilExpDate(Date expDate) {
        return getItems(expDate);
    }
    public ArrayList<Item> ItemsafterExpDate(Date expDate) {
        return getItems_after(expDate);
    }

    private ArrayList<Item>getItems(Date expDate) {
        ArrayList<Item> result = new ArrayList<>();
        for (ItemSpecs item_spec : items) {
            for (Item item : item_spec.getItemByDate().values()) {
                if (item.getExpdate().before(expDate)) {
                    result.add(item);
                }
            }
        }
        return result;
    }
    private ArrayList<Item> getItems_after(Date expDate) {
        ArrayList<Item> result = new ArrayList<>();
        for (ItemSpecs item_spec : items) {
            for (Item item : item_spec.getItemByDate().values()) {
                if (item.getExpdate().after(expDate) || item.getExpdate().equals(expDate)) {
                    result.add(item);
                }
            }
        }
        return result;
    }


    public void makeOrder(HashMap<String,Integer> items, Date expDate) {
        Set<String> items_name = items.keySet();
        HashMap<ItemSpecs,Integer> ordered_items = new HashMap<>();
        for (String itemName : items_name) {
            ItemSpecs item = getItemByName(itemName.replaceAll("\\P{Print}", ""));
            int amount = items.get(itemName);
            if(item!=null) {
                if (item.getAmount() < amount) {
                    System.out.println("Store does not have enough amount of " + itemName);
                } else {
                    item.setAmount(item.getAmount() - amount);
                    ordered_items.put(item,items.get(itemName));
                }
            }
        }
        Order order = new Order(ordered_items,expDate);
        orders.put(order.getOrderId(),order);
    }



    public HashMap<String, Integer> demandLevel() {
        HashMap<String, Integer> items = new HashMap<>();
        for (Order order : orders.values()) {
            for (ItemSpecs item : order.getItems().keySet()) {
                String itemName = item.getIname();
                int quantity = order.getItems().get(item);
                if (items.containsKey(itemName)) {
                    items.put(itemName, items.get(itemName) + quantity);
                } else {
                    items.put(itemName, quantity);
                }
            }
        }
        return items;
    }

}

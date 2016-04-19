package net.ismail.apptest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ismail on 2016-04-15.
 */
public class Product {
    private String name;
    @SerializedName("category_ids")
    private Integer []categoryIds;

    public Product(String name, Integer[] categoryIds) {
        this.name = name;
        this.categoryIds = categoryIds;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Integer[] categoryIds) {
        this.categoryIds = categoryIds;
    }
}

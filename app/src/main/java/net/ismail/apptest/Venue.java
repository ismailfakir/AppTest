package net.ismail.apptest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ismail on 2016-04-15.
 */
public class Venue {

    private String name;
    private float latitude;
    private float longitude;
    @SerializedName("category_ids")
    private int []categoryIds;

    public Venue(String name, float latitude, float longitude, int[] categoryIds) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.categoryIds = categoryIds;
    }

    public Venue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(int[] categoryIds) {
        this.categoryIds = categoryIds;
    }
}

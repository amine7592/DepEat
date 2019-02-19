package org.elis.depeat.datamodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Entity(tableName = "Restaurant")
public class Restaurant {

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "minimum_order")
    private float minimumOrder;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "restaurant_id")
    private String id;


    public static final String ENDPOINT = "restaurants/";

    @Ignore
    private ArrayList<Product> products;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    public Restaurant(String name, String address, float minimumOrder) {
        this.name = name;
        this.address = address;
        this.minimumOrder = minimumOrder;
        products = new ArrayList<>();
    }

    public Restaurant(JSONObject jsonRestaurant) throws JSONException {
        id = jsonRestaurant.getString("id");
        name = jsonRestaurant.getString("name");
        address = jsonRestaurant.getString("address");
        minimumOrder = (float)jsonRestaurant.getDouble("min_order");
        imageUrl = jsonRestaurant.getString("image_url");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getMinimumOrder() {
        return minimumOrder;
    }

    public void setMinimumOrder(float minimumOrder) {
        this.minimumOrder = minimumOrder;
    }

    public String getDescription() {
        return description;
    }


    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getId() {
        return id;
    }
}

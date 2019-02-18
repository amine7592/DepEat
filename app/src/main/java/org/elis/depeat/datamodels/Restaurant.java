package org.elis.depeat.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Restaurant {

    private String name;
    private String address;
    private float minimumOrder;
    private String description;

    private String imageUrl;

    public static final String ENDPOINT = "restaurants";


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
}

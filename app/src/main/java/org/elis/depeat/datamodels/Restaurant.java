package org.elis.depeat.datamodels;

public class Restaurant {

    private String name;
    private String address;
    private float minimumOrder;
    private String description;

    private String imageUrl;



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    public Restaurant(String name, String description, float minimumOrder) {
        this.name = name;
        this.description = description;
        this.minimumOrder = minimumOrder;
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
}

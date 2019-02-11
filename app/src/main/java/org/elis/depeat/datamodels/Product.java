package org.elis.depeat.datamodels;

public class Product {

    private String name;
    private int quantity = 0;
    private float price;


    public Product(String name, float price){

        this.name = name;
        this.price = price;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void increaseQuantity(){
        this.quantity++;
    }
    public void decreaseQuantity(){
        if(quantity == 0) return;
        this.quantity--;
    }

    public float getSubtotal(){
        return quantity * price;
    }


}

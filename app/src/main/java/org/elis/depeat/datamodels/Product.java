package org.elis.depeat.datamodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "Product")
public class Product {

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "quantity")
    private int quantity = 0;

    @ColumnInfo(name = "price")
    private float price;

    @ColumnInfo(name = "product_id")
    private String id;


    public Product(String name, float price){

        this.name = name;
        this.price = price;
    }


    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Product(String name, float price, int quantity){

        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(JSONObject jsonProduct) throws JSONException{
        name = jsonProduct.getString("name");
        price =(float) jsonProduct.getDouble("price");
        id = jsonProduct.getString("id");



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

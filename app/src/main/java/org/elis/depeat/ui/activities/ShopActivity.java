package org.elis.depeat.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.elis.depeat.R;
import org.elis.depeat.datamodels.Product;
import org.elis.depeat.datamodels.Restaurant;
import org.elis.depeat.ui.adapters.ProductAdapter;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements ProductAdapter.OnQuanityChangedListener{

    // UI components
    private TextView shopNameTv, shopAddress, totalTxtView;
    private Button checkout;
    private ProgressBar progressBar;
    private ImageView restaurantIv;

    // RecyclerView components
    private RecyclerView productRv;
    private LinearLayoutManager layoutManager;
    private ProductAdapter adapter;


    // data model
    private Restaurant restaurant;


    private float total = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopNameTv = findViewById(R.id.shop_name_tv);
        shopAddress = findViewById(R.id.shop_desc_tv);
        totalTxtView = findViewById(R.id.total_tv);
        restaurantIv = findViewById(R.id.shop_iv);

        checkout = findViewById(R.id.checkout);
        progressBar = findViewById(R.id.progress);
        productRv = findViewById(R.id.product_rv);

        binData();
        layoutManager = new LinearLayoutManager(this);
        adapter = new ProductAdapter(this, restaurant.getProducts());
        adapter.setOnQuanityChangedListener(this);
        productRv.setLayoutManager(layoutManager);
        ((SimpleItemAnimator) productRv.getItemAnimator()).setSupportsChangeAnimations(false);
        productRv.setAdapter(adapter);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this,CheckoutActivity.class));
            }
        });


    }

    //TODO there is some hardcoding inside
    private void binData(){

        restaurant = getRestaurant();
        restaurant.setImageUrl("https://rovato5stelle.files.wordpress.com/2013/11/mcdonald.jpg");
        restaurant.setProducts(getProducts());
        shopNameTv.setText(restaurant.getName());
        shopAddress.setText(restaurant.getAddress());
        Glide.with(this).load(restaurant.getImageUrl()).into(restaurantIv);
        progressBar.setMax((int)restaurant.getMinimumOrder() * 100);


    }

    //TODO hardcoded
    private Restaurant getRestaurant() {
        return new Restaurant("Fraschetta", "Via le mani dal naso", 10);
    }

    //TODO hardcoded
    private ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("McMenu", 7));
        products.add(new Product("McMenu", 7));
        products.add(new Product("McMenu", 7));
        products.add(new Product("McMenu", 7));
        products.add(new Product("McMenu", 7));
        return products;

    }

    private void updateTotal(float item){
        total= total + item;
        totalTxtView.setText(getString(R.string.total).concat(String.valueOf(total)));
    }

    private void updateProgress(int progress){
        progressBar.setProgress(progress);
    }

    private void enableBttuon(){
        checkout.setEnabled(total>=restaurant.getMinimumOrder());
    }

    @Override
    public void onChange(float price) {
        updateTotal(price);
        updateProgress((int)total*100);
        enableBttuon();
    }
}

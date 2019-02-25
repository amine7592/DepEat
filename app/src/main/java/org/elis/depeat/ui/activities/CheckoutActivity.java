package org.elis.depeat.ui.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.elis.depeat.R;
import org.elis.depeat.datamodels.Order;
import org.elis.depeat.datamodels.Product;
import org.elis.depeat.datamodels.Restaurant;
import org.elis.depeat.services.AppDatabase;
import org.elis.depeat.ui.adapters.OrderProductsAdapter;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener,OrderProductsAdapter.onItemRemovedListener{

    private TextView restaturantTv,restaurantAdress,totalTv;
    private RecyclerView productRv;
    private Button payBtn;
    private LinearLayoutManager layoutManager;
    private OrderProductsAdapter adapter;


    private float total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        restaturantTv = findViewById(R.id.restaurant_name);
        restaurantAdress = findViewById(R.id.restaurant_adress);
        totalTv = findViewById(R.id.total_tv);
        productRv = findViewById(R.id.product_rv);
        payBtn = findViewById(R.id.pay_btn);

        // setup recyclerview
        layoutManager = new LinearLayoutManager(this);
        productRv.setLayoutManager(layoutManager);
        adapter = new OrderProductsAdapter(this);
        adapter.setOnItemRemovedListener(this);
        productRv.setAdapter(adapter);

        //set click listener for button
        payBtn.setOnClickListener(this);

        new GetOrder().execute();


    }


    private void bindData(Order order){
        restaturantTv.setText(order.getRestaurant().getName());
        restaurantAdress.setText(order.getRestaurant().getAddress());
        totalTv.setText(String.valueOf(order.getTotal()));
        adapter.setData(order.getProducts());



    }


    //TODO hardcoded



    class GetOrder extends AsyncTask<Void, Void, Void> {

        private List<Order> orders;

        @Override
        protected Void doInBackground(Void... voids) {
           this.orders = AppDatabase.getAppDatabase(CheckoutActivity.this).orderDao().getAll();
           return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            bindData(orders.get(0));
        }
    }







    private Restaurant getRestaurant() {
        return new Restaurant("Fraschetta", "Via le mani dal naso", 10);
    }

    //TODO hardcoded
    private ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("McMenu", 5,2));
        products.add(new Product("McMenu", 5,2));
        products.add(new Product("McMenu", 5,2));

        return products;

    }

    @Override
    public void onClick(View view) {
            //TODO manageClick
    }

    @Override
    public void onItemRemoved(float subtotal) {
        updateTotal(subtotal);
    }

    private void updateTotal(float subtotal) {
        if(total == 0) return;
        total -=subtotal;
        totalTv.setText(String.valueOf(total));
    }



}

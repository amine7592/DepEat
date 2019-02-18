package org.elis.depeat.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import org.elis.depeat.R;
import org.elis.depeat.datamodels.Product;
import org.elis.depeat.datamodels.Restaurant;
import org.elis.depeat.services.RestController;
import org.elis.depeat.ui.adapters.ProductAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements ProductAdapter.OnQuanityChangedListener,Response.Listener<String>,Response.ErrorListener{

    public static final String RESTAURANT_ID_KEY = "RESTAURANT_ID_KEY";
    private static final String TAG = ShopActivity.class.getSimpleName();
    private static final int LOGIN_REQUEST_CODE = 2001;


    // UI components
    private TextView shopNameTv, shopAddress, totalTxtView;
    private Button checkout;
    private ProgressBar progressBar;
    private ImageView restaurantIv;

    // RecyclerView components
    private RecyclerView productRv;
    private LinearLayoutManager layoutManager;
    private ProductAdapter adapter;

    private Menu menu;


    // data model
    private Restaurant restaurant;


    private float total = 0;

    private RestController restController;
    private String restaurantId;



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

        layoutManager = new LinearLayoutManager(this);
        adapter = new ProductAdapter(this);
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

        restaurantId = getIntent().getStringExtra(ShopActivity.RESTAURANT_ID_KEY);
        restController = new RestController(this);
        restController.getRequest(
                Restaurant.ENDPOINT.concat(restaurantId),
                this,
                this);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shop_menu,menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.login_menu){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent,LOGIN_REQUEST_CODE);


        }else if(item.getItemId() == R.id.checkout_menu){

        }
        return super.onOptionsItemSelected(item);
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


    private void bindData(){
        shopNameTv.setText(restaurant.getName());
        shopAddress.setText(restaurant.getAddress());
        Glide.with(this).load(restaurant.getImageUrl()).into(restaurantIv);
        progressBar.setMax((int)restaurant.getMinimumOrder()*100);
        adapter.setData(restaurant.getProducts());


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG,"requestCode " + requestCode);
        Log.d(TAG,"resultCode " + resultCode);

        if(requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            // TODO login is successful manage result

            Log.d(TAG,data.getStringExtra("response"));
            menu.findItem(R.id.login_menu).setTitle(R.string.profile)
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            startActivity(new Intent(ShopActivity.this,ProfileActivity.class));
                            return true;
                        }
                    });


        }
    }

    @Override
    public void onChange(float price) {
        updateTotal(price);
        updateProgress((int)total*100);
        enableBttuon();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG,error.getMessage());
        Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            restaurant = new Restaurant(jsonObject);
            JSONArray jsonProducts = jsonObject.getJSONArray("products");
            ArrayList<Product> products = new ArrayList<>();

            for (int i = 0; i<jsonProducts.length(); i++){
                products.add(new Product(jsonProducts.getJSONObject(i)));
            }

            restaurant.setProducts(products);

            bindData();





        } catch (JSONException e) {
            Log.e(TAG,e.getMessage());
        }
    }
}

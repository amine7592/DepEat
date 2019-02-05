package org.elis.depeat.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.elis.depeat.R;
import org.elis.depeat.datamodels.Restaurant;
import org.elis.depeat.ui.adapters.RestaturantAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView restaurantRV;
    RecyclerView.LayoutManager layoutManager;
    RestaturantAdapter adapter;
    ArrayList<Restaurant> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurantRV = findViewById(R.id.places_rv);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RestaturantAdapter(this,getData());

        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);
    }


    private ArrayList<Restaurant> getData(){
        arrayList = new ArrayList<>();
        arrayList.add(new Restaurant("Burger","via le mani dal naso",2.00f));
        arrayList.add(new Restaurant("Burger","via le mani dal naso",2.00f));
        arrayList.add(new Restaurant("Burger","via le mani dal naso",2.00f));
        arrayList.add(new Restaurant("Burger","via le mani dal naso",2.00f));


        return arrayList;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.view_mode){
            setLayoutManager();
            return true;
        }
        if(item.getItemId() == R.id.login_menu){

            startActivity(new Intent(this,LoginActivity.class));
            return true;
        }else if(item.getItemId() == R.id.checkout_menu){
            startActivity(new Intent(this,CheckoutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setLayoutManager(){
        layoutManager = adapter.isGridMode() ? new LinearLayoutManager(this) : new GridLayoutManager(this,2);
        adapter.setGridMode(!adapter.isGridMode());
        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);
    }
}

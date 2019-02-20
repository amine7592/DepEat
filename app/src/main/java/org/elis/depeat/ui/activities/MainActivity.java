package org.elis.depeat.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.elis.depeat.R;
import org.elis.depeat.datamodels.Restaurant;
import org.elis.depeat.services.RestController;
import org.elis.depeat.ui.adapters.RestaturantAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView restaurantRV;
    RecyclerView.LayoutManager layoutManager;
    RestaturantAdapter adapter;
    ArrayList<Restaurant> arrayList = new ArrayList<>();

    LoginReceiver receiver;
    public static final String LOGIN_ACTION = "LOGIN_ACTION";

    Menu menu;

    private RestController restController;

    SharedPreferences sharedPreferences;

    private static final String SharedPrefs = "org.elis.depeat.general_prefs";
    private static final String VIEW_MODE = "VIEW_MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurantRV = findViewById(R.id.places_rv);

        layoutManager = getLayoutManager(getSavedLayoutManager());
        adapter = new RestaturantAdapter(this);
        adapter.setGridMode(getSavedLayoutManager());

        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);

        restController = new RestController(this);
        restController.getRequest(Restaurant.ENDPOINT, this, this);

        receiver = new LoginReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver
                (receiver,new IntentFilter(LOGIN_ACTION));

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;
        menu.findItem(R.id.view_mode).setIcon(getSavedLayoutManager() ? R.drawable.baseline_grid_on_white_24 : R.drawable.baseline_list_white_24);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.view_mode) {
            setLayoutManager();
            item.setIcon(adapter.isGridMode() ? R.drawable.baseline_grid_on_white_24 : R.drawable.baseline_list_white_24);
            return true;
        }
        if (item.getItemId() == R.id.login_menu) {

            startActivity(new Intent(this, LoginActivity.class));
            return true;
        } else if (item.getItemId() == R.id.checkout_menu) {
            startActivity(new Intent(this, CheckoutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setLayoutManager() {
        layoutManager = getLayoutManager(!adapter.isGridMode());
        adapter.setGridMode(!adapter.isGridMode());
        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);
        saveLayoutManager(adapter.isGridMode());
    }


    private void saveLayoutManager(boolean isGridLayout) {
        sharedPreferences = getSharedPreferences(SharedPrefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(VIEW_MODE, isGridLayout);
        editor.apply();
    }

    private RecyclerView.LayoutManager getLayoutManager(boolean isGridLayout) {
        return isGridLayout ? new GridLayoutManager(this, 2) : new LinearLayoutManager(this);
    }

    private boolean getSavedLayoutManager() {
        sharedPreferences = getSharedPreferences(SharedPrefs, MODE_PRIVATE);
        return sharedPreferences.getBoolean(VIEW_MODE, false);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.getMessage());
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                arrayList.add(new Restaurant(jsonArray.getJSONObject(i)));
            }
            adapter.setData(arrayList);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(receiver);


    }

    //EXTEND Broadcast receiver class
    public class LoginReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "login successful");

            menu.findItem(R.id.login_menu).setTitle(R.string.profile)
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                            return true;
                        }
                    });
        }
    }

}

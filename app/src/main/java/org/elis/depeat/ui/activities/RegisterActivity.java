package org.elis.depeat.ui.activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.request.Request;

import org.elis.depeat.R;
import org.elis.depeat.Utils;
import org.elis.depeat.datamodels.User;
import org.elis.depeat.services.RestController;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,Response.Listener<String>,Response.ErrorListener{
    EditText emailEt, passwordEt, usernameEt;
    Button registerBtn;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    boolean isUsernameValid, isPasswordValid, isEmailValid;

    RestController restController;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        usernameEt = findViewById(R.id.username);
        registerBtn = findViewById(R.id.register_btn);

        restController =  new RestController(this);
        registerBtn.setOnClickListener(this);

        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("Before", charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("onText", charSequence.toString());
                isEmailValid = Utils.verifyEmail(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("After", editable.toString());
                enableButton();

            }
        });

        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isPasswordValid = Utils.isPasswordValid(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableButton();
            }
        });


        usernameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isUsernameValid = Utils.isUsernameValid(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableButton();
            }
        });


    }




    private void enableButton() {
        registerBtn.setEnabled(isEmailValid && isPasswordValid && isUsernameValid);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.register_btn){
            Map<String,String> params = new HashMap<>();
            params.put("username",usernameEt.getText().toString());
            params.put("email",emailEt.getText().toString());
            params.put("password",passwordEt.getText().toString());

            restController.postRequest(User.REGISTER_ENDPOINT,params,this,this);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG,error.getMessage());
        Toast.makeText(this,error.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG,response);


    }
}

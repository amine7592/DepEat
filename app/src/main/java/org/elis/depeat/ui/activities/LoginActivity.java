package org.elis.depeat.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.elis.depeat.R;
import org.elis.depeat.Utils;
import org.elis.depeat.datamodels.User;
import org.elis.depeat.services.RestController;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener, Response.Listener<String> {


    private static final String TAG = LoginActivity.class.getSimpleName();

    EditText emailEt;
    EditText passwordEt;

    Button loginBtn;
    Button registerBtn;
    final static int LEN_PASSWORD=6;

    private RestController restController;

    public static final String EMAIL_KEY = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize UI controllers
        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);


        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

        restController = new RestController(this);


    }


    private void doLogin(){
        /*if(verifyEmail(emailEt.getText().toString()) && passwordEt.getText().toString().length() > LEN_PASSWORD){
            showToast(getString(R.string.accesso_eseguito));
        } else if(!verifyEmail(emailEt.getText().toString())) {
            showToast(getString(R.string.email_invalida));
        } else{
            showToast(getString(R.string.password_invalida));
        }*/

        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString();

        if(!Utils.verifyEmail(email)){
            showToast(R.string.email_invalida);
            return;
        }

        if(!Utils.isPasswordValid(password)){
            showToast(R.string.password_invalida);
            return;
        }

        Map<String,String> params = new HashMap<>();
        params.put("identifier",email);
        params.put("password",password);


        restController.postRequest(User.LOGIN_ENDPOINT,params,this,this);



    }


    private void showToast(@StringRes int resId){
        Toast.makeText(this,getString(resId), Toast.LENGTH_LONG).show();

    }

    private void showToast(String message){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();

    }



    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.login_btn){
            doLogin();
        }else if ( view.getId() == R.id.register_btn) {
            startActivity(new Intent(this,RegisterActivity.class));
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG, error.getMessage());
        showToast(error.getMessage());
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG,response);
        Intent i = new Intent();
        i.putExtra("response",response);
        setResult(Activity.RESULT_OK,i);
        finish();




        //TODO fare cose



    }
}

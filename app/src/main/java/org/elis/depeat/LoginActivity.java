package org.elis.depeat;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    EditText emailEt;
    EditText passwordEt;

    Button loginBtn;
    Button registerBtn;
    final static int LEN_PASSWORD=6;

    public static final String EMAIL_KEY = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialize UI controllers
        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);


        loginBtn.setOnClickListener(this);
        //registerBtn.setOnClickListener(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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

        if(Utils.isPasswordValid(password)){
            showToast(R.string.password_invalida);
            return;
        }

        showToast(R.string.accesso_eseguito);
        /*Intent intent = new Intent(this,WelcomeActivity.class);
        intent.putExtra(EMAIL_KEY,email);
        startActivity(intent);*/




    }


    private void showToast(@StringRes int resId){
        Toast.makeText(this,getString(resId), Toast.LENGTH_LONG).show();

    }



    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.login_btn){
            doLogin();
        }
    }
}

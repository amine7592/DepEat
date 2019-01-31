package org.elis.depeat.ui.activities;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.elis.depeat.R;
import org.elis.depeat.Utils;

public class RegisterActivity extends AppCompatActivity{
    EditText emailEt, passwordEt, phoneEt;
    Button registerBtn;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    boolean isPhoneValid, isPasswordValid, isEmailValid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailEt = findViewById(R.id.email_et);
        passwordEt = findViewById(R.id.password_et);
        phoneEt = findViewById(R.id.phone_et);
        registerBtn = findViewById(R.id.register_btn);

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


        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isPhoneValid = Utils.isPhoneValid(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableButton();
            }
        });


    }




    private void enableButton() {
        registerBtn.setEnabled(isEmailValid && isPasswordValid && isPhoneValid);
    }

}

package com.socialcodia.restapi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.socialcodia.restapi.R;
import com.socialcodia.restapi.api.ApiClient;
import com.socialcodia.restapi.model.LoginResponse;
import com.socialcodia.restapi.model.User;
import com.socialcodia.restapi.storage.SharedPrefHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private TextView tvRegister;
    private SharedPrefHandler sharedPrefHandler;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Ui Init
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        tvRegister = findViewById(R.id.tvRegister);
        btnLogin = findViewById(R.id.btnLogin);

        //Share Pref init
        sharedPrefHandler = new SharedPrefHandler(getApplicationContext());

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToRegister();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        isLoggedIn();
    }

    private void isLoggedIn()
    {
        if (SharedPrefHandler.getInstance(getApplicationContext()).isLoggedIn())
        {
            sendToMain();
        }
    }

    private void sendToRegister()
    {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    private void validateData()
    {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if (email.isEmpty())
        {
            inputEmail.setError("Enter Email");
            inputEmail.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.length()>50)
        {
            inputEmail.setError("Enter Valid Email");
            inputEmail.requestFocus();
        }
        else if (password.isEmpty())
        {
            inputPassword.setError("Enter Password");
            inputPassword.requestFocus();
        }
        else if (password.length()<7 || password.length()>20)
        {
            inputPassword.setError("Password should be in between 7 to 20 Character");
            inputPassword.requestFocus();
        }
        else
        {
            doLogin(email,password);
        }
    }

    private void doLogin(String email, String password)
    {
        btnLogin.setEnabled(false);
        Call<LoginResponse> call = ApiClient.getInstance().getApi().login(email,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse!=null)
                {
                    if (loginResponse.getError())
                    {
                        btnLogin.setEnabled(true);
                        String message = loginResponse.getMessage();
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                    btnLogin.setEnabled(true);
                    User user = loginResponse.getUser();
                    if (user!=null)
                    {
                        SharedPrefHandler.getInstance(getApplicationContext()).saveUser(user);
                        sendToMain();
                        Toast.makeText(LoginActivity.this,loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Server Not Responding Any Data", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    btnLogin.setEnabled(true);
                    Toast.makeText(LoginActivity.this, "No Response From Server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                btnLogin.setEnabled(true);
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendToMain()
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
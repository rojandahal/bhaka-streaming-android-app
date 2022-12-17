package com.example.bhakamusic.ui.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhakamusic.Apis.RetrofitClient;
import com.example.bhakamusic.MainActivity;
import com.example.bhakamusic.MainActivity2;
import com.example.bhakamusic.ModelResponse.LoginRequest;
import com.example.bhakamusic.ModelResponse.RegisterRequest;
import com.example.bhakamusic.ModelResponse.RegisterResponse;
import com.example.bhakamusic.ModelResponse.UserResponse;
import com.example.bhakamusic.R;
import com.example.bhakamusic.RoomDatabase.UserDB.UserCredentials;
import com.example.bhakamusic.ui.Player.Player;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SIGN IN";

    private TextInputLayout username;
    private TextInputLayout password;
    private Button signIn;
    private Button signUp;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Objects.requireNonNull(getSupportActionBar()).hide();
        signIn = findViewById(R.id.signin_button_signin);
        signUp = findViewById(R.id.signup_button_signin);
        username = findViewById(R.id.editTextUsernameSignin);
        password = findViewById(R.id.editTextPasswordSignin);
        progressBar = findViewById(R.id.LoginProgressBar);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_button_signin:
                startActivity(new Intent(SignIn.this, SignUp.class));
                break;
            case R.id.signin_button_signin:
               signInUser();
                break;
        }
    }

    private void signInUser() {
        String userUsername = username.getEditText().getText().toString();
        String userPassword = password.getEditText().getText().toString();
        if (userUsername.isEmpty()){
            username.requestFocus();
            username.setError("Please enter your username");
        }else if (userPassword.isEmpty()){
            password.requestFocus();
            password.setError("Please enter your password");
        }

        signIn.setEnabled(false);
        signUp.setEnabled(false);
        progressBar.isIndeterminate();
        progressBar.setVisibility(View.VISIBLE);

        LoginRequest loginRequest = new LoginRequest(userUsername,userPassword);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Call<RegisterResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .login(loginRequest);

                call.enqueue(new Callback<RegisterResponse>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        RegisterResponse rs = response.body();
                        if(response.isSuccessful()){
                            assert rs != null;
                            Log.d(TAG, "onResponseIN: "+ rs.getToken());
                            //Shared preference
                            SharedPreferences sharedPref = getApplication().getSharedPreferences(String.valueOf(R.string.token_sharedpref),Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(String.valueOf(R.string.token),rs.getToken());
                            editor.apply();
                            Intent intent = new Intent(SignIn.this, MainActivity2.class);
                            intent.putExtra("token",rs.getToken());
                            RetrofitClient.token=rs.getToken();
                            signIn.setEnabled(true);
                            signUp.setEnabled(true);
                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(intent);
                        }else {
                            username.setError("Invalid Credentials!");
                            password.setError("Invalid Credentials!");
                            signIn.setEnabled(true);
                            signUp.setEnabled(true);
                            progressBar.setVisibility(View.INVISIBLE);
                            Log.d(TAG, "onResponse: ERROR");
                        }

                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        signIn.setEnabled(true);
                        signUp.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SignIn.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: FAILURE ERROR");
                    }
                });
            }
        },4000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
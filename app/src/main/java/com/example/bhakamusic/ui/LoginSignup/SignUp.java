package com.example.bhakamusic.ui.LoginSignup;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bhakamusic.Apis.RetrofitClient;
import com.example.bhakamusic.ModelResponse.RegisterRequest;
import com.example.bhakamusic.ModelResponse.RegisterResponse;
import com.example.bhakamusic.R;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout email;
    private TextInputLayout username;
    private TextInputLayout password;
    private Button signIn, signUp;
    ProgressBar loading;
    private static final String TAG = "SIGN UP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        signIn = findViewById(R.id.signin_button_signup);
        signUp = findViewById(R.id.signup_button_signup);
        email = findViewById(R.id.editTextEmailAddress);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        loading = findViewById(R.id.SignupProgressBar);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin_button_signup:
                startActivity(new Intent(SignUp.this, SignIn.class));
                break;
            case R.id.signup_button_signup:
                registerUser();
//                signUp.setEnabled(false);
//                loadingBar.setVisibility(View.VISIBLE);
//                checkEmptyFields();
                break;
        }
    }

    private void registerUser() {
        String userEmail = email.getEditText().getText().toString();
        String userUsername = username.getEditText().getText().toString();
        String userPassword = password.getEditText().getText().toString();

        if(userEmail.isEmpty()){
            email.requestFocus();
            email.setError("Please enter your email");
        }else if (userUsername.isEmpty()){
            username.requestFocus();
            username.setError("Please enter your username");
        }else if (userPassword.isEmpty()){
            password.requestFocus();
            password.setError("Please enter your password");
        }

        signUp.setEnabled(false);
        signIn.setEnabled(false);
        loading.isIndeterminate();
        loading.setVisibility(View.VISIBLE);

        RegisterRequest r = new RegisterRequest(userUsername,userEmail,userPassword);
        Call<RegisterResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .register(r);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.d(TAG, "onResponseIN: "+ response);
                RegisterResponse registerResponse = response.body();
                if(response.isSuccessful()){
                    assert registerResponse != null;
                    Log.d("SIGNUP", "onResponseIFIN: " + registerResponse.getToken());
                    SharedPreferences sharedPref = SignUp.this.getSharedPreferences("token,",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.token),registerResponse.getToken());
                    editor.apply();
                    Toast.makeText(SignUp.this,"Signup Completed!",Toast.LENGTH_LONG).show();
                    signUp.setEnabled(true);
                    signIn.setEnabled(true);
                    loading.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(SignUp.this,SignIn.class));
                }else {
                    signUp.setEnabled(true);
                    signIn.setEnabled(true);
                    loading.setVisibility(View.INVISIBLE);
                    Log.d("SIGNUP", "onResponseELSE: ERROR" );
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.d("SIGNUP", "onFailure: FAILED");
                Toast.makeText(SignUp.this,"Error Occured!",Toast.LENGTH_SHORT).show();
                signUp.setEnabled(true);
                signIn.setEnabled(true);
                loading.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
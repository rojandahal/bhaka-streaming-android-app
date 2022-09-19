package com.example.bhakamusic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhakamusic.Apis.RetrofitClient;
import com.example.bhakamusic.R;
import com.example.bhakamusic.RoomDatabase.UserDB.UserCredentials;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Setting extends AppCompatActivity {
    private TextView preference;
    private SwitchCompat switchCompat;
    ImageView back;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Objects.requireNonNull(getSupportActionBar()).hide();
        preference = findViewById(R.id.preference);
        switchCompat = findViewById(R.id.swOnOff);
        back = findViewById(R.id.setting_back);
        preference.setText(UserCredentials.getPreference());

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Call<ResponseBody> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .getPreferenceChange("Bearer " + UserCredentials.getToken());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            final String s = Objects.equals(UserCredentials.getPreference(), "flac") ? "opus" : "flac";
                            UserCredentials.setPreference(s);
                            preference.setText(UserCredentials.getPreference());
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }else {
                    Call<ResponseBody> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .getPreferenceChange("Bearer " + UserCredentials.getToken());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            final String s = Objects.equals(UserCredentials.getPreference(), "flac") ? "opus" : "flac";
                            UserCredentials.setPreference(s);
                            preference.setText(UserCredentials.getPreference());
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
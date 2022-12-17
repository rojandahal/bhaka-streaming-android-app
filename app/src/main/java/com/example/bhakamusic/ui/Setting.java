package com.example.bhakamusic.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
    public static final String TAG = "SETTING";
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
        SharedPreferences sref = getApplication().getSharedPreferences(String.valueOf(getString(R.string.id)), Context.MODE_PRIVATE);
        String id = sref.getString(String.valueOf(R.string.id),UserCredentials.getId());
        preference.setText(id);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Call<ResponseBody> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .getPreferenceChange("Bearer " + UserCredentials.getToken());
                    Log.d("TAG", "onCheckedChanged: " + UserCredentials.getToken());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            final String s = Objects.equals(id, "flac") ? "opus" : "flac";
                            SharedPreferences.Editor edit = sref.edit();
                            edit.putString(String.valueOf(R.string.id),s);
                            edit.apply();
                            preference.setText(s);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }else if (!isChecked) {
                    Call<ResponseBody> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .getPreferenceChange("Bearer " + UserCredentials.getToken());
                    Log.d(TAG, "onCheckedChanged: " + UserCredentials.getToken());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            final String s = Objects.equals(id, "flac") ? "opus" : "flac";
                            SharedPreferences.Editor edit = sref.edit();
                            edit.putString(String.valueOf(R.string.id),s);
                            edit.apply();
                            preference.setText(s);
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
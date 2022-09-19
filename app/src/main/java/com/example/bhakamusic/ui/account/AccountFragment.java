package com.example.bhakamusic.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bhakamusic.R;
import com.example.bhakamusic.RoomDatabase.UserDB.UserCredentials;
import com.example.bhakamusic.databinding.FragmentAccountBinding;
import com.example.bhakamusic.ui.LoginSignup.SignIn;
import com.example.bhakamusic.ui.Player.Player;
import com.example.bhakamusic.ui.Setting;


public class AccountFragment extends Fragment{

    private FragmentAccountBinding binding;
    private TextView username;
    private TextView logout;
    private TextView setting;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        logout = view.findViewById(R.id.logout);
        username = view.findViewById(R.id.username_text);
        setting = view.findViewById(R.id.setting);

        username.setText(UserCredentials.getUsername());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = requireActivity().getApplication().getSharedPreferences(String.valueOf(R.string.token_sharedpref), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(String.valueOf(R.string.token),"token");
                editor.apply();
                Log.d("TAG", "ACCOUNT PAGE: " + sharedPref.getString(String.valueOf(R.string.token),"token"));
                Player.getExoPlayer(getActivity()).release();
                startActivity(new Intent(getActivity(), SignIn.class));
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Setting.class));
                Log.d("TAG", "onClick: " + UserCredentials.getToken());
            }
        });
        return view;
    }
}
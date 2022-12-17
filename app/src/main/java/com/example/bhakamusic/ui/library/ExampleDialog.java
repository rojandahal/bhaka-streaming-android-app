package com.example.bhakamusic.ui.library;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.bhakamusic.R;

public class ExampleDialog extends AppCompatDialogFragment {
    private EditText name;
    private EditText description;
    private ExampleDialogListner listner;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.layout_daialog,null);
        name = view.findViewById(R.id.editTextName);
        description = view.findViewById(R.id.editDescription);
        builder.setView(view)
                .setTitle("Save Playlist")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String playlistName = name.getText().toString();
                        String desc = description.getText().toString();
                        listner.applyText(playlistName,desc);
                    }
                });
        return builder.create();
    }

    public interface ExampleDialogListner{
        void applyText(String name, String description);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listner = (ExampleDialogListner) context;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

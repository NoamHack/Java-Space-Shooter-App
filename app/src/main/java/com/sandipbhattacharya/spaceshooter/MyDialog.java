package com.sandipbhattacharya.spaceshooter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyDialog {
    Dialog dialog;
    Button btnDialogSave, btnDialogCancel;
    Context context;
    public MyDialog(Context context){
        this.context = context;
    }
    //context is reference to the activtiy
    public void createDialog(){
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_layout);
        btnDialogSave = dialog.findViewById(R.id.btnDialogSave);
        btnDialogCancel = dialog.findViewById(R.id.btnDialogCancel);
        dialog.show();

        btnDialogSave.setOnClickListener(view -> {
            GameOver s = new GameOver();
            s.exit(view);
        });
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

    }
}


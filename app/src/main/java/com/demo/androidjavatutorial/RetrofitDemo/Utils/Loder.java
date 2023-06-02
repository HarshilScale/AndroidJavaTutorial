package com.demo.androidjavatutorial.RetrofitDemo.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import com.example.androidjavatutorial.R;


public class Loder {

    Activity activity;
    public static Dialog dialog;

    public Loder(Activity mActivity) {
        this.activity = mActivity;
    }

    public void startLoding() {
        dialog = new Dialog(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        dialog.setContentView(inflater.inflate(R.layout.loder_dialog, null));
        dialog.setCancelable(false);
        dialog.show();
    }

    public void back() {
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    activity.finish();
                    dialog.dismiss();
                }
                return true;
            }
        });
    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}

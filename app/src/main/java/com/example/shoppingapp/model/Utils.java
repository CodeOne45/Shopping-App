package com.example.shoppingapp.model;

import android.content.Context;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {

    public static KProgressHUD kProgressHUD;
    private static final String TAG = "Utils";

    public static String getText(TextView textView){
        return textView.getText().toString().trim();
    }

    public static boolean isEmpty(TextView textView){
        if (textView.getText().toString().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static void showProgressDialogue(Context context){
        kProgressHUD  = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait...")
                .setMaxProgress(100)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
    }

    public static void hideProgressDialogue(){
        kProgressHUD.dismiss();
    }
}

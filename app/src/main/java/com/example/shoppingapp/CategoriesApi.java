package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;


public class CategoriesApi extends AppCompatActivity {
    private static final String TAG = CategoriesApi.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);



    }

    public void appiCoonection(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://amazon24.p.rapidapi.com/api/category?country=US")
                .get()
                .addHeader("x-rapidapi-key", "fd5a8df027msh0b94b28cfd9f50dp164841jsnf9573aea2221")
                .addHeader("x-rapidapi-host", "amazon24.p.rapidapi.com")
                .build();

        //Response response = client.newCall(request).execute();
    }
}

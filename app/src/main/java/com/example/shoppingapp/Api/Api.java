package com.example.shoppingapp.Api;



import com.example.shoppingapp.model.CategoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("category?country=FR")
    Call<List<CategoryModel>> get_categories();
}

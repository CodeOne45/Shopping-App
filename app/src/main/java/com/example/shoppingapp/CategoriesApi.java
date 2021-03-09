package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapp.adapters.CategoryAdapter;
import com.example.shoppingapp.model.Categories;
import com.example.shoppingapp.model.CategoryModel;
import com.example.shoppingapp.model.RetrofitClint;
import com.example.shoppingapp.model.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoriesApi extends AppCompatActivity {

    private EditText search_et;
    private RecyclerView categorie_recycler;
    private List<CategoryModel> data,search_data;
    private CategoryAdapter adapter;
    private static final String TAG = CategoriesApi.class.getSimpleName();

    // User infos
    TextView userName, userBudget;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // User details
        userName = findViewById(R.id.name);
        userBudget = findViewById(R.id.amount);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference dRef = fStore.collection("users").document(userId);
        dRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                userName.setText(documentSnapshot.getString("uName"));
                userBudget.setText(String.valueOf(documentSnapshot.getDouble("budget")) + " €");
            }
        });

        // Category generation

        try {
            getSupportActionBar().hide();
        }catch (Exception e){}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }

        init();

        fetchCategories();

        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    search_data.clear();
                    search_data.addAll(data);
                    Log.d(TAG, "afterTextChanged:empty "+data.size());
                    adapter.notifyDataSetChanged();
                }else{
                    search_data.clear();
                    for (int i=0;i<data.size();i++){
                        if (data.get(i).getName().toLowerCase().contains(s.toString().toLowerCase())){
                            search_data.add(data.get(i));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }

    private void init(){
        search_et = findViewById(R.id.search_et);
        categorie_recycler = findViewById(R.id.categorie_recycler);
        categorie_recycler.setLayoutManager(new StaggeredGridLayoutManager(2,1));
        data = new ArrayList<>();
        search_data = new ArrayList<>();
    }

    private void fetchCategories(){
        Utils.showProgressDialogue(CategoriesApi.this);
        Call<List<CategoryModel>> call = RetrofitClint.getInstance().getApi().get_categories();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                Utils.hideProgressDialogue();
                data = response.body();
                search_data.addAll(data);
                adapter = new CategoryAdapter(getApplicationContext(),search_data);
                categorie_recycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                Utils.hideProgressDialogue();
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(CategoriesApi.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });

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

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}

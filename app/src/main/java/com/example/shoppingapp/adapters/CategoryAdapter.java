package com.example.shoppingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.shoppingapp.Products;
import com.example.shoppingapp.R;
import com.example.shoppingapp.model.CategoryModel;

import java.util.List;
import java.util.Random;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<CategoryModel> data;
    private Integer[] colorList = {R.color.color1,R.color.color2,R.color.color3,R.color.color4};
    private Random random;
    private static final String TAG = "StoreAdapter";

    public CategoryAdapter(Context context, List<CategoryModel> data) {
        this.context = context;
        this.data = data;
        random = new Random();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        final CategoryModel model = data.get(position);

        // Set category name + layout
        holder.name.setText(model.getName());

        holder.root.setCardBackgroundColor(context.getResources().getColor(colorList[holder.getAdapterPosition()%colorList.length]));

        // Open product activity --> intent
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Products.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("category",model);
                context.startActivity(intent);
            }
        });



        if (holder.getAdapterPosition()==1){
            holder.extra_space.setVisibility(View.VISIBLE);
        }else{
            holder.extra_space.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout extra_space;
        private TextView name;
        private ImageView imageView;
        private CardView root;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            extra_space = itemView.findViewById(R.id.extra_space);
            name = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image);
            root = itemView.findViewById(R.id.root);
        }
    }
}

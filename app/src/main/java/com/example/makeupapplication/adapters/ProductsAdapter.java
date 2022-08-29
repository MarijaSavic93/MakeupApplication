package com.example.makeupapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.makeupapplication.R;
import com.example.makeupapplication.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>{

    private ArrayList<Product> productList;
    private Context context;
    private final OnItemClickListener itemClickListener;

    public ProductsAdapter(ArrayList<Product> productList, OnItemClickListener itemClickListener, Context context) {
        this.productList = productList;
        this.itemClickListener = itemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item_layout,parent, false);
        return new ProductViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.tvItemTitle.setText(productList.get(position).getName());
        holder.tvItemBrand.setText(productList.get(position).getBrand());
        holder.tvItemPrice.setText(String.format("%s EUR", productList.get(position).getPrice()));

        Glide.with(context).load(productList.get(position).getImageUrl())
                .fitCenter().placeholder(R.drawable.ic_pending).error(R.drawable.ic_error).into(holder.ivItemImage);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateDataSet(List<Product> newList){
        if(newList != null){
            productList.clear();
            productList.addAll(newList);
        }
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView ivItemImage;
        TextView tvItemTitle, tvItemBrand, tvItemPrice;

        public ProductViewHolder(@NonNull View itemView, OnItemClickListener itemClickListener) {
            super(itemView);

            ivItemImage = itemView.findViewById(R.id.iv_item_image);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            tvItemBrand = itemView.findViewById(R.id.tv_item_brand);
            tvItemPrice = itemView.findViewById(R.id.tv_item_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            itemClickListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }
    }
}

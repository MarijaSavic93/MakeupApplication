package com.example.makeupapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.makeupapplication.models.Product;

public class ProductDetailFragment extends Fragment implements View.OnClickListener {

    private Product product;
    private ImageView ivProduct;
    private TextView tvTitle, tvType, tvBrand, tvPrice;
    private Button btnGoBack;
    private Context context;

    public ProductDetailFragment() {
    }


    public static ProductDetailFragment newInstance() {
        return new ProductDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = ProductDetailFragmentArgs.fromBundle(getArguments()).getProduct();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        initComponents(view);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initComponents(View view){
        ivProduct = view.findViewById(R.id.iv_image);
        Glide.with(context).load(product.getImageUrl())
                .fitCenter().placeholder(R.drawable.ic_pending_white).error(R.drawable.ic_error_white).into(ivProduct);

        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(product.getName());

        tvBrand = view.findViewById(R.id.tv_brand);
        tvBrand.setText(String.format("Brand: %s", product.getBrand()));

        tvPrice = view.findViewById(R.id.tv_price);
        tvPrice.setText(String.format("Price: %s EUR", product.getPrice()));

        tvType = view.findViewById(R.id.tv_type);
        tvType.setText(product.getType());

        btnGoBack = view.findViewById(R.id.btn_go_back);
        btnGoBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        NavDirections action = ProductDetailFragmentDirections
                .actionProductDetailFragmentToProductListFragment();
        Navigation.findNavController(view).navigate(action);
    }
}
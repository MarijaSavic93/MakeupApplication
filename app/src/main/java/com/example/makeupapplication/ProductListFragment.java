package com.example.makeupapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.makeupapplication.adapters.OnItemClickListener;
import com.example.makeupapplication.adapters.ProductsAdapter;
import com.example.makeupapplication.api.RetrofitClient;
import com.example.makeupapplication.models.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductListFragment extends Fragment implements OnItemClickListener {

    private static final String PRODUCT_LIST = "productList";

    private Context context;
    private RecyclerView rvProducts;
    private ProductsAdapter productsAdapter;
    private ArrayList<Product> productList;

    public ProductListFragment() {
    }

    public static ProductListFragment newInstance() {
        return new ProductListFragment();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PRODUCT_LIST, productList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            productList = new ArrayList<>();

        }else{
            productList = savedInstanceState.getParcelableArrayList(PRODUCT_LIST);
        }

        productsAdapter = new ProductsAdapter(productList, this, context);

        if(savedInstanceState == null){
            getListFromServer();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        rvProducts = view.findViewById(R.id.rv_product);
        rvProducts.setLayoutManager(new GridLayoutManager(context, 2));
        rvProducts.setAdapter(productsAdapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onItemClick(View view, int position) {
        NavDirections action = ProductListFragmentDirections
                .actionProductListFragmentToProductDetailFragment(productList.get(position));
        Navigation.findNavController(view).navigate(action);

    }

    public void getListFromServer(){
        Call<List<Product>> call = RetrofitClient.getInstance(context).getApi().getProducts("maybelline");
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        productList.addAll(response.body());
                        productsAdapter.updateDataSet(response.body());
                    }else{
                        Toast.makeText(context, "Response body empty", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Response unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
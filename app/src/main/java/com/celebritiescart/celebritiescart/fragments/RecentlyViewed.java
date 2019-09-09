package com.celebritiescart.celebritiescart.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.celebritiescart.celebritiescart.adapters.ProductAdapter;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.databases.User_Recents_DB;
import com.celebritiescart.celebritiescart.models.product_model.ProductData;
import com.celebritiescart.celebritiescart.models.product_model.ProductDetails;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class RecentlyViewed extends Fragment {

    String customerID;

    TextView emptyRecord, headerText;
    RecyclerView recents_recycler;

    ProductAdapter productAdapter;
    User_Recents_DB recents_db = new User_Recents_DB();

    ArrayList<Integer> recents;
    List<ProductDetails> recentViewedList;
    String CelebrityId="";
    String customerMainId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_products_horizontal, container, false);

        recents = new ArrayList<>();
        recentViewedList = new ArrayList<>();

        // Get the List of RecentlyViewed Product's IDs from the Local Databases User_Recents_DB
        recents = recents_db.getUserRecents();
        CelebrityId=getArguments().getString("CelebrityId");
        // Get the CustomerID from SharedPreferences
        try {
            customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
            customerMainId = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("customerID", "");
        } catch (Exception e){

        }

        // Binding Layout Views
        emptyRecord = rootView.findViewById(R.id.empty_record_text);
        headerText = rootView.findViewById(R.id.products_horizontal_header);
        recents_recycler = rootView.findViewById(R.id.products_horizontal_recycler);


        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);

        // Set text of Header
//        headerText.setText(getString(R.string.recentlyViewed));


        // Initialize the ProductAdapterRemovable and LayoutManager for RecyclerView
        productAdapter = new ProductAdapter(getContext(), recentViewedList, true,"","");
        recents_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Set the Adapter and LayoutManager to the RecyclerView
        recents_recycler.setAdapter(productAdapter);


        // Check if the recents List isn't empty
        if (recents.size() < 1) {
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setVisibility(View.VISIBLE);

            Map<String, String> data = new HashMap<>();

            int i = 0;
            for (Integer obj : recents) {
                data.put("searchCriteria[filterGroups][0][filters][" + i + "][field]", "entity_id");
                data.put("searchCriteria[filterGroups][0][filters][" + i + "][value]", String.valueOf(obj));
                data.put("searchCriteria[filterGroups][0][filters][" + i + "][conditionType]", "eq");
                i++;
            }
            data.put("searchCriteria[sortOrders][0][field]", "created_at");
            data.put("searchCriteria[sortOrders][0][direction]", "DESC");

            RequestProductDetails(data);
        }
        headerText.setVisibility(View.GONE);


        return rootView;
    }


    //*********** Adds Products returned from the Server to the RecentViewedList ********//

    private void addRecentProducts(ProductData productData) {

        // Add Products to recentViewedList
        if (productData.getProductData().size() > 0) {
            recentViewedList.addAll(productData.getProductData());
        }

        // Notify the Adapter
        productAdapter.notifyDataSetChanged();
    }


    //*********** Request the Product's Details from the Server based on Product's ID ********//

    public void RequestProductDetails(final Map<String, String> data) {


        Call<ProductData> call = APIClient.getInstance()
                .getAllProducts(ConstantValues.AUTHORIZATION, data,CelebrityId,customerMainId);


        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {

                if (response.isSuccessful()) {

                    if (!response.body().getProductData().isEmpty()) {
                        // Product's Details has been returned.
                        // Add Product to the recentViewedList
                        addRecentProducts(response.body());

                    } else if (!response.body().getProductData().isEmpty()) {
                        // Product's Details haven't been returned.
                        // Call the method to process some implementations
                        addRecentProducts(response.body());

                    }
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                if(getContext()!=null) {
           //         Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                    }
            }
        });

    }

}


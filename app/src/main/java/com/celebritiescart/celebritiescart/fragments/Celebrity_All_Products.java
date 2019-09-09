package com.celebritiescart.celebritiescart.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.celebritiescart.celebritiescart.adapters.ProductAdapter;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.EndlessRecyclerViewScroll;
import com.celebritiescart.celebritiescart.models.product_model.ProductData;
import com.celebritiescart.celebritiescart.models.product_model.ProductDetails;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class Celebrity_All_Products extends Fragment {

    View rootView;

    private int pageNo = 1;
    String customerID;
    String sortBy = "created_at";
    private int visibleThreshold = 16;
    TextView emptyRecord;
    ProgressBar progressBar;
    RecyclerView all_products_recycler;
    boolean stopLoading = false;

    ProductAdapter productAdapter;
    List<ProductDetails> productsList;

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    private String CategoryID="";
    private String CelebrityID;
    private String BrandID;
    private int totalPages;
    String celebrity;
    String customerMainId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_celebrity_products_vertical, container, false);

        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy");
            }


            if (getArguments().containsKey("CategoryID")) {
                CategoryID = String.valueOf(getArguments().getInt("CategoryID"));


            }
            if (getArguments().containsKey("CelebrityID")) {
                CelebrityID = String.valueOf(getArguments().getInt("CelebrityID"));
            }
            if (getArguments().containsKey("BrandID")) {
                BrandID = String.valueOf(getArguments().getInt("BrandID"));
            }
            if (getArguments().containsKey("celebrity")) {
                celebrity = String.valueOf(getArguments().getString("celebrity"));
            }
        }

        // Get the Customer's ID from SharedPreferences
        try {
            customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
            customerMainId = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("customerID", "");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Binding Layout Views

        emptyRecord = rootView.findViewById(R.id.empty_record);
        progressBar = rootView.findViewById(R.id.loading_bar);
        all_products_recycler = rootView.findViewById(R.id.products_recycler);
        all_products_recycler.setNestedScrollingEnabled(false);

        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);



        productsList = new ArrayList<>();

        // Request for Products based on PageNo.
        RequestAllProducts(pageNo, sortBy);


        // Initialize GridLayoutManager and LinearLayoutManager
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getContext());


        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), productsList, false,celebrity,CategoryID);

        all_products_recycler.setAdapter(productAdapter);


        // Handle the Scroll event of Product's RecyclerView
        all_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll() {
            // Override abstract method onLoadMore() of EndlessRecyclerViewScroll class
            @Override
            public void onLoadMore(int current_page) {
                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                if (!stopLoading && !(pageNo >= totalPages)) {
                    progressBar.setVisibility(View.VISIBLE);
                    pageNo++;
                    new LoadMoreTask(pageNo).execute();
                }
            }
        });

        setRecyclerViewLayoutManager(true);
        productAdapter.notifyDataSetChanged();


        // Toggle RecyclerView's LayoutManager





        return rootView;

    }


    //*********** Switch RecyclerView's LayoutManager ********//

    public void setRecyclerViewLayoutManager(Boolean isGridView) {
        int scrollPosition = 0;

        // If a LayoutManager has already been set, get current Scroll Position
        if (all_products_recycler.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) all_products_recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }

        productAdapter.toggleLayout(isGridView);

        all_products_recycler.setLayoutManager(isGridView ? gridLayoutManager : linearLayoutManager);
        all_products_recycler.setAdapter(productAdapter);

        all_products_recycler.scrollToPosition(scrollPosition);
    }


    public void onLoadMoreFromParent(ProgressBar loading_bar) {
//            loading_bar.setVisibility(View.VISIBLE);
//        all_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
//            // Override abstract method onLoadMore() of EndlessRecyclerViewScroll class
//            @Override
//            public void onLoadMore(int current_page) {
        pageNo++;
        // Execute AsyncTask LoadMoreTask to Load More Products from Server
        new LoadMoreTask(pageNo, loading_bar).execute();

//            }
//        });


    }

    public boolean morePagesAvailable() {
        return !stopLoading && !(pageNo >= totalPages);
    }

    //*********** Adds Products returned from the Server to the ProductsList ********//

    private void addProducts(ProductData productData) {

        // Add Products to ProductsList from the List of ProductData
        for (int i = 0; i < productData.getProductData().size(); i++) {
            ProductDetails productDetails = productData.getProductData().get(i);
            productsList.add(productDetails);
        }

        productAdapter.notifyDataSetChanged();


        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (productAdapter.getItemCount() == 0) {
            emptyRecord.setVisibility(View.VISIBLE);
        } else {
            emptyRecord.setVisibility(View.GONE);
        }

    }


    //*********** Request Products from the Server based on PageNo. ********//

    public void RequestAllProducts(int pageNumber, String sortBy) {

        Call<ProductData> call;

        if (CategoryID != null) {


            call = APIClient.getInstance()
                    .getAllProducts(ConstantValues.AUTHORIZATION, "category_id", CategoryID, "eq", sortBy, "DESC", String.valueOf(visibleThreshold), String.valueOf(pageNumber),CategoryID,customerMainId);
        } else {
            call = APIClient.getInstance()
                    .getAllProducts(ConstantValues.AUTHORIZATION, sortBy, "DESC", String.valueOf(visibleThreshold), String.valueOf(pageNumber),CategoryID,customerMainId);

        }
        progressBar.setVisibility(View.VISIBLE);

        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    emptyRecord.setVisibility(View.GONE);


                }
            });

        }
        stopLoading = false;
        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {

                if (response.isSuccessful()) {
                    if (!response.body().getProductData().isEmpty()) {
                        // Products have been returned. Add Products to the ProductsList
                        addProducts(response.body());
                        totalPages = (response.body().getTotalCount() + visibleThreshold - 1) / visibleThreshold;


                    } else if (response.body().getProductData().isEmpty()) {
                        stopLoading = true;
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    emptyRecord.setVisibility(View.VISIBLE);


                                }
                            });

                        }
                    } else {
                        // Unable to get Success status
                    //    Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }

                    // Hide the ProgressBar


                    progressBar.setVisibility(View.GONE);

                } else {
                    if (getContext() != null) {

                    //    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                if (getContext() != null) {
               //     Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void RequestAllProducts(int pageNumber, String sortBy, ProgressBar loading_bar) {

        Call<ProductData> call;

        if (CategoryID != null) {

            call = APIClient.getInstance()
                    .getAllProducts(ConstantValues.AUTHORIZATION, "category_id", CategoryID, "eq", sortBy, "DESC", String.valueOf(visibleThreshold), String.valueOf(pageNumber),CategoryID,customerMainId);
        } else {
            call = APIClient.getInstance()
                    .getAllProducts(ConstantValues.AUTHORIZATION, sortBy, "DESC", String.valueOf(visibleThreshold), String.valueOf(pageNumber),CategoryID,customerMainId);

        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loading_bar.setVisibility(View.VISIBLE);


            }
        });
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    emptyRecord.setVisibility(View.GONE);


                }
            });

        }
        stopLoading = false;
        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading_bar.setVisibility(View.GONE);

                    }
                });

                if (response.isSuccessful()) {
                    if (!response.body().getProductData().isEmpty()) {
                        // Products have been returned. Add Products to the ProductsList
                        addProducts(response.body());
                        totalPages = (response.body().getTotalCount() + visibleThreshold - 1) / visibleThreshold;


                    } else if (response.body().getProductData().isEmpty()) {
                        stopLoading = true;
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    emptyRecord.setVisibility(View.VISIBLE);


                                }
                            });

                        }
                    } else {
                        // Unable to get Success status
                     //   Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }

                    // Hide the ProgressBar


                    progressBar.setVisibility(View.GONE);

                } else {                                if(getContext()!=null) {

                    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();}
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading_bar.setVisibility(View.GONE);
                    }
                });
                if(getContext()!=null) {

                Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();}
            }
        });
    }


    /*********** LoadMoreTask Used to Load more Products from the Server in the Background Thread using AsyncTask ********/

    private class LoadMoreTask extends AsyncTask<String, Void, String> {

        private ProgressBar loading_bar;
        int page_number;


        private LoadMoreTask(int page_number) {
            this.page_number = page_number;
        }

        private LoadMoreTask(int page_number, ProgressBar loading_bar) {
            this.page_number = page_number;
            this.loading_bar = loading_bar;
        }

        //*********** Runs on the UI thread before #doInBackground() ********//

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//

        @Override
        protected String doInBackground(String... params) {

            // Request for Products based on PageNo.
            if (loading_bar != null) {
                RequestAllProducts(page_number, sortBy, loading_bar);

            } else {
                RequestAllProducts(page_number, sortBy);

            }

            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
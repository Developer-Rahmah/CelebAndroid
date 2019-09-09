package com.celebritiescart.celebritiescart.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.celebritiescart.celebritiescart.adapters.TVAdapter;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.models.videos_model.Post;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.celebritiescart.celebritiescart.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class Celebrity_TV extends Fragment implements BaseSliderView.OnSliderClickListener {

    View rootView;

    int pageNo = 0;
    boolean isGridView;
    String customerID;
    String sortBy = "Newest";
    PagerIndicator pagerIndicator;
    LinearLayout bottomBar;
    LinearLayout sortList;
    TextView emptyRecord;
    TextView sortListText;
    ProgressBar progressBar;
    ToggleButton filterButton;
    ToggleButton toggleLayoutView;
    RecyclerView all_products_recycler;

    TVAdapter productAdapter;
    List<Post> videosList;

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    private String CategorySlug;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_celebrity_tv_vertical, container, false);

        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy");
            }
            if (getArguments().containsKey("CategorySlug")) {
                CategorySlug = getArguments().getString("CategorySlug");
            }

        }


        // Get the Customer's ID from SharedPreferences
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        bottomBar = rootView.findViewById(R.id.bottomBar);
        sortList = rootView.findViewById(R.id.sort_list);
        sortListText = rootView.findViewById(R.id.sort_text);
        emptyRecord = rootView.findViewById(R.id.empty_record);
        progressBar = rootView.findViewById(R.id.loading_bar);
        filterButton = rootView.findViewById(R.id.filterBtn);
        toggleLayoutView = rootView.findViewById(R.id.layout_toggleBtn);
        all_products_recycler = rootView.findViewById(R.id.products_recycler);

//        all_products_recycler.setNestedScrollingEnabled(false);
        bottomBar.setVisibility(View.GONE);

        // Hide some of the Views
        filterButton.setVisibility(View.GONE);
        emptyRecord.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);


        isGridView = true;
        toggleLayoutView.setChecked(isGridView);


        // Set sortListText text
        if (sortBy.equalsIgnoreCase("top seller")) {
            sortListText.setText(getString(R.string.top_seller));
        } else if (sortBy.equalsIgnoreCase("special")) {
            sortListText.setText(getString(R.string.super_deals));
        } else if (sortBy.equalsIgnoreCase("most liked")) {
            sortListText.setText(getString(R.string.most_liked));
        } else {
            sortListText.setText(getString(R.string.newest));
        }


        videosList = new ArrayList<>();

        // Request for Products based on PageNo.
        RequestAllProducts(pageNo, sortBy);


        // Initialize GridLayoutManager and LinearLayoutManager
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getContext());


        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new TVAdapter(getContext(), videosList, false);

        setRecyclerViewLayoutManager(isGridView);
        all_products_recycler.setAdapter(productAdapter);


        // Handle the Scroll event of Product's RecyclerView
//        all_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
//            // Override abstract method onLoadMore() of EndlessRecyclerViewScroll class
//            @Override
//            public void onLoadMore(int current_page) {
//
//
//                progressBar.setVisibility(View.VISIBLE);
//                // Execute AsyncTask LoadMoreTask to Load More Products from Server
//                new LoadMoreTask(current_page).execute();
//            }
//        });

//        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            if (v.getChildAt(v.getChildCount() - 1) != null) {
//                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
//                        scrollY > oldScrollY) {
////                    progressBar.setVisibility(View.VISIBLE);
//
//                    // Execute AsyncTask LoadMoreTask to Load More Products from Server
//                    new LoadMoreTask(1).execute();
//                }
//            }
//        });

        productAdapter.notifyDataSetChanged();


        // Toggle RecyclerView's LayoutManager
        toggleLayoutView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isGridView = isChecked;
                setRecyclerViewLayoutManager(isGridView);
            }
        });


        sortList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get sortBy_array from String_Resources
                final String[] sortArray = getResources().getStringArray(R.array.sortBy_array);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(true);

                dialog.setItems(sortArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String selectedText = sortArray[which];
                        sortListText.setText(selectedText);


                        if (selectedText.equalsIgnoreCase(sortArray[0])) {
                            sortBy = "Newest";
                        } else if (selectedText.equalsIgnoreCase(sortArray[1])) {
                            sortBy = "a to z";
                        } else if (selectedText.equalsIgnoreCase(sortArray[2])) {
                            sortBy = "z to a";
                        } else if (selectedText.equalsIgnoreCase(sortArray[3])) {
                            sortBy = "high to low";
                        } else if (selectedText.equalsIgnoreCase(sortArray[4])) {
                            sortBy = "low to high";
                        } else if (selectedText.equalsIgnoreCase(sortArray[5])) {
                            sortBy = "top seller";
                        } else if (selectedText.equalsIgnoreCase(sortArray[6])) {
                            sortBy = "special";
                        } else if (selectedText.equalsIgnoreCase(sortArray[7])) {
                            sortBy = "most liked";
                        } else {
                            sortBy = "Newest";
                        }


                        videosList.clear();
                        // Request for Products based on sortBy
                        RequestAllProducts(pageNo, sortBy);
                        dialog.dismiss();


                        // Handle the Scroll event of Product's RecyclerView
//                        all_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
//                            // Override abstract method onLoadMore() of EndlessRecyclerViewScroll class
//                            @Override
//                            public void onLoadMore(int current_page) {
//
//
//                                progressBar.setVisibility(View.VISIBLE);
//                                // Execute AsyncTask LoadMoreTask to Load More Products from Server
//                                new LoadMoreTask(current_page).execute();
//                            }
//                        });

                    }
                });
                dialog.show();
            }
        });


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



    //*********** Adds Products returned from the Server to the ProductsList ********//

    private void addProducts(List<Post> productData) {

        // Add Products to ProductsList from the List of ProductData
//        for (int i = 0; i < productData.getProductData().size(); i++) {
//            ProductDetails productDetails = productData.getProductData().get(i);
//            videosList.add(productDetails);
//        }

        videosList.addAll(productData);
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

//        GetAllProducts getAllProducts = new GetAllProducts();
//        getAllProducts.setPageNumber(pageNumber);
//        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
//        getAllProducts.setCustomersId(customerID);
//        getAllProducts.setType(sortBy);

        Call<String> call = APIClient.getInstance()
                .getCelebrityVideoListing(ConstantValues.AUTHORIZATION, "1", CategorySlug);

        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {



                    Type listType = new TypeToken<List<Post>>() {
                    }.getType();

                    List<Post> yourList = new Gson().fromJson(response.body(), listType);


                    addProducts(yourList);
                } else {
                    if(getContext()!=null) {
                        Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();}
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if(getContext()!=null) {
                    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();}
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


    /*********** LoadMoreTask Used to Load more Products from the Server in the Background Thread using AsyncTask ********/

    private class LoadMoreTask extends AsyncTask<String, Void, String> {

        int page_number;


        private LoadMoreTask(int page_number) {
            this.page_number = page_number;
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
            RequestAllProducts(page_number, sortBy);

            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
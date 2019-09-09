package com.celebritiescart.celebritiescart.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.ToggleButton;

import com.celebritiescart.celebritiescart.adapters.TVAdapter;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.DialogLoader;
import com.celebritiescart.celebritiescart.models.banner_model.BannerDetails;
import com.celebritiescart.celebritiescart.models.videos_model.Post;
import com.celebritiescart.celebritiescart.models.videos_model.VideoListing;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class All_TV extends Fragment implements BaseSliderView.OnSliderClickListener {

    View rootView;
    private NestedScrollView nestedScrollView;

    int pageNo = 1;
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
    private List<BannerDetails> bannerImages;
    private SliderLayout sliderLayout;
    private DialogLoader dialogLoader;
    int totalPages;
    LinearLayout topBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_tv_vertical, container, false);

        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy");
            }
        }
        pagerIndicator = rootView.findViewById(R.id.banner_slider_indicator);

        sliderLayout = rootView.findViewById(R.id.banner_slider);

        // Get the Customer's ID from SharedPreferences
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");

        nestedScrollView = rootView.findViewById(R.id.parentScroll);
        // Binding Layout Views
        bottomBar = rootView.findViewById(R.id.bottomBar);
        sortList = rootView.findViewById(R.id.sort_list);
        sortListText = rootView.findViewById(R.id.sort_text);
        emptyRecord = rootView.findViewById(R.id.empty_record);
        progressBar = rootView.findViewById(R.id.loading_bar);
        filterButton = rootView.findViewById(R.id.filterBtn);
        toggleLayoutView = rootView.findViewById(R.id.layout_toggleBtn);
        all_products_recycler = rootView.findViewById(R.id.products_recycler);
        topBar=rootView.findViewById(R.id.topBar);
        all_products_recycler.setNestedScrollingEnabled(false);
        bottomBar.setVisibility(View.GONE);

        // Hide some of the Views
        filterButton.setVisibility(View.GONE);
        emptyRecord.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        dialogLoader = new DialogLoader(getContext());
        dialogLoader.showProgressDialog();

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

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {
//                    progressBar.setVisibility(View.VISIBLE);

                    // Execute AsyncTask LoadMoreTask to Load More Products from Server
                    if (pageNo < totalPages) {
                        pageNo++;
                        new LoadMoreTask(pageNo).execute();
                    }
                }
            }
        });

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
                        pageNo=1;
                        RequestAllProducts(pageNo, sortBy);
                        dialog.dismiss();


                        // Handle the Scroll event of Product's RecyclerView
                        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                            if (v.getChildAt(v.getChildCount() - 1) != null) {
                                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                                        scrollY > oldScrollY) {
//                                    progressBar.setVisibility(View.VISIBLE);

                                    // Execute AsyncTask LoadMoreTask to Load More Products from Server
                                    if (pageNo < totalPages) {
                                        pageNo++;
                                        new LoadMoreTask(pageNo).execute();
                                    }
                                }
                            }
                        });

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

    private void setupBannerSlider() {

        // Initialize new LinkedHashMap<ImageName, ImagePath>
        final LinkedHashMap<String, String> slider_covers = new LinkedHashMap<>();


        // Get bannerDetails at given Position from bannerImages List

        // Put Image's Name and URL to the HashMap slider_covers
        slider_covers.put
                (
                        "Image" + videosList.get(videosList.size() - 1).getTitle(),
                        videosList.get(videosList.size() - 1).getFeaturedImg()
                );


        for (String name : slider_covers.keySet()) {
            // Initialize DefaultSliderView
            final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

            // Set Attributes(Name, Image, Type etc) to DefaultSliderView
            defaultSliderView
                    .description(name)
                    .image(slider_covers.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);


            // Add DefaultSliderView to the SliderLayout
            sliderLayout.addSlider(defaultSliderView);
        }

        // Set PresetTransformer type of the SliderLayout
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);


        // Check if the size of Images in the Slider is less than 2
        if (slider_covers.size() < 2) {
            // Disable PagerTransformer
            sliderLayout.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });


            // Hide Slider PagerIndicator
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);


        } else {
            // Set custom PagerIndicator to the SliderLayout
            sliderLayout.setCustomIndicator(pagerIndicator);

            // Make PagerIndicator Visible
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        }

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

        setupBannerSlider();

    }


    //*********** Request Products from the Server based on PageNo. ********//

    public void RequestAllProducts(int pageNumber, String sortBy) {

//        GetAllProducts getAllProducts = new GetAllProducts();
//        getAllProducts.setPageNumber(pageNumber);
//        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
//        getAllProducts.setCustomersId(customerID);
//        getAllProducts.setType(sortBy);

        Call<String> call = APIClient.getInstance()
                .getVideoListing(ConstantValues.AUTHORIZATION, "null", "null", String.valueOf(ConstantValues.LANGUAGE_ID), String.valueOf(pageNumber), "14");


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                progressBar.setVisibility(View.GONE);
                dialogLoader.hideProgressDialog();
                if (response.isSuccessful()) {


                    VideoListing videoListing = new Gson().fromJson(response.body(), VideoListing.class);
                    totalPages = videoListing.getLastPage();

                    if (videoListing.getPosts().size()!=0) {
                        topBar.setVisibility(View.VISIBLE);
                        emptyRecord.setVisibility(View.GONE);
                        addProducts(videoListing.getPosts());
                    }else {
                        topBar.setVisibility(View.GONE);
                        emptyRecord.setVisibility(View.VISIBLE);
                    }


                } else {
                    if (getContext() != null) {
              //          Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (getContext() != null) {
             //       Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
                dialogLoader.hideProgressDialog();
            }
        });
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        Bundle itemInfo = new Bundle();
        itemInfo.putParcelable("productDetails", videosList.get(videosList.size() - 1));

        // Navigate to Product_Description of selected Product
        Fragment fragment = new TV_Profile();
        fragment.setArguments(itemInfo);
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();


        // Add the Product to User's Recently Viewed Products
//                    if (!recents_db.getUserRecents().contains(product.getProductsId())) {
//                        recents_db.insertRecentItem(product.getProductsId());
//                    }
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
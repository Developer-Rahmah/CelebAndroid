package com.celebritiescart.celebritiescart.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.celebritiescart.celebritiescart.adapters.ProductAdapter;
import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.EndlessRecyclerViewScroll;
import com.celebritiescart.celebritiescart.models.banner_model.BannerDetails;
import com.celebritiescart.celebritiescart.models.product_model.ProductData;
import com.celebritiescart.celebritiescart.models.product_model.ProductDetails;
import com.celebritiescart.celebritiescart.models.videos_model.Post;
import com.celebritiescart.celebritiescart.models.videos_model.VideoProducts;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class TV_Profile extends Fragment {


    private MainActivity myContext;

    private  LinearLayout layoutEndOfList;
    private TextView tvEndOfListMsg;
    View rootView;
    private static String VIDEO_ID = "hSutMuALyAQ";
    int pageNo = 0;
    boolean isGridView;
    String customerID;
    String sortBy = "created_at";
    LinearLayout bottomBar;
    LinearLayout sortList;
    TextView emptyRecord;
    //    YouTubePlayer mainPlayer;
    ProgressBar progressBar;
//    YouTubePlayerView youtubePlayerView;

    RecyclerView all_products_recycler;

    ProductAdapter productAdapter;
    List<ProductDetails> productsList;

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    private int productID;
    private Post productDetails;
    String customerMainId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_tv_profile_pvertical, container, false);

        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy");
            }
        }


        if (getArguments() != null) {

            if (getArguments().containsKey("productDetails")) {
                productDetails = getArguments().getParcelable("productDetails");

                if (productDetails != null) {

//                 VIDEO_ID = YoutubeHelper.extractVideoIdFromUrl(productDetails.getShortContent().toString());
                    if (productDetails.getShortContent() != null) {
                        VIDEO_ID = productDetails.getShortContent().toString();

                    }
                }
                // Set Product Details
//                setProductDetails(productDetails);

            }
        }

        if (getActivity().findViewById(R.id.myLayout).getVisibility()!=View.GONE ) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }



        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
//        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        SpannableString s = new SpannableString(productDetails.getTitle());
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);


        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();


        youTubePlayerFragment.initialize(ConstantValues.YOUTUBE_API_KEY, new com.google.android.youtube.player.YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(com.google.android.youtube.player.YouTubePlayer.Provider provider, com.google.android.youtube.player.YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);

                if (!b) {
                    if (myContext != null) {
                        myContext.youTubePlayer = youTubePlayer;
                    }
                    youTubePlayer.setOnFullscreenListener(_isFullScreen -> {
                        if (myContext != null) {
                            myContext.isYouTubePlayerFullScreen = _isFullScreen;
                        }
                    });
                    youTubePlayer.loadVideo(VIDEO_ID);
                    youTubePlayer.play();
                }

            }

            @Override
            public void onInitializationFailure(com.google.android.youtube.player.YouTubePlayer.Provider arg0, YouTubeInitializationResult error) {
                // TODO Auto-generated method stub

                final int REQUEST_CODE = 1;

                if (getActivity() != null) {
                    if (error.isUserRecoverableError()) {
                        error.getErrorDialog(getActivity(), REQUEST_CODE).show();
                    } else {
                        String errorMessage = String.format("There was an error initializing the YoutubePlayer (%1$s)", error.toString());
                        if(getContext()!=null) {
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();}
                    }
                }
            }


        });


        // Initialize youtube fragment
//        youtubePlayerView = rootView.findViewById(R.id.youtube_player_view);
//        getLifecycle().addObserver(youtubePlayerView);
//
//        youtubePlayerView.initialize(new YouTubePlayerInitListener() {
//
//            @Override
//            public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
//                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
//                    @Override
//                    public void onReady() {
//                        initializedYouTubePlayer.loadVideo(VIDEO_ID, 0);
////                        initializedYouTubePlayer.pause();
//                        mainPlayer = initializedYouTubePlayer;
//                        youtubePlayerView.enterFullScreen();
//                    }
//                });
//            }
//        }, true);


        List<BannerDetails> bannerImages = ((App) getContext().getApplicationContext()).getBannersList();

        // Get the Customer's ID from SharedPreferences
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        customerMainId = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("customerID", "");


        // Binding Layout Views
        bottomBar = rootView.findViewById(R.id.bottomBar);
        sortList = rootView.findViewById(R.id.sort_list);
        emptyRecord = rootView.findViewById(R.id.empty_record);
        progressBar = rootView.findViewById(R.id.loading_bar);

        all_products_recycler = rootView.findViewById(R.id.products_recycler);


        // Hide some of the Views
//        filterButton.setVisibility(View.GONE);
        emptyRecord.setVisibility(View.GONE);
//        progressBar.setVisibility(View.GONE);


        isGridView = true;
//        toggleLayoutView.setChecked(isGridView);


        productsList = new ArrayList<>();

        // Request for Products based on PageNo.
        RequestAllProducts();


        // Initialize GridLayoutManager and LinearLayoutManager
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getContext());


        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), productsList, false,"","");

        setRecyclerViewLayoutManager(isGridView);
        all_products_recycler.setAdapter(productAdapter);


        // Handle the Scroll event of Product's RecyclerView
        all_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar,layoutEndOfList) {
            // Override abstract method onLoadMore() of EndlessRecyclerViewScroll class
            @Override
            public void onLoadMore(int current_page) {
                progressBar.setVisibility(View.VISIBLE);
                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                new LoadMoreTask(current_page).execute();
            }
        });

        productAdapter.notifyDataSetChanged();


        return rootView;

    }


    @Override
    public void onAttach(Context activity) {

        if (activity instanceof MainActivity) {
            myContext = (MainActivity) activity;
        }

        super.onAttach(activity);
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

    public void RequestAllProducts(List<VideoProducts> ids) {

        Call<ProductData> call;
        if (!ids.isEmpty()) {

            Map<String, String> data = new HashMap<>();

            int i = 0;
            for (VideoProducts obj : ids) {
                data.put("searchCriteria[filterGroups][0][filters][" + i + "][field]", "entity_id");
                data.put("searchCriteria[filterGroups][0][filters][" + i + "][value]", String.valueOf(obj.getRelatedId()));
                data.put("searchCriteria[filterGroups][0][filters][" + i + "][conditionType]", "eq");
                i++;
            }
            data.put("searchCriteria[sortOrders][0][field]", "created_at");
            data.put("searchCriteria[sortOrders][0][direction]", "DESC");

            call = APIClient.getInstance()
                    .getAllProducts(ConstantValues.AUTHORIZATION, data,"",customerMainId);

        } else {

            call = APIClient.getInstance()
                    .getAllProducts(ConstantValues.AUTHORIZATION, "category_id", String.valueOf(productDetails.getPostId()), "eq", "created_at", "DESC", String.valueOf(1), String.valueOf(99),"",customerMainId);


        }

        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {

                if (response.isSuccessful()) {
                    if (!response.body().getProductData().isEmpty()) {
                        // Products have been returned. Add Products to the ProductsList
                        addProducts(response.body());

                    } else if (!response.body().getProductData().isEmpty()) {
                        // Products haven't been returned. Call the method to process some implementations
                        addProducts(response.body());
                     //   Snackbar.make(rootView, "sdfds", Snackbar.LENGTH_SHORT).show();
                    } else {
                        // Unable to get Success status
                        Snackbar.make(rootView, "No Products Found", Snackbar.LENGTH_SHORT).show();
                        emptyRecord.setVisibility(View.VISIBLE);
                    }

                    // Hide the ProgressBar
                    progressBar.setVisibility(View.GONE);

                } else {                                if(getContext()!=null) {

                    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();}
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {                                if(getContext()!=null) {

                Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();}
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void RequestAllProducts() {


        Call<String> call = APIClient.getInstance()
                .getVideoProducts(ConstantValues.AUTHORIZATION, productDetails.getPostId());


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {

                if (response.isSuccessful()) {


                    Type listType = new TypeToken<List<VideoProducts>>() {
                    }.getType();

                    List<VideoProducts> yourList = new Gson().fromJson(response.body(), listType);

                    RequestAllProducts(yourList);


                } else {
                    if(getContext()!=null) {
                        Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();}
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if(getContext()!=null) {
                    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();}
            }
        });
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
            // RequestAllProducts(page_number, sortBy);

            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
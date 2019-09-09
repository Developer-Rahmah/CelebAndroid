package com.celebritiescart.celebritiescart.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.models.category_model.CategoryDetails;
import com.celebritiescart.celebritiescart.models.category_model.CategoryFilterData;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Celebrity_Social_Media extends Fragment {

    View rootView;

    private String CategorySlug;
    private String sortBy;
    private String customerID;
    private ProgressBar progressBar;
    private String CategoryID;
    private CategoryDetails categoryData;
    private ImageView snapChat;
    private View socialLayout;
    private ImageView instagram;
    private ImageView facebook;
    private ImageView twitter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.celebrity_social_media, container, false);

        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy");
            }
            if (getArguments().containsKey("CategorySlug")) {
                CategorySlug = getArguments().getString("CategorySlug");
            }
            if (getArguments().containsKey("CategoryID")) {
                CategoryID = String.valueOf( getArguments().getInt("CategoryID"));
//                Log.i("categpryId"," "+CategoryID);
            }

        }
        progressBar = rootView.findViewById(R.id.progressBar);
        snapChat = rootView.findViewById(R.id.snapChat);
        socialLayout = rootView.findViewById(R.id.socialLayout);
        instagram = rootView.findViewById(R.id.insta);
        facebook = rootView.findViewById(R.id.fb);
        twitter = rootView.findViewById(R.id.tw);

        RequestAllProducts();
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(categoryData.getInstagramURL()));

                Intent chooser = Intent.createChooser(intent, "Open " + categoryData.getName() + "'s instagram.");
                startActivity(chooser);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(categoryData.getTwitterURL()));

                Intent chooser = Intent.createChooser(intent, "Open " + categoryData.getName() + "'s twitter.");
                startActivity(chooser);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(categoryData.getFacebookURL()));

                Intent chooser = Intent.createChooser(intent, "Open " + categoryData.getName() + "'s facebook.");
                startActivity(chooser);
            }
        });
        snapChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(categoryData.getSnapchatURL()));

                Intent chooser = Intent.createChooser(intent, "Open " + categoryData.getName() + "'s snapchat.");
                startActivity(chooser);
            }
        });


        // Get the Customer's ID from SharedPreferences
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        return rootView;

    }


    //*********** Switch RecyclerView's LayoutManager ********//


    //*********** Adds Products returned from the Server to the ProductsList ********//


    //*********** Request Products from the Server based on PageNo. ********//

    public void RequestAllProducts() {

        if (isAdded()) {
            progressBar.setVisibility(View.VISIBLE);
            socialLayout.setVisibility(View.GONE);
            snapChat.setVisibility(View.GONE);

        }

        Call<CategoryFilterData> call = APIClient.getInstance()
                .getFilteredCategories(ConstantValues.AUTHORIZATION, "entity_id", CategoryID, "1", "1");


        call.enqueue(new Callback<CategoryFilterData>() {
            @Override
            public void onResponse(Call<CategoryFilterData> call, Response<CategoryFilterData> response) {

                if (response.isSuccessful()) {
                    if (!response.body().getItems().isEmpty()) {
                        if (isAdded()) {
                            progressBar.setVisibility(View.GONE);
                            socialLayout.setVisibility(View.VISIBLE);
                            snapChat.setVisibility(View.VISIBLE);
                        }
                        if (isAdded()) {
                            categoryData = response.body().getItems().get(0);
                        }
                    }
                    // tasks available
                } else {
                    if (isAdded()) {
                        progressBar.setVisibility(View.GONE);

                    }
                    // error response, no access to resource?


                }
            }

            @Override
            public void onFailure(Call<CategoryFilterData> call, Throwable t) {
                // something went completely south (like no internet connection)
                if (isAdded()) {
                    progressBar.setVisibility(View.GONE);

                }

            }
        });
    }


    /*********** LoadMoreTask Used to Load more Products from the Server in the Background Thread using AsyncTask ********/

}
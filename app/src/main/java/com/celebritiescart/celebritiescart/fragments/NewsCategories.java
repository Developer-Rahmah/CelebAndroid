package com.celebritiescart.celebritiescart.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.celebritiescart.celebritiescart.adapters.NewsCategoriesAdapter;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.EndlessRecyclerViewScroll;
import com.celebritiescart.celebritiescart.models.news_model.news_categories.NewsCategoryData;
import com.celebritiescart.celebritiescart.models.news_model.news_categories.NewsCategoryDetails;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class NewsCategories extends Fragment {

    View rootView;

    int pageNo = 0;
    Boolean isHeaderVisible = false;

    ProgressBar progressBar;
    TextView emptyText, headerText;
    RecyclerView news_recycler;

    NewsCategoriesAdapter newsCategoriesAdapter;
    List<NewsCategoryDetails> newsCategoriesList;

    GridLayoutManager gridLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_all, container, false);
        if (getActivity().findViewById(R.id.myLayout).getVisibility()!=View.GONE ) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }

        if (getArguments() != null) {
            if (getArguments().containsKey("isHeaderVisible")) {
                isHeaderVisible = getArguments().getBoolean("isHeaderVisible");
            }
        }

        // Set the Title of Toolbar
        SpannableString s = new SpannableString(getString(R.string.news_categories));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);


        // Binding Layout Views
        headerText = rootView.findViewById(R.id.news_header);
        emptyText = rootView.findViewById(R.id.empty_record_text);
        progressBar = rootView.findViewById(R.id.loading_bar);
        news_recycler = rootView.findViewById(R.id.news_recycler);


        // Hide some of the Views
        emptyText.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        // Check if Header must be Invisible or not
        if (!isHeaderVisible) {
            // Hide the Header of CategoriesList
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.news_categories));
        }


        // Initialize ProductList
        newsCategoriesList = new ArrayList<>();

        // Request for Products based on PageNo.
        RequestAllNewsCategories(pageNo);


        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        // Initialize the ProductAdapter for RecyclerView
        newsCategoriesAdapter = new NewsCategoriesAdapter(getContext(), newsCategoriesList);

        // Set the Adapter and LayoutManager to the RecyclerView
        news_recycler.setAdapter(newsCategoriesAdapter);
        news_recycler.setLayoutManager(gridLayoutManager);



        // Handle the Scroll event of Product's RecyclerView
        news_recycler.addOnScrollListener(new EndlessRecyclerViewScroll() {
            // Override abstract method onLoadMore() of EndlessRecyclerViewScroll class
            @Override
            public void onLoadMore(int current_page) {
                progressBar.setVisibility(View.VISIBLE);
                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                new LoadMoreTask(current_page).execute();
            }
        });


        newsCategoriesAdapter.notifyDataSetChanged();


        return rootView;
    }
    
    
    
    //*********** Add NewsCategories returned to the NewsCategoriesList ********//
    
    private void addNewsCategories(NewsCategoryData newsCategoryData) {
        
        for (int i = 0; i < newsCategoryData.getData().size(); i++) {
            newsCategoriesList.add(newsCategoryData.getData().get(i));
        }

        newsCategoriesAdapter.notifyDataSetChanged();


        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (newsCategoriesAdapter.getItemCount() == 0) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }



    //*********** Request News Categories from the Server based on PageNo. ********//

    public void RequestAllNewsCategories(int pageNumber) {

        Call<NewsCategoryData> call = APIClient.getInstance()
                .allNewsCategories
                        (
                                ConstantValues.LANGUAGE_ID,
                                pageNumber
                        );

        call.enqueue(new Callback<NewsCategoryData>() {
            @Override
            public void onResponse(Call<NewsCategoryData> call, retrofit2.Response<NewsCategoryData> response) {
                
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        
                        // Products have been returned. Add Products to the ProductsList
                        addNewsCategories(response.body());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        addNewsCategories(response.body());
                     //   Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
                    }
                    else {
                        // Unable to get Success status
                    //    Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
    
                    progressBar.setVisibility(View.GONE);
    
                }
                else {
                    if(getContext()!=null) {Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();}
                }
            }

            @Override
            public void onFailure(Call<NewsCategoryData> call, Throwable t) {
                if(getContext()!=null) {Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();}
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
            RequestAllNewsCategories(page_number);

            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}
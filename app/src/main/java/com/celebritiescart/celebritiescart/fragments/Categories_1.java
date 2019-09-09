package com.celebritiescart.celebritiescart.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.celebritiescart.celebritiescart.adapters.CategoryListAdapter_1;
import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.models.category_model.CategoryDetails;
import com.celebritiescart.celebritiescart.models.category_model.CategoryFilterData;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Categories_1 extends Fragment {

    Boolean isMenuItem = true;
    Boolean isHeaderVisible = false;

    TextView emptyText, headerText;
    RecyclerView category_recycler;

    CategoryListAdapter_1 categoryListAdapter;

    List<CategoryDetails> allCategoriesList;
    List<CategoryDetails> mainCategoriesList;
    private BottomNavigationView bottom_nav;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.categories, container, false);

        if (getActivity().findViewById(R.id.myLayout).getVisibility()!=View.GONE ) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }

        if (getArguments() != null) {
            if (getArguments().containsKey("isHeaderVisible")) {
                isHeaderVisible = getArguments().getBoolean("isHeaderVisible", false);
            }
        
            if (getArguments().containsKey("isMenuItem")) {
                isMenuItem = getArguments().getBoolean("isMenuItem", true);
            }
        }
        
        
        if (isMenuItem) {
            // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
            MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

            SpannableString s = new SpannableString(getString(R.string.categories));
            s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);
        }
        requestAllCelebrityCategories();
        bottom_nav = getActivity().findViewById(R.id.myBottomNav);
        bottom_nav.getMenu().findItem(R.id.bottomNavCategory).setChecked(true);

        allCategoriesList = new ArrayList<>();

        // Get CategoriesList from ApplicationContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getHomeCategoriesList();
        
        
        // Binding Layout Views
        emptyText = rootView.findViewById(R.id.empty_record_text);
        headerText = rootView.findViewById(R.id.categories_header);
        category_recycler = rootView.findViewById(R.id.categories_recycler);
        NestedScrollView scroll_container = rootView.findViewById(R.id.scroll_container);
        
        scroll_container.setNestedScrollingEnabled(true);
        category_recycler.setNestedScrollingEnabled(false);
        

        // Hide some of the Views
        emptyText.setVisibility(View.GONE);

        // Check if Header must be Invisible or not
        if (!isHeaderVisible) {
            // Hide the Header of CategoriesList
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.categories));
        }



        mainCategoriesList= new ArrayList<>();

        for (int i=0;  i<allCategoriesList.size();  i++) {

//            if (allCategoriesList.get(i).getParentId().equalsIgnoreCase("0")) {
                mainCategoriesList.add(allCategoriesList.get(i));
//            }
        }


        // Initialize the CategoryListAdapter for RecyclerView
        categoryListAdapter = new CategoryListAdapter_1(getContext(), mainCategoriesList, false);

        // Set the Adapter and LayoutManager to the RecyclerView
        category_recycler.setAdapter(categoryListAdapter);
        category_recycler.setLayoutManager(new GridLayoutManager(getContext(), 1));

        categoryListAdapter.notifyDataSetChanged();


        return rootView;
    }


    public void requestAllCelebrityCategories() {
        List<CategoryDetails> finalItems= new ArrayList<>();
        Call<CategoryFilterData> call = APIClient.getInstance()
                .getFilteredCategories
                        (
                                ConstantValues.AUTHORIZATION, "parent_id", "2", null, null
                        );

        call.enqueue(new Callback<CategoryFilterData>() {
            @Override
            public void onResponse(Call<CategoryFilterData> call, Response<CategoryFilterData> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getItems().isEmpty()) {
                        for (int i=0;i<response.body().getItems().size();i++) {
                            if(response.body().getItems().get(i).getIs_active()){
                                if (isAdded()) {
                                    if(response.body().getItems().get(i).getIs_active()){
                                        finalItems.add((response.body().getItems()).get(i));

                            ((App) getContext().getApplicationContext()).setCategoriesList(finalItems);
                            categoryListAdapter.notifyDataSetChanged();

                        }
                    }
                    // tasks available
                } }}}else {
                    // error response, no access to resource?


                }
            }

            @Override
            public void onFailure(Call<CategoryFilterData> call, Throwable t) {
                // something went completely south (like no internet connection)

            }
        });

    }

}


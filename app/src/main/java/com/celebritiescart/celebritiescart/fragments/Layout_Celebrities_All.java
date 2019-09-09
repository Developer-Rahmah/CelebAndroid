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

import com.celebritiescart.celebritiescart.adapters.AllCelebritiesListAdapter;
import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.DialogLoader;
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


public class Layout_Celebrities_All extends Fragment {

    Boolean isMenuItem = true;
    Boolean isHeaderVisible = false;

    TextView emptyText, headerText;
    RecyclerView category_recycler;

    AllCelebritiesListAdapter categoryListAdapter;

    List<CategoryDetails> allCategoriesList;
    List<CategoryDetails> mainCategoriesList;
    private BottomNavigationView bottom_nav;
    private DialogLoader dialogLoader;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.categories, container, false);

        if (getArguments() != null) {
            if (getArguments().containsKey("isHeaderVisible")) {
                isHeaderVisible = getArguments().getBoolean("isHeaderVisible", false);
            }

            if (getArguments().containsKey("isMenuItem")) {
                isMenuItem = getArguments().getBoolean("isMenuItem", true);
            }
        }

        if (getActivity().findViewById(R.id.myLayout).getVisibility()!=View.GONE ) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }

        if (isMenuItem) {
            // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
            MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            SpannableString s = new SpannableString(getString(R.string.celebritiesall));
            s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);
        }

        bottom_nav = getActivity().findViewById(R.id.myBottomNav);
        bottom_nav.getMenu().findItem(R.id.bottomNavCelebrities).setChecked(true);

        allCategoriesList = new ArrayList<>();

        // Get CategoriesList from ApplicationContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCelebritiesList();

        if(allCategoriesList.isEmpty()) {
            dialogLoader = new DialogLoader(getContext());
            dialogLoader.showProgressDialog();
        }
        ((App) getContext().getApplicationContext()).setListener(() -> {
                    if (categoryListAdapter != null)
                        categoryListAdapter.notifyDataSetChanged();
                    if(dialogLoader!=null) {
                        dialogLoader.hideProgressDialog();
                    }
                }
        );
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


        // Initialize the CategoryListAdapter for RecyclerView
        categoryListAdapter = new AllCelebritiesListAdapter(getContext(), allCategoriesList, false);

        // Set the Adapter and LayoutManager to the RecyclerView
        category_recycler.setAdapter(categoryListAdapter);
        category_recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));

        categoryListAdapter.notifyDataSetChanged();
        getAllCelebrities();

        return rootView;
    }

    public void getAllCelebrities(){
        Call<CategoryFilterData> celebcall1 = APIClient.getInstance()
                .getFilteredCategories
                        (
                                ConstantValues.AUTHORIZATION, "parent_id", "48", "3", "1"
                        );

//        if (isAdded()) {
//
//            if (((App) getContext().getApplicationContext()).getCelebritiesList_2().isEmpty()) {
//                rootView.findViewById(R.id.celebritiesProgressBar).setVisibility(View.VISIBLE);
//            }
//        }

        celebcall1.enqueue(new Callback<CategoryFilterData>() {
            @Override
            public void onResponse(Call<CategoryFilterData> call, Response<CategoryFilterData> response) {
                if(dialogLoader!=null) {
                    dialogLoader.hideProgressDialog();
                }
                if (response.isSuccessful()) {
                    if (!response.body().getItems().isEmpty()) {
                        if (isAdded()) {
                            ((App) getContext().getApplicationContext()).setCelebritiesList_1(response.body().getItems().subList(0, 3));
                            ((App) getContext().getApplicationContext()).setCelebritiesList_2(response.body().getItems().subList(3, 6));
                            ((App) getContext().getApplicationContext()).setCelebritiesList(response.body().getItems());

                            if (categoryListAdapter != null)
                                categoryListAdapter.notifyDataSetChanged();

                        }
                    }
                } else {
                    // error response, no access to resource?


                }
            }

            @Override
            public void onFailure(Call<CategoryFilterData> call, Throwable t) {
                if(dialogLoader!=null) {
                    dialogLoader.hideProgressDialog();
                }

                // something went completely south (like no internet connection)

            }
        });

    }
}


package com.celebritiescart.celebritiescart.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.adapters.SubCategoryListAdapter_1;
import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.models.category_model.CategoryDetails;
import com.celebritiescart.celebritiescart.models.subcategory.SubCategoryModel;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubCategories extends Fragment {

    int parentCategoryID;
    Boolean isHeaderVisible;

    RecyclerView category_recycler;

    SubCategoryListAdapter_1 subCategoryListAdapter_1;

    List<CategoryDetails> allCategoriesList;
    List<CategoryDetails> subCategoriesList;
    ArrayList<Integer>al_categoryId;
    public String CategoryName;
    public int position;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subcategories, container, false);

        if (getActivity().findViewById(R.id.myLayout).getVisibility()!=View.GONE ) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }

        category_recycler = rootView.findViewById(R.id.categories_recycler);

        // Get CategoryID from Bundle arguments
        parentCategoryID = getArguments().getInt("CategoryID");
        CategoryName = getArguments().getString("CategoryName");
        position=getArguments().getInt("position");
        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        SpannableString s = new SpannableString(getArguments().getString("CategoryName", getString(R.string.actionCategory)));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);


        // Get CategoriesList from ApplicationContext
        allCategoriesList = new ArrayList<>();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();



        subCategoriesList = new ArrayList<>();
        al_categoryId=new ArrayList<>();
    
    
        // Initialize the CategoryListAdapter and LayoutManager for RecyclerView

    
        // Set the Adapter to the RecyclerView

        requestSubCategories();
        return rootView;
    }

    public void requestSubCategories() {
        Call<List<SubCategoryModel>> call = APIClient.getInstance()
                .getSubCategories
                        (
                                parentCategoryID
                        );

        call.enqueue(new Callback<List<SubCategoryModel>>() {
            @Override
            public void onResponse(Call<List<SubCategoryModel>> call, Response<List<SubCategoryModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size()!=0) {
                        if (isAdded()) {

                            ((App) getContext().getApplicationContext()).setSubCategory(((App) getContext().getApplicationContext()).getCategoriesList(),response.body(),position);
                            subCategoriesList.addAll(((App) getContext().getApplicationContext()).getCategoriesList());
                            for (int i = 0; i < subCategoriesList.get(position).subCategoryModels.size(); i++) {
                                al_categoryId.add(Integer.valueOf(subCategoriesList.get(position).subCategoryModels.get(i).id));
                            }
                            subCategoryListAdapter_1 = new SubCategoryListAdapter_1(getContext(), subCategoriesList, true, al_categoryId, CategoryName,null,position,parentCategoryID);
                            category_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                            category_recycler.setAdapter(subCategoryListAdapter_1);
                        }
                    }
                    // tasks available
                } else {
                    // error response, no access to resource?


                }
            }

            @Override
            public void onFailure(Call<List<SubCategoryModel>> call, Throwable t) {
                // something went completely south (like no internet connection)

            }
        });

    }

}


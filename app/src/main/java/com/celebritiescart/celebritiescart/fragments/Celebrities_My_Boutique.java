package com.celebritiescart.celebritiescart.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.models.category_model.CategoryDetails;
import com.celebritiescart.celebritiescart.models.category_model.CategoryFilterData;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Celebrities_My_Boutique extends Fragment {

//    SliderLayout sliderLayout;
//    PagerIndicator pagerIndicator;

    //    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();
    private NestedScrollView nestedScrollView;
    private ProgressBar loading_bar;
    private ArrayList<Integer> CategoryIDs = new ArrayList<>();
    private String CategoryName;
    private String CategoryImage;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.celebrities_my_boutique, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
//        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

        if (!CategoryIDs.contains(getArguments().getInt("CategoryID")))
            CategoryIDs.add(getArguments().getInt("CategoryID"));




        CategoryName = getArguments().getString("CategoryName", getString(R.string.actionCategory));
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", true);
        bundle.putBoolean("isMenuItem", false);
        bundle.putIntegerArrayList("CategoryIDs", CategoryIDs);
        bundle.putString("CategoryName", CategoryName);
        bundle.putString("CategoryImage",getArguments().getString("CategoryImage"));
        bundle.putString("celebrity","celebrity");


        // Get FragmentManager
        FragmentManager fragmentManager = getChildFragmentManager();
        Bundle categoryInfo = new Bundle();
        categoryInfo.putInt("CategoryID", CategoryIDs.get(0));
        categoryInfo.putString("CategoryName", CategoryName);
        categoryInfo.putBoolean("HideBottomBar", true);
        categoryInfo.putString("celebrity","celebrity");


        Fragment allProducts = new Celebrity_All_Products();
        allProducts.setArguments(categoryInfo);


        fragmentManager.beginTransaction().replace(R.id.top_seller_fragment, allProducts).commit();

        nestedScrollView = rootView.findViewById(R.id.parentScroll);
        loading_bar = rootView.findViewById(R.id.loading_bar_celebrity);
        TextView seeAllText = rootView.findViewById(R.id.seeAllText);
        ImageView seeAllArrow = rootView.findViewById(R.id.seeAllArrow);
        loading_bar.setVisibility(View.GONE);


        Fragment categories = new Categories_3();
        categories.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.all_categories_fragment, categories).commit();

        seeAllArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    Bundle categoryInfo = new Bundle();
                    categoryInfo.putInt("CategoryID", CategoryIDs.get(0));
                    categoryInfo.putString("CategoryName", CategoryName);
                    categoryInfo.putString("MainCategory", getActivity().getString(R.string.celebrity_category_name));
                    categoryInfo.putInt("celebrityId", CategoryIDs.get(0));

                    // Navigate to Products of selected SubCategory
                    Fragment fragment = new Products();
                    fragment.setArguments(categoryInfo);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }
            }
        });
        seeAllText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    Bundle categoryInfo = new Bundle();
                    categoryInfo.putInt("CategoryID", CategoryIDs.get(0));
                    categoryInfo.putString("CategoryName", CategoryName);
                    categoryInfo.putString("MainCategory", getActivity().getString(R.string.celebrity_category_name));
                    categoryInfo.putInt("celebrityId", CategoryIDs.get(0));
                    // Navigate to Products of selected SubCategory
                    Fragment fragment = new Products();
                    fragment.setArguments(categoryInfo);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }
            }
        });
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {
                    //code to fetch more data for endless scrolling


                    FragmentManager fm = getChildFragmentManager();
                    Celebrity_All_Products fragm = (Celebrity_All_Products) fm.findFragmentById(R.id.top_seller_fragment);
                    if (fragm.morePagesAvailable()) {

                        fragm.onLoadMoreFromParent(loading_bar);
                    }
                }
            }
        });


        return rootView;

    }


    public void requestAllCelebrityCategories() {
        Call<CategoryFilterData> call = APIClient.getInstance()
                .getFilteredCategories
                        (
                                ConstantValues.AUTHORIZATION, "parent_id", "2", "99", "1"
                        );

        call.enqueue(new Callback<CategoryFilterData>() {
            @Override
            public void onResponse(Call<CategoryFilterData> call, Response<CategoryFilterData> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getItems().isEmpty()) {
                        if (isAdded()) {
                            ((App) getContext().getApplicationContext()).setCategoriesList(response.body().getItems());


                        }
                    }
                    // tasks available
                } else {
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


package com.celebritiescart.celebritiescart.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentBrandsProduct extends Fragment {

    String sortBy = "created_at";
    boolean isMenuItem = false;
    boolean isSubFragment = false;

    int selectedTabIndex = 0;
    String selectedTabText = "";



    private ArrayList<Integer> CategoryIDs = new ArrayList<>();
    private String actionBarTitle;
    private String CategoryImage;
    private FrameLayout fl_header_image,fl_fragment_category;
    SliderLayout sliderLayout;
    int categoryId=-1;

    String celebrity="";

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        System.out.print(isVisibleToUser+"");

        }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_brands_product, container, false);
        if (getActivity().findViewById(R.id.myLayout).getVisibility() != View.GONE) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }

        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy", "created_at");
            }

            if (getArguments().containsKey("isMenuItem")) {
                isMenuItem = getArguments().getBoolean("isMenuItem", false);
            }

            if (getArguments().containsKey("CategoryName")) {
                selectedTabText = getArguments().getString("CategoryName", "Category");
            }
            if (getArguments().containsKey("CategoryNameShow")) {
                actionBarTitle = getArguments().getString("CategoryNameShow", "Category");
            }

            if (getArguments().containsKey("CategoryID")) {
                categoryId=getArguments().getInt("CategoryID");
            }
            if (getArguments().containsKey("CategoryImage")) {
                CategoryImage = getArguments().getString("CategoryImage", "");

            }
            if (getArguments().containsKey("celebrity")){
                celebrity = getArguments().getString("celebrity", "");

            }

        }


            if (isMenuItem) {
                MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            } else {
                MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            }

            if (actionBarTitle != null) {
                SpannableString s = new SpannableString(actionBarTitle );//+ " " + getString(R.string.products)
                s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

            } else {
                SpannableString s = new SpannableString(getString(R.string.actionShop));
                s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

            }

        fl_header_image = rootView.findViewById(R.id.fl_header_image);
        fl_fragment_category=rootView.findViewById(R.id.fl_fragment_category);
        sliderLayout = rootView.findViewById(R.id.banner_slider);

        if (CategoryImage != null) {
            fl_header_image.setVisibility(View.VISIBLE);
            setupBannerSlider();
        } else {
            fl_header_image.setVisibility(View.GONE);
        }


        Fragment categoryProducts = new Category_Products();
        Bundle categoryInfo = new Bundle();
        categoryInfo.putString("sortBy", sortBy);
        categoryInfo.putInt("CategoryID", categoryId);
        categoryInfo.putString("celebrity", celebrity);
        categoryProducts.setArguments(categoryInfo);


        categoryProducts.setArguments(categoryInfo);
        FragmentManager fragmentManager = ((MainActivity) getContext()).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fl_fragment_category, categoryProducts)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .addToBackStack(getApplicationContext().getString(R.string.actionHome))
                .commit();

        return rootView;

    }


    private void setupBannerSlider() {

        // Initialize new LinkedHashMap<ImageName, ImagePath>
        final LinkedHashMap<String, String> slider_covers = new LinkedHashMap<>();

        slider_covers.put
                (
                        "Image" + "",
                        CategoryImage
                );


        for (String name : slider_covers.keySet()) {
            // Initialize DefaultSliderView
            final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

            // Set Attributes(Name, Image, Type etc) to DefaultSliderView
            defaultSliderView
                    .description(name)
                    .image(slider_covers.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);


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

        }

    }

}


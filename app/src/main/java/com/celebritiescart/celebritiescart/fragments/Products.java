package com.celebritiescart.celebritiescart.fragments;


import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.adapters.ViewPagerCustomAdapter;
import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.helpers.CustomTabLayout;
import com.celebritiescart.celebritiescart.models.category_model.CategoryDetails;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class Products extends Fragment {
public  int fPosition=2;
    String sortBy = "created_at";
    boolean isMenuItem = false;
    boolean isSubFragment = false;

    int selectedTabIndex = 0;
    String selectedTabText = "";

    CustomTabLayout product_tabs;
    public ViewPager product_viewpager;

    ViewPagerCustomAdapter viewPagerCustomAdapter;
    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
    SharedPreferences.Editor editor = pref.edit();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();
    List<CategoryDetails> allSubCategoriesList = new ArrayList<>();
    private ArrayList<Integer> CategoryIDs = new ArrayList<>();
    private String actionBarTitle;
    private String CategoryImage;
    private FrameLayout fl_header_image;
    SliderLayout sliderLayout;
    String isSubCategory = "false";
    String isSubtoSubCategory="false";
    int position=-1;
    int subPosition=-1;
    String celebrity="";
    String celebrityId="";
    String isTrending="";

    public int getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    private  int currentItem;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get CategoriesList from AppContext
        if (getArguments().containsKey("MainCategory")) {
            String category_name = getArguments().getString("MainCategory");
            if (category_name.equals(getString(R.string.brand_category_name))) {
                allCategoriesList = ((App) getContext().getApplicationContext()).getBrandsList();

            } else if (category_name.equals(getString(R.string.celebrity_category_name))) {
                allCategoriesList = ((App) getContext().getApplicationContext()).getCelebritiesList();

            } else if (category_name.equals(getString(R.string.fashion_category_name))) {
                allCategoriesList = ((App) getContext().getApplicationContext()).getHomeCategoriesList();
            }

        } else {
            allCategoriesList = ((App) getContext().getApplicationContext()).getHomeCategoriesList();
        }


        allSubCategoriesList = new ArrayList<>();

        // Get SubCategoriesList from AllCategoriesList
        //            if (!allCategoriesList.get(i).getParentId().equalsIgnoreCase("0")) {
        //            }
        allSubCategoriesList.addAll(allCategoriesList);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.products, container, false);


        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy", "created_at");
            }

            if (getArguments().containsKey("isMenuItem")) {
                isMenuItem = getArguments().getBoolean("isMenuItem", false);
            }

            if (getArguments().containsKey("isSubFragment")) {
                isSubFragment = getArguments().getBoolean("isSubFragment", false);
            }

            if (getArguments().containsKey("CategoryName")) {
                selectedTabText = getArguments().getString("CategoryName", "Category");
            }
            if (getArguments().containsKey("CategoryNameShow")) {
                actionBarTitle = getArguments().getString("CategoryNameShow", "Category");
            }

            if (getArguments().containsKey("CategoryIDs")) {
                CategoryIDs.addAll(getArguments().getIntegerArrayList("CategoryIDs"));
            }
            if (getArguments().containsKey("CategoryImage")) {
                CategoryImage = getArguments().getString("CategoryImage", "");

            }
            if (getArguments().containsKey("isSubCategory")) {
                isSubCategory = getArguments().getString("isSubCategory", "");

            }
            if (getArguments().containsKey("isSubtoSubCategory")){
                isSubtoSubCategory = getArguments().getString("isSubtoSubCategory", "");

            }

            if (getArguments().containsKey("position")){
                position = getArguments().getInt("position", 0);
                allSubCategoriesList.clear();
                allSubCategoriesList.addAll(((App) getContext().getApplicationContext()).getCategoriesList());
            }

            if (getArguments().containsKey("subposition")){
                subPosition = getArguments().getInt("subposition", 0);

            }
            if (getArguments().containsKey("celebrity")){
                celebrity = getArguments().getString("celebrity", "");


                if (celebrity.equalsIgnoreCase("celebrity")) {
                    celebrityId = getArguments().getString("celebrityId", "");
                }

            }

            if (getArguments().containsKey("isTrending")){
                isTrending = getArguments().getString("isTrending", "");
            }



        }

        fl_header_image = rootView.findViewById(R.id.fl_header_image);
        sliderLayout = rootView.findViewById(R.id.banner_slider);



        // Binding Layout Views
        product_tabs = rootView.findViewById(R.id.product_tabs);
        product_viewpager = rootView.findViewById(R.id.product_viewpager);


        // Setup ViewPagerAdapter
        setupViewPagerAdapter();


        product_viewpager.setOffscreenPageLimit(0);
//        product_viewpager.animate().translationX(0f).setDuration(1000);

        product_viewpager.setAdapter(viewPagerCustomAdapter);
        if (getActivity().findViewById(R.id.myLayout).getVisibility() != View.GONE) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }
        // Toggle Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        if (!isSubFragment) {
            if (isMenuItem) {
                MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            } else {
                MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            }

            if (actionBarTitle != null) {
//                SpannableString s = new SpannableString(actionBarTitle + " " + getString(R.string.products));
                SpannableString s = new SpannableString(actionBarTitle);
                s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

            } else {
                SpannableString s = new SpannableString(getString(R.string.actionShop));
                s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

            }
        }

        if (CategoryImage != null) {
            fl_header_image.setVisibility(View.VISIBLE);
            setupBannerSlider();
        } else {
            fl_header_image.setVisibility(View.GONE);
        }
        // Add corresponding ViewPagers to TabLayouts
//        product_tabs.setupWithViewPager(product_viewpager);
        product_tabs.setupWithViewPager(product_viewpager);
        product_tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                int position = tab.getPosition();
                editor.putInt("position", position);
                editor.apply();
                System.out.print(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                editor.putInt("position", position);
                editor.apply();
            }
        });

        for (int i = 0; i < product_tabs.getTabCount(); i++) {
            //noinspection ConstantConditions
            TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_layout, null);
            Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), ConstantValues.LANGUAGE_ID==1 ?"font/baskvill_regular.OTF":"font/geflow.otf");
            tv.setTypeface(custom_font);
            product_tabs.getTabAt(i).setCustomView(tv);
        }
        try {
            product_tabs.getTabAt(selectedTabIndex).select();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (!isTrending.equalsIgnoreCase("")){
            product_tabs.setVisibility(View.GONE);
        }
product_viewpager.getCurrentItem();

        product_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                getActivity().runOnUiThread(() -> {

                setCurrentItem(position);
                fPosition=position;
                editor.putInt("position", position);
                editor.apply();

//                Toast.makeText(getContext(),
//                        "Selected page position: " + getCurrentItem(), Toast.LENGTH_SHORT).show();
                });
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
        return rootView;

    }


    //*********** Setup the ViewPagerAdapter ********//

    public void setupViewPagerAdapter() {

        // Initialize ViewPagerAdapter with ChildFragmentManager for ViewPager
        viewPagerCustomAdapter = new ViewPagerCustomAdapter(getChildFragmentManager());
        product_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                getActivity().runOnUiThread(() -> {
                    setCurrentItem(position);
                    fPosition=position;
                    editor.putInt("position", position);

                    editor.apply();
//                    view.setCurrentItem(tab.getPosition());

                    if (position == 1) {
                        editor.putInt("positionDesc", position);


                    }else  if (position == 2) {
                        editor.putInt("positionDesc", position);


                    }
                  else  if (position == 3) {
                        editor.putInt("positionDesc", position);


                    }
                    else  if (position == 4) {
                        editor.putInt("positionDesc", position);


                    }
                    else  if (position == 5) {
                        editor.putInt("positionDesc", position);


                    }
//                    Toast.makeText(getContext(),
//                            "Selected page position: " + getCurrentItem(), Toast.LENGTH_SHORT).show();
                });
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
        // Initialize All_Products Fragment with specified arguments
        Fragment allProducts = new All_Products();
        Bundle bundleInfo = new Bundle();
        bundleInfo.putString("sortBy", sortBy);
        bundleInfo.putString("celebrity",celebrity);
       // if (isSubCategory.equalsIgnoreCase("false")&&isSubtoSubCategory.equalsIgnoreCase("false")) {
            if (CategoryIDs.size() > 0) {
                bundleInfo.putInt("CategoryID", CategoryIDs.get(0));
            }
        //}
        allProducts.setArguments(bundleInfo);

        // Add the Fragments to the ViewPagerAdapter with TabHeader
        viewPagerCustomAdapter.addFragment(allProducts, getContext().getString(R.string.all));

        if (position==-1) {

            for (int i = 0; i < allSubCategoriesList.size(); i++) {

                // Initialize Category_Products Fragment with specified arguments
                Fragment categoryProducts = new Category_Products();
                Bundle categoryInfo = new Bundle();
                categoryInfo.putString("sortBy", sortBy);
                categoryInfo.putString("celebrityId",celebrityId);
                if (CategoryIDs.isEmpty()) {
                    categoryInfo.putInt("CategoryID", Integer.parseInt(allSubCategoriesList.get(i).getId()));

                } else {

                    ArrayList<Integer> newCat = new ArrayList<>();
                    newCat.addAll(CategoryIDs);

                    if (!newCat.contains(Integer.parseInt(allSubCategoriesList.get(i).getId())))
                        newCat.add(Integer.parseInt(allSubCategoriesList.get(i).getId()));

                    categoryInfo.putIntegerArrayList("CategoryIDs", newCat);

                }

                categoryInfo.putString("celebrity", celebrity);
                categoryProducts.setArguments(categoryInfo);

                // Add the Fragments to the ViewPagerAdapter with TabHeader
                viewPagerCustomAdapter.addFragment(categoryProducts, allSubCategoriesList.get(i).getName());


                if (selectedTabText.equalsIgnoreCase(allSubCategoriesList.get(i).getName())) {
                    selectedTabIndex = i + 1;
                }
            }
        }else {
            if (isSubCategory.equalsIgnoreCase("true")){

                for (int i = 0; i < allSubCategoriesList.get(position).subCategoryModels.size(); i++) {

                    // Initialize Category_Products Fragment with specified arguments
                    Fragment categoryProducts = new Category_Products();
                    Bundle categoryInfo = new Bundle();
                    categoryInfo.putString("sortBy", sortBy);
                    categoryInfo.putString("celebrityId",celebrityId);
                    categoryInfo.putInt("CategoryID", Integer.parseInt(allSubCategoriesList.get(position).subCategoryModels.get(i).id));

                    /*if (CategoryIDs.isEmpty()) {
                        categoryInfo.putInt("CategoryID", Integer.parseInt(allSubCategoriesList.get(position).subCategoryModels.get(i).id));
                    } else {
                        ArrayList<Integer> newCat = new ArrayList<>();
                        newCat.addAll(CategoryIDs);

                        if (!newCat.contains(Integer.parseInt(allSubCategoriesList.get(position).subCategoryModels.get(i).id)))
                            newCat.add(Integer.parseInt(allSubCategoriesList.get(position).subCategoryModels.get(i).id));

                        categoryInfo.putIntegerArrayList("CategoryIDs", newCat);
                    }*/
                    categoryInfo.putString("celebrity", celebrity);
                    categoryProducts.setArguments(categoryInfo);

                    // Add the Fragments to the ViewPagerAdapter with TabHeader
                    viewPagerCustomAdapter.addFragment(categoryProducts, allSubCategoriesList.get(position).subCategoryModels.get(i).name);


                    if (selectedTabText.equalsIgnoreCase(allSubCategoriesList.get(position).subCategoryModels.get(i).name)) {
                        selectedTabIndex = i + 1;
                    }
                }

            }else if (isSubtoSubCategory.equalsIgnoreCase("true")){

                for (int i = 0; i < allSubCategoriesList.get(position).subCategoryModels.get(subPosition).subcategories.size(); i++) {

                    // Initialize Category_Products Fragment with specified arguments
                    Fragment categoryProducts = new Category_Products();
                    Bundle categoryInfo = new Bundle();
                    categoryInfo.putString("sortBy", sortBy);
                    categoryInfo.putString("celebrityId",celebrityId);
                    categoryInfo.putInt("CategoryID", Integer.parseInt(allSubCategoriesList.get(position).subCategoryModels.get(subPosition).subcategories.get(i).id));

                    // Commented due to inconsitency in product listing
                    /*if (CategoryIDs.isEmpty()) {
                        categoryInfo.putInt("CategoryID", Integer.parseInt(allSubCategoriesList.get(position).subCategoryModels.get(subPosition).subcategories.get(i).id));
                    } else {
                        ArrayList<Integer> newCat = new ArrayList<>();
                        newCat.addAll(CategoryIDs);

                        if (!newCat.contains(Integer.parseInt(allSubCategoriesList.get(position).subCategoryModels.get(subPosition).subcategories.get(i).id)))
                            newCat.add(Integer.parseInt(allSubCategoriesList.get(position).subCategoryModels.get(subPosition).subcategories.get(i).id));

                        categoryInfo.putIntegerArrayList("CategoryIDs", newCat);
                    }*/
                    categoryInfo.putString("celebrity", celebrity);
                    categoryProducts.setArguments(categoryInfo);

                    // Add the Fragments to the ViewPagerAdapter with TabHeader
                    viewPagerCustomAdapter.addFragment(categoryProducts,allSubCategoriesList.get(position).subCategoryModels.get(subPosition).subcategories.get(i).name);


                    if (selectedTabText.equalsIgnoreCase(allSubCategoriesList.get(position).subCategoryModels.get(subPosition).subcategories.get(i).name)) {
                        selectedTabIndex = i + 1;
                    }
                }

            }
        }

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


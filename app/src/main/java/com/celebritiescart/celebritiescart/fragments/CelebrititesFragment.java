package com.celebritiescart.celebritiescart.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.adapters.ViewPagerSimpleAdapter;
import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.helpers.CustomTabLayout;
import com.celebritiescart.celebritiescart.helpers.Typefaces;
import com.celebritiescart.celebritiescart.models.category_model.CategoryDetails;
import com.celebritiescart.celebritiescart.models.category_model.CategoryFilterData;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CelebrititesFragment extends Fragment implements BaseSliderView.OnSliderClickListener {

    ViewPager viewPager;
    CustomTabLayout tabLayout;

    SliderLayout sliderLayout;
    PagerIndicator pagerIndicator;

    List<CategoryDetails> allCategoriesList = new ArrayList<>();
    List<CategoryDetails> allSubCategoriesList = new ArrayList<>();
    private BottomNavigationView bottom_nav;
    private TextView headerText;
    private int CategoryID;
    private String CategoryName;
    private String CategorySlug;
    private String CategoryImage;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_celebritites, container, false);
        if (getActivity().findViewById(R.id.myLayout).getVisibility() != View.GONE) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        // Get BannersList from ApplicationContext
        CategoryID = getArguments().getInt("CategoryID");
        CategoryName = getArguments().getString("CategoryName");
        CategorySlug = getArguments().getString("CategorySlug");
        CategoryImage = getArguments().getString("CategoryImage");


        // Get CategoriesList from AppContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

        bottom_nav = getActivity().findViewById(R.id.myBottomNav);
        bottom_nav.getMenu().findItem(R.id.bottomNavCelebrities).setChecked(true);

        // Binding Layout Views
        viewPager = rootView.findViewById(R.id.myViewPager);
        tabLayout = rootView.findViewById(R.id.tabs);
        sliderLayout = rootView.findViewById(R.id.banner_slider);
        pagerIndicator = rootView.findViewById(R.id.banner_slider_indicator);


        headerText = rootView.findViewById(R.id.celebritySliderName);
        SpannableString s = new SpannableString(CategoryName);
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        headerText.setText(s);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

        allSubCategoriesList = new ArrayList<>();

//        Menu menu = bottom_nav.getMenu();
//
//        for (int i = 0, size = menu.size(); i < size; i++) {
//            MenuItem item = menu.getItem(i);
//            if ()
//
//        }

        // Get SubCategoriesList from AllCategoriesList
        //            if (!allCategoriesList.get(i).getParentId().equalsIgnoreCase("0")) {
        //            }
        allSubCategoriesList.addAll(allCategoriesList);


        // Setup BannerSlider
        setupBannerSlider();


        // Setup ViewPagers
        setupViewPager(viewPager);

        // Add corresponding ViewPagers to TabLayouts
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_layout, null);
            Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), ConstantValues.LANGUAGE_ID==1 ?"font/baskvill_regular.OTF":"font/geflow.otf");
            tv.setTextColor(getResources().getColor(R.color.colorAccentDark));
            tv.setTypeface(custom_font);
            tabLayout.getTabAt(i).setCustomView(tv);
        }

        // Setup CustomTabs for all the Categories
       // setupCustomTabs();
        changeTabsFont();

        return rootView;

    }


    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typefaces.get(getContext(), "baskvill_regular"), Typeface.NORMAL);
                    ;
                }
            }
        }
    }

    //*********** Setup the given ViewPager ********//

    private void
    setupCustomTabs() {

        // Initialize New View for custom Tab
        View tabOne = LayoutInflater.from(getContext()).inflate(R.layout.layout_tabs_custom, null);

        // Set Text of custom Tab
        TextView tabText1 = tabOne.findViewById(R.id.myTabs_text);
        tabText1.setText(getString(R.string.all));

        // Set Icon of custom Tab
        ImageView tabIcon1 = tabOne.findViewById(R.id.myTabs_icon);
        tabIcon1.setImageResource(R.drawable.ic_list);

        // Add tabOne to TabLayout at index 0
        tabLayout.getTabAt(0).setCustomView(tabOne);


        for (int i = 0; i < allSubCategoriesList.size(); i++) {

            // Initialize New View for custom Tab
            View tabNew = LayoutInflater.from(getContext()).inflate(R.layout.layout_tabs_custom, null);

            // Set Text of custom Tab
            TextView tabText2 = tabNew.findViewById(R.id.myTabs_text);
            tabText2.setText(allSubCategoriesList.get(i).getName());

            // Set Icon of custom Tab
            ImageView tabIcon2 = tabNew.findViewById(R.id.myTabs_icon);
            Glide.with(this).asBitmap().load(allSubCategoriesList.get(i).getIcon()).into(tabIcon2);


            // Add tabTwo to TabLayout at specified index
            tabLayout.getTabAt(i + 1).setCustomView(tabNew);
        }
    }


    //*********** Setup the given ViewPager ********//

    private void setupViewPager(ViewPager viewPager) {


        Bundle categoryInfo = new Bundle();
        categoryInfo.putInt("CategoryID", CategoryID);
        categoryInfo.putString("CategoryName", CategoryName);
        categoryInfo.putString("CategorySlug", CategorySlug);
        categoryInfo.putString("CategoryImage", CategoryImage);
        ViewPagerSimpleAdapter viewPagerAdapter = new ViewPagerSimpleAdapter(getChildFragmentManager());

        Fragment celebrities_my_boutique = new Celebrities_My_Boutique();
        celebrities_my_boutique.setArguments(categoryInfo);
        String currentString = CategoryName;
        String[] separated = currentString.split(" ");
         // this will contain "Fruit"
        if(  ConstantValues.LANGUAGE_ID==1) {

            viewPagerAdapter.addFragment(celebrities_my_boutique, (separated[0] + "'s Cart"));
        }else {
            viewPagerAdapter.addFragment(celebrities_my_boutique, (separated[0]+" " + "كارت"));

        }
        //Note: Hide Temporary
       /* Fragment fragment = new Celebrity_TV();
        fragment.setArguments(categoryInfo);
        viewPagerAdapter.addFragment(fragment, getString(R.string.videos));*/
//        Log.i("categpryId"," "+CategoryID);

        Fragment fragmentSocialMedia = new Celebrity_Social_Media();
        fragmentSocialMedia.setArguments(categoryInfo);
        viewPagerAdapter.addFragment(fragmentSocialMedia, getString(R.string.celebrity_social_media));


//        for (int i=0;  i < allSubCategoriesList.size();  i++){
//
//            // Add CategoryID to new Bundle for Fragment arguments
//            Bundle categoryInfo = new Bundle();
//            categoryInfo.putInt("CategoryID", Integer.parseInt(allSubCategoriesList.get(i).getId()));
//
//            // Initialize Category_Products Fragment with specified arguments
//            Fragment fragment = new Category_Products();
//            fragment.setArguments(categoryInfo);
//
//            // Add the Fragments to the ViewPagerAdapter with TabHeader
//            viewPagerAdapter.addFragment(fragment, allSubCategoriesList.get(i).getName());
//        }

        // Set the number of pages that should be retained to either side of the current page
        viewPager.setOffscreenPageLimit(0);
        // Attach the ViewPagerAdapter to given ViewPager
        viewPager.setAdapter(viewPagerAdapter);
    }


    //*********** Setup the BannerSlider with the given List of BannerImages ********//

    private void setupBannerSlider() {

        // Initialize new LinkedHashMap<ImageName, ImagePath>
        final LinkedHashMap<String, String> slider_covers = new LinkedHashMap<>();

        slider_covers.put
                (
                        "Image" + CategoryName,
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

        } else {
            // Set custom PagerIndicator to the SliderLayout
            sliderLayout.setCustomIndicator(pagerIndicator);
            // Make PagerIndicator Visible
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        }

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


    //*********** Handle the Click Listener on BannerImages of Slider ********//

    @Override
    public void onSliderClick(BaseSliderView slider) {


        Bundle bundle = new Bundle();
        bundle.putInt("CategoryID", CategoryID);
        bundle.putString("CategoryName", CategoryName);
        bundle.putString("MainCategory", getString(R.string.celebrity_category_name));
        bundle.putInt("celebrityId", CategoryID);

        // Navigate to Products Fragment
        Fragment fragment = new Products();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();


    }

}


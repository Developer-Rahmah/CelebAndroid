package com.celebritiescart.celebritiescart.fragments;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.adapters.AllCategoriesListAdapter;
import com.celebritiescart.celebritiescart.adapters.AllCelebritiesListAdapter;
import com.celebritiescart.celebritiescart.adapters.ExclusiveBrandsListAdapter;
import com.celebritiescart.celebritiescart.adapters.ProductAdapter;
import com.celebritiescart.celebritiescart.adapters.ViewPagerCustomAdapter;
import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.models.banner_model.Banner;
import com.celebritiescart.celebritiescart.models.banner_model.BannerDetails;
import com.celebritiescart.celebritiescart.models.category_model.CategoryDetails;
import com.celebritiescart.celebritiescart.models.category_model.CategoryFilterData;
import com.celebritiescart.celebritiescart.models.drawer_model.Drawer_Items;
import com.celebritiescart.celebritiescart.models.product_model.ProductData;
import com.celebritiescart.celebritiescart.models.product_model.ProductDetails;
import com.celebritiescart.celebritiescart.models.product_model.WishListData;
import com.celebritiescart.celebritiescart.models.videos_model.Post;
import com.celebritiescart.celebritiescart.models.videos_model.VideoListing;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.network.APIClientBanner;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomePage_1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    View rootView;
    final int sdk = android.os.Build.VERSION.SDK_INT;

    int pageNo = 0;
    String sortBy = "Newest";

    SliderLayout sliderLayout;
    PagerIndicator pagerIndicator;

    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList;
    List<Banner> al_banner = new ArrayList<>();
    List<CategoryDetails> allCelebritiesList1;
    List<CategoryDetails> allCelebritiesList2;
    List<CategoryDetails> allBrandsList;
    List<CategoryDetails> al_ExclusiveBrandsList;
    List<CategoryDetails> featuredBrandsList;

    FragmentManager fragmentManager;
    Fragment recentlyViewed, productsFragment;
    RecyclerView celebRecyclerView;
    private ArrayList<CategoryDetails> mainCategoriesList;
    private AllCelebritiesListAdapter celebrityListAdapter;
//    private AllCelebritiesListAdapter celebrityListAdapter2;

    private ProductAdapter productAdapter;
    List<ProductDetails> categoryProductsList;
    private RecyclerView category_products_recycler;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    private boolean isGridView;
    private String customerID;
    private String customerMainId;
    private int categoryID;
    private RecyclerView exclusivePicksRecyclerView;
    private RecyclerView featureBrandRecyclerView;
    private List<CategoryDetails> exclusivePicksList;
    private ExclusiveBrandsListAdapter exclusiveBrandsAdapter;
    private ExclusiveBrandsListAdapter featuredBrandsAdapter;
    private RecyclerView allCategoriesRecyclerView;
    private AllCategoriesListAdapter allCategoriesAdapter;
    private SliderLayout sliderLayout1;
    private PagerIndicator pagerIndicator1;
    private BottomNavigationView bottom_nav;
    private SliderLayout sliderLayout2;
    private PagerIndicator pagerIndicator2;
    private List<ProductDetails> favouriteProductsList;
    private SwipeRefreshLayout swipeLayout;
    private HomePage_1 homeFragment;
    private List<CategoryDetails> allCelebritiesList;
    TextView celeb_title, featured_brands_title, featured_brands, brands_title, categories_title, video_title, boutique_title, tv_special_collection, shop_look_title;
private ImageView trending_now_img;
private ImageView new_products_img ;
private ImageView sp1_img;
private  ImageView sp2_img;
    LinearLayout ll_video;
    LinearLayout ll_video_coming_soon;
    RelativeLayout rl_video;
    TextView tv_videos_see_all;


    LinearLayout ll_shop;
    LinearLayout ll_shop_coming_soon;
    TextView tv_shop_see_all;


    View featuredBrandsProgress;

    public void RequestWishList() {
        if (ConstantValues.IS_USER_LOGGED_IN) {

            Call<List<WishListData>> call = APIClient.getInstance()
                    .getWishListProducts
                            (
                                    "Bearer " + customerID
                            );

            call.enqueue(new Callback<List<WishListData>>() {
                @Override
                public void onResponse(@NonNull Call<List<WishListData>> call, @NonNull retrofit2.Response<List<WishListData>> response) {


                    // Check if the Response is successful
                    if (response.isSuccessful()) {


                        List<ProductDetails> collect;

                        // Products have been returned. Add Products to the favouriteProductsList
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            collect = response.body().stream().map(WishListData::getProduct).collect(Collectors.toList());
                        } else {
                            collect = new ArrayList<>();
                            for (WishListData s : response.body()) {
                                collect.add(s.getProduct());
                            }

                        }
                        addProducts(collect);


                    } else {


                    }

                }

                @Override
                public void onFailure(Call<List<WishListData>> call, Throwable t) {


                }
            });
        }
    }



    public void RequestBanners() {


        Call<ArrayList<Banner>> call = APIClientBanner.getInstance()
                .getBanners();

        call.enqueue(new Callback<ArrayList<Banner>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Banner>> call, @NonNull retrofit2.Response<ArrayList<Banner>> response) {


                // Check if the Response is successful
                if (response.isSuccessful()) {
                    al_banner = response.body();
                    ((App) getContext().getApplicationContext()).setListBanners(response.body());
                    setupBannerSlider();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Banner>> call, Throwable t) {


            }
        });

    }

    private void addProducts(List<ProductDetails> productData) {

        // Add Products to favouriteProductsList from the List of ProductData
        favouriteProductsList.clear();
        favouriteProductsList.addAll(productData);
        productAdapter.notifyDataSetChanged();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.homepage_1, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        if (getActivity().findViewById(R.id.myLayout).getVisibility() != View.VISIBLE) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.VISIBLE);
        }

        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        SpannableString s = new SpannableString(getString(R.string.app_name));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);
        bottom_nav = getActivity().findViewById(R.id.myBottomNav);
        bottom_nav.getMenu().findItem(R.id.bottomNavHome).setChecked(true);
        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
        favouriteProductsList = ((App) getContext().getApplicationContext()).getFavouriteProductsList();
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        customerMainId = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("customerID", "");

        categoryID = 7;

        // Binding Layout Views
        sliderLayout = rootView.findViewById(R.id.banner_slider);
        sliderLayout1 = rootView.findViewById(R.id.banner_slider1);
        sliderLayout2 = rootView.findViewById(R.id.banner_slider2);
        pagerIndicator = rootView.findViewById(R.id.banner_slider_indicator);
        pagerIndicator1 = rootView.findViewById(R.id.banner_slider_indicator1);
        pagerIndicator2 = rootView.findViewById(R.id.banner_slider_indicator2);
trending_now_img=rootView.findViewById(R.id.trending_now_img);
        new_products_img=rootView.findViewById(R.id.new_products_img);
        sp1_img=rootView.findViewById(R.id.sp1_img);
        sp2_img=rootView.findViewById(R.id.sp2_img);

        ll_video = rootView.findViewById(R.id.ll_video);
        rl_video = rootView.findViewById(R.id.rl_video);
        tv_videos_see_all=rootView.findViewById(R.id.tv_videos_see_all);

         ll_shop= rootView.findViewById(R.id.ll_shop);
         ll_shop_coming_soon= rootView.findViewById(R.id.ll_shop_coming_soon);
         tv_shop_see_all= rootView.findViewById(R.id.tv_shop_see_all);

        celeb_title = rootView.findViewById(R.id.celeb_title);
        featured_brands_title = rootView.findViewById(R.id.featured_brands_title);

        brands_title = rootView.findViewById(R.id.brands_title);
        categories_title = rootView.findViewById(R.id.categories_title);
        video_title = rootView.findViewById(R.id.video_title);
        boutique_title = rootView.findViewById(R.id.boutique_title);
        tv_special_collection = rootView.findViewById(R.id.tv_special_collection);
        shop_look_title = rootView.findViewById(R.id.shop_look_title);
        ll_video_coming_soon=rootView.findViewById(R.id.ll_video_coming_soon);

//        celeb_title.setTypeface(Typeface.DEFAULT_BOLD);
//        featured_brands_title.setTypeface(Typeface.DEFAULT_BOLD);
//        brands_title.setTypeface(Typeface.DEFAULT_BOLD);
//        categories_title.setTypeface(Typeface.DEFAULT_BOLD);
//        video_title.setTypeface(Typeface.DEFAULT_BOLD);
//        boutique_title.setTypeface(Typeface.DEFAULT_BOLD);
//        tv_special_collection.setTypeface(Typeface.DEFAULT_BOLD);
//        shop_look_title.setTypeface(Typeface.DEFAULT_BOLD);

        if(  ConstantValues.LANGUAGE_ID==1){

            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                trending_now_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.trending_now_en) );
                new_products_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.new_products_img_en));
                sp1_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sp_en));
                sp2_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sp2_en));

            } else {
                trending_now_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.trending_now_en));
                new_products_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.new_products_img_en) );
                sp1_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sp_en));
                sp1_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sp_en));
                sp2_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sp2_en));

            }
        }else {
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                trending_now_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.trending_now_ar) );
                new_products_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.new_products_img_ar) );
                sp1_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sp1_ar));
                sp2_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sp2_ar));
            } else {
                trending_now_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.trending_now_ar));
                new_products_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.new_products_img_ar) );
                sp1_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sp1_ar));
                sp2_img.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.sp2_ar));
            }
        }
        trending_now_img.getLayoutParams().height =480;
        new_products_img.getLayoutParams().height =480;
        trending_now_img.requestLayout();
        new_products_img.requestLayout();

        homeFragment = this;
        celebRecyclerView = rootView.findViewById(R.id.cele);

        featuredBrandsProgress = rootView.findViewById(R.id.featured_brands_progress_bar);
        featureBrandRecyclerView = rootView.findViewById(R.id.featured_brands);
        featureBrandRecyclerView.setHasFixedSize(true);

        exclusivePicksRecyclerView = rootView.findViewById(R.id.exclusivePicks);
        exclusivePicksRecyclerView.setHasFixedSize(true);

        allCategoriesRecyclerView = rootView.findViewById(R.id.allCategories);
        allCategoriesRecyclerView.setHasFixedSize(true);
        RequestWishList();


        allCategoriesList = ((App) getContext().getApplicationContext()).getHomeCategoriesList();
        al_banner = ((App) getContext().getApplicationContext()).getListBanners();
        allCelebritiesList1 = ((App) getContext().getApplicationContext()).getCelebritiesList_1();
        allCelebritiesList2 = ((App) getContext().getApplicationContext()).getCelebritiesList_2();
        allCelebritiesList = ((App) getContext().getApplicationContext()).getCelebritiesList();
        allBrandsList = ((App) getContext().getApplicationContext()).getBrandsList();
        /*featuredBrandsList = new ArrayList(allBrandsList);
        Collections.reverse(featuredBrandsList);*/

        al_ExclusiveBrandsList = ((App) getContext().getApplicationContext()).getExclusiveBrand();
        featuredBrandsList = ((App) getContext().getApplicationContext()).getFeaturedBrand();
        if (!allCelebritiesList1.isEmpty()) {
            rootView.findViewById(R.id.celebritiesProgressBar).setVisibility(View.GONE);
        }

        // Initialize the CategoryListAdapter for RecyclerView
        celebrityListAdapter = new AllCelebritiesListAdapter(getContext(), allCelebritiesList1, false);
//        celebrityListAdapter2 = new AllCelebritiesListAdapter(getContext(), allCelebritiesList2, false);
        exclusiveBrandsAdapter = new ExclusiveBrandsListAdapter(getContext(), al_ExclusiveBrandsList, false);
        featuredBrandsAdapter = new ExclusiveBrandsListAdapter(getContext(), featuredBrandsList, false);
        allCategoriesAdapter = new AllCategoriesListAdapter(getContext(), allCategoriesList, false);

        // Set the Adapter and LayoutManager to the RecyclerView
        celebRecyclerView.setAdapter(celebrityListAdapter);
        celebRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        celebRecyclerView.setNestedScrollingEnabled(false);

        featureBrandRecyclerView.setAdapter(featuredBrandsAdapter);
        featureBrandRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));

        exclusivePicksRecyclerView.setAdapter(exclusiveBrandsAdapter);
        exclusivePicksRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));

        allCategoriesRecyclerView.setAdapter(allCategoriesAdapter);
        allCategoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        celebrityListAdapter.notifyDataSetChanged();
//        celebrityListAdapter2.notifyDataSetChanged();
        exclusiveBrandsAdapter.notifyDataSetChanged();
        featuredBrandsAdapter.notifyDataSetChanged();
        allCategoriesAdapter.notifyDataSetChanged();
        categoryProductsList = new ArrayList<>();
        category_products_recycler = rootView.findViewById(R.id.mypicks);


        isGridView = true;

//

        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), categoryProductsList, true, "", "");


        category_products_recycler.setAdapter(productAdapter);
        category_products_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        productAdapter.notifyDataSetChanged();


        // Setup BannerSlider

        // Setup ViewPagers


        swipeLayout = rootView.findViewById(R.id.swipeContainer);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccentDark),
                getResources().getColor(R.color.colorPrimaryLight),
                getResources().getColor(R.color.colorPrimaryDark));

        RequestBanners();
        RequestTwoVideos();
        setupBannerSlider();
        RequestAllCategories();
        RequestCategoryProducts(pageNo, sortBy);
        return rootView;

    }


    //*********** Setup the given ViewPager ********//

    private void setupViewPagerOne(ViewPager viewPager) {

        // Initialize new Bundle for Fragment arguments
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", false);

        // Initialize Fragments
        Fragment topSeller = new Top_Seller();
        Fragment specialDeals = new Special_Deals();
        Fragment mostLiked = new Most_Liked();

        topSeller.setArguments(bundle);
        specialDeals.setArguments(bundle);
        mostLiked.setArguments(bundle);


        // Initialize ViewPagerAdapter with ChildFragmentManager for ViewPager
        ViewPagerCustomAdapter viewPagerCustomAdapter = new ViewPagerCustomAdapter(getChildFragmentManager());

        // Add the Fragments to the ViewPagerAdapter with TabHeader
        viewPagerCustomAdapter.addFragment(topSeller, getString(R.string.top_seller));
        viewPagerCustomAdapter.addFragment(specialDeals, getString(R.string.super_deals));
        viewPagerCustomAdapter.addFragment(mostLiked, getString(R.string.most_liked));


        viewPager.setOffscreenPageLimit(2);

        // Attach the ViewPagerAdapter to given ViewPager
        viewPager.setAdapter(viewPagerCustomAdapter);


    }

    public void setRecyclerViewLayoutManager(Boolean isGridView) {
        int scrollPosition = 0;

        // If a LayoutManager has already been set, get current Scroll Position
        if (category_products_recycler.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) category_products_recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }

        productAdapter.toggleLayout(isGridView);

        category_products_recycler.setLayoutManager(isGridView ? gridLayoutManager : linearLayoutManager);
        category_products_recycler.setAdapter(productAdapter);

        category_products_recycler.scrollToPosition(scrollPosition);
    }


    private void setupBannerSlider() {

        if (!al_banner.isEmpty()) {

            sliderLayout.removeAllSliders();
            sliderLayout1.removeAllSliders();
            sliderLayout2.removeAllSliders();
            // Initialize new LinkedHashMap<ImageName, ImagePath>
            final LinkedHashMap<String, String> slider_covers1 = new LinkedHashMap<>();
            final LinkedHashMap<String, String> slider_covers2 = new LinkedHashMap<>();
            final LinkedHashMap<String, String> slider_covers3 = new LinkedHashMap<>();

            int bannerSize = al_banner.size() / 2;

            for (int i = 0; i < al_banner.size(); i++) {
//                if (bannerSize >= i) {
                    if (!al_banner.get(i).getBanner().isEmpty()) {
                        if (al_banner.get(i).getBanner_position() .equals("top") ) {
                            System.out.print("toop");
                            slider_covers1.put
                                    (
//                                        "Image" + al_banner.get(i).getBanner_text(),
                                            "Image" + i,
                                            al_banner.get(i).getBanner()
                                    );
//                            slider_covers1.put( "Image" + i,al_banner.get(i).getBanner_link_text());


                        } else {

//                            if (!al_banner.get(i).getBanner().isEmpty()) {
                                if (al_banner.get(i).getBanner_position() .equals("bottom") ) {

                                    slider_covers2.put
                                            (
                                                    //  "Image" + al_banner.get(i).getBanner_text(),
                                                    "Image" + i,
                                                    al_banner.get(i).getBanner()


                                            );
//                                    slider_covers2.put( "Image" + i,al_banner.get(i).getBanner_link_text());
                                }
                           // }

                        }
                    }}

            int i = 0;

            for (String name : slider_covers1.keySet()) {
                // Initialize DefaultSliderView

                // Set Attributes(Name, Image, Type etc) to DefaultSliderView
                int finalI = i;
                try {
                    final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

                    defaultSliderView
                            .description(name)
                            .image(slider_covers1.get(name))
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
///here
                                    sliderRedirection(finalI);

                                }
                            });


                    // Add DefaultSliderView to the SliderLayout
                    sliderLayout.addSlider(defaultSliderView);

                    i++;

                } catch (Exception e) {

                    final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());


                    defaultSliderView
                            .description(name)
                            .image(R.drawable.placeholder)
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    sliderRedirection(finalI);
                                }
                            });

                    // Add DefaultSliderView to the SliderLayout
//                    sliderLayout.addSlider(defaultSliderView);
                    i++;


                }

            }


            for (String name : slider_covers2.keySet()) {
                // Initialize DefaultSliderView

                // Set Attributes(Name, Image, Type etc) to DefaultSliderView
                int finalI1 = i;
                try {
                    final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

                    defaultSliderView
                            .description(name)
                            .image(slider_covers2.get(name))
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)

                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                               ////here2
                                    sliderRedirection(finalI1-2);
                                    System.out.print(slider_covers2.get(name));
                                }
                            });


                    // Add DefaultSliderView to the SliderLayout
                    sliderLayout1.addSlider(defaultSliderView);
                    i++;

                } catch (Exception e) {
                    final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());
                    defaultSliderView
                            .description(name)
                            .image(R.drawable.placeholder)
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    sliderRedirection(finalI1);
                                }
                            });


                    // Add DefaultSliderView to the SliderLayout
                    sliderLayout1.addSlider(defaultSliderView);
                    i++;

                }

            }


            // Set PresetTransformer type of the SliderLayout
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
            sliderLayout1.setPresetTransformer(SliderLayout.Transformer.Accordion);


//            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
//            sliderLayout1.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
//            sliderLayout2.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);

            // Check if the size of Images in the Slider is less than 2
//            if (slider_covers.size() < 2) {
//                // Disable PagerTransformer
//                sliderLayout.setPagerTransformer(false, new BaseTransformer() {
//                    @Override
//                    protected void onTransform(View view, float v) {
//                    }
//                });
//                sliderLayout1.setPagerTransformer(false, new BaseTransformer() {
//                    @Override
//                    protected void onTransform(View view, float v) {
//                    }
//                });
//                sliderLayout2.setPagerTransformer(false, new BaseTransformer() {
//                    @Override
//                    protected void onTransform(View view, float v) {
//                    }
//                });
//
//                // Hide Slider PagerIndicator
//                sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
//                sliderLayout1.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
//                sliderLayout2.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
//
//            } else {
//                // Set custom PagerIndicator to the SliderLayout
            sliderLayout.setCustomIndicator(pagerIndicator);
            sliderLayout1.setCustomIndicator(pagerIndicator1);
            // Make PagerIndicator Visible
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
            sliderLayout1.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
//            }

        } else {
            sliderLayout.removeAllSliders();
            sliderLayout1.removeAllSliders();

            final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

            defaultSliderView
                    .description("loading")
                    .image("http://www.aptimize.com/blog")
                    .setScaleType(BaseSliderView.ScaleType.Fit);


            // Add DefaultSliderView to the SliderLayout
            sliderLayout.addSlider(defaultSliderView);
            sliderLayout1.addSlider(defaultSliderView);
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
            sliderLayout1.setPresetTransformer(SliderLayout.Transformer.Accordion);


            sliderLayout.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });
            sliderLayout1.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });

            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
            sliderLayout1.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        }
    }

    private void sliderRedirection(int finalI1) {
        try {
            finalI1=(finalI1-2)+4;
            if (al_banner.get(finalI1).getBanner_type().equalsIgnoreCase("brand")) {


                Bundle categoryInfo = new Bundle();
                categoryInfo.putInt("CategoryID", Integer.parseInt(al_banner.get(finalI1).getLink_id()));
                categoryInfo.putString("CategoryName", al_banner.get(finalI1).getBanner_text());
                categoryInfo.putString("CategoryNameShow", al_banner.get(finalI1).getBanner_text());
                categoryInfo.putString("CategoryImage", al_banner.get(finalI1).getBanner());

                Fragment fragment = new FragmentBrandsProduct();
                fragment.setArguments(categoryInfo);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();


            } else if (al_banner.get(finalI1).getBanner_type().equalsIgnoreCase("product")) {


                Bundle itemInfo = new Bundle();
                itemInfo.putInt("itemID", Integer.parseInt(al_banner.get(finalI1).getLink_id()));

                // Navigate to Product_Description of selected Product
                Fragment fragment = new Product_Description();
                fragment.setArguments(itemInfo);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionCart))
                        .commit();


            } else if (al_banner.get(finalI1).getBanner_type().equalsIgnoreCase("category")) {


                if (al_banner.get(finalI1).getChildren_count().equalsIgnoreCase("0")) {  //Note: change the code if childern is coming in category
                    Bundle categoryInfo = new Bundle();
                    categoryInfo.putInt("CategoryID", Integer.parseInt(al_banner.get(finalI1).getLink_id()));
                    categoryInfo.putString("CategoryName", al_banner.get(finalI1).getBanner_text());

                    Fragment fragment;

                    categoryInfo.putString("MainCategory", getString(R.string.fashion_category_name));

                    // Navigate to Products of selected SubCategory
                    fragment = new Products();
                    fragment.setArguments(categoryInfo);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionCategories)).commit();
                } else {
                    Bundle categoryInfo = new Bundle();
                    categoryInfo.putInt("CategoryID", Integer.parseInt(al_banner.get(finalI1).getId()));
                    categoryInfo.putString("CategoryName", al_banner.get(finalI1).getBanner_text());
                    categoryInfo.putInt("position", finalI1); //Note: Check this also for passing position in subCategory
                    Fragment fragment;


                    categoryInfo.putString("MainCategory", getString(R.string.fashion_category_name));

                    // Navigate to Products of selected SubCategory
                    fragment = new SubCategories();
                    fragment.setArguments(categoryInfo);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionCategories)).commit();
                }


            }
        }catch (Exception e){
//           Toast.makeText(getActivity(),"Some Error from admin side",Toast.LENGTH_SHORT).show();
        }
    }


    //*********** Setup the BannerSlider with the given List of BannerImages ********//



    /*private void setupBannerSlider() {

        if (!allCategoriesList.isEmpty()) {

            sliderLayout.removeAllSliders();
            sliderLayout1.removeAllSliders();
            sliderLayout2.removeAllSliders();
            // Initialize new LinkedHashMap<ImageName, ImagePath>
            final LinkedHashMap<String, String> slider_covers1 = new LinkedHashMap<>();
            final LinkedHashMap<String, String> slider_covers2 = new LinkedHashMap<>();
            final LinkedHashMap<String, String> slider_covers3 = new LinkedHashMap<>();


            // Put Image's Name and URL to the HashMap slider_covers
            if (!allCategoriesList.get(0).getImage().isEmpty()) {

                slider_covers1.put
                        (
                                "Image" + allCategoriesList.get(0).getName(),
                                allCategoriesList.get(0).getImage()
                        );
            }
            if (!allCategoriesList.get(1).getImage().isEmpty()) {
                slider_covers1.put
                        (
                                "Image" + allCategoriesList.get(1).getName(),
                                allCategoriesList.get(1).getImage()
                        );
            }
            if (!allCategoriesList.get(2).getImage().isEmpty()) {

                slider_covers2.put
                        (
                                "Image" + allCategoriesList.get(2).getName(),
                                allCategoriesList.get(2).getImage()
                        );
            }
            if (!allCategoriesList.get(3).getImage().isEmpty()) {

                slider_covers2.put
                        (
                                "Image" + allCategoriesList.get(3).getName(),
                                allCategoriesList.get(3).getImage()
                        );
            }
            if (!allCategoriesList.get(4).getImage().isEmpty()) {

                slider_covers3.put
                        (
                                "Image" + allCategoriesList.get(4).getName(),
                                allCategoriesList.get(4).getImage()
                        );
            }
            if (!allCategoriesList.get(5).getImage().isEmpty()) {

                slider_covers3.put
                        (
                                "Image" + allCategoriesList.get(5).getName(),
                                allCategoriesList.get(5).getImage()
                        );
            }

            int i = 0;

            for (String name : slider_covers1.keySet()) {
                // Initialize DefaultSliderView

                // Set Attributes(Name, Image, Type etc) to DefaultSliderView
                int finalI = i;
                try {
                    final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

                    defaultSliderView
                            .description(name)
                            .image(slider_covers1.get(name))
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {




                                    if (allCategoriesList.get(finalI).children.equalsIgnoreCase("")){

                                        Bundle bundle = new Bundle();
                                        bundle.putInt("CategoryID", Integer.parseInt(allCategoriesList.get(finalI).getId()));
                                        bundle.putString("CategoryName", allCategoriesList.get(finalI).getName());
                                        bundle.putString("MainCategory", getString(R.string.fashion_category_name));

                                        // Navigate to Products Fragment
                                        Fragment fragment = new Products();
                                        fragment.setArguments(bundle);
                                        getFragmentManager().beginTransaction()
                                                .replace(R.id.main_fragment, fragment)
                                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                                .addToBackStack(getString(R.string.actionHome)).commit();

                                    }else{
                                        Bundle categoryInfo = new Bundle();
                                        categoryInfo.putInt("CategoryID", Integer.parseInt(allCategoriesList.get(finalI).getId()));
                                        categoryInfo.putString("CategoryName", allCategoriesList.get(finalI).getName());
                                        categoryInfo.putInt("position",finalI);
                                        Fragment fragment;

                                        categoryInfo.putString("MainCategory", getActivity().getString(R.string.fashion_category_name));

                                        // Navigate to Products of selected SubCategory
                                        fragment = new SubCategories();
                                        fragment.setArguments(categoryInfo);
                                        FragmentManager fragmentManager = ((MainActivity) getActivity()).getSupportFragmentManager();
                                        fragmentManager.beginTransaction()
                                                .replace(R.id.main_fragment, fragment)
                                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                                .addToBackStack(getActivity().getString(R.string.actionCategories)).commit();
                                    }


                                }
                            });


                    // Add DefaultSliderView to the SliderLayout
                    sliderLayout.addSlider(defaultSliderView);
                    i++;

                } catch (Exception e) {

                    final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());


                    defaultSliderView
                            .description(name)
                            .image(R.drawable.placeholder)
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("CategoryID", Integer.parseInt(allCategoriesList.get(finalI).getId()));
                                    bundle.putString("CategoryName", allCategoriesList.get(finalI).getName());
                                    bundle.putString("MainCategory", getString(R.string.fashion_category_name));

                                    // Navigate to Products Fragment
                                    Fragment fragment = new Products();
                                    fragment.setArguments(bundle);
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.main_fragment, fragment)
                                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                            .addToBackStack(getString(R.string.actionHome)).commit();
                                }
                            });

                    // Add DefaultSliderView to the SliderLayout
//                    sliderLayout.addSlider(defaultSliderView);
                    i++;


                }

            }


            for (String name : slider_covers2.keySet()) {
                // Initialize DefaultSliderView

                // Set Attributes(Name, Image, Type etc) to DefaultSliderView
                int finalI1 = i;
                try {
                    final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

                    defaultSliderView
                            .description(name)
                            .image(slider_covers2.get(name))
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("CategoryID", Integer.parseInt(allCategoriesList.get(finalI1).getId()));
                                    bundle.putString("CategoryName", allCategoriesList.get(finalI1).getName());
                                    bundle.putString("MainCategory", getString(R.string.fashion_category_name));

                                    // Navigate to Products Fragment
                                    Fragment fragment = new Products();
                                    fragment.setArguments(bundle);
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.main_fragment, fragment)
                                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                            .addToBackStack(getString(R.string.actionHome)).commit();
                                }
                            });


                    // Add DefaultSliderView to the SliderLayout
                    sliderLayout1.addSlider(defaultSliderView);
                    i++;

                } catch (Exception e) {
                    final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());
                    defaultSliderView
                            .description(name)
                            .image(R.drawable.placeholder)
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("CategoryID", Integer.parseInt(allCategoriesList.get(finalI1).getId()));
                                    bundle.putString("CategoryName", allCategoriesList.get(finalI1).getName());
                                    bundle.putString("MainCategory", getString(R.string.fashion_category_name));

                                    // Navigate to Products Fragment
                                    Fragment fragment = new Products();
                                    fragment.setArguments(bundle);
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.main_fragment, fragment)
                                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                            .addToBackStack(getString(R.string.actionHome)).commit();
                                }
                            });


                    // Add DefaultSliderView to the SliderLayout
                    sliderLayout1.addSlider(defaultSliderView);
                    i++;

                }

            }

            for (String name : slider_covers3.keySet()) {

                // Initialize DefaultSliderView

                // Set Attributes(Name, Image, Type etc) to DefaultSliderView
                int finalI2 = i;
                try {
                    final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

                    defaultSliderView
                            .description(name)
                            .image(slider_covers3.get(name))
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("CategoryID", Integer.parseInt(allCategoriesList.get(finalI2).getId()));
                                    bundle.putString("CategoryName", allCategoriesList.get(finalI2).getName());
                                    bundle.putString("MainCategory", getString(R.string.fashion_category_name));

                                    // Navigate to Products Fragment
                                    Fragment fragment = new Products();
                                    fragment.setArguments(bundle);
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.main_fragment, fragment)
                                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                            .addToBackStack(getString(R.string.actionHome)).commit();
                                }
                            });

                    // Add DefaultSliderView to the SliderLayout
                    sliderLayout2.addSlider(defaultSliderView);
                    i++;

                } catch (Exception e) {
                    final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

                    defaultSliderView
                            .description(name)
                            .image(R.drawable.placeholder)
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("CategoryID", Integer.parseInt(allCategoriesList.get(finalI2).getId()));
                                    bundle.putString("CategoryName", allCategoriesList.get(finalI2).getName());
                                    bundle.putString("MainCategory", getString(R.string.fashion_category_name));

                                    // Navigate to Products Fragment
                                    Fragment fragment = new Products();
                                    fragment.setArguments(bundle);
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.main_fragment, fragment)
                                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                            .addToBackStack(getString(R.string.actionHome)).commit();
                                }
                            });

                    // Add DefaultSliderView to the SliderLayout
                    sliderLayout2.addSlider(defaultSliderView);
                    i++;

                }

            }


            // Set PresetTransformer type of the SliderLayout
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
            sliderLayout1.setPresetTransformer(SliderLayout.Transformer.Accordion);
            sliderLayout2.setPresetTransformer(SliderLayout.Transformer.Accordion);


//            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
//            sliderLayout1.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
//            sliderLayout2.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);

            // Check if the size of Images in the Slider is less than 2
//            if (slider_covers.size() < 2) {
//                // Disable PagerTransformer
//                sliderLayout.setPagerTransformer(false, new BaseTransformer() {
//                    @Override
//                    protected void onTransform(View view, float v) {
//                    }
//                });
//                sliderLayout1.setPagerTransformer(false, new BaseTransformer() {
//                    @Override
//                    protected void onTransform(View view, float v) {
//                    }
//                });
//                sliderLayout2.setPagerTransformer(false, new BaseTransformer() {
//                    @Override
//                    protected void onTransform(View view, float v) {
//                    }
//                });
//
//                // Hide Slider PagerIndicator
//                sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
//                sliderLayout1.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
//                sliderLayout2.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
//
//            } else {
//                // Set custom PagerIndicator to the SliderLayout
            sliderLayout.setCustomIndicator(pagerIndicator);
            sliderLayout1.setCustomIndicator(pagerIndicator1);
            sliderLayout2.setCustomIndicator(pagerIndicator2);
            // Make PagerIndicator Visible
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
            sliderLayout1.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
            sliderLayout2.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
//            }

        } else {
            sliderLayout.removeAllSliders();
            sliderLayout1.removeAllSliders();
            sliderLayout2.removeAllSliders();
            final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

            defaultSliderView
                    .description("loading")
                    .image("http://www.aptimize.com/blog")
                    .setScaleType(BaseSliderView.ScaleType.Fit);


            // Add DefaultSliderView to the SliderLayout
            sliderLayout.addSlider(defaultSliderView);
            sliderLayout1.addSlider(defaultSliderView);
            sliderLayout2.addSlider(defaultSliderView);
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
            sliderLayout1.setPresetTransformer(SliderLayout.Transformer.Accordion);
            sliderLayout2.setPresetTransformer(SliderLayout.Transformer.Accordion);

            sliderLayout.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });
            sliderLayout1.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });
            sliderLayout2.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
            sliderLayout1.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
            sliderLayout2.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        }
    }*/


    private void RequestTwoVideos() {
        Call<String> call = APIClient.getInstance()
                .getVideoListing(ConstantValues.AUTHORIZATION, "null", "null", String.valueOf(ConstantValues.LANGUAGE_ID), "1", "4");


        if (isAdded()) {

            if (getContext() != null) {
                rootView.findViewById(R.id.stlProgressBar).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.stl1).setVisibility(View.GONE);
                rootView.findViewById(R.id.stl2).setVisibility(View.GONE);
                rootView.findViewById(R.id.videosProgressBar).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.video1).setVisibility(View.GONE);
                rootView.findViewById(R.id.video2).setVisibility(View.GONE);

            }

        }
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                rootView.findViewById(R.id.videosProgressBar).setVisibility(View.GONE);
                rootView.findViewById(R.id.video1).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.video2).setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {


                    VideoListing videoListing = new Gson().fromJson(response.body(), VideoListing.class);

                    if (videoListing.getPosts().size() != 0) {

                       /* ll_video.setVisibility(View.VISIBLE);
                        tv_videos_see_all.setVisibility(View.VISIBLE);
                        ll_video_coming_soon.setVisibility(View.GONE);

                        tv_shop_see_all.setVisibility(View.VISIBLE);
                        ll_shop.setVisibility(View.VISIBLE);
                        ll_shop_coming_soon.setVisibility(View.GONE);*/

                        //Note: Hide temporary
                        ll_video.setVisibility(View.GONE);
                        tv_videos_see_all.setVisibility(View.GONE);
                        ll_video_coming_soon.setVisibility(View.GONE);

                        tv_shop_see_all.setVisibility(View.GONE);
                        ll_shop.setVisibility(View.GONE);
                        ll_shop_coming_soon.setVisibility(View.GONE);

                        setUpVideos(videoListing.getPosts());
                    }else {
                      /*  tv_videos_see_all.setVisibility(View.GONE);
                        ll_video.setVisibility(View.GONE);
                        ll_video_coming_soon.setVisibility(View.VISIBLE);

                        tv_shop_see_all.setVisibility(View.GONE);
                        ll_shop.setVisibility(View.GONE);
                        ll_shop_coming_soon.setVisibility(View.VISIBLE);*/

                        //Note: Hide temporary
                        tv_videos_see_all.setVisibility(View.GONE);
                        ll_video.setVisibility(View.GONE);
                        ll_video_coming_soon.setVisibility(View.GONE);

                        tv_shop_see_all.setVisibility(View.GONE);
                        ll_shop.setVisibility(View.GONE);
                        ll_shop_coming_soon.setVisibility(View.GONE);

                    }


                } else {
                    if (getContext() != null) {
                        //    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                Log.i("error",t.toString());
                if (getContext() != null) {
                    //    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
                if (isAdded()) {
                    rootView.findViewById(R.id.videosProgressBar).setVisibility(View.GONE);
                }

            }
        });
    }

    private void setUpVideos(List<Post> posts) {
        if (getContext() != null && getActivity() != null) {


            rootView.findViewById(R.id.stlProgressBar).setVisibility(View.GONE);

            //Note: Hide temporary
          /*  rootView.findViewById(R.id.stl1).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.stl2).setVisibility(View.VISIBLE);*/

            rootView.findViewById(R.id.stl1).setVisibility(View.GONE);
            rootView.findViewById(R.id.stl2).setVisibility(View.GONE);


            Glide.with(getContext())
                    .load(posts.get(2).getFeaturedImage())
                    .apply(new RequestOptions().centerCrop()
                            .placeholder(R.drawable.placeholder).error(R.drawable.placeholder))
                    .into((ImageView) rootView.findViewById(R.id.stl1cover));
            Glide.with(getContext())
                    .load(posts.get(3).getFeaturedImage())
                    .apply(new RequestOptions().centerCrop()
                            .placeholder(R.drawable.placeholder).error(R.drawable.placeholder))
                    .into((ImageView) rootView.findViewById(R.id.stl2cover));


            Button c1 = rootView.findViewById(R.id.shopthelook1);
            ImageView stl1cover = rootView.findViewById(R.id.stl1cover);
            ImageView stl2cover = rootView.findViewById(R.id.stl2cover);


            stl1cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails", posts.get(2));

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new TV_Profile();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();
                }
            });


            stl2cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails", posts.get(3));

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new TV_Profile();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();
                }
            });

            c1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails", posts.get(2));

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new TV_Profile();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();
                }
            });
            Button c2 = rootView.findViewById(R.id.shopthelook2);

            c2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails", posts.get(3));

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new TV_Profile();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();
                }
            });


            Glide.with(getContext())
                    .load(posts.get(0).getFeaturedImg()).apply(new RequestOptions().centerCrop()
                    .placeholder(R.drawable.placeholder).error(R.drawable.placeholder))
                    .into((ImageView) rootView.findViewById(R.id.video1Img));
            Glide.with(getContext())
                    .load(posts.get(1).getFeaturedImg()).apply(new RequestOptions().centerCrop()
                    .placeholder(R.drawable.placeholder).error(R.drawable.placeholder))
                    .into((ImageView) rootView.findViewById(R.id.video2Img));
            TextView vT1 = rootView.findViewById(R.id.video1Title);
            TextView vT2 = rootView.findViewById(R.id.video2Title);
            vT1.setText(posts.get(0).getTitle());
            vT2.setText(posts.get(1).getTitle());

            View v1 = rootView.findViewById(R.id.video1);
            View v2 = rootView.findViewById(R.id.video2);
            ImageButton vb1 = rootView.findViewById(R.id.video1Play);
            ImageButton vb2 = rootView.findViewById(R.id.video2Play);


            v1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get Product Info
                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails", posts.get(0));

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new TV_Profile();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();
                }
            });


            v2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get Product Info
                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails", posts.get(1));

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new TV_Profile();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();
                }
            });
            vb1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get Product Info
                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails", posts.get(0));

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new TV_Profile();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();
                }
            });


            vb2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get Product Info
                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails", posts.get(1));

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new TV_Profile();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();
                }
            });


        }
    }

    private void RequestAllCategories() {
List <CategoryDetails> finalItems= new ArrayList<>();
//       Get All default categories
        Call<CategoryFilterData> call = APIClient.getInstance()
                .getFilteredCategories
                        (
                                ConstantValues.AUTHORIZATION, "parent_id", "2", "99", "1"
                        );
        if (isAdded()) {

            if (((App) getContext().getApplicationContext()).getCategoriesList().isEmpty()) {
                rootView.findViewById(R.id.categoriesProgressBar).setVisibility(View.VISIBLE);
            }
        }
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

                                rootView.findViewById(R.id.categoriesProgressBar).setVisibility(View.GONE);
                                //  setupBannerSlider();
                                allCategoriesAdapter.notifyDataSetChanged();

                                if (getActivity() != null) {

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        ((MainActivity) getActivity()).listDataHeader.stream().filter(Objects::nonNull)
                                                .filter(p -> p.getTitle().equals(getString(R.string.actionCategories))).collect(Collectors.toList()).get(0).setCount(String.valueOf(((App) getContext().getApplicationContext()).getHomeCategoriesList().size()));
                                    } else {

                                        for (Drawer_Items person : ((MainActivity) getActivity()).listDataHeader) {
                                            if (person.getTitle().equals(getString(R.string.actionCategories))) {
                                                person.setCount(String.valueOf(((App) getContext().getApplicationContext()).getHomeCategoriesList().size()));
                                            }
                                        }

                                    }
                                    ((MainActivity) getActivity()).drawerExpandableAdapter.notifyDataSetChanged();

                                }
                            }
                        }}}
                    }
                    // tasks available
                } else {
                    // error response, no access to resource?


                }
            }

            @Override
            public void onFailure(Call<CategoryFilterData> call, Throwable t) {
                if (getContext() != null) {
                    //    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
                if (isAdded()) {
                    rootView.findViewById(R.id.categoriesProgressBar).setVisibility(View.GONE);
                }
                // something went completely south (like no internet connection)

            }
        });


        //       Get All celebrities
        Call<CategoryFilterData> celebcall1 = APIClient.getInstance()
                .getFilteredCategories
                        (
                                ConstantValues.AUTHORIZATION, "parent_id",
                                "48", null, null
                        );

        if (isAdded()) {

            if (((App) getContext().getApplicationContext()).getCelebritiesList_2().isEmpty()) {
                rootView.findViewById(R.id.celebritiesProgressBar).setVisibility(View.VISIBLE);

            }

        }

        celebcall1.enqueue(new Callback<CategoryFilterData>() {
            @Override
            public void onResponse(Call<CategoryFilterData> call, Response<CategoryFilterData> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getItems().isEmpty()) {

                        if (isAdded()) {
                            ((App) getContext().getApplicationContext()).setCelebritiesList_1(response.body().getItems().subList(0, 6));
                            ((App) getContext().getApplicationContext()).setCelebritiesList_2(response.body().getItems().subList(3, 6));
                            ((App) getContext().getApplicationContext()).setCelebritiesList(response.body().getItems());

                            rootView.findViewById(R.id.celebritiesProgressBar).setVisibility(View.GONE);


                            celebrityListAdapter.notifyDataSetChanged();
//                            celebrityListAdapter2.notifyDataSetChanged();

                           /* if (getActivity() != null) {

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    ((MainActivity) getActivity()).listDataHeader.stream().filter(Objects::nonNull)
                                            .filter(p -> p.getTitle().equals(getString(R.string.celebritiesNav).toUpperCase())).collect(Collectors.toList()).get(0).setCount(String.valueOf(((App) getContext().getApplicationContext()).getCelebritiesList().size()));
                                } else {

                                    for (Drawer_Items person : ((MainActivity) getActivity()).listDataHeader) {
                                        if (person.getTitle().equals(getString(R.string.celebritiesNav).toUpperCase())) {
                                            person.setCount(String.valueOf(((App) getContext().getApplicationContext()).getCelebritiesList().size()));
                                        }
                                    }

                                }
                                ((MainActivity) getActivity()).drawerExpandableAdapter.notifyDataSetChanged();

                            }*/
                        }
                    }
                } else {
                    // error response, no access to resource?


                }
            }

            @Override
            public void onFailure(Call<CategoryFilterData> call, Throwable t) {
                // something went completely south (like no internet connection)
                if (getContext() != null) {
                    //   Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
                if (isAdded()) {


                    rootView.findViewById(R.id.celebritiesProgressBar).setVisibility(View.GONE);
                    rootView.findViewById(R.id.stlProgressBar).setVisibility(View.GONE);
                }

            }
        });


        //       Get All brands
        Call<CategoryFilterData> brandCall = APIClient.getInstance()
                .getFilteredCategories
                        (
                                ConstantValues.AUTHORIZATION, "parent_id", "29", "20", "1"
                        );

//        }
        if (isAdded()) {
            if (((App) getContext().getApplicationContext()).getBrandsList().isEmpty()) {
                rootView.findViewById(R.id.brandsProgressBar).setVisibility(View.VISIBLE);
                featuredBrandsProgress.setVisibility(View.VISIBLE);
            }
        }
        brandCall.enqueue(new Callback<CategoryFilterData>() {
            @Override
            public void onResponse(Call<CategoryFilterData> call, Response<CategoryFilterData> response) {
                if (response.isSuccessful()) {
                    if (isAdded()) {
                        if (!response.body().getItems().isEmpty()) {
                            ((App) getContext().getApplicationContext()).setBrandsList(response.body().getItems());

                            rootView.findViewById(R.id.brandsProgressBar).setVisibility(View.GONE);
                            featuredBrandsProgress.setVisibility(View.GONE);

                            if (getActivity() != null) {


                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    ((MainActivity) getActivity()).listDataHeader.stream().filter(Objects::nonNull)
                                            .filter(p -> p.getTitle().equals(getString(R.string.actionBrands))).collect(Collectors.toList()).get(0).setCount(String.valueOf(((App) getContext().getApplicationContext()).getBrandsList().size()));
                                } else {

                                    for (Drawer_Items person : ((MainActivity) getActivity()).listDataHeader) {
                                        if (person.getTitle().equals(getString(R.string.actionBrands))) {
                                            person.setCount(String.valueOf(((App) getContext().getApplicationContext()).getBrandsList().size()));
                                        }
                                    }

                                }
                                ((MainActivity) getActivity()).drawerExpandableAdapter.notifyDataSetChanged();


                            }
                            /*featuredBrandsList.clear();
                            featuredBrandsList.addAll(allBrandsList);
                            Collections.reverse(featuredBrandsList);*/
                           /* exclusiveBrandsAdapter.notifyDataSetChanged();
                            featuredBrandsAdapter.notifyDataSetChanged();*/

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<CategoryFilterData> call, Throwable t) {
                // something went completely south (like no internet connection)
                if (getContext() != null) {
                    //    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }

                if (isAdded()) {
                    rootView.findViewById(R.id.brandsProgressBar).setVisibility(View.GONE);
                    featuredBrandsProgress.setVisibility(View.GONE);
                }
            }
        });


        Call<CategoryFilterData> brandCallFeatured = APIClient.getInstance()
                .getBrands
                        (
                                ConstantValues.AUTHORIZATION, "parent_id", "29", "20", "1", "is_featured","1","eq"
                        );

//        }
        if (isAdded()) {
            if (((App) getContext().getApplicationContext()).getFeaturedBrand().isEmpty()) {
                rootView.findViewById(R.id.brandsProgressBar).setVisibility(View.VISIBLE);
                featuredBrandsProgress.setVisibility(View.VISIBLE);
            }
        }
        brandCallFeatured.enqueue(new Callback<CategoryFilterData>() {
            @Override
            public void onResponse(Call<CategoryFilterData> call, Response<CategoryFilterData> response) {
                if (response.isSuccessful()) {
                    if (isAdded()) {
                        if (!response.body().getItems().isEmpty()) {

                            rootView.findViewById(R.id.brandsProgressBar).setVisibility(View.GONE);
                            featuredBrandsProgress.setVisibility(View.GONE);
                            featuredBrandsList.clear();
                            featuredBrandsList = response.body().getItems();
                            ((App) getActivity().getApplicationContext()).setFeaturedBrand(featuredBrandsList);
                            featuredBrandsAdapter.notifyDataSetChanged();

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<CategoryFilterData> call, Throwable t) {
                // something went completely south (like no internet connection)
                if (getContext() != null) {
                    //    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }

                if (isAdded()) {
                    rootView.findViewById(R.id.brandsProgressBar).setVisibility(View.GONE);
                    featuredBrandsProgress.setVisibility(View.GONE);
                }
            }
        });


        Call<CategoryFilterData> brandCallexlusive = APIClient.getInstance()
                .getBrands
                        (
                                ConstantValues.AUTHORIZATION, "parent_id", "29", "20", "1", "is_exclusive","1","eq"
                        );

//        }
        if (isAdded()) {
            if (((App) getContext().getApplicationContext()).getExclusiveBrand().isEmpty()) {
                rootView.findViewById(R.id.brandsProgressBar).setVisibility(View.VISIBLE);
                featuredBrandsProgress.setVisibility(View.VISIBLE);
            }
        }
        brandCallexlusive.enqueue(new Callback<CategoryFilterData>() {
            @Override
            public void onResponse(Call<CategoryFilterData> call, Response<CategoryFilterData> response) {
                if (response.isSuccessful()) {
                    if (isAdded()) {
                        if (!response.body().getItems().isEmpty()) {

                            rootView.findViewById(R.id.brandsProgressBar).setVisibility(View.GONE);
                            featuredBrandsProgress.setVisibility(View.GONE);

                            al_ExclusiveBrandsList = response.body().getItems();
                            ((App) getActivity().getApplicationContext()).setExclusiveBrand(al_ExclusiveBrandsList);

                            exclusiveBrandsAdapter.notifyDataSetChanged();

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<CategoryFilterData> call, Throwable t) {
                // something went completely south (like no internet connection)
                if (getContext() != null) {
                    //    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }

                if (isAdded()) {
                    rootView.findViewById(R.id.brandsProgressBar).setVisibility(View.GONE);
                    featuredBrandsProgress.setVisibility(View.GONE);
                }
            }
        });


    }


    private void addCategoryProducts(ProductData productData) {
        categoryProductsList.addAll(productData.getItems());
        // Add Products to CategoryProductsList from the List of ProductData
        if (isAdded() && categoryProductsList != null) {
            if (categoryProductsList.size() != 0) {
                categoryProductsList.addAll(productData.getProductData());
            }


            productAdapter.notifyDataSetChanged();
        }

        // Change the Visibility of emptyRecord Text based on CategoryProductsList's Size
//        if (productAdapter.getItemCount() == 0) {
//            if (isFilterApplied) {
//                resetFiltersBtn.setVisibility(View.VISIBLE);
//            }
//            emptyRecord.setVisibility(View.VISIBLE);
//
//        } else {
//            emptyRecord.setVisibility(View.GONE);
//            resetFiltersBtn.setVisibility(View.GONE);
//        }
    }
    //*********** Request Products of given Category from the Server based on PageNo. ********//

    public void RequestCategoryProducts(int pageNumber, String sortBy) {


//        GetAllProducts getAllProducts = new GetAllProducts();
//        getAllProducts.setPageNumber(pageNumber);
//        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
//        getAllProducts.setCustomersId(customerID);
//        getAllProducts.setCategoriesId(String.valueOf(categoryID));
//        getAllProducts.setType(sortBy);


        Call<ProductData> call = APIClient.getInstance()
                .getAllProducts(ConstantValues.AUTHORIZATION, "category_id", "173", "eq", "created_at", "DESC", "10", "1", "",customerMainId);
//        getAllProducts(token,"category_id","47","eq","created_at","DESC","10","1");

        if (isAdded()) {
            rootView.findViewById(R.id.productsProgressBar).setVisibility(View.VISIBLE);

        }
        call.enqueue(new Callback<ProductData>() {
//            @Override
//            public void onResponse(Response<ProductData> response, Retrofit retrofit) {
//
//
//
//
//
//
//                if (response.isSuccessful()) {
//                    Snackbar.make(rootView, "HELLLLOOOO", Snackbar.LENGTH_SHORT).show();
//
//                    if (!response.body().getProductData().isEmpty()) {
//
//                        // Products have been returned. Add Products to the ProductsList
//                        addCategoryProducts(response.body());
//
//
//                    }
//                    else if (!response.body().getProductData().isEmpty()) {
//                        // Products haven't been returned. Call the method to process some implementations
//                        addCategoryProducts(response.body());
//
//                        // Show the Message to the User
////                        if (isVisible)
//                            Snackbar.make(rootView, "fsdfs", Snackbar.LENGTH_SHORT).show();
//
//                    }
//                    else {
//                        // Unable to get Success status
////                        if (isVisible)
//
//                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
//                    }
//
//                    // Hide the ProgressBar
////                    progressBar.setVisibility(View.GONE);
//
//                }
//                else {
//
//
//
////                    if (isVisible)
//                        Toast.makeText(getContext(), ""+response.body().toString(), Toast.LENGTH_SHORT).show();
//                }
//
//            }

            @Override
            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
//
                try{
                    if (response.isSuccessful()){
                        addCategoryProducts(response.body());
                        if (isAdded()) {
                            rootView.findViewById(R.id.productsProgressBar).setVisibility(View.GONE);


                        }
                    }
                }catch (Exception e){

                }


            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
//                if (isVisible)
                if (isAdded()) {
                    rootView.findViewById(R.id.productsProgressBar).setVisibility(View.GONE);


                }

                if (getContext() != null) {
                    //    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //*********** Handle the Click Listener on BannerImages of Slider ********//

    //    @Override
    public void onSliderClick(BaseSliderView slider) {


    }

    @Override
    public void onRefresh() {
        if (getActivity() != null) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

//                    swipeLayout.setRefreshing(false);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(homeFragment).attach(homeFragment).commit();

                }
            }, 1300);

        }
    }
}


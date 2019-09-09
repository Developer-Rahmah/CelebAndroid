package com.celebritiescart.celebritiescart.activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.adapters.DrawerExpandableListAdapter;
import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.app.MyAppPrefsManager;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.CircularImageView;
import com.celebritiescart.celebritiescart.customs.CustomTypefaceSpan;
import com.celebritiescart.celebritiescart.customs.LocaleHelper;
import com.celebritiescart.celebritiescart.customs.NotificationBadger;
import com.celebritiescart.celebritiescart.databases.User_Info_DB;
import com.celebritiescart.celebritiescart.fragments.About;
import com.celebritiescart.celebritiescart.fragments.All_TV;
import com.celebritiescart.celebritiescart.fragments.Categories_1;
import com.celebritiescart.celebritiescart.fragments.Categories_2;
import com.celebritiescart.celebritiescart.fragments.Categories_3;
import com.celebritiescart.celebritiescart.fragments.Categories_4;
import com.celebritiescart.celebritiescart.fragments.Categories_5;
import com.celebritiescart.celebritiescart.fragments.Categories_6;
import com.celebritiescart.celebritiescart.fragments.CelebrititesFragment;
import com.celebritiescart.celebritiescart.fragments.ContactUs;
import com.celebritiescart.celebritiescart.fragments.FragmentBrandsProduct;
import com.celebritiescart.celebritiescart.fragments.HomePage_1;
import com.celebritiescart.celebritiescart.fragments.HomePage_2;
import com.celebritiescart.celebritiescart.fragments.HomePage_3;
import com.celebritiescart.celebritiescart.fragments.HomePage_4;
import com.celebritiescart.celebritiescart.fragments.HomePage_5;
import com.celebritiescart.celebritiescart.fragments.HyperPayFragment;
import com.celebritiescart.celebritiescart.fragments.Languages;
import com.celebritiescart.celebritiescart.fragments.Layout_Brands_All;
import com.celebritiescart.celebritiescart.fragments.Layout_Brands_All_Exculisive;
import com.celebritiescart.celebritiescart.fragments.Layout_Celebrities_All;
import com.celebritiescart.celebritiescart.fragments.My_Addresses;
import com.celebritiescart.celebritiescart.fragments.My_Cart;
import com.celebritiescart.celebritiescart.fragments.My_Orders;
import com.celebritiescart.celebritiescart.fragments.News;
import com.celebritiescart.celebritiescart.fragments.Products;
import com.celebritiescart.celebritiescart.fragments.SearchFragment;
import com.celebritiescart.celebritiescart.fragments.SettingsFragment;
import com.celebritiescart.celebritiescart.fragments.Thank_You;
import com.celebritiescart.celebritiescart.fragments.Update_Account;
import com.celebritiescart.celebritiescart.fragments.WishList;
import com.celebritiescart.celebritiescart.helpers.Typefaces;
import com.celebritiescart.celebritiescart.models.CMSRequest;
import com.celebritiescart.celebritiescart.models.NotifyMeRequest;
import com.celebritiescart.celebritiescart.models.address_model.AddressDetails;
import com.celebritiescart.celebritiescart.models.device_model.AppSettingsDetails;
import com.celebritiescart.celebritiescart.models.drawer_model.Drawer_Items;
import com.celebritiescart.celebritiescart.models.notify_me.NotifyMeModel;
import com.celebritiescart.celebritiescart.models.payment_model.SelectPaymentWay;
import com.celebritiescart.celebritiescart.models.user_model.UserDetails;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.receivers.AlarmReceiver;
import com.celebritiescart.celebritiescart.utils.CheckoutIdRequestAsyncTask;
import com.celebritiescart.celebritiescart.utils.CheckoutIdRequestListener;
import com.celebritiescart.celebritiescart.utils.NotificationHelper;
import com.celebritiescart.celebritiescart.utils.NotificationScheduler;
import com.celebritiescart.celebritiescart.utils.PaymentStatusRequestAsyncTask;
import com.celebritiescart.celebritiescart.utils.PaymentStatusRequestListener;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.utils.Utilities;
import com.celebritiescart.celebritiescart.utils.ValidateInputs;
import com.google.android.gms.ads.MobileAds;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.gson.Gson;
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSkipCVVMode;
import com.oppwa.mobile.connect.exception.PaymentError;
import com.oppwa.mobile.connect.provider.Connect;
import com.oppwa.mobile.connect.provider.Transaction;
import com.oppwa.mobile.connect.provider.TransactionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.celebritiescart.celebritiescart.constant.ConstantValues.AUTHORIZATION;
import static com.celebritiescart.celebritiescart.constant.ConstantValues.PROFILE_IMAGE_URL;
import static com.celebritiescart.celebritiescart.fragments.HyperPayFragment.getInstance;


/**
 * MainActivity of the App
 **/

public class MainActivity extends AppCompatActivity implements CheckoutIdRequestListener, PaymentStatusRequestListener {

    int homeStyle, categoryStyle;
    String mobilenum = "";
    Toolbar toolbar;
    ActionBar actionBar;
    public static YouTubePlayer youTubePlayer;
    public static boolean isYouTubePlayerFullScreen;
    ImageView drawer_header;
    DrawerLayout drawerLayout;
    NavigationView navigationDrawer;

    SharedPreferences sharedPreferences;
    MyAppPrefsManager myAppPrefsManager;
    LinearLayout ll_main;

    ExpandableListView main_drawer_list;
    BottomNavigationView bottom_nav;
    public DrawerExpandableListAdapter drawerExpandableAdapter;

    boolean doublePressedBackToExit = false;

    private static String mSelectedItem;
    private static final String SELECTED_ITEM_ID = "selected";
    public static ActionBarDrawerToggle actionBarDrawerToggle;

    public List<Drawer_Items> listDataHeader = new ArrayList<>();
    Map<Drawer_Items, List<Drawer_Items>> listDataChild = new HashMap<>();
    private int cartSize = -1;
    private View cart_badge;
    private BottomNavigationItemView CartitemView;
    private static final String STATE_RESOURCE_PATH = "STATE_RESOURCE_PATH";
    public ProgressDialog progressDialog;
    public String regexResultCode = "/^(000\\.000\\.|000\\.100\\.1|000\\.[36])/";
    public String resourcePath;

    //*********** Called when the Activity is becoming Visible to the User ********//

    @Override
    protected void onStart() {
        super.onStart();

        if (myAppPrefsManager.isFirstTimeLaunch()) {

            NotificationScheduler.setReminder(MainActivity.this, AlarmReceiver.class);
        }

        myAppPrefsManager.setFirstTimeLaunch(false);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        boolean handleReturn = super.dispatchTouchEvent(ev);

        View view = getCurrentFocus();

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        /*if (view instanceof EditText) {
            View innerView = getCurrentFocus();

            if (ev.getAction() == MotionEvent.ACTION_UP &&
                    !getLocationOnScreen((EditText) innerView).contains(x, y)) {

                InputMethodManager input = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(getWindow().getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }*/

        return handleReturn;
    }

    protected Rect getLocationOnScreen(EditText mEditText) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        mEditText.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mEditText.getWidth();
        mRect.bottom = location[1] + mEditText.getHeight();

        return mRect;
    }

    //*********** Called when the Activity is first Created ********//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState != null) {
            resourcePath = savedInstanceState.getString(STATE_RESOURCE_PATH);
        }
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, ConstantValues.ADMOBE_ID);


        // Make botton navigation icons text visible and remove shifting effect
//        BottomNavigationViewHelper.removeShiftMode(findViewById(R.id.myBottomNav));

        // Get MyAppPrefsManager
        myAppPrefsManager = new MyAppPrefsManager(MainActivity.this);
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);


        // Binding Layout Views
        toolbar = findViewById(R.id.myToolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationDrawer = findViewById(R.id.main_drawer);


        // Get ActionBar and Set the Title and HomeButton of Toolbar
        toolbar.findViewById(R.id.myLayout).setVisibility(View.VISIBLE);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        SpannableString s = new SpannableString(ConstantValues.APP_HEADER);
        s.setSpan(new TypefaceSpan(App.getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(s);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher_foreground);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setIcon(R.mipmap.ic_launcher_foreground);


        // Setup Expandable DrawerList
        setupExpandableDrawerList();

        // Setup Expandable Drawer Header
        setupExpandableDrawerHeader();


        // Set Menu Items Font Start


        // Set Menu Items Font Finished


        // Initialize ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Hide OptionsMenu
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Recreate the OptionsMenu
                invalidateOptionsMenu();
            }
        };


        // Add ActionBarDrawerToggle to DrawerLayout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // Synchronize the indicator with the state of the linked DrawerLayout
        actionBarDrawerToggle.syncState();


        // Get the default ToolbarNavigationClickListener
        final View.OnClickListener toggleNavigationClickListener = actionBarDrawerToggle.getToolbarNavigationClickListener();

        // Handle ToolbarNavigationClickListener with OnBackStackChangedListener
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                // Check BackStackEntryCount of FragmentManager
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                    // Set new ToolbarNavigationClickListener
                    actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Close the Drawer if Opened
                            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                                drawerLayout.closeDrawers();
                            } else {
                                // Pop previous Fragment from BackStack
                                getSupportFragmentManager().popBackStack();
                            }
                        }
                    });


                } else {
                    // Set DrawerToggle Indicator and default ToolbarNavigationClickListener
                    SpannableString s = new SpannableString(ConstantValues.APP_HEADER);
                    s.setSpan(new TypefaceSpan(App.getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    actionBar.setTitle(s);
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                    actionBarDrawerToggle.setToolbarNavigationClickListener(toggleNavigationClickListener);
                }
            }
        });


        // Select SELECTED_ITEM from SavedInstanceState
        mSelectedItem = savedInstanceState == null ? ConstantValues.DEFAULT_HOME_STYLE : savedInstanceState.getString(SELECTED_ITEM_ID);

        // Navigate to SelectedItem
        drawerSelectedItemNavigation(mSelectedItem);
        getFAQs();

    }


    //*********** Setup Header of Navigation Drawer ********//

    public void setupExpandableDrawerHeader() {

        // Binding Layout Views of DrawerHeader
        drawer_header = findViewById(R.id.drawer_header);
        CircularImageView drawer_profile_image = findViewById(R.id.drawer_profile_image);
        TextView drawer_profile_name = findViewById(R.id.drawer_profile_name);
        TextView drawer_profile_email = findViewById(R.id.drawer_profile_email);
        TextView drawer_profile_welcome = findViewById(R.id.drawer_profile_welcome);

        // Check if the User is Authenticated
        if (ConstantValues.IS_USER_LOGGED_IN) {
            // Check User's Info from SharedPreferences
            if (sharedPreferences.getString("userEmail", null) != null) {
                findViewById(R.id.drawer_header_layout).setVisibility(View.VISIBLE);

                drawer_profile_image.setVisibility(View.VISIBLE);

                // Get User's Info from Local Database User_Info_DB
                User_Info_DB userInfoDB = new User_Info_DB();
                UserDetails userInfo = userInfoDB.getUserData(sharedPreferences.getString("userID", null));

                // Set User's Name, Email and Photo
                drawer_profile_email.setText(userInfo.getCustomersEmailAddress());
                drawer_profile_name.setText(userInfo.getCustomersFirstname() + " " + userInfo.getCustomersLastname());
                Glide.with(this).asBitmap()
                        .load(PROFILE_IMAGE_URL + "/" + sharedPreferences.getString("userprofileimage", ""))
                        .apply(new RequestOptions().centerCrop()
                                .placeholder(R.drawable.appicon).error(R.drawable.appicon))
                        .into(drawer_profile_image);

            } else {
                // Set Default Name, Email and Photo
//                drawer_profile_image.setImageResource(R.drawable.profile);
                drawer_profile_image.setVisibility(View.GONE);
                findViewById(R.id.drawer_header_layout).setVisibility(View.GONE);

//                drawer_profile_name.setText(getString(R.string.login_or_signup));
//                drawer_profile_email.setText(getString(R.string.login_or_create_account));
            }

        } else {
            // Set Default Name, Email and Photo
            drawer_profile_welcome.setVisibility(View.GONE);
            drawer_profile_image.setVisibility(View.GONE);
            findViewById(R.id.drawer_header_layout).setVisibility(View.GONE);


//            drawer_profile_image.setImageResource(R.drawable.profile);
//            drawer_profile_name.setText(getString(R.string.login_or_signup));
//            drawer_profile_email.setText(getString(R.string.login_or_create_account));

        }


        // Handle DrawerHeader Click Listener
        drawer_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if the User is Authenticated
                if (ConstantValues.IS_USER_LOGGED_IN) {

                    // Navigate to Update_Account Fragment
                    Fragment fragment = new SettingsFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();

                    // Close NavigationDrawer
                    drawerLayout.closeDrawers();

                } else {
                    // Navigate to Login Activity
                    startActivity(new Intent(MainActivity.this, Login.class));
                    finish();
                    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                }
            }
        });
    }


    //*********** Setup Expandable List of Navigation Drawer ********//

    @SuppressLint("RestrictedApi")
    public void setupExpandableDrawerList() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        AppSettingsDetails appSettings = ((App) getApplicationContext()).getAppSettingsDetails();

        if (appSettings != null) {

            homeStyle = appSettings.getHomeStyle();
            categoryStyle = appSettings.getCategoryStyle();

            listDataHeader.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.actionHome)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.celebritiesNav)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.actionBrands)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.actionCategories)));

            //Note: Hide temporary
            //  listDataHeader.add(new Drawer_Items(R.drawable.ic_cart, getString(R.string.actionVideos)));

            listDataHeader.add(new Drawer_Items(R.drawable.ic_account, getString(R.string.actionAccount)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_chat_bubble, getString(R.string.actionContactUs)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_share, getString(R.string.actionLanguage)));
            //     listDataHeader.add(new Drawer_Items(R.drawable.ic_share, getString(R.string.actionFAQs)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.actionFAQs)));

//            if (appSettings.getEditProfilePage() == 1)
//                listDataHeader.add(new Drawer_Items(R.drawable.ic_account, getString(R.string.actionAccount)));
//            if (appSettings.getMyOrdersPage() == 1)
//                listDataHeader.add(new Drawer_Items(R.drawable.ic_order, getString(R.string.actionOrders)));
//            if (appSettings.getShippingAddressPage() == 1)
//                listDataHeader.add(new Drawer_Items(R.drawable.ic_location, getString(R.string.actionAddresses)));
//            if (appSettings.getWishListPage() == 1)
//                listDataHeader.add(new Drawer_Items(R.drawable.ic_favorite, getString(R.string.actionFavourites)));
//            if (appSettings.getIntroPage() == 1)
//                listDataHeader.add(new Drawer_Items(R.drawable.ic_intro, getString(R.string.actionIntro)));
//            if (appSettings.getNewsPage() == 1)
//                listDataHeader.add(new Drawer_Items(R.drawable.ic_newspaper, getString(R.string.actionNews)));
//            if (appSettings.getContactUsPage() == 1)
//                listDataHeader.add(new Drawer_Items(R.drawable.ic_chat_bubble, getString(R.string.actionContactUs)));
//            if (appSettings.getAboutUsPage() == 1)
//                listDataHeader.add(new Drawer_Items(R.drawable.ic_info, getString(R.string.actionAbout)));
//            if (appSettings.getShareApp() == 1)
//                listDataHeader.add(new Drawer_Items(R.drawable.ic_share, getString(R.string.actionShareApp)));
//            if (appSettings.getRateApp() == 1)
//                listDataHeader.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.actionRateApp)));
//            if (appSettings.getSettingPage() == 1)
//                listDataHeader.add(new Drawer_Items(R.drawable.ic_settings, getString(R.string.actionSettings)));

            // Add last Header Item in Drawer Header List
            if (ConstantValues.IS_USER_LOGGED_IN) {
                listDataHeader.add(new Drawer_Items(R.drawable.ic_logout, getString(R.string.actionLogout)));
            } else {
                listDataHeader.add(new Drawer_Items(R.drawable.ic_logout, getString(R.string.actionLogin)));
            }


            if (!ConstantValues.IS_CLIENT_ACTIVE) {
                List<Drawer_Items> home_styles = new ArrayList<>();
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle1)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle2)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle3)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle4)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle5)));

                List<Drawer_Items> category_styles = new ArrayList<>();
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle1)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle2)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle3)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle4)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle5)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle6)));

                List<Drawer_Items> shop_childs = new ArrayList<>();
                shop_childs.add(new Drawer_Items(R.drawable.ic_arrow_up, getString(R.string.Newest)));
                shop_childs.add(new Drawer_Items(R.drawable.ic_sale, getString(R.string.topSeller)));
                shop_childs.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.superDeals)));
                shop_childs.add(new Drawer_Items(R.drawable.ic_most_liked, getString(R.string.mostLiked)));


                // Add Child to selective Headers
                listDataChild.put(listDataHeader.get(0), home_styles);
                listDataChild.put(listDataHeader.get(1), category_styles);
                listDataChild.put(listDataHeader.get(2), shop_childs);
            }

        } else {
            homeStyle = 1;
            categoryStyle = 1;

            listDataHeader.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.actionHome)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.celebritiesNav)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.actionBrands)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.actionCategories)));

            //Note: Hide temporary
            //listDataHeader.add(new Drawer_Items(R.drawable.ic_cart, getString(R.string.actionVideos)));

            listDataHeader.add(new Drawer_Items(R.drawable.ic_account, getString(R.string.actionAccount)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_chat_bubble, getString(R.string.actionContactUs)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_share, getString(R.string.actionLanguage)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.actionFAQs)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.actionHome)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.actionCategories)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_cart, getString(R.string.actionShop)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_account, getString(R.string.actionAccount)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_order, getString(R.string.actionOrders)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_location, getString(R.string.actionAddresses)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_favorite, getString(R.string.actionFavourites)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_intro, getString(R.string.actionIntro)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_newspaper, getString(R.string.actionNews)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_info, getString(R.string.actionAbout)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_chat_bubble, getString(R.string.actionContactUs)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_share, getString(R.string.actionShareApp)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.actionRateApp)));
//            listDataHeader.add(new Drawer_Items(R.drawable.ic_settings, getString(R.string.actionSettings)));
            // Add last Header Item in Drawer Header List
            if (ConstantValues.IS_USER_LOGGED_IN) {
                listDataHeader.add(new Drawer_Items(R.drawable.ic_logout, getString(R.string.actionLogout)));
            } else {
                listDataHeader.add(new Drawer_Items(R.drawable.ic_logout, getString(R.string.actionLogin)));
            }


            if (!ConstantValues.IS_CLIENT_ACTIVE) {
                List<Drawer_Items> home_styles = new ArrayList<>();
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle1)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle2)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle3)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle4)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle5)));

                List<Drawer_Items> category_styles = new ArrayList<>();
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle1)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle2)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle3)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle4)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle5)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle6)));

                List<Drawer_Items> shop_childs = new ArrayList<>();
                shop_childs.add(new Drawer_Items(R.drawable.ic_arrow_up, getString(R.string.Newest)));
                shop_childs.add(new Drawer_Items(R.drawable.ic_sale, getString(R.string.topSeller)));
                shop_childs.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.superDeals)));
                shop_childs.add(new Drawer_Items(R.drawable.ic_most_liked, getString(R.string.mostLiked)));


                // Add Child to selective Headers
                listDataChild.put(listDataHeader.get(0), home_styles);
                listDataChild.put(listDataHeader.get(1), category_styles);
                listDataChild.put(listDataHeader.get(2), shop_childs);
            }

        }


        // Initialize DrawerExpandableListAdapter
        drawerExpandableAdapter = new DrawerExpandableListAdapter(this, listDataHeader, listDataChild);

        // Bind ExpandableListView and set DrawerExpandableListAdapter to the ExpandableListView
        main_drawer_list = findViewById(R.id.main_drawer_list);
        bottom_nav = findViewById(R.id.myBottomNav);
        ll_main = findViewById(R.id.ll_main);
        main_drawer_list.setAdapter(drawerExpandableAdapter);

        drawerExpandableAdapter.notifyDataSetChanged();

        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) bottom_nav.getChildAt(0);

        View view = mbottomNavigationMenuView.getChildAt(4);

        CartitemView = (BottomNavigationItemView) view;

        if (cart_badge == null) {
            cart_badge = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.notification_badge,
                            mbottomNavigationMenuView, false);
        }
        if (cart_badge.getParent() != null)
            ((ViewGroup) cart_badge.getParent()).removeView(cart_badge);
        CartitemView.addView(cart_badge);
        bottom_nav.setOnNavigationItemSelectedListener(item -> {

            if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.celebrities))) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                Layout_Celebrities_All fragment = new Layout_Celebrities_All();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
                drawerLayout.closeDrawers();

            } else if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.actionCategoriesB))) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                Categories_1 fragment = new Categories_1();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            } else if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.actionFavourites))) {
                if (ConstantValues.IS_USER_LOGGED_IN) {
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    WishList fragment = new WishList();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();

//                    drawerLayout.closeDrawers();

                } else {
                    // Navigate to Login Activity
                    startActivity(new Intent(MainActivity.this, Login.class));
                    finish();
                    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                }


            } else if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.actionHome))) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                HomePage_1 fragment = new HomePage_1();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            }
            else if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.actionCartTemp))) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                My_Cart fragment = new My_Cart();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            }
            return true;
        });

//
//        cartSize = My_Cart.getCartSize();
//
//        if (cartSize > 0) {
//
//
//
//
//            BottomNavigationMenuView mbottomNavigationMenuView =
//                    (BottomNavigationMenuView) bottom_nav.getChildAt(0);
//
//            View view = mbottomNavigationMenuView.getChildAt(4);
//            Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_icon);
//            animation2.setRepeatMode(Animation.REVERSE);
//            animation2.setRepeatCount(1);
//
//            view.startAnimation(animation2);
//            view.setAnimation(null);
//            // Set BadgeCount on Cart_Icon with the static method of NotificationBadger class
//            NotificationBadger.setBottomBadgeCount(this, bottom_nav, String.valueOf(cartSize));
//
//
//        } else {
//            MenuItem cartItem1 = bottom_nav.getMenu().findItem(R.id.bottomNavCart);
//            // Set the Icon for Empty Cart
//            cartItem1.setIcon(R.drawable.ic_cart_empty);
//        }
        // Handle Group Item Click Listener
        main_drawer_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (drawerExpandableAdapter.getChildrenCount(groupPosition) < 1) {
                    // Navigate to Selected Main Item
                    if (groupPosition == 0) {
                        drawerSelectedItemNavigation(ConstantValues.DEFAULT_HOME_STYLE);
                    }
// else if (groupPosition == 1) {
//                        drawerSelectedItemNavigation(ConstantValues.DEFAULT_CATEGORY_STYLE);
//                    }
                    else {
                        drawerSelectedItemNavigation(listDataHeader.get(groupPosition).getTitle());
                    }
                }
                return false;
            }
        });


        // Handle Child Item Click Listener
        main_drawer_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // Navigate to Selected Child Item
                drawerSelectedItemNavigation(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTitle());
                return false;
            }
        });


        // Handle Group Expand Listener
        main_drawer_list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });
        // Handle Group Collapse Listener
        main_drawer_list.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

    }


    //*********** Navigate to given Selected Item of NavigationDrawer ********//

    private void drawerSelectedItemNavigation(String selectedItem) {

        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (selectedItem.equalsIgnoreCase(getString(R.string.actionHome))) {
            mSelectedItem = selectedItem;

            // Navigate to any selected HomePage Fragment
            fragment = new HomePage_1();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle1))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage1 Fragment
            fragment = new HomePage_1();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle2))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage2 Fragment
            fragment = new HomePage_2();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle3))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage3 Fragment
            fragment = new HomePage_3();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle4))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage4 Fragment
            fragment = new HomePage_4();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle5))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            fragment = new HomePage_5();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionCategories))) {
            mSelectedItem = selectedItem;

            Bundle bundle = new Bundle();
            bundle.putBoolean("isHeaderVisible", false);

            // Navigate to any selected CategoryPage Fragment
            fragment = new Categories_1();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle1))) {
            mSelectedItem = selectedItem;

            Bundle bundle = new Bundle();
            bundle.putBoolean("isHeaderVisible", false);

            // Navigate to Categories_1 Fragment
            fragment = new Categories_1();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle2))) {
            mSelectedItem = selectedItem;

            Bundle bundle = new Bundle();
            bundle.putBoolean("isHeaderVisible", false);

            // Navigate to Categories_2 Fragment
            fragment = new Categories_2();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle3))) {
            mSelectedItem = selectedItem;

            Bundle bundle = new Bundle();
            bundle.putBoolean("isHeaderVisible", false);

            // Navigate to Categories_3 Fragment
            fragment = new Categories_3();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle4))) {
            mSelectedItem = selectedItem;

            Bundle bundle = new Bundle();
            bundle.putBoolean("isHeaderVisible", false);

            // Navigate to Categories_4 Fragment
            fragment = new Categories_4();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle5))) {
            mSelectedItem = selectedItem;

            Bundle bundle = new Bundle();
            bundle.putBoolean("isHeaderVisible", false);

            // Navigate to Categories_5 Fragment
            fragment = new Categories_5();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle6))) {
            mSelectedItem = selectedItem;

            Bundle bundle = new Bundle();
            bundle.putBoolean("isHeaderVisible", false);

            // Navigate to Categories_6 Fragment
            fragment = new Categories_6();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionShop))) {
            mSelectedItem = selectedItem;

            Bundle bundle = new Bundle();
            bundle.putString("sortBy", "Newest");
            bundle.putBoolean("isMenuItem", true);

            // Navigate to Products Fragment
            fragment = new Products();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.Newest))) {
            mSelectedItem = selectedItem;

            Bundle bundle = new Bundle();
            bundle.putString("sortBy", "Newest");
            bundle.putBoolean("isMenuItem", true);

            // Navigate to Products Fragment
            fragment = new Products();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.superDeals))) {
            mSelectedItem = selectedItem;

            Bundle bundle = new Bundle();
            bundle.putString("sortBy", "special");
            bundle.putBoolean("isMenuItem", true);

            // Navigate to Products Fragment
            fragment = new Products();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.topSeller))) {
            mSelectedItem = selectedItem;

            Bundle bundle = new Bundle();
            bundle.putString("sortBy", "top seller");
            bundle.putBoolean("isMenuItem", true);

            // Navigate to Products Fragment
            fragment = new Products();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.mostLiked))) {
            mSelectedItem = selectedItem;

            Bundle bundle = new Bundle();
            bundle.putString("sortBy", "most liked");
            bundle.putBoolean("isMenuItem", true);

            // Navigate to Products Fragment
            fragment = new Products();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionAccount))) {
            if (ConstantValues.IS_USER_LOGGED_IN) {
                mSelectedItem = selectedItem;

                // Navigate to Update_Account Fragment
                fragment = new Update_Account();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();

                drawerLayout.closeDrawers();

            } else {
                // Navigate to Login Activity
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            }

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionOrders))) {
            if (ConstantValues.IS_USER_LOGGED_IN) {
                mSelectedItem = selectedItem;

                // Navigate to My_Orders Fragment
                fragment = new My_Orders();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();

                drawerLayout.closeDrawers();

            } else {
                // Navigate to Login Activity
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            }

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionAddresses))) {
            if (ConstantValues.IS_USER_LOGGED_IN) {
                mSelectedItem = selectedItem;

                // Navigate to My_Addresses Fragment
                fragment = new My_Addresses();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();

                drawerLayout.closeDrawers();

            } else {
                // Navigate to Login Activity
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            }

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionFavourites))) {
            if (ConstantValues.IS_USER_LOGGED_IN) {
                mSelectedItem = selectedItem;

                // Navigate to WishList Fragment
                fragment = new WishList();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();

                drawerLayout.closeDrawers();

            } else {
                // Navigate to Login Activity
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            }

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionNews))) {
            mSelectedItem = selectedItem;

            // Navigate to News Fragment
            fragment = new News();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionVideos))) {
            mSelectedItem = selectedItem;

            // Navigate to News Fragment
            fragment = new All_TV();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionIntro))) {
            mSelectedItem = selectedItem;

            // Navigate to IntroScreen
            startActivity(new Intent(getBaseContext(), IntroScreen.class));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionAbout))) {
            mSelectedItem = selectedItem;

            // Navigate to About Fragment
            fragment = new About();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionShareApp))) {
            mSelectedItem = selectedItem;

            // Share App with the help of static method of Utilities class
            Utilities.shareMyApp(MainActivity.this);

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionRateApp))) {
            mSelectedItem = selectedItem;

            // Rate App with the help of static method of Utilities class
            Utilities.rateMyApp(MainActivity.this);

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionContactUs))) {
            mSelectedItem = selectedItem;

            // Navigate to ContactUs Fragment
            fragment = new ContactUs();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionSettings))) {
            mSelectedItem = selectedItem;

            // Navigate to SettingsFragment Fragment
            fragment = new SettingsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.celebrities))) {

            mSelectedItem = selectedItem;

            fragment = new Layout_Celebrities_All();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();
            drawerLayout.closeDrawers();


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionFAQs))) {

            mSelectedItem = selectedItem;

    /*        fragment = new FaqFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();*/

            Intent intent = new Intent(this, FaqActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);

            drawerLayout.closeDrawers();
//            Intent i = new Intent(this, VideoPlayerActivity.class);
//            this.startActivity(i);
//            this.finish();
//            this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionLanguage))) {
            mSelectedItem = selectedItem;

            // Navigate to Login Activity
            fragment = new Languages();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionBrands))) {
            mSelectedItem = selectedItem;

            // Navigate to Login Activity
            fragment = new Layout_Brands_All();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionLogin))) {
            mSelectedItem = selectedItem;

            // Navigate to Login Activity
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionLogout))) {
            mSelectedItem = selectedItem;

            // Edit UserID in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userID", "");
            editor.apply();

            // Set UserLoggedIn in MyAppPrefsManager
            MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(this);
            myAppPrefsManager.setUserLoggedIn(false);

            // Set isLogged_in of ConstantValues
            ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


            // Navigate to Login Activity
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
        }
    }


    //*********** Called by the System when the Device's Configuration changes while Activity is Running ********//

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Configure ActionBarDrawerToggle with new Configuration
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    //*********** Invoked to Save the Instance's State when the Activity may be Temporarily Destroyed ********//

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the Selected NavigationDrawer Item
        outState.putString(SELECTED_ITEM_ID, mSelectedItem);
        outState.putString(STATE_RESOURCE_PATH, resourcePath);

    }


    //*********** Set the Base Context for the ContextWrapper ********//

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typefaces.get(App.getContext(), "baskvill_regular");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        String languageCode = ConstantValues.LANGUAGE_CODE;
        if ("".equalsIgnoreCase(languageCode))
            languageCode = ConstantValues.LANGUAGE_CODE = "en";

        try {
            if (ConstantValues.LANGUAGE_CODE.equals("en")) {
                CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("font/baskvill_regular.OTF")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
                );
            } else {
                CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("font/geflow.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
                );
            }

        } catch (Exception ignored) {

        }

        super.attachBaseContext(LocaleHelper.wrapLocale(newBase, languageCode));
//        }
    }


    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CheckoutActivity.REQUEST_CODE_CHECKOUT) {
            switch (resultCode) {
                case CheckoutActivity.RESULT_OK:
                    /* Transaction completed. */
                    Transaction transaction = data.getParcelableExtra(
                            CheckoutActivity.CHECKOUT_RESULT_TRANSACTION);

                    resourcePath = data.getStringExtra(
                            CheckoutActivity.CHECKOUT_RESULT_RESOURCE_PATH);

                    if (transaction.getTransactionType() == TransactionType.SYNC) {
                        /* Check the status of synchronous transaction. */
                        requestPaymentStatus(resourcePath, getInstance().card_type);
                    } else {
                        /* Asynchronous transaction is processed in the onNewIntent(). */
                        hideProgressDialog();
                    }

                    break;
                case CheckoutActivity.RESULT_CANCELED:
                    hideProgressDialog();

                    break;
                case CheckoutActivity.RESULT_ERROR:
                    hideProgressDialog();

                    PaymentError error = data.getParcelableExtra(
                            CheckoutActivity.CHECKOUT_RESULT_ERROR);

                    showErrorAlertDialog(getString(R.string.error_message));
            }
        }
    }


    //*********** Creates the Activity's OptionsMenu ********//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate toolbar_menu Menu
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        // Bind Menu Items
        MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);


        languageItem.setVisible(false);


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView image = (ImageView) inflater.inflate(R.layout.layout_animated_ic_cart, null);

        Drawable itemIcon = cartItem.getIcon().getCurrent();
        image.setImageDrawable(itemIcon);

        cartItem.setActionView(image);


        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to My_Cart Fragment
                Fragment fragment = new My_Cart();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            }
        });


        // Tint Menu Icons with the help of static method of Utilities class
        Utilities.tintMenuIcon(MainActivity.this, languageItem, R.color.colorPrimary);
        Utilities.tintMenuIcon(MainActivity.this, searchItem, R.color.colorPrimary);
        Utilities.tintMenuIcon(MainActivity.this, cartItem, R.color.colorPrimary);

        return true;
    }


    //*********** Prepares the OptionsMenu of Toolbar ********//

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        cartSize = My_Cart.getCartSize();

        if (cartSize > 0) {

            MenuItem cartItem1 = bottom_nav.getMenu().findItem(R.id.bottomNavCart);
            // Set the Icon for Empty Cart
            cartItem1.setIcon(R.drawable.ic_cart_full);

            Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_icon);
            animation2.setRepeatMode(Animation.REVERSE);
            animation2.setRepeatCount(1);

            cart_badge.startAnimation(animation2);
            cart_badge.setAnimation(null);
            cart_badge.setVisibility(View.VISIBLE);
            // Set BadgeCount on Cart_Icon with the static method of NotificationBadger class
            NotificationBadger.setBottomBadgeCount(cart_badge, cartSize);


        } else {
            cart_badge.setVisibility(View.GONE);
            MenuItem cartItem1 = bottom_nav.getMenu().findItem(R.id.bottomNavCart);
            // Set the Icon for Empty Cart
            cartItem1.setIcon(R.drawable.ic_cart_empty);

        }
        // Clear OptionsMenu if NavigationDrawer is Opened
        if (drawerLayout.isDrawerOpen(navigationDrawer)) {

            MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
            MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
            MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);

            languageItem.setVisible(true);
            searchItem.setVisible(false);
            cartItem.setVisible(false);

        } else {

            MenuItem cartItem2 = menu.findItem(R.id.toolbar_ic_cart);

            // Get No. of Cart Items with the static method of My_Cart Fragment
            if (cartSize == -1) {
                cartSize = My_Cart.getCartSize();
            }

            // if Cart has some Items
            if (cartSize > 0) {

                // Animation for cart_menuItem

                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_icon);
                animation2.setRepeatMode(Animation.REVERSE);
                animation2.setRepeatCount(1);

//                cartItem1.getActionView().startAnimation(animation1);
                cartItem2.getActionView().startAnimation(animation2);
//                cartItem1.getActionView().setAnimation(null);
                cartItem2.getActionView().setAnimation(null);


                LayerDrawable icon2 = null;
                Drawable drawable2 = cartItem2.getIcon();

                if (drawable2 instanceof DrawableWrapper) {
                    drawable2 = ((DrawableWrapper) drawable2).getWrappedDrawable();
                }

                icon2 = new LayerDrawable(new Drawable[]{drawable2});


                // Set BadgeCount on Cart_Icon with the static method of NotificationBadger class
                NotificationBadger.setBadgeCount(this, icon2, String.valueOf(cartSize));

            } else {
                // Set the Icon for Empty Cart
                cartItem2.setIcon(R.drawable.ic_cart_empty);
            }

        }

        return super.onPrepareOptionsMenu(menu);
    }


    //*********** Called whenever an Item in OptionsMenu is Selected ********//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();


        switch (item.getItemId()) {

            case R.id.toolbar_ic_language:

                drawerLayout.closeDrawers();

                // Navigate to Languages Fragment
                fragment = new Languages();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
                break;

            case R.id.toolbar_ic_search:

                // Navigate to SearchFragment Fragment
                fragment = new SearchFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
                break;

            case R.id.toolbar_ic_cart:

                // Navigate to My_Cart Fragment
                fragment = new My_Cart();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
                break;

            default:
                break;
        }

        return true;
    }


    public void getFAQs() {


        Call<CMSRequest> call = APIClient.getInstance()
                .getfaqs
                        (
                                ConstantValues.AUTHORIZATION
                        );
        call.enqueue(new Callback<CMSRequest>() {
            @Override
            public void onResponse(Call<CMSRequest> call, retrofit2.Response<CMSRequest> response) {




                if (response.isSuccessful()) {
                    ConstantValues.FAQS = response.body().getContent();
                } else {

                }
            }

            @Override
            public void onFailure(Call<CMSRequest> call, Throwable t) {
            }
        });
    }


    //*********** Called when the Activity has detected the User pressed the Back key ********//

    @Override
    public void onBackPressed() {

        if (youTubePlayer != null && isYouTubePlayerFullScreen) {
            youTubePlayer.setFullscreen(false);
        } else {

            // Get FragmentManager
            FragmentManager fm = getSupportFragmentManager();


            // Check if NavigationDrawer is Opened
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();

            }
            // Check if BackStack has some Fragments
            else if (fm.getBackStackEntryCount() > 0) {

                // Pop previous Fragment
                fm.popBackStack();

            }
            // Check if doubleBackToExitPressed is true
            else if (doublePressedBackToExit) {
                super.onBackPressed();

            } else {
                this.doublePressedBackToExit = true;
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

                // Delay of 2 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // Set doublePressedBackToExit false after 2 seconds
                        doublePressedBackToExit = false;
                    }
                }, 2000);
            }
        }
    }

    public void seeAllCelebs(View view) {
        Layout_Celebrities_All fragment = new Layout_Celebrities_All();
        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getString(R.string.actionHome)).commit();
    }

    public void paymentScreen(String amount, String type, String card_type) {
        Bundle bundle = new Bundle();
        bundle.putString("amount", amount);
        bundle.putString("type", type);
        bundle.putString("card_type", card_type);
        Fragment fragment = new HyperPayFragment();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getString(R.string.actionHome)).commit(); //Note: Check this
    }

    public void seeAllBrands(View view) {


        Layout_Brands_All fragment = new Layout_Brands_All();
        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getString(R.string.actionHome)).commit();
    }

    /*public void seeAllFeaturedBrands(View view) {
        Layout_Brands_All fragment = new Layout_Brands_All();
        FragmentManager fm = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("brandType", getString(R.string.featured_brands1));
        fragment.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getString(R.string.actionHome)).commit();
    }


    public void seeAllExclusiveBrands(View view) {
        Layout_Brands_All fragment = new Layout_Brands_All();
        FragmentManager fm = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("brandType", getString(R.string.exclusive_brands1));
        fragment.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getString(R.string.actionHome)).commit();
    }*/

    public void seeAllProducts(View view) {
//        Bundle bundle = new Bundle();
//        bundle.putBoolean("isSubFragment", false);

        // Navigate to Products Fragment
//        Fragment fragment = new Products();
//        fragment.setArguments(bundle);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.main_fragment, fragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .addToBackStack(getString(R.string.actionHome)).commit();



        Bundle bundle = new Bundle();
        bundle.putInt("CategoryID", 173);
//        categoryInfo.putString("CategoryName", categoriesList.get(getAdapterPosition()).getName());
//        categoryInfo.putString("CategoryNameShow", categoriesList.get(getAdapterPosition()).getName());
//        categoryInfo.putString("CategoryImage", categoriesList.get(getAdapterPosition()).getImage());

//         Navigate to Products Fragment
        Fragment fragment = new FragmentBrandsProduct();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getString(R.string.actionHome)).commit();

//
//        Fragment fragment = new FragmentBrandsProduct();
//        fragment.setArguments(categoryInfo);
//        FragmentManager fragmentManager = ((MainActivity) getApplicationContext()).getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.main_fragment, fragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .addToBackStack(getApplicationContext().getString(R.string.actionHome)).commit();

    }

    public void seeAllNewProducts(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSubFragment", false);
        bundle.putString("isTrending", "yes");
        // Navigate to Products Fragment
        Fragment fragment = new Products();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getString(R.string.actionHome)).commit();
    }


    public void seeAllVideos(View view) {

        FragmentManager fragmentManager = getSupportFragmentManager();


        Fragment fragment = new All_TV();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getString(R.string.actionHome)).commit();

    }

    public void seeAllTProducts(View view) {


        Bundle categoryInfo = new Bundle();
        ArrayList<Integer> selectedCategoriesIDs = new ArrayList<>();
        selectedCategoriesIDs.add(18);
        categoryInfo.putIntegerArrayList("CategoryIDs", selectedCategoriesIDs);
        categoryInfo.putString("CategoryName", getString(R.string.trending_now));
        categoryInfo.putString("isTrending", "yes");

        categoryInfo.putString("CategoryNameShow", getString(R.string.trending_now));

        Fragment fragment;
        fragment = new Products();


        fragment.setArguments(categoryInfo);
        FragmentManager fragmentManager = ((MainActivity) this).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getString(R.string.actionHome)).commit();


    }

    public void seeAllSProducts(View view) {

        Bundle categoryInfo = new Bundle();
        ArrayList<Integer> selectedCategoriesIDs = new ArrayList<>();
        selectedCategoriesIDs.add(19);
        categoryInfo.putIntegerArrayList("CategoryIDs", selectedCategoriesIDs);
        categoryInfo.putString("CategoryName", getString(R.string.special_collection));


        categoryInfo.putString("CategoryNameShow", getString(R.string.special_collection));

        Fragment fragment;
        fragment = new Products();


        fragment.setArguments(categoryInfo);
        FragmentManager fragmentManager = ((MainActivity) this).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getString(R.string.actionHome)).commit();


    }

    public void seeAllCategories(View view) {
//        mSelectedItem = selectedItem;

        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", false);
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Navigate to any selected CategoryPage Fragment
        Categories_1 fragment = new Categories_1();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getString(R.string.actionHome)).commit();
    }


    public void goToCelebrities(MenuItem item) {
        BottomNavigationView bottomNavigationView = findViewById(R.id.myBottomNav);

        bottomNavigationView.setSelectedItemId(item.getItemId());
        FragmentManager fragmentManager = getSupportFragmentManager();

        CelebrititesFragment fragment = new CelebrititesFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public static void changeToolbarFont(Toolbar toolbar, Activity context) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (tv.getText().equals(toolbar.getTitle())) {
                    applyFont(tv, context);
                    break;
                }
            }
        }
    }

    public static void applyFont(TextView tv, Activity context) {
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/baskvill_regular.OTF"));
    }


    //Payment

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);

        if (resourcePath != null && hasCallbackScheme(intent)) {
            requestPaymentStatus(resourcePath, getInstance().card_type);
        }
    }

    public boolean hasCallbackScheme(Intent intent) {
        String scheme = intent.getScheme();

        return getString(R.string.custom_ui_callback_scheme).equals(scheme);
    }


    public void requestCheckoutId(String callbackScheme, String amount, String amountType, String cardtype, String merchantTransactionId) {
        showProgressDialog(R.string.progress_message_checkout_id);

        new CheckoutIdRequestAsyncTask(this)
                .execute(amount, amountType, cardtype, merchantTransactionId);
    }

    @Override
    public void onCheckoutIdReceived(String checkoutId) {
        hideProgressDialog();

        if (checkoutId == null) {
            showAlertDialog(R.string.error_message);
        }

        if (checkoutId != null) {

            getInstance().requestCheckoutInfo(checkoutId);
        }

    }

    @Override
    public void onErrorOccurred() {
        hideProgressDialog();
        showAlertDialog(R.string.error_message);
    }

    @Override
    public void onPaymentStatusReceived(String paymentStatus) {
        hideProgressDialog();
        String[] paymentStatusArray = paymentStatus.split(",");

        //000.100.112 Test
        //000.000.000 Live

        if ("000.000.000".equalsIgnoreCase(paymentStatusArray[0]) || "000.000.100".equalsIgnoreCase(paymentStatusArray[0]) || "000.100.110".equalsIgnoreCase(paymentStatusArray[0]) || "000.100.111".equalsIgnoreCase(paymentStatusArray[0])) {
            //   showAlertDialog(R.string.message_successful_payment);

            //    PlaceOrderNow(new SelectPaymentWay(new PaymentMethod(ConstantValues.cardType));

            NotificationHelper.showNewNotification(App.getContext(), null, getString(R.string.thank_you), paymentStatusArray[2]);

            // Clear User's Cart
            My_Cart.ClearCart();

            // Clear User's Shipping and Billing info from AppContext
            ((App) App.getContext().getApplicationContext()).setShippingAddress(new AddressDetails());
            ((App) App.getContext().getApplicationContext()).setBillingAddress(new AddressDetails());
            Bundle bundle = new Bundle();
            bundle.putString("orderNo", paymentStatusArray[2]);

            // Navigate to Thank_You Fragment
            Fragment fragment = new Thank_You();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack(getString(R.string.actionCart), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .commit();


            return;
        }
        if (paymentStatusArray[3].equalsIgnoreCase("")) {
            showErrorAlertDialog(getString(R.string.message_unsuccessful_payment));
        } else {
            showErrorAlertDialog(paymentStatusArray[3]);
        }

    }

    public void requestPaymentStatus(String resourcePath, String paymentCode) {
        //    showProgressDialog(R.string.progress_message_payment_status);
        new PaymentStatusRequestAsyncTask(this, paymentCode).execute(resourcePath);
    }


    public CheckoutSettings createCheckoutSettings(String checkoutId, String callbackScheme, String paymentType) {
        return new CheckoutSettings(checkoutId, Utilities.PAYMENT_BRANDS,
                Connect.ProviderMode.TEST)
                .setSkipCVVMode(CheckoutSkipCVVMode.FOR_STORED_CARDS)
                .setWindowSecurityEnabled(false)
                .setShopperResultUrl(callbackScheme + "://callback");
    }

    public void showProgressDialog(int messageId) {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
        }

        progressDialog.setMessage(getString(messageId));
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog == null) {
            return;
        }

        progressDialog.dismiss();
    }

    public void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, null)
                .setCancelable(false)
                .show();
    }

    public void showAlertDialog(int messageId) {
        showAlertDialog(getString(messageId));
    }

    protected void showErrorAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (Fragment fragmentdelete : getSupportFragmentManager().getFragments()) {
                            if (fragmentdelete != null) {
                                getSupportFragmentManager().beginTransaction().remove(fragmentdelete).commit();
                            }
                        }

                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isSubFragment", false);

                        // Navigate to Products Fragment
                        Fragment fragment = new HomePage_1();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_fragment, fragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();


                    }
                })
                .setCancelable(false)
                .show();
    }


    public void PlaceOrderNow(SelectPaymentWay postOrder) {

        String customerID = "Bearer " + getSharedPreferences("UserInfo", App.getContext().MODE_PRIVATE).getString("userID", "");
        Call<String> call;
        if (ConstantValues.IS_USER_LOGGED_IN) {
            call = APIClient.getInstance()
                    .addToCustomerOrder
                            (
                                    customerID, postOrder
                            );
        } else {
            call = APIClient.getInstance()
                    .addToOrder
                            (
                                    AUTHORIZATION, postOrder, ((App) App.getContext().getApplicationContext()).getGuestCartID()
                            );
        }

//        progressDialog.setVisibility(View.VISIBLE);
//        progressDialog.showProgressDialog();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {

//                progressDialog.setVisibility(View.GONE);
                hideProgressDialog();


                // Check if the Response is successful
                if (response.isSuccessful()) {
//                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                    // Order has been placed Successfully
                    if (postOrder.getPaymentMethod().getMethod().equalsIgnoreCase("cashondelivery")) {
                        NotificationHelper.showNewNotification(App.getContext(), null, getString(R.string.thank_you), response.body());


                        // Clear User's Cart
                        My_Cart.ClearCart();

                        // Clear User's Shipping and Billing info from AppContext
                        ((App) App.getContext().getApplicationContext()).setShippingAddress(new AddressDetails());
                        ((App) App.getContext().getApplicationContext()).setBillingAddress(new AddressDetails());
                        Bundle bundle = new Bundle();
                        bundle.putString("orderNo", response.body());

                        // Navigate to Thank_You Fragment
                        Fragment fragment = new Thank_You();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.popBackStack(getString(R.string.actionCart), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_fragment, fragment)
                                .commit();


//                    }
//                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
//                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
//
//                    }
//                    else {
//                        // Unable to get Success status
//                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
//                    }
                    } else {
                        My_Cart.ClearCart();

                        // Clear User's Shipping and Billing info from AppContext
                        ((App) App.getContext().getApplicationContext()).setShippingAddress(new AddressDetails());
                        ((App) App.getContext().getApplicationContext()).setBillingAddress(new AddressDetails());

                        requestCheckoutId(getString(R.string.custom_ui_callback_scheme), String.valueOf(String.format("%.2f", Double.parseDouble(getInstance().amount))), getInstance().type, getInstance().card_type, response.body());

                    }
                } else {
                    if (App.getContext() != null) {
                        //      Toast.makeText(App.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
//                    try {
//                        Log.i("error", response.errorBody().string());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                progressDialog.setVisibility(View.GONE);
                hideProgressDialog();

                if (App.getContext() != null) {
                    //    Toast.makeText(App.getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    public void notifyMeDialog(Activity activity, String product_id) {


        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_notify_me, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        final AlertDialog alertDialog = dialog.create();

        Button btn_cancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        EditText et_mobile_no = dialogView.findViewById(R.id.et_mobile_no);
        CheckBox cb_remember_me = dialogView.findViewById(R.id.cb_remember_me);

        Button btn_send = (Button) dialogView.findViewById(R.id.btn_send);
        if (sharedPreferences.getString("mobile_no", "").length() > 4) {
            cb_remember_me.setChecked(true);
            et_mobile_no.setText(sharedPreferences.getString("mobile_no", ""));
        } else {
            cb_remember_me.setChecked(false);

        }


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ValidateInputs.isValidPhoneNo(et_mobile_no.getText().toString().trim())) {
                    et_mobile_no.setError(getString(R.string.invalid_contact));

                } else {
                    if (cb_remember_me.isChecked() == true) {
                        sharedPreferences.edit().putString("mobile_no", et_mobile_no.getText().toString().trim()).commit();
                    }

                    alertDialog.dismiss();

                    showProgressDialog(R.string.please_wait);

                    notifyUser(et_mobile_no.getText().toString(), product_id);
                }


            }
        });


        alertDialog.show();


    }


   /* public void notifyUser(String phone_number, String product_id) {
        Call<String> call;
        if (ConstantValues.IS_USER_LOGGED_IN) {
            String customerID = "Bearer " + getSharedPreferences("UserInfo", MODE_PRIVATE).getString("userID", "");

            call = APIClient.getInstance()
                    .notifyUser
                            (customerID,
                                    product_id,
                                    phone_number
                            );
        } else {
            call = APIClient.getInstance()
                    .notifyUser
                            (AUTHORIZATION,
                                    product_id,
                                    phone_number
                            );
        }
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                hideProgressDialog();

                if (response.isSuccessful()) {
                    Snackbar.make(ll_main, response.body(), Snackbar.LENGTH_SHORT).show();
                } else {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                hideProgressDialog();

            }
        });
    }*/


    public void notifyUser(String phone_number, String product_id) {

        NotifyMeRequest notifyMeRequest = new NotifyMeRequest();
        notifyMeRequest.phone_number = phone_number;
        notifyMeRequest.product_id = product_id;

        Call<NotifyMeModel> call;
        if (ConstantValues.IS_USER_LOGGED_IN) {
            String customerID = "Bearer " + getSharedPreferences("UserInfo", MODE_PRIVATE).getString("userID", "");

            call = APIClient.getInstance()
                    .notifyUser
                            ("application/json",
                                    customerID,
                                    notifyMeRequest
                            );
        } else {
            call = APIClient.getInstance()
                    .notifyUser
                            ("application/json",
                                    AUTHORIZATION,
                                    notifyMeRequest
                            );
        }

        Gson gson = new Gson();
        String json = gson.toJson(notifyMeRequest);

        call.enqueue(new Callback<NotifyMeModel>() {
            @Override
            public void onResponse(Call<NotifyMeModel> call, Response<NotifyMeModel> response) {

                hideProgressDialog();

                if (response.isSuccessful()) {
                    Snackbar.make(ll_main, response.body().message, Snackbar.LENGTH_SHORT).show();
                } else {

                }
            }

            @Override
            public void onFailure(Call<NotifyMeModel> call, Throwable t) {
                hideProgressDialog();

            }
        });
    }

    public void clearStack() {
        //Here we are clearing back stack fragment entries
        int backStackEntry = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntry > 0) {
            for (int i = 0; i < backStackEntry; i++) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }

        //Here we are removing all the fragment that are shown here
        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
            for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
                Fragment mFragment = getSupportFragmentManager().getFragments().get(i);
                if (mFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
                }
            }
        }
    }

    public void seeAllBrandsExculisive(View view) {
        Layout_Brands_All_Exculisive fragment = new Layout_Brands_All_Exculisive();
        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getString(R.string.actionHome)).commit();

    }
}


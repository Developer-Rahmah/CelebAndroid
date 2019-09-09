package com.celebritiescart.celebritiescart.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.Login;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.adapters.ProductAdapter;
import com.celebritiescart.celebritiescart.adapters.ProductAttributesAdapter;
import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.DialogLoader;
import com.celebritiescart.celebritiescart.models.RatingDatum;
import com.celebritiescart.celebritiescart.models.ReviewRequest;
import com.celebritiescart.celebritiescart.models.ReviewResponse;
import com.celebritiescart.celebritiescart.models.cart_model.CartProduct;
import com.celebritiescart.celebritiescart.models.cart_model.CartProductAttributes;
import com.celebritiescart.celebritiescart.models.category_model.CategoryDetails;
import com.celebritiescart.celebritiescart.models.product_detail.Media_gallery_entries;
import com.celebritiescart.celebritiescart.models.product_detail.ProductDetailMainModel;
import com.celebritiescart.celebritiescart.models.product_model.Attribute;
import com.celebritiescart.celebritiescart.models.product_model.CustomAttribute;
import com.celebritiescart.celebritiescart.models.product_model.GetAllProducts;
import com.celebritiescart.celebritiescart.models.product_model.Image;
import com.celebritiescart.celebritiescart.models.product_model.Option;
import com.celebritiescart.celebritiescart.models.product_model.ProductData;
import com.celebritiescart.celebritiescart.models.product_model.ProductDetails;
import com.celebritiescart.celebritiescart.models.product_model.ProductRating;
import com.celebritiescart.celebritiescart.models.product_model.Value;
import com.celebritiescart.celebritiescart.models.product_model.WishListData;
import com.celebritiescart.celebritiescart.models.product_model.WishListResponse;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.utils.Utilities;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Product_Description extends Fragment implements RatingDialogListener {

    View rootView;
    int productID;
    static String customerID;
    double attributesPrice;
    double productBasePrice;
    double productFinalPrice;
    RatingBar productRating;

    Button productCartBtn;
    Button product_notifyme;
    TextView tv_out_of_stock;
private TextView sku;

    ImageView sliderImageView;
    SliderLayout sliderLayout;
    PagerIndicator pagerIndicator;
    ImageButton product_share_btn;
    ToggleButton product_like_btn;
    LinearLayout product_attributes;
    RecyclerView attribute_recycler;
    WebView product_description_webView;
    TextView title, category, price_new, price_old, product_stock, product_likes, product_tag_new, product_tag_discount;

    DialogLoader dialogLoader;
    ProductDetails productDetails;
    ProductAttributesAdapter attributesAdapter;

    List<Image> itemImages = new ArrayList<>();
    List<Attribute> attributesList = new ArrayList<>();
    List<CartProductAttributes> selectedAttributesList;
    private android.support.v4.app.FragmentManager fragmentManager;
    private RecentlyViewed recentlyViewed;
    private static List<ProductDetails> favouriteProductsList;
    private TextView product_brand_label;
    private AppRatingDialog dialog;
    private String customerName;
    private String customerMainID;
    private CategoryDetails brandInfo;
    public String CelebrityId = "";
    String brandName = "No";
    String brandId = "";
    String brandImage = "";
    String is_wishlist = "";
    TextView product_description_body;


    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.product_description, container, false);
        if (getActivity().findViewById(R.id.myLayout).getVisibility() != View.GONE) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }


        // Set the Title of Toolbar

        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        SpannableString s = new SpannableString(getString(R.string.product_description));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);


        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        customerMainID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("customerID", "");
        try {
            customerName = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userName", "");
        } catch (Exception ignored) {

        }


        if (getArguments().containsKey("CelebrityId")) {
            CelebrityId = getArguments().getString("CelebrityId");
        }


        // Binding Layout Views
        title = rootView.findViewById(R.id.product_title);
        product_brand_label = rootView.findViewById(R.id.product_brand_name);
//        category = (TextView) rootView.findViewById(R.id.product_category);
//        price_old = (TextView) rootView.findViewById(R.id.product_price_old);
        price_new = rootView.findViewById(R.id.product_price_new);
        productRating = rootView.findViewById(R.id.product_total_likes);
        product_description_body = rootView.findViewById(R.id.product_description_body);
        sku = rootView.findViewById(R.id.sku);

        productRating.setRating(0);
//        product_stock = (TextView) rootView.findViewById(R.id.product_stock);
//        product_likes = (TextView) rootView.findViewById(R.id.product_total_likes);
        product_tag_new = rootView.findViewById(R.id.product_tag_new);
        product_tag_discount = rootView.findViewById(R.id.product_tag_discount);
        product_description_webView = rootView.findViewById(R.id.product_description_webView);
        sliderLayout = rootView.findViewById(R.id.product_cover_slider);
        pagerIndicator = rootView.findViewById(R.id.product_slider_indicator);
        product_like_btn = rootView.findViewById(R.id.product_like_btn);
        product_share_btn = rootView.findViewById(R.id.product_share_btn);
        product_attributes = rootView.findViewById(R.id.product_attributes);
        attribute_recycler = rootView.findViewById(R.id.product_attributes_recycler);
        productCartBtn = rootView.findViewById(R.id.product_cart_btn);
        product_notifyme = rootView.findViewById(R.id.product_notifyme);
        tv_out_of_stock = rootView.findViewById(R.id.tv_out_of_stock);

        product_tag_new.setVisibility(View.GONE);
        product_tag_discount.setVisibility(View.GONE);
        product_attributes.setVisibility(View.VISIBLE);

        attribute_recycler.setNestedScrollingEnabled(false);

        // Set Paint flag on price_old TextView that applies a strike-through decoration to price_old Text
//        price_old.setPaintFlags(price_old.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);


        dialogLoader = new DialogLoader(getContext());
        favouriteProductsList = ((App) getContext().getApplicationContext()).getFavouriteProductsList();

        selectedAttributesList = new ArrayList<>();

        // Get product Info from bundle arguments
        if (getArguments() != null) {

            if (getArguments().containsKey("itemID")) {
                productID = getArguments().getInt("itemID");

                Call<List<ProductRating>> call = APIClient.getInstance()
                        .getRating
                                (
                                        String.valueOf(productID), ConstantValues.AUTHORIZATION
                                );


                call.enqueue(new Callback<List<ProductRating>>() {
                    @Override
                    public void onResponse(Call<List<ProductRating>> call, Response<List<ProductRating>> response) {
                        if (response.isSuccessful()) {
                            try {
                                if (response.body() != null && response.body().get(0).getCount() > 1) {
//                                holder.productRating.setVisibility(View.VISIBLE);
                                    productRating.setRating(((Float.valueOf(response.body().get(0).getAvgRatingPercent()) * (5 - 1) / 100) + 1));
                                }
                            } catch (Exception e) {

                            }
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductRating>> call, Throwable t) {

                    }
                });
                // Request Product Details
                RequestProductDetail(productID);

            } else if (getArguments().containsKey("productDetails")) {
                productDetails = getArguments().getParcelable("productDetails");
                Call<List<ProductRating>> call = APIClient.getInstance()
                        .getRating
                                (
                                        String.valueOf(productDetails.getId()), ConstantValues.AUTHORIZATION
                                );


                call.enqueue(new Callback<List<ProductRating>>() {
                    @Override
                    public void onResponse(Call<List<ProductRating>> call, Response<List<ProductRating>> response) {
                        if (response.isSuccessful()) {
                            try {
                                if (response.body() != null && response.body().get(0).getCount() > 1) {
//                                holder.productRating.setVisibility(View.VISIBLE);
                                    productRating.setRating(((Float.valueOf(response.body().get(0).getAvgRatingPercent()) * (5 - 1) / 100) + 1));
                                }
                            } catch (Exception e) {

                            }
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductRating>> call, Throwable t) {

                    }
                });
                // Set Product Details
                //setProductDetails(productDetails);
                ProductDetail(productDetails.getSku());
            }

        }
        fragmentManager = getFragmentManager();

        // Add RecentlyViewed Fragment to specified FrameLayout
        recentlyViewed = new RecentlyViewed();
        Bundle recent = new Bundle();
        recent.putString("CelebrityId", CelebrityId);
        recentlyViewed.setArguments(recent);

        fragmentManager.beginTransaction().replace(R.id.recently_viewed_fragment, recentlyViewed).commit();

        productRating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getActivity() != null && event.getAction() == MotionEvent.ACTION_UP) {
                    if (ConstantValues.IS_USER_LOGGED_IN) {
                        showDialog();
                    } else {
                        Intent i = new Intent(getContext(), Login.class);
                        getContext().startActivity(i);
                        ((MainActivity) getContext()).finish();
                        ((MainActivity) getContext()).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                    }
                }
//                if (getActivity() != null && event.getAction() == MotionEvent.ACTION_UP) {
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//                    View dialogView = getLayoutInflater().inflate(R.layout.dialog_webview, null);
//                    dialog.setView(dialogView);
//                    dialog.setCancelable(true);
//
//                    final WebView mWebview = dialogView.findViewById(R.id.dialog_webView);
//                    final ProgressBar progressBar = dialogView.findViewById(R.id.progressBar);
//                    final TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
//                    final Button dialog_button = dialogView.findViewById(R.id.dialog_button);
//                    final EditText edit = dialogView.findViewById(R.id.edit);
//
//
//                    final AlertDialog alertDialog = dialog.create();
//                    dialog_button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            alertDialog.dismiss();
//
//                        }
//                    });
//                    dialog_title.setText("Rating/Reviews");
//                    dialog_button.setText("Cancel");
//                    mWebview.getSettings().setJavaScriptEnabled(true);
//                    mWebview.setWebViewClient(new AppWebViewClients(progressBar) {
//                        @SuppressWarnings("deprecation")
//                        @Override
//                        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                            if (getActivity() != null) {
//                                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @TargetApi(android.os.Build.VERSION_CODES.M)
//                        @Override
//                        public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
//                            // Redirect to deprecated method, so you can use it in all SDK versions
//                            onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
//                        }
//                    });
//                    try {
//                        edit.setFocusable(true);
//                        edit.requestFocus();
////                        productDetails.getProductsUrlKey() + "#reviews"
////                        Log.i("fgfdg",productDetails.getProductsUrlKey() + "#reviews");
//                        mWebview.loadUrl(productDetails.getProductsUrlKey() + "#reviews");
////                        mWebview.requestFocus(View.FOCUS_DOWN);
////                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
////                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//
////                    final EditText dialog_input = dialogView.findViewById(R.id.dialog_input);
////                    final TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
//
////                    dialog_button.setText(getString(R.string.send));
////                    dialog_title.setText(getString(R.string.forgot_your_password));
//
//                        alertDialog.show();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//                    }
//
//
//                }
                return true;
            }


        });
        return rootView;

    }

    private void showDialog() {
        if (getActivity() != null) {
            dialog = new AppRatingDialog.Builder()
//                    .setPositiveButtonText("Submit")
//                    .setNegativeButtonText("Cancel")
//                    .setNeutralButtonText("Later")
//                    .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"))
//                    .setDefaultRating(3)
//                    .setTitle("Rate this product")
//                    .setDescription("Please give your feedback")
//                    .setCommentInputEnabled(true)
//                    .setStarColor(R.color.colorAccent)
//                    .setNoteDescriptionTextColor(R.color.textColorPrimary)
//                    .setTitleTextColor(R.color.black)
//                    .setDescriptionTextColor(R.color.black)
//                    .setHint("Please write your comment here ...")
//                    .setHintTextColor(R.color.textColorPrimary)
//                    .setCommentTextColor(R.color.textColorPrimary)
//                    .setCommentBackgroundColor(R.color.windowBackground)
//                    .setCancelable(true)
//                    .setCanceledOnTouchOutside(true)
//                    .setWindowAnimation(R.style.MyDialogFadeAnimation)
//                    .create((MainActivity) getActivity())
//                    .setTargetFragment(this, R.string.product_description); // only if listener is implemented by fragment
//            dialog.show();


                    .setPositiveButtonText((R.string.submite))
                    .setNegativeButtonText((R.string.cancle))
                    .setNeutralButtonText((R.string.later))
                                       .setNoteDescriptions(Arrays.asList(getResources().getString(R.string.chooce1), getResources().getString(R.string.chooce2), getResources().getString(R.string.chooce3), getResources().getString(R.string.chooce4), getResources().getString(R.string.chooc5)))

//                    .setNoteDescriptions(Arrays.asList(getResources().getString(R.string.chooce1),getResources().getString(R.string.chooce2), getResources().getString(R.string.chooce3), getResources().getString(R.string.chooce4), getResources().getString(R.string.chooce5)))
                    .setDefaultRating(3)
                    .setTitle((R.string.rate))
                    .setDescription((R.string.feedback))
                    .setCommentInputEnabled(true)
                    .setStarColor(R.color.colorAccent)
                    .setNoteDescriptionTextColor(R.color.textColorPrimary)
                    .setTitleTextColor(R.color.black)
                    .setDescriptionTextColor(R.color.black)
                    .setHint((R.string.hint))
                    .setHintTextColor(R.color.textColorPrimary)
                    .setCommentTextColor(R.color.textColorPrimary)
                    .setCommentBackgroundColor(R.color.windowBackground)
                    .setCancelable(true)
                    .setCanceledOnTouchOutside(true)
                    .setWindowAnimation(R.style.MyDialogFadeAnimation)
                    .create((MainActivity) getActivity())
                    .setTargetFragment(this, R.string.product_description); // only if listener is implemented by fragment

            dialog.show();

        }

        //                .setCancelable(true)
//                .setCanceledOnTouchOutside(false)
    }

    //*********** Adds Product's Details to the Views ********//

    private void setProductDetails(final ProductDetails productDetails) {

        this.productDetails = productDetails;
        // Get Product Images and Attributes
        itemImages = productDetails.getImages();
//        attributesList = productDetails.getAttributes();


        // Setup the ImageSlider of Product Images
        // ImageSlider(productDetails.getProductsImage(), itemImages,null);


        // Set Product's Information
        title.setText(productDetails.getProductsName());
        // brandInfo = ((App) Objects.requireNonNull(getContext()).getApplicationContext()).findBrandByid(productDetails.getBrandsName());
        sku.setText(productDetails.getSku());
        sku.setTypeface(Typeface.createFromAsset(
                getContext().getAssets(),
                "font/baskvill_regular.OTF"
        ));

        if (productDetails.customAttributes != null) {

//            productDetails.setProductsQuantity(92);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    brandName = productDetails.customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("brands") || p.getAttributeCode().equals("brands")).collect(Collectors.toList()).get(0).getValue();
                    brandId = productDetails.customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("brand_id") || p.getAttributeCode().equals("brand_id")).collect(Collectors.toList()).get(0).getValue();
                    brandImage = productDetails.customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("brand_image") || p.getAttributeCode().equals("brand_image")).collect(Collectors.toList()).get(0).getValue();

                    productDetails.setProductsQuantity(Integer.parseInt(productDetails.customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("quantity")).collect(Collectors.toList()).get(0).getValue()));

                    if (ConstantValues.IS_USER_LOGGED_IN) {
                        productDetails.setWishlist_item_id(String.valueOf(Integer.parseInt(productDetails.customAttributes.stream().filter(Objects::nonNull)
                                .filter(p -> p.getAttributeCode().equals("is_wishlist")).collect(Collectors.toList()).get(0).getValue())));
                    }


                } catch (Exception e) {

                }

            } else {

                for (CustomAttribute person : productDetails.customAttributes) {
                    if (person.getAttributeCode().equals("brands")) {
                        brandName = person.getValue();

                    }
                    if (person.getAttributeCode().equals("brand_id")) {
                        brandId = person.getValue();

                    }
                    if (person.getAttributeCode().equals("brand_image")) {
                        brandImage = person.getValue();
                    }
                    if (person.getAttributeCode().equals("is_wishlist")) {
                        productDetails.setWishlist_item_id(person.getValue());

                    }
                    if (person.getAttributeCode().equals("quantity")) {
                        productDetails.setProductsQuantity(Integer.parseInt(person.getValue()));
                    }
                }

            }
        }

        if (!brandName.equalsIgnoreCase("No")) {
            product_brand_label.setText(brandName);
//            sku.setText(productDetails.getSku());
            product_brand_label.setOnClickListener(view -> {
                if (getActivity() != null) {


                    // Navigate to Products of selected SubCategory
                    Bundle categoryInfo = new Bundle();
                    categoryInfo.putInt("CategoryID", Integer.parseInt(brandId));
                    categoryInfo.putString("CategoryName", brandName);
                    categoryInfo.putString("CategoryNameShow", brandName);
                    categoryInfo.putString("CategoryImage", brandImage);

                    Fragment fragment = new FragmentBrandsProduct();
                    fragment.setArguments(categoryInfo);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionBrands)).commit();
                }
            });
        } else {
            product_brand_label.setText("Nil");
        }
        // Check Discount on Product with the help of static method of Helper class
        String discount = Utilities.checkDiscount(productDetails.getProductsPrice(), productDetails.getDiscountPrice());

        if (discount != null) {
            productDetails.setIsSaleProduct("1");

            // Set Discount Tag
            product_tag_discount.setVisibility(View.VISIBLE);
            product_tag_discount.setText(discount);
            // Set Price info based on Discount
//            price_old.setVisibility(View.VISIBLE);
//            price_old.setText(ConstantValues.CURRENCY_SYMBOL + productDetails.getProductsPrice());
            productBasePrice = Double.parseDouble(productDetails.getDiscountPrice());

        } else {
            productDetails.setIsSaleProduct("0");

//            price_old.setVisibility(View.GONE);
            product_tag_discount.setVisibility(View.GONE);
            productBasePrice = Double.parseDouble(productDetails.getProductsPrice());
        }


        // Check if the Product is Out of Stock
        if (productDetails.getProductsQuantity() < 1) {
      /*      productCartBtn.setText(getString(R.string.outOfStock));
            productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_red));*/
            productCartBtn.setVisibility(View.GONE);
            product_notifyme.setVisibility(View.VISIBLE);
            tv_out_of_stock.setVisibility(View.VISIBLE);

        } else {
//            product_stock.setText(getString(R.string.in_stock));
            productCartBtn.setText(getString(R.string.addToCart));
//            product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentBlue));
            productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_accent));
        }


        // Check if the Product is Newly Added with the help of static method of Helper class
//        if (Utilities.checkNewProduct(productDetails.getProductsDateAdded())) {
//            product_tag_new.setVisibility(View.VISIBLE);
//        }
//        else {
//            product_tag_new.setVisibility(View.GONE);
//        }


        String description = productDetails.getProductsDescription();
        product_description_body.setText(Html.fromHtml(description));
       /* String styleSheet = "<style type=\"text/css\"> " +
                "@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/baskvill_regular.OTF\")}body {font-family: MyFont;font-size: 14px;text-align: justify;background:#ffffff; margin:0; padding:0}" +
                "p{color:#000000;} " +
                "img{display:inline; height:auto; max-width:100%;}" +
                "</style>";

        product_description_webView.setHorizontalScrollBarEnabled(false);
        product_description_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);*/


        // Set Product's Prices
        attributesPrice = 0;


        if (attributesList.size() > 0) {
            product_attributes.setVisibility(View.VISIBLE);

            for (int i = 0; i < attributesList.size(); i++) {

                CartProductAttributes productAttribute = new CartProductAttributes();

                // Get Name and First Value of current Attribute
                Option option = attributesList.get(i).getOption();
                Value value = attributesList.get(i).getValues().get(0);


                // Add the Attribute's Value Price to the attributePrices
                String attrPrice = value.getPricePrefix() + value.getPrice();
                attributesPrice += Double.parseDouble(attrPrice);


                // Add Value to new List
                List<Value> valuesList = new ArrayList<>();
                valuesList.add(value);


                // Set the Name and Value of Attribute
                productAttribute.setOption(option);
                productAttribute.setValues(valuesList);


                // Add current Attribute to selectedAttributesList
                selectedAttributesList.add(i, productAttribute);
            }


            // Initialize the ProductAttributesAdapter for RecyclerView
            attributesAdapter = new ProductAttributesAdapter(getContext(), Product_Description.this, attributesList, selectedAttributesList);

            // Set the Adapter and LayoutManager to the RecyclerView
            attribute_recycler.setAdapter(attributesAdapter);
            attribute_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            attributesAdapter.notifyDataSetChanged();

        } else {
            product_attributes.setVisibility(View.GONE);
        }


        productFinalPrice = productBasePrice + attributesPrice;
        price_new.setText(productFinalPrice + " " + ConstantValues.CURRENCY_SYMBOL);
        price_new.setTypeface(Typeface.createFromAsset(
                getContext().getAssets(),
                "font/baskvill_regular.OTF"
        ));
        // Check if the User has Liked the Product
        if (ConstantValues.IS_USER_LOGGED_IN) {
            if (productDetails.getWishlist_item_id() != null && !productDetails.getWishlist_item_id().equalsIgnoreCase("")) {
                product_like_btn.setChecked(true);
            } else {
                product_like_btn.setChecked(false);
            }

           /* boolean done = false;
            for (ProductDetails prod :
                    favouriteProductsList) {

                if (prod.getId() == productDetails.getId()) {
                    product_like_btn.setChecked(true);
                    productDetails.setWishlist_item_id(prod.getWishlist_item_id());
                    done = true;
                }

            }

            if (!done) {
                product_like_btn.setChecked(false);

            }*/
        }

        if (My_Cart.checkCartHasProduct(productDetails.getId())) {
            productCartBtn.setText(R.string.added_to_cart);
            productCartBtn.setEnabled(false);
        } else {
        }


        // Handle Click event of product_share_btn Button
        product_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Share Product with the help of static method of Helper class
                Utilities.shareProduct
                        (
                                getContext(),
                                productDetails.getProductsName(),
                                sliderImageView,
                                productDetails.getProductsUrlKey()
                        );
            }
        });


        // Handle Click event of product_like_btn Button
        product_like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ConstantValues.IS_USER_LOGGED_IN) {
                    //  RequestWishList(null);

                    if (product_like_btn.isChecked()) {
                        productDetails.setIsLiked("1");
                        product_like_btn.setChecked(true);

                        LikeProduct(productDetails.getProductsId(), customerID, getContext(), view);

                    } else {

                        /*boolean notExist = true;
                        for (ProductDetails prod :
                                favouriteProductsList) {

                            if (prod.getId() == productDetails.getId()) {
                                productDetails.setWishlist_item_id(prod.getWishlist_item_id());
                                notExist = false;
                                break;

                            }
                            notExist = true;

                        }*/

                        if (productDetails.getWishlist_item_id() != null && !productDetails.getWishlist_item_id().equals("")) {
                            productDetails.setIsLiked("0");
                            product_like_btn.setChecked(false);


                            UnlikeProduct(productDetails.getWishlist_item_id(), customerID, getContext(), view);

                        } else {
                            product_like_btn.setChecked(true);

                        }

                    }

                } else {
                    // Keep the Like Button Unchecked
                    product_like_btn.setChecked(false);

                    // Navigate to Login Activity
                    Intent i = new Intent(getContext(), Login.class);
                    getContext().startActivity(i);
                    ((MainActivity) getContext()).finish();
                    ((MainActivity) getContext()).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                }
            }
        });


        // ak-===== Handle Click event of productCartBtn Button
        productCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (productDetails.getProductsQuantity() > 0) {

                    CartProduct cartProduct = new CartProduct();

                    // Set Product's Price, Quantity and selected Attributes Info
                    productDetails.setCustomersBasketQuantity(1);
                    productDetails.setProductsPrice(String.valueOf(productBasePrice));
                    productDetails.setAttributesPrice(String.valueOf(attributesPrice));
                    productDetails.setProductsFinalPrice(String.valueOf(productFinalPrice));
                    productDetails.setTotalPrice(String.valueOf(productFinalPrice));
                    cartProduct.setCustomersBasketProduct(productDetails);
                    cartProduct.setCustomersBasketProductAttributes(selectedAttributesList);
                    cartProduct.setCelebrityId(CelebrityId);

                    // Add the Product to User's Cart with the help of static method of My_Cart class
                    My_Cart.AddCartItem
                            (
                                    cartProduct
                            );
                    productCartBtn.setText(R.string.added_to_cart);
                    productCartBtn.setEnabled(false);

                    // Recreate the OptionsMenu
                    ((MainActivity) getContext()).invalidateOptionsMenu();
                    addToCartDialog();
                    // Snackbar.make(view, getContext().getString(R.string.item_added_to_cart), Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        product_notifyme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) getActivity()).notifyMeDialog((MainActivity) getActivity(), String.valueOf(productDetails.getProductsId()));
            }
        });
    }


    //*********** Update Product's final Price based on selected Attributes ********//

    public void updateProductPrice() {

        attributesPrice = 0;

        // Get Attribute's Prices List from ProductAttributesAdapter
        String[] attributePrices = attributesAdapter.getAttributePrices();

        double attributesTotalPrice = 0.0;

        for (int i = 0; i < attributePrices.length; i++) {
            // Get the Price of Attribute at given Position in attributePrices array
            double price = Double.parseDouble(attributePrices[i]);

            attributesTotalPrice += price;
        }

        attributesPrice = attributesTotalPrice;


        // Calculate and Set Product's total Price
        productFinalPrice = productBasePrice + attributesPrice;
        price_new.setText(ConstantValues.CURRENCY_SYMBOL + productFinalPrice);
        price_new.setTypeface(Typeface.createFromAsset(
                getContext().getAssets(),
                "font/baskvill_regular.OTF"
        ));
    }


    //*********** Setup the ImageSlider with the given List of Product Images ********//

    private void ImageSlider(String itemThumbnail, List<Image> itemImages, ArrayList<Media_gallery_entries> media_gallery_entries) {

        // Initialize new HashMap<ImageName, ImagePath>
        final HashMap<String, String> slider_covers = new HashMap<>();
        String[] images = new String[]{itemThumbnail};

        // Initialize new Array for Image's URL
        if (itemImages != null) {
            images = new String[itemImages.size()];


            if (itemImages.size() > 0) {
                for (int i = 0; i < itemImages.size(); i++) {
                    // Get Image's URL at given Position from itemImages List
                    images[i] = itemImages.get(i).getImage();
                }
            }
        }
        if (media_gallery_entries != null) {

            for (int i = 0; i < media_gallery_entries.size(); i++) {


                if (media_gallery_entries.get(i) != null) {

                    if (media_gallery_entries.get(i).types != null) {
                        slider_covers.put(i + "", "https://celebritiescart.com/pub/media/catalog/product/" + media_gallery_entries.get(i).file);

                        if (media_gallery_entries.get(i).types.contains("image")) {
                            String temp = slider_covers.get("0");
                            slider_covers.put("0", "https://celebritiescart.com/pub/media/catalog/product/" + media_gallery_entries.get(i).file);
                            slider_covers.put(i + "", temp);

                        }
                    }
                }

            }


        } else {
            // Put Image's Name and URL to the HashMap slider_covers
            if (itemThumbnail.equalsIgnoreCase("")) {
                slider_covers.put("a", "" + R.drawable.placeholder);

            } else if (images.length == 0) {
                slider_covers.put("a", itemThumbnail);

            } else {
                slider_covers.put("a", itemThumbnail);

                for (int i = 0; i < images.length; i++) {
                    slider_covers.put("b" + i, images[i]);
                }
            }

        }
        for (String name : slider_covers.keySet()) {

            // Initialize DefaultSliderView
            DefaultSliderView defaultSliderView = new DefaultSliderView(getContext()) {
                @Override
                public View getView() {
                    View v = LayoutInflater.from(getContext()).inflate(com.daimajia.slider.library.R.layout.render_type_default, null);

                    // Get daimajia_slider_image ImageView of DefaultSliderView
                    sliderImageView = v.findViewById(com.daimajia.slider.library.R.id.daimajia_slider_image);

                    // Set ScaleType of ImageView
                    sliderImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    bindEventAndShow(v, sliderImageView);

                    return v;
                }
            };

            // Set Attributes(Name, Placeholder, Image, Type etc) to DefaultSliderView
            defaultSliderView
                    .description(name)
                    .empty(R.drawable.placeholder)
                    .image(slider_covers.get(name))
                    .setScaleType(DefaultSliderView.ScaleType.CenterInside);

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


    //*********** Request Product Details from the Server based on productID ********//

    public void RequestProductDetail(final int productID) {

        dialogLoader.showProgressDialog();


        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(0);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setProductsId(String.valueOf(productID));


        Call<ProductData> call = APIClient.getInstance()
                .getAllProducts(ConstantValues.AUTHORIZATION, "entity_id", String.valueOf(productID), "eq", null, "DESC", "2", "1", CelebrityId, customerMainID);


        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (!response.body().getProductData().isEmpty()) {

                        // Product's Details has been returned
                        setProductDetails(response.body().getProductData().get(0));
                        ProductDetail(response.body().getProductData().get(0).getSku());
                    } else if (response.body().getProductData().isEmpty()) {
                        Snackbar.make(rootView, "Product not found", Snackbar.LENGTH_LONG).show();

                    } else {
                        // Unable to get Success status
                        //    Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    if (getContext() != null) {
                        //     Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                if (getContext() != null) {
                    //    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void ProductDetail(final String sku) {

        dialogLoader.showProgressDialog();


        Call<ProductDetailMainModel> call = APIClient.getInstance()
                .getProductDetail(sku, customerMainID);


        call.enqueue(new Callback<ProductDetailMainModel>() {
            @Override
            public void onResponse(Call<ProductDetailMainModel> call, retrofit2.Response<ProductDetailMainModel> response) {
                try {

                    dialogLoader.hideProgressDialog();

                    // Check if the Response is successful
                    if (response.isSuccessful()) {
                        if (!response.body().media_gallery_entries.isEmpty()) {

                            // Product's Details has been returned
                            ImageSlider(productDetails.image, null, response.body().media_gallery_entries);
                            productDetails = new ProductDetails();
                            productDetails.setId(Integer.valueOf(response.body().id));
                            productDetails.setSku(response.body().sku);
                            productDetails.setAttributeSetId(Double.valueOf(response.body().attribute_set_id));
                            productDetails.setPrice(Double.valueOf(response.body().price));
                            productDetails.setName(response.body().name);
                            productDetails.setStatus(Double.valueOf(response.body().status));
                            productDetails.setVisibility(Double.valueOf(response.body().visibility));
                            productDetails.setTypeId(response.body().type_id);
                            productDetails.setCreatedAt(response.body().created_at);
                            productDetails.setUpdatedAt(response.body().updated_at);
                            productDetails.setProductLinks(response.body().product_links);
                            productDetails.setTierPrices(response.body().tierPrices);


                            Gson gson = new Gson();
                            Type type = new TypeToken<List<CustomAttribute>>() {
                            }.getType();

                            JsonArray jsonArray = response.body().custom_attributes;
                            List<CustomAttribute> object = gson.fromJson(jsonArray, type);
                            productDetails.customAttributes.addAll(object);
                            setProductDetails(productDetails);
                        }
                    } else {
                        if (getContext() != null) {
                            //     Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ProductDetailMainModel> call, Throwable t) {
                dialogLoader.showProgressDialog();
                if (getContext() != null) {
                    //    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void RequestWishList(ProductAdapter adapter) {

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
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }

                    } else {


                    }

                }

                @Override
                public void onFailure(Call<List<WishListData>> call, Throwable t) {


                }
            });
        }
    }

    private static void addProducts(List<ProductDetails> productData) {

        // Add Products to favouriteProductsList from the List of ProductData
        favouriteProductsList.clear();

        favouriteProductsList.addAll(productData);
        Log.i("Fav List", favouriteProductsList.toString());
    }
    //*********** Request the Server to Like the Product based on productID and customerID ********//

    public static void LikeProduct(int productID, String customerID, final Context context, final View view) {

        Call<List<WishListResponse>> call = APIClient.getInstance()
                .likeProduct
                        (
                                String.valueOf(productID),
                                "Bearer " + customerID
                        );

        call.enqueue(new Callback<List<WishListResponse>>() {
            @Override
            public void onResponse(Call<List<WishListResponse>> call, retrofit2.Response<List<WishListResponse>> response) {
                // Check if the Response is successful
                if (response.isSuccessful()) {

                    // Check the Success status
                    if (response.body().get(0).getStatus()) {

                        // Product has been Liked. Show the message to User
                        //    Snackbar.make(view, context.getString(R.string.added_to_favourites), Snackbar.LENGTH_SHORT).show();

                    } else if (!response.body().get(0).getStatus()) {
                        //    Snackbar.make(view, response.body().get(0).getMessage(), Snackbar.LENGTH_LONG).show();

                    } else {
                        // Unable to get Success status
                        //    Snackbar.make(view, context.getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    if (context != null) {

                        //    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<WishListResponse>> call, Throwable t) {
                if (context != null) {

                    //    Toast.makeText(context, "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public static void LikeProduct(int productID, String customerID, final Context context, final View view, ProductAdapter productAdapter) {

        Call<List<WishListResponse>> call = APIClient.getInstance()
                .likeProduct
                        (
                                String.valueOf(productID),
                                "Bearer " + customerID
                        );

        call.enqueue(new Callback<List<WishListResponse>>() {
            @Override
            public void onResponse(Call<List<WishListResponse>> call, retrofit2.Response<List<WishListResponse>> response) {
                // Check if the Response is successful
                if (response.isSuccessful()) {

                    // Check the Success status
                    if (response.body().get(0).getStatus()) {

                        // Product has been Liked. Show the message to User
                        if (productAdapter != null) {
                            //    RequestWishList(productAdapter);
                        }
                        //  productAdapter.notifyDataSetChanged();
                        //   Snackbar.make(view, context.getString(R.string.added_to_favourites), Snackbar.LENGTH_SHORT).show();


                    } else if (!response.body().get(0).getStatus()) {
                        //   Snackbar.make(view, response.body().get(0).getMessage(), Snackbar.LENGTH_LONG).show();

                    } else {
                        // Unable to get Success status
                        //    Snackbar.make(view, context.getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    if (context != null) {

                        //    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<WishListResponse>> call, Throwable t) {
                if (context != null) {

                    //   Toast.makeText(context, "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //*********** Request the Server to Unlike the Product based on productID and customerID ********//

    public static void UnlikeProduct(String productID, String customerID, final Context context, final View view) {

        Call<List<WishListResponse>> call = APIClient.getInstance()
                .unlikeProduct
                        (
                                productID,
                                "Bearer " + customerID
                        );

        call.enqueue(new Callback<List<WishListResponse>>() {
            @Override
            public void onResponse(Call<List<WishListResponse>> call, retrofit2.Response<List<WishListResponse>> response) {
                // Check if the Response is successful
                if (response.isSuccessful()) {

                    // Check the Success status
                    if (response.body().get(0).getStatus()) {

                        // Product has been Disliked. Show the message to User
                        //    Snackbar.make(view, context.getString(R.string.removed_from_favourites), Snackbar.LENGTH_SHORT).show();

                    } else if (!response.body().get(0).getStatus()) {
                        //    Snackbar.make(view, response.body().get(0).getMessage(), Snackbar.LENGTH_LONG).show();

                    } else {
                        // Unable to get Success status
                        //    Snackbar.make(view, context.getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();

                    }
                } else {
                    if (context != null) {

                        //    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<List<WishListResponse>> call, Throwable t) {
                if (context != null) {

                    //    Toast.makeText(context, "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public static void UnlikeProduct(String productID, String customerID, final Context context, final View view, ProductAdapter productAdapter) {

        Call<List<WishListResponse>> call = APIClient.getInstance()
                .unlikeProduct
                        (
                                productID,
                                "Bearer " + customerID
                        );

        call.enqueue(new Callback<List<WishListResponse>>() {
            @Override
            public void onResponse(Call<List<WishListResponse>> call, retrofit2.Response<List<WishListResponse>> response) {
                // Check if the Response is successful
                if (response.isSuccessful()) {

                    // Check the Success status
                    if (response.body().get(0).getStatus()) {

                        // Product has been Disliked. Show the message to User
                        if (productAdapter != null) {
                            //   RequestWishList(productAdapter);
                        }
                        //    Snackbar.make(view, context.getString(R.string.removed_from_favourites), Snackbar.LENGTH_SHORT).show();


                    } else if (!response.body().get(0).getStatus()) {
                        //    Snackbar.make(view, response.body().get(0).getMessage(), Snackbar.LENGTH_LONG).show();

                    } else {
                        // Unable to get Success status
                        //  Snackbar.make(view, context.getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();

                    }
                } else {
                    if (context != null) {

                        //    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<List<WishListResponse>> call, Throwable t) {
                if (context != null) {

                    //    Toast.makeText(context, "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {

//        Log.i("negative", "negative");
//        Toast.makeText(getContext(), "Neutral button clicked", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNeutralButtonClicked() {
//        Log.i("neutral", "neutral");


    }

    @Override
    public void onPositiveButtonClicked(int i, String s) {

//        Log.i("positive", "positive");

        List<String> titleArray = Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!");
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setCustomerId(customerMainID);


        reviewRequest.setNickname(customerName);
        reviewRequest.setStoreId("1");
        reviewRequest.setProductId(String.valueOf(productDetails.getId()));
        reviewRequest.setDetail(s);
        reviewRequest.setTitle(titleArray.get(i - 1));

        List<RatingDatum> ratingData = new ArrayList<>();
        ratingData.add(new RatingDatum("1", "Quality", String.valueOf(i)));
        ratingData.add(new RatingDatum("2", "Value", String.valueOf(i)));
        ratingData.add(new RatingDatum("3", "Price", String.valueOf(i)));
        reviewRequest.setRatingData(ratingData);

        Call<List<ReviewResponse>> call = APIClient.getInstance()
                .postCustomerReview
                        (
                                "Bearer " + customerID, reviewRequest
                        );

        dialogLoader.showProgressDialog();
        call.enqueue(new Callback<List<ReviewResponse>>() {
            @Override
            public void onResponse(Call<List<ReviewResponse>> call, Response<List<ReviewResponse>> response) {
                dialogLoader.hideProgressDialog();

                if (response.isSuccessful()) {
                    try {
                        if (response.body().get(0).getStatus()) {
                            //    Snackbar.make(rootView, response.body().get(0).getMessage(), Snackbar.LENGTH_SHORT).show();

                        } else {
                            //    Snackbar.make(rootView, response.body().get(0).getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
//                        Log.i("msg", "success");

                    } catch (Exception e) {

                    }
                } else {

//                    Log.i("msg", response.message());
//                    try {
//                        Log.i("msg", response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }

            @Override
            public void onFailure(Call<List<ReviewResponse>> call, Throwable t) {
                dialogLoader.hideProgressDialog();

                try {
                    //    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_LONG).show();
                } catch (Exception ignored) {

                }

            }
        });


    }

    //Add to cart dailog
    public void addToCartDialog() {


        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.add_to_cart_dialog, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        final AlertDialog alertDialog = dialog.create();

        Button yes = (Button) dialogView.findViewById(R.id.yes);

        Button checkout = (Button) dialogView.findViewById(R.id.checkout);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                FragmentManager fragmentManager = getFragmentManager();

                My_Cart fragment = new My_Cart();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            }
        });


        alertDialog.show();


    }
}


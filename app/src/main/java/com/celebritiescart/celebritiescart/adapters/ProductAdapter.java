package com.celebritiescart.celebritiescart.adapters;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.Login;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.databases.User_Recents_DB;
import com.celebritiescart.celebritiescart.fragments.My_Cart;
import com.celebritiescart.celebritiescart.fragments.Product_Description;
import com.celebritiescart.celebritiescart.models.cart_model.CartProduct;
import com.celebritiescart.celebritiescart.models.cart_model.CartProductAttributes;
import com.celebritiescart.celebritiescart.models.product_model.CustomAttribute;
import com.celebritiescart.celebritiescart.models.product_model.ProductDetails;
import com.celebritiescart.celebritiescart.models.product_model.ProductRating;
import com.celebritiescart.celebritiescart.models.product_model.WishListData;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * ProductAdapter is the adapter class of RecyclerView holding List of Products in All_Products and other Product relevant Classes
 **/

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private ProductAdapter productAdapter;
    private List<ProductDetails> favouriteProductsList;
    private Context context;
    private String customerID;
    private Boolean isGridView;
    private Boolean isHorizontal;

    private User_Recents_DB recents_db;
    private List<ProductDetails> productList;
    private String isFrom;
    private String CelebrityId = "";
    private String isNew = "";
    private String brandName="";


    public ProductAdapter(Context context, List<ProductDetails> productList, Boolean isHorizontal, String isFrom, String CelebrityId) {
        this.productAdapter = this;
        this.context = context;
        this.productList = productList;
        this.isHorizontal = isHorizontal;
        this.isFrom = isFrom;
        this.CelebrityId = CelebrityId;
        favouriteProductsList = ((App) context.getApplicationContext()).getFavouriteProductsList();
//        RequestWishList();
        recents_db = new User_Recents_DB();

        try {

            customerID = this.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getString("userID", "");

        } catch (Exception e) {


        }

    }


    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = null;

        // Check which Layout will be Inflated
        if (isHorizontal) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_grid_sm, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(isGridView ? R.layout.layout_product_grid_lg : R.layout.layout_product_list_lg, parent, false);
        }


        // Return a new holder instance
        return new MyViewHolder(itemView);
    }

    public void RequestWishList() {

//        GetAllProducts getAllProducts = new GetAllProducts();
//        getAllProducts.setPageNumber(pageNumber);
//        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
//        getAllProducts.setCustomersId(customerID);
//        getAllProducts.setType("wishlist");

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

    private void addProducts(List<ProductDetails> productData) {

        // Add Products to favouriteProductsList from the List of ProductData
        favouriteProductsList.clear();
        favouriteProductsList.addAll(productData);
//        notifyDataSetChanged();


    }


    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if (position != productList.size()) {

            // Get the data model based on Position
            ProductDetails product = productList.get(position);

//


            Call<List<ProductRating>> call = APIClient.getInstance()
                    .getRating
                            (
                                    String.valueOf(product.getId()), ConstantValues.AUTHORIZATION
                            );


            call.enqueue(new Callback<List<ProductRating>>() {
                @Override
                public void onResponse(Call<List<ProductRating>> call, Response<List<ProductRating>> response) {
                    if (response.isSuccessful()) {
                        try {
                            if (response.body() != null && response.body().get(0).getCount() > 1) {
//                                holder.productRating.setVisibility(View.VISIBLE);
                                holder.productRating.setRating(((Float.valueOf(response.body().get(0).getAvgRatingPercent()) * (5 - 1) / 100) + 1));
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

            // Check if the Product is already in the Cart
            if (My_Cart.checkCartHasProduct(product.getProductsId())) {
                holder.product_checked.setVisibility(View.VISIBLE);
            } else {
                holder.product_checked.setVisibility(View.GONE);
            }


            // Set Product Image on ImageView with Glide Library
            Glide.with(context)
                    .load(product.getProductsImage()).apply(new RequestOptions()
                    .placeholder(R.drawable.placeholder).error(R.drawable.placeholder))

                    .into(holder.product_thumbnail);
            if (isFrom.equalsIgnoreCase("celebrity")) {
                if (product.customAttributes.size() != 0) {
                    for (int i = 0; i < product.customAttributes.size(); i++) {
                        holder.tv_count.setVisibility(View.INVISIBLE);
                        if (product.customAttributes.get(i).getAttributeCode().equalsIgnoreCase("product_count")) {

                            holder.tv_count.setText(product.customAttributes.get(i).getValue());
                            break;
                        } else {
                            holder.tv_count.setVisibility(View.GONE);

                        }

                    }

                } else {
                    holder.tv_count.setVisibility(View.GONE);
                }
            } else {
                holder.tv_count.setVisibility(View.GONE);

            }


            holder.product_title.setText(product.getProductsName());
            holder.product_price_old.setPaintFlags(holder.product_price_old.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            if (product.customAttributes != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        isNew = product.customAttributes.stream().filter(Objects::nonNull)
                                .filter(p -> p.getAttributeCode().equals("is_new") || p.getAttributeCode().equals("is_new")).collect(Collectors.toList()).get(0).getValue();
                        brandName = product.customAttributes.stream().filter(Objects::nonNull)
                                .filter(p -> p.getAttributeCode().equals("brands") || p.getAttributeCode().equals("brands")).collect(Collectors.toList()).get(0).getValue();
                        product.setProductsQuantity(Integer.parseInt(product.customAttributes.stream().filter(Objects::nonNull)
                                .filter(p -> p.getAttributeCode().equals("quantity")).collect(Collectors.toList()).get(0).getValue()));
                        product.setWishlist_item_id(String.valueOf(Integer.parseInt(product.customAttributes.stream().filter(Objects::nonNull)
                                .filter(p -> p.getAttributeCode().equals("is_wishlist")).collect(Collectors.toList()).get(0).getValue())));
                    } catch (Exception e) {

                    }

                } else {

                    for (CustomAttribute person : product.customAttributes) {
                        if (person.getAttributeCode().equals("is_new")) {
                            isNew = person.getValue();

                        }
                        if (person.getAttributeCode().equals("brands")) {
                            brandName = person.getValue();

                        }
                        if (person.getAttributeCode().equals("quantity")) {
                            product.setProductsQuantity(Integer.parseInt(person.getValue()));
                        }

                        if (person.getAttributeCode().equals("is_wishlist")) {
                            product.setWishlist_item_id(person.getValue());
                        }

                    }

                }
            }



            if (isNew.equalsIgnoreCase("")) {
                holder.product_tag_new.setVisibility(View.GONE);
                holder.product_tag_new_text.setVisibility(View.GONE);
            } else if (isNew.equalsIgnoreCase("0")) {
                holder.product_tag_new.setVisibility(View.GONE);
                holder.product_tag_new_text.setVisibility(View.GONE);
            } else if (isNew.equalsIgnoreCase("1")) {
                holder.product_tag_new.setVisibility(View.VISIBLE);
                holder.product_tag_new_text.setVisibility(View.VISIBLE);
            }

            if (brandName.equalsIgnoreCase("")) {
                holder.brand_title.setVisibility(View.INVISIBLE);
            } else {
                holder.brand_title.setVisibility(View.VISIBLE);
                holder.brand_title.setText(brandName);
                holder.brand_title.setTypeface(null, Typeface.BOLD);
            }

            // Calculate the Discount on Product with static method of Helper class
            final String discount = Utilities.checkDiscount(product.getProductsPrice(), product.getDiscountPrice());

            if (discount != null) {
                // Set Product's Price
                holder.product_price_old.setVisibility(View.VISIBLE);
                holder.product_price_old.setText(product.getProductsPrice() + " " + ConstantValues.CURRENCY_SYMBOL);
                holder.product_price_new.setText(product.getDiscountPrice() + " " + ConstantValues.CURRENCY_SYMBOL);

                holder.product_price_old.setTypeface(Typeface.createFromAsset(
                        this.context.getAssets(),
                        "font/baskvill_regular.OTF"
                ));
                holder.product_price_new.setTypeface(Typeface.createFromAsset(
                        this.context.getAssets(),
                        "font/baskvill_regular.OTF"
                ));

                /*

                holder.product_tag_new.setVisibility(View.GONE);
                holder.product_tag_new_text.setVisibility(View.GONE);
*/

                // Set Discount Tag and its Text
                holder.product_tag_discount.setVisibility(View.VISIBLE);
                holder.product_tag_discount_text.setVisibility(View.VISIBLE);
                holder.product_tag_discount_text.setText(discount);

            } else {

                // Check if the Product is Newly Added with the help of static method of Helper class
           /*     if (Utilities.checkNewProduct(product.getProductsDateAdded())) {
                    // Set New Tag and its Text
                    holder.product_tag_new.setVisibility(View.VISIBLE);
                    holder.product_tag_new_text.setVisibility(View.VISIBLE);
                } else {
                    holder.product_tag_new.setVisibility(View.GONE);
                    holder.product_tag_new_text.setVisibility(View.GONE);
                }
*/
                // Hide Discount Text and Set Product's Price
                holder.product_tag_discount.setVisibility(View.GONE);
                holder.product_tag_discount_text.setVisibility(View.GONE);
                holder.product_price_old.setVisibility(View.GONE);
                holder.product_price_new.setText(product.getProductsPrice() + " " + ConstantValues.CURRENCY_SYMBOL);
                holder.product_price_new.setTypeface(Typeface.createFromAsset(
                        this.context.getAssets(),
                        "font/baskvill_regular.OTF"
                ));

            }


            holder.product_like_btn.setOnCheckedChangeListener(null);

            // Check if Product is Liked
            if (ConstantValues.IS_USER_LOGGED_IN) {
                if (product.getWishlist_item_id()!=null && !product.getWishlist_item_id().equalsIgnoreCase("")){
                    holder.product_like_btn.setChecked(true);
                }else {
                    holder.product_like_btn.setChecked(false);
                }



                /*boolean done = false;
                for (ProductDetails prod :
                        favouriteProductsList) {

                    if (prod.getId().equals(product.getId())) {
                        holder.product_like_btn.setChecked(true);
                        product.setWishlist_item_id(prod.getWishlist_item_id());
                        done = true;
                        break;
                    }
                    Log.e("productAdapter","normal product "+prod.getId().toString()+" Fav is"+product.getId().toString());
                }

                if (!done) {
                    holder.product_like_btn.setChecked(false);

                }*/
            }
            // Handle the Click event of product_like_btn ToggleButton
            holder.product_like_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Check if the User is Authenticated
                    if (ConstantValues.IS_USER_LOGGED_IN) {


                          RequestWishList();


                        if (holder.product_like_btn.isChecked()) {
                            product.setIsLiked("1");
                            holder.product_like_btn.setChecked(true);
                            Product_Description.LikeProduct(product.getProductsId(), customerID, context, view, productAdapter);
                        } else {

//                            RequestWishList();
                            boolean notExist = true;
                         /*   for (ProductDetails prod :
                                    favouriteProductsList) {

                                if (prod.getId().equals(product.getId())) {
                                    product.setWishlist_item_id(prod.getWishlist_item_id());
                                    notExist = false;
                                    break;

                                }
                                notExist = true;

                            }*/


                            if (product.getWishlist_item_id() != null && !product.getWishlist_item_id().equals("")) {
                                product.setIsLiked("0");
                                holder.product_like_btn.setChecked(false);

                                Product_Description.UnlikeProduct(product.getWishlist_item_id(), customerID, context, view, productAdapter);
                            } else {
                                holder.product_like_btn.setChecked(true);

                            }
                        }

                    } else {
                        // Keep the Like Button Unchecked
                        holder.product_like_btn.setChecked(false);

                        // Navigate to Login Activity
                        Intent i = new Intent(context, Login.class);
                        context.startActivity(i);
                        ((MainActivity) context).finish();
                        ((MainActivity) context).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                    }
                }
            });


            // Handle the Click event of product_thumbnail ImageView
            holder.product_thumbnail.setOnClickListener(new View.OnClickListener() {

                // main category calling sub category click
                @Override
                public void onClick(View view) {

                    // Get Product Info


                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails", product);
                    itemInfo.putString("CelebrityId", CelebrityId);

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new Product_Description();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();


                    // Add the Product to User's Recently Viewed Products
                    if (!recents_db.getUserRecents().contains(product.getProductsId())) {
                        recents_db.insertRecentItem(product.getProductsId());
                    }
                }
            });


            // Handle the Click event of product_checked ImageView
            holder.product_checked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Get Product Info
                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails", product);
                    itemInfo.putString("CelebrityId", CelebrityId);
                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new Product_Description();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();


                    // Add the Product to User's Recently Viewed Products
                    if (!recents_db.getUserRecents().contains(product.getProductsId())) {
                        recents_db.insertRecentItem(product.getProductsId());
                    }
                }
            });



            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                product.setProductsQuantity(Integer.parseInt(product.customAttributes.stream().filter(Objects::nonNull)
                        .filter(p -> p.getAttributeCode().equals("quantity")).collect(Collectors.toList()).get(0).getValue()));
            } else {

                for (CustomAttribute person : product.customAttributes) {
                    if (person.getAttributeCode().equals("quantity")) {
                        product.setProductsQuantity(Integer.parseInt(person.getValue()));
                    }
                }

            }*/


            // Check the Button's Visibility
            if (ConstantValues.IS_ADD_TO_CART_BUTTON_ENABLED) {

                holder.product_add_cart_btn.setVisibility(View.VISIBLE);
                holder.product_add_cart_btn.setOnClickListener(null);

                if (product.getProductsQuantity() < 1) {
                    holder.product_notifyme.setVisibility(View.VISIBLE);
//                    holder.tv_out_of_stock.setVisibility(View.VISIBLE);
                    holder.tv_out_of_stock.setBackgroundColor (context.getResources().getColor( R.color.black));

                    holder.product_add_cart_btn.setVisibility(View.GONE);
                /*    holder.product_add_cart_btn.setText(context.getString(R.string.outOfStock));
                    holder.product_add_cart_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_red));*/


                } else {
                    holder.product_add_cart_btn.setText(context.getString(R.string.addToCart));
                    holder.product_notifyme.setVisibility(View.GONE);
//                    holder.tv_out_of_stock.setVisibility(View.GONE);
                    holder.tv_out_of_stock.setBackgroundColor (context.getResources().getColor( R.color.white));
                    holder.product_add_cart_btn.setVisibility(View.VISIBLE);
                    holder.product_add_cart_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_green));
                }

                holder.product_add_cart_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (product.getProductsQuantity() > 0) {
                            Utilities.animateCartMenuIcon(context, (MainActivity) context);

                            // Add Product to User's Cart
                            addProductToCart(product);

                            holder.product_checked.setVisibility(View.VISIBLE);
                            addToCartDialog((MainActivity) context);
                            //  Snackbar.make(view, context.getString(R.string.item_added_to_cart), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

                holder.product_notifyme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ((MainActivity)context).notifyMeDialog((MainActivity)context, String.valueOf(product.getProductsId()));
                    }
                });

            } else {
                // Make the Button Invisible
                holder.product_add_cart_btn.setVisibility(View.GONE);
            }

        }

    }


    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateList(List<ProductDetails> itemList){
//     productList.clear();
        this.productList = itemList;
//        productList.addAll(itemList);
//        notifyItemInserted(productList.size());
//        notifyDataSetChanged();
//        this.productList.addAll(itemList);
//        notifyItemInserted(productList.size());
        notifyDataSetChanged();
//        productList.clear();

    }
    //********** Toggles the RecyclerView LayoutManager *********//

    public void toggleLayout(Boolean isGridView) {
        this.isGridView = isGridView;
    }


    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ProgressBar cover_loader;
        ImageView product_checked;
        Button product_add_cart_btn;
        ToggleButton product_like_btn;
        ImageView product_thumbnail, product_tag_new, product_tag_discount;
        TextView product_title, product_price_old, product_price_new, product_tag_new_text, product_tag_discount_text, tv_count;
        RatingBar productRating;
        TextView tv_out_of_stock,brand_title;
        Button product_notifyme;


        public MyViewHolder(final View itemView) {
            super(itemView);

            product_checked = itemView.findViewById(R.id.product_checked);
            cover_loader = itemView.findViewById(R.id.product_cover_loader);

            product_add_cart_btn = itemView.findViewById(R.id.product_card_Btn);
            try {
                productRating = itemView.findViewById(R.id.productRating);
//                productRating.setVisibility(View.GONE);
            } catch (Exception e) {

            }
            product_like_btn = itemView.findViewById(R.id.product_like_btn);
            product_title = itemView.findViewById(R.id.product_title);
            product_price_old = itemView.findViewById(R.id.product_price_old);
            product_price_new = itemView.findViewById(R.id.product_price_new);
            product_thumbnail = itemView.findViewById(R.id.product_cover);
            product_tag_new = itemView.findViewById(R.id.product_tag_new);
            product_tag_new_text = itemView.findViewById(R.id.product_tag_new_text);
            product_tag_discount = itemView.findViewById(R.id.product_tag_discount);
            product_tag_discount_text = itemView.findViewById(R.id.product_tag_discount_text);
            tv_count = itemView.findViewById(R.id.tv_count);
            tv_out_of_stock=itemView.findViewById(R.id.tv_out_of_stock);
            product_notifyme=itemView.findViewById(R.id.product_notifyme);
            brand_title=itemView.findViewById(R.id.brand_title);

        }

    }


    //********** Adds the Product to User's Cart *********//

    private void addProductToCart(ProductDetails product) {

        CartProduct cartProduct = new CartProduct();

        double productBasePrice, productFinalPrice, attributesPrice = 0;
        List<CartProductAttributes> selectedAttributesList = new ArrayList<>();


        // Check Discount on Product with the help of static method of Helper class
        final String discount = Utilities.checkDiscount(product.getProductsPrice(), product.getDiscountPrice());

        // Get Product's Price based on Discount
        if (discount != null) {
            product.setIsSaleProduct("1");
            productBasePrice = Double.parseDouble(product.getDiscountPrice());
        } else {
            product.setIsSaleProduct("0");
            productBasePrice = Double.parseDouble(product.getProductsPrice());
        }


        // Get Default Attributes from AttributesList
        for (int i = 0; i < product.getAttributes().size(); i++) {

//            CartProductAttributes productAttribute = new CartProductAttributes();

//            // Get Name and First Value of current Attribute
//            Option option = product.getAttributes().get(i).getOption();
//            Value value = product.getAttributes().get(i).getValues().get(0);
//
//
//            // Add the Attribute's Value Price to the attributePrices
//            String attrPrice = value.getPricePrefix() + value.getPrice();
//            attributesPrice += Double.parseDouble(attrPrice);


            // Add Value to new List
//            List<Value> valuesList = new ArrayList<>();
//            valuesList.add(value);
//
//
//            // Set the Name and Value of Attribute
//            productAttribute.setOption(option);
//            productAttribute.setValues(valuesList);
//
//
//            // Add current Attribute to selectedAttributesList
//            selectedAttributesList.add(i, productAttribute);
        }


        // Add Attributes Price to Product's Final Price
        productFinalPrice = productBasePrice;


        // Set Product's Price and Quantity
        product.setCustomersBasketQuantity(1);
        product.setProductsPrice(String.valueOf(productBasePrice));
//        product.setAttributesPrice(String.valueOf(attributesPrice));
        product.setProductsFinalPrice(String.valueOf(productFinalPrice));
        product.setTotalPrice(String.valueOf(productFinalPrice));

        // Set Customer's Basket Product and selected Attributes Info
        cartProduct.setCustomersBasketProduct(product);
        cartProduct.setCustomersBasketProductAttributes(selectedAttributesList);
        cartProduct.setCelebrityId(CelebrityId);

        // Add the Product to User's Cart with the help of static method of My_Cart class
        My_Cart.AddCartItem
                (
                        cartProduct
                );


        // Recreate the OptionsMenu
        ((MainActivity) context).invalidateOptionsMenu();

    }

    public void addToCartDialog(Activity activity) {


        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        View dialogView = activity.getLayoutInflater().inflate(R.layout.add_to_cart_dialog, null);
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
                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();

                My_Cart fragment = new My_Cart();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(context.getResources().getString(R.string.actionHome)).commit();
            }
        });


        alertDialog.show();


    }


}


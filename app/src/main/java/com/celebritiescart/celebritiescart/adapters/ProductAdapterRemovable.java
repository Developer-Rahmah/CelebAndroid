package com.celebritiescart.celebritiescart.adapters;


import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.celebritiescart.celebritiescart.models.product_model.CustomAttribute;
import com.celebritiescart.celebritiescart.models.product_model.ProductDetails;
import com.celebritiescart.celebritiescart.utils.Utilities;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.databases.User_Recents_DB;
import com.celebritiescart.celebritiescart.fragments.Product_Description;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.celebritiescart.celebritiescart.constant.ConstantValues.ECOMMERCE_PRODUCT_IMAGEURL;


/**
 * ProductAdapterRemovable is the adapter class of RecyclerView holding List of Products in RecentlyViewed and WishList
 **/
public class ProductAdapterRemovable extends RecyclerView.Adapter<ProductAdapterRemovable.MyViewHolder> {

    private Context context;

    private String customerID;
    private TextView emptyText;

    private boolean isRecents;
    private boolean isHorizontal;

    private List<ProductDetails> productList;

    private User_Recents_DB recents_db;
    private String isNew="";


    public ProductAdapterRemovable(Context context, List<ProductDetails> productList, boolean isHorizontal, boolean isRecents, TextView emptyText) {
        this.context = context;
        this.productList = productList;
        this.isHorizontal = isHorizontal;
        this.isRecents = isRecents;
        this.emptyText = emptyText;

        recents_db = new User_Recents_DB();
        customerID = this.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getString("userID", "");
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = null;

        if (isHorizontal) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_grid_sm, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_grid_lg, parent, false);
        }

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        // Get the data model based on Position
        final ProductDetails product = productList.get(position);

        holder.product_checked.setVisibility(View.GONE);


        // Set Product Image on ImageView with Glide Library
/*        Glide.with(context)
                .load(product.getProductsImage())
                .into(holder.product_thumbnail);*/
        Glide.with(context)
                .load(ECOMMERCE_PRODUCT_IMAGEURL+product.image)
                .into(holder.product_thumbnail);


        holder.product_title.setText(product.getProductsName());
        holder.product_price_old.setPaintFlags(holder.product_price_old.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);


        // Calculate the Discount on Product with static method of Helper class
        String discount = Utilities.checkDiscount(product.getProductsPrice(), product.getDiscountPrice());

        if (discount != null) {
            // Set Product's Price
            holder.product_price_old.setVisibility(View.VISIBLE);
            holder.product_price_old.setText( product.getProductsPrice()+ConstantValues.CURRENCY_SYMBOL );
            holder.product_price_new.setText( product.getDiscountPrice()+ConstantValues.CURRENCY_SYMBOL );
            holder.product_price_old.setTypeface(Typeface.createFromAsset(
                    this.context.getAssets(),
                    "font/baskvill_regular.OTF"
            ));

            holder.product_price_new.setTypeface(Typeface.createFromAsset(
                    this.context.getAssets(),
                    "font/baskvill_regular.OTF"
            ));


            //    holder.product_tag_new.setVisibility(View.GONE);
            //  holder.product_tag_new_text.setVisibility(View.GONE);

            // Set Discount Tag and its Text
            holder.product_tag_discount.setVisibility(View.VISIBLE);
            holder.product_tag_discount_text.setVisibility(View.VISIBLE);
            holder.product_tag_discount_text.setText(discount);

        } else {

            // Check if the Product is Newly Added with the help of static method of Helper class
           /* if (Utilities.checkNewProduct(product.getProductsDateAdded())) {
                // Set New Tag and its Text
                holder.product_tag_new.setVisibility(View.VISIBLE);
                holder.product_tag_new_text.setVisibility(View.VISIBLE);
            } else {
                holder.product_tag_new.setVisibility(View.GONE);
                holder.product_tag_new_text.setVisibility(View.GONE);
            }*/

            // Hide Discount Text and Set Product's Price
            holder.product_tag_discount.setVisibility(View.GONE);
            holder.product_tag_discount_text.setVisibility(View.GONE);
            holder.product_price_old.setVisibility(View.GONE);
            holder.product_price_new.setText( product.getProductsPrice() + ConstantValues.CURRENCY_SYMBOL);

            holder.product_price_new.setTypeface(Typeface.createFromAsset(
                    this.context.getAssets(),
                    "font/baskvill_regular.OTF"
            ));
        }

        if (product.customAttributes != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    isNew = product.customAttributes.stream().filter(Objects::nonNull)
                            .filter(p -> p.getAttributeCode().equals("is_new") || p.getAttributeCode().equals("is_new")).collect(Collectors.toList()).get(0).getValue();

                } catch (Exception e) {

                }

            } else {

                for (CustomAttribute person : product.customAttributes) {
                    if (person.getAttributeCode().equals("is_new")) {
                        isNew = person.getValue();

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


        // Handle the Click event of product_thumbnail ImageView
        holder.product_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Product Info
                Bundle itemInfo = new Bundle();
                itemInfo.putParcelable("productDetails", product);

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


        holder.product_remove_btn.setVisibility(View.VISIBLE);

        // Handle Click event of product_remove_btn Button
        holder.product_remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if Product is in User's Recents
                if (isRecents) {
                    // Delete Product from User_Recents_DB Local Database
                    recents_db.deleteRecentItem(product.getProductsId());

                } else {
                    // Unlike the Product for the User with the static method of Product_Description
                    Product_Description.UnlikeProduct(product.getWishlist_item_id(), customerID, context, view);
                }

                // Remove Product from productList List
                productList.remove(holder.getAdapterPosition());

                notifyItemRemoved(holder.getAdapterPosition());

                // Update View
                updateView(isRecents);
            }
        });

    }



    //********** Returns the total number of items in the RecyclerView *********//

    @Override
    public int getItemCount() {
        return productList.size();
    }



    //********** Change the Layout View based on the total number of items in the RecyclerView *********//

    public void updateView(boolean isRecentProducts) {


        // Check if Product is in User's Recents
        if (isRecentProducts) {

            // Check if RecyclerView has some Items
            if (getItemCount() != 0) {
                emptyText.setVisibility(View.VISIBLE);
            } else {
                emptyText.setVisibility(View.GONE);
            }

        } else {

            // Check if RecyclerView has some Items
            if (getItemCount() != 0) {
                emptyText.setVisibility(View.GONE);
            } else {
                emptyText.setVisibility(View.VISIBLE);
            }
        }
        emptyText.setVisibility(View.GONE);


        notifyDataSetChanged();

    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ProgressBar cover_loader;
        ImageView product_checked;
        Button product_remove_btn;
        ToggleButton product_like_btn;
        ImageView product_thumbnail, product_tag_new, product_tag_discount;
        TextView product_title, product_price_old, product_price_new, product_tag_new_text, product_tag_discount_text;

        public MyViewHolder(final View itemView) {
            super(itemView);

            product_checked = itemView.findViewById(R.id.product_checked);
            cover_loader = itemView.findViewById(R.id.product_cover_loader);

            product_remove_btn = itemView.findViewById(R.id.product_card_Btn);
            product_like_btn = itemView.findViewById(R.id.product_like_btn);
            product_title = itemView.findViewById(R.id.product_title);
            product_price_old = itemView.findViewById(R.id.product_price_old);
            product_price_new = itemView.findViewById(R.id.product_price_new);
            product_thumbnail = itemView.findViewById(R.id.product_cover);
            product_tag_new = itemView.findViewById(R.id.product_tag_new);
            product_tag_new_text = itemView.findViewById(R.id.product_tag_new_text);
            product_tag_discount = itemView.findViewById(R.id.product_tag_discount);
            product_tag_discount_text = itemView.findViewById(R.id.product_tag_discount_text);

            product_like_btn.setVisibility(View.GONE);
            product_remove_btn.setText(context.getString(R.string.removeProduct));
            product_remove_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_red));
        }
    }

}


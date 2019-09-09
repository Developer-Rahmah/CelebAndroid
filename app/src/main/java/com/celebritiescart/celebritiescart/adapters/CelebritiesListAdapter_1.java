package com.celebritiescart.celebritiescart.adapters;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.celebritiescart.celebritiescart.models.category_model.CategoryDetails;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.fragments.CelebrititesFragment;

import java.util.List;


/**
 * CategoryListAdapter is the adapter class of RecyclerView holding List of Categories in MainCategories
 **/

public class CelebritiesListAdapter_1 extends RecyclerView.Adapter<CelebritiesListAdapter_1.MyViewHolder> {

    boolean isSubCategory;
    int mViewWidth = 3;
    Context context;
    List<CategoryDetails> categoriesList;

    public CelebritiesListAdapter_1(Context context) {

    }



    public CelebritiesListAdapter_1(Context context, List<CategoryDetails> categoriesList, boolean isSubCategory) {
        this.context = context;
        this.isSubCategory = isSubCategory;
        this.categoriesList = categoriesList;
//        mViewWidth = defineViewWidth(context);



    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_celebrities_1, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final CategoryDetails categoryDetails = categoriesList.get(position);

        // Set Category Image on ImageView with Glide Library
        Glide.with(context)
                .load(categoryDetails.getImage())
                .apply(new RequestOptions().centerCrop()
                        .placeholder(R.drawable.placeholder).error(R.drawable.placeholder))
                .into(holder.category_image);
//        Glide.with(context)
//                .load(ConstantValues.ECOMMERCE_URL+categoryDetails.getImage())
//                .error(R.drawable.placeholder)
//                .into(holder.category_image);


        holder.category_title.setText(categoryDetails.getName());
        holder.category_products.setText(categoryDetails.getTotalProducts() + " "+ context.getString(R.string.products));

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    
        LinearLayout category_card;
        ImageView category_image;
        TextView category_title, category_products;


        public MyViewHolder(final View itemView) {
            super(itemView);
            category_card = itemView.findViewById(R.id.category_card);
            category_image = itemView.findViewById(R.id.category_image);
            category_title = itemView.findViewById(R.id.category_title);
            category_products = itemView.findViewById(R.id.category_products);

            category_card.setOnClickListener(this);
        }


        // Handle Click Listener on Category item
        @Override
        public void onClick(View view) {
            // Get Category Info
            Bundle categoryInfo = new Bundle();
            categoryInfo.putInt("CategoryID", Integer.parseInt(categoriesList.get(getAdapterPosition()).getId()));
            categoryInfo.putString("CategoryName", categoriesList.get(getAdapterPosition()).getName());
            categoryInfo.putString("CategorySlug", categoriesList.get(getAdapterPosition()).getSlug());
            categoryInfo.putString("CategoryImage", categoriesList.get(getAdapterPosition()).getImage());

            Fragment fragment = new CelebrititesFragment();

//            if (isSubCategory) {
//                // Navigate to Products Fragment
//                fragment = new Products();
//            } else {
//                // Navigate to SubCategories Fragment
//                fragment = new SubCategories_1();
//            }

            fragment.setArguments(categoryInfo);
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        }
    }

}


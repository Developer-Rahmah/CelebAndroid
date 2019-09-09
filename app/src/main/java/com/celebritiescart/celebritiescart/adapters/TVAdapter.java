package com.celebritiescart.celebritiescart.adapters;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.celebritiescart.celebritiescart.models.videos_model.Post;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.databases.User_Recents_DB;
import com.celebritiescart.celebritiescart.fragments.TV_Profile;

import java.util.List;


/**
 * ProductAdapter is the adapter class of RecyclerView holding List of Products in All_Products and other Product relevant Classes
 **/

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.MyViewHolder> {

    private Context context;
    private String customerID;

    private User_Recents_DB recents_db;
    private List<Post> videoList;


    public TVAdapter(Context context, List<Post> videoList, Boolean isHorizontal) {
        this.context = context;
        this.videoList = videoList;

        recents_db = new User_Recents_DB();
        customerID = this.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getString("userID", "");
    }


    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = null;

        // Check which Layout will be Inflated
//        if (isHorizontal) {
//            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_grid_sm, parent, false);
//        }
//        else {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_tv_grid_lg, parent, false);
//        }


        // Return a new holder instance
        return new MyViewHolder(itemView);
    }


    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if (position != videoList.size()) {

            // Get the data model based on Position
            final Post product = videoList.get(position);


            // Set Product Image on ImageView with Glide Library
            Glide.with(context)
                    .load(product.getFeaturedImg()).apply(new RequestOptions().centerCrop()
                    .placeholder(R.drawable.placeholder).error(R.drawable.placeholder))
                    .into(holder.product_thumbnail);


            holder.product_title.setText(product.getTitle());


            // Handle the Click event of product_thumbnail ImageView
            holder.video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Get Product Info
                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails", product);

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new TV_Profile();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(byIdName(context, R.string.actionVideos)).commit();


                    // Add the Product to User's Recently Viewed Products
//                    if (!recents_db.getUserRecents().contains(product.getProductsId())) {
//                        recents_db.insertRecentItem(product.getProductsId());
//                    }
                }
            });
            holder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Get Product Info
                    Bundle itemInfo = new Bundle();
                    itemInfo.putParcelable("productDetails", product);

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new TV_Profile();
                    fragment.setArguments(itemInfo);
                    MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(byIdName(context, R.string.actionVideos)).commit();


                    // Add the Product to User's Recently Viewed Products
//                    if (!recents_db.getUserRecents().contains(product.getProductsId())) {
//                        recents_db.insertRecentItem(product.getProductsId());
//                    }
                }
            });


            // Handle the Click event of product_checked ImageView


            // Check the Button's Visibility


        }

    }


    //********** Returns the total number of items in the data set *********//
    private static String byIdName(Context context, int name) {
        Resources res = context.getResources();
        return res.getString(name);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    //********** Toggles the RecyclerView LayoutManager *********//

    public void toggleLayout(Boolean isGridView) {
    }


    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ProgressBar cover_loader;
        ImageView product_thumbnail;
        ImageView play;
        View video;
        TextView product_title;


        public MyViewHolder(final View itemView) {
            super(itemView);

            cover_loader = itemView.findViewById(R.id.product_cover_loader);

            product_title = itemView.findViewById(R.id.product_title);

            product_thumbnail = itemView.findViewById(R.id.product_cover);
            video = itemView.findViewById(R.id.video);
            play = itemView.findViewById(R.id.play);

        }

    }


}


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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.celebritiescart.celebritiescart.models.search_model.SearchResults;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.fragments.Product_Description;
import com.celebritiescart.celebritiescart.fragments.Products;

import java.util.ArrayList;
import java.util.List;


/**
 * SearchResultsAdapter is the adapter class of RecyclerView holding List of Search Results in SearchFragment
 **/

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<SearchResults> searchResultsList;

    private List<SearchResults> filteredData = null;
    private ItemFilter mFilter = new ItemFilter();



    private String actionCategory = "";

    public SearchResultsAdapter(Context context, List<SearchResults> searchResultsList, String action) {
        this.context = context;
        this.searchResultsList = searchResultsList;
        this.filteredData = searchResultsList ;
//        if (action.equals(context.getString(R.string.actionBrands))) {
//            actionCategory = context.getString(R.string.brand_category_name);
//        } else if (action.equals(context.getString(R.string.celebrities))) {
//            actionCategory = context.getString(R.string.celebrity_category_name);
//        } else {
//            actionCategory = context.getString(R.string.fashion_category_name);
//        }

    }


    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_results, parent, false);

        return new MyViewHolder(itemView);
    }
    public Object getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    //********** Called by RecyclerView to display the Data at the specified Position *********//
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final SearchResults searchResult = filteredData.get(position);

        // Get the Type of SearchItem (i.e Product, SubCategory)
        final String parent = searchResult.getParent();


        holder.search_result_title.setText(searchResult.getName());

        // Set Item Image on ImageView with Glide Library
        Glide
                .with(context)
                .load(searchResult.getImage())
                .into(holder.search_result_cover);


        // Handle the SearchItem Click Event
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check the Type of SearchItem (i.e Product, Categories) to navigate to relevant Fragment
                if (parent.equalsIgnoreCase("Categories")) {

                    // Get SubCategory Info
                    Bundle categoryInfo = new Bundle();
                    categoryInfo.putInt("CategoryID", searchResult.getId());
                    categoryInfo.putString("CategoryName", searchResult.getName());
                    categoryInfo.putString("MainCategory", searchResult.getActionName());

                    // Navigate to Products of selected SubCategory
                    Fragment fragment = new Products();
                    fragment.setArguments(categoryInfo);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();


                } else if (parent.equalsIgnoreCase("Products")) {
                    // Get Product Info
                    Bundle itemInfo = new Bundle();
                    itemInfo.putInt("itemID", searchResult.getId());

                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new Product_Description();
                    fragment.setArguments(itemInfo);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                }
            }
        });

    }


    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<SearchResults> list = searchResultsList;

            int count = list.size();
            final ArrayList<SearchResults> nlist = new ArrayList<>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getName();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<SearchResults>) results.values;
            notifyDataSetChanged();
        }

    }

    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView search_result_title;
        ImageView search_result_cover;


        public MyViewHolder(final View itemView) {
            super(itemView);

            search_result_title = itemView.findViewById(R.id.search_result_title);
            search_result_cover = itemView.findViewById(R.id.search_result_cover);
        }
    }
}


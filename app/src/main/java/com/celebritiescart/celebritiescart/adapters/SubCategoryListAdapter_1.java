package com.celebritiescart.celebritiescart.adapters;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.fragments.Products;
import com.celebritiescart.celebritiescart.models.category_model.CategoryDetails;
import com.celebritiescart.celebritiescart.models.subcategory.SubCategoryModel;

import java.util.ArrayList;
import java.util.List;


/**
 * CategoryListAdapter is the adapter class of RecyclerView holding List of Categories in MainCategories
 **/

public class SubCategoryListAdapter_1 extends RecyclerView.Adapter<SubCategoryListAdapter_1.MyViewHolder> {

    boolean isSubCategory;

    Context context;
    List<CategoryDetails> categoriesList;
    private ArrayList<Integer> selectedCategoriesIDs;
    private String CategoryName;
    private String CategoryImage;
    SubtoSubCategoryListAdapter_1 subtosubCategoryListAdapter_1;
    ArrayList<Integer> al_categoryId;
    int postion;
    int subCategoryPosition;
    boolean isSameDropDown=false;
    ArrayList<Integer> cateogrylistIds=new ArrayList<>();
    int parentId;
    public SubCategoryListAdapter_1(Context context, List<CategoryDetails> categoriesList, boolean isSubCategory, ArrayList<Integer> categoryIDs, String categoryName, String CategoryImage, int position,int parentId) {

        this.context = context;
        this.isSubCategory = isSubCategory;
        this.categoriesList = categoriesList;
        selectedCategoriesIDs = categoryIDs;
        CategoryName = categoryName;
        this.CategoryImage = CategoryImage;
        this.postion = position;
        this.parentId=parentId;

    }


    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_subcategories, parent, false);

        return new MyViewHolder(itemView);
    }


    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final SubCategoryModel categoryDetails = categoriesList.get(postion).subCategoryModels.get(position);
        al_categoryId = new ArrayList<>();
        holder.tv_item_name.setText(categoryDetails.name);
        holder.tv_item_name.setTypeface(Typeface.DEFAULT_BOLD);

        if(subCategoryPosition==position){
            if (isSameDropDown){
                holder.rv_sub_tosub_category.setVisibility(View.GONE);

                subCategoryPosition=-1;

            }else {
                holder.rv_sub_tosub_category.setVisibility(View.VISIBLE);

            }
        }else {
            holder.rv_sub_tosub_category.setVisibility(View.GONE);
        }


        if (categoryDetails.subcategories.size() != 0) {
            holder.iv_drop_down.setVisibility(View.VISIBLE);
            for (int i = 0; i < categoryDetails.subcategories.size(); i++) {
                al_categoryId.add(Integer.valueOf(categoryDetails.subcategories.get(i).id));
            }
            subtosubCategoryListAdapter_1 = new SubtoSubCategoryListAdapter_1(context, categoryDetails.subcategories, true, al_categoryId, CategoryName, null, postion,subCategoryPosition,parentId);
            holder.rv_sub_tosub_category.setLayoutManager(new LinearLayoutManager(context));
            holder.rv_sub_tosub_category.setAdapter(subtosubCategoryListAdapter_1);
        } else {
            holder.iv_drop_down.setVisibility(View.GONE);
        }
    }


    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return categoriesList.get(postion).subCategoryModels.size();
    }


    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tv_item_name;
        RecyclerView rv_sub_tosub_category;
        ImageView iv_drop_down;



        public MyViewHolder(final View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            rv_sub_tosub_category = itemView.findViewById(R.id.rv_sub_tosub_category);
            iv_drop_down = itemView.findViewById(R.id.iv_drop_down);
            iv_drop_down.setOnClickListener(this);
            tv_item_name.setOnClickListener(this);
        }


        // Handle Click Listener on Category item
        @Override
        public void onClick(View view) {
            // Get Category Info
            switch (view.getId()) {

                case R.id.tv_item_name:
                    Bundle categoryInfo = new Bundle();
                    try {
                        if (!selectedCategoriesIDs.contains(Integer.parseInt(categoriesList.get(postion).subCategoryModels.get(getAdapterPosition()).id))) {
                            selectedCategoriesIDs.add(Integer.parseInt(categoriesList.get(postion).subCategoryModels.get(getAdapterPosition()).id));

                        }
                    } catch (Exception e) {

                    }
                    cateogrylistIds.add(parentId);
                    cateogrylistIds.addAll(selectedCategoriesIDs);
                    categoryInfo.putIntegerArrayList("CategoryIDs", cateogrylistIds);
                    categoryInfo.putString("CategoryName", categoriesList.get(postion).subCategoryModels.get(getAdapterPosition()).name);
                    categoryInfo.putString("isSubCategory", "true");
                    categoryInfo.putInt("position", postion);

                    categoryInfo.putString("CategoryNameShow", CategoryName);

                    Fragment fragment;
                    fragment = new Products();


                    fragment.setArguments(categoryInfo);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                    break;
                case R.id.iv_drop_down:
                    if(subCategoryPosition==getAdapterPosition()){
                        isSameDropDown=true;
                    }else {
                        isSameDropDown=false;
                    }

                    subCategoryPosition=getAdapterPosition();
                    notifyDataSetChanged();
                    break;
            }
        }
    }

}


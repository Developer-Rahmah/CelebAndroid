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
import android.widget.TextView;

import com.celebritiescart.celebritiescart.models.category_model.CategoryDetails;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.fragments.Products;
import com.celebritiescart.celebritiescart.models.subcategory.Subcategories;

import java.util.ArrayList;
import java.util.List;


/**
 * CategoryListAdapter is the adapter class of RecyclerView holding List of Categories in MainCategories
 **/

public class SubtoSubCategoryListAdapter_1 extends RecyclerView.Adapter<SubtoSubCategoryListAdapter_1.MyViewHolder> {

    boolean isSubCategory;

    Context context;
    List<Subcategories> categoriesList;
    private ArrayList<Integer> selectedCategoriesIDs;
    private String CategoryName;
    private String CategoryImage;
    private List<CategoryDetails> al_CategoryDetail;
    private int mainPosition;
    private int subCategoryPosition;
    private int parentId;
    private ArrayList<Integer> al_categoryIds=new ArrayList<>();

    public SubtoSubCategoryListAdapter_1(Context context, List<Subcategories> categoriesList, boolean isSubCategory, ArrayList<Integer> categoryIDs, String categoryName, String CategoryImage,int mainPosition,int subCategoryPosition,int parentId) {

        this.context = context;
        this.isSubCategory = isSubCategory;
        this.categoriesList = categoriesList;
        selectedCategoriesIDs = categoryIDs;
        CategoryName = categoryName;
        this.CategoryImage=CategoryImage;
        this.mainPosition=mainPosition;
        this.subCategoryPosition=subCategoryPosition;
        this.parentId=parentId;

    }


    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_subtosubcategories, parent, false);

        return new MyViewHolder(itemView);
    }


    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final Subcategories categoryDetails = categoriesList.get(position);

        holder.tv_item_name.setText(categoryDetails.name);

    }


    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }


    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tv_item_name;
        RecyclerView rv_sub_tosub_category;


        public MyViewHolder(final View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            rv_sub_tosub_category=itemView.findViewById(R.id.rv_sub_tosub_category);
            tv_item_name.setOnClickListener(this);
        }


        // Handle Click Listener on Category item
        @Override
        public void onClick(View view) {
            // Get Category Info
            Bundle categoryInfo = new Bundle();
            try {
                if (!selectedCategoriesIDs.contains(Integer.parseInt(categoriesList.get(getAdapterPosition()).id))) {
                    selectedCategoriesIDs.add(Integer.parseInt(categoriesList.get(getAdapterPosition()).id));

                }
            } catch (Exception e) {

            }

         /*   al_CategoryDetail=new ArrayList<>();
            for (int i = 0; i < categoriesList.size(); i++) {
                CategoryDetails details=new CategoryDetails();
                details.setId(categoriesList.get(i).id);
                details.setName(categoriesList.get(i).name);
                details.setParentId(categoriesList.get(i).parent_id);
                al_CategoryDetail.add(details);

            }
            ((App) getContext().getApplicationContext()).setCategoriesList(al_CategoryDetail);
*/
            al_categoryIds.add(parentId);
            al_categoryIds.addAll(selectedCategoriesIDs);

            categoryInfo.putIntegerArrayList("CategoryIDs", al_categoryIds);
            categoryInfo.putString("CategoryName", categoriesList.get(getAdapterPosition()).name);
            categoryInfo.putString("isSubtoSubCategory","true");
            categoryInfo.putInt("position",mainPosition);
            categoryInfo.putInt("subposition",subCategoryPosition);
            categoryInfo.putString("CategoryNameShow", CategoryName);

            Fragment fragment;
            fragment = new Products();


            fragment.setArguments(categoryInfo);
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        }
    }

}


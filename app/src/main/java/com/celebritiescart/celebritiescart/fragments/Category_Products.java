package com.celebritiescart.celebritiescart.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.celebritiescart.celebritiescart.adapters.ProductAdapter;
import com.celebritiescart.celebritiescart.adapters.ViewPagerCustomAdapter;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.EndlessRecyclerViewScroll;
import com.celebritiescart.celebritiescart.customs.FilterDialog;
import com.celebritiescart.celebritiescart.models.filter_model.get_filters.FilterData;
import com.celebritiescart.celebritiescart.models.filter_model.get_filters.FilterDetails;
import com.celebritiescart.celebritiescart.models.filter_model.post_filters.PostFilterData;
import com.celebritiescart.celebritiescart.models.product_model.ProductData;
import com.celebritiescart.celebritiescart.models.product_model.ProductDetails;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class Category_Products extends Fragment {
    String  isSubtoSubCategory ="false";
            View rootView;
    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
    SharedPreferences.Editor editor = pref.edit();
    int pageNo = 1;
    double maxPrice = 0;
    boolean isVisible;
    boolean isGridView;
    boolean isFilterApplied;
    LinearLayout layoutEndOfList;
    private TextView tvEndOfListMsg;
    int CategoryID;
    private String CelebrityID;
    private String BrandID;
    String customerID;
    String sortBy = "created_at";
    ViewPagerCustomAdapter viewPagerCustomAdapter;
    LinearLayout bottomBar;
    LinearLayout sortList;
    TextView emptyRecord;
    TextView sortListText;
    Button resetFiltersBtn;
    ProgressBar progressBar;
    ToggleButton filterButton;
    ToggleButton toggleLayoutView;
    RecyclerView category_products_recycler;

    LoadMoreTask loadMoreTask;
    FilterDialog filterDialog;
    PostFilterData filters = null;

    ProductAdapter productAdapter;
    //    List<ProductDetails> categoryProductsList;
    List<ProductDetails>  categoryProductsList = new ArrayList<>();

    List<FilterDetails> filtersList = new ArrayList<>();

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    private int totalPages = 1;
    private int visibleThreshold = 16;
    private ArrayList<Integer> CategoryIDs = new ArrayList<>();
    private String direction;
    private ProgressBar loading_bar_first;
    private String celebrity="";
    private String celebrityId="";
    String customerMainId="";


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null) {
            isVisible = true;
            // fetchdata() contains logic to show data when page is selected mostly asynctask to fill the data
            if (!CategoryIDs.isEmpty()) {
                categoryProductsList.clear();
                RequestCategoryProducts(pageNo, sortBy, direction);

            }

            //

        } else {
//            RequestCategoryProducts(pageNo, sortBy, direction);
            if (!CategoryIDs.isEmpty()) {
                categoryProductsList.clear();
//                if(loading_bar_first.getVisibility()==View.VISIBLE){
//                    loading_bar_first.setVisibility(View.INVISIBLE);
//
//                }
            }
            //RequestCategoryProducts(pageNo, sortBy, direction);
            isVisible = false;
        }

//        isVisible = isVisibleToUser;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_products_vertical, container, false);


        // Get CategoryID from bundle arguments

        if (getArguments() != null) {
            if (getArguments().containsKey("CategoryID")) {
                CategoryID = getArguments().getInt("CategoryID");

            } else if (getArguments().containsKey("CategoryIDs")) {
                CategoryIDs.addAll(getArguments().getIntegerArrayList("CategoryIDs"));
            }
            // Get sortBy from bundle arguments
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy");
            }
            if (getArguments().containsKey("celebrity")) {

                celebrity = getArguments().getString("celebrity");
                if (celebrity.equalsIgnoreCase("celebrity")) {
                    celebrityId = getArguments().getString("celebrityId");
                }


            }

        }

        // Get the Customer's ID from SharedPreferences
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        customerMainId = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("customerID", "");


        // Binding Layout Views
        bottomBar = rootView.findViewById(R.id.bottomBar);
        sortList = rootView.findViewById(R.id.sort_list);
        sortListText = rootView.findViewById(R.id.sort_text);
        emptyRecord = rootView.findViewById(R.id.empty_record);
        progressBar = rootView.findViewById(R.id.loading_bar);
        loading_bar_first = rootView.findViewById(R.id.loading_bar_first);
        resetFiltersBtn = rootView.findViewById(R.id.resetFiltersBtn);
        filterButton = rootView.findViewById(R.id.filterBtn);
        toggleLayoutView = rootView.findViewById(R.id.layout_toggleBtn);
        category_products_recycler = rootView.findViewById(R.id.products_recycler);
        layoutEndOfList = rootView.findViewById(R.id.layoutEndOfList);
        tvEndOfListMsg = rootView.findViewById(R.id.tvEndOfListMsg);

        // Hide some of the Views
//        progressBar.setVisibility(View.GONE);
        emptyRecord.setVisibility(View.GONE);
        resetFiltersBtn.setVisibility(View.GONE);
        layoutEndOfList.setVisibility(View.INVISIBLE);
        tvEndOfListMsg.setVisibility(View.INVISIBLE);

        isGridView = true;
        isFilterApplied = false;
        filterButton.setChecked(isFilterApplied);
        toggleLayoutView.setChecked(isGridView);


        // Set sortListText text
        if (sortBy.equalsIgnoreCase("top seller")) {
            sortListText.setText(getString(R.string.top_seller));
        } else if (sortBy.equalsIgnoreCase("special")) {
            sortListText.setText(getString(R.string.super_deals));
        } else if (sortBy.equalsIgnoreCase("most liked")) {
            sortListText.setText(getString(R.string.most_liked));
        } else {
            sortListText.setText(getString(R.string.newest));
        }
        loading_bar_first.setVisibility(View.VISIBLE);

        // Initialize CategoryProductsList

        if (!CategoryIDs.isEmpty()) {
            categoryProductsList.clear();
        }//        if (!isVisible) {
//                RequestCategoryProducts(pageNo, sortBy, direction);
//        }
//        productAdapter.notifyDataSetChanged();

        // Request for Products of given Category based on PageNo.
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                RequestCategoryProducts(pageNo, sortBy, direction);
//                CategoryIDs.clear();
//            }
//
//        }, 1000);


        //RequestAllProducts(pageNo,sortBy);

        // Request for Filters of given Category
//        RequestFilters(CategoryID);


        // Initialize GridLayoutManager and LinearLayoutManager
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getContext());


        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), categoryProductsList, false,celebrity,celebrityId);


        setRecyclerViewLayoutManager(isGridView);
        category_products_recycler.setAdapter(productAdapter);


        // Handle the Scroll event of Product's RecyclerView
        category_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar,layoutEndOfList) {
            @Override
            public void onLoadMore(final int current_page) {
                if (getContext() != null) {
                    //   Toast.makeText(getContext(), "" + current_page, Toast.LENGTH_SHORT).show();
                }
                if (!(pageNo >= totalPages)) {
                    progressBar.setVisibility(View.VISIBLE);
                    pageNo++;


                    if (isFilterApplied) {
                        // Initialize LoadMoreTask to Load More Products from Server against some Filters
                        loadMoreTask = new LoadMoreTask(pageNo, filters);
                    } else {
                        // Initialize LoadMoreTask to Load More Products from Server without Filters
                        loadMoreTask = new LoadMoreTask(pageNo, filters);
                    }

                    // Execute AsyncTask LoadMoreTask to Load More Products from Server
                    loadMoreTask.execute();
                }
                else{
                    layoutEndOfList.setVisibility(View.VISIBLE);
                    tvEndOfListMsg.setVisibility(View.VISIBLE);

                }
            }
        });

        productAdapter.notifyDataSetChanged();


        // Toggle RecyclerView's LayoutManager
        toggleLayoutView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isGridView = isChecked;
                setRecyclerViewLayoutManager(isGridView);
            }
        });


        // Initialize FilterDialog and Override its abstract methods
        filterDialog = new FilterDialog(getContext(), CategoryID, filtersList, maxPrice) {
            @Override
            public void clearFilters() {
                // Clear Filters
                isFilterApplied = false;
                filterButton.setChecked(false);
                filters = null;
                categoryProductsList.clear();
                pageNo = 1;
                new LoadMoreTask(pageNo, filters).execute();
            }

            @Override
            public void applyFilters(PostFilterData postFilterData) {
                // Apply Filters
                isFilterApplied = true;
                filterButton.setChecked(true);
                filters = postFilterData;
                categoryProductsList.clear();
                pageNo = 1;
                new LoadMoreTask(pageNo, filters).execute();
            }
        };


        // Handle the Click event of Filter Button
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isFilterApplied) {
                    filterButton.setChecked(true);
                    filterDialog.show();

                } else {
                    filterButton.setChecked(false);
                    filterDialog = new FilterDialog(getContext(), CategoryID, filtersList, maxPrice) {
                        @Override
                        public void clearFilters() {
                            isFilterApplied = false;
                            filterButton.setChecked(false);
                            filters = null;
                            categoryProductsList.clear();
                            pageNo = 1;
                            new LoadMoreTask(pageNo, filters).execute();
                        }

                        @Override
                        public void applyFilters(PostFilterData postFilterData) {
                            isFilterApplied = true;
                            filterButton.setChecked(true);
                            filters = postFilterData;
                            categoryProductsList.clear();
                            pageNo = 1;
                            new LoadMoreTask(pageNo, filters).execute();
                        }
                    };
                    filterDialog.show();
                }
            }
        });


        sortList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] sortArray = getResources().getStringArray(R.array.sortBy_array);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(true);

                dialog.setItems(sortArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String selectedText = sortArray[which];
                        sortListText.setText(selectedText);


                        if (selectedText.equalsIgnoreCase(sortArray[0])) {
                            sortBy = "created_at";
                            direction = "DESC";
                        } else if (selectedText.equalsIgnoreCase(sortArray[1])) {
                            sortBy = "name";
                            direction = "ASC";
                        } else if (selectedText.equalsIgnoreCase(sortArray[2])) {
                            sortBy = "name";
                            direction = "DESC";
                        } else if (selectedText.equalsIgnoreCase(sortArray[3])) {
                            sortBy = "high to low";
                            sortBy = "price";
                            direction = "DESC";
                        } else if (selectedText.equalsIgnoreCase(sortArray[4])) {
                            sortBy = "low to high";
                            sortBy = "price";
                            direction = "ASC";
                        } else {
                            sortBy = "created_at";
                            direction = "DESC";
                        }
                        pageNo = 1;

                        categoryProductsList.clear();
                        loading_bar_first.setVisibility(View.VISIBLE);
                        if (isFilterApplied) {
                            // Initialize LoadMoreTask to Load More Products from Server against some Filters
                            RequestFilteredProducts(pageNo, sortBy, filters, direction);

                        } else {
                            // Initialize LoadMoreTask to Load More Products from Server without Filters
                            RequestCategoryProducts(pageNo, sortBy, direction);
                        }
                        dialog.dismiss();


                        // Handle the Scroll event of Product's RecyclerView
                        category_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar,layoutEndOfList) {
                            @Override
                            public void onLoadMore(final int current_page) {


                                if (!(pageNo >= totalPages)) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    pageNo++;
                                    if (isFilterApplied) {
                                        // Initialize LoadMoreTask to Load More Products from Server against some Filters
                                        loadMoreTask = new LoadMoreTask(pageNo, filters);
                                    } else {
                                        // Initialize LoadMoreTask to Load More Products from Server without Filters
                                        loadMoreTask = new LoadMoreTask(pageNo, filters);
                                    }

                                    // Execute AsyncTask LoadMoreTask to Load More Products from Server
                                    loadMoreTask.execute();
                                }
                                else{
                                    layoutEndOfList.setVisibility(View.VISIBLE);
                                    tvEndOfListMsg.setVisibility(View.VISIBLE);

                                }
                            }
                        });

                    }
                });
                dialog.show();
            }
        });


        resetFiltersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFilterApplied = false;
                filterButton.setChecked(false);
                filters = null;
//                categoryProductsList.clear();
                pageNo = 1;
                new LoadMoreTask(pageNo, filters).execute();
            }
        });


        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!CategoryIDs.isEmpty()) {
//            categoryProductsList.clear();
        }




        }

    @Override
    public void onResume() {
        super.onResume();
//        isSubtoSubCategory = getArguments().getString("isSubtoSubCategory", "");
if(!(getArguments().containsKey("CategoryName"))&&CategoryIDs.isEmpty()){
    if(loading_bar_first.getVisibility()==View.VISIBLE){

        loading_bar_first.setVisibility(View.INVISIBLE);

    }
}else{
    System.out.print(getArguments().getString("isSubtoSubCategory"));
}

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!CategoryIDs.isEmpty()) {
            categoryProductsList.clear();
            pageNo=1;
            totalPages=1;

        }        productAdapter.notifyDataSetChanged();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                RequestCategoryProducts(pageNo, sortBy, direction);
//                CategoryIDs.clear();
//            }
//
//        }, 5000);
        if (!isVisible) {
            isSubtoSubCategory = getArguments().getString("isSubtoSubCategory", "");

//            if (!CategoryIDs.isEmpty()) {
                categoryProductsList.clear();

//            }//

            RequestCategoryProducts(pageNo, sortBy, direction);
        }else{
if(isSubtoSubCategory=="true"){
    categoryProductsList.clear();
    RequestCategoryProducts(pageNo, sortBy, direction);

//    loading_bar_first.setVisibility(View.INVISIBLE);

//            }//
//    RequestCategoryProducts(pageNo, sortBy, direction);
}
        }

    }

//*********** Switch RecyclerView's LayoutManager ********//

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


    //*********** Adds Products returned from the Server to the CategoryProductsList ********//

    private void addCategoryProducts(ProductData productData) {
        if (!CategoryIDs.isEmpty()) {
//            categoryProductsList.clear();
        }        // Add Products to CategoryProductsList from the List of ProductData
        categoryProductsList.addAll(productData.getProductData());

        productAdapter.notifyDataSetChanged();


        // Change the Visibility of emptyRecord Text based on CategoryProductsList's Size
        if (productAdapter.getItemCount() == 0) {
            if (isFilterApplied) {
                resetFiltersBtn.setVisibility(View.VISIBLE);
            }
            emptyRecord.setVisibility(View.VISIBLE);

        } else {
            emptyRecord.setVisibility(View.GONE);
            resetFiltersBtn.setVisibility(View.GONE);
        }
    }


    //*********** Request Products of given Category from the Server based on PageNo. ********//



    private void addProducts(ProductData productData) {

        // Add Products to ProductsList from the List of ProductData
        for (int i = 0; i < productData.getProductData().size(); i++) {
            ProductDetails productDetails = productData.getProductData().get(i);

            categoryProductsList.add(productDetails);
        }

        productAdapter.notifyDataSetChanged();


        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (productAdapter.getItemCount() == 0) {
            emptyRecord.setVisibility(View.VISIBLE);
        } else {
            emptyRecord.setVisibility(View.GONE);
        }

    }



    public void RequestAllProducts(int pageNumber, String sortBy) {
        String test = String.valueOf(getArguments().getInt("CategoryID"));

        Call<ProductData> call;

        if (test != null) {


            call = APIClient.getInstance()
                    .getAllProducts(ConstantValues.AUTHORIZATION, "category_id", test, "eq", sortBy, direction, String.valueOf(visibleThreshold), String.valueOf(pageNumber),CelebrityID,customerMainId);
        } else {
//            call = APIClient.getInstance()
//                    .getAllProducts(ConstantValues.AUTHORIZATION, sortBy, direction, String.valueOf(visibleThreshold), String.valueOf(pageNumber),CelebrityID,customerMainId);
            call = APIClient.getInstance()
                    .getAllProducts(ConstantValues.AUTHORIZATION, "category_id", test, "eq", sortBy, direction, String.valueOf(visibleThreshold), String.valueOf(pageNumber),CelebrityID,customerMainId);

        }
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {

                if (loading_bar_first.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);

                }
                bottomBar.setVisibility(View.GONE);
                emptyRecord.setVisibility(View.GONE);


            });
        }
//        stopLoading = false;
        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
//                bottomBar.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {
                    if (!response.body().getProductData().isEmpty()) {
                        // Products have been returned. Add Products to the ProductsList
                        addProducts(response.body());
                        totalPages = (response.body().getTotalCount() + visibleThreshold - 1) / visibleThreshold;


                    } else if (response.body().getProductData().isEmpty()) {
//                        stopLoading = true;
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    emptyRecord.setVisibility(View.VISIBLE);


                                }
                            });

                        }
                    } else {
                        // Unable to get Success status
                        //    Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }

                    // Hide the ProgressBar


                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {


                            if (loading_bar_first.getVisibility() == View.VISIBLE) {
                                loading_bar_first.setVisibility(View.GONE);
                            } else if (progressBar.getVisibility() == View.VISIBLE) {
                                progressBar.setVisibility(View.GONE);
                            }
                            bottomBar.setVisibility(View.VISIBLE);


                        });

                    }

                } else {
                    if (getContext() != null) {

                        Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {


                        if (loading_bar_first.getVisibility() == View.VISIBLE) {
                            loading_bar_first.setVisibility(View.GONE);
                        } else if (progressBar.getVisibility() == View.VISIBLE) {
                            progressBar.setVisibility(View.GONE);
                        }
                        bottomBar.setVisibility(View.VISIBLE);


                    });

                }
                if (getContext() != null) {
                    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }






    public void updateList(List<ProductDetails> list){
        if(productAdapter != null) {
            productAdapter.updateList(list);
        }
    }




    public void RequestCategoryProducts(int pageNumber, String sortBy, String direction) {
//        loading_bar_first.setVisibility(View.VISIBLE);
        Products products=new Products();
        int t=pref.getInt("position", 1);
        int desc=pref.getInt("positionDesc", 1);

        if (!CategoryIDs.isEmpty()) {
//            categoryProductsList.clear();
        }
        Call<ProductData> call;
        if (!CategoryIDs.isEmpty()) {
//            if(t!=desc){
//                categoryProductsList.clear();
//
//            }else if(t== desc &&categoryProductsList.size()>0&&pageNo==1){
//                categoryProductsList.clear();
//
//            }else{
//                System.out.print(""+desc+""+""+t);
//            }

//            System.out.print(CategoryIDs);
//            System.out.print(CelebrityID);
//            Map<String, String> data = new HashMap<>();
//            int i = 0;
////            for (Integer obj : CategoryIDs) {
////                data.put("searchCriteria[filterGroups][" + i + "][filters][0][field]", "category_id");
////                data.put("searchCriteria[filterGroups][" + i + "][filters][0][value]", String.valueOf(obj));
////                data.put("searchCriteria[filterGroups][" + i + "][filters][0][conditionType]", "eq");
////                i++;
////            }
////for (int test=0;test<=CategoryIDs.size();test++) {
////            int t=pref.getInt("position", 1);             // getting Integer
//int t=pref.getInt("position", 1);
//
//
//
//            if(t==1) {
////                            for (Integer obj : CategoryIDs) {
////                data.put("searchCriteria[filterGroups][" + i + "][filters][0][field]", "category_id");
////                data.put("searchCriteria[filterGroups][" + i + "][filters][0][value]", String.valueOf(25));
////                data.put("searchCriteria[filterGroups][" + i + "][filters][0][conditionType]", "eq");
////                i++;
////            }
//
//
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][field]", "category_id");
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][value]", String.valueOf(25));
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][conditionType]", "eq");
//
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][field]", "category_id");
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][value]", String.valueOf(25));
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][conditionType]", "eq");
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][value]", String.valueOf(getArguments().getString("celebrityId")));
//
//                data.put("searchCriteria[sortOrders][0][field]", sortBy);
//            }else  if(t==2) {
////                for (Integer obj : CategoryIDs) {
////                    data.put("searchCriteria[filterGroups][" + i + "][filters][0][field]", "category_id");
////                    data.put("searchCriteria[filterGroups][" + i + "][filters][0][value]", String.valueOf(26));
////                    data.put("searchCriteria[filterGroups][" + i + "][filters][0][conditionType]", "eq");
////                    i++;
////                }
//
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][field]", "category_id");
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][value]", String.valueOf(26));
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][conditionType]", "eq");
//
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][field]", "category_id");
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][value]", String.valueOf(26));
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][conditionType]", "eq");
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][value]", String.valueOf(getArguments().getString("celebrityId")));
//
//                data.put("searchCriteria[sortOrders][0][field]", sortBy);
//            }else  if(t==3) {
//
////                for (Integer obj : CategoryIDs) {
////                    data.put("searchCriteria[filterGroups][" + i + "][filters][0][field]", "category_id");
////                    data.put("searchCriteria[filterGroups][" + i + "][filters][0][value]", String.valueOf(27));
////                    data.put("searchCriteria[filterGroups][" + i + "][filters][0][conditionType]", "eq");
////                    i++;
////                }
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][field]", "category_id");
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][value]", String.valueOf(27));
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][conditionType]", "eq");
//
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][field]", "category_id");
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][value]", String.valueOf(27));
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][conditionType]", "eq");
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][value]", String.valueOf(getArguments().getString("celebrityId")));
//
//                data.put("searchCriteria[sortOrders][0][field]", sortBy);
//            }else  if(t==4) {
////                for (Integer obj : CategoryIDs) {
////                    data.put("searchCriteria[filterGroups][" + i + "][filters][0][field]", "category_id");
////                    data.put("searchCriteria[filterGroups][" + i + "][filters][0][value]", String.valueOf(28));
////                    data.put("searchCriteria[filterGroups][" + i + "][filters][0][conditionType]", "eq");
////                    i++;
////                }
//
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][field]", "category_id");
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][value]", String.valueOf(28));
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][conditionType]", "eq");
//
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][field]", "category_id");
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][value]", String.valueOf(28));
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][conditionType]", "eq");
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][value]", String.valueOf(getArguments().getString("celebrityId")));
//
//                data.put("searchCriteria[sortOrders][0][field]", sortBy);
//            }else   if(t==5) {
//
////                for (Integer obj : CategoryIDs) {
////                    data.put("searchCriteria[filterGroups][" + i + "][filters][0][field]", "category_id");
////                    data.put("searchCriteria[filterGroups][" + i + "][filters][0][value]", String.valueOf(44));
////                    data.put("searchCriteria[filterGroups][" + i + "][filters][0][conditionType]", "eq");
////                    i++;
////                }
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][field]", "category_id");
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][value]", String.valueOf(44));
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][conditionType]", "eq");
//
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][field]", "category_id");
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][value]", String.valueOf(44));
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][conditionType]", "eq");
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][value]", String.valueOf(getArguments().getString("celebrityId")));
//
//                data.put("searchCriteria[sortOrders][0][field]", sortBy);
//            }else {
//                System.out.print(t);
//            }
//
//            //}
//            if (direction == null) {
//
//                data.put("searchCriteria[sortOrders][0][direction]", "DESC");
//
//            } else {
//
//                data.put("searchCriteria[sortOrders][0][direction]", direction);
//
//            }
//            data.put("searchCriteria[pageSize]", String.valueOf(visibleThreshold));
//            data.put("searchCriteria[currentPage]", String.valueOf(pageNumber));
//
//            call = APIClient.getInstance()
//                    .getAllProducts(ConstantValues.AUTHORIZATION, data,"",customerMainId);

            if (direction == null) {
                call = APIClient.getInstance()
                        .getAllProductsCeleb(ConstantValues.AUTHORIZATION, "category_id","category_id", String.valueOf(String.valueOf(getArguments().getString("celebrityId"))), String.valueOf(25), "eq","eq", sortBy, "DESC", String.valueOf(visibleThreshold), String.valueOf(pageNumber),String.valueOf(getArguments().getString("celebrityId")),customerMainId);
                if(t==1){
                    call = APIClient.getInstance()
                            .getAllProductsCeleb(ConstantValues.AUTHORIZATION, "category_id","category_id", String.valueOf(String.valueOf(getArguments().getString("celebrityId"))), String.valueOf(25), "eq","eq", sortBy, "DESC", String.valueOf(visibleThreshold), String.valueOf(pageNumber),String.valueOf(getArguments().getString("celebrityId")),customerMainId);
                }
                else  if(t==2){
                    call = APIClient.getInstance()
                            .getAllProductsCeleb(ConstantValues.AUTHORIZATION, "category_id","category_id", String.valueOf(String.valueOf(getArguments().getString("celebrityId"))), String.valueOf(26), "eq","eq", sortBy, "DESC", String.valueOf(visibleThreshold), String.valueOf(pageNumber),String.valueOf(getArguments().getString("celebrityId")),customerMainId);
                }

                else  if(t==3){
                    call = APIClient.getInstance()
                            .getAllProductsCeleb(ConstantValues.AUTHORIZATION, "category_id","category_id", String.valueOf(String.valueOf(getArguments().getString("celebrityId"))), String.valueOf(27), "eq","eq", sortBy, "DESC", String.valueOf(visibleThreshold), String.valueOf(pageNumber),String.valueOf(getArguments().getString("celebrityId")),customerMainId);
                }
                else  if(t==4){
                    call = APIClient.getInstance()
                            .getAllProductsCeleb(ConstantValues.AUTHORIZATION, "category_id","category_id", String.valueOf(String.valueOf(getArguments().getString("celebrityId"))), String.valueOf(28), "eq","eq", sortBy, "DESC", String.valueOf(visibleThreshold), String.valueOf(pageNumber),String.valueOf(getArguments().getString("celebrityId")),customerMainId);
                }
                else  if(t==5){
                    call = APIClient.getInstance()
                            .getAllProductsCeleb(ConstantValues.AUTHORIZATION, "category_id","category_id", String.valueOf(String.valueOf(getArguments().getString("celebrityId"))), String.valueOf(44), "eq","eq", sortBy, "DESC", String.valueOf(visibleThreshold), String.valueOf(pageNumber),String.valueOf(getArguments().getString("celebrityId")),customerMainId);
                }
            } else {

                call = APIClient.getInstance()
                        .getAllProductsCeleb(ConstantValues.AUTHORIZATION, "category_id","category_id", String.valueOf(String.valueOf(getArguments().getString("celebrityId"))), String.valueOf(25), "eq","eq", sortBy, "DESC", String.valueOf(visibleThreshold), String.valueOf(pageNumber),String.valueOf(getArguments().getString("celebrityId")),customerMainId);
                if(t==1){
                    call = APIClient.getInstance()
                            .getAllProductsCeleb(ConstantValues.AUTHORIZATION, "category_id","category_id", String.valueOf(String.valueOf(getArguments().getString("celebrityId"))), String.valueOf(25), "eq","eq", sortBy, direction, String.valueOf(visibleThreshold), String.valueOf(pageNumber),String.valueOf(getArguments().getString("celebrityId")),customerMainId);
                }
                else  if(t==2){
                    call = APIClient.getInstance()
                            .getAllProductsCeleb(ConstantValues.AUTHORIZATION, "category_id","category_id", String.valueOf(String.valueOf(getArguments().getString("celebrityId"))), String.valueOf(26), "eq","eq", sortBy, direction, String.valueOf(visibleThreshold), String.valueOf(pageNumber),String.valueOf(getArguments().getString("celebrityId")),customerMainId);
                }

                else  if(t==3){
                    call = APIClient.getInstance()
                            .getAllProductsCeleb(ConstantValues.AUTHORIZATION, "category_id","category_id", String.valueOf(String.valueOf(getArguments().getString("celebrityId"))), String.valueOf(27), "eq","eq", sortBy, direction, String.valueOf(visibleThreshold), String.valueOf(pageNumber),String.valueOf(getArguments().getString("celebrityId")),customerMainId);
                }
                else  if(t==4){
                    call = APIClient.getInstance()
                            .getAllProductsCeleb(ConstantValues.AUTHORIZATION, "category_id","category_id", String.valueOf(String.valueOf(getArguments().getString("celebrityId"))), String.valueOf(28), "eq","eq", sortBy, direction, String.valueOf(visibleThreshold), String.valueOf(pageNumber),String.valueOf(getArguments().getString("celebrityId")),customerMainId);
                }
                else  if(t==5){
                    call = APIClient.getInstance()
                            .getAllProductsCeleb(ConstantValues.AUTHORIZATION, "category_id","category_id", String.valueOf(String.valueOf(getArguments().getString("celebrityId"))), String.valueOf(44), "eq","eq", sortBy, direction, String.valueOf(visibleThreshold), String.valueOf(pageNumber),String.valueOf(getArguments().getString("celebrityId")),customerMainId);
                }


                }

        } else {

            if (direction == null) {
                call = APIClient.getInstance()
                        .getAllProducts(ConstantValues.AUTHORIZATION, "category_id", String.valueOf(CategoryID), "eq", sortBy, "DESC", String.valueOf(visibleThreshold), String.valueOf(pageNumber),"",customerMainId);
            } else {
                call = APIClient.getInstance()
                        .getAllProducts(ConstantValues.AUTHORIZATION, "category_id", String.valueOf(CategoryID), "eq", sortBy, direction, String.valueOf(visibleThreshold), String.valueOf(pageNumber),"",customerMainId);
            }

        }


        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                if (!CategoryIDs.isEmpty()) {
//                    categoryProductsList.clear();
                }
                if (loading_bar_first.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);

                } else {
                    progressBar.setVisibility(View.GONE);

                }
                bottomBar.setVisibility(View.GONE);

                emptyRecord.setVisibility(View.GONE);

//                layoutEndOfList.setVisibility(View.GONE);
//                tvEndOfListMsg.setVisibility(View.GONE);


            });
        }
        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {

                if (response.isSuccessful()) {
                    if (!response.body().getProductData().isEmpty()) {

                        totalPages = (response.body().getTotalCount() + visibleThreshold - 1) / visibleThreshold;
                                           // Products have been returned. Add Products to the ProductsList
                        addCategoryProducts(response.body());

                        productAdapter.notifyDataSetChanged();
//                        updateList(categoryProductsList);

                    } else {
                        addCategoryProducts(response.body());
                        productAdapter.notifyDataSetChanged();
                        // Unable to get Success status
//                        if (isVisible)
//                        if (getContext() != null) {
//                            Toast.makeText(getContext(), "No products found.", Toast.LENGTH_SHORT).show();
//                        }
//                        Snackbar.make(rootView, "No products found.", Snackbar.LENGTH_SHORT).show();
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(() ->
                                    {
                                        emptyRecord.setVisibility(View.VISIBLE);

                                    }

                            );

                        }
                    }

                    // Hide the ProgressBar
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {


                            if (loading_bar_first.getVisibility() == View.VISIBLE) {
                                loading_bar_first.setVisibility(View.GONE);

                            } else if (progressBar.getVisibility() == View.VISIBLE) {
                                progressBar.setVisibility(View.GONE);

                            }
                            bottomBar.setVisibility(View.VISIBLE);
//layoutEndOfList.setVisibility(View.GONE);
//                            tvEndOfListMsg.setVisibility(View.GONE);


                        });

                    }

                } else {

                    if (isVisible)
                        if (getContext() != null) {
                            //     Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                }

            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {

                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {


                        if (loading_bar_first.getVisibility() == View.VISIBLE) {
                            loading_bar_first.setVisibility(View.GONE);
                        } else if (progressBar.getVisibility() == View.VISIBLE) {
                            progressBar.setVisibility(View.GONE);
                        }
                        bottomBar.setVisibility(View.VISIBLE);

//layoutEndOfList.setVisibility(View.GONE);
//                        tvEndOfListMsg.setVisibility(View.GONE);

                    });

                }
                if (isVisible) {
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    //*********** Request Products of given Category from the Server based on PageNo. against some Filters ********//

    public void RequestFilteredProducts(int pageNumber, String sortBy, PostFilterData postFilterData, String direction) {
//                &searchCriteriafilterGroups[filters][0][field]=price&
//                searchCriteriafilterGroups[filters][0][value]=40&
//                searchCriteriafilterGroups[filters][0][condition_type]=gteq&
//                searchCriteriafilterGroups[filters][0][field]=price&
//                searchCriteriafilterGroups[filters][0][value]=50&
//                searchCriteriafilterGroups[filters][0][condition_type]=lteq
        Call<ProductData> call;
        if (!CategoryIDs.isEmpty()) {

            Map<String, String> data = new HashMap<>();
            int i = 0;
            for (Integer obj : CategoryIDs) {
                data.put("searchCriteria[filterGroups][" + i + "][filters][0][field]", "category_id");
                data.put("searchCriteria[filterGroups][" + i + "][filters][0][value]", String.valueOf(obj));
                data.put("searchCriteria[filterGroups][" + i + "][filters][0][conditionType]", "eq");
                i++;
            }
            data.put("searchCriteria[filterGroups][" + i + "][filters][0][field]", "price");
            data.put("searchCriteria[filterGroups][" + i + "][filters][0][value]", String.valueOf(postFilterData.getPrice().getMinPrice()));
            data.put("searchCriteria[filterGroups][" + i + "][filters][0][conditionType]", "gteq");
            data.put("searchCriteria[filterGroups][" + i + 1 + "][filters][0][field]", "price");
            data.put("searchCriteria[filterGroups][" + i + 1 + "][filters][0][value]", String.valueOf(postFilterData.getPrice().getMaxPrice()));
            data.put("searchCriteria[filterGroups][" + i + 1 + "][filters][0][conditionType]", "lteq");
            data.put("searchCriteria[sortOrders][0][field]", sortBy);

            if (direction == null) {

                data.put("searchCriteria[sortOrders][0][direction]", "DESC");

            } else {

                data.put("searchCriteria[sortOrders][0][direction]", direction);

            }
            data.put("searchCriteria[pageSize]", String.valueOf(visibleThreshold));
            data.put("searchCriteria[currentPage]", String.valueOf(pageNumber));
            call = APIClient.getInstance()
                    .getAllProducts(ConstantValues.AUTHORIZATION, data,"",customerMainId);

        } else {

            Map<String, String> data = new HashMap<>();

            data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][field]", "category_id");
            data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][value]", String.valueOf(CategoryID));
            data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][conditionType]", "eq");
            data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][field]", "price");
            data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][value]", String.valueOf(postFilterData.getPrice().getMinPrice()));
            data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][conditionType]", "gteq");
            data.put("searchCriteria[filterGroups][" + 2 + "][filters][0][field]", "price");
            data.put("searchCriteria[filterGroups][" + 2 + "][filters][0][value]", String.valueOf(postFilterData.getPrice().getMaxPrice()));
            data.put("searchCriteria[filterGroups][" + 2 + "][filters][0][conditionType]", "lteq");


            data.put("searchCriteria[sortOrders][0][field]", sortBy);
            if (direction == null) {

                data.put("searchCriteria[sortOrders][0][direction]", "DESC");

            } else {

                data.put("searchCriteria[sortOrders][0][direction]", direction);

            }
            data.put("searchCriteria[pageSize]", String.valueOf(visibleThreshold));
            data.put("searchCriteria[currentPage]", String.valueOf(pageNumber));

            call = APIClient.getInstance()
                    .getAllProducts(ConstantValues.AUTHORIZATION, data,"",customerMainId);

        }
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {

                if (loading_bar_first.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);

                }
                bottomBar.setVisibility(View.GONE);
                emptyRecord.setVisibility(View.GONE);
//                layoutEndOfList.setVisibility(View.GONE);
//                tvEndOfListMsg.setVisibility(View.GONE);

            });
        }
        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
                if (getActivity() != null) {

                    getActivity().runOnUiThread(() -> {

                        progressBar.setVisibility(View.GONE);
                        bottomBar.setVisibility(View.VISIBLE);
//                        layoutEndOfList.setVisibility(View.GONE);
//                        tvEndOfListMsg.setVisibility(View.GONE);


                    });
                }
                if (response.isSuccessful()) {
                    if (!response.body().getProductData().isEmpty()) {
                        if (!CategoryIDs.isEmpty()) {
//                            categoryProductsList.clear();
                        }
                        // Products have been returned. Add Products to the ProductsList
                        addCategoryProducts(response.body());
                        productAdapter.notifyDataSetChanged();
                        totalPages = (response.body().getTotalCount() + visibleThreshold - 1) / visibleThreshold;

                    } else if (!response.body().getProductData().isEmpty()) {
                        // Products haven't been returned. Call the method to process some implementations
                        if (!CategoryIDs.isEmpty()) {
//                            categoryProductsList.clear();
                        }                        addCategoryProducts(response.body());
                        productAdapter.notifyDataSetChanged();
                        // Show the Message to the User
                        //    Snackbar.make(rootView, response.body().getProductData().size(), Snackbar.LENGTH_SHORT).show();

                    } else {
                        // Unable to get Success status
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(() -> emptyRecord.setVisibility(View.VISIBLE));

                        }
//                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }

                    // Hide the ProgressBar
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {


                            if (loading_bar_first.getVisibility() == View.VISIBLE) {
                                loading_bar_first.setVisibility(View.GONE);
                            } else if (progressBar.getVisibility() == View.VISIBLE) {
                                progressBar.setVisibility(View.GONE);
                            }
                            bottomBar.setVisibility(View.VISIBLE);
//layoutEndOfList.setVisibility(View.GONE);
//                            tvEndOfListMsg.setVisibility(View.GONE);


                        });

                    }

                } else {
                    if (getContext() != null) {
                        //      Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                if (getActivity() != null) {

                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {


                            if (loading_bar_first.getVisibility() == View.VISIBLE) {
                                loading_bar_first.setVisibility(View.GONE);
                            } else if (progressBar.getVisibility() == View.VISIBLE) {
                                progressBar.setVisibility(View.GONE);
                            }
                            bottomBar.setVisibility(View.VISIBLE);
//layoutEndOfList.setVisibility(View.GONE);
//                            tvEndOfListMsg.setVisibility(View.GONE);


                        });

                    }
                }
                if (getContext() != null) {
                    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //*********** Request Filters of the given Category ********//

    private void RequestFilters(int categories_id) {

        Call<FilterData> call = APIClient.getInstance()
                .getFilters
                        (
                                categories_id,
                                ConstantValues.LANGUAGE_ID
                        );

        call.enqueue(new Callback<FilterData>() {
            @Override
            public void onResponse(Call<FilterData> call, retrofit2.Response<FilterData> response) {

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        filtersList = response.body().getFilters();
                        maxPrice = Double.parseDouble(response.body().getMaxPrice());

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        //    Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    } else {
//                        if (isVisible)
                        //    Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
//                    if (isVisible)
                    if (getContext() != null) {
                        //    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<FilterData> call, Throwable t) {
//                if (isVisible)
                if (getContext() != null) {
                    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /*********** LoadMoreTask Used to Load more Products from the Server in the Background Thread using AsyncTask ********/

    private class LoadMoreTask extends AsyncTask<String, Void, String> {

        int page_number;
        PostFilterData postFilters;


        private LoadMoreTask(int page_number, PostFilterData postFilterData) {
            this.page_number = page_number;
            this.postFilters = postFilterData;
        }


        //*********** Runs on the UI thread before #doInBackground() ********//

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//

        @Override
        protected String doInBackground(String... params) {

            // Check if any of the Filter is applied
            if (isFilterApplied) {
                // Request for Products against specified Filters, based on PageNo.
                RequestFilteredProducts(page_number, sortBy, postFilters, direction);
            } else {
                // Request for Products of given Category, based on PageNo.
                RequestCategoryProducts(page_number, sortBy, direction);
            }

            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}
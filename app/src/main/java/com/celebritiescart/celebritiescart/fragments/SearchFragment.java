package com.celebritiescart.celebritiescart.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.celebritiescart.celebritiescart.adapters.SearchResultsAdapter;
import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.DividerItemDecoration;
import com.celebritiescart.celebritiescart.helpers.CompositeUnmodifiableList;
import com.celebritiescart.celebritiescart.models.category_model.CategoryDetails;
import com.celebritiescart.celebritiescart.models.product_model.ProductData;
import com.celebritiescart.celebritiescart.models.product_model.ProductDetails;
import com.celebritiescart.celebritiescart.models.search_model.SearchResults;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class SearchFragment extends Fragment {

    View rootView;

    AdView mAdView;
    EditText search_editText;
    FrameLayout banner_adView;
    RecyclerView categories_recycler;
    ProgressBar progressBar;
    List<SearchResults> resultsList;
    List<CategoryDetails> subCategoriesList;

    SearchResultsAdapter searchCategoriesProductsAdapter;
    private String searchQuery;
    private ArrayList<SearchResults> categorySearchResults = new ArrayList<>();
    String customerMainId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.search_fragment, container, false);

        if (getActivity().findViewById(R.id.myLayout).getVisibility() != View.GONE) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }

        setHasOptionsMenu(true);
        customerMainId = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("customerID", "");

        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.actionSearch));


        // Binding Layout Views
        banner_adView = rootView.findViewById(R.id.banner_adView);
        search_editText = rootView.findViewById(R.id.search_editText);
//        products_recycler = rootView.findViewById(R.id.products_recycler);
        categories_recycler = rootView.findViewById(R.id.categories_recycler);
        progressBar = rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        if (ConstantValues.IS_ADMOBE_ENABLED) {
            // Initialize Admobe
            mAdView = new AdView(getContext());
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(ConstantValues.AD_UNIT_ID_BANNER);
            AdRequest adRequest = new AdRequest.Builder().build();
            banner_adView.addView(mAdView);
            mAdView.loadAd(adRequest);
        }


//        products_recycler.setNestedScrollingEnabled(false);
        categories_recycler.setNestedScrollingEnabled(false);

        // Hide some of the Views
//        products_recycler.setVisibility(View.GONE);
        categories_recycler.setVisibility(View.GONE);


        resultsList = new ArrayList<>();


        subCategoriesList = new CompositeUnmodifiableList<CategoryDetails>(((App) getContext().getApplicationContext()).getCelebritiesList(), ((App) getContext().getApplicationContext()).getBrandsList());

        categorySearchResults.clear();
        for (int i = 0; i < subCategoriesList.size(); i++) {
            // Add the current Category Info to SearchResults
            SearchResults searchResult = new SearchResults();
            searchResult.setId(Integer.parseInt(subCategoriesList.get(i).getId()));
            searchResult.setName(subCategoriesList.get(i).getName());
            searchResult.setImage(subCategoriesList.get(i).getImage());
            searchResult.setParent("Categories");
            if (subCategoriesList.get(i).isCelebrity()) {
                searchResult.setActionName(getString(R.string.celebrity_category_name));
            } else if (subCategoriesList.get(i).isBrand()) {
                searchResult.setActionName(getString(R.string.brand_category_name));

            }
            categorySearchResults.add(searchResult);
        }


        // Initialize the SearchResultsAdapter for RecyclerView
        searchCategoriesProductsAdapter = new SearchResultsAdapter(getContext(), resultsList, ((AppCompatActivity) getActivity()).getSupportActionBar().getTitle().toString());

        // Set the Adapter, LayoutManager and ItemDecoration to the RecyclerView
        categories_recycler.setAdapter(searchCategoriesProductsAdapter);
        categories_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        categories_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        // Initialize the SearchResultsAdapter for RecyclerView
//        searchProductsAdapter = new SearchResultsAdapter(getContext(), resultsList, ((AppCompatActivity) getActivity()).getSupportActionBar().getTitle().toString());

        // Set the Adapter, LayoutManager and ItemDecoration to the RecyclerView
//        products_recycler.setAdapter(searchProductsAdapter);
//        products_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        products_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        // Show Categories
        resultsList.clear();
        searchCategoriesProductsAdapter.notifyDataSetChanged();
//        searchProductsAdapter.notifyDataSetChanged();
//        products_recycler.setVisibility(View.GONE);
        showCategories();


        search_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


//                if (search_editText.getText().toString().isEmpty()) {
//                    // Show Categories
//                    showCategories();
//                }
//                else {
                searchCategoriesProductsAdapter.getFilter().filter(s.toString());
                searchQuery = s.toString();
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        // Set listener to be called when an action is performed on the search_editText
        search_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (getActivity() != null) {
                      /*  InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);*/
                    }
                    if (!search_editText.getText().toString().isEmpty()) {
                        RequestSearchData(search_editText.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });


        return rootView;
    }


    //*********** Show Main Categories in the SearchList ********//

    private void showCategories() {

        // Make CategoriesList Visible
        categories_recycler.setVisibility(View.VISIBLE);
        resultsList.clear();
        resultsList.addAll(categorySearchResults);


        // Notify the Adapter
        searchCategoriesProductsAdapter.notifyDataSetChanged();

    }


    //*********** Adds SearchResults returned from the Server to the resultsList ********//

    private void addResults(List<ProductDetails> searchData) {

        // Get the model SearchDetails from SearchData


        if (searchData.size() > 0) {

            // Make CategoriesList Visible
            categories_recycler.setVisibility(View.VISIBLE);
//            products_recycler.setVisibility(View.VISIBLE);

            resultsList.clear();
//            searchCategoriesProductsAdapter.notifyDataSetChanged();
//            searchProductsAdapter.notifyDataSetChanged();

            for (int i = 0; i < searchData.size(); i++) {
                // Add the current Product Info to SearchResults
                SearchResults searchResult = new SearchResults();
                searchResult.setId(searchData.get(i).getProductsId());
                searchResult.setName(searchData.get(i).getProductsName());
                searchResult.setImage(searchData.get(i).getProductsImage());
                searchResult.setParent("Products");
                // Add SearchResults to results List
                resultsList.add(searchResult);
            }
          resultsList.addAll(categorySearchResults);
//            searchCategoriesProductsAdapter.notifyDataSetChanged();
            searchCategoriesProductsAdapter.getFilter().filter(searchQuery);

//            searchProductsAdapter.notifyDataSetChanged();
//            showCategories();

        } else {
//            resultsList.clear();
//            searchCategoriesProductsAdapter.notifyDataSetChanged();
////            searchProductsAdapter.notifyDataSetChanged();
////            products_recycler.setVisibility(View.GONE);
//            showCategories();

        }

    }


    //*********** Request Search Results from the Server based on the given Query ********//

    public void RequestSearchData(String searchValue) {

        Call<ProductData> call;
        call = APIClient.getInstance()
                .getAllProducts(ConstantValues.AUTHORIZATION, "name",
                        "%25" + searchValue + "%25", "like",
                        "created_at", "DESC", null,
                        null,"",customerMainId);
        progressBar.setVisibility(View.VISIBLE);
        categories_recycler.setVisibility(View.GONE);
//        products_recycler.setVisibility(View.GONE);
        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (!response.body().getProductData().isEmpty()) {

                        // Search Results have been returned. Add Results to the resultsList
                        addResults(response.body().getProductData());

                    } else if (response.body().getProductData().isEmpty()) {
                        if (getContext() != null) {
                           Snackbar.make(rootView, "No products found.", Snackbar.LENGTH_LONG).show();
//                            Toast.makeText(getContext(), "No products found.", Toast.LENGTH_SHORT).show();
                            addResults(response.body().getProductData());
                        }
                    } else {
                        if (getContext() != null) {

                            // Unable to get Success status
//                            Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();

                        }
                    }
                } else {
                    if (getContext() != null) {
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (getContext() != null) {
                    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Hide Search Icon in the Toolbar
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        cartItem.setVisible(true);
        searchItem.setVisible(false);
    }

}




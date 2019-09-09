package com.celebritiescart.celebritiescart.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.celebritiescart.celebritiescart.adapters.CartItemsAdapter;
import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.DialogLoader;
import com.celebritiescart.celebritiescart.databases.User_Cart_DB;
import com.celebritiescart.celebritiescart.models.cart_model.AddToCart;
import com.celebritiescart.celebritiescart.models.cart_model.CartProduct;
import com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response.AddToCartResponse;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class My_Cart extends Fragment {

    public TextView cart_total_price;
    public TextView total_items;

    RecyclerView cart_items_recycler;
    LinearLayout cart_view, cart_view_empty;
    Button cart_checkout_btn, continue_shopping_btn;

    CartItemsAdapter cartItemsAdapter;
    User_Cart_DB user_cart_db = new User_Cart_DB();

    List<CartProduct> cartItemsList = new ArrayList<>();
    private BottomNavigationView bottom_nav;
    private DialogLoader dialogLoader;
    private List<AddToCartResponse> itemsProccessed = new ArrayList<>();
    private String customerID;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_cart, container, false);

        if (getActivity().findViewById(R.id.myLayout).getVisibility() != View.GONE) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }

        setHasOptionsMenu(true);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        SpannableString s = new SpannableString(getString(R.string.actionCartTemp));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);
        dialogLoader = new DialogLoader(getContext());
        // Get the List of Cart Items from the Local Databases User_Cart_DB

        cartItemsList = user_cart_db.getCartItems();
        bottom_nav = getActivity().findViewById(R.id.myBottomNav);
        bottom_nav.getMenu().findItem(R.id.bottomNavCart).setChecked(true);

        // Binding Layout Views
        cart_view = rootView.findViewById(R.id.cart_view);
        cart_total_price = rootView.findViewById(R.id.cart_total_price);
        total_items = rootView.findViewById(R.id.total_items);
        cart_checkout_btn = rootView.findViewById(R.id.cart_checkout_btn);
        cart_items_recycler = rootView.findViewById(R.id.cart_items_recycler);
        cart_view_empty = rootView.findViewById(R.id.cart_view_empty);
        continue_shopping_btn = rootView.findViewById(R.id.continue_shopping_btn);
        try {
            customerID = "Bearer " + getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        } catch (Exception ignored) {

        }

        // Change the Visibility of cart_view and cart_view_empty LinearLayout based on CartItemsList's Size
        if (cartItemsList.size() != 0) {
            cart_view.setVisibility(View.VISIBLE);
            cart_view_empty.setVisibility(View.GONE);
        } else {
            cart_view.setVisibility(View.GONE);
            cart_view_empty.setVisibility(View.VISIBLE);
        }


        // Initialize the AddressListAdapter for RecyclerView
        cartItemsAdapter = new CartItemsAdapter(getContext(), cartItemsList, My_Cart.this);

        // Set the Adapter and LayoutManager to the RecyclerView
        cart_items_recycler.setAdapter(cartItemsAdapter);
        cart_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        // Show the Cart's Total Price with the help of static method of CartItemsAdapter
        cartItemsAdapter.setCartTotal();


        cartItemsAdapter.notifyDataSetChanged();


        // Handle Click event of continue_shopping_btn Button
        continue_shopping_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putBoolean("isSubFragment", false);

                // Navigate to Products Fragment
                Fragment fragment = new Products();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionCart)).commit();

            }
        });


        // Handle Click event of cart_checkout_btn Button  Note: this is just commented because we are getting app live with test credentials
        cart_checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if cartItemsList isn't empty
                if (cartItemsList.size() != 0) {

                    // Check if User is Logged-In
                    if (ConstantValues.IS_USER_LOGGED_IN) {

                        // Navigate to Shipping_Address Fragment
//                        Fragment fragment = new Shipping_Address();
//                        FragmentManager fragmentManager = getFragmentManager();
//                        fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
//                                .addToBackStack(getString(R.string.actionCart)).commit();


                        Call<String> call = APIClient.getInstance()
                                .getCustomerCart
                                        (
                                                customerID
                                        );

                        dialogLoader.showProgressDialog();
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {

                                dialogLoader.hideProgressDialog();

                                if (response.isSuccessful()) {


                                    if (!response.body().isEmpty()) {
                                        // Get the User Details from Response

                                        addItemsToCart(response.body());

                                    } else {
                                        if (getContext() != null) {

                                         //   Toast.makeText(getContext(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                } else {
                                    if (response.code() == 400) {
                                        try {
                                         //   Toast.makeText(getContext(), "Product is unavailable.", Toast.LENGTH_LONG).show();


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        if (getContext() != null) {

                                       //     Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                }

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                dialogLoader.hideProgressDialog();
                                if (getContext() != null) {

                               //     Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                                }
                            }

                        });


                    } else {

                        Call<String> call = APIClient.getInstance()
                                .getGuestCart
                                        (
                                                ConstantValues.AUTHORIZATION
                                        );

                        dialogLoader.showProgressDialog();

                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {

                                dialogLoader.hideProgressDialog();

                                if (response.isSuccessful()) {


                                    if (!response.body().isEmpty()) {
                                        // Get the User Details from Response

                                        addItemsToCart(response.body());

                                    } else {
                                        if (getContext() != null) {
                                     //       Toast.makeText(getContext(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                } else {
                                    if (response.code() == 400) {
                                        try {
                                         //   Toast.makeText(getContext(), "Product is unavailable.", Toast.LENGTH_LONG).show();


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        if (getContext() != null) {

                                      //      Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                }

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                dialogLoader.hideProgressDialog();
                                if (getContext() != null) {

                            //        Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                                }
                            }

                        });


                    }
                }
            }
        });


        return rootView;
    }

    private void addItemsToCart(String body) {
        itemsProccessed.clear();

        for (CartProduct item : cartItemsList) {
            addSingleItemToCart(item.getCartProduct(body),item.getCelebrityId());
        }
    }


    private void addSingleItemToCart(AddToCart item,String celebrityId) {
        Call<AddToCartResponse> call;
        if (ConstantValues.IS_USER_LOGGED_IN) {
            call = APIClient.getInstance()
                    .addSingleItemToCartCustomer
                            (
                                    customerID, item,celebrityId
                            );

        } else {


            call = APIClient.getInstance()
                    .addSingleItemToCart
                            (
                                    ConstantValues.AUTHORIZATION, item, item.getCartItem().getQuoteId(),celebrityId
                            );
        }

        dialogLoader.showProgressDialog();

        call.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {

                dialogLoader.hideProgressDialog();
                if (response.isSuccessful()) {

                    itemsProccessed.add(response.body());
                    dialogLoader.hideProgressDialog();


                    if (itemsProccessed.size() == cartItemsList.size()) {

                        Fragment fragment = new Shipping_Address();
                        Bundle cartInfo = new Bundle();
                        cartInfo.putString("QuoteID", item.getCartItem().getQuoteId());
                        ((App) getContext().getApplicationContext()).setGuestCartID(item.getCartItem().getQuoteId());
                        fragment.setArguments(cartInfo);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
                                .addToBackStack(getString(R.string.actionCart)).commit();
                    }

                } else {

//                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                    if (response.code() == 400) {
                        try {

                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            if (jObjError.isNull("parameters")) {
                                if (getContext() != null) {

                          //          Toast.makeText(getContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                JSONArray jsonArray = jObjError.getJSONArray("parameters");

                                List<String> params = new ArrayList<String>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    params.add(jsonArray.getString(i));
                                }
                                if (params.size() > 0) {
                                    if (getContext() != null) {

                          //              Toast.makeText(getContext(), jObjError.getString("message").replace("%1", params.get(0)), Toast.LENGTH_LONG).show();

                                    }
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    dialogLoader.hideProgressDialog();

                }


            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                if (getContext() != null) {

            //        Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }

        });

    }


    //*********** Change the Layout View of My_Cart Fragment based on Cart Items ********//

    public void updateCartView(int cartListSize) {

        // Check if Cart has some Items
        if (cartListSize != 0) {
            cart_view.setVisibility(View.VISIBLE);
            cart_view_empty.setVisibility(View.GONE);
        } else {
            cart_view.setVisibility(View.GONE);
            cart_view_empty.setVisibility(View.VISIBLE);
        }
    }


    //*********** Static method to Add the given Item to User's Cart ********//

    //step 4 akki
    public static void AddCartItem(CartProduct cartProduct) {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.addCartItem
                (
                        cartProduct
                );
    }


    //*********** Static method to Get the Cart Product based on product_id ********//

    public static CartProduct GetCartProduct(int product_id) {
        User_Cart_DB user_cart_db = new User_Cart_DB();

        CartProduct cartProduct = user_cart_db.getCartProduct
                (
                        product_id
                );

        return cartProduct;
    }


    //*********** Static method to Update the given Item in User's Cart ********//

    public static void UpdateCartItem(CartProduct cartProduct) {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.updateCartItem
                (
                        cartProduct
                );
    }


    //*********** Static method to Delete the given Item from User's Cart ********//

    public static void DeleteCartItem(int cart_item_id) {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.deleteCartItem
                (
                        cart_item_id
                );
    }


    //*********** Static method to Clear User's Cart ********//

    public static void ClearCart() {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.clearCart();
    }


    //*********** Static method to get total number of Items in User's Cart ********//

    public static int getCartSize() {
        int cartSize = 0;

        User_Cart_DB user_cart_db = new User_Cart_DB();
        List<CartProduct> cartItems = user_cart_db.getCartItems();

        for (int i = 0; i < cartItems.size(); i++) {
            cartSize += cartItems.get(i).getCustomersBasketProduct().getCustomersBasketQuantity();
        }

        return cartSize;
    }


    //*********** Static method to check if the given Product is already in User's Cart ********//

    public static boolean checkCartHasProduct(int cart_item_id) {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        return user_cart_db.getCartItemsIDs().contains(cart_item_id);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Hide Cart Icon in the Toolbar
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        cartItem.setVisible(false);
        searchItem.setVisible(true);
    }

}


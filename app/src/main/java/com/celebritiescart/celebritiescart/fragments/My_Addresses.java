package com.celebritiescart.celebritiescart.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.celebritiescart.celebritiescart.adapters.AddressListAdapter;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.DialogLoader;
import com.celebritiescart.celebritiescart.models.address_model.AddressData;
import com.celebritiescart.celebritiescart.models.address_model.AddressDetails;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class My_Addresses extends Fragment {

    View rootView;
    String customerID;
    String defaultAddressID;

    RecyclerView addresses_recycler;
    FloatingActionButton add_address_fab;

    DialogLoader dialogLoader;
    AddressListAdapter addressListAdapter;

    List<AddressDetails> addressesList = new ArrayList<>();

    private int defaultAddressPosition = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.my_addresses, container, false);

        if (getActivity().findViewById(R.id.myLayout).getVisibility()!=View.GONE ) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }
        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        SpannableString s = new SpannableString(getString(R.string.actionAddresses));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);

        // Get the CustomerID and DefaultAddressID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        defaultAddressID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userDefaultAddressID", "");


        // Binding Layout Views
        addresses_recycler = rootView.findViewById(R.id.addresses_recycler);
        add_address_fab = rootView.findViewById(R.id.add_address_fab);


        dialogLoader = new DialogLoader(getContext());

        // Request for User's Addresses
//        RequestAllAddresses();



        // Handle Click event of add_address_fab FAB
        add_address_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Navigate to Add_Address Fragment with arguments
                Fragment fragment = new Add_Address();
                Bundle args = new Bundle();
                args.putBoolean("isUpdate", false);
                fragment.setArguments(args);

                FragmentManager fragmentManager = ((MainActivity) getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
                        .addToBackStack(null).commit();
            }
        });


        return rootView;
    }



    //*********** Adds Addresses returned from the Server to the AddressesList ********//

    private void addAddresses(AddressData addressData) {

        // Add Addresses to addressesList from the List of AddressData
        addressesList = addressData.getData();

        for (int i=0;  i<addressesList.size();  i++) {
            if (addressesList.get(i).getAddressId() == addressesList.get(i).getDefaultAddress()) {
                defaultAddressPosition = i;
            }
        }

        if (addressesList.size() == 1) {
            MakeAddressDefault(customerID, String.valueOf(addressesList.get(0).getAddressId()), getContext(), rootView);
        }
        

        // Initialize the AddressListAdapter for RecyclerView
        addressListAdapter = new AddressListAdapter(getContext(), customerID, defaultAddressPosition, addressesList);

        // Set the Adapter and LayoutManager to the RecyclerView
        addresses_recycler.setAdapter(addressListAdapter);
        addresses_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        addressListAdapter.notifyDataSetChanged();

    }



    //*********** Request User's all Addresses from the Server ********//

    public void RequestAllAddresses() {

        dialogLoader.showProgressDialog();

        Call<AddressData> call = APIClient.getInstance()
                .getAllAddress
                        (
                                customerID
                        );

        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, retrofit2.Response<AddressData> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Addresses have been returned. Add Addresses to the addressesList
                        addAddresses(response.body());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Addresses haven't been returned. Show the Message to the User
                     //   Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                    else {
                        // Unable to get Success status
                     //   Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    if(getContext()!=null) {  Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();}
                }
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                if(getContext()!=null) {   Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();}
            }
        });
    }



    //*********** Request to Delete the given User's Address ********//

    public static void DeleteAddress(final String customerID, final String addressID, final Context context, final View view) {

        Call<AddressData> call = APIClient.getInstance()
                .deleteUserAddress
                        (
                                customerID,
                                addressID
                        );

        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, retrofit2.Response<AddressData> response) {
                
                if (response.isSuccessful()) {

                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Address has been Deleted. Show the Message to the User
                    //    Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                     //   Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
                    }
                    else {
                        // Unable to get Success status
                    //    Snackbar.make(view, context.getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    if(context!=null) {   Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();}
                }
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                if(context!=null) {  Toast.makeText(context, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();}
            }
        });
    }



    //*********** Request for Changing the Address to User's Default Address ********//

    public static void MakeAddressDefault(final String customerID, final String addressID, final Context context, final View view) {
        
        final DialogLoader dialogLoader = new DialogLoader(context);
        dialogLoader.showProgressDialog();
        
        Call<AddressData> call = APIClient.getInstance()
                .updateDefaultAddress
                        (
                                customerID,
                                addressID
                        );

        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, retrofit2.Response<AddressData> response) {
    
                dialogLoader.hideProgressDialog();
                
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Change the value of userDefaultAddressID in the SharedPreferences
                        SharedPreferences.Editor editor = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit();
                        editor.putString("userDefaultAddressID", addressID);
                        editor.apply();

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                     //   Snackbar.make(view, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
                    }
                    else {
                        // Unable to get Success status
                     //   Snackbar.make(view, context.getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(context, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }

}


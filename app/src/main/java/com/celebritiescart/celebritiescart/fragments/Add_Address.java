package com.celebritiescart.celebritiescart.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.models.address_model.AddressData;
import com.celebritiescart.celebritiescart.models.address_model.Zones;
import com.celebritiescart.celebritiescart.models.city_model.Cities;
import com.celebritiescart.celebritiescart.models.city_model.CityMainModel;
import com.celebritiescart.celebritiescart.models.city_model.Countries;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.network.APIClient1;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.utils.ValidateInputs;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class Add_Address extends Fragment {

    View rootView;
    Boolean isUpdate;

    String customerID, addressID;
    String  selectedCountryID;
    String selectedCityID;

    Button saveAddressBtn;
    LinearLayout default_shipping_layout;
    EditText input_fullname, input_address, input_country, input_city;
    
    ArrayAdapter<String> countryAdapter;
    ArrayAdapter<String> cityAdapter;

    List<String> countryNames;
    List<String> cityNames;
   // List<CountryDetails> countryList = new ArrayList<>();
   List<Countries> countryList = new ArrayList<>();
   List<Cities>cityList=new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.address, container, false);

        if (getActivity().findViewById(R.id.myLayout).getVisibility()!=View.GONE ) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }

        // Get the Bundle Arguments
        Bundle addressInfo = getArguments();
        isUpdate = addressInfo.getBoolean("isUpdate");

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        
        // Set the Title of Toolbar
        if (isUpdate) {
            SpannableString s = new SpannableString(getString(R.string.update_address));
            s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);
        } else {
            SpannableString s = new SpannableString(getString(R.string.new_address));
            s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);
        }
        

        // Get the CustomersID from SharedPreferences
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        input_fullname = rootView.findViewById(R.id.fullname);
        input_address = rootView.findViewById(R.id.address);
        input_country = rootView.findViewById(R.id.country);
        input_city = rootView.findViewById(R.id.city);
        saveAddressBtn = rootView.findViewById(R.id.save_address_btn);
        default_shipping_layout = rootView.findViewById(R.id.default_shipping_layout);


        // Set KeyListener of some View to null
        input_country.setKeyListener(null);

        countryNames = new ArrayList<>();

        // Hide the Default Checkbox Layout
        default_shipping_layout.setVisibility(View.GONE);


        // Request for Countries List
        RequestCountriesandCities("");


        // Check if an existing Address is being Edited
        if (isUpdate) {
            // Set the Address details from Bundle Arguments
            addressID = addressInfo.getString("addressID");
            selectedCountryID = addressInfo.getString("addressCountryID");

            input_fullname.setText(addressInfo.getString("addressFirstname")+" "+addressInfo.getString("addressLastname"));
            input_address.setText(addressInfo.getString("addressStreet"));
            input_country.setText(addressInfo.getString("addressCountryName"));
            input_city.setText(addressInfo.getString("addressCity"));

//            RequestZones(String.valueOf(selectedCountryID));
        }
        else {

        }



        // Handle Touch event of input_country EditText
        input_country.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
    
                    countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
                    countryAdapter.addAll(countryNames);
    
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list_search, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(false);
    
                    Button dialog_button = dialogView.findViewById(R.id.dialog_button);
                    EditText dialog_input = dialogView.findViewById(R.id.dialog_input);
                    TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
                    ListView dialog_list = dialogView.findViewById(R.id.dialog_list);
    
                    dialog_title.setText(getString(R.string.city));
                    dialog_list.setVerticalScrollBarEnabled(true);
                    dialog_list.setAdapter(countryAdapter);
    
                    dialog_input.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                            // Filter CountryAdapter
                            countryAdapter.getFilter().filter(charSequence);
                        }
                        @Override
                        public void afterTextChanged(Editable s) {}
                    });
    
    
                    final AlertDialog alertDialog = dialog.create();
    
                    dialog_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
    
                    alertDialog.show();
    
    
    
                    dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            
                            alertDialog.dismiss();
                            final String selectedItem = countryAdapter.getItem(position);

                            String countryID = "";
                            input_country.setText(selectedItem);
            
                            if (!selectedItem.equalsIgnoreCase("Other")) {
                
                                for (int i=0;  i<countryList.size();  i++) {
                                    if (countryList.get(i).getCode().equalsIgnoreCase(selectedItem)) {
                                        // Get the ID of selected Country
                                        countryID = countryList.get(i).getLable();
                                    }
                                }
                
                            }
            
                            selectedCountryID = String.valueOf(countryID);
            
//                            zoneNames.clear();
//                            input_zone.setText("");
            
                            // Request for all Zones in the selected Country
                           RequestCountriesandCities(String.valueOf(selectedCountryID));
                        }
                    });
                }

                return false;
            }
        });



        input_city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    cityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
                    cityAdapter.addAll(cityNames);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list_search, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(false);

                    Button dialog_button = dialogView.findViewById(R.id.dialog_button);
                    EditText dialog_input = dialogView.findViewById(R.id.dialog_input);
                    TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
                    ListView dialog_list = dialogView.findViewById(R.id.dialog_list);

                    dialog_title.setText(getString(R.string.country));
                    dialog_list.setVerticalScrollBarEnabled(true);
                    dialog_list.setAdapter(cityAdapter);

                    dialog_input.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                            // Filter CountryAdapter
                            cityAdapter.getFilter().filter(charSequence);
                        }
                        @Override
                        public void afterTextChanged(Editable s) {}
                    });


                    final AlertDialog alertDialog = dialog.create();

                    dialog_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();



                    dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            alertDialog.dismiss();
                            final String selectedItem = cityAdapter.getItem(position);

                            String cityId = "";
                            input_country.setText(selectedItem);

                            if (!selectedItem.equalsIgnoreCase("Other")) {

                                for (int i=0;  i<cityList.size();  i++) {
                                    if (cityList.get(i).getCode().equalsIgnoreCase(selectedItem)) {
                                        // Get the ID of selected Country
                                        cityId = cityList.get(i).getCode();
                                    }
                                }

                            }

                            selectedCityID = String.valueOf(cityId);


                        }
                    });
                }

                return false;
            }
        });


        // Handle Touch event of input_zone EditText
//        input_zone.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//
//                    zoneAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
//                    zoneAdapter.addAll(zoneNames);
//
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
//                    View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list_search, null);
//                    dialog.setView(dialogView);
//                    dialog.setCancelable(false);
//
//                    Button dialog_button = dialogView.findViewById(R.id.dialog_button);
//                    EditText dialog_input = dialogView.findViewById(R.id.dialog_input);
//                    TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
//                    ListView dialog_list = dialogView.findViewById(R.id.dialog_list);
//
//                    dialog_title.setText(getString(R.string.zone));
//                    dialog_list.setVerticalScrollBarEnabled(true);
//                    dialog_list.setAdapter(zoneAdapter);
//
//                    dialog_input.addTextChangedListener(new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//                        @Override
//                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//                            // Filter ZoneAdapter
//                            zoneAdapter.getFilter().filter(charSequence);
//                        }
//                        @Override
//                        public void afterTextChanged(Editable s) {}
//                    });
//
//
//                    final AlertDialog alertDialog = dialog.create();
//
//                    dialog_button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            alertDialog.dismiss();
//                        }
//                    });
//
//                    alertDialog.show();
//
//
//
//                    dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                            alertDialog.dismiss();
//                            final String selectedItem = zoneAdapter.getItem(position);
//
//                            String zoneID = "";
//                            input_zone.setText(selectedItem);
//
//                            if (!zoneAdapter.getItem(position).equalsIgnoreCase("Other")) {
//
//                                for (int i=0;  i<zoneList.size();  i++) {
//                                    if (zoneList.get(i).getZoneName().equalsIgnoreCase(selectedItem)) {
//                                        // Get the ID of selected Country
//                                        zoneID = zoneList.get(i).getZoneId();
//                                    }
//                                }
//                            }
//
//                            selectedZoneID = String.valueOf(zoneID);
//                        }
//                    });
//                }
//
//                return false;
//            }
//        });


        // Handle the Click event of Save Button
        saveAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate Address Form Inputs
                boolean isValidData = validateAddressForm();

                if (isValidData) {
                    if (isUpdate) {
                        // Update the Address
                        updateUserAddress(addressID);
                    } else {
                        // Add a new Address
                        addUserAddress();
                    }
                }
            }
        });


        return rootView;
    }



    //*********** Get Countries List from the Server ********//

    //Old Api
    /*private void RequestCountries() {

        Call<List<CountryDetails>> call = APIClient.getInstance()
                .getCountries(ConstantValues.AUTHORIZATION);

        call.enqueue(new Callback<List<CountryDetails>>() {
            @Override
            public void onResponse(Call<List<CountryDetails>> call, Response<List<CountryDetails>> response) {
                
                if (response.isSuccessful()) {

                	// Check the Success status
                    if (!response.body().isEmpty()) {

                        countryList = response.body();

                        // Add the Country Names to the countryNames List
                        for (int i=0;  i<countryList.size();  i++) {
                            countryNames.add(countryList.get(i).getCountriesName());
                        }

                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CountryDetails>> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }*/

    private void RequestCountriesandCities(String selectedCountryID) {

        Call<CityMainModel> call = APIClient1.getInstance()
                .getCountries_n_Cities(selectedCountryID);

        call.enqueue(new Callback<CityMainModel>() {
            @Override
            public void onResponse(Call<CityMainModel> call, Response<CityMainModel> response) {

                if (response.isSuccessful()) {

                    // Check the Success status
                    if (response.body()!=null) {
                        countryNames=new ArrayList<>();
                        cityNames=new ArrayList<>();

                        countryList = response.body().getCountries();
                        cityList=response.body().getCities();
                        // Add the Country Names to the countryNames List
                        for (int i=0;  i<countryList.size();  i++) {
                            countryNames.add(countryList.get(i).getCode());
                        }
                        for (int i=0;  i<cityList.size();  i++) {
                            cityNames.add(cityList.get(i).getCode());
                        }

                    }
                    else {
                        // Unable to get Success status
                    //    Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CityMainModel> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }

    //*********** Get Zones List of the Country from the Server ********//

    private void RequestZones(String countryID) {

        Call<Zones> call = APIClient.getInstance()
                .getZones
                        (
                                countryID, ConstantValues.AUTHORIZATION
                        );

        call.enqueue(new Callback<Zones>() {
            @Override
            public void onResponse(Call<Zones> call, Response<Zones> response) {

                if (response.isSuccessful()) {

                    if (!response.body().getSuccess().isEmpty()) {
    
//                        zoneList = response.body().getData();
//
//                        // Add the Zone Names to the zoneNames List
//                        for (int i=0;  i<zoneList.size();  i++){
//                            zoneNames.add(zoneList.get(i).getZoneName());
//                        }
//
//                        zoneNames.add("Other");
                        
                    }
                    else {
                        // Unable to get Success status
                    //    Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Zones> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Proceed the Request of New Address ********//

    public void addUserAddress() {

        final String customers_default_address_id = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userDefaultAddressID", "");

        String name = input_fullname.getText().toString().trim();
        String lastName = "";
        String firstName= "";
        if(name.split("\\w+").length>1){

            lastName = name.substring(name.lastIndexOf(" ")+1);
            firstName = name.substring(0, name.lastIndexOf(' '));
        }
        else{
            firstName = name;
        }
        Call<AddressData> call = APIClient.getInstance()
                .addUserAddress
                        (
                                customerID,
                                firstName,
                                lastName,
                                input_address.getText().toString().trim(),
                                null,
                                input_city.getText().toString().trim(),
                                selectedCountryID,
                                null,
                                customers_default_address_id
                        );

        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, Response<AddressData> response) {
                
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        // Address has been added to User's Addresses
                        // Navigate to Addresses fragment
                        ((MainActivity) getContext()).getSupportFragmentManager().popBackStack();
                        
                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                    //    Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
                    }
                    else {
                        // Unable to get Success status
                    //    Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Proceed the Request of Update Address ********//

    public void updateUserAddress(String addressID) {

        final String customers_default_address_id = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userDefaultAddressID", "");

        String name = input_fullname.getText().toString().trim();
        String lastName = "";
        String firstName= "";
        if(name.split("\\w+").length>1){

            lastName = name.substring(name.lastIndexOf(" ")+1);
            firstName = name.substring(0, name.lastIndexOf(' '));
        }
        else{
            firstName = name;
        }
        Call<AddressData> call = APIClient.getInstance()
                .updateUserAddress
                        (
                                customerID,
                                addressID,
                                firstName,
                                lastName,
                                input_address.getText().toString().trim(),
                                null,
                                input_city.getText().toString().trim(),
                                selectedCountryID,
                                null,
                                customers_default_address_id
                        );

        call.enqueue(new Callback<AddressData>() {
            @Override
            public void onResponse(Call<AddressData> call, Response<AddressData> response) {
                
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        // Address has been Edited
                        // Navigate to Addresses fragment
                        ((MainActivity) getContext()).getSupportFragmentManager().popBackStack();
                        
                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Address has not been Edited
                        // Show the Message to the User
                        Toast.makeText(getContext(), ""+response.body().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Validate Address Form Inputs ********//

    private boolean validateAddressForm() {
        if (!ValidateInputs.isValidName(input_fullname.getText().toString().trim())) {
            input_fullname.setError(getString(R.string.invalid_name));
            return false;
        }  else if (!ValidateInputs.isValidInput(input_address.getText().toString().trim())) {
            input_address.setError(getString(R.string.invalid_address));
            return false;
        } else if (!ValidateInputs.isValidInput(input_country.getText().toString().trim())) {
            input_country.setError(getString(R.string.select_country));
            return false;
        } else if (!ValidateInputs.isValidInput(input_city.getText().toString().trim())) {
            input_city.setError(getString(R.string.enter_city));
            return false;
        }  else {
            return true;
        }
    }

}


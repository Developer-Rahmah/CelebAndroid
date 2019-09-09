package com.celebritiescart.celebritiescart.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.DialogLoader;
import com.celebritiescart.celebritiescart.models.address_model.AddressData;
import com.celebritiescart.celebritiescart.models.address_model.AddressDetails;
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


public class Shipping_Address extends Fragment {

    View rootView;
    Boolean isUpdate = false;
    String customerID, defaultAddressID;
    String selectedCountryID;
    String selectedCityID;

    List<String> countryNames;
    List<Countries> countryList;
    List<Cities>cityList=new ArrayList<>();


    List<String> cityNames;

    ArrayAdapter<String> countryAdapter;
    ArrayAdapter<String> cityAdapter;
    Button proceed_checkout_btn;
    LinearLayout default_shipping_layout;
    EditText input_fullname, input_address, input_country, input_city, input_telephone, input_email;

    DialogLoader dialogLoader;


    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.address, container, false);

        if (getActivity().findViewById(R.id.myLayout).getVisibility() != View.GONE) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }

        if (getArguments() != null) {
            if (getArguments().containsKey("isUpdate")) {
                isUpdate = getArguments().getBoolean("isUpdate", false);
            }
        }


        // Set the Title of Toolbar
        SpannableString s = new SpannableString(getString(R.string.shipping_address));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

        // Get the customersID and defaultAddressID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        defaultAddressID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userDefaultAddressID", "");


        // Binding Layout Views
        input_fullname = rootView.findViewById(R.id.fullname);
        input_address = rootView.findViewById(R.id.address);
        input_country = rootView.findViewById(R.id.country);
        input_city = rootView.findViewById(R.id.city);
        input_telephone = rootView.findViewById(R.id.input_telephone);
        input_email = rootView.findViewById(R.id.input_email);
        default_shipping_layout = rootView.findViewById(R.id.default_shipping_layout);
        proceed_checkout_btn = rootView.findViewById(R.id.save_address_btn);


        // Set KeyListener of some View to null
        input_country.setKeyListener(null);

        countryNames = new ArrayList<String>();


        // Hide the Default Checkbox Layout
        default_shipping_layout.setVisibility(View.GONE);

        // Set the text of Button
        proceed_checkout_btn.setText(getContext().getString(R.string.next));


        dialogLoader = new DialogLoader(getContext());


        // Request Countries
     //   RequestCountries();
        RequestCountriesandCities("");

        // If an existing Address is being Edited
        if (isUpdate) {
            // Get the Shipping AddressDetails from AppContext that is being Edited
            AddressDetails shippingAddress = ((App) getContext().getApplicationContext()).getShippingAddress();

            // Set the Address details
            selectedCountryID = shippingAddress.getCountriesId();
            input_address.setText(shippingAddress.getStreet().get(0));
            input_country.setText(shippingAddress.getCountryName());
            input_city.setText(shippingAddress.getCity());

        } else {


            // Request All Addresses of the User
//            RequestAllAddresses();


//
//            AddressDetails shippingAddress = ((App) getContext().getApplicationContext()).getShippingAddress();
//
//            // Set the Address details
//            selectedZoneID = shippingAddress.getZoneId();
//            selectedCountryID = shippingAddress.getCountriesId();
//            input_firstname.setText(shippingAddress.getFirstname());
//            input_lastname.setText(shippingAddress.getLastname());
//            input_address.setText(shippingAddress.getStreet().get(0));
//            input_country.setText(shippingAddress.getCountryName());
//            input_zone.setText(shippingAddress.getZoneName());
//            input_city.setText(shippingAddress.getCity());
//            input_postcode.setText(shippingAddress.getPostcode());
//
//            RequestZones(String.valueOf(selectedCountryID));
        }


        // Handle Touch event of input_country EditText
        input_country.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {

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

                        dialog_title.setText(getString(R.string.country));
                        dialog_list.setVerticalScrollBarEnabled(true);
                        dialog_list.setAdapter(countryAdapter);

                        dialog_input.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                                countryAdapter.getFilter().filter(charSequence);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                            }
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

                                    for (int i = 0; i < countryList.size(); i++) {
                                        if (countryList.get(i).getCode() != null) {

                                            if (countryList.get(i).getCode().equalsIgnoreCase(selectedItem)) {
                                                // Get the ID of selected Country
                                                countryID = countryList.get(i).getLable();
                                            }

                                        }
                                    }

                                }

                                selectedCountryID = countryID;

                                RequestCountriesandCities(selectedCountryID);
                                // Request for all Zones in the selected Country
//                                RequestZones(String.valueOf(selectedCountryID));
                            }
                        });


                    }
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            }
        });
        input_city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {

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

                        dialog_title.setText(getString(R.string.city));
                        dialog_list.setVerticalScrollBarEnabled(true);
                        dialog_list.setAdapter(cityAdapter);

                        dialog_input.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                                cityAdapter.getFilter().filter(charSequence);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                            }
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
                                input_city.setText(selectedItem);

                                if (!selectedItem.equalsIgnoreCase("Other")) {

                                    for (int i = 0; i < cityList.size(); i++) {
                                        if (cityList.get(i).getCode() != null) {

                                            if (cityList.get(i).getCode().equalsIgnoreCase(selectedItem)) {
                                                // Get the ID of selected Country
                                                cityId = cityList.get(i).getCode();
                                            }

                                        }
                                    }

                                }

                                selectedCityID = cityId;


                                // Request for all Zones in the selected Country
//                                RequestZones(String.valueOf(selectedCountryID));
                            }
                        });


                    }
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

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
//                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                        }
//
//                        @Override
//                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//                            zoneAdapter.getFilter().filter(charSequence);
//                        }
//
//                        @Override
//                        public void afterTextChanged(Editable s) {
//                        }
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
//                                for (int i = 0; i < zoneList.size(); i++) {
//                                    if (zoneList.get(i).getZoneName().equalsIgnoreCase(selectedItem)) {
//                                        // Get the ID of selected Country
//                                        zoneID = zoneList.get(i).getZoneId();
//                                    }
//                                }
//                            }
//
//                            selectedZoneID = zoneID;
//                        }
//                    });
//
//                }
//
//                return false;
//            }
//        });


        // Handle the Click event of Proceed Order Button
        proceed_checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate Address Form Inputs
                boolean isValidData = validateAddressForm();

                if (isValidData) {
                    // New Instance of AddressDetails
                    AddressDetails shippingAddress = new AddressDetails();

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
                    shippingAddress.setFirstname(firstName);
                    shippingAddress.setLastname(lastName);
                    shippingAddress.setCountryName(input_country.getText().toString().trim());
                    shippingAddress.setZoneName(null);
                    shippingAddress.setCity(input_city.getText().toString().trim());
                    shippingAddress.setStreet(input_address.getText().toString().trim());
                    shippingAddress.setPostcode(null);
                    shippingAddress.setZoneId(null);
                    shippingAddress.setCountriesId(selectedCountryID);
                    shippingAddress.setTelephone(input_telephone.getText().toString().trim());
                    shippingAddress.setEmail(input_email.getText().toString().trim());

                    // Save the AddressDetails
                    ((App) getContext().getApplicationContext()).setShippingAddress(shippingAddress);


                    // Check if an Address is being Edited
                    if (isUpdate) {
                        // Navigate to Checkout Fragment
                        ((MainActivity) getContext()).getSupportFragmentManager().popBackStack();
                    } else {
                        // Navigate to Billing_Address Fragment
                        Fragment fragment = new Billing_Address();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
                                .addToBackStack(null).commit();
                    }
                }
            }
        });


        return rootView;
    }


    //*********** Get Countries List from the Server ********//

    //old api
    /*private void RequestCountries() {

        Call<List<CountryDetails>> call = APIClient.getInstance()
                .getCountries(
                        ConstantValues.AUTHORIZATION
                );
        dialogLoader.showProgressDialog();


        call.enqueue(new Callback<List<CountryDetails>>() {
            @Override
            public void onResponse(Call<List<CountryDetails>> call, Response<List<CountryDetails>> response) {

                if (response.isSuccessful()) {
                    if (!response.body().isEmpty()) {

                        countryList = response.body();

                        // Add the Country Names to the countryNames List
                        for (int i = 0; i < countryList.size(); i++) {
                            if (countryList.get(i).getCountriesName() != null) {
                                countryNames.add(countryList.get(i).getCountriesName());
                            }
                        }

                        dialogLoader.hideProgressDialog();

//                        countryNames.add("Other");

                    } else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                        dialogLoader.hideProgressDialog();

                    }
                } else {
                    if (getContext() != null) {

                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CountryDetails>> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                if (getContext() != null) {

                    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/
    private void RequestCountriesandCities(String selectedCountryID) {
        dialogLoader.showProgressDialog();
        Call<CityMainModel> call = APIClient1.getInstance()
                .getCountries_n_Cities(selectedCountryID);

        call.enqueue(new Callback<CityMainModel>() {
            @Override
            public void onResponse(Call<CityMainModel> call, Response<CityMainModel> response) {

                if (response.isSuccessful()) {
                    dialogLoader.hideProgressDialog();
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
                        dialogLoader.hideProgressDialog();

                        // Unable to get Success status
                    //    Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    dialogLoader.hideProgressDialog();

             //       Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CityMainModel> call, Throwable t) {
                dialogLoader.hideProgressDialog();

        //        Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }

    //*********** Get Zones List of the Country from the Server ********//

    private void RequestZones(String countryID) {

        Call<Zones> call = APIClient.getInstance()
                .getZones
                        (
                                countryID,
                                ConstantValues.AUTHORIZATION
                        );
        dialogLoader.showProgressDialog();


        call.enqueue(new Callback<Zones>() {
            @Override
            public void onResponse(Call<Zones> call, Response<Zones> response) {
                dialogLoader.hideProgressDialog();

                if (response.isSuccessful()) {

                    if (!response.body().getSuccess().isEmpty()) {

//                        zoneNames.clear();
//                        zoneList = response.body().getData();
//
//                        // Add the Zone Names to the zoneNames List
//                        for (int i = 0; i < zoneList.size(); i++) {
//                            zoneNames.add(zoneList.get(i).getZoneName());
//                        }

//                        zoneNames.add("Other");

                    } else {
                        // Unable to get Success status
                    //    Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    if (getContext() != null) {

                //        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Zones> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                if (getContext() != null) {

              //      Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //*********** Get the User's Default Address from all the Addresses ********//

    private void filterDefaultAddress(AddressData addressData) {

        // Get CountryList from Response
        List<AddressDetails> addressesList = addressData.getData();

        // Initialize new AddressDetails for DefaultAddress
        AddressDetails defaultAddress = new AddressDetails();


        for (int i = 0; i < addressesList.size(); i++) {
            // Check if the Current Address is User's Default Address
//            if (addressesList.get(i).getAddressId() == addressesList.get(i).getDefaultAddress()) {
            // Set the Default AddressDetails
            defaultAddress = addressesList.get(i);
//            }
        }




        // Set Default Address Data and Display to User
        selectedCountryID = defaultAddress.getCountriesId();
        input_fullname.setText(defaultAddress.getFirstname()+" "+defaultAddress.getLastname());
        input_address.setText(defaultAddress.getStreet().get(0));
        input_country.setText(defaultAddress.getCountryName());
        input_city.setText(defaultAddress.getCity());

        // Request Zones of selected Country
//        RequestZones(String.valueOf(selectedCountryID));
    }


    //*********** Request List of User Addresses ********//

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

                        // Filter all the Addresses to get the Default Address
                        filterDefaultAddress(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<AddressData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                if (getContext() != null) {

               //     Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //*********** Validate Address Form Inputs ********//

    private boolean validateAddressForm() {
        if (!ValidateInputs.isValidName(input_fullname.getText().toString().trim())) {
            input_fullname.setError(getString(R.string.invalid_name));
            return false;
        } else if (!ValidateInputs.isValidInput(input_address.getText().toString().trim())) {
            input_address.setError(getString(R.string.invalid_address));
            return false;
        } else if (!ValidateInputs.isValidInput(input_country.getText().toString().trim())) {
            input_country.setError(getString(R.string.select_country));
            return false;
        }  else if (!ValidateInputs.isValidInput(input_city.getText().toString().trim())) {
            input_city.setError(getString(R.string.enter_city));
            return false;
        } else if (!ValidateInputs.isValidPhoneNo(input_telephone.getText().toString().trim())) {
            /*input_telephone.setError(getString(R.string.bt_use_a_different_phone_number));*/
            input_telephone.setError(getString(R.string.invalid_contact));
            return false;
        } else if (!ValidateInputs.isValidEmail(input_email.getText().toString().trim())) {
            input_email.setError(getString(R.string.invalid_email));
            return false;
        } else {
            return true;
        }
    }

}


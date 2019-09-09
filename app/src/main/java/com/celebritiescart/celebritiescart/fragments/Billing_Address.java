package com.celebritiescart.celebritiescart.fragments;


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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.DialogLoader;
import com.celebritiescart.celebritiescart.models.address_model.AddressDetails;
import com.celebritiescart.celebritiescart.models.address_model.Zones;
import com.celebritiescart.celebritiescart.models.cart_model.AddressInformationMainBody;
import com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response.AddressInformation;
import com.celebritiescart.celebritiescart.models.cart_model.shipping_response.ShippingInformationResponse;
import com.celebritiescart.celebritiescart.models.city_model.Cities;
import com.celebritiescart.celebritiescart.models.city_model.CityMainModel;
import com.celebritiescart.celebritiescart.models.city_model.Countries;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.network.APIClient1;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.utils.ValidateInputs;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Billing_Address extends Fragment {

    View rootView;

    Boolean isUpdate = false;
    String customerID, defaultAddressID;

    String selectedCountryID,selectedCityID;


    List<String> countryNames;
    List<String> cityNames;

    List<Countries> countryList = new ArrayList<>();
    List<Cities>cityList=new ArrayList<>();

    ArrayAdapter<String> countryAdapter;

    Button proceed_checkout_btn;
    CheckBox default_shipping_checkbox;
    EditText input_fullname, input_address, input_country, input_city, input_telephone, input_email;

    private DialogLoader dialogLoader;

    ArrayAdapter<String> cityAdapter;




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
        dialogLoader = new DialogLoader(getContext());

        // Set the Title of Toolbar
        SpannableString s = new SpannableString(getString(R.string.billing_address));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

        // Get the customersID and defaultAddressID from SharedPreferences
        customerID = "Bearer "+getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        defaultAddressID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userDefaultAddressID", "");


        // Binding Layout Views
        input_fullname = rootView.findViewById(R.id.fullname);
        input_address = rootView.findViewById(R.id.address);
        input_country = rootView.findViewById(R.id.country);
        input_city = rootView.findViewById(R.id.city);
        input_telephone = rootView.findViewById(R.id.input_telephone);
        input_email = rootView.findViewById(R.id.input_email);
        default_shipping_checkbox = rootView.findViewById(R.id.default_shipping_checkbox);
        proceed_checkout_btn = rootView.findViewById(R.id.save_address_btn);


        // Set KeyListener of some View to null
        input_country.setKeyListener(null);

        countryNames = new ArrayList<>();


        // Set the text of Button
        proceed_checkout_btn.setText(getContext().getString(R.string.next));


        // If an existing Address is being Edited
        if (isUpdate) {
            // Get the Address details from AppContext
            AddressDetails billingAddress = ((App) getContext().getApplicationContext()).getBillingAddress();

            // Set the Address details
            selectedCountryID = billingAddress.getCountriesId();
            input_fullname.setText(billingAddress.getFirstname()+" "+billingAddress.getLastname());
            input_address.setText(billingAddress.getStreet().get(0));
            input_country.setText(billingAddress.getCountryName());
            input_city.setText(billingAddress.getCity());
            input_email.setText(billingAddress.getEmail());
//            RequestZones(String.valueOf(selectedCountryID));

        } else {
            // Get the Shipping AddressDetails from AppContext that is being Edited
            AddressDetails shippingAddress = ((App) getContext().getApplicationContext()).getShippingAddress();

            // Set the Address details
            selectedCountryID = shippingAddress.getCountriesId();
            input_fullname.setText(shippingAddress.getFirstname()+" "+shippingAddress.getLastname());
            input_address.setText(shippingAddress.getStreet().get(0));
            input_country.setText(shippingAddress.getCountryName());
            input_city.setText(shippingAddress.getCity());
            input_telephone.setText(shippingAddress.getTelephone());
            input_email.setText(shippingAddress.getEmail());
//            RequestZones(String.valueOf(selectedCountryID));

            default_shipping_checkbox.setChecked(true);
        }

        // Request for Countries List
        RequestCountriesandCities(selectedCountryID);

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
//                            RequestZones(String.valueOf(selectedCountryID));
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

                            String cityID = "";
                            input_city.setText(selectedItem);

                            if (!selectedItem.equalsIgnoreCase("Other")) {

                                for (int i = 0; i < cityList.size(); i++) {
                                    if (cityList.get(i).getCode() != null) {
                                        if (cityList.get(i).getCode().equalsIgnoreCase(selectedItem)) {
                                            // Get the ID of selected Country
                                            cityID = cityList.get(i).getCode();
                                        }
                                    }
                                }

                            }

                            selectedCityID = cityID;


                            // Request for all Zones in the selected Country
//                            RequestZones(String.valueOf(selectedCountryID));
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
//                if (event.getAction() == MotionEvent.ACTION_UP && zoneNames.size() > 0) {
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
//                }
//
//                return false;
//            }
//        });


        // Handle the Click event of Default Shipping Address CheckBox
        default_shipping_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Check if the CheckBox is Checked
                if (isChecked) {
                    // Get the Shipping AddressDetails from AppContext that is being Edited
                    AddressDetails shippingAddress = ((App) getContext().getApplicationContext()).getShippingAddress();

//                    selectedZoneID = shippingAddress.getZoneId();
                    selectedCountryID = shippingAddress.getCountriesId();

                    input_fullname.setText(shippingAddress.getFirstname()+" "+shippingAddress.getLastname());
                    input_address.setText(shippingAddress.getStreet().get(0));
                    input_country.setText(shippingAddress.getCountryName());
                    input_city.setText(shippingAddress.getCity());
                    input_telephone.setText(shippingAddress.getTelephone());
                    input_email.setText(shippingAddress.getEmail());

                } else {
                    input_fullname.setText("");
                    input_address.setText("");
                    input_country.setText("");
                    input_city.setText("");
                    input_telephone.setText("");
                    input_email.setText("");
                }
            }
        });


        // Handle the Click event of Proceed Order Button
        proceed_checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate Address Form Inputs
                boolean isValidData = validateAddressForm();

                if (isValidData) {
                    // New Instance of AddressDetails
                    AddressDetails billingAddress = new AddressDetails();
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
                    billingAddress.setFirstname(firstName);
                    billingAddress.setLastname(lastName);
                    billingAddress.setCountryName(input_country.getText().toString().trim());
                    billingAddress.setCity(input_city.getText().toString().trim());
                    billingAddress.setStreet(input_address.getText().toString().trim());
                    billingAddress.setTelephone(input_telephone.getText().toString().trim());
                    billingAddress.setEmail(input_email.getText().toString().trim());
                    billingAddress.setCountriesId(selectedCountryID);

                    // Save the AddressDetails
                    ((App) getContext().getApplicationContext()).setBillingAddress(billingAddress);


                    // Check if an Address is being Edited
                    if (isUpdate) {
                        // Navigate to Checkout Fragment
                        ((MainActivity) getContext()).getSupportFragmentManager().popBackStack();
                    } else {
                        // Navigate to Shipping_Methods Fragment


//                        Fragment fragment = new Shipping_Methods();
//                        FragmentManager fragmentManager = getFragmentManager();
//                        fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
//                                .addToBackStack(null).commit();
                        StoreShippingInformation();
                    }

                }
            }
        });


        return rootView;
    }


    //*********** Add shipping/billing info to the cart/server ********//

    private void StoreShippingInformation() {
        AddressInformation addressInformation = new AddressInformation();
        addressInformation.setBillingAddress(((App) getContext().getApplicationContext()).getBillingAddress());
        if (default_shipping_checkbox.isChecked()) {
            ((App) getContext().getApplicationContext()).getShippingAddress().setDefaultAddress(1);
        } else {
            ((App) getContext().getApplicationContext()).getShippingAddress().setDefaultAddress(0);
        }
        addressInformation.setShippingAddress(((App) getContext().getApplicationContext()).getShippingAddress());
        AddressInformationMainBody info = new AddressInformationMainBody(addressInformation);

        Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().serializeNulls().create();

        Call<ShippingInformationResponse> call;
        if (ConstantValues.IS_USER_LOGGED_IN) {
            call = APIClient.getInstance()
                    .addCustomerShippingInformation(
                            customerID, info
                    );
        } else {
            call = APIClient.getInstance()
                    .addShippingInformation(
                            ConstantValues.AUTHORIZATION, info, ((App) getContext().getApplicationContext()).getGuestCartID()
                    );
        }
        dialogLoader.showProgressDialog();


        call.enqueue(new Callback<ShippingInformationResponse>() {
            @Override
            public void onResponse(Call<ShippingInformationResponse> call, Response<ShippingInformationResponse> response) {

                if (response.isSuccessful()) {
                    dialogLoader.hideProgressDialog();


                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CartInfo", response.body());

//                    Fragment fragment = new Shipping_Methods();
                    Fragment fragment = new Checkout();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
                            .addToBackStack(null).commit();

                } else {
                    dialogLoader.hideProgressDialog();
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (response.code() == 400) {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            if (jObjError.isNull("parameters")) {
                                if (getContext() != null) {
                                //    Toast.makeText(getContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                JSONArray jsonArray = jObjError.getJSONArray("parameters");

                                List<String> params = new ArrayList<String>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    params.add(jsonArray.getString(i));
                                }
                                if (params.size() > 0) {
                                    if (getContext() != null) {

                                    //    Toast.makeText(getContext(), jObjError.getString("message").replace("%1", params.get(0)), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

//                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShippingInformationResponse> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                if (getContext() != null) {

                //    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //*********** Get Countries List from the Server ********//

    //Old Api
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
                    dialogLoader.hideProgressDialog();


                    if (!response.body().isEmpty()) {

                        countryList = response.body();

                        // Add the Country Names to the countryNames List
                        for (int i = 0; i < countryList.size(); i++) {
                            if (countryList.get(i).getCountriesName() != null) {
                                countryNames.add(countryList.get(i).getCountriesName());
                            }
                        }

                        countryNames.add("Other");

                    } else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    dialogLoader.hideProgressDialog();
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

        Call<CityMainModel> call = APIClient1.getInstance()
                .getCountries_n_Cities(selectedCountryID);

        dialogLoader.showProgressDialog();

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

                //    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CityMainModel> call, Throwable t) {
             //   Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
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

                if (response.isSuccessful()) {
                    dialogLoader.hideProgressDialog();

                    if (!response.body().getSuccess().isEmpty()) {

//                        zoneNames.clear();
//                        zoneList = response.body().getData();
//
//                        // Add the Zone Names to the zoneNames List
//                        for (int i = 0; i < zoneList.size(); i++) {
//                            zoneNames.add(zoneList.get(i).getZoneName());
//                        }
//
//                        zoneNames.add("Other");

                    } else {
                        dialogLoader.hideProgressDialog();

                        // Unable to get Success status
                    //    Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    if (getContext() != null) {

                    //    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Zones> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                if (getContext() != null) {

                //    Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //*********** Validate Address Form Inputs ********//
    private boolean validateAddressForm() {
        if ("".equals(input_fullname.getText().toString().trim())) {
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
           /* input_telephone.setError(getString(R.string.bt_use_a_different_phone_number));*/
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


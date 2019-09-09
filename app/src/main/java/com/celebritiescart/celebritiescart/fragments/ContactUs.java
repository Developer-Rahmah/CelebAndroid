package com.celebritiescart.celebritiescart.fragments;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.CustomScrollMapFragment;
import com.celebritiescart.celebritiescart.customs.DialogLoader;
import com.celebritiescart.celebritiescart.models.device_model.AppSettingsDetails;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.utils.ValidateInputs;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ContactUs extends Fragment implements OnMapReadyCallback {

    View rootView;

    DialogLoader dialogLoader;

    Button btn_contact_us;
    EditText ed_name, ed_email, ed_message;
    TextView tv_address, tv_email, tv_telephone;
    CoordinatorLayout coordinator_container;

    private GoogleMap mGoogleMap;
    private AppSettingsDetails appSettings;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.contact_us, container, false);

        if (getActivity().findViewById(R.id.myLayout).getVisibility() != View.GONE) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }


        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        SpannableString s = new SpannableString(getString(R.string.actionContactUs));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

        // Get AppSettingsDetails from ApplicationContext
        appSettings = ((App) getContext().getApplicationContext()).getAppSettingsDetails();


        // Binding Layout Views
        btn_contact_us = rootView.findViewById(R.id.btn_contact_us);
        ed_name = rootView.findViewById(R.id.ed_name);
        ed_email = rootView.findViewById(R.id.ed_email);
        ed_message = rootView.findViewById(R.id.ed_message);
        tv_address = rootView.findViewById(R.id.tv_address);
        tv_email = rootView.findViewById(R.id.tv_email);
        tv_telephone = rootView.findViewById(R.id.tv_telephone);
        coordinator_container = rootView.findViewById(R.id.coordinator_container);

        /*SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.user_location_map));
        mapFragment.getMapAsync(this);*/

        dialogLoader = new DialogLoader(getContext());


        tv_address.setText(appSettings.getAddress());
        tv_email.setText(appSettings.getContactUsEmail());
        tv_telephone.setText(appSettings.getPhoneNo());

        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), ConstantValues.LANGUAGE_ID==1 ?"font/baskvill_regular.OTF":"font/baskvill_regular.OTF");
        tv_telephone.setTypeface(custom_font);
        tv_address.setTypeface(custom_font);
        tv_email.setTypeface(custom_font);
        if(  ConstantValues.LANGUAGE_ID==1) {

            tv_telephone.setGravity(Gravity.START);
        }else{
            tv_telephone.setGravity(Gravity.END);

        }
        btn_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateInputs.isValidName(ed_name.getText().toString())) {
                    if (ValidateInputs.isValidEmail(ed_email.getText().toString())) {
                        if (!"".equalsIgnoreCase(ed_message.getText().toString())) {

                            ContactWithUs();

                        } else {
                            ed_message.setError(getString(R.string.enter_message));
                        }
                    } else {
                        ed_email.setError(getString(R.string.invalid_email));
                    }
                } else {
                    ed_name.setError(getString(R.string.enter_name));
                }

            }
        });


        return rootView;
    }


    //*********** Called after onCreateView() has returned, but before any saved state has been restored in to the view ********//

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CustomScrollMapFragment mapFragment = ((CustomScrollMapFragment) getChildFragmentManager().findFragmentByTag("mapFragment"));
        if (mapFragment == null) {
            mapFragment = new CustomScrollMapFragment();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.user_location_map, mapFragment, "mapFragment")
                    .commit();
            getChildFragmentManager().executePendingTransactions();
        }
        mapFragment.getMapAsync(this);

        mapFragment.setListener(new CustomScrollMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                coordinator_container.requestDisallowInterceptTouchEvent(true);
            }
        });
    }


    //*********** Triggered when the Map is ready to be used ********//

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //googleMap.setMyLocationEnabled(true);
        mGoogleMap.setTrafficEnabled(false);
        mGoogleMap.setIndoorEnabled(false);
        mGoogleMap.setBuildingsEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);


        double latitude = Double.parseDouble(appSettings.getLatitude());
        double longitude = Double.parseDouble(appSettings.getLongitude());

        drawMarker(latitude, longitude);
    }


    //*********** Draws location marker on given location ********//

    private void drawMarker(double latitude, double longitude) {
        mGoogleMap.clear();

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(new LatLng(latitude, longitude));
        markerOptions.title(ConstantValues.APP_HEADER);
        markerOptions.snippet("Lat:" + latitude + ", Lng:" + longitude);

        markerOptions.draggable(false);

        mGoogleMap.addMarker(markerOptions);

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
    }


    /*********** Send Feedback to the Server ********/

    public void ContactWithUs() {

        dialogLoader.showProgressDialog();
        new ContactUsPing().execute();
//        Call<String> call = APIClient.getInstance()
//                .contactUs
//                        (
//                                ConstantValues.ECOMMERCE_BASE_URL+"contactapi.php",
//                                "application/json",
//                                ed_name.getText().toString().trim(),
//                                ed_email.getText().toString().trim(),
//                                ed_message.getText().toString().trim()
//                        );


//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
//
//                dialogLoader.hideProgressDialog();
//
//                if (response.isSuccessful()) {
////                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
//
//                    Snackbar.make(rootView, response.body(), Snackbar.LENGTH_SHORT).show();
//                    Log.i("errornae",""+response.body());
//
////                    }
//
//
//                } else {
//                    if (getContext() != null) {
//                        try {
//                            Log.i("error",""+response.errorBody().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        Toast.makeText(getContext(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                dialogLoader.hideProgressDialog();
//                if (getContext() != null) {
//
//                    Toast.makeText(getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
//                    Log.i("kuch aur",""+t);
//
//                }
//            }
//        });
    }

    private class ContactUsPing extends AsyncTask<String, Void, String> {

        private Exception exception;

        private String executeReq(URL urlObject) throws IOException {
            HttpURLConnection conn = null;

            conn = (HttpURLConnection) urlObject.openConnection();
            conn.setReadTimeout(100000); //Milliseconds
            conn.setConnectTimeout(150000); //Milliseconds
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Start connect
            conn.connect();
            String response = convertStreamToString(conn.getInputStream());
            dialogLoader.hideProgressDialog();

//            Log.d("Response:", response);
        //    Snackbar.make(rootView, response, Snackbar.LENGTH_SHORT).show();
            return response;

        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

        protected String doInBackground(String... urls) {
            try {
                String parameters = "?fullname=" + ed_name.getText().toString().trim() + "&email=" + ed_email.getText().toString().trim() + "&message=" + ed_message.getText().toString().trim(); //
                URL url = new URL(ConstantValues.ECOMMERCE_BASE_URL + "contactapi.php" + parameters);
                return executeReq(url);


            } catch (Exception e) {
            //    Toast.makeText(getContext(), "NetworkCallFailure : " + e, Toast.LENGTH_LONG).show();
//                Log.i("kuch aur",""+e);
                return "";
            }
        }

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

}


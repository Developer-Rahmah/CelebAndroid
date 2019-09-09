package com.celebritiescart.celebritiescart.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.celebritiescart.celebritiescart.adapters.LanguagesAdapter;
import com.celebritiescart.celebritiescart.app.MyAppPrefsManager;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.models.language_model.LanguageData;
import com.celebritiescart.celebritiescart.models.language_model.LanguageDetails;
import com.celebritiescart.celebritiescart.network.StartAppRequests;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class Languages extends Fragment {

    View rootView;
    
    MyAppPrefsManager appPrefs;
    
    String selectedLanguageID;
    String selectedLanguageCode;
    
    AdView mAdView;
    Button saveLanguageBtn;
    ListView languageListView;
    FrameLayout banner_adView;
    
    LanguagesAdapter languagesAdapter;
    List<LanguageDetails> languagesList;
    
    private CheckBox lastChecked_CB = null;
    
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.languages, container, false);

        if (getActivity().findViewById(R.id.myLayout).getVisibility()!=View.GONE ) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }
    
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        SpannableString s = new SpannableString(getString(R.string.actionLanguage));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);
    
    
        appPrefs = new MyAppPrefsManager(getContext());
        
        selectedLanguageCode = appPrefs.getUserLanguageCode();
        selectedLanguageID = String.valueOf(appPrefs.getUserLanguageId());
        
    
        // Binding Layout Views
        banner_adView = rootView.findViewById(R.id.banner_adView);
        saveLanguageBtn = rootView.findViewById(R.id.btn_save_language);
        languageListView = rootView.findViewById(R.id.languages_list);
    
        
        if (ConstantValues.IS_ADMOBE_ENABLED) {
            // Initialize Admobe
            mAdView = new AdView(getContext());
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(ConstantValues.AD_UNIT_ID_BANNER);
            AdRequest adRequest = new AdRequest.Builder().build();
            banner_adView.addView(mAdView);
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener(){
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    banner_adView.setVisibility(View.VISIBLE);
                }
                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    banner_adView.setVisibility(View.GONE);
                }
            });
        }
        
    
        languagesList = new ArrayList<>();
    
        // Request Languages
        RequestLanguages();


    
    
        // Initialize the LanguagesAdapter for RecyclerView
        languagesAdapter = new LanguagesAdapter(getContext(), languagesList, this);
    
        languageListView.setAdapter(languagesAdapter);
        languagesAdapter.notifyDataSetChanged();



        saveLanguageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            
                if (!selectedLanguageID.equalsIgnoreCase(String.valueOf(appPrefs.getUserLanguageId()))) {
                    // Change Language
                    
                    appPrefs.setUserLanguageCode(selectedLanguageCode);
                    appPrefs.setUserLanguageId(Integer.parseInt(selectedLanguageID));




                    ConstantValues.LANGUAGE_ID = appPrefs.getUserLanguageId();
                    ConstantValues.LANGUAGE_CODE = appPrefs.getUserLanguageCode();
    
    
                    ChangeLocaleTask changeLocaleTask = new ChangeLocaleTask();
                    changeLocaleTask.execute();
                    
                }
            }
        });


        return rootView;
    }
    
    
    
    //*********** Recreates Activity ********//
    
    private void recreateActivity() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }
    
    
    public void setLastCheckedCB(CheckBox lastChecked_CB) {
        this.lastChecked_CB = lastChecked_CB;
    }
    
    
    public String getSelectedLanguageID() {
        return selectedLanguageID;
    }
    
    
    
    //*********** Adds Orders returned from the Server to the OrdersList ********//
    
    private void addLanguages(LanguageData languageData) {
        
        languagesList.addAll(languageData.getLanguages());
        
        
        if (selectedLanguageID.equalsIgnoreCase("") && languagesList.size() != 0) {
    
            selectedLanguageID = languagesList.get(0).getLanguagesId();
            selectedLanguageCode = languagesList.get(0).getCode();
            
            for (int i=0;  i<languagesList.size();  i++) {
                if (languagesList.get(i).getIsDefault().equalsIgnoreCase("1")) {
                    selectedLanguageCode = languagesList.get(i).getCode();
                    selectedLanguageID = languagesList.get(i).getLanguagesId();
                }
            }
            
        }
        else {
            for (int i=0;  i<languagesList.size();  i++) {
                if (languagesList.get(i).getLanguagesId().equalsIgnoreCase(String.valueOf(appPrefs.getUserLanguageId()))) {
                    selectedLanguageCode = languagesList.get(i).getCode();
                    selectedLanguageID = languagesList.get(i).getLanguagesId();
                }
            }
        }
        
        

        
        languageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                CheckBox currentChecked_CB = view.findViewById(R.id.cb_language);
                LanguageDetails language = (LanguageDetails) parent.getAdapter().getItem(position);
                
                
                // UnCheck last Checked CheckBox
                if (lastChecked_CB != null) {
                    lastChecked_CB.setChecked(false);
                }
                
                currentChecked_CB.setChecked(true);
                lastChecked_CB = currentChecked_CB;
    
    
                selectedLanguageID = language.getLanguagesId();
                selectedLanguageCode = language.getCode();
            }
        });
        
    }
    
    
    
    //*********** Request App Languages from the Server ********//
    
    public void RequestLanguages() {


        String json = "{\n" +
                "    \"success\": \"1\",\n" +
                "    \"languages\": [\n" +
                "        {\n" +
                "            \"languages_id\": 1,\n" +
                "            \"name\": \"English\",\n" +
                "            \"code\": \"en\",\n" +
                "            \"image\": \"https://static.thenounproject.com/png/1454057-200.png\",\n" +
                "            \"directory\": \"english\",\n" +
                "            \"sort_order\": 1,\n" +
                "            \"direction\": \"ltr\",\n" +
                "            \"is_default\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"languages_id\": 2,\n" +
                "            \"name\": \"العَرَبِيَّة\",\n" +
                "            \"code\": \"ar\",\n" +
                "            \"image\": \"https://static.thenounproject.com/png/1355749-200.png\",\n" +
                "            \"directory\": \"arabic\",\n" +
                "            \"sort_order\": 4,\n" +
                "            \"direction\": \"rtl\",\n" +
                "            \"is_default\": 0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"message\": \"Returned all languages.\"\n" +
                "}";
        LanguageData languageData = new Gson().fromJson(json, LanguageData.class);


//        Call<LanguageData> call = APIClient.getInstance()
//                .getLanguages();
        if(languageData.getSuccess().equalsIgnoreCase("1")) {
            addLanguages(languageData);
        }






//        call.enqueue(new Callback<LanguageData>() {
//            @Override
//            public void onResponse(Call<LanguageData> call, retrofit2.Response<LanguageData> response) {
//
//                if (response.isSuccessful()) {
//                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
//
//                        // Languages have been returned. Add Languages to the languageList
//                        addLanguages(response.body());
//
//                    }
//                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
//                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
//
//                    }
//                    else {
//                        // Unable to get Success status
//                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
//                    }
//                }
//                else {
//                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LanguageData> call, Throwable t) {
//                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
//            }
//        });
    }
    
    
    
    
    private class ChangeLocaleTask extends AsyncTask<Void, Void, Void> {
    
        @Override
        protected Void doInBackground(Void... params) {
    
            // Recall some Network Requests
            StartAppRequests startAppRequests = new StartAppRequests(getContext());
            startAppRequests.StartRequests();
            
            return null;
        }
    
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            
            recreateActivity();
        }
    }
}


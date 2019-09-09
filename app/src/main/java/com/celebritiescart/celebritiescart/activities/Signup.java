package com.celebritiescart.celebritiescart.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.CircularImageView;
import com.celebritiescart.celebritiescart.customs.DialogLoader;
import com.celebritiescart.celebritiescart.customs.LocaleHelper;
import com.celebritiescart.celebritiescart.helpers.AppWebViewClients;
import com.celebritiescart.celebritiescart.models.CMSRequest;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.network.SignupRequest;
import com.celebritiescart.celebritiescart.network.SignupResponse;
import com.celebritiescart.celebritiescart.utils.CheckPermissions;
import com.celebritiescart.celebritiescart.utils.ImagePicker;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.utils.Utilities;
import com.celebritiescart.celebritiescart.utils.ValidateInputs;
import com.celebritiescart.celebritiescart.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.celebritiescart.celebritiescart.app.App.getContext;


/**
 * SignUp activity handles User's Registration
 **/

public class Signup extends AppCompatActivity {
    final int sdk = android.os.Build.VERSION.SDK_INT;

    View parentView;
    String profileImage;
    private static final int PICK_IMAGE_ID = 360;           // the number doesn't matter

    Toolbar toolbar;
    ActionBar actionBar;

    DialogLoader dialogLoader;

    AdView mAdView;
    Button signupBtn;
    FrameLayout banner_adView;
    TextView signup_loginText;
    TextView service_terms, privacy_policy, refund_policy, and_text;
    CircularImageView user_photo;
    FloatingActionButton user_photo_edit_fab;
    EditText user_firstname, user_lastname, user_email, user_password, user_mobile;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        boolean handleReturn = super.dispatchTouchEvent(ev);

        View view = getCurrentFocus();

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        /*if (view instanceof EditText) {
            View innerView = getCurrentFocus();

            if (ev.getAction() == MotionEvent.ACTION_UP &&
                    !getLocationOnScreen((EditText) innerView).contains(x, y)) {

                InputMethodManager input = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(getWindow().getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }*/

        return handleReturn;
    }


    private void RequestStaticPagesData() {

        getFAQs();
        getAboutUs();
        getPrivacyPolicy();
        getTerms();
        getRefundPolicy();

    }


    public void getPrivacyPolicy() {
        dialogLoader.showProgressDialog();
        if(  ConstantValues.LANGUAGE_ID==1) {

            Call<CMSRequest> call = APIClient.getInstance()
                    .getPrivacyPolicy
                            (
                                    ConstantValues.AUTHORIZATION
                            );
            call.enqueue(new Callback<CMSRequest>() {
                @Override
                public void onResponse(Call<CMSRequest> call, retrofit2.Response<CMSRequest> response) {

                    dialogLoader.hideProgressDialog();


                    if (response.isSuccessful()) {
                        ConstantValues.PRIVACY_POLICY = response.body().getContent();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<CMSRequest> call, Throwable t) {
                    dialogLoader.hideProgressDialog();
                }
            });
        }else {
            Call<CMSRequest> call = APIClient.getInstance()
                    .getPrivacyPolicyAr
                            (
                            );
            call.enqueue(new Callback<CMSRequest>() {
                @Override
                public void onResponse(Call<CMSRequest> call, retrofit2.Response<CMSRequest> response) {

                    dialogLoader.hideProgressDialog();


                    if (response.isSuccessful()) {
                        ConstantValues.PRIVACY_POLICY = response.body().getContent();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<CMSRequest> call, Throwable t) {
                    dialogLoader.hideProgressDialog();
                }
            });
        }
    }

    public void getFAQs() {
        dialogLoader.showProgressDialog();


        if(  ConstantValues.LANGUAGE_ID==1){

            Call<CMSRequest> call = APIClient.getInstance()
                    .getfaqs
                            (
                                    ConstantValues.AUTHORIZATION
                            );
            call.enqueue(new Callback<CMSRequest>() {
                @Override
                public void onResponse(Call<CMSRequest> call, retrofit2.Response<CMSRequest> response) {

                    dialogLoader.hideProgressDialog();


                    if (response.isSuccessful()) {
                        ConstantValues.FAQS = response.body().getContent();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<CMSRequest> call, Throwable t) {
                    dialogLoader.hideProgressDialog();
                }
            });
        }

        else {
            Call<CMSRequest> call = APIClient.getInstance()
                    .getfaqsAr
                            (
                                    ConstantValues.AUTHORIZATION
                            );
            call.enqueue(new Callback<CMSRequest>() {
                @Override
                public void onResponse(Call<CMSRequest> call, retrofit2.Response<CMSRequest> response) {

                    dialogLoader.hideProgressDialog();


                    if (response.isSuccessful()) {
                        ConstantValues.FAQS = response.body().getContent();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<CMSRequest> call, Throwable t) {
                    dialogLoader.hideProgressDialog();
                }
            });
        }
    }

    public void getRefundPolicy() {
        dialogLoader.showProgressDialog();
        if(  ConstantValues.LANGUAGE_ID==1) {

            Call<CMSRequest> call = APIClient.getInstance()
                    .getRefundPolicy
                            (
                                    ConstantValues.AUTHORIZATION
                            );
            call.enqueue(new Callback<CMSRequest>() {
                @Override
                public void onResponse(Call<CMSRequest> call, retrofit2.Response<CMSRequest> response) {

                    dialogLoader.hideProgressDialog();


                    if (response.isSuccessful()) {
                        ConstantValues.REFUND_POLICY = response.body().getContent();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<CMSRequest> call, Throwable t) {
                    dialogLoader.hideProgressDialog();
                }
            });
        }else {

            Call<CMSRequest> call = APIClient.getInstance()
                    .getRefundPolicyAr
                            (
                                    ConstantValues.AUTHORIZATION
                            );
            call.enqueue(new Callback<CMSRequest>() {
                @Override
                public void onResponse(Call<CMSRequest> call, retrofit2.Response<CMSRequest> response) {

                    dialogLoader.hideProgressDialog();


                    if (response.isSuccessful()) {
                        ConstantValues.REFUND_POLICY = response.body().getContent();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<CMSRequest> call, Throwable t) {
                    dialogLoader.hideProgressDialog();
                }
            });

        }
    }

    public void getTerms() {
        dialogLoader.showProgressDialog();
        if(  ConstantValues.LANGUAGE_ID==1) {

            Call<CMSRequest> call = APIClient.getInstance()
                    .getTermsConditions
                            (
                                    ConstantValues.AUTHORIZATION
                            );
            call.enqueue(new Callback<CMSRequest>() {
                @Override
                public void onResponse(Call<CMSRequest> call, retrofit2.Response<CMSRequest> response) {

                    dialogLoader.hideProgressDialog();


                    if (response.isSuccessful()) {
                        ConstantValues.TERMS_SERVICES = response.body().getContent();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<CMSRequest> call, Throwable t) {
                    dialogLoader.hideProgressDialog();
                }
            });
        }else {
            Call<CMSRequest> call = APIClient.getInstance()
                    .getTermsConditionsAr
                            (
                                    ConstantValues.AUTHORIZATION
                            );
            call.enqueue(new Callback<CMSRequest>() {
                @Override
                public void onResponse(Call<CMSRequest> call, retrofit2.Response<CMSRequest> response) {

                    dialogLoader.hideProgressDialog();


                    if (response.isSuccessful()) {
                        ConstantValues.TERMS_SERVICES = response.body().getContent();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<CMSRequest> call, Throwable t) {
                    dialogLoader.hideProgressDialog();
                }
            });
        }
    }

    public void getAboutUs() {
        dialogLoader.showProgressDialog();

        Call<CMSRequest> call = APIClient.getInstance()
                .getAboutUs
                        (
                                ConstantValues.AUTHORIZATION
                        );
        call.enqueue(new Callback<CMSRequest>() {
            @Override
            public void onResponse(Call<CMSRequest> call, retrofit2.Response<CMSRequest> response) {

                dialogLoader.hideProgressDialog();


                if (response.isSuccessful()) {
                    ConstantValues.ABOUT_US = response.body().getContent();
                } else {

                }
            }

            @Override
            public void onFailure(Call<CMSRequest> call, Throwable t) {
                dialogLoader.hideProgressDialog();
            }
        });
    }


    protected Rect getLocationOnScreen(EditText mEditText) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        mEditText.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mEditText.getWidth();
        mRect.bottom = location[1] + mEditText.getHeight();

        return mRect;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        dialogLoader = new DialogLoader(Signup.this);

        RequestStaticPagesData();

        MobileAds.initialize(this, ConstantValues.ADMOBE_ID);


        // setting Toolbar
        toolbar = findViewById(R.id.myToolbar);
//        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        SpannableString s = new SpannableString("");
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        actionBar.setTitle(s);
//        actionBar.setDisplayHomeAsUpEnabled(false);
//

        // Binding Layout Views
        user_photo = findViewById(R.id.user_photo);
        user_firstname = findViewById(R.id.user_firstname);
        user_lastname = findViewById(R.id.user_lastname);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        user_mobile = findViewById(R.id.user_mobile);
        signupBtn = findViewById(R.id.signupBtn);
        and_text = findViewById(R.id.and);
        service_terms = findViewById(R.id.service_terms);
        privacy_policy = findViewById(R.id.privacy_policy);
        refund_policy = findViewById(R.id.refund_policy);
        signup_loginText = findViewById(R.id.signup_loginText);
        banner_adView = findViewById(R.id.banner_adView);
        user_photo_edit_fab = findViewById(R.id.user_photo_edit_fab);





        if (ConstantValues.IS_ADMOBE_ENABLED) {
            // Initialize Admobe
            mAdView = new AdView(Signup.this);
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(ConstantValues.AD_UNIT_ID_BANNER);
            AdRequest adRequest = new AdRequest.Builder().build();
            banner_adView.addView(mAdView);
            mAdView.loadAd(adRequest);
            mAdView.setAdListener(new AdListener() {
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



        and_text.setText(" " + getString(R.string.and) + " ");


        privacy_policy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RequestStaticPagesData();

                AlertDialog.Builder dialog = new AlertDialog.Builder(Signup.this, android.R.style.Theme_NoTitleBar);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);

                final ImageButton dialog_button = dialogView.findViewById(R.id.dialog_button);

                if(  ConstantValues.LANGUAGE_ID==1){

                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back) );


                    } else {
                        dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back) );

                    }
                }else {
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back_ar) );

                    } else {
                        dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back_ar) );

                    }
                }
                dialog_button.getLayoutParams().height =85;
                dialog_button.getLayoutParams().width =90;
                dialog_button.setPadding(0,0,100,0);



                final TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = dialogView.findViewById(R.id.dialog_webView);

                dialog_title.setText(getString(R.string.privacy_policy));


                String description = ConstantValues.PRIVACY_POLICY;
                String styleSheet = "<style type=\"text/css\"> " +
                        "@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/baskvill_regular.OTF\")}body {font-family: MyFont;font-size: 14px;text-align: justify;background:#ffffff; margin:0; padding:0}" +
                        "p{color:#000000;} " +
                        "img{display:inline; height:auto; max-width:100%;}" +
                        "</style>";

                dialog_webView.getSettings().setJavaScriptEnabled(true);
                dialog_webView.getSettings().setDomStorageEnabled(true);



//                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);

//                dialog_webView.setWebViewClient(new dialog_webView);

//                openURL();
                final AlertDialog alertDialog = dialog.create();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(Signup.this, R.color.colorPrimaryDark));
                }

                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();

            }
        });

        refund_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Signup.this, android.R.style.Theme_NoTitleBar);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);

                final ImageButton dialog_button = dialogView.findViewById(R.id.dialog_button);
                if(  ConstantValues.LANGUAGE_ID==1){

                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back) );


                    } else {
                        dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back) );

                    }
                }else {
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back_ar) );

                    } else {
                        dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back_ar) );

                    }
                }
                dialog_button.getLayoutParams().height =85;
                dialog_button.getLayoutParams().width =90;
                dialog_button.setPadding(0,0,100,0);
                final TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = dialogView.findViewById(R.id.dialog_webView);

                dialog_title.setText(getString(R.string.refund_policy));


                String description = ConstantValues.REFUND_POLICY;
                String styleSheet = "<style type=\"text/css\"> " +
                        "@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/baskvill_regular.OTF\")}body {font-family: MyFont;font-size: 14px;text-align: justify;background:#ffffff; margin:0; padding:0}" +
                        "p{color:#000000;} " +
                        "img{display:inline; height:auto; max-width:100%;}" +
                        "</style>";


                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);


                final AlertDialog alertDialog = dialog.create();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(Signup.this, R.color.colorPrimaryDark));
                }

                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        service_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Signup.this, android.R.style.Theme_NoTitleBar);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);

                final ImageButton dialog_button = dialogView.findViewById(R.id.dialog_button);
                if(  ConstantValues.LANGUAGE_ID==1){

                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back) );


                    } else {
                        dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back) );

                    }
                }else {
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back_ar) );

                    } else {
                        dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back_ar) );

                    }
                }
                dialog_button.getLayoutParams().height =85;
                dialog_button.getLayoutParams().width =90;
                dialog_button.setPadding(0,0,100,0);
                final TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = dialogView.findViewById(R.id.dialog_webView);

                dialog_title.setText(getString(R.string.service_terms));


                String description = ConstantValues.TERMS_SERVICES;
                String styleSheet = "<style type=\"text/css\"> " +
                        "@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/baskvill_regular.OTF\")}body {font-family: MyFont;font-size: 14px;text-align: justify;background:#ffffff; margin:0; padding:0}" +
                        "p{color:#000000;} " +
                        "img{display:inline; height:auto; max-width:100%;}" +
                        "</style>";


                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);


                final AlertDialog alertDialog = dialog.create();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(Signup.this, R.color.colorPrimaryDark));
                }

                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });


        // Handle Click event of user_photo_edit_fab FAB
        user_photo_edit_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckPermissions.is_CAMERA_PermissionGranted() && CheckPermissions.is_STORAGE_PermissionGranted()) {
                    pickImage();
                } else {
                    ActivityCompat.requestPermissions
                            (
                                    Signup.this,
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    CheckPermissions.PERMISSIONS_REQUEST_CAMERA
                            );
                }
            }
        });


        // Handle Click event of signup_loginText TextView
        signup_loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish SignUpActivity to goto the LoginActivity
                finish();
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
            }
        });


        // Handle Click event of signupBtn Button
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate Login Form Inputs
                boolean isValidData = validateForm();

                if (isValidData) {
                    parentView = v;

                    // Proceed User Registration
                    processRegistration();
                }
            }
        });
    }


    //*********** Picks User Profile Image from Gallery or Camera ********//

    private void pickImage() {
        // Get Intent with Options of Image Picker Apps from the static method of ImagePicker class
        Intent chooseImageIntent = ImagePicker.getImagePickerIntent(Signup.this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        // Start Activity with Image Picker Intent
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }


    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Handle Activity Result
            if (requestCode == PICK_IMAGE_ID) {

                // Get the User Selected Image as Bitmap from the static method of ImagePicker class
                Bitmap bitmap = ImagePicker.getImageFromResult(Signup.this, resultCode, data);

                // Upload the Bitmap to ImageView
                user_photo.setImageBitmap(bitmap);

                // Get the converted Bitmap as Base64ImageString from the static method of Helper class
                profileImage = Utilities.getBase64ImageStringFromBitmap(bitmap);

            }
        }
    }


    //*********** This method is invoked for every call on requestPermissions(Activity, String[], int) ********//

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CheckPermissions.PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // The Camera and Storage Permission is granted
                pickImage();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Signup.this, Manifest.permission.CAMERA)) {
                    // Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                    builder.setTitle(getString(R.string.permission_camera_storage));
                    builder.setMessage(getString(R.string.permission_camera_storage_needed));
                    builder.setPositiveButton(getString(R.string.grant), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions
                                    (
                                            Signup.this,
                                            new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            CheckPermissions.PERMISSIONS_REQUEST_CAMERA
                                    );
                        }
                    });
                    builder.setNegativeButton(getString(R.string.not_now), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(Signup.this, getString(R.string.permission_rejected), Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    //*********** Proceed User Registration Request ********//

    private void processRegistration() {

        dialogLoader.showProgressDialog();

        SignupRequest body = new SignupRequest(user_email.getText().toString().trim(),
                user_firstname.getText().toString().trim(),
                user_lastname.getText().toString().trim(),
                user_mobile.getText().toString().trim(),
                user_password.getText().toString().trim()
        );
//        profileImage


        Call<SignupResponse> regCall = APIClient.getInstance()
                .customerRegistration
                        (
                                ConstantValues.AUTHORIZATION,
                                body
                        );

        regCall.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, retrofit2.Response<SignupResponse> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.code() == 200 || response.code() == 201) {

                        // Finish SignUpActivity to goto the LoginActivity
                        finish();
                        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);

                    } else if (response.code() != 200 && response.code() != 201) {
                        // Get the Error Message from Response
                        Snackbar.make(parentView, response.message(), Snackbar.LENGTH_SHORT).show();

                    } else {
                        // Unable to get Success status
                        Toast.makeText(Signup.this, getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // Show the Error Message
//                    Toast.makeText(Signup.this, response.message(), Toast.LENGTH_SHORT).show();
//                    Converter<ResponseBody, ModelError> errorConverter =
//                            getRetrofit().responseBodyConverter(ModelError.class, new Annotation[0]);
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if (jObjError.isNull("parameters")) {
                            Toast.makeText(Signup.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } else {
                            JSONArray jsonArray = jObjError.getJSONArray("parameters");

                            List<String> params = new ArrayList<String>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                params.add(jsonArray.getString(i));
                            }
                            if (params.size() > 0) {
                                Toast.makeText(Signup.this, jObjError.getString("message").replace("%1", params.get(0)), Toast.LENGTH_LONG).show();

                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Signup.this, "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
            }
        });
    }


    //*********** Validate SignUp Form Inputs ********//

    private boolean validateForm() {
        if (!ValidateInputs.isValidName(user_firstname.getText().toString().trim())) {
            user_firstname.setError(getString(R.string.invalid_first_name));
            return false;
        } else if (!ValidateInputs.isValidName(user_lastname.getText().toString().trim())) {
            user_lastname.setError(getString(R.string.invalid_last_name));
            return false;
        } else if (!ValidateInputs.isValidEmail(user_email.getText().toString().trim())) {
            user_email.setError(getString(R.string.invalid_email));
            return false;
        } else if (!ValidateInputs.isValidPassword(user_password.getText().toString().trim())) {
            user_password.setError(getString(R.string.invalid_password));
            return false;
        } else if (!ValidateInputs.isValidPhoneNo(user_mobile.getText().toString().trim())) {
            user_mobile.setError(getString(R.string.invalid_contact));
            return false;
        } else {
            return true;
        }
    }


    //*********** Set the Base Context for the ContextWrapper ********//

    @Override
    protected void attachBaseContext(Context newBase) {

        String languageCode = ConstantValues.LANGUAGE_CODE;
        if ("".equalsIgnoreCase(languageCode))
            languageCode = ConstantValues.LANGUAGE_CODE = "en";

        super.attachBaseContext(LocaleHelper.wrapLocale(newBase, languageCode));
    }


    //*********** Called when the Activity has detected the User pressed the Back key ********//

    @Override
    public void onBackPressed() {
        // Finish SignUpActivity to goto the LoginActivity
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
    }

}


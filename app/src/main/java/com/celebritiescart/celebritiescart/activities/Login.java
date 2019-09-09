package com.celebritiescart.celebritiescart.activities;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.app.MyAppPrefsManager;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customer_reg.ExtensionAttributes;
import com.celebritiescart.celebritiescart.customs.DialogLoader;
import com.celebritiescart.celebritiescart.customs.LocaleHelper;
import com.celebritiescart.celebritiescart.databases.User_Info_DB;
import com.celebritiescart.celebritiescart.models.ForgotPasswordRequest;
import com.celebritiescart.celebritiescart.models.user_model.UserDetails;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.network.APIClient1;
import com.celebritiescart.celebritiescart.network.LoginRequest;
import com.celebritiescart.celebritiescart.network.SignupRequest;
import com.celebritiescart.celebritiescart.network.SignupResponse;
import com.celebritiescart.celebritiescart.network.SocialLoginRequest;
import com.celebritiescart.celebritiescart.services.MyFirebaseInstanceIDService;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.utils.ValidateInputs;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Login activity handles User's Email, Facebook and Google Login
 **/


public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    View parentView;
    Toolbar toolbar;
    ActionBar actionBar;

    EditText user_email, user_password;
    TextView forgotPasswordText, signupText;
    Button loginBtn, facebookLoginBtn, googleLoginBtn;


    User_Info_DB userInfoDB;
    DialogLoader dialogLoader;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    private CallbackManager callbackManager;

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions mGoogleSignInOptions;

    private static final int RC_SIGN_IN = 100;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        boolean handleReturn = super.dispatchTouchEvent(ev);

        View view = getCurrentFocus();

        int x = (int) ev.getX();
        int y = (int) ev.getY();

       /* if (view instanceof EditText) {
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

        // Initialize Facebook SDk for Facebook Login
//        FacebookSdk.sdkInitialize(getApplicationContext());

        // Initializing Google SDK for Google Login
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

try {
    setContentView(R.layout.login);
}catch (Exception e){

}

        // setting Toolbar
//        toolbar = findViewById(R.id.myToolbar);
//        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        SpannableString s = new SpannableString("");
        s.setSpan(new TypefaceSpan(App.getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        actionBar.setTitle(s);
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(false);


        // Binding Layout Views
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        loginBtn = findViewById(R.id.loginBtn);
        facebookLoginBtn = findViewById(R.id.facebookLoginBtn);
        googleLoginBtn = findViewById(R.id.googleLoginBtn);
        signupText = findViewById(R.id.login_signupText);
        forgotPasswordText = findViewById(R.id.forgot_password_text);

        parentView = signupText;


        if (ConstantValues.IS_GOOGLE_LOGIN_ENABLED) {
            googleLoginBtn.setVisibility(View.VISIBLE);
        } else {
            googleLoginBtn.setVisibility(View.GONE);
        }

        if (ConstantValues.IS_FACEBOOK_LOGIN_ENABLED) {
            facebookLoginBtn.setVisibility(View.VISIBLE);
        } else {
            facebookLoginBtn.setVisibility(View.GONE);
        }


        dialogLoader = new DialogLoader(Login.this);

        userInfoDB = new User_Info_DB();
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);


        user_email.setText(sharedPreferences.getString("userEmail", null));


        // Register Callback for Facebook LoginManager
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//                // Get Access Token and proceed Facebook Registration
//                String accessToken = loginResult.getAccessToken().getToken();
//                processFacebookRegistration(accessToken);
//
//            }


            @Override
            public void onSuccess(LoginResult loginResult) {
                final AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    String email = object.getString("email");
                                    String name = object.getString("name");
                                    String id = object.getString("id");
                                    processFacebookRegistration(id, email, name);
//                                    Toast.makeText(Login.this, object.toString(), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(Login.this, e + "", Toast.LENGTH_LONG).show();

                                }
//                                String birthday = object.getString("birthday"); // 01/31/1980 format
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();

//                GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {
//                        Toast.makeText(Login.this, user.optString("email"), Toast.LENGTH_LONG).show();
//                        Toast.makeText(Login.this, user.toString(), Toast.LENGTH_LONG).show();
////                        Toast.makeText(Login.this, user.optString("id"), Toast.LENGTH_LONG).show();
//
////                        Log.d("das", user.optString("email"));
////                        Log.d("sad", user.optString("name"));
////                        Log.d(TAG, user.optString("id"));
//                    }
//                }).executeAsync();
            }


            @Override
            public void onCancel() {
                // If User Canceled
            }

            @Override
            public void onError(FacebookException e) {
                // If Login Fails
                Toast.makeText(Login.this, "FacebookException : " + e, Toast.LENGTH_LONG).show();
            }
        });


        // Initializing Google API Client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(Login.this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build();


        // Handle on Forgot Password Click
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_input, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);

                final Button dialog_button = dialogView.findViewById(R.id.dialog_button);
                final EditText dialog_input = dialogView.findViewById(R.id.dialog_input);
                final TextView dialog_title = dialogView.findViewById(R.id.dialog_title);

                dialog_button.setText(getString(R.string.send));
                dialog_title.setText(getString(R.string.forgot_your_password));


                final AlertDialog alertDialog = dialog.create();
                alertDialog.show();

                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (ValidateInputs.isValidEmail(dialog_input.getText().toString().trim())) {
                            // Request for Password Reset
                            processForgotPassword(dialog_input.getText().toString());

                        } else {
                            Snackbar.make(parentView, getString(R.string.invalid_email), Snackbar.LENGTH_LONG).show();
                        }

                        try {


                   /*         InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(dialog_input.getWindowToken(),
                                    InputMethodManager.RESULT_UNCHANGED_SHOWN);*/
                        } catch (Exception e) {

                        }
                        alertDialog.dismiss();

                    }
                });
            }
        });


        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignUp Activity
                startActivity(new Intent(Login.this, Signup.class));
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate Login Form Inputs
                boolean isValidData = validateLogin();

                if (isValidData) {

                    // Proceed User Login
                    processLogin();
                }
            }
        });


        // Handle Facebook Login Button click
        facebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout the User if already Logged-in
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                }

                // Login and Access User Date

                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("email", "public_profile"));
            }
        });


        // Handle Google Login Button click
        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout the User if already Logged-in
                if (mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                }

                // Get the Google SignIn Intent
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

                // Start Activity with Google SignIn Intent
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }


    //*********** Called if Connection fails for Google Login ********//

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // If Connection fails for GoogleApiClient
    }


    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // Handle Activity Result
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            handleGoogleSignInResult(result);
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }


    //*********** Get Google Account Details from GoogleSignInResult ********//

    private void handleGoogleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Getting google account


            GoogleSignInAccount acct = result.getSignInAccount();

            // Proceed Google Registration
            processGoogleRegistration(acct);

        } else {
            // If Login Fails


            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }


    //*********** Proceed Login with User Email and Password ********//

    private void processLogin() {

        dialogLoader.showProgressDialog();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(user_email.getText().toString().trim());
        loginRequest.setPassword(user_password.getText().toString().trim());

        Call<String> call = APIClient.getInstance()
                .processLogin
                        (
                                ConstantValues.AUTHORIZATION,
                                "application/json",
                                loginRequest

                        );

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.isSuccessful()) {

                    Call<UserDetails> callUserDetails = APIClient.getInstance()
                            .getUserDetails
                                    (
                                            "Bearer " + response.body()
                                    );
                    String mainResponse = response.body();


                    if (!response.body().isEmpty()) {
                        callUserDetails.enqueue(new Callback<UserDetails>() {
                            @Override
                            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                                dialogLoader.hideProgressDialog();


                                if (response.isSuccessful() && response.body() != null) {


                                    response.body().setCustomersId(mainResponse);
                                    response.body().setToken("Bearer " + mainResponse);
                                    // Save User Data to Local Databases
                                    if (userInfoDB.getUserData(response.body().getCustomersId()) != null) {
                                        // User already exists
                                        userInfoDB.updateUserData(response.body());
                                    } else {
                                        // Insert Details of New User
                                        userInfoDB.insertUserData(response.body());
                                    }

                                    // Save necessary details in SharedPrefs
                                    editor = sharedPreferences.edit();
                                    editor.putString("customerID", String.valueOf(response.body().getId()));


                                    editor.putString("userID", response.body().getCustomersId());
                                    editor.putString("userEmail", response.body().getCustomersEmailAddress());
                                    editor.putString("userName", response.body().getCustomersFirstname() + " " + response.body().getCustomersLastname());
                                    editor.putString("userDefaultAddressID", response.body().getCustomersDefaultAddressId());
                                    if (response.body().getCustomAttributes() != null) {
                                        if (response.body().getCustomAttributes().size() != 0) {
                                            for (int i = 0; i < response.body().getCustomAttributes().size(); i++) {

                                                if (response.body().getCustomAttributes().get(i).getAttributeCode().equalsIgnoreCase("mobile_no")) {
                                                    editor.putString("mobile_no", response.body().getCustomAttributes().get(i).getValue());
                                                } else if (response.body().getCustomAttributes().get(i).getAttributeCode().equalsIgnoreCase("profile_picture")) {

                                                    editor.putString("userprofileimage", response.body().getCustomAttributes().get(i).getValue());

                                                }

                                            }

                                        }
                                    }
                                    editor.putBoolean("isLogged_in", true);
                                    editor.apply();

                                    // Set UserLoggedIn in MyAppPrefsManager
                                    MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                    myAppPrefsManager.setUserLoggedIn(true);

                                    // Set isLogged_in of ConstantValues
                                    ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                    MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                    // Navigate back to MainActivity
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                                } else {
                                    UserDetails userDetails = new UserDetails();
                                    userDetails.setCustomersId(mainResponse);
                                    userDetails.setCustomersEmailAddress(user_email.getText().toString().trim());
                                    userDetails.setToken(mainResponse);

                                    // Save User Data to Local Databases
                                    if (userInfoDB.getUserData(userDetails.getCustomersId()) != null) {
                                        // User already exists
                                        userInfoDB.updateUserData(userDetails);
                                    } else {
                                        // Insert Details of New User
                                        userInfoDB.insertUserData(userDetails);
                                    }

                                    // Save necessary details in SharedPrefs
                                    editor = sharedPreferences.edit();
                                    editor.putString("userID", userDetails.getCustomersId());
                                    editor.putString("userEmail", userDetails.getCustomersEmailAddress());
                                    editor.putString("userName", userDetails.getCustomersFirstname() + " " + userDetails.getCustomersLastname());
                                    editor.putString("userDefaultAddressID", userDetails.getCustomersDefaultAddressId());
                                    editor.putBoolean("isLogged_in", true);
                                    editor.apply();

                                    // Set UserLoggedIn in MyAppPrefsManager
                                    MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                    myAppPrefsManager.setUserLoggedIn(true);

                                    // Set isLogged_in of ConstantValues
                                    ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                    MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                    // Navigate back to MainActivity
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                                }
                            }

                            @Override
                            public void onFailure(Call<UserDetails> call, Throwable t) {
                                dialogLoader.hideProgressDialog();
                                UserDetails userDetails = new UserDetails();
                                userDetails.setCustomersId(mainResponse);
                                userDetails.setCustomersEmailAddress(user_email.getText().toString().trim());
                                userDetails.setToken(mainResponse);

                                // Save User Data to Local Databases
                                if (userInfoDB.getUserData(userDetails.getCustomersId()) != null) {
                                    // User already exists
                                    userInfoDB.updateUserData(userDetails);
                                } else {
                                    // Insert Details of New User
                                    userInfoDB.insertUserData(userDetails);
                                }

                                // Save necessary details in SharedPrefs
                                editor = sharedPreferences.edit();
                                editor.putString("userID", userDetails.getCustomersId());
                                editor.putString("userEmail", userDetails.getCustomersEmailAddress());
                                editor.putString("userName", userDetails.getCustomersFirstname() + " " + userDetails.getCustomersLastname());
                                editor.putString("userDefaultAddressID", userDetails.getCustomersDefaultAddressId());
                                editor.putBoolean("isLogged_in", true);
                                editor.apply();

                                // Set UserLoggedIn in MyAppPrefsManager
                                MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                myAppPrefsManager.setUserLoggedIn(true);

                                // Set isLogged_in of ConstantValues
                                ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                // Navigate back to MainActivity
                                Intent i = new Intent(Login.this, MainActivity.class);
                                startActivity(i);
                                finish();
                                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                            }
                        });


                    } else {
                        dialogLoader.hideProgressDialog();

                        Toast.makeText(Login.this, getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    dialogLoader.hideProgressDialog();

                    Toast.makeText(Login.this, getString(R.string.txt_not_valid_usr_pass), Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialogLoader.hideProgressDialog();
             //   Toast.makeText(Login.this, getString(R.string.txt_not_valid_usr_pass), Toast.LENGTH_LONG).show();

            Toast.makeText(Login.this, getString(R.string.txt_network_failure), Toast.LENGTH_LONG).show();
            }

        });
    }


    //*********** Proceed Forgot Password Request ********//

    private void processForgotPassword(String email) {

        dialogLoader.showProgressDialog();

        Call<Boolean> call = APIClient.getInstance()
                .processForgotPassword
                        (
                                new ForgotPasswordRequest(email)
                        );

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                dialogLoader.hideProgressDialog();

                if (response.isSuccessful()) {
                    // Show the Response Message
                    if (response.body()) {
                        Snackbar.make(parentView, "Forgot email is sent to " + email, Snackbar.LENGTH_LONG).show();
                    } else {

                        Snackbar.make(parentView, "Cannot send email to " + email, Snackbar.LENGTH_LONG).show();
                    }

                } else {
                    // Show the Error Message
//                    try {
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    Snackbar.make(parentView, "Cannot send email to " + email, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                dialogLoader.hideProgressDialog();

                Toast.makeText(Login.this, "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
            }
        });
    }


    //*********** Proceed Facebook Registration Request ********//

    private void processFacebookRegistration(String id, String email, String name) {

        dialogLoader.showProgressDialog();


        socialFacebookLoginRequest(id, email, name);
    }


    //*********** Proceed Google Registration Request ********//

    private void processGoogleRegistration(GoogleSignInAccount account) {

        dialogLoader.showProgressDialog();

        String photoURL = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : "";

        //Log.i("ID",account.getId());
        //Log.i("getIdToken",account.getIdToken());

        socialGoogleLoginRequest(account);


    }


    //*********** Validate Login Form Inputs ********//

    private boolean validateLogin() {
        if (!ValidateInputs.isValidEmail(user_email.getText().toString().trim())) {
            user_email.setError(getString(R.string.invalid_email));
            return false;
        } else if (!ValidateInputs.isValidPassword(user_password.getText().toString().trim())) {
            user_password.setError(getString(R.string.invalid_password));
            return false;
        } else {
            return true;
        }
    }

    public void googleSignUpFirst(GoogleSignInAccount account) {
        SignupRequest body = new SignupRequest(account.getEmail(),
                account.getGivenName(),
                account.getFamilyName(),
                "",
                null
        );
        body.setExtensionAttributes(new ExtensionAttributes(account.getId(), "google"));
        body.getCustomer().setExtensionAttributes(new ExtensionAttributes(account.getId(), "google"));
        Call<SignupResponse> call = APIClient.getInstance()
                .customerRegistration
                        (
                                ConstantValues.AUTHORIZATION, body
                        );

        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {


                if (response.isSuccessful()) {


                    dialogLoader.showProgressDialog();
                    new PostSocialLogin(account.getId(), "google", String.valueOf(response.body().getId()), account, "", "").execute();


                } else {
                    // Show the Error Message
                    dialogLoader.hideProgressDialog();

                    try {
                        Log.i("signup google", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(Login.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void facebookSignUpFirst(String id, String email, String name) {
        SignupRequest body;
        if (name.split(" ").length > 1) {
            body = new SignupRequest(email,
                    name.split(" ")[0],
                    name.split(" ")[1],
                    "",
                    null
            );
        } else {
            body = new SignupRequest(email,
                    name,
                    "\u200D",
                    "",
                    null
            );
        }
        body.setExtensionAttributes(new ExtensionAttributes(id, "facebook"));
        body.getCustomer().setExtensionAttributes(new ExtensionAttributes(id, "facebook"));
        Call<SignupResponse> call = APIClient.getInstance()
                .customerRegistration
                        (
                                ConstantValues.AUTHORIZATION, body
                        );

        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {


                if (response.isSuccessful()) {


                    dialogLoader.showProgressDialog();
                    new PostSocialLogin(id, "facebook", String.valueOf(response.body().getId()), null, email, name).execute();


                } else {
                    // Show the Error Message
                    dialogLoader.hideProgressDialog();

                    try {
                        Log.i("signup fb", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(Login.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void socialGoogleLoginRequest(GoogleSignInAccount account) {
        String photoURL = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : "";

        Log.i("ID", account.getId());



        SocialLoginRequest loginRequest = new SocialLoginRequest(account.getId(), "google", account.getEmail(), account.getGivenName(), account.getFamilyName());

        Call<String> call = APIClient1.getInstance()
                .processSocialLoginPost
                        (
                                ConstantValues.AUTHORIZATION,
                                "application/json",
                                loginRequest.social_id,
                                loginRequest.email,
                                loginRequest.firstname,
                                loginRequest.social_type,
                                loginRequest.lastname,
                                photoURL

                        );

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.isSuccessful()) {

                    Call<UserDetails> callUserDetails = APIClient.getInstance()
                            .getUserDetails
                                    (
                                            "Bearer " + response.body()
                                    );
                    String mainResponse = response.body();


                    if (!response.body().isEmpty()) {
                        callUserDetails.enqueue(new Callback<UserDetails>() {
                            @Override
                            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                                dialogLoader.hideProgressDialog();


                                if (response.isSuccessful() && response.body() != null) {


                                    response.body().setCustomersId(mainResponse);
                                    response.body().setToken("Bearer " + mainResponse);
                                    // Save User Data to Local Databases
                                    if (userInfoDB.getUserData(response.body().getCustomersId()) != null) {
                                        // User already exists
                                        userInfoDB.updateUserData(response.body());
                                    } else {
                                        // Insert Details of New User
                                        userInfoDB.insertUserData(response.body());
                                    }

                                    // Save necessary details in SharedPrefs
                                    editor = sharedPreferences.edit();
                                    editor.putString("userID", response.body().getCustomersId());
                                    editor.putString("customerID", String.valueOf(response.body().getId()));
                                    editor.putString("userEmail", response.body().getCustomersEmailAddress());
                                    editor.putString("userName", response.body().getCustomersFirstname() + " " + response.body().getCustomersLastname());
                                    editor.putString("userDefaultAddressID", response.body().getCustomersDefaultAddressId());
                                    if (response.body().getCustomAttributes() != null) {
                                        if (response.body().getCustomAttributes().size() != 0) {
                                            for (int i = 0; i < response.body().getCustomAttributes().size(); i++) {

                                                if (response.body().getCustomAttributes().get(i).getAttributeCode().equalsIgnoreCase("mobile_no")) {
                                                    editor.putString("mobile_no", response.body().getCustomAttributes().get(i).getValue());
                                                } else if (response.body().getCustomAttributes().get(i).getAttributeCode().equalsIgnoreCase("profile_picture")) {

                                                    editor.putString("userprofileimage", response.body().getCustomAttributes().get(i).getValue());

                                                }

                                            }

                                        }
                                    }
                                    editor.putBoolean("isLogged_in", true);
                                    editor.apply();

                                    // Set UserLoggedIn in MyAppPrefsManager
                                    MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                    myAppPrefsManager.setUserLoggedIn(true);

                                    // Set isLogged_in of ConstantValues
                                    ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                    MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                    // Navigate back to MainActivity
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                                } else {
                                    UserDetails userDetails = new UserDetails();
                                    userDetails.setCustomersId(mainResponse);
                                    userDetails.setCustomersEmailAddress(user_email.getText().toString().trim());
                                    userDetails.setToken(mainResponse);

                                    // Save User Data to Local Databases
                                    if (userInfoDB.getUserData(userDetails.getCustomersId()) != null) {
                                        // User already exists
                                        userInfoDB.updateUserData(userDetails);
                                    } else {
                                        // Insert Details of New User
                                        userInfoDB.insertUserData(userDetails);
                                    }

                                    // Save necessary details in SharedPrefs
                                    editor = sharedPreferences.edit();
                                    editor.putString("userID", userDetails.getCustomersId());
                                    editor.putString("userEmail", userDetails.getCustomersEmailAddress());
                                    editor.putString("userName", userDetails.getCustomersFirstname() + " " + userDetails.getCustomersLastname());
                                    editor.putString("userDefaultAddressID", userDetails.getCustomersDefaultAddressId());
                                    editor.putBoolean("isLogged_in", true);
                                    editor.apply();

                                    // Set UserLoggedIn in MyAppPrefsManager
                                    MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                    myAppPrefsManager.setUserLoggedIn(true);

                                    // Set isLogged_in of ConstantValues
                                    ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                    MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                    // Navigate back to MainActivity
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                                }
                            }

                            @Override
                            public void onFailure(Call<UserDetails> call, Throwable t) {
                                dialogLoader.hideProgressDialog();
                                UserDetails userDetails = new UserDetails();
                                userDetails.setCustomersId(mainResponse);
                                userDetails.setCustomersEmailAddress(user_email.getText().toString().trim());
                                userDetails.setToken(mainResponse);

                                // Save User Data to Local Databases
                                if (userInfoDB.getUserData(userDetails.getCustomersId()) != null) {
                                    // User already exists
                                    userInfoDB.updateUserData(userDetails);
                                } else {
                                    // Insert Details of New User
                                    userInfoDB.insertUserData(userDetails);
                                }

                                // Save necessary details in SharedPrefs
                                editor = sharedPreferences.edit();
                                editor.putString("userID", userDetails.getCustomersId());
                                editor.putString("userEmail", userDetails.getCustomersEmailAddress());
                                editor.putString("userName", userDetails.getCustomersFirstname() + " " + userDetails.getCustomersLastname());
                                editor.putString("userDefaultAddressID", userDetails.getCustomersDefaultAddressId());
                                editor.putBoolean("isLogged_in", true);
                                editor.apply();

                                // Set UserLoggedIn in MyAppPrefsManager
                                MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                myAppPrefsManager.setUserLoggedIn(true);

                                // Set isLogged_in of ConstantValues
                                ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                // Navigate back to MainActivity
                                Intent i = new Intent(Login.this, MainActivity.class);
                                startActivity(i);
                                finish();
                                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                            }
                        });


                    } else {
                        googleSignUpFirst(account);

//                        Toast.makeText(Login.this, getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    try {
                        Log.i("First Login", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Toast.makeText(Login.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    googleSignUpFirst(account);
//                    dialogLoader.hideProgressDialog();
//                    try {
//                        Log.i("First Login",response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        Toast.makeText(Login.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void socialFacebookLoginRequest(String id, String email, String name) {


        SocialLoginRequest loginRequest = new SocialLoginRequest(id, "facebook", email, name, "");

        Call<String> call = APIClient1.getInstance()
                .processSocialLoginPost
                        (
                                ConstantValues.AUTHORIZATION,
                                "application/json",
                                loginRequest.social_id,
                                loginRequest.email,
                                loginRequest.firstname,
                                loginRequest.social_type,
                                loginRequest.lastname,
                                ""

                        );

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.isSuccessful()) {

                    Call<UserDetails> callUserDetails = APIClient.getInstance()
                            .getUserDetails
                                    (
                                            "Bearer " + response.body()
                                    );
                    String mainResponse = response.body();


                    if (!response.body().isEmpty()) {
                        callUserDetails.enqueue(new Callback<UserDetails>() {
                            @Override
                            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                                dialogLoader.hideProgressDialog();


                                if (response.isSuccessful() && response.body() != null) {

                                    Toast.makeText(Login.this, getResources().getText(R.string.login_successful), Toast.LENGTH_SHORT).show();

                                    response.body().setCustomersId(mainResponse);
                                    response.body().setToken("Bearer " + mainResponse);
                                    // Save User Data to Local Databases
                                    if (userInfoDB.getUserData(response.body().getCustomersId()) != null) {
                                        // User already exists
                                        userInfoDB.updateUserData(response.body());
                                    } else {
                                        // Insert Details of New User
                                        userInfoDB.insertUserData(response.body());
                                    }

                                    // Save necessary details in SharedPrefs
                                    editor = sharedPreferences.edit();
                                    editor.putString("userID", response.body().getCustomersId());
                                    editor.putString("customerID", String.valueOf(response.body().getId()));
                                    editor.putString("userEmail", response.body().getCustomersEmailAddress());
                                    editor.putString("userName", response.body().getCustomersFirstname() + " " + response.body().getCustomersLastname());
                                    editor.putString("userDefaultAddressID", response.body().getCustomersDefaultAddressId());

                                    if (response.body().getCustomAttributes() != null) {
                                        if (response.body().getCustomAttributes().size() != 0) {
                                            for (int i = 0; i < response.body().getCustomAttributes().size(); i++) {

                                                if (response.body().getCustomAttributes().get(i).getAttributeCode().equalsIgnoreCase("mobile_no")) {
                                                    editor.putString("mobile_no", response.body().getCustomAttributes().get(i).getValue());
                                                } else if (response.body().getCustomAttributes().get(i).getAttributeCode().equalsIgnoreCase("profile_picture")) {

                                                    editor.putString("userprofileimage", response.body().getCustomAttributes().get(i).getValue());

                                                }

                                            }

                                        }
                                    }

                                    editor.putBoolean("isLogged_in", true);
                                    editor.apply();

                                    // Set UserLoggedIn in MyAppPrefsManager
                                    MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                    myAppPrefsManager.setUserLoggedIn(true);

                                    // Set isLogged_in of ConstantValues
                                    ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                    MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                    // Navigate back to MainActivity
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                                } else {
                                    UserDetails userDetails = new UserDetails();
                                    userDetails.setCustomersId(mainResponse);
                                    userDetails.setCustomersEmailAddress(user_email.getText().toString().trim());
                                    userDetails.setToken(mainResponse);

                                    // Save User Data to Local Databases
                                    if (userInfoDB.getUserData(userDetails.getCustomersId()) != null) {
                                        // User already exists
                                        userInfoDB.updateUserData(userDetails);
                                    } else {
                                        // Insert Details of New User
                                        userInfoDB.insertUserData(userDetails);
                                    }

                                    // Save necessary details in SharedPrefs
                                    editor = sharedPreferences.edit();
                                    editor.putString("userID", userDetails.getCustomersId());
                                    editor.putString("userEmail", userDetails.getCustomersEmailAddress());
                                    editor.putString("userName", userDetails.getCustomersFirstname() + " " + userDetails.getCustomersLastname());
                                    editor.putString("userDefaultAddressID", userDetails.getCustomersDefaultAddressId());
                                    editor.putBoolean("isLogged_in", true);
                                    editor.apply();

                                    // Set UserLoggedIn in MyAppPrefsManager
                                    MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                    myAppPrefsManager.setUserLoggedIn(true);

                                    // Set isLogged_in of ConstantValues
                                    ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                    MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                    // Navigate back to MainActivity
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                                }
                            }

                            @Override
                            public void onFailure(Call<UserDetails> call, Throwable t) {
                                dialogLoader.hideProgressDialog();
                                UserDetails userDetails = new UserDetails();
                                userDetails.setCustomersId(mainResponse);
                                userDetails.setCustomersEmailAddress(user_email.getText().toString().trim());
                                userDetails.setToken(mainResponse);

                                // Save User Data to Local Databases
                                if (userInfoDB.getUserData(userDetails.getCustomersId()) != null) {
                                    // User already exists
                                    userInfoDB.updateUserData(userDetails);
                                } else {
                                    // Insert Details of New User
                                    userInfoDB.insertUserData(userDetails);
                                }

                                // Save necessary details in SharedPrefs
                                editor = sharedPreferences.edit();
                                editor.putString("userID", userDetails.getCustomersId());
                                editor.putString("userEmail", userDetails.getCustomersEmailAddress());
                                editor.putString("userName", userDetails.getCustomersFirstname() + " " + userDetails.getCustomersLastname());
                                editor.putString("userDefaultAddressID", userDetails.getCustomersDefaultAddressId());
                                editor.putBoolean("isLogged_in", true);
                                editor.apply();

                                // Set UserLoggedIn in MyAppPrefsManager
                                MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                myAppPrefsManager.setUserLoggedIn(true);

                                // Set isLogged_in of ConstantValues
                                ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                // Navigate back to MainActivity
                                Intent i = new Intent(Login.this, MainActivity.class);
                                startActivity(i);
                                finish();
                                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                            }
                        });


                    } else {
                        facebookSignUpFirst(id, email, name);

//                        Toast.makeText(Login.this, getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    try {
                        Log.i("First Login", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Toast.makeText(Login.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    facebookSignUpFirst(id, email, name);
//                    dialogLoader.hideProgressDialog();
//                    try {
//                        Log.i("First Login",response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        Toast.makeText(Login.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
            }

        });
    }


    public void socialGoogleLoginNestedRequest(GoogleSignInAccount account) {
        String photoURL = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : "";

//        Log.i("ID",account.getId());
//        Log.i("getIdToken",account.getIdToken());

        SocialLoginRequest loginRequest = new SocialLoginRequest(account.getId(), "google", account.getEmail(), account.getGivenName(), account.getFamilyName());


        Call<String> call = APIClient1.getInstance()
                .processSocialLoginPost
                        (
                                ConstantValues.AUTHORIZATION,
                                "application/json",
                                loginRequest.social_id,
                                loginRequest.email,
                                loginRequest.firstname,
                                loginRequest.social_type,
                                loginRequest.lastname,
                                photoURL

                        );

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.isSuccessful()) {

                    Call<UserDetails> callUserDetails = APIClient.getInstance()
                            .getUserDetails
                                    (
                                            "Bearer " + response.body()
                                    );
                    String mainResponse = response.body();


                    if (!response.body().isEmpty()) {
                        callUserDetails.enqueue(new Callback<UserDetails>() {
                            @Override
                            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                                dialogLoader.hideProgressDialog();


                                if (response.isSuccessful() && response.body() != null) {

                                    Toast.makeText(Login.this, getResources().getText(R.string.login_successful), Toast.LENGTH_SHORT).show();

                                    response.body().setCustomersId(mainResponse);
                                    response.body().setToken("Bearer " + mainResponse);
                                    // Save User Data to Local Databases
                                    if (userInfoDB.getUserData(response.body().getCustomersId()) != null) {
                                        // User already exists
                                        userInfoDB.updateUserData(response.body());
                                    } else {
                                        // Insert Details of New User
                                        userInfoDB.insertUserData(response.body());
                                    }

                                    // Save necessary details in SharedPrefs
                                    editor = sharedPreferences.edit();
                                    editor.putString("customerID", String.valueOf(response.body().getId()));


                                    editor.putString("userID", response.body().getCustomersId());
                                    editor.putString("userEmail", response.body().getCustomersEmailAddress());
                                    editor.putString("userName", response.body().getCustomersFirstname() + " " + response.body().getCustomersLastname());
                                    editor.putString("userDefaultAddressID", response.body().getCustomersDefaultAddressId());
                                    if (response.body().getCustomAttributes() != null) {
                                        if (response.body().getCustomAttributes().size() != 0) {
                                            for (int i = 0; i < response.body().getCustomAttributes().size(); i++) {

                                                if (response.body().getCustomAttributes().get(i).getAttributeCode().equalsIgnoreCase("mobile_no")) {
                                                    editor.putString("mobile_no", response.body().getCustomAttributes().get(i).getValue());
                                                } else if (response.body().getCustomAttributes().get(i).getAttributeCode().equalsIgnoreCase("profile_picture")) {

                                                    editor.putString("userprofileimage", response.body().getCustomAttributes().get(i).getValue());

                                                }

                                            }

                                        }
                                    }
                                    editor.putBoolean("isLogged_in", true);
                                    editor.apply();

                                    // Set UserLoggedIn in MyAppPrefsManager
                                    MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                    myAppPrefsManager.setUserLoggedIn(true);

                                    // Set isLogged_in of ConstantValues
                                    ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                    MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                    // Navigate back to MainActivity
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                                } else {
                                    UserDetails userDetails = new UserDetails();
                                    userDetails.setCustomersId(mainResponse);
                                    userDetails.setCustomersEmailAddress(user_email.getText().toString().trim());
                                    userDetails.setToken(mainResponse);

                                    // Save User Data to Local Databases
                                    if (userInfoDB.getUserData(userDetails.getCustomersId()) != null) {
                                        // User already exists
                                        userInfoDB.updateUserData(userDetails);
                                    } else {
                                        // Insert Details of New User
                                        userInfoDB.insertUserData(userDetails);
                                    }

                                    // Save necessary details in SharedPrefs
                                    editor = sharedPreferences.edit();
                                    editor.putString("userID", userDetails.getCustomersId());
                                    editor.putString("userEmail", userDetails.getCustomersEmailAddress());
                                    editor.putString("userName", userDetails.getCustomersFirstname() + " " + userDetails.getCustomersLastname());
                                    editor.putString("userDefaultAddressID", userDetails.getCustomersDefaultAddressId());
                                    editor.putBoolean("isLogged_in", true);
                                    editor.apply();

                                    // Set UserLoggedIn in MyAppPrefsManager
                                    MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                    myAppPrefsManager.setUserLoggedIn(true);

                                    // Set isLogged_in of ConstantValues
                                    ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                    MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                    // Navigate back to MainActivity
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                                }
                            }

                            @Override
                            public void onFailure(Call<UserDetails> call, Throwable t) {
                                dialogLoader.hideProgressDialog();
                                UserDetails userDetails = new UserDetails();
                                userDetails.setCustomersId(mainResponse);
                                userDetails.setCustomersEmailAddress(user_email.getText().toString().trim());
                                userDetails.setToken(mainResponse);

                                // Save User Data to Local Databases
                                if (userInfoDB.getUserData(userDetails.getCustomersId()) != null) {
                                    // User already exists
                                    userInfoDB.updateUserData(userDetails);
                                } else {
                                    // Insert Details of New User
                                    userInfoDB.insertUserData(userDetails);
                                }

                                // Save necessary details in SharedPrefs
                                editor = sharedPreferences.edit();
                                editor.putString("userID", userDetails.getCustomersId());
                                editor.putString("userEmail", userDetails.getCustomersEmailAddress());
                                editor.putString("userName", userDetails.getCustomersFirstname() + " " + userDetails.getCustomersLastname());
                                editor.putString("userDefaultAddressID", userDetails.getCustomersDefaultAddressId());
                                editor.putBoolean("isLogged_in", true);
                                editor.apply();

                                // Set UserLoggedIn in MyAppPrefsManager
                                MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                myAppPrefsManager.setUserLoggedIn(true);

                                // Set isLogged_in of ConstantValues
                                ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                // Navigate back to MainActivity
                                Intent i = new Intent(Login.this, MainActivity.class);
                                startActivity(i);
                                finish();
                                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                            }
                        });


                    } else {
                        dialogLoader.hideProgressDialog();

                        Toast.makeText(Login.this, getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    dialogLoader.hideProgressDialog();
                    try {
                        Log.i("nested error", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Toast.makeText(Login.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void socialFacebookLoginNestedRequest(String socialId, String socialType, String email, String name) {

//        Log.i("ID",account.getId());
//        Log.i("getIdToken",account.getIdToken());

        SocialLoginRequest loginRequest = new SocialLoginRequest(socialId, socialType, email, name, "");


        Call<String> call = APIClient1.getInstance()
                .processSocialLoginPost
                        (
                                ConstantValues.AUTHORIZATION,
                                "application/json",
                                loginRequest.social_id,
                                loginRequest.email,
                                loginRequest.firstname,
                                loginRequest.social_type,
                                loginRequest.lastname,
                                ""

                        );

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                if (response.isSuccessful()) {

                    Call<UserDetails> callUserDetails = APIClient.getInstance()
                            .getUserDetails
                                    (
                                            "Bearer " + response.body()
                                    );
                    String mainResponse = response.body();


                    if (!response.body().isEmpty()) {
                        callUserDetails.enqueue(new Callback<UserDetails>() {
                            @Override
                            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                                dialogLoader.hideProgressDialog();


                                if (response.isSuccessful() && response.body() != null) {


                                    response.body().setCustomersId(mainResponse);
                                    response.body().setToken("Bearer " + mainResponse);
                                    // Save User Data to Local Databases
                                    if (userInfoDB.getUserData(response.body().getCustomersId()) != null) {
                                        // User already exists
                                        userInfoDB.updateUserData(response.body());
                                    } else {
                                        // Insert Details of New User
                                        userInfoDB.insertUserData(response.body());
                                    }

                                    // Save necessary details in SharedPrefs
                                    editor = sharedPreferences.edit();
                                    editor.putString("customerID", String.valueOf(response.body().getId()));


                                    editor.putString("userID", response.body().getCustomersId());
                                    editor.putString("userEmail", response.body().getCustomersEmailAddress());
                                    editor.putString("userName", response.body().getCustomersFirstname() + " " + response.body().getCustomersLastname());
                                    editor.putString("userDefaultAddressID", response.body().getCustomersDefaultAddressId());
                                    if (response.body().getCustomAttributes() != null) {
                                        if (response.body().getCustomAttributes().size() != 0) {
                                            for (int i = 0; i < response.body().getCustomAttributes().size(); i++) {

                                                if (response.body().getCustomAttributes().get(i).getAttributeCode().equalsIgnoreCase("mobile_no")) {
                                                    editor.putString("mobile_no", response.body().getCustomAttributes().get(i).getValue());
                                                } else if (response.body().getCustomAttributes().get(i).getAttributeCode().equalsIgnoreCase("profile_picture")) {

                                                    editor.putString("userprofileimage", response.body().getCustomAttributes().get(i).getValue());

                                                }

                                            }

                                        }
                                    }
                                    editor.putBoolean("isLogged_in", true);
                                    editor.apply();

                                    // Set UserLoggedIn in MyAppPrefsManager
                                    MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                    myAppPrefsManager.setUserLoggedIn(true);

                                    // Set isLogged_in of ConstantValues
                                    ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                    MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                    // Navigate back to MainActivity
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                                } else {
                                    UserDetails userDetails = new UserDetails();
                                    userDetails.setCustomersId(mainResponse);
                                    userDetails.setCustomersEmailAddress(user_email.getText().toString().trim());
                                    userDetails.setToken(mainResponse);

                                    // Save User Data to Local Databases
                                    if (userInfoDB.getUserData(userDetails.getCustomersId()) != null) {
                                        // User already exists
                                        userInfoDB.updateUserData(userDetails);
                                    } else {
                                        // Insert Details of New User
                                        userInfoDB.insertUserData(userDetails);
                                    }

                                    // Save necessary details in SharedPrefs
                                    editor = sharedPreferences.edit();
                                    editor.putString("userID", userDetails.getCustomersId());
                                    editor.putString("userEmail", userDetails.getCustomersEmailAddress());
                                    editor.putString("userName", userDetails.getCustomersFirstname() + " " + userDetails.getCustomersLastname());
                                    editor.putString("userDefaultAddressID", userDetails.getCustomersDefaultAddressId());
                                    editor.putBoolean("isLogged_in", true);
                                    editor.apply();

                                    // Set UserLoggedIn in MyAppPrefsManager
                                    MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                    myAppPrefsManager.setUserLoggedIn(true);

                                    // Set isLogged_in of ConstantValues
                                    ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                    MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                    // Navigate back to MainActivity
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                                }
                            }

                            @Override
                            public void onFailure(Call<UserDetails> call, Throwable t) {
                                dialogLoader.hideProgressDialog();
                                UserDetails userDetails = new UserDetails();
                                userDetails.setCustomersId(mainResponse);
                                userDetails.setCustomersEmailAddress(user_email.getText().toString().trim());
                                userDetails.setToken(mainResponse);

                                // Save User Data to Local Databases
                                if (userInfoDB.getUserData(userDetails.getCustomersId()) != null) {
                                    // User already exists
                                    userInfoDB.updateUserData(userDetails);
                                } else {
                                    // Insert Details of New User
                                    userInfoDB.insertUserData(userDetails);
                                }

                                // Save necessary details in SharedPrefs
                                editor = sharedPreferences.edit();
                                editor.putString("userID", userDetails.getCustomersId());
                                editor.putString("userEmail", userDetails.getCustomersEmailAddress());
                                editor.putString("userName", userDetails.getCustomersFirstname() + " " + userDetails.getCustomersLastname());
                                editor.putString("userDefaultAddressID", userDetails.getCustomersDefaultAddressId());
                                editor.putBoolean("isLogged_in", true);
                                editor.apply();

                                // Set UserLoggedIn in MyAppPrefsManager
                                MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                                myAppPrefsManager.setUserLoggedIn(true);

                                // Set isLogged_in of ConstantValues
                                ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


                                MyFirebaseInstanceIDService.RegisterDeviceForFCM(Login.this);


                                // Navigate back to MainActivity
                                Intent i = new Intent(Login.this, MainActivity.class);
                                startActivity(i);
                                finish();
                                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                            }
                        });


                    } else {
                        dialogLoader.hideProgressDialog();

                        Toast.makeText(Login.this, getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    dialogLoader.hideProgressDialog();
                    try {
                        Log.i("nested error", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Toast.makeText(Login.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();
            }

        });
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

        // Navigate back to MainActivity
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
    }

    private class PostSocialLogin extends AsyncTask<String, Void, String> {

        private Exception exception;
        private String socialId;
        private String socialType;
        private String customerId;
        private GoogleSignInAccount account;
        String email;
        String name;

        PostSocialLogin(String socialId, String socialType, String customerId, GoogleSignInAccount account, String email, String name) {

            this.socialId = socialId;
            this.socialType = socialType;
            this.customerId = customerId;
            this.account = account;
            this.email = email;
            this.name = name;
        }

        private String executeReq(URL urlObject) throws IOException {
            HttpURLConnection conn = null;

            conn = (HttpURLConnection) urlObject.openConnection();
            conn.setReadTimeout(100000); //Milliseconds
            conn.setConnectTimeout(150000); //Milliseconds
            conn.setRequestMethod("POST");
            conn.setDoInput(true);

            // Start connect
            conn.connect();
            String response = convertStreamToString(conn.getInputStream());
            Log.i("bebo mei dil ley lou", response);
            if (conn.getResponseCode() == 200 && this.account != null) {
                socialGoogleLoginNestedRequest(this.account);
            } else if (conn.getResponseCode() == 200) {
                socialFacebookLoginNestedRequest(this.socialId, this.socialType, this.email, this.name);

            } else {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, response, Toast.LENGTH_LONG).show();

            }


//            Log.d("Response:", response);
//            Snackbar.make(rootView, response, Snackbar.LENGTH_SHORT).show();
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
                String parameters = "?socialId=" + this.socialId + "&socialType=" + this.socialType + "&customerId=" + this.customerId; //
                URL url = new URL(ConstantValues.ECOMMERCE_BASE_URL + "social.php" + parameters);
                return executeReq(url);


            } catch (Exception e) {
//                Toast.makeText(this, "NetworkCallFailure : " + e, Toast.LENGTH_LONG).show();
                Log.i("kuch aur", "" + e);
                return "";
            }
        }

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

}


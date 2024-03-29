package com.celebritiescart.celebritiescart.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.CircularImageView;
import com.celebritiescart.celebritiescart.customs.DialogLoader;
import com.celebritiescart.celebritiescart.databases.User_Info_DB;
import com.celebritiescart.celebritiescart.models.product_model.CustomAttribute;
import com.celebritiescart.celebritiescart.models.product_model.UpdateValue;
import com.celebritiescart.celebritiescart.models.user_model.Customer;
import com.celebritiescart.celebritiescart.models.user_model.CustomerUpdate;
import com.celebritiescart.celebritiescart.models.user_model.UserDetails;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.utils.CheckPermissions;
import com.celebritiescart.celebritiescart.utils.ImagePicker;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.utils.Utilities;
import com.celebritiescart.celebritiescart.utils.ValidateInputs;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.MODE_PRIVATE;
import static com.celebritiescart.celebritiescart.constant.ConstantValues.PROFILE_IMAGE_URL;
import static com.facebook.FacebookSdk.getApplicationContext;


public class Update_Account extends Fragment {

    View rootView;
    String customers_id;
    String profileImageCurrent = "";
    String profileImageChanged = "";
    private static final int PICK_IMAGE_ID = 360;           // the number doesn't matter

    Button updateInfoBtn;
    CircularImageView user_photo;
    FloatingActionButton user_photo_edit_fab;
    EditText input_first_name, input_last_name, input_dob, input_contact_no, input_current_password, input_new_password;
    SharedPreferences sharedPreferences;
    DialogLoader dialogLoader;

    UserDetails userInfo;
    User_Info_DB userInfoDB = new User_Info_DB();
    private String customers_email;
    String encodedImage = "";
    UpdateValue updateValue;
    File imageFile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        rootView = inflater.inflate(R.layout.update_account, container, false);
        sharedPreferences = getContext().getSharedPreferences("UserInfo", MODE_PRIVATE);

        if (getActivity().findViewById(R.id.myLayout).getVisibility() != View.GONE) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }
        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        SpannableString s = new SpannableString(getString(R.string.actionAccount));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);

        // Get the CustomerID from SharedPreferences
        customers_id = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        customers_email = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userEmail", "");


        // Binding Layout Views
        user_photo = rootView.findViewById(R.id.user_photo);
        input_first_name = rootView.findViewById(R.id.firstname);
        input_last_name = rootView.findViewById(R.id.lastname);
        input_dob = rootView.findViewById(R.id.dob);
        input_contact_no = rootView.findViewById(R.id.contact);
        input_current_password = rootView.findViewById(R.id.current_password);
        input_new_password = rootView.findViewById(R.id.new_password);
        updateInfoBtn = rootView.findViewById(R.id.updateInfoBtn);
        user_photo_edit_fab = rootView.findViewById(R.id.user_photo_edit_fab);


        // Set KeyListener of some View to null
        input_dob.setKeyListener(null);


        dialogLoader = new DialogLoader(getContext());

        // Get the User's Info from the Local Databases User_Info_DB
        userInfo = userInfoDB.getUserData(customers_id);


        // Set User's Info to Form Inputs
        input_first_name.setText(userInfo.getCustomersFirstname());
        input_last_name.setText(userInfo.getCustomersLastname());
        input_contact_no.setText(userInfo.getCustomersTelephone());


        // Set User's Date of Birth
        if (userInfo.getCustomersDob() == null) {
            input_dob.setText("");
        } else if (userInfo.getCustomersDob().equalsIgnoreCase("0000-00-00 00:00:00")) {
            input_dob.setText("");
        } else {
            // Get the String of Date from userInfo
            String dateString = userInfo.getCustomersDob();
            // Set Date Format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            // Convert String of Date to Date Format
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            input_dob.setText(dateFormat.format(convertedDate));
        }

        Glide.with(this).asBitmap().load(PROFILE_IMAGE_URL + sharedPreferences.getString("userprofileimage", "")).apply(new RequestOptions()
                .placeholder(R.drawable.profile).error(R.drawable.profile))
                .into(user_photo);
        // Set User's Photo
       /* if (userInfo.getCustomersPicture() != null && !userInfo.getCustomersPicture().isEmpty()) {
            profileImageCurrent = userInfo.getCustomersPicture();
            Glide.with(this).asBitmap()
                    .load(profileImageCurrent)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.profile).error(R.drawable.profile)
                    )
                    .into(user_photo);

        } else {
            profileImageCurrent = "";
            Glide.with(this).asBitmap()
                    .load(R.drawable.profile)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.profile).error(R.drawable.profile)
                    )
                    .into(user_photo);
        }*/


        // Handle Touch event of input_dob EditText
        input_dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Get Calendar instance
                    final Calendar calendar = Calendar.getInstance();

                    // Initialize DateSetListener of DatePickerDialog
                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            // Set the selected Date Info to Calendar instance
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, monthOfYear);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            // Set Date Format
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                            // Set Date in input_dob EditText
                            input_dob.setText(dateFormat.format(calendar.getTime()));
                        }
                    };


                    // Initialize DatePickerDialog
                    DatePickerDialog datePicker = new DatePickerDialog
                            (
                                    getContext(),
                                    date,
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                            );

                    // Show datePicker Dialog
                    datePicker.show();
                }

                return false;
            }
        });


        // Handle Click event of user_photo_edit_fab FAB
        user_photo_edit_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckPermissions.is_CAMERA_PermissionGranted() && CheckPermissions.is_STORAGE_PermissionGranted()) {
                    pickImage();
                } else {
                    requestPermissions
                            (
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    CheckPermissions.PERMISSIONS_REQUEST_CAMERA
                            );
                }

            }
        });


        // Handle Click event of updateInfoBtn Button
        updateInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate User's Info Form Inputs
                boolean isValidData = validateInfoForm();

                if (isValidData) {
                    if ("".equalsIgnoreCase(input_current_password.getText().toString()) && "".equalsIgnoreCase(input_new_password.getText().toString())) {
                        // Proceed User Registration
                        try {
                            uploadProfileImage();

                        }catch (Exception e){

                        }

                    } else {
                        if (validatePasswordForm())
                            try {
                                uploadProfileImage();

                            }catch (Exception e){
                                dialogLoader.showProgressDialog();

                            }

                    }

                }
            }
        });


        return rootView;

    }


    //*********** Picks User Profile Image from Gallery or Camera ********//

    private void pickImage() {
        // Intent with Image Picker Apps from the static method of ImagePicker class
        Intent chooseImageIntent = ImagePicker.getImagePickerIntent(getContext());

        // Start Activity with Image Picker Intent
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }


    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_ID) {

                // Get the User Selected Image as Bitmap from the static method of ImagePicker class
                Bitmap bitmap = ImagePicker.getImageFromResult(this.getActivity(), resultCode, data);
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                imageFile = new File(getRealPathFromURI(tempUri));

                // Upload the Bitmap to ImageView
                user_photo.setImageBitmap(bitmap);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();

                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();

                updateValue = new UpdateValue();
                updateValue.setBase64EncodedData(encodedImage);
                updateValue.setName(ts + ".png");
                updateValue.setType("image/png");


                // Get the converted Bitmap as Base64ImageString from the static method of Helper class
                profileImageChanged = Utilities.getBase64ImageStringFromBitmap(bitmap);

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
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                    // Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getString(R.string.permission_camera_storage));
                    builder.setMessage(getString(R.string.permission_camera_storage_needed));
                    builder.setPositiveButton(getString(R.string.grant), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            requestPermissions
                                    (
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
                    if (getContext() != null) {
                        //     Toast.makeText(getContext(), getString(R.string.permission_rejected), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }


    //*********** Updates User's Personal Information ********//

    private void updateCustomerInfo(String imageName) {

        CustomAttribute cA = new CustomAttribute();
        cA.setAttributeCode("profile_picture");
        cA.setValue(imageName);

        CustomAttribute cA1 = new CustomAttribute();
        cA1.setAttributeCode("mobile_no");
        cA1.setValue(sharedPreferences.getString("mobile_no",""));

        List<CustomAttribute> list = new ArrayList<>();
        list.add(cA);
        list.add(cA1);
        CustomerUpdate body = new CustomerUpdate(new Customer(customers_email, input_dob.getText().toString().trim(), input_first_name.getText().toString().trim(), input_last_name.getText().toString().trim(), list));

        Call<UserDetails> call = APIClient.getInstance()
                .updateCustomerInfo
                        (
                                "Bearer " + customers_id,
                                body
                        );
        Gson gson = new Gson();
        String json = gson.toJson(body);


        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, retrofit2.Response<UserDetails> response) {

                dialogLoader.hideProgressDialog();


                if (response.isSuccessful()) {

                    // User's Info has been Updated.

                    UserDetails userDetails = response.body();

                    // Update in Local Databases as well
                    userInfoDB.updateUserData(userDetails);
//                        userInfoDB.updateUserPassword(userDetails);

                    // Get the User's Info from the Local Databases User_Info_DB
                    userInfo = userInfoDB.getUserData(customers_id);

                    // Set the userName in SharedPreferences
                    SharedPreferences.Editor editor = getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).edit();
                    editor.putString("userName", userDetails.getCustomersFirstname() + " " + userDetails.getCustomersLastname());
                    if (response.body().getCustomAttributes()!=null) {
                        if (response.body().getCustomAttributes().size() != 0) {
                            for (int i = 0; i < response.body().getCustomAttributes().size(); i++) {

                                if (response.body().getCustomAttributes().get(i).getAttributeCode().equalsIgnoreCase("mobile_no")) {
                                    editor.putString("mobile_no", response.body().getCustomAttributes().get(i).getValue());
                                } else if(response.body().getCustomAttributes().get(i).getAttributeCode().equalsIgnoreCase("profile_picture")){

                                    editor.putString("userprofileimage", response.body().getCustomAttributes().get(i).getValue());

                                }

                            }

                        }
                    }
                    editor.apply();

                    // Set the User Info in the NavigationDrawer Header with the public method of MainActivity
                    ((MainActivity) getActivity()).setupExpandableDrawerHeader();

                    Snackbar.make(rootView, "Profile Updated", Snackbar.LENGTH_SHORT).show();


                } else {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (getContext() != null) {
                        //     Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();}
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                if (getContext() != null) {
                    //   Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();}
                }
            }

        });
    }

    private void uploadProfileImage() {
        try {

        dialogLoader.showProgressDialog();


            File file = new File(imageFile.getAbsolutePath());

            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);


            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("profile_picture", file.getName(), mFile);


            Call<String> call = APIClient.getInstance()
                    .profileImageUpload
                            (
                                    ConstantValues.AUTHORIZATION,
                                    body
                            );


            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {


                    updateCustomerInfo(response.body());

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    dialogLoader.hideProgressDialog();
                    if (getContext() != null) {
                        //   Toast.makeText(getContext(), "NetworkCallFailure : " + "Server is not responding try again after sometime.", Toast.LENGTH_LONG).show();}
                    }
                }

            });
        }catch (Exception e){
            dialogLoader.hideProgressDialog();

        }
    }
    //*********** Validate User Info Form Inputs ********//

    private boolean validateInfoForm() {
        if (!ValidateInputs.isValidName(input_first_name.getText().toString().trim())) {
            input_first_name.setError(getString(R.string.invalid_first_name));
            return false;
        } else if (!ValidateInputs.isValidName(input_last_name.getText().toString().trim())) {
            input_last_name.setError(getString(R.string.invalid_last_name));
            return false;
        } else {
            return true;
        }
    }


    //*********** Validate Password Info Form Inputs ********//

    private boolean validatePasswordForm() {
        if (!input_current_password.getText().toString().trim().equals(userInfo.getCustomersPassword())) {
            input_current_password.setError(getString(R.string.invalid_password));
            return false;
        } else if (!ValidateInputs.isValidPassword(input_new_password.getText().toString().trim())) {
            input_new_password.setError(getString(R.string.invalid_password));
            return false;
        } else {
            return true;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}

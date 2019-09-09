package com.celebritiescart.celebritiescart.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.celebritiescart.celebritiescart.models.device_model.DeviceInfo;
import com.celebritiescart.celebritiescart.models.user_model.UserData;
import com.celebritiescart.celebritiescart.network.APIClient;
import com.celebritiescart.celebritiescart.utils.Utilities;
import com.google.firebase.messaging.FirebaseMessagingService;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * FirebaseInstanceIdService Gets FCM instance ID token from Firebase Cloud Messaging Server
 */

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    
    SharedPreferences sharedPreferences;
    
    
    //*********** Called whenever the Token is Generated ********//

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);




        // Save FCM Token to sharedPreferences
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FCM_Token", s);
        editor.apply();


        // Register Device
        RegisterDeviceForFCM(getApplicationContext());

    }

    
    
    //*********** Register Device with FCM_Token and some other Device Info ********//
    
    public static void RegisterDeviceForFCM(final Context context) {
    
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
        
        DeviceInfo device = Utilities.getDeviceInfo(context);
        String token_FCM = sharedPreferences.getString("FCM_Token", "");
        String customer_ID = sharedPreferences.getString("userID", "");
        
        
        Call<UserData> call = APIClient.getInstance()
                .registerDeviceToFCM
                        (
                                token_FCM,
                                device.getDeviceType(),
                                device.getDeviceRAM(),
                                device.getDeviceProcessors(),
                                device.getDeviceAndroidOS(),
                                device.getDeviceLocation(),
                                device.getDeviceModel(),
                                device.getDeviceManufacturer(),
                                customer_ID
                        );
        
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, retrofit2.Response<UserData> response) {
                
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
    

//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
    
                    }
                    else {
    

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else {

                }
            }
            
            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
//                Toast.makeText(context, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }
    
}

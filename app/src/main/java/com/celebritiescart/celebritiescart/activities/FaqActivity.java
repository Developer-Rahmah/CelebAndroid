package com.celebritiescart.celebritiescart.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.models.CMSRequest;
import com.celebritiescart.celebritiescart.network.APIClient;

import retrofit2.Call;
import retrofit2.Callback;

import static com.celebritiescart.celebritiescart.app.App.getContext;

public class FaqActivity extends Activity {
    final int sdk = android.os.Build.VERSION.SDK_INT;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_webview_fullscreen);
        getFAQs();
        init();
        getFAQs();

    }

    public void getFAQs() {


        if(  ConstantValues.LANGUAGE_ID==1){

            Call<CMSRequest> call = APIClient.getInstance()
                    .getfaqs
                            (
                                    ConstantValues.AUTHORIZATION
                            );
            call.enqueue(new Callback<CMSRequest>() {
                @Override
                public void onResponse(Call<CMSRequest> call, retrofit2.Response<CMSRequest> response) {



                    if (response.isSuccessful()) {
                        ConstantValues.FAQS = response.body().getContent();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<CMSRequest> call, Throwable t) {
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



                    if (response.isSuccessful()) {
                        ConstantValues.FAQS = response.body().getContent();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<CMSRequest> call, Throwable t) {
                }
            });
        }
    }
    private void init() {
        final ImageButton dialog_button = findViewById(R.id.dialog_button);
        final TextView dialog_title = findViewById(R.id.dialog_title);
        final WebView dialog_webView = findViewById(R.id.dialog_webView);
        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), ConstantValues.LANGUAGE_ID==1 ?"font/baskvill_regular.OTF":"font/geflow.otf");
        dialog_title.setTypeface(custom_font);
        if(  ConstantValues.LANGUAGE_ID==1){

            dialog_title.setText(("FAQS"));

            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back) );


            } else {
                dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back) );

            }
        }else {
            dialog_title.setText(("أسئلة وأجوبة"));

            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back_ar) );

            } else {
                dialog_button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_back_ar) );

            }
        }
        dialog_button.getLayoutParams().height =85;
        dialog_button.getLayoutParams().width =90;
        dialog_button.setPadding(0,0,100,0);

//        dialog_title.setText(getString(R.string.actionFAQs));
        String description = ConstantValues.FAQS;
        String styleSheet ="<style type=\"text/css\"> " +
                "@font-face {font-family: MyFont;src: url(\"file:///android_asset/font/baskvill_regular.OTF\")}body {font-family: MyFont;font-size: 14px;text-align: justify;background:#ffffff; margin:0; padding:0}" +
                "p{color:#000000;} " +
                "img{display:inline; height:auto; max-width:100%;}" +
                "</style>";

        dialog_webView.setHorizontalScrollBarEnabled(false);
        dialog_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);
        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

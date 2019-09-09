package com.celebritiescart.celebritiescart.network;

import android.content.Context;

import com.celebritiescart.celebritiescart.app.App;
import com.celebritiescart.celebritiescart.models.banner_model.Banner;
import com.celebritiescart.celebritiescart.models.banner_model.BannerData;
import com.celebritiescart.celebritiescart.models.banner_model.BannerDetails;
import com.celebritiescart.celebritiescart.models.category_model.CategoryFilterData;
import com.celebritiescart.celebritiescart.models.device_model.AppSettingsData;
import com.celebritiescart.celebritiescart.models.pages_model.PagesData;
import com.celebritiescart.celebritiescart.models.pages_model.PagesDetails;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * StartAppRequests contains some Methods and API Requests, that are Executed on Application Startup
 **/

public class StartAppRequests {

    private App app;


    public StartAppRequests(Context context) {
        app = ((App) context.getApplicationContext());
    }



    //*********** Contains all methods to Execute on Startup ********//

    public void StartRequests(){

        RequestBanners();
//        RequestAllCategories();
        RequestAppSetting();
        RequestStaticPagesData();
    }



    //*********** API Request Method to Fetch App Banners ********//

    private void RequestBanners() {

        Call<ArrayList<Banner>> call = APIClient.getInstance()
                .getBanners();

        BannerData bannerData = new BannerData();

        try {
//            bannerData = call.execute().body();

            List<BannerDetails> data = new ArrayList<>();

            BannerDetails ob = new BannerDetails();

            ob.setId(1);

            ob.setImage("https://scontent.flhe6-1.fna.fbcdn.net/v/t1.0-9/15492213_1324984907531767_9198832298732903376_n.png?_nc_cat=0&oh=1f4d11e04d86fd8adab3663c1d4df2cf&oe=5BE6DB67");

            ob.setUrl("https://scontent.flhe6-1.fna.fbcdn.net/v/t1.0-9/15492213_1324984907531767_9198832298732903376_n.png?_nc_cat=0&oh=1f4d11e04d86fd8adab3663c1d4df2cf&oe=5BE6DB67");

            ob.setTitle("banner-1");

            ob.setType("category");

            data.add(ob);



//            if (!bannerData.getSuccess().isEmpty())

            app.setBannersList(data);



        } catch (Exception e) {

            e.printStackTrace();
        }
    }



    //*********** API Request Method to Fetch All Categories ********//
    
    private void RequestAllCategories() {

//       Get All default categories
        Call<CategoryFilterData> call =APIClient.getInstance()
                .getFilteredCategories
                        (
                                ConstantValues.AUTHORIZATION,"parent_id","2","99","1"
                        );

//        CategoryFilterData categoryData = new CategoryFilterData();
//
//        try {
//            categoryData = call.execute().body();
//
//            if (!categoryData.getItems().isEmpty())
//                app.setCategoriesList(categoryData.getItems());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        call.enqueue(new Callback<CategoryFilterData>() {
                         @Override
                         public void onResponse(Call<CategoryFilterData> call, Response<CategoryFilterData> response) {
                             if (response.isSuccessful()) {
                                 if (!response.body().getItems().isEmpty())
                                     app.setCategoriesList(response.body().getItems());
                                 // tasks available
                             } else {
                                 // error response, no access to resource?


                             }
                         }

                         @Override
                         public void onFailure(Call<CategoryFilterData> call, Throwable t) {
                             // something went completely south (like no internet connection)

                         }
                     });







        //       Get All celebrities
        Call<CategoryFilterData> celebcall1 = APIClient.getInstance()
                .getFilteredCategories
                        (
                                ConstantValues.AUTHORIZATION,"parent_id","37","3","1"
                        );

        CategoryFilterData celebFilterData = new CategoryFilterData();

        try {
            celebFilterData = celebcall1.execute().body();

            if (!celebFilterData.getItems().isEmpty())
                app.setCelebritiesList_1(celebFilterData.getItems());

        } catch (IOException e) {
            e.printStackTrace();
        }


//        celebcall1.enqueue(new Callback<CategoryFilterData>() {
//            @Override
//            public void onResponse(Call<CategoryFilterData> call, Response<CategoryFilterData> response) {
//                if (response.isSuccessful()) {
//                    if (!response.body().getItems().isEmpty())
//                    app.setCelebritiesList_1(response.body().getItems());
//                    // tasks available
//                } else {
//                    // error response, no access to resource?
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CategoryFilterData> call, Throwable t) {
//                // something went completely south (like no internet connection)
//
//            }
//        });







        //       Get All brands
        Call<CategoryFilterData> brandCall = APIClient.getInstance()
                .getFilteredCategories
                        (
                                ConstantValues.AUTHORIZATION,"parent_id","29","20","1"
                        );


        brandCall.enqueue(new Callback<CategoryFilterData>() {
            @Override
            public void onResponse(Call<CategoryFilterData> call, Response<CategoryFilterData> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getItems().isEmpty())
                        app.setBrandsList(response.body().getItems());
                    // tasks available
                } else {
                    // error response, no access to resource?


                }
            }

            @Override
            public void onFailure(Call<CategoryFilterData> call, Throwable t) {
                // something went completely south (like no internet connection)

            }
        });


    }



    //*********** Request App Settings from the Server ********//
    
    private void RequestAppSetting() {
        String json = "{\n" +
                "    \"success\": \"1\",\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"setting_id\": 1,\n" +
                "            \"facebook_app_id\": \"\",\n" +
                "            \"facebook_secret_id\": \"\",\n" +
                "            \"facebook_login\": 1,\n" +
                "            \"google_login\": 1,\n" +
                "            \"contact_us_email\": \"info@celebritiescart.com\",\n" +
                "            \"address\": \"Amman, Jordan\",\n" +
                "            \"city\": \"Amman\",\n" +
                "            \"state\": \"\",\n" +
                "            \"zip\": 10003,\n" +
                "            \"country\": \"Jordan\",\n" +
                "            \"latitude\": \"30.5852\",\n" +
                "            \"longitude\": \"36.2384\",\n" +
                "            \"phone_no\": \"00962799933717\",\n" +
                "            \"fcm_android\": \"\",\n" +
                "            \"fcm_ios\": \"\",\n" +
                "            \"fcm_desktop\": \"\",\n" +
                "            \"app_logo\": \"\",\n" +
                "            \"fcm_android_sender_id\": \"\",\n" +
                "            \"fcm_ios_sender_id\": \"\",\n" +
                "            \"app_name\": \"Celebrities Cart\",\n" +
                "            \"currency_symbol\": \"JOD\",\n" +
                "            \"new_product_duration\": 20,\n" +
                "            \"notification_title\": \"Celebrities Cart\",\n" +
                "            \"notification_text\": \"A bundle of products waiting for you!\",\n" +
                "            \"lazzy_loading_effect\": \"bubbles\",\n" +
                "            \"footer_button\": 1,\n" +
                "            \"cart_button\": 1,\n" +
                "            \"featured_category\": 0,\n" +
                "            \"notification_duration\": \"day\",\n" +
                "            \"home_style\": 1,\n" +
                "            \"wish_list_page\": 1,\n" +
                "            \"edit_profile_page\": 1,\n" +
                "            \"shipping_address_page\": 1,\n" +
                "            \"my_orders_page\": 1,\n" +
                "            \"contact_us_page\": 1,\n" +
                "            \"about_us_page\": 1,\n" +
                "            \"news_page\": 1,\n" +
                "            \"intro_page\": 1,\n" +
                "            \"setting_page\": 1,\n" +
                "            \"share_app\": 1,\n" +
                "            \"rate_app\": 1,\n" +
                "            \"site_url\": \"http://celebritiescart.com/\",\n" +
                "            \"admob\": 0,\n" +
                "            \"admob_id\": \"\",\n" +
                "            \"ad_unit_id_banner\": \"\",\n" +
                "            \"ad_unit_id_interstitial\": \"\",\n" +
                "            \"category_style\": 1,\n" +
                "            \"package_name\": \"package name\",\n" +
                "            \"google_analytic_id\": \"test\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"message\": \"Returned all site data.\"\n" +
                "}";



        AppSettingsData appSettingsData = new Gson().fromJson(json, AppSettingsData.class);

        try {
//            appSettingsData = call.execute().body();
    
            if (!appSettingsData.getSuccess().isEmpty())
                app.setAppSettingsDetails(appSettingsData.getProductData().get(0));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    //*********** Request Static Pages Data from the Server ********//
    
    private void RequestStaticPagesData() {

        String json = "{\n" +
                "    \"success\": \"1\",\n" +
                "    \"pages_data\": [\n" +
                "        {\n" +
                "            \"page_id\": 1,\n" +
                "            \"slug\": \"privacy-policy\",\n" +
                "            \"status\": 1,\n" +
                "            \"page_description_id\": 1,\n" +
                "            \"name\": \"Privacy Policy\",\n" +
                "            \"description\": \"<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy</p>\\r\\n\\r\\n<p>text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen</p>\\r\\n\\r\\n<p>book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially</p>\\r\\n\\r\\n<p>unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,</p>\\r\\n\\r\\n<p>and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. Lorem</p>\\r\\n\\r\\n<p>Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard</p>\\r\\n\\r\\n<p>dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type</p>\\r\\n\\r\\n<p>specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining</p>\\r\\n\\r\\n<p>essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum</p>\\r\\n\\r\\n<p>passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem</p>\\r\\n\\r\\n<p>Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s</p>\\r\\n\\r\\n<p>standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make</p>\\r\\n\\r\\n<p>a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,</p>\\r\\n\\r\\n<p>remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing</p>\\r\\n\\r\\n<p>Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions</p>\\r\\n\\r\\n<p>of Lorem Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been</p>\\r\\n\\r\\n<p>the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled</p>\\r\\n\\r\\n<p>it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,</p>\\r\\n\\r\\n<p>remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing</p>\\r\\n\\r\\n<p>Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions</p>\\r\\n\\r\\n<p>of Lorem Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been</p>\\r\\n\\r\\n<p>the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled</p>\\r\\n\\r\\n<p>it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,</p>\\r\\n\\r\\n<p>remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing</p>\\r\\n\\r\\n<p>Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions</p>\\r\\n\\r\\n<p>of Lorem Ipsum.</p>\\r\\n\",\n" +
                "            \"language_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"page_id\": 2,\n" +
                "            \"slug\": \"term-services\",\n" +
                "            \"status\": 1,\n" +
                "            \"page_description_id\": 4,\n" +
                "            \"name\": \"Term & Services\",\n" +
                "            \"description\": \"<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy</p>\\r\\n\\r\\n<p>text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen</p>\\r\\n\\r\\n<p>book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially</p>\\r\\n\\r\\n<p>unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,</p>\\r\\n\\r\\n<p>and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. Lorem</p>\\r\\n\\r\\n<p>Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard</p>\\r\\n\\r\\n<p>dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type</p>\\r\\n\\r\\n<p>specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining</p>\\r\\n\\r\\n<p>essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum</p>\\r\\n\\r\\n<p>passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem</p>\\r\\n\\r\\n<p>Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s</p>\\r\\n\\r\\n<p>standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make</p>\\r\\n\\r\\n<p>a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,</p>\\r\\n\\r\\n<p>remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing</p>\\r\\n\\r\\n<p>Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions</p>\\r\\n\\r\\n<p>of Lorem Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been</p>\\r\\n\\r\\n<p>the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled</p>\\r\\n\\r\\n<p>it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,</p>\\r\\n\\r\\n<p>remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing</p>\\r\\n\\r\\n<p>Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions</p>\\r\\n\\r\\n<p>of Lorem Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been</p>\\r\\n\\r\\n<p>the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled</p>\\r\\n\\r\\n<p>it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,</p>\\r\\n\\r\\n<p>remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing</p>\\r\\n\\r\\n<p>Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions</p>\\r\\n\\r\\n<p>of Lorem Ipsum.</p>\\r\\n\",\n" +
                "            \"language_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"page_id\": 3,\n" +
                "            \"slug\": \"refund-policy\",\n" +
                "            \"status\": 1,\n" +
                "            \"page_description_id\": 7,\n" +
                "            \"name\": \"Refund Policy\",\n" +
                "            \"description\": \"<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy</p>\\r\\n\\r\\n<p>text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen</p>\\r\\n\\r\\n<p>book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially</p>\\r\\n\\r\\n<p>unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,</p>\\r\\n\\r\\n<p>and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. Lorem</p>\\r\\n\\r\\n<p>Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard</p>\\r\\n\\r\\n<p>dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type</p>\\r\\n\\r\\n<p>specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining</p>\\r\\n\\r\\n<p>essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum</p>\\r\\n\\r\\n<p>passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem</p>\\r\\n\\r\\n<p>Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s</p>\\r\\n\\r\\n<p>standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make</p>\\r\\n\\r\\n<p>a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,</p>\\r\\n\\r\\n<p>remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing</p>\\r\\n\\r\\n<p>Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions</p>\\r\\n\\r\\n<p>of Lorem Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been</p>\\r\\n\\r\\n<p>the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled</p>\\r\\n\\r\\n<p>it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,</p>\\r\\n\\r\\n<p>remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing</p>\\r\\n\\r\\n<p>Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions</p>\\r\\n\\r\\n<p>of Lorem Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been</p>\\r\\n\\r\\n<p>the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled</p>\\r\\n\\r\\n<p>it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,</p>\\r\\n\\r\\n<p>remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing</p>\\r\\n\\r\\n<p>Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions</p>\\r\\n\\r\\n<p>of Lorem Ipsum.</p>\\r\\n\",\n" +
                "            \"language_id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"page_id\": 4,\n" +
                "            \"slug\": \"about-us\",\n" +
                "            \"status\": 1,\n" +
                "            \"page_description_id\": 10,\n" +
                "            \"name\": \"About Us\",\n" +
                "            \"description\": \"<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy</p>\\r\\n\\r\\n<p>text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen</p>\\r\\n\\r\\n<p>book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially</p>\\r\\n\\r\\n<p>unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages,</p>\\r\\n\\r\\n<p>and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum. Lorem</p>\\r\\n\\r\\n<p>Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard</p>\\r\\n\\r\\n<p>dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type</p>\\r\\n\\r\\n<p>specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining</p>\\r\\n\\r\\n<p>essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum</p>\\r\\n\\r\\n<p>passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem</p>\\r\\n\\r\\n<p>Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s</p>\\r\\n\\r\\n<p>standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make</p>\\r\\n\\r\\n<p>a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,</p>\\r\\n\\r\\n<p>remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing</p>\\r\\n\\r\\n<p>Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions</p>\\r\\n\\r\\n<p>of Lorem Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been</p>\\r\\n\\r\\n<p>the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled</p>\\r\\n\\r\\n<p>it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,</p>\\r\\n\\r\\n<p>remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing</p>\\r\\n\\r\\n<p>Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions</p>\\r\\n\\r\\n<p>of Lorem Ipsum. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been</p>\\r\\n\\r\\n<p>the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled</p>\\r\\n\\r\\n<p>it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,</p>\\r\\n\\r\\n<p>remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing</p>\\r\\n\\r\\n<p>Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions</p>\\r\\n\\r\\n<p>of Lorem Ipsum.</p>\\r\\n\",\n" +
                "            \"language_id\": 1\n" +
                "        }\n" +
                "    ],\n" +
                "    \"message\": \"Returned all products.\"\n" +
                "}";
    
        ConstantValues.ABOUT_US = app.getString(R.string.lorem_ipsum);
        ConstantValues.TERMS_SERVICES = app.getString(R.string.lorem_ipsum);
        ConstantValues.PRIVACY_POLICY = app.getString(R.string.lorem_ipsum);
        ConstantValues.REFUND_POLICY = app.getString(R.string.lorem_ipsum);
        


        PagesData pages = new Gson().fromJson(json, PagesData.class);

//        PagesData pages = new PagesData();
        
        try {
//            pages = call.execute().body();
    
            if (pages.getSuccess().equalsIgnoreCase("1")) {
    
                app.setStaticPagesDetails(pages.getPagesData());
    
                for (int i=0;  i<pages.getPagesData().size();  i++) {
                    PagesDetails page = pages.getPagesData().get(i);
        
                    if (page.getSlug().equalsIgnoreCase("about-us")) {
                        ConstantValues.ABOUT_US = page.getDescription();
                    }
                    else if (page.getSlug().equalsIgnoreCase("term-services")) {
                        ConstantValues.TERMS_SERVICES = page.getDescription();
                    }
                    else if (page.getSlug().equalsIgnoreCase("privacy-policy")) {
                        ConstantValues.PRIVACY_POLICY = page.getDescription();
                    }
                    else if (page.getSlug().equalsIgnoreCase("refund-policy")) {
                        ConstantValues.REFUND_POLICY = page.getDescription();
                    }
                }
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}





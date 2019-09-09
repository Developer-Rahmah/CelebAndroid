package com.celebritiescart.celebritiescart.network;


import com.celebritiescart.celebritiescart.models.CMSRequest;
import com.celebritiescart.celebritiescart.models.ForgotPasswordRequest;
import com.celebritiescart.celebritiescart.models.NotifyMeRequest;
import com.celebritiescart.celebritiescart.models.ReviewRequest;
import com.celebritiescart.celebritiescart.models.ReviewResponse;
import com.celebritiescart.celebritiescart.models.address_model.AddressData;
import com.celebritiescart.celebritiescart.models.address_model.CountryDetails;
import com.celebritiescart.celebritiescart.models.address_model.Zones;
import com.celebritiescart.celebritiescart.models.banner_model.Banner;
import com.celebritiescart.celebritiescart.models.cart_model.AddToCart;
import com.celebritiescart.celebritiescart.models.cart_model.AddressInformationMainBody;
import com.celebritiescart.celebritiescart.models.cart_model.add_to_cart_response.AddToCartResponse;
import com.celebritiescart.celebritiescart.models.cart_model.shipping_response.ShippingInformationResponse;
import com.celebritiescart.celebritiescart.models.category_model.CategoryData;
import com.celebritiescart.celebritiescart.models.category_model.CategoryFilterData;
import com.celebritiescart.celebritiescart.models.category_model.CategoryFilterDataInHome;
import com.celebritiescart.celebritiescart.models.city_model.CityMainModel;
import com.celebritiescart.celebritiescart.models.contact_model.ContactUsData;
import com.celebritiescart.celebritiescart.models.coupons_model.CouponsData;
import com.celebritiescart.celebritiescart.models.device_model.AppSettingsData;
import com.celebritiescart.celebritiescart.models.filter_model.get_filters.FilterData;
import com.celebritiescart.celebritiescart.models.language_model.LanguageData;
import com.celebritiescart.celebritiescart.models.news_model.all_news.NewsData;
import com.celebritiescart.celebritiescart.models.news_model.news_categories.NewsCategoryData;
import com.celebritiescart.celebritiescart.models.notify_me.NotifyMeModel;
import com.celebritiescart.celebritiescart.models.order_model.OrderData;
import com.celebritiescart.celebritiescart.models.pages_model.PagesData;
import com.celebritiescart.celebritiescart.models.payment_model.GetBrainTreeToken;
import com.celebritiescart.celebritiescart.models.payment_model.PaymentMethodsData;
import com.celebritiescart.celebritiescart.models.payment_model.SelectPaymentWay;
import com.celebritiescart.celebritiescart.models.product_detail.ProductDetailMainModel;
import com.celebritiescart.celebritiescart.models.product_model.ProductData;
import com.celebritiescart.celebritiescart.models.product_model.ProductRating;
import com.celebritiescart.celebritiescart.models.product_model.WishListData;
import com.celebritiescart.celebritiescart.models.product_model.WishListResponse;
import com.celebritiescart.celebritiescart.models.search_model.SearchData;
import com.celebritiescart.celebritiescart.models.shipping_model.PostTaxAndShippingData;
import com.celebritiescart.celebritiescart.models.shipping_model.ShippingRateData;
import com.celebritiescart.celebritiescart.models.subcategory.SubCategoryModel;
import com.celebritiescart.celebritiescart.models.user_model.CustomerUpdate;
import com.celebritiescart.celebritiescart.models.user_model.UserData;
import com.celebritiescart.celebritiescart.models.user_model.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


/**
 * APIRequests contains all the Network Request Methods with relevant API Endpoints
 **/

public interface APIRequests {


    //******************** User Data ********************//

    @FormUrlEncoded
    @POST("processRegistration")
    Call<UserData> processRegistration(@Field("customers_firstname") String customers_firstname,
                                       @Field("customers_lastname") String customers_lastname,
                                       @Field("customers_email_address") String customers_email_address,
                                       @Field("customers_password") String customers_password,
                                       @Field("customers_telephone") String customers_telephone,
                                       @Field("customers_picture") String customers_picture);

    @POST("customers")
    Call<SignupResponse> customerRegistration(@Header("Authorization") String token, @Body SignupRequest body);


    @POST("integration/customer/token")
    Call<String> processLogin(@Header("Authorization") String token, @Header("Content-Type") String contentType, @Body LoginRequest body);


    @POST("customer_social_post_data")
    Call<String> customerSocialPostData(
            @Header("Authorization") String token,
            @Header("Content-Type") String contentType,
            @Query("socialId") String field,
            @Query("socialType") String value,
            @Query("customerId") String pageSize
    );

   /* @POST("integration/customer/social_token")
    Call<String> processSocialLogin(@Header("Authorization") String token, @Header("Content-Type") String contentType, @Body SocialLoginRequest body);
*/

    @GET("webapi/login/customertoken")
    Call<String> processSocialLogin(@Header("Authorization") String token, @Header("Content-Type") String contentType, @Query("socialId") String socialId, @Query("email") String email, @Query("firstname") String firstname, @Query("socialType") String socialType, @Query("lastname") String lastname );


    //Post api for social token right now commented will look into future once ios developer made this changes

    @GET("sociallogin/login/customertoken")
    Call<String> processSocialLoginPost(@Header("Authorization") String token,@Header("Content-Type") String contentType, @Query("socialId") String socialId, @Query("email") String email, @Query("firstname") String firstname, @Query("socialType") String socialType, @Query("lastname") String lastname,@Query("profile_picture") String profile_picture);

     @POST("guest-carts/{quote_id}/shipping-information")
    Call<ShippingInformationResponse> addShippingInformation(@Header("Authorization") String token, @Body AddressInformationMainBody body, @Path("quote_id") String quote_id);

    @POST("carts/mine/shipping-information")
    Call<ShippingInformationResponse> addCustomerShippingInformation(@Header("Authorization") String token, @Body AddressInformationMainBody body);

    @POST("guest-carts/{quote_id}/items")
    Call<AddToCartResponse> addSingleItemToCart(@Header("Authorization") String token, @Body AddToCart body, @Path("quote_id") String quote_id, @Query("celebrityId") String celebrityId);

    @POST("carts/mine/items")
    Call<AddToCartResponse> addSingleItemToCartCustomer(@Header("Authorization") String token, @Body AddToCart body, @Query("celebrityId") String celebrityId);


    @FormUrlEncoded
    @POST("facebookRegistration")
    Call<UserData> facebookRegistration(@Field("access_token") String access_token);

    @GET("cmsPage/4")
    Call<CMSRequest> getPrivacyPolicy(@Header("Authorization") String token);

    @GET("cmsPage/549")
    Call<CMSRequest> getTermsConditions(@Header("Authorization") String token);

    @GET("cmsPage/549")
    Call<CMSRequest> getRefundPolicy(@Header("Authorization") String token);

    @GET("cmsPage/534")
    Call<CMSRequest> getAboutUs(@Header("Authorization") String token);

    @GET("cmsPage/535")
    Call<CMSRequest> getfaqs(@Header("Authorization") String token);


    //ar static pages


    @GET("cmsPage/551")
    Call<CMSRequest> getPrivacyPolicyAr();
    @GET("cmsPage/553")
    Call<CMSRequest> getRefundPolicyAr(@Header("Authorization") String token);
    @GET("cmsPage/552")
    Call<CMSRequest> getTermsConditionsAr(@Header("Authorization") String token);
    @GET("cmsPage/554")
    Call<CMSRequest> getfaqsAr(@Header("Authorization") String token);




    @GET("customers/me")
    Call<UserDetails> getUserDetails(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("googleRegistration")
    Call<UserData> googleRegistration(@Field("idToken") String idToken,
                                      @Field("userId") String userId,
                                      @Field("givenName") String givenName,
                                      @Field("familyName") String familyName,
                                      @Field("email") String email,
                                      @Field("imageUrl") String imageUrl);

    @PUT("customers/password")
    Call<Boolean> processForgotPassword(@Body ForgotPasswordRequest body);

    @PUT("customers/me")
    Call<UserDetails> updateCustomerInfo(@Header("Authorization") String token, @Body CustomerUpdate body);


    //******************** Address Data ********************//

    @GET("directory/countries")
    Call<List<CountryDetails>> getCountries(@Header("Authorization") String token);

    // city Api
    @GET("webapi/directory/countries")
    Call<CityMainModel> getCountries_n_Cities(@Query("countryId") String countryId);


    @GET("directory/countries/{countryId}")
    Call<Zones> getZones(@Path("countryId") String zone_country_id, @Header("Authorization") String token);


    @GET("review/reviews/{productId}")
    Call<List<ProductRating>> getRating(@Path("productId") String productId, @Header("Authorization") String token);

    @POST("review/mine/post")
    Call<List<ReviewResponse>> postCustomerReview(@Header("Authorization") String token, @Body ReviewRequest body);


    @GET("blog/post/list/{type}/{term}/{store_id}/{page}/{limit}")
    Call<String> getVideoListing(@Header("Authorization") String token,
                                 @Path("type") String type,
                                 @Path("term") String term,
                                 @Path("store_id") String store_id,
                                 @Path("page") String page,
                                 @Path("limit") String limit);

    @GET("blog/post/videosbycategory/{store_id}/{identifier}")
    Call<String> getCelebrityVideoListing(@Header("Authorization") String token,
                                          @Path("store_id") String store_id,
                                          @Path("identifier") String identifier);

    @GET("blog/post/productslist/{video_id}")
    Call<String> getVideoProducts(@Header("Authorization") String token,
                                  @Path("video_id") String video_id);


    @FormUrlEncoded
    @POST("getAllAddress")
    Call<AddressData> getAllAddress(@Field("customers_id") String customers_id);

    @FormUrlEncoded
    @POST("addShippingAddress")
    Call<AddressData> addUserAddress(@Field("customers_id") String customers_id,
                                     @Field("entry_firstname") String entry_firstname,
                                     @Field("entry_lastname") String entry_lastname,
                                     @Field("entry_street_address") String entry_street_address,
                                     @Field("entry_postcode") String entry_postcode,
                                     @Field("entry_city") String entry_city,
                                     @Field("entry_country_id") String entry_country_id,
                                     @Field("entry_zone_id") String entry_zone_id,
                                     @Field("customers_default_address_id") String customers_default_address_id);

    @FormUrlEncoded
    @POST("updateShippingAddress")
    Call<AddressData> updateUserAddress(@Field("customers_id") String customers_id,
                                        @Field("address_id") String address_id,
                                        @Field("entry_firstname") String entry_firstname,
                                        @Field("entry_lastname") String entry_lastname,
                                        @Field("entry_street_address") String entry_street_address,
                                        @Field("entry_postcode") String entry_postcode,
                                        @Field("entry_city") String entry_city,
                                        @Field("entry_country_id") String entry_country_id,
                                        @Field("entry_zone_id") String entry_zone_id,
                                        @Field("customers_default_address_id") String customers_default_address_id);

    @FormUrlEncoded
    @POST("updateDefaultAddress")
    Call<AddressData> updateDefaultAddress(@Field("customers_id") String customers_id,
                                           @Field("address_book_id") String address_book_id);

    @FormUrlEncoded
    @POST("deleteShippingAddress")
    Call<AddressData> deleteUserAddress(@Field("customers_id") String customers_id,
                                        @Field("address_book_id") String address_book_id);


    //******************** Category Data ********************//

    @FormUrlEncoded
    @POST("allCategories")
    Call<CategoryData> getAllCategories(@Field("language_id") int language_id);


    @GET("categories")
    Call<CategoryData> getAllCategories(@Header("Authorization") String token);

    @GET("categories/list")
    Call<CategoryFilterData> getFilteredCategories(@Header("Authorization") String token,
                               @Query("searchCriteria[filterGroups][0][filters][0][field]") String field,
                               @Query("searchCriteria[filterGroups][0][filters][0][value]") String value,
                               @Query("searchCriteria[pageSize]") String pageSize,
                               @Query("searchCriteria[currentPage]") String currentPage
    );

    @GET("categories/list")
    Call<CategoryFilterDataInHome> getFilteredCategoriesInHome(@Header("Authorization") String token,
                                                               @Query("searchCriteria[filterGroups][0][filters][0][field]") String field,
                                                               @Query("searchCriteria[filterGroups][0][filters][0][value]") String value,
                                                               @Query("searchCriteria[pageSize]") String pageSize,
                                                               @Query("searchCriteria[currentPage]") String currentPage
    );
    //******************** Product Data ********************//

//    @POST("getAllProducts")
//    Call<ProductData> getAllProducts(       @Body GetAllProducts getAllProducts);

    //?searchCriteria[filterGroups][0][filters][0][field]=category_id&searchCriteria[filterGroups][0][filters][0][value]=2&searchCriteria[filterGroups][0][filters][0][conditionType]=eq&searchCriteria[sortOrders][0][field]=created_at&searchCriteria[sortOrders][0][direction]=DESC&searchCriteria[pageSize]=10&searchCriteria[currentPage]=1
    @GET("products")
    Call<ProductData> getAllProducts(@Header("Authorization") String token,
                                     @Query("searchCriteria[filterGroups][0][filters][0][field]") String field,
                                     @Query("searchCriteria[filterGroups][0][filters][0][value]") String value,
                                     @Query("searchCriteria[filterGroups][0][filters][0][conditionType]") String conditionType,
                                     @Query("searchCriteria[sortOrders][0][field]") String sortOrders,
                                     @Query("searchCriteria[sortOrders][0][direction]") String sortDirection,
                                     @Query("searchCriteria[pageSize]") String pageSize,
                                     @Query("searchCriteria[currentPage]") String currentPage,
                                     @Query("celebrityId") String celebrityId,
                                     @Query("customer_id")String customer_id

    );

    ///rahmah get celeb products

    @GET("products")

           // data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][field]", "category_id");
    //                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][value]", String.valueOf(44));
//                data.put("searchCriteria[filterGroups][" + 0 + "][filters][0][conditionType]", "eq");
//
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][field]", "category_id");
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][value]", String.valueOf(44));
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][conditionType]", "eq");
//                data.put("searchCriteria[filterGroups][" + 1 + "][filters][0][value]", String.valueOf(getArguments().getString("celebrityId")));
//
    Call<ProductData> getAllProductsCeleb(@Header("Authorization") String token,
                                     @Query("searchCriteria[filterGroups][0][filters][0][field]") String field,
                                          @Query("searchCriteria[filterGroups][1][filters][0][field]") String field2,
                                     @Query("searchCriteria[filterGroups][0][filters][0][value]") String value,
                                          @Query("searchCriteria[filterGroups][1][filters][0][value]") String value2,
                                     @Query("searchCriteria[filterGroups][0][filters][0][conditionType]") String conditionType,
                                          @Query("searchCriteria[filterGroups][1][filters][0][conditionType]") String conditionType2,

                                          @Query("searchCriteria[sortOrders][0][field]") String sortOrders,
                                     @Query("searchCriteria[sortOrders][0][direction]") String sortDirection,
                                     @Query("searchCriteria[pageSize]") String pageSize,
                                     @Query("searchCriteria[currentPage]") String currentPage,
                                     @Query("celebrityId") String celebrityId,
                                     @Query("customer_id")String customer_id

    );





    @GET("products")
    Call<ProductData> getAllProducts(@Header("Authorization") String token,
                                     @Query("searchCriteria[sortOrders][0][field]") String sortOrders,
                                     @Query("searchCriteria[sortOrders][0][direction]") String sortDirection,
                                     @Query("searchCriteria[pageSize]") String pageSize,
                                     @Query("searchCriteria[currentPage]") String currentPage,
                                     @Query("celebrityId") String celebrityId,
                                     @Query("customer_id")String customer_id
    );


    @GET("products")
    Call<ProductData> getAllProducts(
            @Header("Authorization") String token,
            @QueryMap Map<String, String> options,
            @Query("celebrityId") String celebrityId,
            @Query("customer_id")String customer_id


    );

    @GET("products/{sku}")
    Call<ProductDetailMainModel> getProductDetail(
            @Path("sku")String sku,
            @Query("customer_id")String customer_id


    );


//    @GET
//    default Call<ProductData> getAllProducts(String token) {
//        return getAllProducts(token,"category_id","2","eq","created_at","DESC","10","1");
//    }
//
//
//    default Call<ProductData> getAllVideos(String token) {
//        return getAllProducts(token,"category_id","47","eq","created_at","DESC","10","1");
//
//    }


    @GET("wishlist/items")
    Call<List<WishListData>> getWishListProducts(@Header("Authorization") String token);

    @POST("guest-carts/")
    Call<String> getGuestCart(@Header("Authorization") String token);

    @POST("carts/mine")
    Call<String> getCustomerCart(@Header("Authorization") String token);


    @POST("wishlist/add/{productId}")
    Call<List<WishListResponse>> likeProduct(@Path("productId") String liked_products_id,
                                             @Header("Authorization") String liked_customers_id);


    @DELETE("wishlist/delete/{productId}")
    Call<List<WishListResponse>> unlikeProduct(@Path("productId") String liked_products_id,
                                               @Header("Authorization") String liked_customers_id);


    @FormUrlEncoded
    @POST("getFilters")
    Call<FilterData> getFilters(@Field("categories_id") int categories_id,
                                @Field("language_id") int language_id);


    @FormUrlEncoded
    @POST("getSearchData")
    Call<SearchData> getSearchData(@Field("searchValue") String searchValue,
                                   @Field("language_id") int language_id);


    //******************** News Data ********************//

    @FormUrlEncoded
    @POST("getAllNews")
    Call<NewsData> getAllNews(@Field("language_id") int language_id,
                              @Field("page_number") int page_number,
                              @Field("is_feature") int is_feature,
                              @Field("categories_id") String categories_id);

    @FormUrlEncoded
    @POST("allNewsCategories")
    Call<NewsCategoryData> allNewsCategories(@Field("language_id") int language_id,
                                             @Field("page_number") int page_number);


    //******************** Order Data ********************//

    @PUT("guest-carts/{quote_id}/order")
    Call<String> addToOrder(@Header("Authorization") String AUTHORIZATION, @Body SelectPaymentWay postOrder, @Path("quote_id") String quote_id);

    @POST("carts/mine/payment-information")
    Call<String> addToCustomerOrder(@Header("Authorization") String AUTHORIZATION, @Body SelectPaymentWay postOrder);

    @FormUrlEncoded
    @POST("getOrders")
    Call<OrderData> getOrders(@Field("customers_id") String customers_id,
                              @Field("language_id") int language_id);


    @FormUrlEncoded
    @POST("getCoupon")
    Call<CouponsData> getCouponInfo(@Field("code") String code);


    @GET("getPaymentMethods")
    Call<PaymentMethodsData> getPaymentMethods();

    @GET("generateBraintreeToken")
    Call<GetBrainTreeToken> generateBraintreeToken();


    //******************** Banner Data ********************//

    @GET("banners")
    Call<ArrayList<Banner>> getBanners();


    //******************** Tax & Shipping Data ********************//

    @POST("getRate")
    Call<ShippingRateData> getShippingMethodsAndTax(
            @Body PostTaxAndShippingData postTaxAndShippingData);


    //******************** Contact Us Data ********************//


    @GET
    Call<String> contactUs(@Url String url,
                           @Header("Content-Type") String contentType,
                           @Query("fullname") String name,
                           @Query("email") String email,
                           @Query("message") String message);


    //******************** Languages Data ********************//

    @GET("getLanguages")
    Call<LanguageData> getLanguages();


    //******************** App Settings Data ********************//

    @GET("siteSetting")
    Call<AppSettingsData> getAppSetting();


    //******************** Static Pages Data ********************//

    @FormUrlEncoded
    @POST("getAllPages")
    Call<PagesData> getStaticPages(@Field("language_id") int language_id);


    //******************** Notifications Data ********************//

    @FormUrlEncoded
    @POST("registerDevices")
    Call<UserData> registerDeviceToFCM(@Field("device_id") String device_id,
                                       @Field("device_type") String device_type,
                                       @Field("ram") String ram,
                                       @Field("processor") String processor,
                                       @Field("device_os") String device_os,
                                       @Field("location") String location,
                                       @Field("device_model") String device_model,
                                       @Field("manufacturer") String manufacturer,
                                       @Field("customers_id") String customers_id);


    @FormUrlEncoded
    @POST("notify_me")
    Call<ContactUsData> notify_me(@Field("is_notify") String is_notify,
                                  @Field("device_id") String device_id);

    @Multipart
    @POST("profileImage")
    Call<String> profileImageUpload(@Header("Authorization") String Authorization, @Part MultipartBody.Part profile_picture);

    @GET("subcategories/list")
    Call<List<SubCategoryModel>> getSubCategories(@Query("categoryId") int categoryId);


    @GET("categories/list")
    Call<CategoryFilterData> getBrands(@Header("Authorization") String token,
                                       @Query("searchCriteria[filterGroups][0][filters][0][field]") String field,
                                       @Query("searchCriteria[filterGroups][0][filters][0][value]") String value,
                                       @Query("searchCriteria[pageSize]") String pageSize,
                                       @Query("searchCriteria[currentPage]") String currentPage,
                                       @Query("searchCriteria[filterGroups][1][filters][0][field]") String brandType,
                                       @Query("searchCriteria[filterGroups][1][filters][0][value]")String brandid,
                                       @Query("searchCriteria[filterGroups][1][filters][0][condition_type]")String conditiontype


    );

/*    @FormUrlEncoded
    @POST("notifyUser")
    Call<NotifyMeModel> notifyUser(@Header("Content-Type") String content_type,@Header("Authorization") String token,
                                   @Field("product_id") String product_id, @Field("phone_number") String phone_number);*/


    @POST("notifyUser")
    Call<NotifyMeModel> notifyUser(@Header("Content-Type") String content_type, @Header("Authorization") String token,
                                   @Body NotifyMeRequest notifyMeRequest);
}






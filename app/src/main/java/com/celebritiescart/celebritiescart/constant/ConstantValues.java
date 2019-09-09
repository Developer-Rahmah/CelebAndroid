package com.celebritiescart.celebritiescart.constant;


/**
 * ConstantValues contains some constant variables, used all over the App.
 **/


public class ConstantValues {

    //*********** API Base URL ********//
//    public static final String ECOMMERCE_BASE_URL = "https://ecommerce.mystaging.me/";
    //  public static final String ECOMMERCE_BASE_URL = "http://boutiqa.mystaging.me/";
    public static final String ECOMMERCE_BASE_URL = "https://celebritiescart.com/";
    //   public static final String ECOMMERCE_BASE_URL = "http://siteproofs.net/magento2/osama/boutiqa/";
    public static final String ECOMMERCE_URL = ECOMMERCE_BASE_URL + "rest/V1/";


    public static final String AR_ECOMMERCE_URL = ECOMMERCE_BASE_URL + "rest/ae/V1/";
    public static final String ECOMMERCE_CATEGORY_IMAGEURL = ECOMMERCE_BASE_URL + "pub/media/catalog/category/";
    public static final String ECOMMERCE_BASE_IMAGEURL = ECOMMERCE_BASE_URL + "pub/media/";
    public static final String ECOMMERCE_PRODUCT_IMAGEURL = ECOMMERCE_BASE_URL + "pub/media/catalog/product/";
    public static final String PROFILE_IMAGE_URL = ECOMMERCE_BASE_URL + "pub/media/customer";
    // public static final String PAYMENT_BASE_URL = "http://52.59.56.185";
    public static final String PAYMENT_BASE_URL = ECOMMERCE_BASE_URL + "rest/V1/hyperpay";
    public static final String YOUTUBE_API_KEY = "AIzaSyAVli6eGfJS4v6sU3gumzAisG3dRfSfq18";
    // "false" if compiling the project for Demo, "true" otherwise
    public static final boolean IS_CLIENT_ACTIVE = true;

    public static String APP_HEADER;

    public static String DEFAULT_HOME_STYLE;
    public static String DEFAULT_CATEGORY_STYLE;

    public static int LANGUAGE_ID;

//    public static String AUTHORIZATION="Bearer e3v4ar3wyo93ieyt87qpp38t5g6nr2ba";

    //Live
    //   public static String AUTHORIZATION = "Bearer pjixt6t2ya3es6eeot4nd7tce6d33vd1";

    //SiteProofs
    public static String AUTHORIZATION = "Bearer nwld1huaq924l18vwubfcgfrwp5nsr22";


    public static String LANGUAGE_CODE;
    public static String CURRENCY_SYMBOL;
    public static long NEW_PRODUCT_DURATION;

    public static boolean IS_GOOGLE_LOGIN_ENABLED;
    public static boolean IS_FACEBOOK_LOGIN_ENABLED;
    public static boolean IS_ADD_TO_CART_BUTTON_ENABLED;

    public static boolean IS_ADMOBE_ENABLED;
    public static String ADMOBE_ID;
    public static String AD_UNIT_ID_BANNER;
    public static String AD_UNIT_ID_INTERSTITIAL;

    public static String ABOUT_US;
    public static String TERMS_SERVICES;
    public static String PRIVACY_POLICY;
    public static String FAQS;
    public static String REFUND_POLICY;

    public static boolean IS_USER_LOGGED_IN;
    public static boolean IS_PUSH_NOTIFICATIONS_ENABLED;
    public static boolean IS_LOCAL_NOTIFICATIONS_ENABLED;

    public static String cardType;

}

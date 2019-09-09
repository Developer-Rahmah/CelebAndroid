package com.celebritiescart.celebritiescart.app;



import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.celebritiescart.celebritiescart.databases.DB_Handler;
import com.celebritiescart.celebritiescart.databases.DB_Manager;
import com.celebritiescart.celebritiescart.models.address_model.AddressDetails;
import com.celebritiescart.celebritiescart.models.banner_model.Banner;
import com.celebritiescart.celebritiescart.models.banner_model.BannerDetails;
import com.celebritiescart.celebritiescart.models.category_model.CategoryDetails;
import com.celebritiescart.celebritiescart.models.device_model.AppSettingsDetails;
import com.celebritiescart.celebritiescart.models.drawer_model.Drawer_Items;
import com.celebritiescart.celebritiescart.models.pages_model.PagesDetails;
import com.celebritiescart.celebritiescart.models.product_model.ProductDetails;
import com.celebritiescart.celebritiescart.models.shipping_model.ShippingService;
import com.celebritiescart.celebritiescart.models.subcategory.SubCategoryModel;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * App extending Application, is used to save some Lists and Objects with Application Context.
 **/


public class App extends MultiDexApplication {

    // Application Context
    private static Context context;
    private static DB_Handler db_handler;


    private List<Drawer_Items> drawerHeaderList;
    private Map<Drawer_Items, List<Drawer_Items>> drawerChildList;


    private AppSettingsDetails appSettingsDetails = null;
    private List<BannerDetails> bannersList = new ArrayList<>();
    private List<CategoryDetails> categoriesList = new ArrayList<>();
    private List<CategoryDetails> homeCategoriesList = new ArrayList<>();
    private List<ProductDetails> favouriteProductsList = new ArrayList<>();
    private ChangeListener brandsChangelistener;
    private List<CategoryDetails> celebritiesList_1 = new ArrayList<>();
    private List<CategoryDetails> celebritiesList_2 = new ArrayList<>();
    private List<CategoryDetails> brandsList = new ArrayList<>();
    private List<CategoryDetails> exclusivebrandsList = new ArrayList<>();
    private List<CategoryDetails> featuredbrandsList = new ArrayList<>();
    private ChangeListener listener;
    private List<PagesDetails> staticPagesDetails = new ArrayList<>();
    private List<Banner>listBanners=new ArrayList<>();
//    private Thread.UncaughtExceptionHandler handleAppCrash =
//            (thread, ex) -> {
//
//                String exception = getStackTrace(ex);
//                new SendEmailAsync(exception).execute();
//            };
    private String tax = "";
    private String guestCartID = "";
    private ShippingService shippingService = null;
    private AddressDetails shippingAddress = new AddressDetails();
    private AddressDetails billingAddress = new AddressDetails();
    private List<CategoryDetails> celebritiesList = new ArrayList<>();

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Thread.setDefaultUncaughtExceptionHandler(handleAppCrash);


        // set App Context
        context = this.getApplicationContext();

        // initialize DB_Handler and DB_Manager
        db_handler = new DB_Handler();
        DB_Manager.initializeInstance(db_handler);

    }


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
//        MultiDex.install(this);
    }

    //*********** Returns Application Context ********//

    public static Context getContext() {
        return context;
    }


    public List<Drawer_Items> getDrawerHeaderList() {
        return drawerHeaderList;
    }

    public void setDrawerHeaderList(List<Drawer_Items> drawerHeaderList) {
        this.drawerHeaderList = drawerHeaderList;
    }

    public Map<Drawer_Items, List<Drawer_Items>> getDrawerChildList() {
        return drawerChildList;
    }

    public void setDrawerChildList(Map<Drawer_Items, List<Drawer_Items>> drawerChildList) {
        this.drawerChildList = drawerChildList;
    }


    public AppSettingsDetails getAppSettingsDetails() {
        return appSettingsDetails;
    }

    public void setAppSettingsDetails(AppSettingsDetails appSettingsDetails) {
        this.appSettingsDetails = appSettingsDetails;
    }

    public List<BannerDetails> getBannersList() {
        return bannersList;
    }

    public void setBannersList(List<BannerDetails> bannersList) {
        this.bannersList = bannersList;
    }

    public List<CategoryDetails> getCategoriesList() {
        return categoriesList;
    }

    public void setSubCategory(List<CategoryDetails>al_Categorydetail,List<SubCategoryModel> subCategoryModels,int position){

        for (int i = 0; i < al_Categorydetail.size(); i++) {
            if (i==position){
                categoriesList.get(i).subCategoryModels.clear();
                categoriesList.get(i).subCategoryModels.addAll(subCategoryModels);

            }
        }

    }

    public void setCategoriesList(List<CategoryDetails> categoriesList) {
        this.categoriesList.clear();
        this.homeCategoriesList.clear();

        CategoryDetails tempTrending = null;
        CategoryDetails tempSpecial = null;
        for (CategoryDetails category :
                categoriesList) {
            if (!category.getSlug().toLowerCase().contains("trending") &&
                    !category.getSlug().toLowerCase().contains("special") &&
                    !category.getSlug().toLowerCase().contains("tv") &&
                    !category.getSlug().toLowerCase().contains("celebrit") &&
                    !category.getSlug().toLowerCase().contains("brand")&&!category.getId().equals("19")) {
                this.categoriesList.add(category);
                this.homeCategoriesList.add(category);

            }
            if (category.getSlug().toLowerCase().contains("trending")) {
                tempTrending = category;
            } else if (category.getSlug().toLowerCase().contains("special")|| category.getId().equals("19")) {
                tempSpecial = category;
            }
        }


        if (tempTrending != null) {
            this.categoriesList.add(0, tempTrending);
        }
        if (tempSpecial != null) {
            this.categoriesList.add(1, tempSpecial);
        }


    }

    public List<PagesDetails> getStaticPagesDetails() {
        return staticPagesDetails;
    }

    public void setStaticPagesDetails(List<PagesDetails> staticPagesDetails) {
        this.staticPagesDetails = staticPagesDetails;
    }


    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public ShippingService getShippingService() {
        return shippingService;
    }

    public void setShippingService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }


    public AddressDetails getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressDetails shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public AddressDetails getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressDetails billingAddress) {
        this.billingAddress = billingAddress;
    }

    public List<CategoryDetails> getBrandsList() {
        return brandsList;
    }

    public void setBrandsList(List<CategoryDetails> brandsList) {
        this.brandsList.clear();
        this.brandsList.addAll(brandsList);
        if (brandsChangelistener != null) brandsChangelistener.onChange();
    }

    public void setExclusiveBrand(List<CategoryDetails> exclusivebrandsList) {
        this.exclusivebrandsList.clear();
        this.exclusivebrandsList.addAll(exclusivebrandsList);
    }
    public List<CategoryDetails>  getExclusiveBrand(){
        return exclusivebrandsList;
    }

    public void setFeaturedBrand(List<CategoryDetails> exclusivebrandsList) {
        this.featuredbrandsList.clear();
        this.featuredbrandsList.addAll(exclusivebrandsList);
    }
    public List<CategoryDetails>  getFeaturedBrand(){
        return featuredbrandsList;
    }

    public List<CategoryDetails> getCelebritiesList_1() {
        return celebritiesList_1;
    }

    public void setCelebritiesList_1(List<CategoryDetails> celebritiesList_1) {
        this.celebritiesList_1.clear();
        this.celebritiesList_1.addAll(celebritiesList_1);
    }

    public List<CategoryDetails> getCelebritiesList_2() {
        return celebritiesList_2;
    }

    public void setCelebritiesList_2(List<CategoryDetails> celebritiesList_2) {
        this.celebritiesList_2.clear();
        this.celebritiesList_2.addAll(celebritiesList_2);
    }

    public void setCelebritiesList(List<CategoryDetails> celebritiesList) {
        this.celebritiesList.clear();
        this.celebritiesList.addAll(celebritiesList);
        if (listener != null) listener.onChange();
    }

    public List<CategoryDetails> getCelebritiesList() {
        return celebritiesList;
    }


    public CategoryDetails findCelebrityByid(String id){
        for (CategoryDetails celebrity : celebritiesList) {
            if (celebrity.getId().equals(id)) {
                return celebrity;
            }
        }
        return null;
    }
    public CategoryDetails findBrandByid(String id){
        for (CategoryDetails brand : brandsList) {
            if (brand.getId().equals(id)) {
                return brand;
            }
        }
        return null;
    }

    public ChangeListener getListener() {
        return listener;
    }

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public ChangeListener getBrandsChangelistener() {
        return brandsChangelistener;
    }

    public void setBrandsChangelistener(ChangeListener brandsChangelistener) {
        this.brandsChangelistener = brandsChangelistener;
    }

    public String getGuestCartID() {
        return guestCartID;
    }

    public void setGuestCartID(String guestCartID) {
        this.guestCartID = guestCartID;
    }

    public List<CategoryDetails> getHomeCategoriesList() {
        return homeCategoriesList;
    }

    public void setHomeCategoriesList(List<CategoryDetails> homeCategoriesList) {
        this.homeCategoriesList = homeCategoriesList;
    }

    public List<ProductDetails> getFavouriteProductsList() {
        return favouriteProductsList;
    }

    public void setFavouriteProductsList(List<ProductDetails> favouriteProductsList) {
        this.favouriteProductsList = favouriteProductsList;
    }


    public void setListBanners(List<Banner> listBanners) {

        this.listBanners=listBanners;

    }

    public List<Banner> getListBanners(){
        return listBanners;
    }

    public interface ChangeListener {
        void onChange();
    }
}



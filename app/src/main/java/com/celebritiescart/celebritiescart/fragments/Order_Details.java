package com.celebritiescart.celebritiescart.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.celebritiescart.celebritiescart.adapters.CouponsAdapter;
import com.celebritiescart.celebritiescart.adapters.OrderedProductsListAdapter;
import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.customs.DividerItemDecoration;
import com.celebritiescart.celebritiescart.models.coupons_model.CouponsInfo;
import com.celebritiescart.celebritiescart.models.order_model.OrderDetails;
import com.celebritiescart.celebritiescart.models.order_model.OrderProducts;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.R;

import java.util.List;


public class Order_Details extends Fragment {

    View rootView;
    
    OrderDetails orderDetails;

    CardView buyer_comments_card, seller_comments_card;
    RecyclerView checkout_items_recycler, checkout_coupons_recycler;
    TextView checkout_subtotal, checkout_tax, checkout_shipping, checkout_discount, checkout_total;
    TextView billing_name, billing_street, billing_address, shipping_name, shipping_street, shipping_address;
    TextView order_price, order_products_count, order_status, order_date, shipping_method, payment_method, buyer_comments, seller_comments;

    List<CouponsInfo> couponsList;
    List<OrderProducts> orderProductsList;

    CouponsAdapter couponsAdapter;
    OrderedProductsListAdapter orderedProductsAdapter;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.order_details, container, false);
        if (getActivity().findViewById(R.id.myLayout).getVisibility()!=View.GONE ) {
            getActivity().findViewById(R.id.myLayout).setVisibility(View.GONE);
        }
    
    
        // Get orderDetails from bundle arguments
        orderDetails = getArguments().getParcelable("orderDetails");
        
        
        // Set the Title of Toolbar
        SpannableString s = new SpannableString(getString(R.string.order_details));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(s);
        

        // Binding Layout Views
        order_price = rootView.findViewById(R.id.order_price);
        order_products_count = rootView.findViewById(R.id.order_products_count);
        shipping_method = rootView.findViewById(R.id.shipping_method);
        payment_method = rootView.findViewById(R.id.payment_method);
        order_status = rootView.findViewById(R.id.order_status);
        order_date = rootView.findViewById(R.id.order_date);
        checkout_subtotal = rootView.findViewById(R.id.checkout_subtotal);
        checkout_tax = rootView.findViewById(R.id.checkout_tax);
        checkout_shipping = rootView.findViewById(R.id.checkout_shipping);
        checkout_discount = rootView.findViewById(R.id.checkout_discount);
        checkout_total = rootView.findViewById(R.id.checkout_total);
        billing_name = rootView.findViewById(R.id.billing_name);
        billing_address = rootView.findViewById(R.id.billing_address);
        billing_street = rootView.findViewById(R.id.billing_street);
        shipping_name = rootView.findViewById(R.id.shipping_name);
        shipping_address = rootView.findViewById(R.id.shipping_address);
        shipping_street = rootView.findViewById(R.id.shipping_street);
        buyer_comments = rootView.findViewById(R.id.buyer_comments);
        seller_comments = rootView.findViewById(R.id.seller_comments);
        buyer_comments_card = rootView.findViewById(R.id.buyer_comments_card);
        seller_comments_card = rootView.findViewById(R.id.seller_comments_card);
        checkout_items_recycler = rootView.findViewById(R.id.checkout_items_recycler);
        checkout_coupons_recycler = rootView.findViewById(R.id.checkout_coupons_recycler);

        checkout_items_recycler.setNestedScrollingEnabled(false);
        checkout_coupons_recycler.setNestedScrollingEnabled(false);


        // Set Order Details

        couponsList = orderDetails.getCoupons();
        orderProductsList = orderDetails.getProducts();

        double subTotal = 0;
        int noOfProducts = 0;
        for (int i=0;  i<orderProductsList.size();  i++) {
            subTotal += Double.parseDouble(orderProductsList.get(i).getFinalPrice());
            noOfProducts += orderProductsList.get(i).getProductsQuantity();
        }


        order_products_count.setText(String.valueOf(noOfProducts));
        order_price.setText(ConstantValues.CURRENCY_SYMBOL + orderDetails.getOrderPrice());
        shipping_method.setText(orderDetails.getShippingMethod());
        payment_method.setText(orderDetails.getPaymentMethod());
        order_status.setText(orderDetails.getOrdersStatus());
        order_date.setText(orderDetails.getDatePurchased());

        checkout_subtotal.setText(ConstantValues.CURRENCY_SYMBOL + String.valueOf(subTotal));
        checkout_tax.setText(ConstantValues.CURRENCY_SYMBOL + orderDetails.getTotalTax());
        checkout_shipping.setText(ConstantValues.CURRENCY_SYMBOL + orderDetails.getShippingCost());
        checkout_discount.setText(ConstantValues.CURRENCY_SYMBOL + orderDetails.getCouponAmount());
        checkout_total.setText(ConstantValues.CURRENCY_SYMBOL + orderDetails.getOrderPrice());

        billing_name.setText(orderDetails.getBillingName());
        billing_address.setText(orderDetails.getBillingCity());
        billing_street.setText(orderDetails.getBillingStreetAddress());
        shipping_name.setText(orderDetails.getDeliveryName());
        shipping_address.setText(orderDetails.getDeliveryCity());
        shipping_street.setText(orderDetails.getDeliveryStreetAddress());

        
        if (!orderDetails.getCustomerComments().isEmpty()  &&  !orderDetails.getCustomerComments().equalsIgnoreCase("")) {
            buyer_comments_card.setVisibility(View.VISIBLE);
            buyer_comments.setText(orderDetails.getCustomerComments());
        } else {
            buyer_comments_card.setVisibility(View.GONE);
        }

        if (!orderDetails.getAdminComments().isEmpty()  &&  !orderDetails.getAdminComments().equalsIgnoreCase("")) {
            seller_comments_card.setVisibility(View.VISIBLE);
            seller_comments.setText(orderDetails.getAdminComments());
        } else {
            seller_comments_card.setVisibility(View.GONE);
        }


        couponsAdapter = new CouponsAdapter(getContext(), couponsList, false, null);

        checkout_coupons_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        checkout_coupons_recycler.setAdapter(couponsAdapter);

        
        orderedProductsAdapter = new OrderedProductsListAdapter(getContext(), orderProductsList);

        checkout_items_recycler.setAdapter(orderedProductsAdapter);
        checkout_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        checkout_items_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        return rootView;

    }


}




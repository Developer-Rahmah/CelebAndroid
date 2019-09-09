package com.celebritiescart.celebritiescart.fragments;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.celebritiescart.celebritiescart.constant.ConstantValues;
import com.celebritiescart.celebritiescart.models.payment_model.PaymentMethod;
import com.celebritiescart.celebritiescart.models.payment_model.SelectPaymentWay;
import com.celebritiescart.celebritiescart.utils.CheckoutIdRequestListener;
import com.celebritiescart.celebritiescart.utils.PaymentStatusRequestListener;
import com.celebritiescart.celebritiescart.utils.TypefaceSpan;
import com.celebritiescart.celebritiescart.R;
import com.celebritiescart.celebritiescart.activities.MainActivity;
import com.oppwa.mobile.connect.exception.PaymentError;
import com.oppwa.mobile.connect.exception.PaymentException;
import com.oppwa.mobile.connect.payment.BrandsValidation;
import com.oppwa.mobile.connect.payment.CheckoutInfo;
import com.oppwa.mobile.connect.payment.ImagesRequest;
import com.oppwa.mobile.connect.payment.PaymentParams;
import com.oppwa.mobile.connect.payment.card.CardPaymentParams;
import com.oppwa.mobile.connect.provider.Connect;
import com.oppwa.mobile.connect.provider.ITransactionListener;
import com.oppwa.mobile.connect.provider.Transaction;
import com.oppwa.mobile.connect.provider.TransactionType;
import com.oppwa.mobile.connect.service.ConnectService;
import com.oppwa.mobile.connect.service.IProviderBinder;



public class HyperPayFragment extends Fragment implements ITransactionListener, CheckoutIdRequestListener, PaymentStatusRequestListener {

    View rootView;

    private String cardBrand;
    private EditText cardHolderEditText;
    private EditText cardNumberEditText;
    private EditText cardExpiryMonthEditText;
    private EditText cardExpiryYearEditText;
    private EditText cardCvvEditText;
    public String amount,type,card_type;
    private String checkoutId;
    ImageView payment_method_icon;
    ITransactionListener iTransactionListener;
    private IProviderBinder providerBinder;
    public static HyperPayFragment instance;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            providerBinder = (IProviderBinder) service;
            providerBinder.addTransactionListener(iTransactionListener);

            try {
                providerBinder.initializeProvider(Connect.ProviderMode.LIVE);
            } catch (PaymentException ee) {
                showErrorDialog(ee.getMessage());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            providerBinder = null;
        }

    };


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_custom_ui, container, false);
        iTransactionListener = this;
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        instance=this;
         amount = getArguments().getString("amount");
         type=getArguments().getString("type");
        card_type=getArguments().getString("card_type");
        ConstantValues.cardType=card_type;
        SpannableString s = new SpannableString(getString(R.string.payment));
        s.setSpan(new TypefaceSpan(getContext(), ConstantValues.LANGUAGE_ID==1 ?"baskvill_regular.OTF":"geflow.otf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(s);
        Log.e("initHyper",amount+" "+type+ " "+card_type);
        cardHolderEditText = rootView.findViewById(R.id.holder_edit_text);

        cardNumberEditText = rootView.findViewById(R.id.number_edit_text);

        payment_method_icon=rootView.findViewById(R.id.payment_method_icon);

        cardExpiryMonthEditText = rootView.findViewById(R.id.expiry_month_edit_text);

        cardExpiryYearEditText = rootView.findViewById(R.id.expiry_year_edit_text);

        cardCvvEditText = rootView.findViewById(R.id.cvv_edit_text);

        if (card_type.equalsIgnoreCase("HyperPay_Visa")){
            payment_method_icon.setImageDrawable(getResources().getDrawable(R.drawable.visa));
            cardBrand="visa";
        }else {
            payment_method_icon.setImageDrawable(getResources().getDrawable(R.drawable.master));
            cardBrand="master";

        }

        rootView.findViewById(R.id.button_pay_now).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (providerBinder != null && checkFields()) {
                    ((MainActivity) getActivity()).showProgressDialog(R.string.processing);

                    ((MainActivity)getActivity()).PlaceOrderNow(new SelectPaymentWay(new PaymentMethod(card_type)));
                }
            }
        });
    }

    @Override
    public void brandsValidationRequestSucceeded(BrandsValidation brandsValidation) {

    }

    @Override
    public void brandsValidationRequestFailed(PaymentError paymentError) {

    }

    @Override
    public void imagesRequestSucceeded(ImagesRequest imagesRequest) {

    }

    @Override
    public void imagesRequestFailed() {

    }

    @Override
    public void paymentConfigRequestSucceeded(final CheckoutInfo checkoutInfo) {
        ((MainActivity) getActivity()).hideProgressDialog();

        if (checkoutInfo == null) {
            showErrorDialog(getString(R.string.error_message));

            return;
        }


        ((MainActivity) getActivity()).resourcePath = checkoutInfo.getResourcePath();

        ((MainActivity) getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showConfirmationDialog(
                        String.valueOf(checkoutInfo.getAmount()),
                        checkoutInfo.getCurrencyCode()
                );
            }
        });
    }

    @Override
    public void paymentConfigRequestFailed(PaymentError paymentError) {
        ((MainActivity) getActivity()).hideProgressDialog();
        showErrorDialog(paymentError);
    }

    @Override
    public void transactionCompleted(Transaction transaction) {
        ((MainActivity)getActivity()).hideProgressDialog();

        if (transaction == null) {
            showErrorDialog(getString(R.string.error_message));

            return;
        }

        if (transaction.getTransactionType() == TransactionType.SYNC) {
            ((MainActivity)getActivity()).requestPaymentStatus(((MainActivity)getActivity()).resourcePath,card_type);
        } else {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(transaction.getRedirectUrl())));
        }
    }

    @Override
    public void transactionFailed(Transaction transaction, PaymentError paymentError) {
        ((MainActivity)getActivity()).hideProgressDialog();
        showErrorDialog(paymentError);
    }

    @Override
    public void onStart() {
        super.onStart();

        Intent intent = new Intent(getActivity(), ConnectService.class);

        getActivity().startService(intent);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();

        getActivity().unbindService(serviceConnection);
        getActivity().stopService(new Intent(getActivity(), ConnectService.class));
    }

    private void showErrorDialog(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showErrorAlertDialog(message);
            }
        });
    }

    private void showErrorDialog(PaymentError paymentError) {
        showErrorDialog(paymentError.getErrorMessage());
    }

    protected void showAlertDialog(String message) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, null)
                .setCancelable(false)
                .show();
    }
    protected void showErrorAlertDialog(String message) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (Fragment fragmentdelete:getFragmentManager().getFragments()) {
                            if (fragmentdelete!=null) {
                                getFragmentManager().beginTransaction().remove(fragmentdelete).commit();
                            }
                        }

                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isSubFragment", false);

                        // Navigate to Products Fragment
                        Fragment fragment = new HomePage_1();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_fragment, fragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private boolean checkFields() {
        if (cardHolderEditText.getText().length() == 0 ||
                cardNumberEditText.getText().length() == 0 ||
                cardExpiryMonthEditText.getText().length() == 0 ||
                cardExpiryYearEditText.getText().length() == 0 ||
                cardCvvEditText.getText().length() == 0) {
            showAlertDialog(R.string.error_empty_fields);

            return false;
        }

        return true;
    }

    protected void showAlertDialog(int messageId) {
        showAlertDialog(getString(messageId));
    }


    @Override
    public void onCheckoutIdReceived(String checkoutId) {

        if (checkoutId != null) {
            this.checkoutId = checkoutId;

            requestCheckoutInfo(checkoutId);
        }
    }

    @Override
    public void onErrorOccurred() {

    }

    @Override
    public void onPaymentStatusReceived(String paymentStatus) {

    }

    public void requestCheckoutInfo(String checkoutId) {
        this.checkoutId=checkoutId;
        if (providerBinder != null) {
            try {
                providerBinder.requestCheckoutInfo(checkoutId);
                ((MainActivity) getActivity()).showProgressDialog(R.string.progress_message_checkout_info);
            } catch (PaymentException e) {
                showErrorAlertDialog(e.getMessage());
            }
        }
    }

    public void pay(String checkoutId) {
        try {
            PaymentParams paymentParams = createPaymentParams(checkoutId);
            paymentParams.setShopperResultUrl(getString(R.string.custom_ui_callback_scheme) + "://result");
            Transaction transaction = new Transaction(paymentParams);

            providerBinder.submitTransaction(transaction);
            ((MainActivity) getActivity()).showProgressDialog(R.string.progress_message_processing_payment);
        } catch (PaymentException e) {
            showErrorDialog(e.getError());
        }
    }

    private PaymentParams createPaymentParams(String checkoutId) throws PaymentException {
        String cardHolder = cardHolderEditText.getText().toString();
        String cardNumber = cardNumberEditText.getText().toString();
        String cardExpiryMonth = cardExpiryMonthEditText.getText().toString();
        String cardExpiryYear = cardExpiryYearEditText.getText().toString();
        String cardCVV = cardCvvEditText.getText().toString();

        return new CardPaymentParams(
                checkoutId,
                cardBrand.toUpperCase(),
                cardNumber,
                cardHolder,
                cardExpiryMonth,
                "20" + cardExpiryYear,
                cardCVV
        );
    }

    private void showConfirmationDialog(String amount, String currency) {
        new AlertDialog.Builder(getActivity())
                .setMessage(String.format(getString(R.string.message_payment_confirmation), amount, currency))
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pay(checkoutId);
                    }
                })
                .setNegativeButton(R.string.button_cancel, null)
                .setCancelable(false)
                .show();
    }
    public static HyperPayFragment getInstance() {
        return instance;
    }
}




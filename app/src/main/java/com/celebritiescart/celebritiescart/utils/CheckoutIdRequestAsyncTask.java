package com.celebritiescart.celebritiescart.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.celebritiescart.celebritiescart.constant.ConstantValues.PAYMENT_BASE_URL;


public class CheckoutIdRequestAsyncTask extends AsyncTask<String, Void, String> {

    private CheckoutIdRequestListener listener;

    public CheckoutIdRequestAsyncTask(CheckoutIdRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length != 4) {
            return null;
        }

        String amount = params[0];
        String currency = params[1];
        String cardtype=params[2];
        String merchantTransactionId=params[3];

        return requestCheckoutId(amount, currency,cardtype,merchantTransactionId);
    }

    @Override
    protected void onPostExecute(String checkoutId) {
        if (listener != null) {
            listener.onCheckoutIdReceived(checkoutId);
        }
    }

    private String requestCheckoutId(String amount,
                                     String currency,String cardtype,String merchantTransactionId) {
        String urlString = PAYMENT_BASE_URL + "/token?" +
                "amount=" + amount +
                "&currency=" + currency +
                "&paymentType=DB" +
                "&paymentCode="+cardtype+
                "&merchantTransactionId="+merchantTransactionId+
                /* store notificationUrl on your server to change it any time without updating the app */
                "&notificationUrl="+PAYMENT_BASE_URL+"/notification";
        URL url;
        HttpURLConnection connection = null;
        String checkoutId = null;

        try {
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);

             checkoutId=readFullyAsString(connection.getInputStream(),"UTF-8");
             checkoutId = checkoutId.replace("\"", "");


       /*     JsonReader reader = new JsonReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));

            reader.beginObject();

            while (reader.hasNext()) {
                if (reader.nextName().equals("checkoutId")) {
                    checkoutId = reader.nextString();

                    break;
                }
            }

            reader.endObject();
            reader.close();*/

            Log.d("Checkout", "Checkout ID: " + checkoutId);

        } catch (Exception e) {

            Log.e("Checkout", "Error: ", e);

        } finally {

            if (connection != null) {
                connection.disconnect();
            }

        }

        return checkoutId;

    }

    public String readFullyAsString(InputStream inputStream, String encoding) throws IOException {
        return readFully(inputStream).toString(encoding);
    }

    private ByteArrayOutputStream readFully(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos;
    }
}
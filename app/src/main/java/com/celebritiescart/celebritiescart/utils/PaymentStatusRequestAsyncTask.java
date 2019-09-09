package com.celebritiescart.celebritiescart.utils;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.celebritiescart.celebritiescart.constant.ConstantValues.PAYMENT_BASE_URL;


/**
 * Represents an async task to request a payment status from the server.
 */
public class PaymentStatusRequestAsyncTask extends AsyncTask<String, Void, String> {

    private PaymentStatusRequestListener listener;
    private String paymentCode;

    public PaymentStatusRequestAsyncTask(PaymentStatusRequestListener listener, String paymentCode) {
        this.listener = listener;
        this.paymentCode = paymentCode;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length != 1) {
            return null;
        }

        String resourcePath = params[0];

        if (resourcePath != null) {
            return requestPaymentStatus(resourcePath, paymentCode);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String paymentStatus) {
        if (listener != null) {
            if (paymentStatus == null) {
                listener.onErrorOccurred();

                return;
            }

            listener.onPaymentStatusReceived(paymentStatus);
        }
    }

    private String requestPaymentStatus(String resourcePath, String paymentCode) {
        if (resourcePath == null) {
            return null;
        }

        URL url;
        String urlString;
        HttpURLConnection connection = null;
        String paymentStatus = "";

        try {
            urlString = PAYMENT_BASE_URL + "/status?resourcePath=" +
                    URLEncoder.encode(resourcePath, "UTF-8") + "&paymentCode=" + paymentCode;

            Log.d("Status request url", "Status request url: " + urlString);

            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);


            JsonReader jsonReader = new JsonReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));

            jsonReader.beginArray();

            while (jsonReader.hasNext()) {
               /* if (jsonReader.nextName().equals("code")) {
                    paymentStatus = jsonReader.nextString();
                } else {
                    jsonReader.skipValue();
                }*/
                if (paymentStatus.isEmpty()) {
                    paymentStatus = jsonReader.nextString();
                } else {
                    paymentStatus = paymentStatus +","+ jsonReader.nextString();

                }
            }

            jsonReader.endArray();
            jsonReader.close();

            Log.d("Status", "Status: " + paymentStatus);
        } catch (Exception e) {
            Log.e("Error", "Error: ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return paymentStatus;
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

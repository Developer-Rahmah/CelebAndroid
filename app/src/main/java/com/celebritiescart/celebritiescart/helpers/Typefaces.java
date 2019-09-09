package com.celebritiescart.celebritiescart.helpers;

import android.content.Context;
import android.graphics.Typeface;

import com.celebritiescart.celebritiescart.constant.ConstantValues;

import java.util.Hashtable;

public class Typefaces {

    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    public static Typeface get(Context c, String name) {
        synchronized (cache) {
            if (ConstantValues.LANGUAGE_CODE.equals("en") && !cache.containsKey("baskvill_regular")) {
                Typeface t;
                t = Typeface.createFromAsset(
                        c.getAssets(),
                        "font/baskvill_regular.OTF"
                );
                cache.put("baskvill_regular", t);

            } else if (ConstantValues.LANGUAGE_CODE.equals("ar") && !cache.containsKey("geflow")) {
                Typeface t;

                t = Typeface.createFromAsset(
                        c.getAssets(),
                        "font/geflow.otf"
                );
                cache.put("geflow", t);

            }
            if (ConstantValues.LANGUAGE_CODE.equals("en")) {

                return cache.get("baskvill_regular");
            } else {
                return cache.get("geflow");

            }
        }
    }

}
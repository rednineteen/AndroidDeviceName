package com.rednineteen.android.adn;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.text.TextUtils;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created on 11/04/2017 by Juan Velasquez - email:  juan@rednineteen.com.
 *
 * Copyright 2017 Juan Velasquez - Rednineteen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ADNDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME       = "supported_devices.db";
    private static final int DATABASE_VERSION       = 1;
    private static final String DEVICE_TABLE_NAME   = "device";
    private static final String COLUMN_BRAND        = "brand";
    private static final String COLUMN_MARKET_NAME  = "market_name";
    private static final String COLUMN_DEVICE       = "device";
    private static final String COLUMN_MODEL        = "model";
    private static final String[] ALL_COLUMNS       = {COLUMN_BRAND,COLUMN_MARKET_NAME,COLUMN_DEVICE,COLUMN_MODEL};
    private static final String IGNORE_REGEX        = "[\"']";

    private static ADNDatabase instance;
    private static boolean closeDB                  = true;

    /**
     * Static to get the singleton of this class.
     *
     * @param context The application context.
     * @return ADNDatabase
     */
    public static ADNDatabase init(Context context) {
        if (instance == null)
            instance = new ADNDatabase(context);

        return instance;
    }

    /**
     * Private constructor
     *
     * @param context The application context.
     */
    private ADNDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Force DB overwrite every time the version number increments.
        setForcedUpgrade();
    }

    /**
     * Returns the Device object for the given model.
     *
     * @param model The model as given by Build.MODEL
     * @return Device object or null if not found
     */
    public static ADNDevice getDevice(String model) {
        return getDevice(model, null);
    }

    /**
     * Returns the Device object for the given model and codename.
     *
     * @param model The model as given by Build.MODEL
     * @param codename The codename as given by Build.DEVICE
     * @return Device object or null if not found
     */
    public static ADNDevice getDevice(String model, String codename) {
        // Check library has been initilised.
        if (instance == null) throw new IllegalStateException("Make sure you have init the library by calling ADNDatabase.init(context).");
        // Return if model is empty
        if (model == null || model.isEmpty()) return null;
        SQLiteDatabase db = instance.getReadableDatabase();;

        String selection;
        String args[];
        // We remove all single and double as all values in the DB shouldn't have them (they are removed when generating the DB)
        String m = model.replaceAll(IGNORE_REGEX,"").toLowerCase();
        String c;
        if (codename != null && !codename.isEmpty()) {
            c           = codename.replaceAll(IGNORE_REGEX,"").toLowerCase();
            args        = new String[]{m, c};
            selection   = "LOWER(" + COLUMN_MODEL + ") = ? AND LOWER(" + COLUMN_DEVICE + ") = ?";
        } else {
            args        = new String[]{m};
            selection   = "LOWER(" + COLUMN_MODEL + ") = ?";
        }

        Cursor cursor   = db.query(DEVICE_TABLE_NAME, ALL_COLUMNS, selection, args, null, null, null);
        ADNDevice d     = null;
        if (cursor.moveToFirst()) {
            d = new ADNDevice(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }
        cursor.close();

        if (closeDB)
            instance.close();

        return d;
    }

    /**
     * Returns the market name of the current device or returns the value of Build.MODEL if not found.
     *
     * @return String with the current device market name
     */
    public static String getDeviceName() {
        return getDeviceName(Build.MODEL, Build.MODEL, false);
    }

    /**
     * Returns the market name of the given model.
     *
     * @param model The model as given by Build.MODEL
     * @param fallback Fallback string name to be returned it no name was found
     * @return String with the current device market name
     */
    public static String getDeviceName(String model, String fallback) {
        return getDeviceName(model, fallback, false);
    }

    /**
     * Returns the market name of the given model.
     * If specified it also returns the Brand name concatenated with the market name.
     *
     * @param model The model as given by Build.MODEL
     * @param fallback Fallback string name to be returned it no name was found
     * @param withBrand If true it will return also the Brand name concatenated with the market name. If false it will return market name only.
     * @return String with the current device market name
     */
    public static String getDeviceName(String model, String fallback, boolean withBrand) {
        return getDeviceName(model, null, fallback, withBrand);
    }

    /**
     * Returns the market name of the given model and codename. If codename is null it tries to retrieve the market name with only the model.
     * If specified it also returns the Brand name concatenated with the market name.
     *
     * @param model The model as given by Build.MODEL
     * @param codename The codename as given by Build.DEVICE
     * @param fallback Fallback string name to be returned it no name was found
     * @param withBrand If true it will return also the Brand name concatenated with the market name. If false it will return market name only.
     * @return String with the current device market name
     */
    public static String getDeviceName(String model, String codename, String fallback, boolean withBrand) {
        ADNDevice d = getDevice(model, codename);
        if (d == null || TextUtils.isEmpty(d.getMarketName()))
            return fallback;
        else if (!withBrand || TextUtils.isEmpty( d.getBrand() ) || d.getMarketName().toLowerCase().contains( d.getBrand().toLowerCase() ))
            return d.getMarketName();
        else
            return d.getBrand() + " " + d.getMarketName();

    }

    /**
     * Opens the DB for multiple queries. Should be done on the onStart() method of an Activity.
     */
    public static void openDB() {
        if (instance == null) throw new IllegalStateException("Make sure you have init the library by calling ADNDatabase.init(context).");
        instance.getReadableDatabase();
        closeDB = false;
    }

    /**
     * Closes the DB. Should be done on the onStop() method of an Activity.
     */
    public static void closeDB() {
        if (instance == null) throw new IllegalStateException("Make sure you have init the library by calling ADNDatabase.init(context).");
        instance.close();
        closeDB = true;
    }

}

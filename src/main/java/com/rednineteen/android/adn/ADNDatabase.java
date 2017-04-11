package com.rednineteen.android.adn;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.text.TextUtils;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created on 11/04/2017 by Juan Velasquez - email:  juan@rednineteen.com.
 */
public class ADNDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME       = "supported_devices.sql3";
    private static final int DATABASE_VERSION       = 1;
    private static final String DEVICE_TABLE_NAME   = "device";
    private static final String COLUMN_BRAND        = "brand";
    private static final String COLUMN_MARKET_NAME  = "market_name";
    private static final String COLUMN_DEVICE       = "device";
    private static final String COLUMN_MODEL        = "model";
    private static final String[] ALL_COLUMNS       = {COLUMN_BRAND,COLUMN_MARKET_NAME,COLUMN_DEVICE,COLUMN_MODEL};

    private static ADNDatabase instance;

    public static ADNDatabase getInstance(Context context) {
        if (instance == null)
            instance = new ADNDatabase(context);

        return instance;
    }

    private ADNDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Force DB overwrite every time the version number increments.
        setForcedUpgrade();
    }

    public ADNDevice getDevice(String model) {
        SQLiteDatabase db   = getReadableDatabase();
        String selection    = COLUMN_MODEL + " = ?";
        Cursor cursor       = db.query(DEVICE_TABLE_NAME, ALL_COLUMNS, selection, new String[] {model}, null, null, null);

        ADNDevice d = null;
        if (cursor.moveToFirst()) {
            d = new ADNDevice(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }

        db.close();
        return d;
    }

    public String getDeviceName(String model, String fallback) {
        return getDeviceName(model, fallback, false);
    }

    public String getDeviceName(String model, String fallback, boolean withBrand) {
        ADNDevice d = getDevice(model);
        if (d == null || TextUtils.isEmpty(d.getMarketName()))
            return fallback;
        else if (!withBrand || TextUtils.isEmpty( d.getBrand() ) || d.getMarketName().toLowerCase().contains( d.getBrand().toLowerCase() ))
            return d.getMarketName();
        else
            return d.getBrand() + " " + d.getMarketName();

    }
}

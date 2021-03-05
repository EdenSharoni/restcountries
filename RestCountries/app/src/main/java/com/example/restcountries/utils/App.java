package com.example.restcountries.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restcountries.R;
import com.example.restcountries.adapter.CountryAdapter;
import com.example.restcountries.model.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private static Context mContext;

    //Country properties
    private static String mNativeName, mName;
    private static Long mArea;
    private static ArrayList<String> mBorders;
    private static JSONArray mBordersArray;

    //HTTP Request
    private static RequestQueue mRequestQueue;
    private static JsonArrayRequest mJsonArrayRequest;
    private static JsonObjectRequest mJsonObjectRequest;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        // Http request queue
        mRequestQueue = Volley.newRequestQueue(this);
    }

    public static Context getContext() {
        return mContext;
    }

    public static void infoToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT);
    }

    public static void SortByNameAscending(ArrayList<Country> mCountryArray) {
        Collections.sort(mCountryArray, (country1, country2) -> country1.getName().compareToIgnoreCase(country2.getName()));
    }

    public static void SortByNameDescending(ArrayList<Country> mCountryArray) {
        Collections.sort(mCountryArray, (country1, country2) -> country2.getName().compareToIgnoreCase(country1.getName()));
    }

    public static void SortAreaAscending(ArrayList<Country> mCountryArray) {
        Collections.sort(mCountryArray, (country1, country2) -> country1.getArea().compareTo(country2.getArea()));
    }

    public static void SortAreaDescending(ArrayList<Country> mCountryArray) {
        Collections.sort(mCountryArray, (country1, country2) -> country2.getArea().compareTo(country1.getArea()));
    }

    public static void sort(String mFilter, String tag, ArrayList<Country> mCountryArray, CountryAdapter mAdapter) {
        if (mFilter.equals(mContext.getResources().getStringArray(R.array.filter)[0])) {
            if (tag.equals(mContext.getString(R.string.ASC))) {
                Log.d(TAG, "SortByNameAscending");
                SortByNameAscending(mCountryArray);
            } else {
                Log.d(TAG, "SortByNameDescending");
                SortByNameDescending(mCountryArray);
            }
        } else {
            if (tag.equals(mContext.getString(R.string.ASC))) {
                Log.d(TAG, "SortAreaAscending");
                SortAreaAscending(mCountryArray);
            } else {
                Log.d(TAG, "SortAreaDescending");
                SortAreaDescending(mCountryArray);
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    public static void fetchAllData(ArrayList<Country> mCountryArray, CountryAdapter mAdapter, RecyclerView mRecyclerView) {
        mJsonArrayRequest = new JsonArrayRequest(Request.Method.GET, mContext.getString(R.string.url), null, response -> {
            try {
                for (int i = 0; i < 50; i++) {
                    JSONObject currentCountry = response.getJSONObject(i);
                    mNativeName = currentCountry.optString(mContext.getString(R.string.nativeName));
                    mName = currentCountry.optString(mContext.getString(R.string.name));
                    mArea = currentCountry.optLong(mContext.getString(R.string.area));
                    mBorders = new ArrayList<>();
                    mBordersArray = currentCountry.optJSONArray("borders");
                    if (mBordersArray != null) for (int j = 0; j < mBordersArray.length(); j++)
                        mBorders.add(mBordersArray.get(j).toString());
                    Country country = new Country(mNativeName, mName, mArea, mBorders);
                    mCountryArray.add(country);
                }
                mRecyclerView.setAdapter(mAdapter);
            } catch (JSONException e) {
                infoToast(e.getMessage());
            }
        }, error -> infoToast(error.getMessage()));
        mRequestQueue.add(mJsonArrayRequest);
    }

    public static void fetchDataByCode(ArrayList<Country> mCountryArray, CountryAdapter mAdapter, String code) {
        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mContext.getString(R.string.urlByCode) + code, null, response -> {
            mNativeName = response.optString(mContext.getString(R.string.nativeName));
            mName = response.optString(mContext.getString(R.string.name));
            mArea = response.optLong(mContext.getString(R.string.area));
            Country country = new Country(mNativeName, mName, mArea, null);
            mCountryArray.add(country);
            mAdapter.notifyDataSetChanged();
        }, error -> infoToast(error.getMessage()));
        mRequestQueue.add(mJsonObjectRequest);
    }
}

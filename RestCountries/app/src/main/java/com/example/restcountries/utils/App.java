package com.example.restcountries.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
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
            if (tag.equals(mContext.getString(R.string.ASC))) SortByNameAscending(mCountryArray);
            else SortByNameDescending(mCountryArray);
        } else {
            if (tag.equals(mContext.getString(R.string.ASC))) SortAreaAscending(mCountryArray);
            else SortAreaDescending(mCountryArray);
        }
        mAdapter.notifyDataSetChanged();
    }


    public static void fetchAllData(ArrayList<Country> mCountryArray, CountryAdapter mAdapter, RecyclerView mRecyclerView, ProgressBar mProgressBar) {
        JsonArrayRequest mJsonArrayRequest = new JsonArrayRequest(Request.Method.GET, mContext.getString(R.string.url), null, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject currentCountry = response.getJSONObject(i);
                    mNativeName = currentCountry.optString(mContext.getString(R.string.nativeName));
                    mName = currentCountry.optString(mContext.getString(R.string.name));
                    mArea = currentCountry.optLong(mContext.getString(R.string.area));
                    mBorders = new ArrayList<>();
                    mBordersArray = currentCountry.optJSONArray(mContext.getString(R.string.borders));
                    if (mBordersArray != null) for (int j = 0; j < mBordersArray.length(); j++)
                        mBorders.add(mBordersArray.get(j).toString());
                    Country country = new Country(mNativeName, mName, mArea, mBorders);
                    mCountryArray.add(country);
                }
                mRecyclerView.setAdapter(mAdapter);

                mRecyclerView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                SortByNameAscending(mCountryArray);
            } catch (JSONException e) {
                mProgressBar.setVisibility(View.GONE);
                infoToast(e.getMessage());
            }
        }, error -> {
            mProgressBar.setVisibility(View.GONE);
            infoToast(mContext.getString(R.string.fetchError));
        });
        mRequestQueue.add(mJsonArrayRequest);
    }

    public static void fetchDataByCode(ArrayList<Country> mCountryArray, CountryAdapter mAdapter, String url, ProgressBar mProgressBar) {
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            mNativeName = response.optString(mContext.getString(R.string.nativeName));
            mName = response.optString(mContext.getString(R.string.name));
            mArea = response.optLong(mContext.getString(R.string.area));
            Country country = new Country(mNativeName, mName, mArea, null);
            mCountryArray.add(country);
            mAdapter.notifyDataSetChanged();
        }, error -> {
            mProgressBar.setVisibility(View.GONE);
            infoToast(mContext.getString(R.string.fetchError));
        });
        mRequestQueue.add(mJsonObjectRequest);
    }

    public static void fetchBorders(ArrayList<Country> mCountryArray, ArrayList<String> borders, CountryAdapter mAdapter, RecyclerView mRecyclerView, ProgressBar mProgressBar, TextView mNoBordersText) {
        if (borders.size() > 0)
            for (int i = 0; i < borders.size(); i++)
                fetchDataByCode(mCountryArray, mAdapter, mContext.getString(R.string.urlByCode) + borders.get(i), mProgressBar);
        if (borders.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            mNoBordersText.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mNoBordersText.setVisibility(View.GONE);
        }
        mProgressBar.setVisibility(View.GONE);
    }
}

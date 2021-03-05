package com.example.restcountries.utils;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.restcountries.model.Country;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
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
}

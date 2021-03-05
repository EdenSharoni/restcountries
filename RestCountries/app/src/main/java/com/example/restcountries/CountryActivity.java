package com.example.restcountries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restcountries.adapter.CountryAdapter;
import com.example.restcountries.model.Country;

import java.util.ArrayList;

public class CountryActivity extends AppCompatActivity {
    private static final String TAG = CountryActivity.class.getSimpleName();

    //XML
    private RecyclerView mRecyclerView;
    private CountryAdapter mAdapter;

    private TextView mTitle;

    // Contains all photos url
    private ArrayList<Country> mCountryArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        // Find view from xml
        mTitle = findViewById(R.id.title);

        Country country = getIntent().getParcelableExtra(getResources().getString(R.string.country));

        Log.d(TAG, "nativeName: " + country.getNativeName());
        Log.d(TAG, "name: " + country.getName());
        Log.d(TAG, "area: " + country.getArea());
        Log.d(TAG, "borders: " + country.getBorders());

        mTitle.setText(country.getName());

//
//        // Find view from xml
//        mRecyclerView = findViewById(R.id.recycler_viewer);
//
//        // Initialize recycler view and adpater
//        mAdapter = new CountryAdapter(mCountryArray);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setAdapter(mAdapter);

    }
}
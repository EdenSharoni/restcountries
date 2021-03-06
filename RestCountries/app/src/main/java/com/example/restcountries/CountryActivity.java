package com.example.restcountries;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restcountries.adapter.CountryAdapter;
import com.example.restcountries.model.Country;

import java.util.ArrayList;

import static com.example.restcountries.utils.App.fetchBorders;

public class CountryActivity extends AppCompatActivity implements CountryAdapter.OnCountryListener {

    private static final String TAG = CountryActivity.class.getSimpleName();

    //XML
    private RecyclerView mRecyclerView;
    private TextView mTitle;
    private TextView mNoBordersText;
    private ProgressBar mProgressBar;

    //Adapters
    private CountryAdapter mAdapter;

    // Contains all countries
    private ArrayList<Country> mCountryArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        // Find view from xml
        mTitle = findViewById(R.id.title);
        mRecyclerView = findViewById(R.id.recyclerViewerBorders);
        mNoBordersText = findViewById(R.id.noBordersText);
        mProgressBar = findViewById(R.id.progressBarCountry);

        //get chosen country from parcelable
        Country country = getIntent().getParcelableExtra(getString(R.string.country));

        //set title text
        mTitle.setText(country.getName() + " " + getString(R.string.borders));

        //Initialize recyclerview and adapter
        mAdapter = new CountryAdapter(mCountryArray, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        fetchBorders(mCountryArray, country.getBorders(), mAdapter, mRecyclerView, mProgressBar, mNoBordersText);
    }

    @Override
    public void onCountryClick(int position, View viewItem) {
        //nothing
    }
}
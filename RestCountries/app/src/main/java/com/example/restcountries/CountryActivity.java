package com.example.restcountries;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restcountries.adapter.CountryAdapter;
import com.example.restcountries.model.Country;

import java.util.ArrayList;

import static com.example.restcountries.utils.App.fetchDataByCode;
import static com.example.restcountries.utils.App.infoToast;

public class CountryActivity extends AppCompatActivity implements CountryAdapter.OnCountryListener {

    private static final String TAG = CountryActivity.class.getSimpleName();

    //XML
    private RecyclerView mRecyclerView;
    private TextView mTitle;

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
        mRecyclerView = findViewById(R.id.recycler_viewer_borders);

        Country country = getIntent().getParcelableExtra(getString(R.string.country));
        
        Log.d(TAG, "borders: " + country.getBorders());
        ArrayList<String> borders = country.getBorders();
        mTitle.setText(country.getName() + getString(R.string.borders));

        //Initialize recyclerview and adapter
        mAdapter = new CountryAdapter(mCountryArray, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        for (int i = 0; i < country.getBorders().size(); i++) {
            fetchDataByCode(mCountryArray, mAdapter, country.getBorders().get(i));
        }
    }

    @Override
    public void onCountryClick(int position, View viewItem) {

    }
}
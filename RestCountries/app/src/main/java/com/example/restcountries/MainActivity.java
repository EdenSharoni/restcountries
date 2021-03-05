package com.example.restcountries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restcountries.adapter.CountryAdapter;
import com.example.restcountries.model.Country;

import java.util.ArrayList;

import static com.example.restcountries.utils.App.fetchAllData;
import static com.example.restcountries.utils.App.infoToast;
import static com.example.restcountries.utils.App.sort;

public class MainActivity extends AppCompatActivity implements CountryAdapter.OnCountryListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    //XML
    private RecyclerView mRecyclerView;
    private ImageView mFilterAscOrDes;
    private Spinner mSpinner;

    //Adapter
    private CountryAdapter mAdapter;
    private ArrayAdapter<String> mSpinnerAdapter;

    // Contains all countries
    private ArrayList<Country> mCountryArray = new ArrayList<>();

    private int mBackTwice = 1;
    private String mFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find view from xml
        mRecyclerView = findViewById(R.id.recycler_viewer);
        mFilterAscOrDes = findViewById(R.id.filterAscOrDes);
        mSpinner = findViewById(R.id.spinner);

        // Initialize spinner and adapter
        mSpinnerAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.filter));
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSpinnerAdapter);

        //Initialize recyclerview and adapter
        mAdapter = new CountryAdapter(mCountryArray, this);
        mRecyclerView.setHasFixedSize(true);

        //Listeners
        mSpinner.setOnItemSelectedListener(this);
        mFilterAscOrDes.setOnClickListener(this);

        fetchAllData(mCountryArray, mAdapter, mRecyclerView);
    }

    @Override
    public void onBackPressed() {
        if (mBackTwice == 1) {
            infoToast(getString(R.string.backspace));
            mBackTwice--;
        } else super.onBackPressed();
    }

    @Override
    public void onCountryClick(int position, View viewItem) {
        Intent intent = new Intent(this, CountryActivity.class);
        intent.putExtra(getString(R.string.country), mCountryArray.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mFilter = adapterView.getItemAtPosition(i).toString();
        sort(mFilter, mFilterAscOrDes.getTag().toString(), mCountryArray, mAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (view.getTag().equals(getString(R.string.ASC))) {
            view.setTag(getString(R.string.DSC));
            mFilterAscOrDes.setImageResource(R.drawable.ic_arrow_upward);
        } else {
            view.setTag(getString(R.string.ASC));
            mFilterAscOrDes.setImageResource(R.drawable.ic_arrow_downward);
        }
        sort(mFilter, view.getTag().toString(), mCountryArray, mAdapter);
    }
}
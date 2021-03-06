package com.example.restcountries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restcountries.adapter.CountryAdapter;
import com.example.restcountries.model.Country;

import java.util.ArrayList;

import static com.example.restcountries.utils.App.fetchAllData;
import static com.example.restcountries.utils.App.infoToast;
import static com.example.restcountries.utils.App.sort;

public class MainActivity extends AppCompatActivity implements CountryAdapter.OnCountryListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    //XML
    private RecyclerView mRecyclerView;
    private LinearLayout mFilterAscOrDes;
    private Spinner mSpinner;
    private ProgressBar mProgressBar;
    private TextView mTextViewAscOrDes;
    private ImageView mArrowFilterAscOrDsc;

    //Adapter
    private CountryAdapter mAdapter;
    private ArrayAdapter<String> mSpinnerAdapter;

    // Contains all countries
    private ArrayList<Country> mCountryArray = new ArrayList<>();

    private int mBackTwice = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find view from xml
        mRecyclerView = findViewById(R.id.recyclerViewer);
        mFilterAscOrDes = findViewById(R.id.filterAscOrDes);
        mSpinner = findViewById(R.id.spinner);
        mProgressBar = findViewById(R.id.progressBar);
        mTextViewAscOrDes = findViewById(R.id.textViewAscOrDes);
        mArrowFilterAscOrDsc = findViewById(R.id.arrowFilterAscOrDsc);

        // Initialize spinner and adapter
        mSpinnerAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.filter));
        mSpinner.setAdapter(mSpinnerAdapter);

        //Initialize recyclerview and adapter
        mAdapter = new CountryAdapter(mCountryArray, this);
        mRecyclerView.setHasFixedSize(true);

        //Listeners
        mSpinner.setOnItemSelectedListener(this);
        mFilterAscOrDes.setOnClickListener(this);

        fetchAllData(mCountryArray, mAdapter, mRecyclerView, mProgressBar);
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
    public void onClick(View view) {
        if (view.getTag().equals(getString(R.string.ASC))) {
            view.setTag(getString(R.string.DSC));
            mArrowFilterAscOrDsc.setImageResource(R.drawable.ic_arrow_upward);
            mTextViewAscOrDes.setText(R.string.DSC);
        } else {
            view.setTag(getString(R.string.ASC));
            mArrowFilterAscOrDsc.setImageResource(R.drawable.ic_arrow_downward);
            mTextViewAscOrDes.setText(R.string.ASC);
        }
        sort(mSpinner.getSelectedItem().toString(), view.getTag().toString(), mCountryArray, mAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sort(mSpinner.getSelectedItem().toString(), mFilterAscOrDes.getTag().toString(), mCountryArray, mAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Nothing
    }
}
package com.example.restcountries;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.restcountries.adapter.CountryAdapter;
import com.example.restcountries.model.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.restcountries.utils.App.infoToast;
import static com.example.restcountries.utils.App.SortAreaAscending;
import static com.example.restcountries.utils.App.SortAreaDescending;
import static com.example.restcountries.utils.App.SortByNameAscending;
import static com.example.restcountries.utils.App.SortByNameDescending;

public class MainActivity extends AppCompatActivity implements CountryAdapter.OnCountryListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    //XML
    private RecyclerView mRecyclerView;
    private ImageView mFilterAscOrDes;
    private Spinner mSpinner;

    //Adapter
    private CountryAdapter mAdapter;
    private ArrayAdapter<String> mSpinnerAdapter;

    //HTTP Request
    private RequestQueue mRequestQueue;
    private JsonArrayRequest mJsonArrayRequest;
    private JSONArray mBordersArray;

    // Contains all countries
    private ArrayList<Country> mCountryArray = new ArrayList<>();

    //Country
    private String mNativeName, mName;
    private Long mArea;
    private ArrayList<String> mBorders;

    private int mBackTwice = 1;

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

        // Http request queue
        mRequestQueue = Volley.newRequestQueue(this);

        getData();
    }

    private void getData() {
        mJsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getResources().getString(R.string.url), null, response -> {
            try {
                for (int i = 0; i < 5; i++) {
                    JSONObject currentCountry = response.getJSONObject(i);
                    mNativeName = currentCountry.optString("nativeName");
                    mName = currentCountry.optString("name");
                    mArea = currentCountry.optLong("area");
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

    @Override
    public void onBackPressed() {
        if (mBackTwice == 1) {
            infoToast(getResources().getString(R.string.backspace));
            mBackTwice--;
        } else super.onBackPressed();
    }

    @Override
    public void onCountryClick(int position, View viewItem) {
        Intent intent = new Intent(this, CountryActivity.class);
        intent.putExtra(getResources().getString(R.string.country), mCountryArray.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
    }
}
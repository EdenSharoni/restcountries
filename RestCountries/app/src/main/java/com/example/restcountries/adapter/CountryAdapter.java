package com.example.restcountries.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restcountries.R;
import com.example.restcountries.model.Country;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private static final String TAG = CountryAdapter.class.getSimpleName();

    // Contains all images
    private final ArrayList<Country> mCountryList;
    //Country Click Listener
    private final OnCountryListener mOnCountryListener;

    public CountryAdapter(ArrayList<Country> mCountryList, OnCountryListener mOnCountryListener) {
        this.mCountryList = mCountryList;
        this.mOnCountryListener = mOnCountryListener;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country, parent, false);
        return new CountryViewHolder(view, mOnCountryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country currentCountry = mCountryList.get(position);
        holder.mNativeName.setText(currentCountry.getNativeName());
        holder.name.setText(currentCountry.getName());
        holder.area.setText(currentCountry.getArea().toString());
    }

    @Override
    public int getItemCount() {
        return mCountryList.size();
    }

    public static class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mNativeName;
        private final TextView name;
        private final TextView area;
        private final OnCountryListener mOnCountryListener;

        public CountryViewHolder(@NonNull View itemView, OnCountryListener mOnCountryListener) {
            super(itemView);
            this.mNativeName = itemView.findViewById(R.id.nativeName);
            this.name = itemView.findViewById(R.id.name);
            this.area = itemView.findViewById(R.id.area);

            this.mOnCountryListener = mOnCountryListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnCountryListener.onCountryClick(getAdapterPosition(), view);
        }
    }

    public interface OnCountryListener {
        void onCountryClick(int position, View viewItem);
    }
}

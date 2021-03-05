package com.example.restcountries.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Country implements Parcelable {
    private String mNativeName;
    private String mName;
    private Long mArea;
    private ArrayList<String> mBorders;


    // Constructor
    public Country(String nativeName, String name, Long area, ArrayList<String> borders) {
        this.mNativeName = nativeName;
        this.mName = name;
        this.mArea = area;
        this.mBorders = borders;
    }

    protected Country(Parcel in) {
        mNativeName = in.readString();
        mName = in.readString();
        if (in.readByte() == 0) {
            mArea = null;
        } else {
            mArea = in.readLong();
        }
        mBorders = in.createStringArrayList();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public String getNativeName() {
        return mNativeName;
    }

    public void setNativeName(String mNativeName) {
        this.mNativeName = mNativeName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public Long getArea() {
        return mArea;
    }

    public void setArea(Long mArea) {
        this.mArea = mArea;
    }

    public ArrayList<String> getBorders() {
        return mBorders;
    }

    public void setBorders(ArrayList<String> mBorders) {
        this.mBorders = mBorders;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mNativeName);
        parcel.writeString(mName);
        if (mArea == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(mArea);
        }
        parcel.writeStringList(mBorders);
    }
}

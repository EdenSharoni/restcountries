package com.example.restcountries.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Country implements Parcelable {
    private String nativeName;
    private String name;
    private Long area;
    private ArrayList<String> borders;


    // Constructor
    public Country(String nativeName, String name, Long area, ArrayList<String> borders) {
        this.nativeName = nativeName;
        this.name = name;
        this.area = area;
        this.borders = borders;
    }

    protected Country(Parcel in) {
        nativeName = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            area = null;
        } else {
            area = in.readLong();
        }
        borders = in.createStringArrayList();
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
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public ArrayList<String> getBorders() {
        return borders;
    }

    public void setBorders(ArrayList<String> borders) {
        this.borders = borders;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nativeName);
        parcel.writeString(name);
        if (area == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(area);
        }
        parcel.writeStringList(borders);
    }
}

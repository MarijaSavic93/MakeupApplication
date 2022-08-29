package com.example.makeupapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Product  implements Parcelable {
    @SerializedName("name")
    private String name;

    @SerializedName("brand")
    private String brand;

    @SerializedName("price")
    private String price;

    @SerializedName("image_link")
    private String imageUrl;

    @SerializedName("product_type")
    private String type;

    public Product(String name, String brand, String price, String imageUrl, String type){
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    protected Product(Parcel parcel) {
        name = parcel.readString();
        brand = parcel.readString();
        price = parcel.readString();
        imageUrl = parcel.readString();
        type = parcel.readString();
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(brand);
        parcel.writeString(price);
        parcel.writeString(imageUrl);
        parcel.writeString(type);
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel parcel) {
            return new Product(parcel);
        }

        @Override
        public Product[] newArray(int i) {
            return new Product[i];
        }
    };

}




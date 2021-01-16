package com.example.udclient.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ProductDto implements Serializable, Parcelable {
    private Integer id_product;
    private String name;
    private Double price;
    private String nick;
    private String date;
    private String time;
    private Integer id_meeting;

    public ProductDto() {
    }

    public ProductDto(Integer id_product, String name, Double price, String nick, String date,  String time, Integer id_meeting) {
        this.id_product = id_product;
        this.name = name;
        this.price = price;
        this.nick = nick;
        this.id_meeting = id_meeting;
        this.date = date;
        this.time = time;
    }


    protected ProductDto(Parcel in) {
        if (in.readByte() == 0) {
            id_product = null;
        } else {
            id_product = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        nick = in.readString();
        date = in.readString();
        time = in.readString();
        if (in.readByte() == 0) {
            id_meeting = null;
        } else {
            id_meeting = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id_product == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id_product);
        }
        dest.writeString(name);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
        dest.writeString(nick);
        dest.writeString(date);
        dest.writeString(time);
        if (id_meeting == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id_meeting);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductDto> CREATOR = new Creator<ProductDto>() {
        @Override
        public ProductDto createFromParcel(Parcel in) {
            return new ProductDto(in);
        }

        @Override
        public ProductDto[] newArray(int size) {
            return new ProductDto[size];
        }
    };

    public Integer getId_product() {
        return id_product;
    }

    public void setId_product(Integer id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getId_meeting() {
        return id_meeting;
    }

    public void setId_meeting(Integer id_meeting) {
        this.id_meeting = id_meeting;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

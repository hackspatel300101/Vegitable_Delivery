package com.example.vegitable_delivery.model;

public class Product_details {
    String imageurl;
    String name;
    String price;
    String price1;
    String weight;
    String wwight1;

    public Product_details(String imageurl, String name, String price, String price1, String weight, String wwight1) {
        this.imageurl = imageurl;
        this.name = name;
        this.price = price;
        this.price1 = price1;
        this.weight = weight;
        this.wwight1 = wwight1;
    }

    @Override
    public String toString() {
        return "Product_details{" +
                "imageurl='" + imageurl + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", price1='" + price1 + '\'' +
                ", weight='" + weight + '\'' +
                ", wwight1='" + wwight1 + '\'' +
                '}';
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWwight1() {
        return wwight1;
    }

    public void setWwight1(String wwight1) {
        this.wwight1 = wwight1;
    }
}


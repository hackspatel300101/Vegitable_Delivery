package com.example.vegitable_delivery.model;

public class Product_details {
    String imageurl;
    String name;
    String price;
    String weight;



    public Product_details(String imageurl, String name, String price, String weight) {
        this.imageurl = imageurl;
        this.name = name;
        this.price = price;
        this.weight = weight;
    }



    @Override
    public String toString() {
        return "product_details{" +
                "imageurl='" + imageurl + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", weight='" + weight + '\'' +
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}


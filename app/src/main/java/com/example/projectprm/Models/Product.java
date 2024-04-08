package com.example.projectprm.Models;

import java.io.Serializable;

public class Product implements Serializable {
    String description;
    String pic_url;
    int price;
    int review;
    int score;
    String tittle;
    private int numberInCart;

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public Product() {
    }

    public Product(String description, String pic_url, int price, int review, int score, String tittle) {
        this.description = description;
        this.pic_url = pic_url;
        this.price = price;
        this.review = review;
        this.score = score;
        this.tittle = tittle;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
}

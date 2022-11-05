package com.example.scraping.scrol;

public class Scroll {

    private String title;
    private String genre;
    private String price;
    private String date;
    private String description;
    private String url;

    public Scroll(String title, String genre, String price, String date, String description, String url) {
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.date = date;
        this.description = description;
        this.url = url;
    }

    public Scroll(String title, String price, String description, String url) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.url = url;
    }

    public Scroll() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return  "Title :- " + title + '\n' +
                "Genre :- " + genre + '\n' +
                "Price :- " + price + '\n' +
                "Date :- " + date + '\n' +
                "Description :- " + description + '\n' +
                "URL :- " + url +
                "\n--------------------------------------------------------------------------------------------\n";
    }
}

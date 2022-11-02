package com.example.scraping.scrol;

public class Scrol {
    private String title;
    private String discription;
    private String price;
    private String url;

    public Scrol(String title, String discription, String price, String url) {
        this.title = title;
        this.discription = discription;
        this.price = price;
        this.url = url;
    }

    public Scrol() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Scrol{" +
                "title='" + title + '\'' +
                ", discription='" + discription + '\'' +
                ", price='" + price + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

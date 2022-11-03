package com.example.scraping.scrol;

public record Scroll(String title, String description, String price, String url, String imageUrl) {

    @Override
    public String toString() {
        return "Title :- " + title + '\n' +
                "Price :- " + price + '\n' +
                "Description :- " + description + '\n' +
                "URL :- " + url +
                "\n--------------------------------------------------------------------------------------------\n";
    }
}

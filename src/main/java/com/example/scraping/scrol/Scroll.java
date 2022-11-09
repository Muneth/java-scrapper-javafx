package com.example.scraping.scrol;

/**
 * The type Scroll.
 */
public class Scroll {

    private String title;
    private String genre;
    private String price;
    private String date;
    private String description;
    private String url;

    /**
     * Instantiates a new Scroll.
     *
     * @param title       the title
     * @param genre       the genre
     * @param price       the price
     * @param date        the date
     * @param description the description
     * @param url         the url
     */
    public Scroll(String title, String genre, String price, String date, String description, String url) {
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.date = date;
        this.description = description;
        this.url = url;
    }

    /**
     * Instantiates a new Scroll.
     *
     * @param title       the title
     * @param price       the price
     * @param description the description
     * @param url         the url
     */
    public Scroll(String title, String price, String description, String url) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.url = url;
    }

    /**
     * Instantiates a new Scroll.
     */
    public Scroll() {
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets genre.
     *
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets genre.
     *
     * @param genre the genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
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

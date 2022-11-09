package com.example.scraping.scrol;
import com.example.scraping.VinyleController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLImageElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Vinyl corner scroll.
 */
public class VinylCornerScroll {
    /**
     * Search array list.
     *
     * @param searchWord the search word
     * @param min        the min
     * @param max        the max
     * @param year       the year
     * @return the array list
     * @throws Exception the exception
     */
    public ArrayList<Scroll> search(String searchWord, double min, double max, String year) throws Exception {

        String url = "https://www.vinylcorner.fr/catalogsearch/result/?q=" + searchWord;


        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlAnchor> links = htmlPage.getByXPath("//a[@class='product photo product-item-photo']");

        int limit = 10;
        String title;
        String price;
        String description;
        String genre = "";
        String resultUrl;
        String date;
        String imageUrl="";
        ArrayList <Scroll> scrolls = new ArrayList<>();
        for (int i = 0; i < links.size(); i++) {
            if (i == limit) {
                break;
            }
            resultUrl = String.valueOf(links.get(i).click().getUrl());
            try {
                HtmlPage htmlPage1 = webClient.getPage(resultUrl);
                title = ((HtmlElement) htmlPage1.getByXPath("/html/body/div[2]/main/div/div/div/div[2]/div[2]/p").get(0)).getTextContent();
//                genre = ((HtmlElement) htmlPage1.getByXPath(".//p[@class='ref-genre-cat show-list-only']").get(0)).getTextContent();
                price = ((HtmlElement) htmlPage1.getByXPath("/html/body/div[2]/main/div/div/div/div[2]/div[2]/div[4]/div/span/span/span").get(0)).getTextContent();
                date = ((HtmlElement) htmlPage1.getByXPath("/html/body/div[2]/main/div/div/div/div[5]/div[2]/div/div/div[1]/div[2]/p[2]").get(0)).getTextContent();
                description = ((HtmlElement) htmlPage1.getByXPath("/html/body/div[2]/main/div/div/div/div[2]/div[2]/div[3]/span/span").get(0)).getTextContent();
                //imageUrl = ((HtmlImage)htmlPage1.getFirstByXPath("/html/body/div[2]/main/div/div/div/div[2]/div[1]/div[2]/div[2]/div[2]/div[1]/div[2]/div[1]/img")).getSrcAttribute();
                double prix = VinyleController.convertToDouble(price);
                String scrollYear = VinyleController.getYear(date);
                if(!Objects.equals(year, scrollYear)){
                    System.out.println("No album found ...");
                    break;
                } else {
                    if(prix>=min && prix<=max){
                    scrolls.add(new Scroll(title,genre,price,date, VinyleController.checkIfNull(description),VinyleController.checkIfNull(imageUrl)));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return scrolls;
    }
}

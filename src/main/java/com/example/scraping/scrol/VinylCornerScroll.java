package com.example.scraping.scrol;
import com.example.scraping.VinyleController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.ArrayList;
import java.util.List;

public class VinylCornerScroll {
    public ArrayList<Scroll> search(String searchWord) throws Exception {

        String url = "https://www.vinylcorner.fr/recherche?controller=search&s=" + searchWord;

        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlAnchor> links = htmlPage.getByXPath("//a[@class='btn btn-details']");

        int limit = 5;
        String title;
        String price;
        String description;
        String genre;
        String resultUrl;
        String date;
        ArrayList <Scroll> scrolls = new ArrayList<>();
        for (int i = 0; i < links.size(); i++) {
            if (i == limit) {
                break;
            }
            resultUrl = String.valueOf(links.get(i).click().getUrl());
            try {
                HtmlPage htmlPage1 = webClient.getPage(resultUrl);
                title = ((HtmlHeading1) htmlPage1.getByXPath(".//h1[@class='productpage_title']").get(0)).getTextContent();
                genre = ((HtmlElement) htmlPage1.getByXPath(".//p[@class='ref-genre-cat show-list-only']").get(0)).getTextContent();
                price = ((HtmlElement) htmlPage1.getByXPath("/html/body/main/section/div[2]/div/div/div/section/div[1]/div[2]/div[2]/div[1]/div/span").get(0)).getTextContent();
                date = ((HtmlElement) htmlPage1.getByXPath("/html/body/main/section/div[2]/div/div/div/section/div[1]/div[2]/div[1]/p[2]/strong").get(0)).getTextContent();
                description = ((HtmlDivision) htmlPage1.getByXPath(".//div[@class='product-description']").get(0)).getTextContent();
                scrolls.add(new Scroll(title,genre,price,date, VinyleController.checkIfNull(description),resultUrl));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return scrolls;
    }
}

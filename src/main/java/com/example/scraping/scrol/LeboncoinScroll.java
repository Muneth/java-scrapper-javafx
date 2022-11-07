package com.example.scraping.scrol;
import com.example.scraping.VinyleController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.ArrayList;
import java.util.List;
public class LeboncoinScroll {
    public ArrayList<Scroll> search(String searchWord, int min, int max) throws Exception{

        String url = String.format("https://www.leboncoin.fr/recherche?text=%s", searchWord.replace(" ", "%20"))+ "&price="+ min + "-" + max;

        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlAnchor> links = htmlPage.getByXPath("//a[@data-qa-id='aditem_container']");

        int limit = 3;
        String title;
        String price;
        String description;
        String genre = "";
        String resultUrl;
        String date = "";
        String imageUrl;
        ArrayList <Scroll> scrolls = new ArrayList<>();
        for (int i = 0; i < links.size(); i++) {
            if (i == limit){
                break;
            }
            resultUrl = String.valueOf(links.get(i).click().getUrl());
                try{
                    HtmlPage htmlPage1 = webClient.getPage(resultUrl);
                    title = ((HtmlHeading1) htmlPage1.getByXPath(".//h1[@data-qa-id='adview_title']").get(0)).getTextContent();
//                    genre = ((HtmlElement) htmlPage1.getByXPath(".//p[@class='']").get(0)).getTextContent();
                    price = ((HtmlDivision) htmlPage1.getByXPath(".//div[@data-qa-id='adview_price']").get(0)).getTextContent();
//                    date = ((HtmlElement) htmlPage1.getByXPath("/html/body/main/section/div[2]/div/div/div/section/div[1]/div[2]/div[1]/p[2]/strong").get(0)).getTextContent();
                    description = ((HtmlDivision) htmlPage1.getByXPath(".//div[@data-qa-id='adview_description_container']").get(0)).getTextContent();
                    imageUrl = ((HtmlImage)htmlPage1.getFirstByXPath(".//img[@class='_1cnjm']")).getSrcAttribute();
                    scrolls.add(new Scroll(title,genre,price,date, VinyleController.checkIfNull(description),VinyleController.checkIfNull(imageUrl)));
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        return scrolls;
    }
}

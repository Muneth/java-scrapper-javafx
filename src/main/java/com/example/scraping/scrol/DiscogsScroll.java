package com.example.scraping.scrol;
import com.example.scraping.VinyleController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.ArrayList;
import java.util.List;
public class DiscogsScroll {
    public String search(String searchWord) throws Exception{

        String url = String.format("https://www.discogs.com/fr/sell/list?format=Vinyl&currency=EUR&q=%s", searchWord);

        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlAnchor> links = htmlPage.getByXPath(".//a[@class='item_description_title']");

        String results = "";
        int limit = 5;
        String title;
        String price;
        String resultUrl ="";
        for (int i = 0; i < links.size(); i++) {
            if (i == limit){
                break;
            }
            title = links.get(i).getTextContent();
            resultUrl = String.valueOf(links.get(i).click().getUrl());
            try{
                HtmlPage htmlPage1 = webClient.getPage(resultUrl);
                price = ((HtmlSpan) htmlPage1.getByXPath(".//span[@class='price']").get(0)).getTextContent();
                String head = VinyleController.checkDescription((HtmlDivision) htmlPage1.getByXPath(".//div[@class='head']").get(0));
                String content = VinyleController.checkDescription((HtmlAnchor) htmlPage1.getByXPath(".//div[@class='content']//a").get(0));
                String description = head+"   "+content;
                results += "Title :- " + title + '\n' +
                        "Price :- " + price + '\n' +
                        "Description :-  " +'\n' +
                        "              " + description  +'\n' +
                        "URL :- " + resultUrl +
                        "\n--------------------------------------------------------------------------------------------\n";
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return results;
    }
}

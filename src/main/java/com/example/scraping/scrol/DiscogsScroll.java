package com.example.scraping.scrol;
import com.example.scraping.VinyleController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.ArrayList;
import java.util.List;
public class DiscogsScroll {
    public ArrayList<Scroll> search(String searchWord, double min ,double max) throws Exception{

        String url = String.format("https://www.discogs.com/fr/sell/list?format=Vinyl&currency=EUR&q=%s", searchWord);

        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlAnchor> links = htmlPage.getByXPath(".//a[@class='item_description_title']");

        int limit = 15;
        String title;
        String price;
        String description;
        String genre;
        String resultUrl;
        String date;
        String imageUrl;
        ArrayList <Scroll> scrolls = new ArrayList<>();
        for (int i = 0; i < links.size(); i++) {
            if (i == limit){
                break;
            }
            title = links.get(i).getTextContent();
            resultUrl = String.valueOf(links.get(i).click().getUrl());
            try{
                HtmlPage htmlPage1 = webClient.getPage(resultUrl);
                genre = ((HtmlElement) htmlPage1.getByXPath("/html/body/div[1]/div[4]/div[1]/div/div[1]/div/div[1]/div[11]").get(0)).getTextContent();
                price = ((HtmlSpan) htmlPage1.getByXPath("/html/body/div[1]/div[4]/div[2]/div/div[1]/div/div/p/span[1]").get(0)).getTextContent();
                date = ((HtmlElement) htmlPage1.getByXPath("/html/body/div[1]/div[4]/div[1]/div/div[1]/div/div[1]/div[9]").get(0)).getTextContent();
                String head = ((HtmlDivision) htmlPage1.getByXPath(".//div[@class='head']").get(0)).getTextContent();
                String content = ((HtmlAnchor) htmlPage1.getByXPath(".//div[@class='content']//a").get(0)).getTextContent();
                description = head+"   "+content;
                imageUrl = ((HtmlImage)htmlPage1.getFirstByXPath("/html/body/div[1]/div[4]/div[1]/div/div[1]/div/div[2]/a/span[2]/img")).getSrcAttribute();
                double prix = VinyleController.convertToDouble(price);
                if(prix>=min && prix<=max) {
                    scrolls.add(new Scroll(title, genre, String.valueOf(prix + " €"), date, description, imageUrl));
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return scrolls;
    }
}

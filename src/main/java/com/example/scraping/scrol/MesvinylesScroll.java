package com.example.scraping.scrol;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.List;
public class MesvinylesScroll {
    public String search(String searchWord) throws Exception{

        String url = "https://mesvinyles.fr/fr/recherche?controller=search&s=" + searchWord;

        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlAnchor> links = htmlPage.getByXPath("//a[@class='thumbnail product-thumbnail']");

        String results = "";
        int limit = 5;
        String title;
        String price;
        String description;
        String resultUrl;
        for (int i = 0; i < links.size(); i++) {
            if (i == limit){
                break;
            }
            resultUrl = String.valueOf(links.get(i).click().getUrl());
                try{
                    HtmlPage htmlPage1 = webClient.getPage(resultUrl);
                    title = ((HtmlHeading1) htmlPage1.getByXPath(".//h1[@class='h1 productpage_title']").get(0)).getTextContent();
                    price = ((HtmlElement) htmlPage1.getByXPath("/html/body/main/section/div/div/div/section/div[1]/div[2]/div[2]/div[2]/form/div[2]/div[1]/div/span").get(0)).getTextContent();
                    description = ((HtmlDivision) htmlPage1.getByXPath(".//div[@class='product-description']").get(0)).getTextContent();
                    results += "Title :- " + title + '\n' +
                            "Price :- " + price + '\n' +
                            "Description :- " + description + '\n' +
                            "URL :- " + resultUrl +
                            "\n--------------------------------------------------------------------------------------------\n";
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        return results;
    }
}

package com.example.scraping.scrol;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.List;
public class CultureFactoryScroll {
    public String search(String searchWord) throws Exception{

        String url = "https://culturefactory.fr/recherche?controller=search&s=" + searchWord +"+vinyles";

        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlAnchor> links = htmlPage.getByXPath("//a[@class='thumbnail product-thumbnail']");

        String show = "";
        int limit = 5;
        for (int i = 0; i < links.size(); i++) {
            if (i == limit){
                break;
            }
            String resultUrl = String.valueOf(links.get(i).click().getUrl());
            try{
                HtmlPage htmlPage1 = webClient.getPage(resultUrl);
                String title = ((HtmlHeading1) htmlPage1.getByXPath(".//h1[@class='h1 namne_details']").get(0)).getTextContent();
                String price = ((HtmlElement) htmlPage1.getByXPath("/html/body/main/div[1]/div[2]/div/div/section/div[1]/div[2]/div[1]/div[1]/div/span").get(0)).getTextContent();
                String description = ((HtmlElement) htmlPage1.getByXPath("/html/body/main/div[1]/div[2]/div/div/section/div[1]/div[2]/div[2]/div/p").get(0)).getTextContent();
                show += "Title :- " + title + '\n' +
                        "Price :- " + price + '\n' +
                        "Description :- " + description + '\n' +
                        "URL :- " + resultUrl +
                        "\n--------------------------------------------------------------------------------------------\n";
                System.out.println(show);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        return show;
    }
}

package com.example.scraping.scrol;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.List;
public class Scrol1 {

    public String search(String searchWord, int min, int max) throws Exception{

        String url1

                = "https://www.leboncoin.fr/recherche?text=" + searchWord;
        String url = "https://www.leboncoin.fr/recherche?category=15&text=" + searchWord + "&price="+ min + "-" + max;

        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        List<HtmlAnchor> links = htmlPage.getByXPath("//a[@data-qa-id='aditem_container']");

        String show = "";
        int limit = 1;
        for (int i = 0; i < links.size(); i++) {
            if (i == limit){
                break;
            }
                String resultUrl = String.valueOf(links.get(i).click().getUrl());
                try{
                    HtmlPage htmlPage1 = webClient.getPage(resultUrl);
                    String title = ((HtmlHeading1) htmlPage1.getByXPath(".//h1[@data-qa-id='adview_title']").get(0)).getTextContent();
                    String price = ((HtmlDivision) htmlPage1.getByXPath(".//div[@data-qa-id='adview_price']").get(0)).getTextContent();
                    String description = ((HtmlDivision) htmlPage1.getByXPath(".//div[@data-qa-id='adview_description_container']").get(0)).getTextContent();
                    show += "Title :-" + title+ '\n' + "Price :- " + price + '\n' +"Description :- " + description + '\n' + "URL :- " + resultUrl +
                            "\n--------------------------------------------------------------------------------------------\n";
                    //System.out.println(resultUrl);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        System.out.println(url);
        return show;
    }
}

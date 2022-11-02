package com.example.scraping.scrol;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.List;
public class Scrol3 {
    public String search(String genre) throws Exception{

        String urlGenre = "https://www.fnac.com/SearchResult/ResultList.aspx?SCat=0&Search=vinyles+" + genre;

        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(urlGenre);

        List<HtmlAnchor> links = htmlPage.getByXPath("//a[@class='Article-title js-Search-hashLink']");

        String show = "";
        int limit = 1;
        for (int i = 0; i < links.size(); i++) {
            if (i == limit){
                break;
            }
                String resultUrl = String.valueOf(links.get(i).click().getUrl());
                try{
                    HtmlPage htmlPage1 = webClient.getPage(resultUrl);
                    String title = ((HtmlHeading1) htmlPage1.getByXPath(".//h1[@class='f-productHeader-Title']").get(0)).getTextContent();
                    //String price = ((HtmlDivision) htmlPage1.getByXPath(".//span[@class='f-faPriceBox__price userPrice js-ProductBuy-standardCheckable checked']").get(0)).getTextContent();
                    String priceByDiv = ((HtmlElement) htmlPage1.getByXPath("/html/body/div[3]/div/div[1]/div[2]/div[3]/div[1]/form/div/div[1]/div[1]/div[1]/span").get(0)).getTextContent();
                    //String description = ((HtmlDivision) htmlPage1.getByXPath(".//div[@class='product-description']").get(0)).getTextContent();
                    show += "Title :- " + title + '\n' +
                            "Price :- " + priceByDiv + '\n' +
                            "URL :- " + resultUrl +
                            "\n--------------------------------------------------------------------------------------------\n";
                    System.out.println(title);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        return show;
    }
}

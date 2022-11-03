package com.example.scraping.scrol;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.List;
public class FnacScroll {
    public String search(String searchWord) throws Exception{

        String urlGenre = "https://www.fnac.com/SearchResult/ResultList.aspx?SCat=3%211&Search=" + searchWord;

        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(urlGenre);

        List<HtmlAnchor> links = htmlPage.getByXPath("//a[@class='Article-title js-Search-hashLink']");

        String results = "";
        int limit = 1;
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
                    title = ((HtmlHeading1) htmlPage1.getByXPath("//h1[@class='f-productHeader-Title']").get(0)).getTextContent();
                    price = ((HtmlElement) htmlPage1.getByXPath("//span[@class='f-faPriceBox__price userPrice js-ProductBuy-standardCheckable']").get(0)).getTextContent();
                    description = ((HtmlDivision) htmlPage1.getByXPath("//div[@class='f-productDesc__raw']").get(0)).getTextContent();
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

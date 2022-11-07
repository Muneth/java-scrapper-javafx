package com.example.scraping.scrol;
import com.example.scraping.VinyleController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.ArrayList;
import java.util.List;
public class FnacScroll {
    public ArrayList<Scroll> search(String searchWord) throws Exception{

        String urlGenre = "https://www.fnac.com/SearchResult/ResultList.aspx?SCat=3%211&Search=" + searchWord;

        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(urlGenre);

        List<HtmlAnchor> links = htmlPage.getByXPath("//a[@class='Article-title js-Search-hashLink']");

        int limit = 1;
        String title;
        String price;
        String description;
        String genre = "";
        String resultUrl;
        String date;
        String imageUrl;
        ArrayList<Scroll> scrolls = new ArrayList<>();
        for (int i = 0; i < links.size(); i++) {
            if (i == limit){
                break;
            }
                resultUrl = String.valueOf(links.get(i).click().getUrl());
                try{
                    HtmlPage htmlPage1 = webClient.getPage(resultUrl);
                    title = ((HtmlHeading1) htmlPage1.getByXPath("//h1[@class='f-productHeader-Title']").get(0)).getTextContent();
                   // genre = ((HtmlElement) htmlPage1.getByXPath(".//p[@class='ref-genre-cat show-list-only']").get(0)).getTextContent();
                    price = ((HtmlElement) htmlPage1.getByXPath("/html/body/div[2]/div/div[1]/div[2]/div[3]/div[1]/form/div/div[1]/label[1]/div[2]/span").get(0)).getTextContent();
                    date = ((HtmlElement) htmlPage1.getByXPath("/html/body/div[2]/div/div[1]/div[2]/div[2]/div/div[2]/dl[2]/dd/p").get(0)).getTextContent();
                    description = ((HtmlDivision) htmlPage1.getByXPath("//div[@class='f-productDesc__raw']").get(0)).getTextContent();
                    imageUrl = ((HtmlImage)htmlPage1.getFirstByXPath(".//img[@class='f-productMedias__viewItem--main']")).getSrcAttribute();
                    scrolls.add(new Scroll(title,genre,price,date, VinyleController.checkIfNull(description),VinyleController.checkIfNull(imageUrl)));
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        return scrolls;
    }
}
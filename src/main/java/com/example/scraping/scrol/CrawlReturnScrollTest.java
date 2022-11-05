//package com.example.scraping.scrol;
//
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
//import com.gargoylesoftware.htmlunit.html.HtmlDivision;
//import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CrawlReturnScrollTest {
//    private final WebClient client;
//
//    public CrawlReturnScrollTest() {
//        this.client = new WebClient() {{
//            getOptions().setUseInsecureSSL(false);
//            getOptions().setCssEnabled(false);
//            getOptions().setJavaScriptEnabled(false);
//        }};
//    }
//
//    public List<Scroll> search(final String query, final int maxResults) throws IOException {
//        final String url1 = String.format("https://www.leboncoin.fr/recherche?text=%s", query.replace(" ", "%20"));
//        String url = "https://mesvinyles.fr/fr/recherche?controller=search&s=" + query;
//
//        return queryResults(url, maxResults);
//    }
//
//    public List<Scroll> queryResults(final String url, final int maxResults) throws IOException {
//        final HtmlPage page = client.getPage(url);
//        //final List<HtmlAnchor> entries = page.getByXPath("//a[@data-qa-id='aditem_container']");
//        final List<HtmlAnchor> entries = page.getByXPath("//a[@class='thumbnail product-thumbnail']");
//
//        final ArrayList<Scroll> results = new ArrayList<>();
//
//        int count = 0;
//        for (final HtmlAnchor entry : entries) {
//            if (count++ >= maxResults) break;
//
//            results.add(scrapResult(entry));
//        }
//        return results;
//    }
//
//    private Scroll scrapResult(final HtmlAnchor anchor) throws IOException {
//        //final String url = String.format("https://www.leboncoin.fr%s", anchor.getHrefAttribute());
//        String url = String.valueOf(anchor.click().getUrl());
//        final HtmlPage page = client.getPage(url);
//
//        final HtmlHeading1 title = page.getFirstByXPath(".//h1[@class='h1 productpage_title']");
//        final HtmlDivision price = page.getFirstByXPath("/html/body/main/section/div/div/div/section/div[1]/div[2]/div[2]/div[2]/form/div[2]/div[1]/div/span");
//        final HtmlDivision description = page.getFirstByXPath(".//div[@class='product-description']");
//
//        return new Scroll(
//                title.getTextContent(),
//                description == null ? "No description" : description.getTextContent(),
//                url, url,
//                price == null ? "No price" : price
//                        .getTextContent()
//                        .replaceAll("€.*", "€"));
//    }
//}

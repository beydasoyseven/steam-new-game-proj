import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class newGame {
    public static <Char> void main(String[] args) throws IOException {

        Document doc = Jsoup.connect("https://store.steampowered.com/search/?filter=popularnew&sort_by=Released_DESC&os=win").timeout(6000).get();
        Elements body = doc.select(".search_page");

        Map< String, String> hashMapData2 = new HashMap<>();

        for (Element e : body.select(".tab_filter_control_row ")) {
            String dataValue = e.select(".tab_filter_control_row").attr("data-value");
            String dataLoc = e.select(".tab_filter_control_row").attr("data-loc");

            hashMapData2.put(dataValue, dataLoc);
        }

        for (Element e : body.select(".search_result_row")) {
            String url = e.attr("href");
            String title = e.select(".title").text();
            String date = e.select(".search_released").text();
            String rating = e.select(".search_review_summary").attr("data-tooltip-html");
            String price = e.select(".search_price").text();
            String[] arrayPrice = price.split("TL");
            String oldPrice = e.select("strike").text();
            String data = e.attr("data-ds-tagids");

            System.out.println("URL:" + url);
            System.out.println("Game Name:" + title);
            System.out.println("Game Date:" + date);
            System.out.println("Rating And Raview:" + rating.replace("<br>", " "));
            if (arrayPrice.length > 1) {
                System.out.println("newPrice:" + arrayPrice[1]);
            } else {
                System.out.println("newPrice:" + price);
            }
            System.out.println("Old Price : " + oldPrice);
            data = data.replace("[","").replace("]","");
            String[] stringArray = data.split(",");
            String[] tagArray = new String[stringArray.length];
            for(int i =0 ;i< stringArray.length;i++){
                tagArray[i] = hashMapData2.get(stringArray[i]);
            }
            System.out.println("Tags:" + Arrays.toString(tagArray));
        }
    }
}
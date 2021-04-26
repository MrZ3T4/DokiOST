package dev.mrz3t4.dokiost;

import android.app.Activity;
import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GetSoundtracks {

    private Context context;

    public GetSoundtracks(Context context) {
        this.context = context;
    }

    public void getDataFromQuery(String link){

        new Thread(() -> {
            try {

                Document doc = Jsoup.connect(link).userAgent("Mozilla").maxBodySize(0).get();
                String html = doc.toString();

                Document document = Jsoup.parse(html);

                System.out.println(doc);

                Elements elements = doc.select("section[class=anx-section]");

                for (Element e: elements){

                    String title = e.select("span[class=cd-song]").text();
                    String titlee = e.select("img").attr("alt");

                    String type = e.select("div[class=anx-top]").text();

                    if (type.contains("Opening")){

                        System.out.println("Tipo: " + type);
                        System.out.println("TITULO: " + title);
                    } else if (type.contains("Ending")){

                        System.out.println("Type: " + type);
                        System.out.println("TITULO: " + title);

                    }



                }


            } catch (Exception e){
                e.printStackTrace();
            }

            ((Activity)context).runOnUiThread(()-> {
            });

        }).start();

    }


}

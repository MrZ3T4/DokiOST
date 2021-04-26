package dev.mrz3t4.dokiost;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Search {

    private String path = "https://animex-ost.blogspot.com/search?q=";

    private RecyclerView recyclerView;
    private TextView textView;

    private NestedScrollView nestedScrollView;

    private Context context;
    private ArrayList<Result> results;

    public Search(RecyclerView recyclerView, TextView textView, NestedScrollView nestedScrollView, Context context) {
    this.context = context;
    this.recyclerView = recyclerView;
    this.textView = textView;
    this.nestedScrollView = nestedScrollView;
    }


    public void getDataFromQuery(String query){

       results = new ArrayList<>();

        String final_query;

        if (query.contains(" ")){
            final_query = query.replaceAll(" ", "+");
        } else {
            final_query = query;
        }


        String final_path = path.concat(final_query);

        System.out.println(final_path);


        new Thread(() -> {
            try {

                Document doc = Jsoup.connect(final_path).userAgent("Mozilla").maxBodySize(0).get();
                String html = doc.toString();

                Document document = Jsoup.parse(html);

                System.out.println(document);

                Elements elements = document.select("div[class=card col-12 card-search]");

                for (Element e: elements){

                    Result result = new Result();

                    String titlex = e.select("h2[class=card__title skin-font]").text();
                    String description = e.select("p[class=card__descripcion]").text();
                    String img = e.select("img").attr("src");
                    String url = e.select("a[class=skin-color-hover]").attr("href");

                    String title = titlex.replace(": OST", "");

                    result.setTitle(title);
                    result.setDescription(description);
                    result.setImg(img);
                    result.setUrl(url);

                    results.add(result);

                    System.out.println(title);
                    System.out.println(description);
                    System.out.println(img);
                    System.out.println(url);

                }


            } catch (Exception e){
                e.printStackTrace();
            }

            ((Activity)context).runOnUiThread(()-> {

                if (results.size() == 0){
                    textView.setText("Sin resultados");
                }

                // Do Next...
               nestedScrollView.animate().alpha(1f).setDuration(300).start();
                setRecyclerView(false);
            });

        }).start();

    }

    public void setRecyclerView(boolean clear) {

        if (clear){
            results.clear();
        }

        recyclerView.setItemViewCacheSize(30);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setHasFixedSize(true);

        ResutAdapter resutAdapter = new ResutAdapter(results, context);
        recyclerView.setAdapter(resutAdapter);

        resutAdapter.notifyDataSetChanged();
    }

}

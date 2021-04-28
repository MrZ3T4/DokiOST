package dev.mrz3t4.dokiost;

import android.app.Activity;
import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetSoundtracks {

    private Context context;
    private ArrayList<Song> songArrayList = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> artist = new ArrayList<>();
    private ArrayList<String> urlist = new ArrayList<>();
    private ArrayList<String> urls = new ArrayList<>();

    public GetSoundtracks(Context context) {
        this.context = context;
    }

    public void getDataFromQuery(String link){

        new Thread(() -> {
            try {

                Document doc = Jsoup.connect(link).userAgent("Mozilla").maxBodySize(0).get();
                String html = doc.toString();

                Document document = Jsoup.parse(html);

                //System.out.println(doc);

                Elements elements;


                if (html.contains("div class=\"ost\"")) {
                    elements = doc.select("div[class=ost]");

                    // <<---- Link ---->>

                    String textUrl = elements.toString();

                    String regex = "\\(?\\b(https?://|www[.]|ftp://)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";

                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(textUrl);

                    while(m.find()) {
                        String urlStr = m.group();
                        System.out.println("Url: " + urlStr);
                        urlist.add(urlStr);

                    }

                    for (int pos = 1; pos < urlist.size(); pos++ ){
                        String url = urlist.get(pos);
                        if (!urlist.get(pos-1).isEmpty()){
                            if (url.equalsIgnoreCase(urlist.get(pos-1))){
                                System.out.println(url);
                                urls.add(url);
                            }
                        }
                    }

                    for (Element e: elements){

                        String text = e.select("span").text();
                        String textSong;
                        String textArtist;

                        if (link.contains("bleach")){
                            textArtist = text.replace("Daidai\"", "\"Daidai\"").replaceAll("\"([^\"]*)\"", "");
                            textSong = text.replace("Daidai\"", "\"Daidai\"");
                            System.out.println("xd ---> " +textArtist);
                        } else {
                            textArtist = text.replaceAll("\"([^\"]*)\"", "");
                            textSong = text;
                        }

                        // <<---- Artist ---->>

                        ArrayList<String> myList = new ArrayList<>(Arrays.asList(textArtist.split("by" )));

                        for (int i = 0; myList.size()>i; i++){
                            String filter = myList.get(i).replaceFirst(" ", "");
                            if (!filter.equalsIgnoreCase("by") && !filter.isEmpty()) {
                                System.out.println("Artist: " + filter);
                                artist.add(filter);
                            }

                        }

                        // <<---- Title ---->>

                        Pattern pa = Pattern.compile("\"([^\"]*)\"");
                        Matcher ma = pa.matcher(textSong);

                        while (ma.find()) {
                            System.out.println("Title: " + ma.group(1));
                            titles.add(ma.group(1));
                        }

                    }


                    // <<---- Create Final ArrayList ---->>

                    for (int i  = 0; i < titles.size(); i++){
                        Song song = new Song();
                        song.setTitle(titles.get(i));
                        song.setArtist(artist.get(i));
                        song.setUrl(urls.get(i));
                        songArrayList.add(song);
                    }
                    for (int i  = 0; songArrayList.size() > i; i++){
                        System.out.println("Size: " + songArrayList.size() + " Song --> " + songArrayList.get(i).getTitle() + " by " + songArrayList.get(i).getArtist() + " --> " + songArrayList.get(i).getUrl());
                    }

                System.out.println("XD OST");
                } else {
                    elements = doc.select("section[class=anx-section]");
                    System.out.println("XD NOST");
                    String path = "div[class=anx-song flex wrap]";

                    if (elements.toString().contains("anx-song anx-audio flex wrap"))  path = "div[class=anx-song anx-audio flex wrap]";
                    Elements elements1 = doc.select(path);

                    for (Element e: elements){

                        String type = e.select("div[class=anx-top]").text();

                        if (type.equalsIgnoreCase("Opening")){
                            for (Element test : elements1) {
                                String title = test.select("span[class=cd-song]").text();
                                String artist = test.select("span[class=cd-artist]").text();
                                String download = test.select("a[class=color anx-link]").attr("href");

                                if (!title.isEmpty()) System.out.println("TITULO: " + title);
                                if (!artist.isEmpty()) System.out.println("Artista: " + artist);
                                if (!download.isEmpty()) System.out.println("Link: " + download);
                            }

                        }

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

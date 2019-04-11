package com.example.myapplication2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private static Context mContext;

    List<Noticia> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        new Content().execute();

        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(false);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
    }
/*
    public static void goToUrl(View view){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.publico.es/ciencias"));
        mContext.startActivity(browserIntent);
    }
*/
    public static void goToUrl(View view,String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
        mContext.startActivity(intent);
    }
    private class Content extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... voids) {
            String urlPublico = "https://www.publico.es/ciencias";
            try {
                Connection jsoupconnect = Jsoup.connect(urlPublico);
                Document document = jsoupconnect.get();
                Elements articulos = document.select("div.listing-item");
                Bitmap bitmap;

                for (Element elem : articulos) {
                    String titular = elem.getElementsByClass("page-link").text();
                    String enlace = "https://www.publico.es" + (elem.getElementsByAttribute("href").attr("href"));
                    items.add(new Noticia(titular, enlace));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            adapter = new NoticiaAdapter(items);
            recycler.setAdapter(adapter);

        }
    }
}

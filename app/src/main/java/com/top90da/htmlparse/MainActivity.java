package com.top90da.htmlparse;

import android.app.ProgressDialog;
import android.drm.ProcessedData;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    public ArrayList liste=new ArrayList();
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;
    private static String URL="https://www.ntvspor.net/futbol";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv=(ListView) findViewById(R.id.listele);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);


        Button btn = (Button) findViewById(R.id.vericek);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new VeriGetir().execute();

            }
        });
    }

    private class VeriGetir extends AsyncTask<Void , Void , Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Veriler Çekiliyor");
            progressDialog.setMessage("Lütfen Bekleyiniz...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc= (Document)Jsoup.connect(URL).timeout(30*1000).get();

                Elements haber= doc.select("figcaption");

                for (int i=0;i<haber.size();i++){

                    liste.add(haber.get(i).text());

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lv.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }
}
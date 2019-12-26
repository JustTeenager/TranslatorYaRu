package com.example.translator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;
    EditText editText;
    TextView textview;
    String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        editText = findViewById(R.id.edit);
        textview = findViewById(R.id.TextView);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        MyDownload dw = new MyDownload();
        word = String.valueOf(editText.getText());
        dw.execute(word);


    }

    //////////////////////////////////////////////////////////////////////
 private class MyDownload extends AsyncTask<String, Void, String> {
        HttpURLConnection httpurl;

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20191226T152414Z.d50095db5cd0cb91.28bad095f8428943cf0a8564daaff716aac47144&text="+strings[0]+"&lang=en-ru");
                httpurl = (HttpURLConnection) url.openConnection();
                httpurl.setRequestMethod("GET");
                httpurl.connect();

                InputStream input = httpurl.getInputStream();
                Scanner scan = new Scanner(input);
                StringBuilder strbuild = new StringBuilder();
                while (scan.hasNextLine()) {
                    strbuild.append(scan.nextLine());
                }
                return strbuild.toString();
            } catch (java.io.IOException e) {
                Log.e("UP TO LATE", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson g=new Gson();
            Translator trans=g.fromJson(s,Translator.class);
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            JsonParser jp = new JsonParser();
//            JsonElement json = jp.parse(s);
//            String prettyJsonString = gson.toJson(json);
            String tr=String.valueOf(trans.getText());
            textview.setText(tr);
        }
    }
}

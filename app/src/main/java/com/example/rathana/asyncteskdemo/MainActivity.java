package com.example.rathana.asyncteskdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rathana.asyncteskdemo.adapter.ArticleAdapter;
import com.example.rathana.asyncteskdemo.entity.Article;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArticleAdapter adapter;
    List<Article> articleList=new ArrayList<>();
    ProgressBar progressBar;
    AsyncTask asyncTask;
    TextView footer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }
    void setupUI(){
        progressBar=findViewById(R.id.progressBar);
        footer=findViewById(R.id.footer);
        recyclerView=findViewById(R.id.rvArticle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ArticleAdapter(this,articleList);
        recyclerView.setAdapter(adapter);

        //setdata
        new ArticleAsyncTask().execute();
        footer.setText("@copy 2018.");
    }
    public List<Article> getArticles(){
        List<Article> articles=new ArrayList<>();
        for(int i=0;i<50;i++){
            articles.add(new Article("Smartwatch ស៊េរីថ្មីរបស់\u200B នឹងអាចត្រាំ\u200Bទឹក\u200Bបានដល់ ៥០ ម៉ែត្រ\u200B",
                    "Fitbit ជា\u200Bក្រុមហ៊ុន\u200Bមួយ\u200Bដែរ\u200B ក្នុង\u200Bការ\u200Bផលិត\u200Bនាឡិកា\u200Bវ័យ\u200Bឆ្លាត\u200B (Smartwatch) រួមមាន\u200B Fitbit Blaze និង\u200B Ionic។",
                    0));
        }

        return articles;
    }

    class ArticleAsyncTask extends AsyncTask<Void,Article,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }
        @Override
        protected String doInBackground(Void... voids) {
            for(Article article: getArticles()){
                publishProgress(article);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "get article successful!";
        }
        @Override
        protected void onProgressUpdate(Article... values) {
            super.onProgressUpdate(values);
            adapter.setArticle(values[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            showMessage(s);
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }
    void showMessage(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}

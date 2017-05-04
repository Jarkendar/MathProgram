package com.example.jarek.mathprogram;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LauncherActivity extends AppCompatActivity {

    private static final String PREFERENCE_NAME = "mypreference";//zmienna nazwy pliku

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        TextView tAuthor = (TextView)findViewById(R.id.text_author) ;

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME,MODE_PRIVATE);
        String author = sharedPreferences.getString("text_author_full","by Jarkendar");

        tAuthor.setText(author);

        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent("com.example.jarek.mathprogram.MainActivity");
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

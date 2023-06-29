package com.example.dadjokedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MainActivity extends AppCompatActivity {

    private TextView bubble;
    Button chin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bubble = (TextView) findViewById(R.id.textView);
        chin = (Button) findViewById(R.id.button);

        chin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL url = null;
                try {
                    url = new URL("https://api.api-ninjas.com/v1/dadjokes?limit=3");
                } catch (MalformedURLException e) {
                    System.out.println("Something went wrong.");
                }

                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    System.out.println("Something went wrong.");
                }

                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("accept", "application/json");
                connection.setRequestProperty("X-Api-Key", "Dnm6aN5BuIPfxW2Wc7yQDw==1yl7KGhXEngud4lH");
                try {
                    connection.setRequestMethod("GET");
                } catch (ProtocolException e) {
                    throw new RuntimeException(e);
                }

                // error occurs here
                InputStream responseStream = null;
                try {
                    responseStream = connection.getInputStream();
                    responseStream.close();
                } catch (IOException e) {
                    System.out.println("Something went wrong.");
                }

                ObjectMapper mapper = new ObjectMapper();

                JsonNode root = null;
                try {
                    root = mapper.readTree(responseStream);
                } catch (IOException e) {
                    System.out.println("Something went wrong.");
                }

                bubble.setText(root.path("fact").asText());
            }
    });
}}
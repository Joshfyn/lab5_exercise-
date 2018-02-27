package com.example.joshuaadeegbe.lab5;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class MainActivity extends AppCompatActivity {
    ListView listView;
    public TwitterClass twitterClass;


    Intent twitterUtils;
    private int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.my_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String item = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position + ": " + item, Toast.LENGTH_LONG)
                        .show();
            }
        });

        twitterClass = new TwitterClass("twitterdev", getApplicationContext());
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.INTERNET}, REQUEST_CODE);

        } else {
            //use the getusertimeline
            twitterClass.execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
            twitterClass.execute();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public class TwitterClass extends AsyncTask<Void, Void, List<String>> {
        private String username;

        private Context applicationContext;

        TwitterClass(String username, Context context) {
            this.username = username;

            this.applicationContext = context;
        }


        @Override
        protected List<String> doInBackground(Void... strings) {
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setOAuthConsumerKey(getString(R.string.Twitter_Key))
                    .setOAuthConsumerSecret(getString(R.string.Twitter_Secret))
                    .setOAuthAccessToken(getString(R.string.Oauth_Key))
                    .setOAuthAccessTokenSecret(getString(R.string.Oauth_Secret));

            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            List<String> Twitterlist = new ArrayList<>();
            try {
                List<twitter4j.Status> status = twitter.getUserTimeline(this.username);
                for (twitter4j.Status st : status) {
                    Twitterlist.add(st.getText());
                }

            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return Twitterlist;
        }

        @Override
        protected void onPostExecute(List<String> results) {
            super.onPostExecute(results);
            //String[] items = (String[]) results.toArray();
            ListAdapter listAdapter = new ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, results);     //this object converts an array to list items


            listView.setAdapter(listAdapter); //this coverts the array to display in the list
        }


    }
}


package com.aliindustries.newszoid;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    ListView lvRss;
    ArrayList<String> titles;
    ArrayList<String> links;
    ArrayList<String> img;
    ArrayList<String> pubdate;
    private AppBarConfiguration mAppBarConfiguration;
    int ARTICLE_LIMIT = 50;
    String ARTICLE_NOIMG = "https://drive.google.com/file/d/1qDFoPqZYBR817IuvyQeiqUu_yxKiABfP/view?usp=sharing";
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.ic_launcher_background));

        }
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.INTERNET}, 1);

        lvRss = (ListView) findViewById(R.id.listview);

        titles = new ArrayList<String>();
        links = new ArrayList<String>();
        img = new ArrayList<>();
        pubdate = new ArrayList<>();
        linearLayout = findViewById(R.id.lin2);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.nav_share: {
                        break;
                    }
                    case R.id.nyt: {
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                .getColor(R.color.black)));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
                            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));

                        }
                        getSupportActionBar().setTitle("The New York Times");
                        drawer.closeDrawers();
                        if(isConnectedToInternet()) {
                            new nyt().execute();
                        }
                        else {
                            titles = new ArrayList<String>();
                            links = new ArrayList<String>();
                            img = new ArrayList<>();
                            pubdate = new ArrayList<>();
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Internet connection lost!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            CustomAdapter adapter = new CustomAdapter(titles,pubdate,img,MainActivity.this);
                            lvRss.setAdapter(adapter);
                            if(titles.size() > 0 ) {
                                linearLayout.setVisibility(View.GONE);
                                lvRss.setVisibility(View.VISIBLE);
                            }
                            else {
                                linearLayout.setVisibility(View.VISIBLE);
                                lvRss.setVisibility(View.GONE);
                            }
                        }
                        break;
                    }
                    case R.id.aljazeera: {
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                .getColor(R.color.orange)));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.orange));
                            getWindow().setNavigationBarColor(getResources().getColor(R.color.orange));

                        }
                        getSupportActionBar().setTitle("Al Jazeera");
                        drawer.closeDrawers();
                        if(isConnectedToInternet()) {
                            new aljazeera().execute();
                        }
                        else {
                            titles = new ArrayList<String>();
                            links = new ArrayList<String>();
                            img = new ArrayList<>();
                            pubdate = new ArrayList<>();
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Internet connection lost!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            CustomAdapter2 adapter = new CustomAdapter2(titles,pubdate,MainActivity.this);
                            lvRss.setAdapter(adapter);
                            if(titles.size() > 0 ) {
                                linearLayout.setVisibility(View.GONE);
                                lvRss.setVisibility(View.VISIBLE);
                            }
                            else {
                                linearLayout.setVisibility(View.VISIBLE);
                                lvRss.setVisibility(View.GONE);
                            }
                        }
                        break;
                    }
                    case R.id.guardian: {
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                .getColor(R.color.guardiancolor)));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.guardiancolor));
                            getWindow().setNavigationBarColor(getResources().getColor(R.color.guardiancolor));

                        }
                        getSupportActionBar().setTitle("The Guardian");
                        drawer.closeDrawers();
                        if(isConnectedToInternet()) {
                            new guardian().execute();
                        }
                        else {
                            titles = new ArrayList<String>();
                            links = new ArrayList<String>();
                            img = new ArrayList<>();
                            pubdate = new ArrayList<>();
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Internet connection lost!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            CustomAdapter adapter = new CustomAdapter(titles,pubdate,img,MainActivity.this);
                            lvRss.setAdapter(adapter);
                            if(titles.size() > 0 ) {
                                linearLayout.setVisibility(View.GONE);
                                lvRss.setVisibility(View.VISIBLE);
                            }
                            else {
                                linearLayout.setVisibility(View.VISIBLE);
                                lvRss.setVisibility(View.GONE);
                            }
                        }
                        break;
                    }
                    case R.id.buzz_feed: {
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                .getColor(R.color.buzzfeedcolor)));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.buzzfeedcolor));
                            getWindow().setNavigationBarColor(getResources().getColor(R.color.buzzfeedcolor));

                        }
                        getSupportActionBar().setTitle("BuzzFeed");
                        drawer.closeDrawers();
                        if(isConnectedToInternet()) {
                            new buzzfeed().execute();
                        }
                            else {
                            titles = new ArrayList<String>();
                            links = new ArrayList<String>();
                            img = new ArrayList<>();
                            pubdate = new ArrayList<>();
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Internet connection lost!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            CustomAdapter adapter = new CustomAdapter(titles,pubdate,img,MainActivity.this);
                            lvRss.setAdapter(adapter);
                            if(titles.size() > 0 ) {
                                linearLayout.setVisibility(View.GONE);
                                lvRss.setVisibility(View.VISIBLE);
                            }
                            else {
                                linearLayout.setVisibility(View.VISIBLE);
                                lvRss.setVisibility(View.GONE);
                            }
                        }

                        break;
                    }
                    case R.id.yahoo: {
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                .getColor(R.color.yahoocolor)));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.yahoocolor));
                            getWindow().setNavigationBarColor(getResources().getColor(R.color.yahoocolor));

                        }
                        getSupportActionBar().setTitle("Yahoo News");
                        drawer.closeDrawers();
                        if(isConnectedToInternet()) {
                            new yahoo().execute();
                        }
                        else {
                            titles = new ArrayList<String>();
                            links = new ArrayList<String>();
                            img = new ArrayList<>();
                            pubdate = new ArrayList<>();
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Internet connection lost!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            CustomAdapter adapter = new CustomAdapter(titles,pubdate,img,MainActivity.this);
                            lvRss.setAdapter(adapter);
                            if(titles.size() > 0 ) {
                                linearLayout.setVisibility(View.GONE);
                                lvRss.setVisibility(View.VISIBLE);
                            }
                            else {
                                linearLayout.setVisibility(View.VISIBLE);
                                lvRss.setVisibility(View.GONE);
                            }
                        }
                        break;
                    }
                    case R.id.independent: {
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                                .getColor(R.color.independentcolor)));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(getResources().getColor(R.color.independentcolor));
                            getWindow().setNavigationBarColor(getResources().getColor(R.color.independentcolor));

                        }
                        getSupportActionBar().setTitle("The Independent");
                        drawer.closeDrawers();
                        if(isConnectedToInternet()) {
                            new independent().execute();
                        }
                        else {
                            titles = new ArrayList<String>();
                            links = new ArrayList<String>();
                            img = new ArrayList<>();
                            pubdate = new ArrayList<>();
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Internet connection lost!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            CustomAdapter adapter = new CustomAdapter(titles,pubdate,img,MainActivity.this);
                            lvRss.setAdapter(adapter);
                            if(titles.size() > 0 ) {
                                linearLayout.setVisibility(View.GONE);
                                lvRss.setVisibility(View.VISIBLE);
                            }
                            else {
                                linearLayout.setVisibility(View.VISIBLE);
                                lvRss.setVisibility(View.GONE);
                            }
                        }
                        break;
                    }
                }
                return true;

            }
            });


        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Uri uri = Uri.parse(links.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        if(titles.size() <= 0 ) {
            linearLayout.setVisibility(View.VISIBLE);
            lvRss.setVisibility(View.GONE);
        }
    }

    public InputStream getInputStream(URL url)
    {
        try
        {
            //openConnection() returns instance that represents a connection to the remote object referred to by the URL
            //getInputStream() returns a stream that reads from the open connection
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                finish();
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

        public class nyt extends AsyncTask<Integer, Void, String>
    {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            titles = new ArrayList<String>();
            links = new ArrayList<String>();
            img = new ArrayList<>();
            pubdate = new ArrayList<>();
            progressDialog.setMessage("Loading.... please wait");
            progressDialog.show();

        }


        @Override
        protected String doInBackground(Integer... integers) {
            try{
                URL url = new URL("https://rss.nytimes.com/services/xml/rss/nyt/MostViewed.xml");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem)
                            {
                                if(titles.size() < ARTICLE_LIMIT) {
                                    titles.add(xpp.nextText());
                                    img.add(ARTICLE_NOIMG);
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("link"))
                        {
                            if (insideItem)
                            {
                                if(links.size() < ARTICLE_LIMIT) {

                                    links.add(xpp.nextText());
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("pubDate"))
                        {
                            if (insideItem)
                            {
                                if(pubdate.size() < ARTICLE_LIMIT) {

                                    pubdate.add(xpp.nextText());
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("media:content"))
                        {
                            if (insideItem) {
                                if (img.size() <= ARTICLE_LIMIT) {
                                    String s = xpp.getAttributeValue(null, "url");
                                    if (img.size() > 0) {
                                        img.set(img.size() - 1, s);
                                    }
                                }
                            }
                        }

                    }

                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }
                    eventType = xpp.next();
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(titles.size() > 0 ) {
                linearLayout.setVisibility(View.GONE);
                lvRss.setVisibility(View.VISIBLE);
            }
            else {
                linearLayout.setVisibility(View.VISIBLE);
                lvRss.setVisibility(View.GONE);
            }
            CustomAdapter adapter = new CustomAdapter(titles,pubdate,img,MainActivity.this);
            lvRss.setAdapter(adapter);
            progressDialog.dismiss();


        }
    }

    public class aljazeera extends AsyncTask<Integer, Void, String>
    {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            titles = new ArrayList<String>();
            links = new ArrayList<String>();
            img = new ArrayList<>();
            pubdate = new ArrayList<>();
            progressDialog.setMessage("Loading.... please wait");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(Integer... integers) {
            try{
                URL url = new URL("https://www.aljazeera.com/xml/rss/all.xml");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem)
                            {
                                if(titles.size() < ARTICLE_LIMIT) {
                                    titles.add(xpp.nextText());
                                    img.add("hee");
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("link"))
                        {
                            if (insideItem)
                            {
                                if(links.size() < ARTICLE_LIMIT) {

                                    links.add(xpp.nextText());
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("pubDate"))
                        {
                            if (insideItem)
                            {
                                if(pubdate.size() < ARTICLE_LIMIT) {

                                    pubdate.add(xpp.nextText());
                                }


                            }
                        }

                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }
                    eventType = xpp.next();
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(titles.size() > 0 ) {
                linearLayout.setVisibility(View.GONE);
                lvRss.setVisibility(View.VISIBLE);
            }
            else {
                linearLayout.setVisibility(View.VISIBLE);
                lvRss.setVisibility(View.GONE);
            }
            CustomAdapter2 adapter = new CustomAdapter2(titles,pubdate,MainActivity.this);
            lvRss.setAdapter(adapter);

            progressDialog.dismiss();

        }
    }



    public class guardian extends AsyncTask<Integer, Void, String>
    {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            titles = new ArrayList<String>();
            links = new ArrayList<String>();
            img = new ArrayList<>();
            pubdate = new ArrayList<>();
            progressDialog.setMessage("Loading.... please wait");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(Integer... integers) {
            try{
                URL url = new URL("https://www.theguardian.com/world/rss");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem)
                            {
                                if(titles.size() < ARTICLE_LIMIT) {
                                    titles.add(xpp.nextText());
                                    img.add(ARTICLE_NOIMG);
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("link"))
                        {
                            if (insideItem)
                            {
                                if(links.size() < ARTICLE_LIMIT) {

                                    links.add(xpp.nextText());
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("pubDate"))
                        {
                            if (insideItem)
                            {
                                if(pubdate.size() < ARTICLE_LIMIT) {

                                    pubdate.add(xpp.nextText());
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("media:content"))
                        {
                            if (insideItem) {
                                if (img.size() <= ARTICLE_LIMIT) {

                                    String s = xpp.getAttributeValue(null, "url");
                                    System.out.println("the url link is: " + s);
                                    if (img.size() > 0) {
                                        img.set(img.size() - 1, s);
                                    }
                                }
                            }
                        }

                    }

                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }
                    eventType = xpp.next();
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(titles.size() > 0 ) {
                linearLayout.setVisibility(View.GONE);
                lvRss.setVisibility(View.VISIBLE);
            }
            else {
                linearLayout.setVisibility(View.VISIBLE);
                lvRss.setVisibility(View.GONE);
            }
            CustomAdapter adapter = new CustomAdapter(titles,pubdate,img,MainActivity.this);
            lvRss.setAdapter(adapter);

            progressDialog.dismiss();

        }
    }





    public class buzzfeed extends AsyncTask<Integer, Void, String>
    {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            titles = new ArrayList<String>();
            links = new ArrayList<String>();
            img = new ArrayList<>();
            pubdate = new ArrayList<>();
            progressDialog.setMessage("Loading.... please wait");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(Integer... integers) {
            try{
                URL url = new URL("https://www.buzzfeed.com/world.xml");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem) {
                                if (titles.size() < ARTICLE_LIMIT) {

                                    titles.add(xpp.nextText());
                                    img.add(ARTICLE_NOIMG);

                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("link"))
                        {
                            if (insideItem)
                            {
                                if (links.size() < ARTICLE_LIMIT) {

                                    links.add(xpp.nextText());
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("pubDate"))
                        {
                            if (insideItem)
                            {
                                if (pubdate.size() < ARTICLE_LIMIT) {

                                    pubdate.add(xpp.nextText());
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("description"))
                        {
                            if (insideItem) {
                                String s = xpp.nextText();

                                int j = s.indexOf("src");
                                if (j >= 0) {
                                    if (img.size() <= ARTICLE_LIMIT) {
                                        s = s.substring(j);
                                        int j2 = s.indexOf(" />");
                                        s = s.substring(4, j2);
                                        s = s.replace("\"", "");
                                        System.out.println("the url link is: " + s);
                                        if (img.size() > 0) {
                                            img.set(img.size() - 1, s);
                                        }
                                    }
                                }
                            }
                        }

                    }

                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }
                    eventType = xpp.next();
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(titles.size() > 0 ) {
                linearLayout.setVisibility(View.GONE);
                lvRss.setVisibility(View.VISIBLE);
            }
            else {
                linearLayout.setVisibility(View.VISIBLE);
                lvRss.setVisibility(View.GONE);
            }
            CustomAdapter adapter = new CustomAdapter(titles,pubdate,img,MainActivity.this);
            lvRss.setAdapter(adapter);

            progressDialog.dismiss();


        }
    }


    public class yahoo extends AsyncTask<Integer, Void, String>
    {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            titles = new ArrayList<String>();
            links = new ArrayList<String>();
            img = new ArrayList<>();
            pubdate = new ArrayList<>();
            progressDialog.setMessage("Loading.... please wait");
            progressDialog.show();

        }


        @Override
        protected String doInBackground(Integer... integers) {
            try{
                URL url = new URL("https://news.yahoo.com/rss/");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem)
                            {
                                if (titles.size() < ARTICLE_LIMIT) {

                                    String s = xpp.nextText();
                                    s = s.replace("&#39;", "'");
                                    titles.add(s);
                                    img.add(ARTICLE_NOIMG);
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("link"))
                        {
                            if (insideItem)
                            {
                                if (links.size() < ARTICLE_LIMIT) {

                                    links.add(xpp.nextText());
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("pubDate"))
                        {
                            if (insideItem)
                            {
                                if (pubdate.size() < ARTICLE_LIMIT) {

                                    pubdate.add(xpp.nextText());
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("media:content"))
                        {
                            if (insideItem) {
                                if (img.size() <= ARTICLE_LIMIT) {

                                    String s = xpp.getAttributeValue(null, "url");
                                    System.out.println("the url link is: " + s);
                                    if (img.size() > 0) {
                                        img.set(img.size() - 1, s);
                                    }
                                }
                            }
                        }

                    }

                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }
                    eventType = xpp.next();
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(titles.size() > 0 ) {
                linearLayout.setVisibility(View.GONE);
                lvRss.setVisibility(View.VISIBLE);
            }
            else {
                linearLayout.setVisibility(View.VISIBLE);
                lvRss.setVisibility(View.GONE);
            }
            CustomAdapter adapter = new CustomAdapter(titles,pubdate,img,MainActivity.this);
            lvRss.setAdapter(adapter);

            progressDialog.dismiss();


        }
    }



    public class independent extends AsyncTask<Integer, Void, String>
    {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            titles = new ArrayList<String>();
            links = new ArrayList<String>();
            img = new ArrayList<>();
            pubdate = new ArrayList<>();
            progressDialog.setMessage("Loading.... please wait");
            progressDialog.show();

        }


        @Override
        protected String doInBackground(Integer... integers) {
            try{
                URL url = new URL("http://www.independent.co.uk/news/world/rss");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (insideItem)
                            {
                                if (titles.size() < ARTICLE_LIMIT) {

                                    String s = xpp.nextText();
                                    s = s.replace("&apos;", "'");
                                    titles.add(s);
                                    img.add(ARTICLE_NOIMG);
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("link"))
                        {
                            if (insideItem)
                            {
                                if (links.size() < ARTICLE_LIMIT) {

                                    links.add(xpp.nextText());
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("pubDate"))
                        {
                            if (insideItem)
                            {
                                if (pubdate.size() < ARTICLE_LIMIT) {

                                    pubdate.add(xpp.nextText());
                                }
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("media:content"))
                        {
                            if (insideItem) {
                                if (img.size() <= ARTICLE_LIMIT) {

                                    String s = xpp.getAttributeValue(null, "url");
                                    System.out.println("the url link is: " + s);
                                    if (img.size() > 0) {
                                        img.set(img.size() - 1, s);
                                    }
                                }
                            }
                        }

                    }

                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }
                    eventType = xpp.next();
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(titles.size() > 0 ) {
                linearLayout.setVisibility(View.GONE);
                lvRss.setVisibility(View.VISIBLE);
            }
            else {
                linearLayout.setVisibility(View.VISIBLE);
                lvRss.setVisibility(View.GONE);
            }

            CustomAdapter adapter = new CustomAdapter(titles,pubdate,img,MainActivity.this);
            lvRss.setAdapter(adapter);

            progressDialog.dismiss();


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&  grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish();
                    System.exit(0);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
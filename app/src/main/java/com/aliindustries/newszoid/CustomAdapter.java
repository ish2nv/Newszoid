package com.aliindustries.newszoid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    ArrayList<String> title;
    ArrayList<String> pubdate;
    ArrayList<String> img;
    private Context mContext;
    private View view2;
    LayoutInflater inflater;
    public ImageLoader imageLoader;

    public CustomAdapter(ArrayList<String> title, ArrayList<String> pubdate, ArrayList<String> img, Context mContext) {
        this.title = title;
        this.pubdate = pubdate;
        this.img = img;
        this.mContext = mContext;
        imageLoader=new ImageLoader(this.mContext);

    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object getItem(int position) {
        return title.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView m_title;
        TextView m_date;
        ImageView m_image;

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            view2 = new View(mContext);

        } else {
            view2 = (View) convertView;
            //re-using if already here
        }

        view2 = inflater.inflate(R.layout.list_items1, parent, false);
        m_title = (TextView) view2.findViewById(R.id.textView2);
        m_date = (TextView) view2.findViewById(R.id.textView3);
        m_image = (ImageView) view2.findViewById(R.id.imageView2);


            m_title.setText(title.get(position));
            m_date.setText(pubdate.get(position));
        System.out.println("the img size is: " + img.size());

        imageLoader.DisplayImage(img.get(position), m_image);

        return view2;








    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            boolean a = true;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
                a = false;
            }

            if(a == false) {
                try {
                    InputStream in = new java.net.URL("https://image.flaticon.com/icons/png/512/125/125768.png").openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);

        }
    }



}

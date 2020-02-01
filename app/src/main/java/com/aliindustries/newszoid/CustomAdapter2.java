package com.aliindustries.newszoid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter2 extends BaseAdapter {

    ArrayList<String> title;
    ArrayList<String> pubdate;
    private Context mContext;
    private View view2;
    LayoutInflater inflater;


    public CustomAdapter2(ArrayList<String> title, ArrayList<String> pubdate, Context mContext) {
        this.title = title;
        this.pubdate = pubdate;
        this.mContext = mContext;
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
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            view2 = new View(mContext);

        } else {
            view2 = (View) convertView;
            //re-using if already here
        }

        view2 = inflater.inflate(R.layout.list_items2, parent, false);
        m_title = (TextView) view2.findViewById(R.id.textView4);
        m_date = (TextView) view2.findViewById(R.id.textView5);
        m_title.setText(title.get(position));
        m_date.setText(pubdate.get(position));

        return view2;
    }
}

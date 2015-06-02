package com.example.maxx.navigtab.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maxx.navigtab.R;

import java.util.ArrayList;

import com.example.maxx.navigtab.model.NavigationDrawerItem;

/**
 * Created by maxx on 2/5/15.
 */
public class DrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavigationDrawerItem> NavigationDrawerItems;

    public DrawerListAdapter(Context context, ArrayList<NavigationDrawerItem> NavigationDrawerItems){
        this.context = context;
        this.NavigationDrawerItems = NavigationDrawerItems;
    }

    @Override
    public int getCount() {
        return NavigationDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return NavigationDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.nav_drawer_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.nav_item_icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.nav_item_title);


        imgIcon.setImageResource(NavigationDrawerItems.get(position).getIcon());
        txtTitle.setText(NavigationDrawerItems.get(position).getTitle());

        // displaying count
        // check whether it set visible or not


        return convertView;
    }
}
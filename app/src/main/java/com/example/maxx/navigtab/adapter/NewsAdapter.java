package com.example.maxx.navigtab.adapter;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.maxx.navigtab.MySingleton;
import com.example.maxx.navigtab.R;
import com.example.maxx.navigtab.model.NewsArticles;

import java.util.List;

/**
 * Created by maxx on 23/5/15.
 */
public class NewsAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<NewsArticles> newsArticlesItems;
    ImageLoader imageLoader;

    public NewsAdapter(Activity activity, List<NewsArticles> newsArticlesItems)
    {
        this.activity = activity;
        this.newsArticlesItems = newsArticlesItems;
    }

    @Override
    public int getCount() {
        return newsArticlesItems.size();
    }

    @Override
    public Object getItem(int location) {
        return newsArticlesItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

    ViewHolder viewHolder;

        if(convertView == null)
        {
            inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cat_list_items, null);
            imageLoader = MySingleton.getInstance(activity).getImageLoader();
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.cat_title);
            viewHolder.thumbnail =  (NetworkImageView) convertView.findViewById(R.id.cat_thumbnail);
            viewHolder.name = (TextView) convertView.findViewById(R.id.cat_paper);

            //store the holder of the view
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NewsArticles news = newsArticlesItems.get(position);


        if(news.getThumbnailUrl()=="")
        {
            viewHolder.thumbnail.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.thumbnail.setVisibility(View.VISIBLE);
            viewHolder.thumbnail.setImageUrl(news.getThumbnailUrl(), imageLoader);
        }

        viewHolder.title.setText(Html.fromHtml(news.getTitle()));
        viewHolder.name.setText(Html.fromHtml(news.getNewspaperName()));

        return convertView;
    }
    static class ViewHolder{
        TextView title;
        TextView name;
        NetworkImageView thumbnail;
    }
}

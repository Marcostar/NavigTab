package com.example.maxx.navigtab.adapter;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.maxx.navigtab.R;
import com.example.maxx.navigtab.model.NewsPapers;

import java.util.List;

/**
 * Created by maxx on 23/6/15.
 */
public class NewsPaperAdapter extends BaseAdapter {

    private Activity activity;
    private List<NewsPapers> papers;
    private LayoutInflater inflater;

    public NewsPaperAdapter(Activity activity, List<NewsPapers> papers) {
        this.activity = activity;
        this.papers = papers;
    }

    @Override
    public int getCount() {
        return papers.size();
    }

    @Override
    public Object getItem(int location) {
        return papers.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.individual_paper_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.newspaper = (TextView) convertView.findViewById(R.id.SinglePaperName);

            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NewsPapers paperName = papers.get(position);

        viewHolder.newspaper.setText(Html.fromHtml(paperName.getNewsPaperName()));

        return convertView;
    }

    static class ViewHolder
    {
        TextView newspaper;
    }
}

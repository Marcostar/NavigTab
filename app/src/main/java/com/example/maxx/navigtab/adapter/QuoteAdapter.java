package com.example.maxx.navigtab.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.maxx.navigtab.R;
import com.example.maxx.navigtab.model.Quotes;

import java.util.List;

/**
 * Created by Dzeko on 8/24/2015.
 */
public class QuoteAdapter extends BaseAdapter {
    private Activity activity;
    private List<Quotes> quotesList;
    private LayoutInflater inflater;

    public QuoteAdapter(Activity activity, List<Quotes> quotes) {
        this.activity = activity;
        this.quotesList = quotes;
    }

    @Override
    public int getCount() {
        return quotesList.size();
    }

    @Override
    public Object getItem(int location) {
        return quotesList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.quote_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.Quote = (TextView) convertView.findViewById(R.id.quote);
            viewHolder.Timestamp = (TextView) convertView.findViewById(R.id.quoteTime);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Quotes quote = quotesList.get(position);

        viewHolder.Quote.setText(quote.getQuote());
        viewHolder.Timestamp.setText(quote.getTimeStamp());

        return convertView;
    }

    static class ViewHolder
    {
        TextView Quote;
        TextView Timestamp;
    }
}

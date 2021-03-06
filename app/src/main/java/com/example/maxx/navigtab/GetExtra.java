package com.example.maxx.navigtab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.maxx.navigtab.adapter.QuoteAdapter;
import com.example.maxx.navigtab.helper.MySQLiteHelper;
import com.example.maxx.navigtab.model.Quotes;

import java.util.ArrayList;
import java.util.List;

public class GetExtra extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private List<Quotes> QuoteList;
    private QuoteAdapter adapter;
    private LinearLayout QuoteErrorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_extra);
        QuoteErrorLayout = (LinearLayout) findViewById(R.id.QuoteError);
        listView = (ListView) findViewById(R.id.quoteList);
        MySQLiteHelper helper = new MySQLiteHelper(this);
        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("Get Extra");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        QuoteList = new ArrayList<>(helper.getAllQuotes());
        if (QuoteList.size()==0)
        {
            QuoteErrorLayout.setVisibility(View.VISIBLE);
        }
        adapter = new QuoteAdapter(this,QuoteList);
        listView.setAdapter(adapter);
        /*for (Quotes qn : QuoteList)
        {
            values.add(qn.getQuote());
        }*/
        helper.close();
/*        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);*/
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_extra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

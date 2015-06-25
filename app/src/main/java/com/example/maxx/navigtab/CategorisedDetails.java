package com.example.maxx.navigtab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


public class CategorisedDetails extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView title;
    private TextView newsPaperName;
    private TextView content;
    private NetworkImageView thumbnail;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorised_details);

        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //grab all the details from MainActivity
        String NewsPaperName = getIntent().getExtras().getString("NewsPaperName");
        String Title = getIntent().getExtras().getString("Title");
        String ThumbnailURL = getIntent().getExtras().getString("ThumbnailURL");
        String Content = getIntent().getExtras().getString("Content");
        String ArticleLink = getIntent().getExtras().getString("ArticleLink");

        title = (TextView) findViewById(R.id.det_title);
        newsPaperName = (TextView) findViewById(R.id.det_paper);
        content = (TextView) findViewById(R.id.det_story);
        content.setMovementMethod(new ScrollingMovementMethod());
        thumbnail = (NetworkImageView) findViewById(R.id.det_thumbnail);
        imageLoader = MySingleton.getInstance(this).getImageLoader();

        if(ThumbnailURL.equals(""))
        {
            thumbnail.setVisibility(View.GONE);
        }
        else {
            thumbnail.setVisibility(View.VISIBLE);
            thumbnail.setImageUrl(ThumbnailURL, imageLoader);
        }
        title.setText(Html.fromHtml(Title));
        newsPaperName.setText(Html.fromHtml(NewsPaperName));
        content.setText(Html.fromHtml(Content));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_categorised_details, menu);
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
        if (id == android.R.id.home)
        {
            Intent upIntent = new Intent(this, MainActivity.class);
            upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(upIntent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

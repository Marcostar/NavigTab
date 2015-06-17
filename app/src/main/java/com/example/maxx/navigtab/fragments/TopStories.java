package com.example.maxx.navigtab.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.maxx.navigtab.MainActivity;
import com.example.maxx.navigtab.MySingleton;
import com.example.maxx.navigtab.R;
import com.example.maxx.navigtab.adapter.NewsAdapter;
import com.example.maxx.navigtab.model.NewsArticles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxx on 22/5/15.
 */
public class TopStories extends Fragment {
    private static final String TAG = TopStories.class.getSimpleName();

    private static final String StoryType = "TopStories.php";
    private String url = "http://192.168.1.4/simplepie/India";
    SharedPreferences sharedPreferences;
    private String language;
    private ProgressDialog loadDialog;
    private ListView listView;
    private List<NewsArticles> newsArticlesList = new ArrayList<NewsArticles>();
    private NewsAdapter newsAdapter;

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootview = inflater.inflate(R.layout.categorized_list,container,false);
        sharedPreferences = this.getActivity().getSharedPreferences("PreferenceSETTING",Context.MODE_PRIVATE);
        language = sharedPreferences.getString("LANGUAGE","English");
        listView = (ListView) rootview.findViewById(R.id.cat_list);
        newsAdapter = new NewsAdapter(getActivity(),newsArticlesList);
        listView.setAdapter(newsAdapter);

        loadDialog = new ProgressDialog(getActivity());
        loadDialog.setMessage("Getting Stories...");
        loadDialog.show();
//hello
        //Requesting News now
        JsonObjectRequest topStoriesRequest = new JsonObjectRequest(url+language+StoryType, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideDialog();
                try {
                    JSONArray NewsItems = response.getJSONArray("NewsItems");
                    Log.d(MainActivity.class.getSimpleName(),NewsItems.toString());
                    for(int i = 0;i<NewsItems.length();i++)
                    {
                        JSONObject object = NewsItems.getJSONObject(i);
                        NewsArticles news = new NewsArticles();
                        //news.setArticleLink(object.getString("ArticleLink"));
                        news.setTitle(object.getString("Title"));
                        //news.setContent(object.getString("Content"));
                        news.setNewspaperName(object.getString("Source"));
                        //Image Extraction
                        Document doc = Jsoup.parse(object.getString("Content"));
                        Elements link = doc.select("img");
                        if(link.attr("src")!= null)
                        {
                            news.setThumbnailUrl(link.attr("src"));
                        }
                        else {
                            news.setThumbnailUrl(null);
                        }
                        newsArticlesList.add(news);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                newsAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                hideDialog();
            }
        });
        topStoriesRequest.setRetryPolicy(new DefaultRetryPolicy(5000,5,1f));
        MySingleton.getInstance(getActivity()).addToRequestQueue(topStoriesRequest);

        return rootview;
    }
    public void onDestroy()
    {
        super.onDestroy();
        hideDialog();
    }

    private void hideDialog() {
        if(loadDialog!=null)
        {
            loadDialog.dismiss();
            loadDialog = null;
        }
    }


}

package com.example.maxx.navigtab.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.maxx.navigtab.IndividualPaperDetails;
import com.example.maxx.navigtab.MainActivity;
import com.example.maxx.navigtab.MySingleton;
import com.example.maxx.navigtab.R;
import com.example.maxx.navigtab.adapter.IndividualNewsAdapter;
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
 * Created by Santosh on 7/15/2015.
 */
public class SingleSport extends Fragment {

    private static final String TAG = SingleSport.class.getSimpleName();
    private String url,paperName;
    private SharedPreferences sharedPreferences;
    private ListView listView;
    private List<NewsArticles> newsArticlesList = new ArrayList<NewsArticles>();
    private IndividualNewsAdapter newsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout layout,timeOutErrorLayout,JsonExceptionLayout,articleLoading;


    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootview = inflater.inflate(R.layout.categorized_list,container,false);
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.PreferenceSETTINGS, Context.MODE_PRIVATE);
        url = sharedPreferences.getString(MainActivity.SPORTURL, null);
        paperName = sharedPreferences.getString(MainActivity.PAPERNAME, null);
        listView = (ListView) rootview.findViewById(R.id.cat_list);
        layout = (LinearLayout) rootview.findViewById(R.id.VolleyError);
        timeOutErrorLayout = (LinearLayout) rootview.findViewById(R.id.TimeOutError);
        JsonExceptionLayout = (LinearLayout) rootview.findViewById(R.id.JsonException);
        articleLoading = (LinearLayout) rootview.findViewById(R.id.articleLoading);
        // Retrieve the SwipeRefreshLayout and ListView instances
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipeRefresh);

        // Set the color scheme of the SwipeRefreshLayout by providing 4 color resource ids
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4);
        //Requesting News now

        return rootview;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsAdapter = new IndividualNewsAdapter(getActivity(),newsArticlesList);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //String NewsPaperName = newsArticlesList.get(position).getNewspaperName();
                String Title = newsArticlesList.get(position).getTitle();
                String ThumbnailURL = newsArticlesList.get(position).getThumbnailUrl();
                String Content = newsArticlesList.get(position).getcontent();
                String ArticleLink = newsArticlesList.get(position).getArticleLink();

                //Pass the values to another activity
                Intent intent = new Intent(getActivity(), IndividualPaperDetails.class);
                intent.putExtra("NewsPaperName", paperName);
                intent.putExtra("Title", Title);
                intent.putExtra("ThumbnailURL", ThumbnailURL);
                intent.putExtra("Content", Content);
                intent.putExtra("ArticleLink", ArticleLink);

                startActivity(intent);
            }
        });
        articleLoading.setVisibility(View.VISIBLE);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JsonExceptionLayout.setVisibility(View.GONE);
                timeOutErrorLayout.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
                initiateRefresh();
            }
        });


        initiateRefresh();

        /*mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                initiateRefresh();
            }
        });*/
    }

    private void initiateRefresh() {

        mSwipeRefreshLayout.setRefreshing(true);
        if(!MainActivity.isOnline())
        {
            if(!newsArticlesList.isEmpty())
            {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(),R.string.NoInternet,Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            newsArticlesList.clear();
        }
        final JsonObjectRequest topStoriesRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    articleLoading.setVisibility(View.GONE);
                    JSONObject RSS = response.getJSONObject("rss");
                    JSONObject Channel = RSS.getJSONObject("channel");
                    JSONArray NewsItems = Channel.getJSONArray("item");
                    Log.d(MainActivity.class.getSimpleName(), NewsItems.toString());
                    for(int i = 0;i < NewsItems.length();i++)
                    {
                        JSONObject object = NewsItems.getJSONObject(i);
                        NewsArticles news = new NewsArticles();

                        news.setTitle(object.getString("title"));

                        //news.setNewspaperName(object.getString("Source"));

                        Document doc = Jsoup.parse(object.getString("description"));
                        news.setcontent(doc.text());

                        news.setArticleLink(object.getString("link"));

                        //Image Extraction
                        doc = Jsoup.parse(object.getString("description"));
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
                    JsonExceptionLayout.setVisibility(View.VISIBLE);
                }
                newsAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                articleLoading.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d(TAG, volleyError.toString());

                if(volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError)
                {
                    timeOutErrorLayout.setVisibility(View.VISIBLE);
                }

                if((newsArticlesList.isEmpty())&& (!MainActivity.isOnline()) )
                {
                    layout.setVisibility(View.VISIBLE);
                }
                else if(!MainActivity.isOnline())
                {
                    Toast.makeText(getActivity(), "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                }

            }
        });
        topStoriesRequest.setRetryPolicy(new DefaultRetryPolicy(4000,2,2f));
        MySingleton.getInstance(getActivity()).addToRequestQueue(topStoriesRequest);

    }

    public void onDestroy()
    {
        super.onDestroy();

    }



}


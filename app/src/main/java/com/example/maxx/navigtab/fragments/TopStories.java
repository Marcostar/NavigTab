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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.maxx.navigtab.CategorisedDetails;
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
    private String url = "http://ft.sagycorp.com/SimplePie/";
    private SharedPreferences sharedPreferences;
    private String language;
    private ListView listView;
    private List<NewsArticles> newsArticlesList = new ArrayList<NewsArticles>();
    private NewsAdapter newsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout loadingError,articleLoading;


    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootview = inflater.inflate(R.layout.categorized_list,container,false);
        sharedPreferences = this.getActivity().getSharedPreferences(MainActivity.PreferenceSETTINGS, Context.MODE_PRIVATE);
        language = sharedPreferences.getString(MainActivity.LANGUAGE, "India");
        listView = (ListView) rootview.findViewById(R.id.cat_list);
        loadingError = (LinearLayout) rootview.findViewById(R.id.VolleyError);
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
        newsAdapter = new NewsAdapter(getActivity(),newsArticlesList);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String NewsPaperName = newsArticlesList.get(position).getNewspaperName();
                String Title = newsArticlesList.get(position).getTitle();
                String ThumbnailURL = newsArticlesList.get(position).getThumbnailUrl();
                String Content = newsArticlesList.get(position).getcontent();
                String ArticleLink = newsArticlesList.get(position).getArticleLink();

                //Pass the values to another activity
                Intent intent = new Intent(getActivity(), CategorisedDetails.class);
                intent.putExtra("NewsPaperName", NewsPaperName);
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
                loadingError.setVisibility(View.GONE);
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

        final JsonObjectRequest topStoriesRequest = new JsonObjectRequest(url+language+StoryType, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    articleLoading.setVisibility(View.GONE);
                    JSONArray NewsItems = response.getJSONArray("NewsItems");
                    Log.d(MainActivity.class.getSimpleName(), NewsItems.toString());
                    for(int i = 0;i<NewsItems.length();i++)
                    {
                        JSONObject object = NewsItems.getJSONObject(i);
                        NewsArticles news = new NewsArticles();

                        news.setTitle(object.getString("Title"));

                        news.setNewspaperName(object.getString("Source"));

                        Document doc = Jsoup.parse(object.getString("Content"));
                        news.setcontent(doc.text());

                        news.setArticleLink(object.getString("ArticleLink"));

                        //Image Extraction
                        doc = Jsoup.parse(object.getString("Content"));
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
                mSwipeRefreshLayout.setRefreshing(false);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                articleLoading.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d(TAG, volleyError.toString());


                if((newsArticlesList.isEmpty())&& (!MainActivity.isOnline()) )
                {
                    loadingError.setVisibility(View.VISIBLE);
                }
                else if(!MainActivity.isOnline())
                {
                    Toast.makeText(getActivity(), "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                }

            }
        });
        topStoriesRequest.setRetryPolicy(new DefaultRetryPolicy(5000,5,1f));
        MySingleton.getInstance(getActivity()).addToRequestQueue(topStoriesRequest);

    }

    public void onDestroy()
    {
        super.onDestroy();

    }



}

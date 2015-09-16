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
import com.example.maxx.navigtab.IndividualPaperTabs;
import com.example.maxx.navigtab.MainActivity;
import com.example.maxx.navigtab.MySingleton;
import com.example.maxx.navigtab.R;
import com.example.maxx.navigtab.adapter.NewsPaperAdapter;
import com.example.maxx.navigtab.model.NewsPapers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxx on 4/5/15.
 */
public class IndividualPaper extends Fragment
{
    private static final String TAG = IndividualPaper.class.getSimpleName();
    private static final String StoryType = ".php";
    private String url = "http://ft.sagycorp.com/GetNews/";
    private ListView listView;
    private List<NewsPapers> papersList = new ArrayList<>();
    private NewsPaperAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private LinearLayout NewspaperLoading,LoadingError;
    private String language;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView =  inflater.inflate(R.layout.individual_paper_list,
                container, false);
        NewspaperLoading = (LinearLayout) rootView.findViewById(R.id.NewsPaperLoading);
        LoadingError = (LinearLayout) rootView.findViewById(R.id.NewsPaperLoadingError);
        preferences = getActivity().getSharedPreferences(MainActivity.PreferenceSETTINGS, Context.MODE_PRIVATE);
        listView = (ListView) rootView.findViewById(R.id.NewsPaperList);
        language = preferences.getString(MainActivity.LANGUAGE, "India");
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);

        // Set the color scheme of the SwipeRefreshLayout by providing 4 color resource ids
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4);
        return rootView;

    }
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        adapter = new NewsPaperAdapter(getActivity(), papersList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), IndividualPaperTabs.class);
                editor = preferences.edit();
                editor.putString(MainActivity.PAPERNAME, papersList.get(position).getNewsPaperName());
                editor.putString(MainActivity.TOPSTORIESURL, papersList.get(position).getTopStoriesURL());
                editor.putString(MainActivity.NATIONALURL, papersList.get(position).getNationalURL());
                editor.putString(MainActivity.WORLDURL, papersList.get(position).getWorldURL());
                editor.putString(MainActivity.SPORTURL, papersList.get(position).getSportURL());
                editor.putString(MainActivity.ENTERTAINMENTURL, papersList.get(position).getEntertainmentURL());
                editor.putString(MainActivity.BUSINESSURL, papersList.get(position).getBusinessURL());
                editor.apply();
                String NewspaperName = papersList.get(position).getNewsPaperName();
                intent.putExtra("NewsPaperName", NewspaperName);
                startActivity(intent);
            }
        });
        NewspaperLoading.setVisibility(View.VISIBLE);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadingError.setVisibility(View.GONE);
                initiate();
            }
        });

        initiate();


    }

    private void initiate()
    {
        mSwipeRefreshLayout.setRefreshing(true);
        papersList.clear();
        JsonObjectRequest request = new JsonObjectRequest(url + language + StoryType, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                NewspaperLoading.setVisibility(View.GONE);
                try {
                    JSONArray array = response.getJSONArray("NewsPaper");
                    for(int i = 0;i<array.length();i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        NewsPapers papers = new NewsPapers();
                        papers.setNewsPaperName(object.getString("PaperName"));
                        papers.setTopStoriesURL(object.getString("TopStoriesURL"));
                        papers.setNationalURL(object.getString("NationalURL"));
                        papers.setWorldURL(object.getString("WorldURL"));
                        papers.setSportURL(object.getString("SportsURL"));
                        papers.setEntertainmentURL(object.getString("EntertainmentURL"));
                        papers.setBusinessURL(object.getString("BusinessURL"));
                        papersList.add(papers);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                NewspaperLoading.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d(TAG, volleyError.toString());

                if(volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError)
                {
                    LoadingError.setVisibility(View.VISIBLE);
                }


                if((papersList.isEmpty())&& (!MainActivity.isOnline()) )
                {
                    LoadingError.setVisibility(View.VISIBLE);
                }
                else if(!MainActivity.isOnline())
                {
                    Toast.makeText(getActivity(), "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                }

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(4000,2,2f));
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);

    }

    public void onDestroy()
    {
        super.onDestroy();
    }

}

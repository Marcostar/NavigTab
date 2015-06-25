package com.example.maxx.navigtab.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.maxx.navigtab.IndividualPaperTabs;
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
    private static final String StoryType = "NewsPaper.php";
    private String url = "http://192.168.1.3/GetNews/India";
    private ListView listView;
    private List<NewsPapers> papersList = new ArrayList<>();
    private NewsPaperAdapter adapter;
    private ProgressDialog dialog;
    private SharedPreferences preferences;
    private String language;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView =  inflater.inflate(R.layout.individual_paper_list,
                container, false);
        listView = (ListView) rootView.findViewById(R.id.NewsPaperList);
        preferences = this.getActivity().getSharedPreferences("PreferenceSETTING", Context.MODE_PRIVATE);
        language = preferences.getString("LANGUAGE","English");
        adapter = new NewsPaperAdapter(getActivity(), papersList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), IndividualPaperTabs.class);
                String NewspaperName = papersList.get(position).getNewsPaperName();
                intent.putExtra("NewsPaperName",NewspaperName);
                startActivity(intent);
            }
        });
        dialog = new ProgressDialog(getActivity());
        dialog.show();

        JsonObjectRequest request = new JsonObjectRequest(url + language + StoryType, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                hideDialog();
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
                        papers.setSportURL(object.getString("SportURL"));
                        papers.setEntertainmentURL(object.getString("EntertainmentURL"));
                        papers.setBusinessURL(object.getString("BusinessURL"));
                        papersList.add(papers);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(5000,5,1f));
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);

        return rootView;

    }
    public void onDestroy()
    {
        super.onDestroy();
        hideDialog();
    }

    private void hideDialog() {
        if(dialog!=null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}

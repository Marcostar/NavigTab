package com.example.maxx.navigtab.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxx.navigtab.R;

/**
 * Created by maxx on 4/5/15.
 */
public class fragment1 extends Fragment
{
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view =  inflater.inflate(R.layout.toolbar_fragment,
                container, false);
        return view;

    }
}

package com.example.maxx.navigtab;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.maxx.navigtab.adapter.drawerListAdapter;
import com.example.maxx.navigtab.fragments.fragment1;
import com.example.maxx.navigtab.fragments.slidingTab;
import com.example.maxx.navigtab.model.navDrawerItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {





    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<navDrawerItem> navDrawerItems;
    private ListView mDrawerList;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ActionBarDrawerToggle mDrawerToggle;
    private drawerListAdapter adapter;
    private int mCurrentSelectedPosition = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();

        navMenuTitles = getResources().getStringArray(R.array.nav_titles);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_icon);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_open,R.string.drawer_close){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
            }

        };


        mDrawerLayout.setDrawerListener(mDrawerToggle);


        navDrawerItems = new ArrayList<>();

        navDrawerItems.add(new navDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new navDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));

        navMenuIcons.recycle();


        adapter = new drawerListAdapter(getApplicationContext(),navDrawerItems);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());



        mDrawerList.setAdapter(adapter);
      //  mDrawerList.setItemChecked(mCurrentSelectedPosition, true);
        if (savedInstanceState == null) {
            selectItem(0);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        FragmentManager fragmentManager  = getSupportFragmentManager();
        switch(position) {
            case 0:

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new slidingTab()).commit();
                mDrawerList.setItemChecked(position, true);
                setTitle(navMenuTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);

                break;

            case 1:

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new fragment1())
                        .commit();
                mDrawerList.setItemChecked(position, true);
                setTitle(navMenuTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;
        }
        // Highlight the selected item, update the title, and close the drawer

    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}

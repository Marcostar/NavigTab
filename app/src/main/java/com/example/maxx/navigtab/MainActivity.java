package com.example.maxx.navigtab;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.maxx.navigtab.adapter.DrawerListAdapter;
import com.example.maxx.navigtab.fragments.IndividualPaper;
import com.example.maxx.navigtab.fragments.SlidingTab;
import com.example.maxx.navigtab.model.NavigationDrawerItem;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<NavigationDrawerItem> NavigationDrawerItems;
    private ListView mDrawerList;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerListAdapter adapter;
    private Toolbar toolbar;
    private String[] editions;
    private String selectedEdition;

    public static final String PreferenceSETTINGS = "Preferences";
    public static final String LANGUAGE = "English";
    public static final String TOPSTORIESURL = null;
    public static final String NATIONALURL = null;
    public static final String WORLDURL = null;
    public static final String SPORTURL = null;
    public static final String ENTERTAINMENTURL = null;
    public static final String BUSINESSURL = null;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    public String URL="http://192.168.1.4/GetNews/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
            setSupportActionBar(toolbar);
            mTitle = mDrawerTitle = getTitle();
            sharedPreferences = getSharedPreferences(PreferenceSETTINGS, this.MODE_PRIVATE);
            navMenuTitles = getResources().getStringArray(R.array.nav_titles);
            navMenuIcons = getResources().obtainTypedArray(R.array.nav_icon);
            mDrawerList = (ListView) findViewById(R.id.left_drawer);
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

                /**
                 * Called when a drawer has settled in a completely closed state.
                 */
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    getSupportActionBar().setTitle(mTitle);
                }

                /**
                 * Called when a drawer has settled in a completely open state.
                 */
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    getSupportActionBar().setTitle(mDrawerTitle);
                }

            };


            mDrawerLayout.setDrawerListener(mDrawerToggle);


            NavigationDrawerItems = new ArrayList<>();

            NavigationDrawerItems.add(new NavigationDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
            NavigationDrawerItems.add(new NavigationDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
            NavigationDrawerItems.add(new NavigationDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
            NavigationDrawerItems.add(new NavigationDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
            navMenuIcons.recycle();


            adapter = new DrawerListAdapter(getApplicationContext(), NavigationDrawerItems);

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
        if (id == R.id.change_edition)
        {
            editor = sharedPreferences.edit();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();

            View dialogView = inflater.inflate(R.layout.change_edition,null);
            builder.setView(dialogView);
            builder.setTitle(R.string.edition_Changer);
            Spinner spinner = (Spinner) dialogView.findViewById(R.id.edition_spinner);
            ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.edition_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    editions = getResources().getStringArray(R.array.edition_array);

                   /* selectedEdition = editions[position];*/
                    if (editions[position] == "English")
                    {
                        selectedEdition = "India";
                    }
                    else
                    {
                        selectedEdition = editions[position];
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editor.putString(LANGUAGE, selectedEdition);
                    editor.commit();
                    if (Build.VERSION.SDK_INT >= 11) {
                        recreate();
                    } else {
                        Intent intent = getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        finish();
                        overridePendingTransition(0, 0);

                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    }
                }
            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();

            return true;

        }
        if(id == R.id.feedback)
        {
            Intent Email = new Intent(Intent.ACTION_SEND);
            Email.setType("text/email");
            Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "admin@hotmail.com" });
            Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            Email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
            startActivity(Intent.createChooser(Email, "Send Feedback:"));
            return true;
        }
        if (id == R.id.rating)
        {
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
                        .replace(R.id.content_frame, new SlidingTab()).commit();
                mDrawerList.setItemChecked(position, true);
                setTitle(navMenuTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);

                break;

            case 1:

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new IndividualPaper())
                        .commit();
                mDrawerList.setItemChecked(position, true);
                setTitle(navMenuTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                break;

            case 2:

                Intent getQuote = new Intent(this, GetExtra.class);
                startActivity(getQuote);

                break;


            case 3:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;
        }
        // Highlight the selected item, update the title, and close the drawer

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    //OnBackPressed
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Read Enough?")
                .setCancelable(false)
                .setMessage("Are you sure you want to stop reading?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public static boolean isOnline()
    {
        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

}

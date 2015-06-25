package com.example.maxx.navigtab.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maxx.navigtab.R;
import com.example.maxx.navigtab.tabView.SlidingTabLayout;

/**
 * Created by maxx on 25/6/15.
 */
public class SinglePaperSlidingTab extends Fragment {

    private SlidingTabLayout mSlidingTabLayout;

    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new tabPages(getActivity().getSupportFragmentManager()));

        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    //Adapter that will assign titles and pages to tabs
    class tabPages extends FragmentStatePagerAdapter {
        public tabPages(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return new SingleTopStories();
                case 1:
                    return new National();
                default:
                    Fragment fragment = new DemoObjectFragment();
                    Bundle args = new Bundle();
                    // Our object is just an integer :-P
                    args.putInt(DemoObjectFragment.ARG_OBJECT, position + 1);
                    fragment.setArguments(args);
                    return fragment;

            }

        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0: return "TOP STORIES ";
                case 1: return "NATIONAL ";
                case 2: return "WORLD";
                case 3: return "SPORT";
                case 4: return "ENTERTAINMENT";
                case 5: return "BUSINESS";
            }
            return null;
        }


    }
    public static class DemoObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.test1, container, false);
            Bundle args = getArguments();

            return rootView;
        }
    }
}


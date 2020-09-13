package id.radikz.movielistwithsql;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.radikz.movielistwithsql.fragment.FavMovieFragment;
import id.radikz.movielistwithsql.fragment.FavTvShowFragment;
import id.radikz.movielistwithsql.fragment.MovieFragment;
import id.radikz.movielistwithsql.fragment.TvShowFragment;


public class FavMovieActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    String lang;

    private final String SIMPLE_FRAGMENT_TAG = "myfragmenttag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.slidingtabs);
        tabLayout.setupWithViewPager(viewPager);

//        adapter = new MoviesAdapter(this);
//        adapter.notifyDataSetChanged();

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentByTag(SIMPLE_FRAGMENT_TAG);
//
//        if (fragment==null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.container, fragmentSimple, SIMPLE_FRAGMENT_TAG)
//                    .commit();
//        }

//        if (savedInstanceState != null) { // savedInstanceState ada, fragment mungkin masih ada
//            // cari fragment yang sudah ada itu dengan tag
//            fragmentSimple = (MovieFragment) getSupportFragmentManager().findFragmentByTag(SIMPLE_FRAGMENT_TAG);
//        } else if (fragmentSimple == null) {
//            // buat hanya jika fragment tersebut belum ada
//            fragmentSimple = new MovieFragment();
//        }

    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //Save the fragment's instance
//        getSupportFragmentManager().putFragment(outState, SIMPLE_FRAGMENT_TAG, fragmentSimple);
//    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FavMovieFragment(), getResources().getString(R.string.fav_movie_tab));
        adapter.addFragment(new FavTvShowFragment(), getResources().getString(R.string.fav_tv_show_tab));
        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }




}

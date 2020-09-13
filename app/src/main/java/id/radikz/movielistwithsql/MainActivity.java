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

import id.radikz.movielistwithsql.fragment.MovieFragment;
import id.radikz.movielistwithsql.fragment.TvShowFragment;


public class MainActivity extends AppCompatActivity {

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
        adapter.addFragment(new MovieFragment(), getResources().getString(R.string.movie_tab));
        adapter.addFragment(new TvShowFragment(), getResources().getString(R.string.tv_show_tab));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_language){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        else if (item.getItemId() == R.id.fav_menu){
            Intent moveWithObjectIntent = new Intent(this, FavMovieActivity.class);
            startActivity(moveWithObjectIntent);
        }
        return super.onOptionsItemSelected(item);
    }


}
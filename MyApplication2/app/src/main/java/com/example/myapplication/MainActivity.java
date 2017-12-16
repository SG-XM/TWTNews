package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //private String url = "http://open.twtstudio.com/api/v1/news/1/page/1";
    private String[] urls = {"https://open.twtstudio.com/api/v1/news/1/page/1",
            "https://open.twtstudio.com/api/v1/news/2/page/1",
            "https://open.twtstudio.com/api/v1/news/3/page/1",
            "https://open.twtstudio.com/api/v1/news/4/page/1",
            "https://open.twtstudio.com/api/v1/news/5/page/1"};

    private boolean isSelected[] = {false, false, false, false, false};
    private String url;
    private RecyclerView recyclerView;

    ///////////////////////////////////////////////ViewPager////////////////////////////////////////////////

    private ViewPager mViewPager;
    private FragmentPagerAdapter fpAdapter;
    private List<android.support.v4.app.Fragment> mFragments;

    //////////////////////////////////////////////ViewPager///////////////////////////////////////////////////

    int GREY;
    int BLACK;

    private Button button0, button1, button2, button3, button4;
    private android.support.v4.app.Fragment fragment0, fragment1, fragment2, fragment3, fragment4;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GREY = getResources().getColor(R.color.topTextGrey);
        BLACK = getResources().getColor(R.color.black);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        initView();
        initEvent();
        button0.setTextColor(BLACK);

    }


    private void initEvent() {
        button0.setOnClickListener(this);//this指的是调用这段代码的对象，也就是上下文，也就是MainActivity
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    private void initView() {

        button0 = findViewById(R.id.top_Button0);
        button1 = findViewById(R.id.top_Button1);
        button2 = findViewById(R.id.top_Button2);
        button3 = findViewById(R.id.top_Button3);
        button4 = findViewById(R.id.top_Button4);

        fragment0 = new NewsFragment();
        fragment1 = new NewsFragment();
        fragment2 = new NewsFragment();
        fragment3 = new NewsFragment();
        fragment4 = new NewsFragment();

//        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
//        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.add(R.id.content, fragment0);
//        fragmentTransaction.add(R.id.content, fragment1);
//        fragmentTransaction.add(R.id.content, fragment2);
//        fragmentTransaction.add(R.id.content, fragment3);
//        fragmentTransaction.add(R.id.content, fragment4);
//        fragmentTransaction.commit();

        /////////////////////////////////////////初始化ViewPager和数据源//////////////////////////////////////////////////

        mViewPager = findViewById(R.id.content);
        mFragments = new ArrayList<>();
        mFragments.add(fragment0);
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);
        mFragments.add(fragment4);

        for (int i = 0; i < mFragments.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString("url", urls[i]);
            mFragments.get(i).setArguments(bundle);
        }

        /////////////////////////////////////////初始化ViewPager和数据源//////////////////////////////////////////////////

        ////////////////////////////////////////////////////初始化Adapter///////////////////////////////////////////////////////////////

        fpAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.setAdapter(fpAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                int currentItem = mViewPager.getCurrentItem();
//                setSelectedTab(currentItem);
                setSelectedTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        ////////////////////////////////////////////////////初始化Adapter////////////////////////////////////////////////////////////////

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_Button0:
                setSelectedTab(0);
                break;

            case R.id.top_Button1:
                setSelectedTab(1);
                break;

            case R.id.top_Button2:
                setSelectedTab(2);
                break;

            case R.id.top_Button3:
                setSelectedTab(3);
                break;

            case R.id.top_Button4:
                setSelectedTab(4);
                break;
        }


    }

    private void setSelectedTab(int i) {
        button0.setTextColor(GREY);
        button1.setTextColor(GREY);
        button2.setTextColor(GREY);
        button3.setTextColor(GREY);
        button4.setTextColor(GREY);

        switch (i) {
            case 0:
                button0.setTextColor(BLACK);
                break;
            case 1:
                button1.setTextColor(BLACK);
                break;
            case 2:
                button2.setTextColor(BLACK);
                break;
            case 3:
                button3.setTextColor(BLACK);
                break;
            case 4:
                button4.setTextColor(BLACK);
                break;
        }
        mViewPager.setCurrentItem(i);

    }

//

    /////////////////////////////////////////////////设置菜单//////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_refresh:
                NewsFragment fragment;
                fragment = (NewsFragment) mFragments.get(mViewPager.getCurrentItem());
              //  fragment.refresh();
                Toast.makeText(this, "你还是下拉刷新吧", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}



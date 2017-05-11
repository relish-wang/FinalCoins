package com.example.aedvance.finalcoins.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.aedvance.finalcoins.R;
import com.example.aedvance.finalcoins.ui.fragment.InfoFragment;
import com.example.aedvance.finalcoins.ui.fragment.MirrorFragment;
import com.example.aedvance.finalcoins.ui.fragment.QuestionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aedvance on 2017/5/2.
 */

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private TabLayout mTabLayout;
    public ViewPager mViewPager;

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<Fragment> fragments = new ArrayList<>();//页卡

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainface);

        initViews();
    }

    private void initViews() {
        //更改状态蓝色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.rgb(73, 133, 208));
        }
        mViewPager = (ViewPager) findViewById(R.id.vp_view);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        //添加页卡视图
        Fragment zone = new QuestionFragment();
        Fragment mirror = new MirrorFragment();
        Fragment info = new InfoFragment();
        fragments.add(zone);
        fragments.add(mirror);
        fragments.add(info);


        //添加页卡标题
        mTitleList.add("圈子");
        mTitleList.add("魔镜");
        mTitleList.add("个人");
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));

        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mViewPager.setOnPageChangeListener(this);
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }

    boolean isCreated = false;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(!isCreated){
            isCreated = true;
            return;
        }
        if (position == 0) {
            ((QuestionFragment) (fragments.get(0))).request();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    //ViewPager适配器
    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }
    }
}

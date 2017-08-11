package com.max.testfragment.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.max.testfragment.R;
import com.max.testfragment.model.ChannelItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 新首页 - 职位推荐2
 * Created by Max on 2017/6/9.
 */
public class JobsFragment2 extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private View mRootView;
    private static JobsFragment2 fragment;

    private TabLayout mTab;
    private ViewPager mViewPager;
    private int mCurIndex = 0;
    private ArrayList<Fragment> mFragmentLists = new ArrayList<>();//存储所有fragment
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private List<ChannelItem> mGridList = new ArrayList<>();//tab条的所有内容
    private List<HashMap<String, JobsItemFragment>> mFragmentsMap = new ArrayList<>();//存储fragment与title的关系，排序也能用

    synchronized public static JobsFragment2 getInstance() {
        if(fragment == null){
            fragment = new JobsFragment2();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_jobs, null);
        initView(mRootView);
        requestMenu();
        return mRootView;
    }

    private void initView(View view) {
        mTab = (TabLayout) view.findViewById(R.id.tab);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPager.addOnPageChangeListener(this);
    }

    private void requestMenu() {
        //TODO 联网获取数据……  假设已经获取
        initDynamicGridViewData();
        initFragmentMap();
        initTab();

        if(mFragmentLists == null || mFragmentLists.size() == 0 || mFragmentLists.size() <= mCurIndex){
            return;
        }
        ((JobsItemFragment)mFragmentLists.get(mCurIndex)).refreshData(true);
    }

    private void initDynamicGridViewData(){
        mGridList.clear();
        //测试数据
        for(int i=0;i<10;i++){
            mGridList.add(new ChannelItem(i, "tag"+i, i, false));
        }
    }

    //刷新Fragment的HashMap。 存储后，如果交换tag顺序，就从已有的map中取回已创建的Fragment
    private void initFragmentMap(){
        mFragmentsMap.clear();
        for (int i = 0; i < mGridList.size(); i++) {
            HashMap<String, JobsItemFragment> map = new HashMap<>();
            map.put(mGridList.get(i).getName(),
                    JobsItemFragment.newInstance(mGridList.get(i).getName(), mGridList.get(i).getId()));
            mFragmentsMap.add(map);
        }
    }

    //按着mGridList中的类型顺序，重新排序fragments
    private void refreshFragments(){
        mFragmentLists.clear();
        for (int i = 0; i < mGridList.size(); i++) {
            for(int j=0;j<mFragmentsMap.size();j++){
                HashMap<String, JobsItemFragment> map = mFragmentsMap.get(j);
                if(map.containsKey(mGridList.get(i).getName())){
                    mFragmentLists.add(map.get(mGridList.get(i).getName()));
                    break;
                }
            }
        }
    }

    protected void initTab() {
        refreshFragments();
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mMyFragmentPagerAdapter);
        mTab.setupWithViewPager(mViewPager);
        // mTab.setViewPager(mViewPager, getTitles(), getActivity(), mFragmentLists);
    }

    private String[] getTitles(){
        String[] titles = new String[mGridList.size()];
        for(int i=0;i<mGridList.size();i++){
            titles[i] = mGridList.get(i).getName();
        }
        return titles;
    }

    @Override
    public void onClick(View v) {
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mCurIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    //首页Fragment的FragmentPagerAdapter
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {//FragmentStatePagerAdapter{
        FragmentManager fm;
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public JobsItemFragment getItem(int position) {
            return (JobsItemFragment) mFragmentLists.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentLists.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getTitles()[position];
        }
    }
}

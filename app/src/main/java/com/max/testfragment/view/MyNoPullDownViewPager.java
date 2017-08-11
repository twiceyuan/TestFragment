package com.max.testfragment.view;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.max.testfragment.pulltorefresh.LoadMoreListView;

/**
 * 防止左右滑动时，触发下拉刷新
 * Created by Max on 2017-8-8.
 */

public class MyNoPullDownViewPager extends ViewPager {

    public MyNoPullDownViewPager(Context context) {
        super(context);
    }
    public MyNoPullDownViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    float mDownX, mDownY;

    private LoadMoreListView mLoadMoreListView;
    public void setPullableView(LoadMoreListView view){
        mLoadMoreListView = view;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录下按下时当前的xy左标
                mDownX = ev.getX();
                mDownY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                //滑动时计算x轴y轴的移动距离 ，如果x轴移动距离大于y轴，说明是水平滑动，禁止下拉刷新
                if (Math.abs(ev.getX() - mDownX) > 0 || Math.abs(ev.getX() - mDownX) > Math.abs(ev.getY() - mDownY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    if(mLoadMoreListView != null){
                        mLoadMoreListView.setCanPullDown(false);
                    }
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    if(mLoadMoreListView != null){
                        mLoadMoreListView.setCanPullDown(true);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                if(mLoadMoreListView != null){
                    mLoadMoreListView.setCanPullDown(true);
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}

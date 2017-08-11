package com.max.testfragment.fragment;

import android.support.v4.app.Fragment;

/**
 * 懒加载
 * Created by Max.
 */
public abstract class LazyBaseFragment extends Fragment {

    /** Fragment当前状态是否可见 */
    protected boolean mIsVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {
        onLazyInvisible();
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();
    protected abstract void onLazyInvisible();
}

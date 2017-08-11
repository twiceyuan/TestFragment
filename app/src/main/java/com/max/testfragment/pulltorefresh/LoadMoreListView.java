package com.max.testfragment.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.max.testfragment.R;


/**
 * 默认带 加载更多 footer
 * 数据少不需要加载按钮的时候，hideFooter()
 * 加载数据操作在 onLoadMore() 中实现，并调用loadMoreFinish（）方法结束（一定要有）。
 * loadMoreFinish()会自动显示隐藏的footer.
 *----------------------------
 *
 * 二次修改，改为滑动到底部自动更新
 * （init:）
 * mLv.setOnLoadMoreListener(this);//实现LoadMoreListView.OnLoadMoreListener接口
 * boolean mIsLoadingMore = false;
 * （重写：）
     @Override
     public void onLoadMore() {
         if(!mIsLoadingMore){
             mIsLoadingMore = true;
             new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     mStartIndex++;
                     getData(true, false);
                }
         }, 200);
         }
     }
 （getData()中，每个数据结果回调要增加：）
     mIsLoadingMore = false;//保证可以再次滑动加载
 */
public class LoadMoreListView extends ListView implements Pullable, AbsListView.OnScrollListener {

    private Context mContext;
    private LinearLayout mFooterView;
    private TextView mFooterText;
    private ProgressBar mFooterProgress;

    public LoadMoreListView(Context context) {
        super(context);
        initView();
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        mContext = getContext();
        setOnScrollListener(this);
        resetFooter();
//        initFootView(mContext);
    }

    private void resetFooter() {
        int count = getFooterViewsCount();
        if (count > 0) {
            return;
        }
        if (mFooterView == null) {
            mFooterView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.footer_item, null);
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    showFooterProgress();
                }
            });
            mFooterText = (TextView) mFooterView.findViewById(R.id.footer_text);
            mFooterProgress = (ProgressBar) mFooterView.findViewById(R.id.footer_progress);
        }
        addFooterView(mFooterView);
        mFooterText.setText("加载中");
        mFooterProgress.setVisibility(View.VISIBLE);
    }

    public void hideFooter(){
        int count = getFooterViewsCount();
        if (count > 0 && mFooterView != null) {
            removeFooterView(mFooterView);
        }
//        resetHint();
    }

    public void loadMoreFinish(boolean isFooterVisible) {
//        onLoadComplete();
        isLoading = false;
        if (isFooterVisible) {
            resetFooter();
//            resetHint();
//            smoothScrollToPosition(getCount());
        } else {
            hideFooter();
        }
    }


    private void showFooterProgress() {
        mFooterText.setText("加载中");
        mFooterProgress.setVisibility(View.VISIBLE);
    }

    private void resetHint() {
        mFooterText.setText("加载更多");
        mFooterProgress.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean canPullDown() {
        if(isCanPullDown) {
            if (getCount() == 0) {
                // 没有item的时候也可以下拉刷新
                return true;
            } else if (getFirstVisiblePosition() == 0
                    && getChildAt(0).getTop() >= 0) {
                // 滑到ListView的顶部了
                return true;
            } else
                return false;
        }else{
            return false;
        }
    }

    @Override
    public boolean canPullUp() {
        return false;
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    //解决嵌套ViewPager的时候，左右滑动和下拉刷新冲突的问题
    private boolean isCanPullDown = true;//是否允许下拉滑动
    public void setCanPullDown(boolean canPullDown) {
        isCanPullDown = canPullDown;
    }

    //============================= 上拉刷新 =========================================

    private boolean isLoading = false;// 判断是否正在加载
    private boolean loadEnable = true;// 开启或者关闭加载更多功能

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        MyLog.d("xmg", "on onScrollStateChanged  #####  scrollState="+scrollState);
        //上拉刷新
        ifNeedLoad(view, scrollState);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(mOnListScrollListener != null){
            mOnListScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    //应该在initView中初始化
    private void initFootView(Context context){
        int count = getFooterViewsCount();
        if (count > 0) {
            return;
        }
        if (mFooterView == null) {
            mFooterView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.footer_item, null);
            mFooterText = (TextView) mFooterView.findViewById(R.id.footer_text);
            mFooterProgress = (ProgressBar) mFooterView.findViewById(R.id.footer_progress);
        }
    }

    // 根据listview滑动的状态判断是否需要加载更多
    private void ifNeedLoad(AbsListView view, int scrollState) {
//        MyLog.d("xmg", "on bottom  #####  ifNeedLoad 1");
        if (!loadEnable) {
            return;
        }
//        MyLog.d("xmg", "on bottom  #####  ifNeedLoad 2  scrollState="+scrollState + "  isLoading="+isLoading);
        try {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && !isLoading) {
//                MyLog.d("xmg", "on bottom  #####  ifNeedLoad 3  LastPosition="+view.getLastVisiblePosition()
//                        +"  view.getCount="+(view.getCount() - 1));
                if (view.getLastVisiblePosition() == view.getCount() - 1) {
                    actionLoad(view);
                }
            }
        } catch (Exception e) {
        }
    }

    public boolean isLoadEnable() {
        return loadEnable;
    }

    // 这里的开启或者关闭加载更多，并不支持动态调整
    public void setLoadEnable(boolean loadEnable) {
        this.loadEnable = loadEnable;
        this.removeFooterView(mFooterView);
    }

    public void actionLoad(AbsListView view){
        isLoading = true;
//        showFooter(view);
        resetFooter();
        //加载更多功能的代码
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMore();
        }
//        showFooterProgress();
    }

    public void showFooter(AbsListView view){
        this.addFooterView(mFooterView);
        smoothScrollToPosition(view.getCount());
    }

    // 用于加载更多结束后的回调
    public void onLoadComplete() {
        isLoading = false;
        try {
            this.removeFooterView(mFooterView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //如果需要外部listview需要设置OnScrollListener就实现这个接口
    public interface OnListScrollListener {
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }
    private OnListScrollListener mOnListScrollListener;
    public void setOnListScrollListener(OnListScrollListener mOnListScrollListener){
        this.mOnListScrollListener = mOnListScrollListener;
    }

}

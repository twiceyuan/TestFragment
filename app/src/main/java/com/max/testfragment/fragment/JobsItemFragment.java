package com.max.testfragment.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.max.testfragment.R;
import com.max.testfragment.adapter.JobRecommendAdapter;
import com.max.testfragment.model.JobRecommendItem;
import com.max.testfragment.pulltorefresh.LoadMoreListView;
import com.max.testfragment.pulltorefresh.Pull2RefreshLayout;

import java.util.ArrayList;


/**
 * 推荐职位的每个列表
 * Created by Administrator on 2017/3/17 0017.
 */

public class JobsItemFragment extends LazyBaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener,
        Pull2RefreshLayout.OnPullRefreshListener {

    private Activity mContext;
    private LoadMoreListView mLv;
    private final ArrayList<JobRecommendItem> mList = new ArrayList<>();
    private JobRecommendAdapter mAdapter;
    private Pull2RefreshLayout mPull2Refresh;
    private boolean isRefreshing;
    public int mStartIndex = 0;
    public final int PAGE_SIZE = 10;

    private String mUserInfoCompleteTips;
    private ImageView mEmptyContent;
    private static JobsItemFragment fragment;
//    private RecyclerViewBanner mCycleView;
    private ProgressDialog mProgressDialog;
    private String mTitle = "";
    private int mTitleId = -1;
    boolean mIsLoadingMore = false;
    private View mRootView;

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            if(mProgressDialog != null){
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        }
    };

    public static JobsItemFragment newInstance(String title, int titleId) {
        JobsItemFragment fragment = new JobsItemFragment();
        Bundle b = new Bundle();
        b.putString("title", title);
        b.putInt("titleId", titleId);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        if(getArguments() != null){
            //取出保存的值
            mTitle = getArguments().getString("title");
            mTitleId = getArguments().getInt("titleId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_job_recommend, container, false);
        initView(mRootView);
        return mRootView;
    }

    private Runnable LOAD_DATA = new Runnable() {
        @Override
        public void run() {
            //在这里将数据内容加载到Fragment上
            isRefreshing = true;
            mStartIndex = 0;
            requestData();
        }
    };


    @Override
    protected void onVisible() {
        super.onVisible();
        if(mList.size() == 0){
            mHandler.postDelayed(LOAD_DATA, 100);
        }
    }


//    private MyNoPullDownViewPager mPager;
//    private List<View> mPagerList;
//    private List<OperationItem> mDatas = new ArrayList<>();
//    private LinearLayout mLlDot;
//    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
//            "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务", "美发", "丽人"};
//    /**
//     * 总的页数
//     */
//    private int pageCount;
//    /**
//     * 每一页显示的个数
//     */
//    private int pageSize = 10;
//    /**
//     * 当前显示的是第几页
//     */
//    private int curIndex = 0;

    private void initView(View rootView) {

//        header = LayoutInflater.from(mContext).inflate(R.layout.job_item_header_item, null);
        mLv = (LoadMoreListView) rootView.findViewById(R.id.list_view);
        mAdapter = new JobRecommendAdapter(mContext, mList);
        mLv.setAdapter(mAdapter);
        mLv.setFocusable(false);//解决刷新后，自动跳到列表头位置，不显示头部以上的内容问题。
//        mLv.setOnLoadMoreListener(this);
//        mLv.setOnItemClickListener(this);
        mPull2Refresh = (Pull2RefreshLayout) rootView.findViewById(R.id.pull2Refresh);
        mPull2Refresh.setOnRefreshListener(this);
//        mCycleView = (RecyclerViewBanner) header.findViewById(R.id.good_job_fragment_imageCycleview);
//        mCycleView.setPullableView(mLv);

        //初始化运营位
//        mRecommendLayout = (LinearLayout) header.findViewById(R.id.ll_job_fragment_vp_layout);
//        mPager = (MyNoPullDownViewPager) header.findViewById(R.id.vp_job_fragment_type_viewpager);
//        mLlDot = (LinearLayout) header.findViewById(R.id.ll_job_fragment_type_dot);
//        mPager.setPullableView(mLv);

        mEmptyContent = (ImageView) rootView.findViewById(R.id.empty_layout_content);

        //加滚动监听，解决左右滑动时，会与下拉刷新冲突
//        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                mLv.requestDisallowInterceptTouchEvent(true);
//                if(positionOffsetPixels > 0){
//                    mLv.setCanPullDown(false);
//                }else if(positionOffsetPixels == 0){
//                    mLv.setCanPullDown(true);
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                mLv.requestDisallowInterceptTouchEvent(false);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                if(state == ViewPager.SCROLL_STATE_IDLE){
//                    mLv.setCanPullDown(true);
//                }
//            }
//        });
    }

    private void showEmptyView(){
        //如果没有内容，就添加一个空View类型的item
        mList.clear();
        JobRecommendItem job = new JobRecommendItem();
        job.setEmptyType(true);
        mList.add(job);
        mAdapter.notifyDataSetChanged();
    }
    private void hintEmptyView(){
        if(mList.size() == 1 && mList.get(0).isEmptyType()){
            return;
        }
        ArrayList<JobRecommendItem> tempList = new ArrayList<>();
        for(int i=0;i<mList.size();i++){
            if(!mList.get(i).isEmptyType()){
                tempList.add(mList.get(i));
            }
        }
        mList.clear();
        mList.addAll(tempList);
        mAdapter.notifyDataSetChanged();
    }

    public void refreshData(boolean mustRefresh) {
        if(mList.size() > 0 && !mustRefresh){
            return;
        }
        try {
            requestData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestData() {
        //假设获取数据已经完成……
        ArrayList<JobRecommendItem> items = new ArrayList<>();
        for(int i=0;i<10;i++){
            items.add(new JobRecommendItem("title"+i));
        }
        if (isRefreshing) {
            mList.clear();
            isRefreshing = false;
            mPull2Refresh.refreshFinish(Pull2RefreshLayout.SUCCEED);
        }
        mList.addAll(items);
        mAdapter.notifyDataSetChanged();
        if(mList.size() == 0){
            mLv.loadMoreFinish(false);
            showEmptyView();
        }else{
            hintEmptyView();
        }
    }

    @Override
    public void onRefreshData() {
        isRefreshing = true;
        mStartIndex = 0;
        requestData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JobRecommendItem item = (JobRecommendItem) parent.getItemAtPosition(position);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void lazyLoad() {
        if(!mIsVisible) {
            return;
        }
    }
    @Override
    protected void onLazyInvisible() {
        if(!mIsVisible) {
            if(mPull2Refresh != null){
                mPull2Refresh.refreshFinish(Pull2RefreshLayout.SUCCEED);
            }
        }
    }

}

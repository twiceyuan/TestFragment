package com.max.testfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.max.testfragment.fragment.JobsFragment;
import com.max.testfragment.fragment.JobsFragment2;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mTab1, mTab2;
    private Fragment mLastFragment = null;
    private FragmentManager mFragmentManager = null;
    private FragmentTransaction mFragmentTransaction = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
    }

    private void initView() {
        mTab1 = (ImageView) findViewById(R.id.iv_fragment1);
        mTab2 = (ImageView) findViewById(R.id.iv_fragment2);

        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
    }

    private void initFragment(){
        if (mFragmentManager == null)
            mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        JobsFragment jobsFragment =  (JobsFragment) mFragmentManager.findFragmentByTag("JobsFragment");
        if(jobsFragment == null){
            jobsFragment = JobsFragment.getInstance();
            mFragmentTransaction.add(R.id.content_container, jobsFragment, "JobsFragment");
        }
        JobsFragment2 jobsFragment2 =  (JobsFragment2) mFragmentManager.findFragmentByTag("JobsFragment2");
        if(jobsFragment2 == null){
            jobsFragment2 = JobsFragment2.getInstance();
            mFragmentTransaction.add(R.id.content_container, jobsFragment2, "JobsFragment2");
        }

        mFragmentTransaction.attach(jobsFragment);
        mFragmentTransaction.attach(jobsFragment2);
        mFragmentTransaction.commit();
        mFragmentTransaction.hide(jobsFragment2);
        mLastFragment = jobsFragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_fragment1:
                changeJobsFragment();
                changeTabBg(0);
                break;
            case R.id.iv_fragment2:
                changeJobsFragment2();
                changeTabBg(1);
                break;
        }
    }

    private void changeTabBg(int index){
        switch (index){
            case 0:
                mTab1.setImageResource(R.drawable.home_page);
                mTab2.setImageResource(R.drawable.home_page_gray);
                break;
            case 1:
                mTab1.setImageResource(R.drawable.home_page_gray);
                mTab2.setImageResource(R.drawable.home_page);
                break;

        }
    }

    private void changeJobsFragment() {
        if (mFragmentManager == null)
            mFragmentManager = getSupportFragmentManager();
        JobsFragment jobsFragment =  (JobsFragment) mFragmentManager.findFragmentByTag("JobsFragment");
        mFragmentTransaction = mFragmentManager.beginTransaction();
        if(jobsFragment == null){
            jobsFragment = JobsFragment.getInstance();
            mFragmentTransaction.add(R.id.content_container, jobsFragment, "JobsFragment");
        }
        if(mLastFragment == null){
            mLastFragment = jobsFragment;
        }
        mFragmentTransaction.attach(jobsFragment);
        mFragmentTransaction.commit();
        mFragmentTransaction.hide(mLastFragment);
        mFragmentTransaction.show(jobsFragment);
        mLastFragment = jobsFragment;
    }

    private void changeJobsFragment2() {
        if (mFragmentManager == null)
            mFragmentManager = getSupportFragmentManager();
        JobsFragment2 jobsFragment2 =  (JobsFragment2) mFragmentManager.findFragmentByTag("JobsFragment2");
        mFragmentTransaction = mFragmentManager.beginTransaction();
        if(jobsFragment2 == null){
            jobsFragment2 = JobsFragment2.getInstance();
            mFragmentTransaction.add(R.id.content_container, jobsFragment2, "JobsFragment2");
        }
        if(mLastFragment == null){
            mLastFragment = jobsFragment2;
        }
        mFragmentTransaction.attach(jobsFragment2);
        mFragmentTransaction.commit();
        mFragmentTransaction.hide(mLastFragment);
        mFragmentTransaction.show(jobsFragment2);
        mLastFragment = jobsFragment2;
    }
}

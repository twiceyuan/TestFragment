package com.max.testfragment.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.max.testfragment.R;
import com.max.testfragment.model.JobRecommendItem;

import java.util.List;

/**
 * Created by Daniel on 2017/1/13.
 */
public class JobRecommendAdapter extends TBaseAdapter<JobRecommendItem> {

//    private final ImageLoader mImageLoader;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_EMPTY = 1;

    public JobRecommendAdapter(Context context, List<JobRecommendItem> list) {
        super(context, list);
//        mImageLoader = new ImageLoader(mContext);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return ((JobRecommendItem)getItem(position)).isEmptyType() ? TYPE_EMPTY : TYPE_ITEM;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            int type = getItemViewType(position);
            switch (type) {
                case TYPE_EMPTY:
                    convertView = mLayoutInflater.inflate(R.layout.empty_list_layout, null);
                    vh = new ViewHolder(convertView);
                    vh.mEmptyView = (ImageView) convertView.findViewById(R.id.empty_layout_content);
                    break;
                default:
                    convertView = mLayoutInflater.inflate(R.layout.item_job_recommend, null);
                    vh = new ViewHolder(convertView);
                    break;
            }
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if(getItemViewType(position) != TYPE_EMPTY){
            fillData(vh, mList.get(position));
        }
        return convertView;
    }

    private void fillData(ViewHolder vh, JobRecommendItem item) {
        vh.tvTitle.setText(item.getName());
    }


    public static class ViewHolder {
        public View rootView;
        public ImageView ivAvatar;
        public TextView tvTitle;

        ImageView mEmptyView;//空布局

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ivAvatar = (ImageView) rootView.findViewById(R.id.iv_avatar);
            this.tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        }

    }
}

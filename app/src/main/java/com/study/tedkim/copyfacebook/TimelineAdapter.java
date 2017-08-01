package com.study.tedkim.copyfacebook;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by tedkim on 2017. 7. 3..
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {

    Context mContext;
    ArrayList<Article> mDataset = new ArrayList<>();
    LayoutInflater mInflater;

    // ViewHolder Type
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    public TimelineAdapter(Context context, ArrayList<Article> dataset) {

        mContext = context;
        mDataset = dataset;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class TimelineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // holder 의 viewType
        int mViewType;  // onBindViewHolder 에서 viewType 에 따른 position 정보에 변화를 주기위해 선언

        // body 의 view components
        TextView tvUserName, tvDate, tvContent, tvLike, tvComment, tvShare;
        Button btnLike, btnComment, btnShare;
        ImageView ivBodyProfile, ivPhoto;

        // header 의 view components
        ImageView ivHeaderProfile;
        EditText etContent;
        Button btnLive, btnGallery, btnCheckin;
        RecyclerView rvStory;

        // viewType 에 따른 viewHolder 정의
        public TimelineViewHolder(View itemView, int viewType) {
            super(itemView);

            mViewType = viewType;
            if (TYPE_HEADER == viewType) {
                initHeaderView(itemView);
            } else {
                initBodyView(itemView);
            }
        }

        // head itemView init
        private void initHeaderView(View itemView) {

            ivHeaderProfile = (ImageView) itemView.findViewById(R.id.imageView_profile);

            etContent = (EditText) itemView.findViewById(R.id.editText_content);

            btnLive = (Button) itemView.findViewById(R.id.button_live);
            btnLive.setOnClickListener(this);

            btnGallery = (Button) itemView.findViewById(R.id.button_gallery);
            btnGallery.setOnClickListener(this);

            btnCheckin = (Button) itemView.findViewById(R.id.button_checkin);
            btnCheckin.setOnClickListener(this);

            // Story 기능을 위한 RecyclerView 추가
            rvStory = (RecyclerView) itemView.findViewById(R.id.recyclerView_story);

        }

        // body itemView init
        private void initBodyView(View itemView) {

            tvUserName = (TextView) itemView.findViewById(R.id.textView_userName);
            tvDate = (TextView) itemView.findViewById(R.id.textView_date);
            tvContent = (TextView) itemView.findViewById(R.id.textView_content);
            tvLike = (TextView) itemView.findViewById(R.id.textView_like);
            tvComment = (TextView) itemView.findViewById(R.id.textView_comment);
            tvShare = (TextView) itemView.findViewById(R.id.textView_share);

            btnLike = (Button) itemView.findViewById(R.id.button_like);
            btnLike.setOnClickListener(this);

            btnComment = (Button) itemView.findViewById(R.id.button_comment);
            btnComment.setOnClickListener(this);

            btnShare = (Button) itemView.findViewById(R.id.button_share);
            btnShare.setOnClickListener(this);

            ivBodyProfile = (ImageView) itemView.findViewById(R.id.imageView_profile);
            ivPhoto = (ImageView) itemView.findViewById(R.id.imageView_photo);
        }

        @Override
        public void onClick(View v) {

            String messege = "";

            switch (v.getId()) {

                case R.id.button_live:

                    messege = "live button is clicked!";

                    break;

                case R.id.button_gallery:

                    messege = "gallery button is clicked!";

                    break;

                case R.id.button_checkin:

                    messege = "checkin button is clicked!";


                    break;

                case R.id.button_like:

                    int tabIconColor = ContextCompat.getColor(mContext, R.color.facebookBlue); // activation color
                    btnLike.setTextColor(tabIconColor);

                    messege = "like button is clicked!";

                    break;

                case R.id.button_comment:

                    messege = "comment button is clicked!";

                    break;

                case R.id.button_share:

                    messege = "share button is clicked!";

                    break;
            }

            Toast.makeText(mContext, messege, Toast.LENGTH_SHORT).show();
        }
    }

    // header 여부 체크 메소드
    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {

        if (isPositionHeader(position))
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }

    // header, body 의 view 정의
    @Override
    public TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {

            View headerView = mInflater.inflate(R.layout.timeline_header_item, parent, false);
            return new TimelineViewHolder(headerView, viewType);
        } else {

            View itemView = mInflater.inflate(R.layout.timeline_article_item, parent, false);
            return new TimelineViewHolder(itemView, viewType);
        }
    }

    // header, body 에 data bind
    @Override
    public void onBindViewHolder(TimelineViewHolder holder, int position) {

        // 1. header data bind
        if (holder.mViewType == TYPE_HEADER) {
            bindHeaderItem(holder);
        }

        // 2. body data bind
        else {
            bindBodyItem(holder, position - 1);
        }
    }

    // head itemSet bind
    private void bindHeaderItem(TimelineViewHolder holder) {

        StoryAdapter adapter = new StoryAdapter(mContext, mDataset);
        holder.rvStory.setAdapter(adapter);

        // Layout Manager 를 이용해 Horizontal Option 을 적용
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        holder.rvStory.setLayoutManager(layoutManager);

    }

    // body itemSet bind
    private void bindBodyItem(TimelineViewHolder holder, int position) {

        // text data bind
        holder.tvUserName.setText(mDataset.get(position).getUserName());
        holder.tvDate.setText(mDataset.get(position).getDate());
        holder.tvContent.setText(mDataset.get(position).getContent());
        holder.tvLike.setText(mDataset.get(position).getLike() + "개");
        holder.tvComment.setText(mDataset.get(position).getComment() + "개");
        holder.tvShare.setText(mDataset.get(position).getShare() + "개");

        // image data bind
        Glide.with(mContext).load(mDataset.get(position).getProfile()).into(holder.ivBodyProfile);
        Glide.with(mContext).load(mDataset.get(position).getPhoto()).into(holder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return mDataset.size() + 1; // (body item count) + (header item count)
    }

}
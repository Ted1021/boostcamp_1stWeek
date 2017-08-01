package com.study.tedkim.copyfacebook;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by tedkim on 2017. 7. 7..
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    Context mContext;
    ArrayList<Article> mDataset = new ArrayList<>();
    LayoutInflater mInflater;

    static final int TYPE_HEADER = 0;
    static final int TYPE_BODY = 1;

    public StoryAdapter(Context context, ArrayList<Article> dataset) {

        mContext = context;
        mDataset = dataset;

        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public class StoryViewHolder extends RecyclerView.ViewHolder {

        // story body items
        ImageButton ibProfile;
        TextView tvUserName;

        // story header items
        ImageButton ibDirect, ibMyStory;

        public StoryViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_HEADER)
                initHeaderView();
            else
                initBodyView();

        }

        // friends story icon init
        private void initBodyView() {

            tvUserName = (TextView) itemView.findViewById(R.id.textView_userName);
            ibProfile = (ImageButton) itemView.findViewById(R.id.imageButton_profile);

        }

        // direct icon, myStory icon init
        private void initHeaderView() {

            ibDirect = (ImageButton) itemView.findViewById(R.id.imageButton_direct);

            ibMyStory = (ImageButton) itemView.findViewById(R.id.imageButton_myStory);

        }

    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0)
            return TYPE_HEADER;
        else
            return TYPE_BODY;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        if (viewType == TYPE_HEADER) {
            itemView = mInflater.inflate(R.layout.story_header_item, parent, false);
            return new StoryViewHolder(itemView, viewType);
        } else {
            itemView = mInflater.inflate(R.layout.story_body_item, parent, false);
            return new StoryViewHolder(itemView, viewType);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(StoryViewHolder holder, int position) {

        if (holder.getItemViewType() == TYPE_HEADER)
            bindHeaderItem(holder);
        else
            bindBodyItem(holder, position - 1);
    }

    private void bindHeaderItem(StoryViewHolder holder) {

        Glide.with(mContext).load(R.drawable.plane_icon)
                .into(holder.ibDirect);

        // ImageView 레이아웃을 동그랗게 깎아주는 옵션 - 사진의 모퉁이가 잘려나감
        Glide.with(mContext).load(R.drawable.ted)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.ibMyStory);


    }

    private void bindBodyItem(StoryViewHolder holder, int position) {

        // story 에 표시되는 userName 은 짧아야 하므로 string 후처리가 필요
        String userName = mDataset.get(position).getUserName();

        // userName 이 세글자 이상 넘어가게 되면,
        if (userName.length() > 3) {
            // 잘라내고 '...' 을 붙임
            userName = userName.substring(0, 3) + "...";
        }

        Glide.with(mContext).load(mDataset.get(position).getProfile())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.ibProfile);

        // 비활성화를 시각적으로 나타내기위해 imageView 에 회색 color filter 적용
        int colorFilter = ContextCompat.getColor(mContext, R.color.grayFilter);
        holder.ibProfile.setColorFilter(colorFilter, PorterDuff.Mode.MULTIPLY);
        holder.tvUserName.setText(userName);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

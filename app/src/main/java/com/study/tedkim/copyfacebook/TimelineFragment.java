package com.study.tedkim.copyfacebook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TimelineFragment extends Fragment {

    // recyclerView scroll state
    public static final int SCROLL_UP = 0;
    public static final int SCROLL_DOWN = 1;

    public TimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_timeline, container, false);

        setTimelineList(view);

        return view;
    }

    // set recyclerView
    public void setTimelineList(View view) {

        RecyclerView rvTimeline = (RecyclerView) view.findViewById(R.id.recyclerView_timeline);

        // 1. set adapter
        TimelineAdapter adapter = new TimelineAdapter(getContext(), MainActivity.mDataset);
        rvTimeline.setAdapter(adapter);

        // 2. set layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTimeline.setLayoutManager(layoutManager);

        // 3. set scroll listener
        rvTimeline.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int scrollState = SCROLL_UP;

            // 3.2 감지 된 스크롤 상태에 따른 동작 정의
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // 3.2.1 스크롤의 상태를 Handler 를 이용해 Activity 로 전달
                if (scrollState == SCROLL_DOWN) {
                    MainActivity.mScrollSatate.sendEmptyMessage(SCROLL_DOWN);
                } else {
                    MainActivity.mScrollSatate.sendEmptyMessage(SCROLL_UP);
                }

            }

            // 3.1 스크롤 감지
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 10)
                    scrollState = SCROLL_DOWN;
                else if (dy < -5)
                    scrollState = SCROLL_UP;
            }
        });
    }
}

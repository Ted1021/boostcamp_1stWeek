package com.study.tedkim.copyfacebook;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    ImageButton ibCamera, ibMessenger, ibSocial;

    EditText etSearch;

    public static Handler mScrollSatate;
    public static ArrayList<Article> mDataset = new ArrayList<>();

    static final int FRAGMENT_COUNT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setViewAction();
        initData();

    }

    // MainActivity View Component init
    public void initView() {

        // toolbar init
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0); // toolbar 시작 위치 조정

        // tab layout init
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // viewPager init
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));

        // toolbar items init
        etSearch = (EditText) findViewById(R.id.editText_search);

        ibCamera = (ImageButton) findViewById(R.id.imageButton_camera);
        ibCamera.setOnClickListener(this);

        ibMessenger = (ImageButton) findViewById(R.id.imageButton_messenger);
        ibMessenger.setOnClickListener(this);

        ibSocial = (ImageButton) findViewById(R.id.imageButton_social);
        ibSocial.setOnClickListener(this);

    }

    public void setViewAction() {

        // tab item 의 상태에 따른 Button color 세팅
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.facebookBlue); // activation color
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.lightGray);    // de-activation color
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // tabLayout 과 viewPager 상호작용을 위한 리스너 선언
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        // TODO - EditText 가 Touch 되었을 때, 친구 검색을 위한 list 호출
        // call SearchFragment
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


            }
        });

    }

    @Override
    public void onClick(View v) {

        String messege = "";

        switch (v.getId()) {

            case R.id.imageButton_camera:
                messege = "camera button is clicked!";
                break;

            case R.id.imageButton_messenger:
                messege = "messenger button is clicked!";
                break;

            case R.id.imageButton_social:
                messege = "social button is clicked!";
                break;

            case R.id.editText_search:
                messege = "editText is clicked!";
                break;

        }
        Toast.makeText(MainActivity.this, messege, Toast.LENGTH_SHORT).show();
    }

    // article itemSet init
    public void initData() {

        // dumpItems 적용
        DumpItems dumpItems = new DumpItems();

        for (int i = 0; i < dumpItems.userName.length; i++) {

            Article item = new Article();

            item.setUserName(dumpItems.userName[i]);
            item.setDate(dumpItems.date[i]);
            item.setContent(dumpItems.content[i]);
            item.setComment(dumpItems.comment[i]);
            item.setLike(dumpItems.like[i]);
            item.setShare(dumpItems.share[i]);

            item.setProfile(dumpItems.profileId[i]);
            item.setPhoto(dumpItems.photoId[i]);

            mDataset.add(item);
        }
    }

    // ViewPager Adapter Class
    // ViewPager 에 의해 교체 될 Fragment 정의
    public class FragmentAdapter extends FragmentStatePagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            // TODO - Fragment 선언 위치 바꿀 것
            Fragment fragment = new TimelineFragment();

            switch (position) {

                // Timeline Fragment
                case 0:
                    fragment = new TimelineFragment();

                    mScrollSatate = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);

                            if (msg.what == TimelineFragment.SCROLL_DOWN) {
                                getSupportActionBar().hide();
                            } else if (msg.what == TimelineFragment.SCROLL_UP) {
                                getSupportActionBar().show();
                            }
                        }
                    };
                    break;

                // RequestFriend Fragment
                case 1:

                    fragment = new RequestFriendFragment();

                    break;

                // History Fragment
                case 2:

                    fragment = new HistoryFragment();

                    break;

                // Menu Fragment
                case 3:

                    fragment = new MenuFragment();

                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }
    }

}

package com.zele.maipuxiaoyuan.ui.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.MessageBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.mvp.model.secondfrag.EMMessageModel;
import com.zele.maipuxiaoyuan.mvp.presenter.login.LoginPresenter;
import com.zele.maipuxiaoyuan.mvp.view.login.LoginView;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;
import com.zele.maipuxiaoyuan.ui.fragment.MainFifthFragment;
import com.zele.maipuxiaoyuan.ui.fragment.MainFirstFragment;
import com.zele.maipuxiaoyuan.ui.fragment.MainFourthFragment;
import com.zele.maipuxiaoyuan.ui.fragment.MainSecondFragment;
import com.zele.maipuxiaoyuan.ui.fragment.MainThirdFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity{

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private Fragment mFirstFragment;
    private Fragment mSecondFragment;
    private Fragment mThirdFragment;
    private Fragment mFourthFragment;
    private Fragment mFifthFragment;

    private String[] mTabs = {
            "首页", "消息", "", "班级圈", "个人中心",
    };
    private List<Fragment> mList = new ArrayList<>();

    private EMMessageModel mEMModel;
    private static TextView mUnreadLabel;
    private MessageBean mKaoqinBean;
    int mSysnoc;
    int mClanoc;
    int mJobnoc;
    int mSchnoc;
    private static String mCustom = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarColor(true);
        initView();
        initFragment();
        initListener();
        initUnredMsgData();
    }

    @Override
    public LoginPresenter createPresenter() {
        return null;
    }

    @Override
    public LoginView createView() {
        return null;
    }

    public void initView() {
        mEMModel = new EMMessageModel();
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);

        mFirstFragment = new MainFirstFragment();
        mList.add(mFirstFragment);
        mSecondFragment = new MainSecondFragment();
        mList.add(mSecondFragment);
        mThirdFragment = new MainThirdFragment();
        mList.add(mThirdFragment);
        mFourthFragment = new MainFourthFragment();
        mList.add(mFourthFragment);
        mFifthFragment = new MainFifthFragment();
        mList.add(mFifthFragment);
    }

    public void initListener() {
        //设置监听事件
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setSelectedTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setUnselectedTab(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void initFragment() {
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        //设置tablayout自定义布局
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            View view = null;
            if (i != 2) {
                view = LayoutInflater.from(this).inflate(R.layout.main_tab_item_view, null);
                ImageView img = view.findViewById(R.id.tab_iconImg);
                TextView tv = view.findViewById(R.id.tab_titleTv);
                tv.setText(mTabs[i]);
                if (i == 0)
                    tv.setTextColor(Color.parseColor("#4bc174"));
                setUnSelectedTabImg(img, i);
                mUnreadLabel = findViewById(R.id.tab_msgNumber);
            } else {
                view = LayoutInflater.from(this).inflate(R.layout.main_tab_item_center_view, null);
            }
            tab.setCustomView(view);
        }
    }

    /**
     * 选中
     *
     * @param tab
     */
    private void setSelectedTab(TabLayout.Tab tab) {
        View customView = tab.getCustomView();
        ImageView img = customView.findViewById(R.id.tab_iconImg);
        if (tab.getPosition() != 2) {
            TextView tv = customView.findViewById(R.id.tab_titleTv);
            tv.setTextColor(Color.parseColor("#4bc174"));
        }

        if (tab.getPosition() == 0) {
            img.setImageResource(R.mipmap.ic_homehover_icon);
        } else if (tab.getPosition() == 1) {
            img.setImageResource(R.mipmap.ic_messagehover_icon);
        } else if (tab.getPosition() == 2) {
            img.setImageResource(R.mipmap.growup_icon);
        } else if (tab.getPosition() == 3) {
            img.setImageResource(R.mipmap.ic_circlehover_icon);
        } else if (tab.getPosition() == 4) {
            img.setImageResource(R.mipmap.ic_minehover_icon);
        }
    }

    /**
     * 未选中
     *
     * @param tab
     */
    private void setUnselectedTab(TabLayout.Tab tab) {
        View customView = tab.getCustomView();
        ImageView img = customView.findViewById(R.id.tab_iconImg);
        if (tab.getPosition() != 2) {
            TextView tv = customView.findViewById(R.id.tab_titleTv);
            tv.setTextColor(Color.parseColor("#a9a9a9"));
        }

        if (tab.getPosition() == 0) {
            img.setImageResource(R.mipmap.ic_home_icon);
        } else if (tab.getPosition() == 1) {
            img.setImageResource(R.mipmap.ic_message_icon);
        } else if (tab.getPosition() == 2) {
            img.setImageResource(R.mipmap.growup_icon);
        } else if (tab.getPosition() == 3) {
            img.setImageResource(R.mipmap.ic_circle_icon);
        } else if (tab.getPosition() == 4) {
            img.setImageResource(R.mipmap.ic_mine_icon);
        }
    }

    /**
     * 初始化状态下tab标签
     *
     * @param img
     * @param position
     */
    private void setUnSelectedTabImg(ImageView img, int position) {
        switch (position) {
            case 0:
                img.setImageResource(R.mipmap.ic_homehover_icon);
                break;
            case 1:
                img.setImageResource(R.mipmap.ic_message_icon);
                break;
            case 2:
                img.setImageResource(R.mipmap.growup_icon);
                break;
            case 3:
                img.setImageResource(R.mipmap.ic_circle_icon);
                break;
            case 4:
                img.setImageResource(R.mipmap.ic_mine_icon);
                break;
        }
    }

    private void initUnredMsgData() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("uid", SharedPreferenceCache.getInstance().getPres("UserId"));
        mEMModel.getMsgList(parameter, new PresenterCallBack<MessageBean>() {

            @Override
            public void onSuccess(MessageBean result) {
                try {
                    mKaoqinBean=result;
                    mSysnoc = mKaoqinBean.getParmsg().getSysNoc();
                    mClanoc = mKaoqinBean.getParmsg().getClaNoc();
                    mJobnoc = mKaoqinBean.getParmsg().getJobNoc();
                    mSchnoc = mKaoqinBean.getParmsg().getSchNoc();
                    mCustom = mKaoqinBean.getCustom();
                    //更新环信的消息数量
                    updateUnreadLabel(mSysnoc, mClanoc, mJobnoc, mSchnoc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String errMsg) {

            }
        });
    }


    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mTabs.length;
        }
    }

    public void setStatusBarColor(boolean isMainPage){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            // 标记可改变 SYSTEM_BAR 颜色，加了这一行才可以修改 状态栏颜色
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            if (isMainPage){
                //顶部状态栏背景由下面的布局填充
                window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }else {
                //顶部状态栏自定义
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.primaryBg));
            }
        }
    }

    /**
     * update unread message count
     * 更新本地数据信息
     *
     * @param schnoc
     * @param jobnoc
     * @param clanoc
     * @param sysnoc
     */
    public static void updateUnreadLabel(int sysnoc, int clanoc, int jobnoc, int schnoc) {
        boolean isonsese = true;
        if (isonsese) {
            int count = getUnreadMsgCountTotal() + sysnoc + clanoc + jobnoc + schnoc;
            if (count > 0) {
                mUnreadLabel.setText(String.valueOf(count));
                mUnreadLabel.setVisibility(View.VISIBLE);
            } else {
                mUnreadLabel.setVisibility(View.INVISIBLE);
            }
            isonsese = false;
        } else {
            isonsese = false;
        }
    }


    public static int getUnreadMsgCountTotal() {
        BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {});
        String sid= studentsBean.getSid();
        int unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        int num = 0;
        for (EMConversation conversation : EMClient.getInstance().chatManager().getAllConversations().values()) {
            if (EMConversation.EMConversationType.Chat == conversation.getType()
                    && (conversation.conversationId().contains("_" + sid)
                    || mCustom.contains(conversation.conversationId()))) {
                num += conversation.getUnreadMsgCount();
            }
            if(conversation.getType() == EMConversation.EMConversationType.GroupChat){
                num += conversation.getUnreadMsgCount();
            }
        }
        return num;
    }
}

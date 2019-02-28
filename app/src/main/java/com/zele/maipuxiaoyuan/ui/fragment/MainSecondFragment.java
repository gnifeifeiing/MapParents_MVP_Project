package com.zele.maipuxiaoyuan.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.umeng.analytics.MobclickAgent;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.MessageBean;
import com.zele.maipuxiaoyuan.chat.MyEaseConversationListFragment;
import com.zele.maipuxiaoyuan.common.utils.MyActivityManager;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.mvp.model.secondfrag.EMMessageModel;
import com.zele.maipuxiaoyuan.retrofit.PresenterCallBack;
import com.zele.maipuxiaoyuan.ui.homefrag.BooksActivity;
import com.zele.maipuxiaoyuan.ui.personalfrag.AboutOurActivity;

import java.util.HashMap;
import java.util.Map;


/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/1/4 16:54
 */

public class MainSecondFragment extends MyEaseConversationListFragment {

    public View mView;
    public Context mContext;
    private EMMessageModel mEMModel;

    private String mSID = "";

    private int mSchnoc;
    private int mJobnoc;
    private int mClanoc;
    private int mSysnoc;
    private String mCustom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mEMModel = new EMMessageModel();
        BindStudentsBean.StudentsBean studentBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {
        });
        mSID = studentBean.getSid();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        View errorView = View.inflate(getContext(), R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getMsgList();
        }
    }


    @Override
    protected void setUpView() {
        super.setUpView();
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 4) {
                    switch (position) {
                        // 系统通知
                        case 0:
                            Intent inttent = new Intent(getActivity(), AboutOurActivity.class);
                            startActivity(inttent);
                            break;
                        // 校园通知
                        case 1:
                            Intent inttent1 = new Intent(getActivity(), AboutOurActivity.class);
                            startActivity(inttent1);
                            break;
                        // 班级通知
                        case 2:
                            Intent inttent2 = new Intent(getActivity(), AboutOurActivity.class);
                            startActivity(inttent2);
                            break;
                        // 作业通知
                        case 3:
                            Intent inttent3 = new Intent(getActivity(), BooksActivity.class);
                            inttent3.putExtra("opop", "0");
                            startActivity(inttent3);
                            break;
                    }
                } else {
                    EMConversation conversation = conversationListView
                            .getItem(position - 4);
                    String username = conversation.conversationId();
                    if (username.equals(EMClient.getInstance().getCurrentUser()))
                        Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                    else {
                        // start chat acitivity
//                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        Intent intent = new Intent(getActivity(), AboutOurActivity.class);
                        if (conversation.isGroup()) {
                            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom) {
                                // it's group chat
                                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,
                                        EaseConstant.CHATTYPE_CHATROOM);
                            } else {
                                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,
                                        EaseConstant.CHATTYPE_GROUP);
                            }
                        }
                        // it's single chat
                        intent.putExtra(EaseConstant.EXTRA_USER_ID, username);
                        startActivity(intent);
                    }
                }
            }
        });

        conversationListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int position = ((ListView) v).pointToPosition(
                        (int) event.getX(), (int) event.getY());
                if (position < 4) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            return true;
                        default:
                            break;
                    }
                    return false;
                } else {
                    return false;
                }
            }
        });
    }

    private void getMsgList() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("uid", SharedPreferenceCache.getInstance().getPres("UserId"));
        mEMModel.getMsgList(parameter, new PresenterCallBack<MessageBean>() {

            @Override
            public void onSuccess(MessageBean result) {
                if (!MyActivityManager.isAlive(MainSecondFragment.this)) return;
                parseMsgList(result);
            }

            @Override
            public void onFail(String errMsg) {

            }
        });
    }

    private void parseMsgList(MessageBean schoolKaoqinBean) {
        try {
            String content = JSON.toJSONString(schoolKaoqinBean);
            //缓存信息
            SharedPreferenceCache.getInstance().setPres("msgList", content);
            mSysnoc = schoolKaoqinBean.getParmsg().getSysNoc();
            mClanoc = schoolKaoqinBean.getParmsg().getClaNoc();
            mJobnoc = schoolKaoqinBean.getParmsg().getJobNoc();
            mSchnoc = schoolKaoqinBean.getParmsg().getSchNoc();
            mCustom = schoolKaoqinBean.getCustoms();

            int count = getUnreadMsgCountTotal() + mSysnoc + mClanoc + mJobnoc + mSchnoc;
            TextView counttext = getActivity().findViewById(R.id.tab_msgNumber);
            if (count != 0) {
                Log.w("unRead", "mmess");
                counttext.setText(String.valueOf(count));
                counttext.setVisibility(View.VISIBLE);
            } else {
                counttext.setVisibility(View.INVISIBLE);
            }
            //更新未读消息数量？
            refresh("0");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get unread message count
     * 获取未读消息的总数，只包括环信的数据
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        Map<String, EMConversation> a = EMClient.getInstance().chatManager().getAllConversations();
        int num = 0;
        if (a != null && a.size() > 0) {
            for (EMConversation conversation : a.values()) {
                if (conversation != null
                        && conversation.conversationId() != null
                        && EMConversation.EMConversationType.Chat == conversation.getType()
                        && conversation.conversationId() != null
                        && !"".equals(conversation.conversationId())
                        && (conversation.conversationId().contains("_" + mSID)
                        || (mCustom != null && mCustom.contains(conversation.conversationId()))))
                {
                                num += conversation.getUnreadMsgCount();
                }
                if (conversation != null && conversation.getType() == EMConversation.EMConversationType.GroupChat) {
                    num += conversation.getUnreadMsgCount();
                }
            }
        }
        return num;
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        getMsgList();
        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面，"MainScreen"为页面名称，可自定义
        registerForContextMenu(conversationListView);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }
}

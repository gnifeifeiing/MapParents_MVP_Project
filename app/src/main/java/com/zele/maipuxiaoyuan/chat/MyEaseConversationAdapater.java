package com.zele.maipuxiaoyuan.chat;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.zele.maipuxiaoyuan.MyApplication;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.ClassParents;
import com.zele.maipuxiaoyuan.bean.ClassTeacher;
import com.zele.maipuxiaoyuan.bean.MessageBean;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.utils.DateUtils;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.views.SwipeItemLayout;
import com.zele.maipuxiaoyuan.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/12/17 15:30
 */

public class MyEaseConversationAdapater extends ArrayAdapter<EMConversation> {
    private static final String TAG = "ChatAllHistoryAdapter";
    private List<EMConversation> conversationList;
    private List<EMConversation> copyConversationList;
    private ConversationFilter conversationFilter;
    private boolean notiyfyByFilter;

    protected int primaryColor;
    protected int secondaryColor;
    protected int timeColor;
    protected int primarySize;
    protected int secondarySize;
    protected float timeSize;
    private int id;

    private ViewHolder holder;
    private int sysNoc;
    private String sysmessage;
    private String sysdate;

    private int clanoc;
    private String cladate;
    private String clamsg;

    private int jobnoc;
    private String jobdate;
    private String jobmsg;

    private int schnoc;
    private String schdate;
    private String schmsg;

    private String mClassId;
    private Context mContext;
    private String kefu="";
    private MessageBean schoolKaoqinBean;
    private String arrnumber;

    public MyEaseConversationAdapater(Context context, int resource,
                                   List<EMConversation> objects) {
        super(context, resource, objects);
        this.mContext=context;
        BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {});
        mClassId= studentsBean.getClassId();

        MyEaseUserUtils.reloadContacts();
        conversationList = objects;
        copyConversationList = new ArrayList<EMConversation>();
        copyConversationList.addAll(objects);
    }

    @Override
    public int getCount() {
        return conversationList.size() + 4;
    }

    public void update(int id, String id0) {
        this.id = id;
        Log.d("refresh", "update" + id0);
        notifyDataSetChanged();
    }

    @Override
    public EMConversation getItem(int arg0) {
        if (arg0 < conversationList.size()) {
            return conversationList.get(arg0);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view02 = null;
        if (convertView == null) {
            View contentView = LayoutInflater.from(getContext()).inflate(R.layout.ease_row_my_chat_history, parent, false);
            view02 = LayoutInflater.from(getContext()).inflate(R.layout.test2, null);
            convertView = new SwipeItemLayout(contentView, view02, null, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.mdr = (ImageView) convertView.findViewById(R.id.mdr);
            holder.unreadLabel = (TextView) convertView.findViewById(R.id.unread_msg_number);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.msgState = convertView.findViewById(R.id.msg_state);
            holder.list_itease_layout = (RelativeLayout) convertView.findViewById(R.id.list_itease_layout);
            holder.motioned = (TextView) convertView.findViewById(R.id.mentioned);
            holder.ll_ = (LinearLayout) view02.findViewById(R.id.ll_);
            holder.ll_delete = (LinearLayout) view02.findViewById(R.id.ll_delete);
            holder.delete = (TextView) view02.findViewById(R.id.delete);
            holder.tv_ = (TextView) view02.findViewById(R.id.tv_);
            holder.list__layout = (LinearLayout) convertView.findViewById(R.id.list__layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //holder.list_itease_layout.setBackgroundResource(R.drawable.ease_mm_listitem);
        holder.position = position;
        // holder.ll_.setOnClickListener(new View.OnClickListener);
        // holder.ll_delete.setOnClickListener(this);
        holder.ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 删除和某个user会话，如果需要保留聊天记录，传false
                EMClient.getInstance().chatManager().deleteConversation(conversationList.get(position - 4).conversationId(), false);
                conversationList.remove(position - 4);
                notifyDataSetChanged();
            }
        });
        holder.ll_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(conversationList.get(position - 4).conversationId());
                conversation.conversationId();
                //String aeerString = conversation.getUnreadMsgCount() + "";
                // 指定会话消息未读数清零
                conversation.markAllMessagesAsRead();
                // //把一条消息置为已读
                conversation.markMessageAsRead(conversationList.get(position - 4).getType() + "");
                // 所有未读消息数清零?
                // sysNoc+"|"+clanoc+"|"+jobnoc+"|"+schnoc
                MainActivity.updateUnreadLabel(sysNoc, clanoc, jobnoc, schnoc);
                notifyDataSetChanged();
            }
        });

        if (position < 4) {
            switch (position) {
                case 0:
                    // String arrnumber=sysNoc+"|"+clanoc+"|"+jobnoc+"|"+schnoc;
                    holder.name.setText("系统通知");

                    holder.avatar.setImageResource(R.mipmap.ic_system_icon);
                    holder.time.setText(sysdate);

                    holder.message.setText(sysmessage);
                    if (sysNoc != 0) {
                        holder.unreadLabel.setText(sysNoc + "");
                        holder.unreadLabel.setVisibility(View.VISIBLE);
                    } else {
                        holder.unreadLabel.setVisibility(View.INVISIBLE);
                    }
                    break;
                case 1:
                    holder.avatar.setImageResource(R.mipmap.ic_campus_icon);
                    // String arrnumber=sysNoc+"|"+clanoc+"|"+jobnoc+"|"+schnoc;
                    holder.name.setText("校园通知");
                    holder.message.setText(schmsg);
                    holder.time.setText(schdate);
                    if (schnoc != 0) {
                        holder.unreadLabel.setText(schnoc + "");
                        holder.unreadLabel.setVisibility(View.VISIBLE);
                    } else {
                        holder.unreadLabel.setVisibility(View.INVISIBLE);
                    }

                    break;
                case 2:
                    // String arrnumber=sysNoc+"|"+clanoc+"|"+jobnoc+"|"+schnoc;
                    holder.name.setText("班级通知");
                    holder.message.setText(clamsg);
                    holder.time.setText(cladate);
                    holder.avatar.setImageResource(R.mipmap.ic_class_icon);

                    if (clanoc != 0) {
                        holder.unreadLabel.setText(clanoc + "");
                        holder.unreadLabel.setVisibility(View.VISIBLE);
                    } else {
                        holder.unreadLabel.setVisibility(View.INVISIBLE);
                    }


                    break;
                case 3:
                    holder.name.setText("作业通知");
                    holder.message.setText(jobmsg);
                    holder.time.setText(jobdate);
                    holder.avatar.setImageResource(R.mipmap.ic_work_icon);
                    if (jobnoc != 0) {
                        holder.unreadLabel.setText(jobnoc + "");
                        holder.unreadLabel.setVisibility(View.VISIBLE);
                    } else {
                        holder.unreadLabel.setVisibility(View.INVISIBLE);
                    }

                    break;
            }
        } else {
            //其他对话信息,也就是环信的对话
            EMConversation conversation = getItem(position - 4);
            List<EMMessage> list = conversation.getAllMessages();
            for (EMMessage item : list) {
                String msgId = item.getMsgId();
                Log.w("res_msg", msgId + "=" + item.isUnread());
            }
            String username = conversation.conversationId();
            if (conversation.getType() == EMConversation.EMConversationType.GroupChat) {
                String groupId = conversation.conversationId();
                if (EaseAtMessageHelper.get().hasAtMeMsg(groupId)) {
                    holder.motioned.setVisibility(View.VISIBLE);
                } else {
                    holder.motioned.setVisibility(View.GONE);
                }
                EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
            } else if (conversation.getType() == EMConversation.EMConversationType.ChatRoom) {
                EMChatRoom room = EMClient.getInstance().chatroomManager().getChatRoom(username);
                holder.motioned.setVisibility(View.GONE);
            } else {
                EaseUserUtils.setUserNick(username, holder.name);
                holder.motioned.setVisibility(View.GONE);
            }
            int num = 0;
            EMMessage mm = conversation.getLastMessage();
            //if(mm!=null && mm.isUnread()) {
            //遍历获取未读数据
            int unreadMsgCount = conversation.getUnreadMsgCount();
            List<EMMessage> kk = conversation.getAllMessages();
            for (EMMessage em : kk) {
                if (em.isUnread()) num++;

            }
            Log.w("res_unread", "unreadMsgCoun=" + unreadMsgCount);
            //}
            if (num > 0) {
                holder.unreadLabel.setText(String.valueOf(num));
                holder.unreadLabel.setVisibility(View.VISIBLE);
            } else {
                holder.unreadLabel.setVisibility(View.INVISIBLE);
            }
            if (conversation.getLastMessage() != null) {
                EMMessage lastMessage = conversation.getLastMessage();
                String content = null;
                if (cvsListHelper != null) {
                    content = cvsListHelper.onSetItemSecondaryText(lastMessage);
                }
                holder.message.setText(EaseSmileUtils.getSmiledText(getContext(),
                        EaseCommonUtils.getMessageDigest(lastMessage, (this.getContext()))), TextView.BufferType.SPANNABLE);
                if (content != null) {
                    holder.message.setText(content);
                }
//				holder.time.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
                holder.time.setText(DateUtils.getListFormat(new Date(lastMessage.getMsgTime())));
                if (lastMessage.direct() == EMMessage.Direct.SEND && lastMessage.status() == EMMessage.Status.FAIL) {
                    holder.msgState.setVisibility(View.VISIBLE);
                } else {
                    holder.msgState.setVisibility(View.GONE);
                }
            }


            String nickNameStr = username;
            if (conversation.getType().compareTo(EMConversation.EMConversationType.GroupChat) == 0) {
                List<EMGroup> emGroup = EMClient.getInstance().groupManager().getAllGroups();
                for (EMGroup em : emGroup) {
                    if (("group_" + mClassId).equals(em.getGroupName())) {
                        nickNameStr = em.getDescription();
                        break;
                    }
                }
                if (MyApplication.black.containsKey(conversation.conversationId())) {
                    holder.mdr.setVisibility(View.VISIBLE);
                } else {
                    holder.mdr.setVisibility(View.GONE);
                }
                holder.avatar.setImageResource(R.mipmap.class_group);
            } else {
                holder.mdr.setVisibility(View.GONE);
                ClassTeacher t = MyEaseUserUtils.teacherMap.get(username);
                if (t != null) {
                    nickNameStr = t.getFriendName();
                    Glide.with(getContext()).load(Constants.SERVER_URL + t.getAvatar())
                            .error(Glide.with(mContext).load(R.mipmap.head)).into(holder.avatar);
                }
                ClassParents p = MyEaseUserUtils.parentMap.get(username);
                if (p != null) {
                    nickNameStr = p.getStudentName() + "(" + p.getRelaName() + ")";
                    Glide.with(getContext()).load(Constants.SERVER_URL + p.getAvatar()).
                            error(Glide.with(mContext).load(R.mipmap.head)).into(holder.avatar);
                }
            }


            EaseUserUtils.setUserNick(nickNameStr, holder.name);
            // TODO 隐藏客服
            if (username.equals(kefu)) {
                holder.list__layout.setVisibility(View.GONE);
                holder.unreadLabel.setVisibility(View.GONE);
                holder.tv_.setVisibility(View.GONE);

                holder.ll_.setVisibility(View.GONE);
                holder.delete.setVisibility(View.GONE);
                holder.ll_delete.setVisibility(View.GONE);
            } else {
                holder.list__layout.setVisibility(View.VISIBLE);
            }
            if (primarySize != 0)
                holder.name.setTextSize(TypedValue.COMPLEX_UNIT_PX, primarySize);
            if (secondarySize != 0)
                holder.message.setTextSize(TypedValue.COMPLEX_UNIT_PX, secondarySize);
            if (timeSize != 0)
                holder.time.setTextSize(TypedValue.COMPLEX_UNIT_PX, timeSize);
        }

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        parseData();

        super.notifyDataSetChanged();
        if(!notiyfyByFilter){
            copyConversationList.clear();
            copyConversationList.addAll(conversationList);
            notiyfyByFilter = false;
        }
    }


    private void parseData() {

        String arr = SharedPreferenceCache.getInstance().getPres("msgList");
        Log.d("parseData", "--------------" + arr);
        try {
            schoolKaoqinBean = JSON.parseObject(arr, MessageBean.class);
            sysmessage = schoolKaoqinBean.getParmsg().getSysMsg();
            sysdate = schoolKaoqinBean.getParmsg().getSysDate();
            sysNoc = schoolKaoqinBean.getParmsg().getSysNoc();

            cladate = schoolKaoqinBean.getParmsg().getClaDate();
            clamsg = schoolKaoqinBean.getParmsg().getClaMsg();
            clanoc = schoolKaoqinBean.getParmsg().getClaNoc();

            jobdate = schoolKaoqinBean.getParmsg().getJobDate();
            jobmsg = schoolKaoqinBean.getParmsg().getJobMsg();
            jobnoc = schoolKaoqinBean.getParmsg().getJobNoc();

            schdate = schoolKaoqinBean.getParmsg().getSchDate();
            schmsg = schoolKaoqinBean.getParmsg().getSchMsg();
            schnoc = schoolKaoqinBean.getParmsg().getSchNoc();

            arrnumber = sysNoc + "|" + clanoc + "|" + jobnoc + "|" + schnoc
                    + "|" + schoolKaoqinBean.getCustoms();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Filter getFilter() {
        if (conversationFilter == null) {
            conversationFilter = new ConversationFilter(conversationList);
        }
        return conversationFilter;
    }


    public void setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
    }

    public void setSecondaryColor(int secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public void setTimeColor(int timeColor) {
        this.timeColor = timeColor;
    }

    public void setPrimarySize(int primarySize) {
        this.primarySize = primarySize;
    }

    public void setSecondarySize(int secondarySize) {
        this.secondarySize = secondarySize;
    }

    public void setTimeSize(float timeSize) {
        this.timeSize = timeSize;
    }


    private class ConversationFilter extends Filter {
        List<EMConversation> mOriginalValues = null;

        public ConversationFilter(List<EMConversation> mList) {
            mOriginalValues = mList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                mOriginalValues = new ArrayList<EMConversation>();
            }
            if (prefix == null || prefix.length() == 0) {
                results.values = copyConversationList;
                results.count = copyConversationList.size();
            } else {
                if (copyConversationList.size() > mOriginalValues.size()) {
                    mOriginalValues = copyConversationList;
                }
                String prefixString = prefix.toString();
                final int count = mOriginalValues.size();
                final ArrayList<EMConversation> newValues = new ArrayList<EMConversation>();

                for (int i = 0; i < count; i++) {
                    final EMConversation value = mOriginalValues.get(i);
                    String username = value.conversationId();

                    EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
                    if(group != null){
                        username = group.getGroupName();
                    }else{
                        EaseUser user = EaseUserUtils.getUserInfo(username);
                        // TODO: not support Nick anymore
//                        if(user != null && user.getNick() != null)
//                            username = user.getNick();
                    }

                    // First match against the whole ,non-splitted value
                    if (username.startsWith(prefixString)) {
                        newValues.add(value);
                    } else{
                        final String[] words = username.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            conversationList.clear();
            if (results.values != null) {
                conversationList.addAll((List<EMConversation>) results.values);
            }
            if (results.count > 0) {
                notiyfyByFilter = true;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    private EaseConversationList.EaseConversationListHelper cvsListHelper;

    public void setCvsListHelper(EaseConversationList.EaseConversationListHelper cvsListHelper){
        this.cvsListHelper = cvsListHelper;
    }


    private static class ViewHolder {
        /**
         * who you chat with
         */
        TextView name;
        ImageView mdr;
        /**
         * unread message count
         */
        TextView unreadLabel;

        /**
         * content of last message
         */
        TextView message;
        /**
         * time of last message
         */
        TextView time, delete, tv_;
        /**
         * avatar
         */
        ImageView avatar;
        /**
         * status of last message
         */
        View msgState;
        /**
         * layout
         */
        RelativeLayout list_itease_layout;
        TextView motioned;
        LinearLayout ll_, ll_delete, list__layout;
        int position;
    }

}

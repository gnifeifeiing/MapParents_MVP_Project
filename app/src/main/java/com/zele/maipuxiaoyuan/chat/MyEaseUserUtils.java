package com.zele.maipuxiaoyuan.chat;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.ClassParents;
import com.zele.maipuxiaoyuan.bean.ClassTeacher;
import com.zele.maipuxiaoyuan.common.utils.Constants;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/12/17 16:12
 */

public class MyEaseUserUtils {

    static EaseUI.EaseUserProfileProvider userProvider;
    public static Map<String,ClassTeacher> teacherMap=new HashMap<String,ClassTeacher>();
    public static Map<String,ClassParents> parentMap=new HashMap<String,ClassParents>();

    private static String mSID;
    private static String mAvatar;

    public MyEaseUserUtils(){
        BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {});
        mSID= studentsBean.getSid();
        mAvatar=studentsBean.getAvatar();
    }

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }

    public static void reloadContacts(){
        String classTeacher=SharedPreferenceCache.getInstance().getPres("ClassTeacher");
        String classParent=SharedPreferenceCache.getInstance().getPres("ClassParent");
        List<ClassTeacher> list2=null;
        List<ClassParents> list=null;
        if(!TextUtils.isEmpty(classTeacher)){
            list2=JSON.parseArray(classTeacher,ClassTeacher.class);
        }
        if(!TextUtils.isEmpty(classParent)){
            list=JSON.parseArray(classParent,ClassParents.class);
        }

        if(list!=null){
            parentMap.clear();
            for(ClassParents c:list){
                parentMap.put(c.getUserName()+"_"+c.getStudentId()+"_"+mSID, c);
            }
        }

        if(list2!=null){
            teacherMap.clear();
            for(ClassTeacher c:list2){
                teacherMap.put(c.getUserName()+"_"+mSID, c);
            }
        }
    }


    /**
     * get EaseUser according username
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username){
        if(userProvider != null)
            return userProvider.getUser(username);

        return null;
    }

    /**
     * set user avatar
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
        if(parentMap.size()==0){
            reloadContacts();
        }
        EaseUser user = getUserInfo(username);
        boolean pass=false;
        if(!pass){
            if(username.equals(EMClient.getInstance().getCurrentUser())){
                Glide.with(context).load(Constants.SERVER_URL+ mAvatar).error(Glide.with(context).load(R.mipmap.head)).into(imageView);
                pass=true;
            }
        }


        ClassTeacher t = MyEaseUserUtils.teacherMap.get(username);
        if(t!=null){
            Glide.with(context).load(Constants.SERVER_URL+ t.getAvatar()).error(Glide.with(context).load(R.mipmap.head)).into(imageView);
        }
        ClassParents p = MyEaseUserUtils.parentMap.get(username);
        if(p!=null){
            Glide.with(context).load(Constants.SERVER_URL+ t.getAvatar()).error(Glide.with(context).load(R.mipmap.head)).into(imageView);
        }
    }


    public static void setUserAvatar(Context context, String username,String sid, ImageView imageView){
        if(parentMap.size()==0){
            reloadContacts();
        }
        if(username.contains("_")){
            username=username.substring(0,username.indexOf("_"));
        }
        EaseUser user = getUserInfo(username);
        if(TextUtils.isEmpty(sid)){
            ClassTeacher t = MyEaseUserUtils.teacherMap.get(username+"_"+mSID);
            if(t!=null){
                Glide.with(context).load(Constants.SERVER_URL+ t.getAvatar()).error(Glide.with(context).load(R.mipmap.head)).into(imageView);
            }
        }else{
            ClassParents p = MyEaseUserUtils.parentMap.get(username+"_"+sid+"_"+mSID);
            if(p!=null){
                Glide.with(context).load(Constants.SERVER_URL+ p.getAvatar()).error(Glide.with(context).load(R.mipmap.head)).into(imageView);
            }
        }
    }

    /**
     * set user's nickname
     */
    public static void setUserNick(String username,TextView textView){
        if(textView != null){
            EaseUser user = getUserInfo(username);
            if(user != null && user.getNickname() != null){
                textView.setText(user.getNickname());
            }else{
                textView.setText(username);
            }
        }
    }

    /**
     * set user's nickname
     */
    public static void setUserNick(String username,String sid,TextView textView){
        if(username.contains("_")){
            username=username.substring(0,username.indexOf("_"));
        }
        if(TextUtils.isEmpty(sid)){
            ClassTeacher t = MyEaseUserUtils.teacherMap.get(username+"_"+ mSID);
            if(t!=null){
                textView.setText(t.getFriendName()+"("+t.getRemark()+")");
            }else{
                textView.setText(username);
            }
        }else{
            ClassParents p = MyEaseUserUtils.parentMap.get(username+"_"+sid+"_"+mSID);
            if(p!=null){
                textView.setText(p.getStudentName()+"("+p.getRelaName()+")");
            }else{
                textView.setText(username);
            }
        }
    }
}

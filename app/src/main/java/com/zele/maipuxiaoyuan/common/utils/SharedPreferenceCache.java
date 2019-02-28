package com.zele.maipuxiaoyuan.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zele.maipuxiaoyuan.MyApplication;


/**
 * Description:      SharedPreference存储
 * Autour：          LF
 * Date：            2018/1/4 15:37
 */
public class SharedPreferenceCache {

    private static SharedPreferenceCache mInstance;
    private static SharedPreferences settings;

    /**
     * 单例
     * @return SharedPreferencesUser对象
     */
    public static SharedPreferenceCache getInstance() {
        if (mInstance == null) {
            synchronized (SharedPreferenceCache.class) {
                if (mInstance == null) {
                    mInstance = new SharedPreferenceCache();
                    settings = MyApplication.getInstance().getSharedPreferences(SharedPreferenceCache.class.getName(),
                            Context.MODE_PRIVATE);
                }
            }
        }
        return mInstance;
    }

    public void setPres(String key, String value) {
        SharedPreferences.Editor editor = settings.edit();
        // 用户ID
        if (key.equals("UserId")) {
            editor.putString("UserId", value);
        }
        // 是否是第一次进入 0-否 1-是
        if (key.equals("IsFirst")) {
            editor.putString("IsFirst", value);
        }
        // 是否登录 0-否 1-是
        else if (key.equals("IsLogin")) {
            editor.putString("IsLogin", value);
        }
        // 登录手机号
        else if (key.equals("LoginMobile")) {
            editor.putString("LoginMobile", value);
        }
        // 登录密码
        else if (key.equals("CustPassword")) {
            editor.putString("CustPassword", value);
        }
        // 用户姓名
        else if (key.equals("UserName")) {
            editor.putString("UserName", value);
        }
        // 头像路径
        else if (key.equals("HeadImgPath")) {
            editor.putString("HeadImgPath", value);
        }
        // 绑定的微信头像路径
        else if (key.equals("WXProfileImageUrl")) {
            editor.putString("WXProfileImageUrl", value);
        }
        // 绑定的QQ头像路径
        else if (key.equals("QQProfileImageUrl")) {
            editor.putString("QQProfileImageUrl", value);
        }
        // token
        else if (key.equals("AccessToken")) {
            editor.putString("AccessToken", value);
        }
        // 环信密码
        else if (key.equals("UUPwd")) {
            editor.putString("UUPwd", value);
        }
        // 用户实体类信息
        else if (key.equals("UserBean")) {
            editor.putString("UserBean", value);
        }
        // 绑定的所有学生所属的班级名称
        else if (key.equals("AllStudentsClassNames")) {
            editor.putString("AllStudentsClassNames", value);
        }
        // 绑定的所有学生信息
        else if (key.equals("StudentsContent")) {
            editor.putString("StudentsContent", value);
        }
        // 绑定的学生列表信息
        else if (key.equals("StudentsList")) {
            editor.putString("StudentsList", value);
        }
        // 学生信息
        else if (key.equals("Student")) {
            editor.putString("Student", value);
        }
        // 学生学期信息
        else if (key.equals("StudentTerms")) {
            editor.putString("StudentTerms", value);
        }
        // 注册步数
        else if (key.equals("RegisterStep")) {
            editor.putString("RegisterStep", value);
        }
        // 学校是否需要考勤卡
        else if (key.equals("BindState")) {
            editor.putString("BindState", value);
        }
        // 是否第一次进入作业界面 0：否  1：是
        else if (key.equals("isFirstInBooks")) {
            editor.putString("isFirstInBooks", value);
        }
        // 环信消息缓存
        else if (key.equals("msgList")) {
            editor.putString("msgList", value);
        }
        // 教师好友列表
        else if (key.equals("ClassTeacher")) {
            editor.putString("ClassTeacher", value);
        }
        // 家长好友列表
        else if (key.equals("ClassParent")) {
            editor.putString("ClassParent", value);
        }


        /*********************** -- 学生信息 -- ***************************/
        else if (key.equals("RE_StudentName")) {
            editor.putString("RE_StudentName", value);
        }
        else if (key.equals("RE_SchoolName")) {
            editor.putString("RE_SchoolName", value);
        }
        else if (key.equals("RE_ClassId")) {
            editor.putString("RE_ClassId", value);
        }
        else if (key.equals("RE_Id")) {
            editor.putString("RE_Id", value);
        }
        else if (key.equals("RE_MasterPhone")) {
            editor.putString("RE_MasterPhone", value);
        }
        else if (key.equals("RE_Avater")) {
            editor.putString("RE_Avater", value);
        }
        else {
            // Students_uid、
            editor.putString(key, value);
        }
        editor.apply();
    }

    public String getPres(String key) {
        String value = "";
        if (key.equals("UserId")) {
            value = settings.getString("UserId", "");
        } else if (key.equals("IsFirst")) {
            value = settings.getString("IsFirst", "1");
        } else if (key.equals("IsLogin")) {
            value = settings.getString("IsLogin", "0");
        } else if (key.equals("LoginMobile")) {
            value = settings.getString("LoginMobile", "");
        } else if (key.equals("CustPassword")) {
            value = settings.getString("CustPassword", "");
        } else if (key.equals("UserName")) {
            value = settings.getString("UserName", "");
        } else if (key.equals("HeadImgPath")) {
            value = settings.getString("HeadImgPath", "");
        } else if (key.equals("WXProfileImageUrl")) {
            value = settings.getString("WXProfileImageUrl", "");
        } else if (key.equals("QQProfileImageUrl")) {
            value = settings.getString("QQProfileImageUrl", "");
        } else if (key.equals("AccessToken")) {
            value = settings.getString("AccessToken", "");
        } else if (key.equals("UUPwd")) {
            value = settings.getString("UUPwd", "");
        } else if (key.equals("UserBean")) {
            value = settings.getString("UserBean", "");
        }  else if (key.equals("AllStudentsClassNames")) {
            value = settings.getString("AllStudentsClassNames", "");
        } else if (key.equals("StudentsContent")) {
            value = settings.getString("StudentsContent", "");
        }  else if (key.equals("StudentsList")) {
            value = settings.getString("StudentsList", "");
        } else if (key.equals("Student")) {
            value = settings.getString("Student", "");
        }  else if (key.equals("Type")) {
            value = settings.getString("Type", "");
        }  else if (key.equals("StudentTerms")) {
            value = settings.getString("StudentTerms", "");
        } else if (key.equals("RegisterStep")) {
            value = settings.getString("RegisterStep", "1");
        } else if (key.equals("BindState")) {
            value = settings.getString("BindState", "");
        } else if (key.equals("isFirstInBooks")) {
            value = settings.getString("isFirstInBooks", "1");
        } else if (key.equals("msgList")) {
            value = settings.getString("msgList", "");
        } else if (key.equals("ClassTeacher")) {
            value = settings.getString("ClassTeacher", "");
        } else if (key.equals("ClassParent")) {
            value = settings.getString("ClassParent", "");
        }

        /*********************** -- 学生信息 -- ***************************/
        else if (key.equals("RE_StudentName")) {
            value = settings.getString("RE_StudentName", "");
        } else if (key.equals("RE_SchoolName")) {
            value = settings.getString("RE_SchoolName", "");
        } else if (key.equals("RE_ClassId")) {
            value = settings.getString("RE_ClassId", "");
        } else if (key.equals("RE_Id")) {
            value = settings.getString("RE_Id", "");
        } else if (key.equals("RE_MasterPhone")) {
            value = settings.getString("RE_MasterPhone", "");
        } else if (key.equals("RE_Avater")) {
            value = settings.getString("RE_Avater", "");
        } else {
            value = settings.getString(key, "");
        }
        return value;
    }


    /**
     * 是否存在该字段
     *
     * @param result
     * @return
     */
    public boolean existResult(String result) {
        return settings.contains(result);
    }

    /**
     * 移除该字段
     *
     * @param preName
     */
    public void removePre(String preName) {
        settings.edit().remove(preName).apply();
    }

    /**
     * 清空用户信息
     */
    public boolean clearUserInfo() {
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        return editor.commit();
    }
}

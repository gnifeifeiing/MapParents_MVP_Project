package com.zele.maipuxiaoyuan.common.utils;

import android.os.Environment;

/**
 * Description:      常量配置类
 * Autour：          LF
 * Date：            2018/8/6 11:01
 */
public class Constants {

    /******************** 生产环境配置 -- 服务器地址 ******************/
    public static String SERVER_URL = "http://api.mapedu.cn/";
    //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//    public static boolean EM_DEBUG_MODE_SWITCH = false;

    /******************* 测试环境配置 -- 测试地址 **********************/
//    public static String SERVER_URL = "http://192.168.66.246/api/v2/";
    public static boolean EM_DEBUG_MODE_SWITCH = true;

    /*************************** 华丽分割线 *****************************/

    /**
     * 用户已移除
     */
    public static final String ACCOUNT_REMOVED = "account_removed";
    /**
     * 单点登录Flag
     */
    public static final String ACCOUNT_CONFLICT = "conflict";

    /**
     * 请求失败分类
     */
    public static String NETWORK_NO = "无网络可用";
    public static String NETWORK_NOT_WORK = "当前网络不可用";
    public static String TIMEOUT_ERROR = "链接超时";
    public static String CONNECT_NOT_ERROR = "无法链接网路";
    public static String JSON_SYNTAX_ERROR = "Json解析失败";
    public static String CONNECT_FAIL = "请求失败";

    /**
     * 默认文件夹路径
     */
    public static String APP_SDCARD_FILE_NAME = "/mapP";
    /**
     * 默认文件夹路径
     */
    public static String APP_SDCARD_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_SDCARD_FILE_NAME;
    /**
     * image图片缓存路径
     */
    public static String IMAGE_PATH = "/image_cache";
    /**
     * 分页数
     */
    public static String PAGE_SIZE = "10";
    /**
     * 分页--显示没有更多数据
     */
    public static String NO_MORE_DATA = "没有更多数据";
}

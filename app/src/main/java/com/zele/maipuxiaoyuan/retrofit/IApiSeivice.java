package com.zele.maipuxiaoyuan.retrofit;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.zele.maipuxiaoyuan.bean.ArticleBean;
import com.zele.maipuxiaoyuan.bean.AttenDanceBean;
import com.zele.maipuxiaoyuan.bean.BannerBean;
import com.zele.maipuxiaoyuan.bean.BaseBean;
import com.zele.maipuxiaoyuan.bean.BindStudentTeacherBean;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.ClassCircleBean;
import com.zele.maipuxiaoyuan.bean.CoinRecordBean;
import com.zele.maipuxiaoyuan.bean.EvaluateRecordBean;
import com.zele.maipuxiaoyuan.bean.EvaluateTagBean;
import com.zele.maipuxiaoyuan.bean.GroupRecordBean;
import com.zele.maipuxiaoyuan.bean.GrowColumDataBean;
import com.zele.maipuxiaoyuan.bean.HallHonorListBean;
import com.zele.maipuxiaoyuan.bean.HelpBean;
import com.zele.maipuxiaoyuan.bean.LoginUserBean;
import com.zele.maipuxiaoyuan.bean.MessageBean;
import com.zele.maipuxiaoyuan.bean.MessageClassesListviewBean;
import com.zele.maipuxiaoyuan.bean.ParentMessageBean;
import com.zele.maipuxiaoyuan.bean.RadarDataBean;
import com.zele.maipuxiaoyuan.bean.RealteBean;
import com.zele.maipuxiaoyuan.bean.RegisterTwoDataBean;
import com.zele.maipuxiaoyuan.bean.RelationVoBean;
import com.zele.maipuxiaoyuan.bean.ScheduleBean;
import com.zele.maipuxiaoyuan.bean.ScoreBean;
import com.zele.maipuxiaoyuan.bean.StuHealthBean;
import com.zele.maipuxiaoyuan.bean.StudentInfoBean;
import com.zele.maipuxiaoyuan.bean.UploadHeadPicBean;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Description:      API接口类
 * Autour：          LF
 * Date：            2017/7/5 16:31
 * <p>
 * retrofit注解：
 *
 * @HTTP：可以替代其他方法的任意一种
 * @HTTP(method = "get", path = "users/{user}", hasBody = false)
 * Call<ResponseBody> getFirstBlog(@Path("user") String user);
 * @Url：使用全路径复写baseUrl，适用于非统一baseUrl的场景。
 * @GET Call<ResponseBody> v3(@Url String url);
 * @Streaming:用于下载大文件
 * @Path：URL占位符，用于替换和动态更新,相应的参数必须使用相同的字符串被@Path进行注释
 * @GET("group/{id}/users") Call<ResponseBody> groupList(@Path("id") int groupId);
 * @Query,@QueryMap:查询参数，用于GET查询,需要注意的是@QueryMap可以约定是否需要encode@GET("group/users") Call<List<User>> groupList(@Query("id") int groupId);
 * Call<List<News>> getNews((@QueryMap(encoded=true) Map<String, String> options);
 * @Body:用于POST请求体，将实例对象根据转换方式转换为对应的json字符串参数，这个转化方式是GsonConverterFactory定义的。
 * @POST("add") Call<List<User>> addUser(@Body User user);
 * @Field，@FieldMap:Post方式传递简单的键值对,需要添加@FormUrlEncoded表示表单提交Content-Type:application/x-www-form-urlencoded
 * @FormUrlEncoded
 * @POST("user/edit") Call<User> updateUser(@Field("first_name") String first, @Field("last_name") String last);
 * @Part，@PartMap：用于POST文件上传其中@Part MultipartBody.Part代表文件，@Part("key") RequestBody代表参数需要添加@Multipart表示支持文件上传的表单，Content-Type: multipart/form-data
 */
public interface IApiSeivice {

    /********************示例模块**********************/
    @GET("v2/movie/top250")
    Call<JsonObject> getMovieList(@QueryMap Map<String, String> options);

    /********************首页模块**********************/
    //轮播图
    @GET("banner/getBanner")
    Call<BannerBean> getBannner(@QueryMap Map<String, String> options);

    //获取学生详情
    @POST("account/getStudent.action")
    Call<StudentInfoBean> getStudentDetail(@QueryMap Map<String, String> options);

    //获取学生学期信息
    @GET("access/getTerms.action")
    Call<JSONObject> getStudentTermsData(@QueryMap Map<String, String> options);

    //获取荣誉奖状数据
    @GET("hall/userHallHonor.action")
    Call<HallHonorListBean> getHallHonorData(@QueryMap Map<String, String> options);

    //获取考勤数据
    @POST("attend/getAttendParent")
    Call<AttenDanceBean> getAttendanceData(@QueryMap Map<String, String> options);

    //获取作业列表数据
    @POST("message/getMessages.action")
    Call<MessageClassesListviewBean> getBooksData(@QueryMap Map<String, String> options);

    //设置当前信息为已读
    @GET("message/readMsg.action")
    Call<BaseBean> setReadByMsgId(@QueryMap Map<String, String> options);

    //获取雷达图数据
    @GET("evalua/getStudAchieve")
    Call<RadarDataBean> getRadarData(@QueryMap Map<String, String> options);

    //成长总览柱状图
    @GET("evalua/getStudItalItems")
    Call<GrowColumDataBean> getColumData(@QueryMap Map<String, String> options);

    //成长总览列表
    @GET("evalua/getHonorStatistics")
    Call<GroupRecordBean> getGroupRecord(@QueryMap Map<String, String> options);

    //获取课程表数据
    @GET("course/getSchedule.action")
    Call<ScheduleBean> getClassScheduleData(@QueryMap Map<String, String> options);

    //获取TAG标签列表
    @GET("evalua/getQuotaTypes.action")
    Call<EvaluateTagBean> getTagList(@QueryMap Map<String, String> options);

    //综合评价评星详情根据班级请求数据
    @GET("evalua/getStudHonors.action")
    Call<EvaluateRecordBean> getStudHonors(@QueryMap Map<String, String> options);


    /********************消息模块**********************/
    //获取环信最新消息
    @POST("message/query.action")
    Call<MessageBean> getMsgList(@QueryMap Map<String, String> options);

    //获取家长好友列表
    @POST("friend/getParfriends.action")
    Call<JSONObject> loadClassParents(@QueryMap Map<String, String> options);

    //获取教师好友列表
    @POST("friend/getTearchfriends.action")
    Call<JSONObject> loadClassTeacher(@QueryMap Map<String, String> options);


    /********************个人中心模块**********************/
    //查看我的积分
    @GET("account/getParentAttr")
    Call<ScoreBean> getScore(@QueryMap Map<String, String> options);

    //获取家长信息
    @POST("account/getParent.action")
    Call<ParentMessageBean> getParentInfo(@QueryMap Map<String, String> options);

    //班级圈首页
    @GET("share/queryParent")
    Call<ClassCircleBean> getCircleData(@QueryMap Map<String, String> options);

    //获取帮助列表
    @GET("news/helpList.action")
    Call<HelpBean> getHelpList(@QueryMap Map<String, String> options);

    //获取学生健康数据
    @GET("mark/getHealthCard.action")
    Call<StuHealthBean> getStuHealthData(@QueryMap Map<String, String> options);

    //查看我的麦粒记录
    @GET("record/getParentGoldList")
    Call<CoinRecordBean> getMailiList(@QueryMap Map<String, String> options);

    //上传头像
    @Multipart
    @POST("uploadFile.do")
    Call<UploadHeadPicBean> upLoadHeadPic(@Part MultipartBody.Part file);

    //提交学生对应的图片信息-头像与学生ID绑定
    @POST("account/saveAvatar.action")
    Call<BaseBean> submitStuPicInfo(@QueryMap Map<String, String> options);

    //修改密码
    @POST("account/saveSecurity.action")
    Call<BaseBean> updatePwd(@QueryMap Map<String, String> options);

    //获取关系表
    @POST("access/getRelates.action")
    Call<RelationVoBean> getRelationList();

    //保存家长信息
    @POST("account/saveStuParData.action")
    Call<BaseBean> saveParentInfo(@QueryMap Map<String, String> options);


    /********************登录模块**********************/
    //登录
    @POST("account/login.action")
    Call<LoginUserBean> login(@QueryMap Map<String, String> options);

    //请求绑定的学生列表
    @POST("account/getbindStuds.action")
    Call<BindStudentsBean> getBindStudents(@QueryMap Map<String, String> options);

    //请求绑定的学生列表
    @POST("/account/login.action")
    Call<BindStudentsBean> LoginByQQOrWx(@QueryMap Map<String, String> options);

    //发送验证码--注册
    @POST("/account/sendVercode.action")
    Call<BaseBean> sendValidationCode(@QueryMap Map<String, String> options);

    //发送验证码--忘记密码
    @POST("/account/valiCodeForUserFind.action")
    Call<BaseBean> sendValidationCodeToForgetPwd(@QueryMap Map<String, String> options);

    //密码重置
    @POST("/account/saveUserFind.action")
    Call<BaseBean> resetPasswd(@QueryMap Map<String, String> options);

    //注册
    @POST("/account/register.action")
    Call<LoginUserBean> register(@QueryMap Map<String, String> options);

    //获取条款服务
    @FormUrlEncoded
    @POST("/news/list.action")
    Call<ArticleBean> getArticle(@FieldMap Map<String, String> options);

    //根据班主任号码查询班级信息
    @GET("/account/getClassinfo")
    Call<BindStudentTeacherBean> getTeacherByPhone(@Query("phone") String groupId);

    //验证学生身份证号是否正确
    @POST("/account/vertifyIdCard")
    Call<JSONObject> validationStudentIDCard(@QueryMap Map<String, String> options);

    //获取关系列表
    @GET("/access/getRelates.action")
    Call<RealteBean> getRelateList();

    //绑定学生关系
    @POST("/account/binding.action")
    Call<RegisterTwoDataBean> bindStudentRelation(@QueryMap Map<String, String> options);
}

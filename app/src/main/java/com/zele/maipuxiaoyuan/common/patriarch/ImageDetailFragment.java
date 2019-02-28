package com.zele.maipuxiaoyuan.common.patriarch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.ClassGroup;
import com.zele.maipuxiaoyuan.bean.ClassParents;
import com.zele.maipuxiaoyuan.bean.ClassTeacher;
import com.zele.maipuxiaoyuan.common.utils.FileUtils;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.ToastUtil;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment {
	public static final int REQUEST_CODE_FORWARD_BACK=16;
	private String mImageUrl;
	private ImageView mImageView;
	private ProgressBar progressBar;
	private PhotoViewAttacher mAttacher;
	private Bitmap image;
	private String mSID;
	public static ImageDetailFragment newInstance(String imageUrl) {
		final ImageDetailFragment f = new ImageDetailFragment();
		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
		BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {});
		mSID= studentsBean.getSid();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
		mImageView = (ImageView) v.findViewById(R.id.image);
		mAttacher = new PhotoViewAttacher(mImageView);
		mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				getActivity().finish();
			}
		});

		mImageView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "保存到图库");
                menu.add(0, 1, 1, "转发");
            }
        });
        mAttacher.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mImageView.showContextMenu();
                return false;
            }
        });
		
		progressBar = (ProgressBar) v.findViewById(R.id.loading);
		return v;
	}
	 //长按菜单处理函数
    public boolean onContextItemSelected(MenuItem aItem) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)aItem.getMenuInfo();
        switch (aItem.getItemId()) {
            case 0:
                if(image!=null) {
                    FileUtils.saveImageToGallery(getActivity(), image);
                }else{
                    ToastUtil.showToast(getActivity(),"图片加载失败！");
                }
                break;
            case 1:
//                Intent intent = new Intent(getActivity(), MessageAddressBookActivty.class);
//                intent.putExtra("displaySelect", true);
//                startActivityForResult(intent,REQUEST_CODE_FORWARD_BACK);
                return true;
        }
        return false;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_FORWARD_BACK: //send the video
                    ArrayList<ClassTeacher> selectTeacher=(ArrayList<ClassTeacher>)data.getSerializableExtra("selectTeacher"); //选择的教师
                    ArrayList<ClassParents> selectParent=(ArrayList<ClassParents>)data.getSerializableExtra("selectParent");  //选择的家长
                    ArrayList<ClassGroup> selectGroup=(ArrayList<ClassGroup>)data.getSerializableExtra("selectGroup");  //选择的群
                    String filePath=FileUtils.saveImageToCache(getActivity(),image);
                    EMMessage message;
                    for(ClassTeacher u:selectTeacher) {
                        message = EMMessage.createImageSendMessage(filePath, false, u.getUserName());
                        message.setChatType(EMMessage.ChatType.Chat);
                        sendMessage(message, mSID+"","");
                    }
                    for(ClassParents u:selectParent) {
                        message = EMMessage.createImageSendMessage(filePath, false, u.getUserName());
                        message.setChatType(EMMessage.ChatType.Chat);
                        sendMessage(message,mSID+"",u.getStudentId()+"");
                    }
                    for(ClassGroup u:selectGroup) {
                        message = EMMessage.createImageSendMessage(filePath, false, u.getGroupId());
                        message.setChatType(EMMessage.ChatType.GroupChat);
                        sendMessage(message,mSID+"","");
                    }
                    break;
                default:
                    break;
            }
        }
    }
    protected void sendMessage(EMMessage message, String from , String to) {
        if (message == null) {
            return;
        }
        if (!TextUtils.isEmpty(to)) {
            message.setAttribute("to", to);
        }
        if (!TextUtils.isEmpty(from)) {
            message.setAttribute("from", from);
        }
        try {
	        org.json.JSONObject json=new  org.json.JSONObject();
	        if (!TextUtils.isEmpty(to)) {
				json.put("to", to);
	        }
	        if (!TextUtils.isEmpty(from)) {
	            json.put("from", from);
	        }
	        if( message.getChatType()==EMMessage.ChatType.GroupChat){
	        	json.put("groupId", message.getTo());
	        }        
	        message.setAttribute("em_apns_ext", json);
        } catch (JSONException e) {
			e.printStackTrace();
		}
        EMClient.getInstance().chatManager().sendMessage(message);
        if(message.getChatType()!=EMMessage.ChatType.GroupChat){
            //生成新的信息保存
            EMMessage msg = EMMessage.createSendMessage(message.getType());
            msg.addBody(message.getBody());
            msg.setFrom(message.getFrom());
            msg.setStatus(EMMessage.Status.SUCCESS);
            String newTo="";
            if(!TextUtils.isEmpty(to)){
            	newTo="_"+to;
            }
            msg.setTo(message.getTo() + newTo+ "_" + from);
            EMConversation ab = EMClient.getInstance().chatManager().getConversation(msg.getTo(), EMConversation.EMConversationType.Chat, true);
            ab.insertMessage(msg);
        }
    }
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
				case IO_ERROR:
					message = "下载错误";
					break;
				case DECODING_ERROR:
					message = "图片无法显示";
					break;
				case NETWORK_DENIED:
					message = "网络有问题，无法下载";
					break;
				case OUT_OF_MEMORY:
					message = "图片太大无法显示";
					break;
				case UNKNOWN:
					message = "未知的错误";
					break;
				}
				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				image=loadedImage;
				progressBar.setVisibility(View.GONE);
				mAttacher.update();
			}
		});
	}
}

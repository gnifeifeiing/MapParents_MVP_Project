package com.zele.maipuxiaoyuan.ui.personalfrag;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.BindStudentsBean;
import com.zele.maipuxiaoyuan.bean.HelpBean;
import com.zele.maipuxiaoyuan.common.base.BaseActivity;
import com.zele.maipuxiaoyuan.common.utils.SharedPreferenceCache;
import com.zele.maipuxiaoyuan.common.utils.SnackbarUtil;
import com.zele.maipuxiaoyuan.mvp.presenter.fifthfrag.HelpPresenter;
import com.zele.maipuxiaoyuan.mvp.view.fifthfrag.HelpView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:      帮助与反馈
 * Autour：          LF
 * Date：            2018/11/22 16:11
 */
public class HelpActivity extends BaseActivity<HelpView,HelpPresenter> implements  HelpView{

    @BindView(R.id.title_backIv)
    ImageView titleBackIv;
    @BindView(R.id.title_titleTv)
    TextView titleTitleTv;
    @BindView(R.id.help_listView)
    ListView mListView;
    @BindView(R.id.help_empty)
    ImageView mEmpty;

    private String mSID;

    private List<HelpBean.Help> mList=new ArrayList<>();
    private HelpAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        showProgress("正在加载...");
        Map<String,String> parameter=new HashMap<>();
        parameter.put("sid",mSID);
        mPresenter.getHelpList(parameter);
    }

    private void initView() {
        titleTitleTv.setText("帮助与反馈");

        BindStudentsBean.StudentsBean studentsBean = JSON.parseObject(SharedPreferenceCache.getInstance().getPres("Student"), new TypeReference<BindStudentsBean.StudentsBean>() {});
        mSID= studentsBean.getSid();

        mListView.setEmptyView(mEmpty);
        mAdapter=new HelpAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HelpBean.Help item = ((HelpBean.Help) parent.getAdapter().getItem(position));
                if(!item.isFirst()) {
                    Intent intent = new Intent(HelpActivity.this, HelpDetailsActivity.class);
                    intent.putExtra("item", item);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public HelpPresenter createPresenter() {
        return new HelpPresenter();
    }

    @Override
    public HelpView createView() {
        return this;
    }

    @OnClick(R.id.title_backIv)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onError(String error) {
        cancelProgress();
        SnackbarUtil.ShortSnackbar(mListView, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void getHelpList(HelpBean bean) {
        cancelProgress();
        if("100".equals(bean.getResult())){
            List<HelpBean.Help> list1= bean.getDatas();
            mList.clear();
            for(HelpBean.Help h:list1){
                h.setFirst(true);
                mList.add(h);
                mList.addAll(h.getList());
            }
            mAdapter.notifyDataSetChanged();
        }
    }


    class HelpAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mList.size();
        }
        @Override
        public HelpBean.Help getItem(int position) {
            return mList.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(HelpActivity.this, R.layout.item_help_layout, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = ((ViewHolder) convertView.getTag());
            }
            HelpBean.Help help=getItem(position);
            if(help.isFirst()){
                holder.content.setVisibility(View.GONE);
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setText(help.getTitle());
                holder.divider.setVisibility(View.GONE);
                holder.layout.setBackgroundColor(Color.parseColor("#f3f5f7"));
            }else{
                holder.content.setVisibility(View.VISIBLE);
                holder.title.setVisibility(View.GONE);
                holder.divider.setVisibility(View.VISIBLE);
                holder.content.setText(help.getTitle());
                holder.layout.setBackgroundColor(Color.parseColor("#ffffff"));

            }

            return convertView;
        }
    }
    class ViewHolder {
        TextView title;
        TextView content;
        RelativeLayout layout;
        View divider;
        public ViewHolder(View convertView) {
            this.title = convertView.findViewById(R.id.title);
            this.content = convertView.findViewById(R.id.content);
            this.layout = convertView.findViewById(R.id.layout);
            this.divider = convertView.findViewById(R.id.divider);
        }
    }
}

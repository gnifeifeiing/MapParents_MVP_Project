package com.zele.maipuxiaoyuan.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.TermBean;
import com.zele.maipuxiaoyuan.common.utils.CommonUtils;

import java.util.List;

/**
 * Description:      描述
 * Autour：          LF
 * Date：            2018/11/19 14:34
 */

public class TermBaseAdapter  extends BaseAdapter {

    //显示多个学期情况
    private List<TermBean.TermsBean> mTerms;
    private Context mContext;
    private int pos = 0;

    public TermBaseAdapter(Context context, List<TermBean.TermsBean> terms){
        this.mContext=context;
        this.mTerms=terms;
    }

    @Override
    public int getCount() {
        if (mTerms!=null){
            return mTerms.size();
        }else {
            return 0;
        }
    }

    @Override
    public String getItem(int i) {
        return mTerms.get(i).getName();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            TextView view1 = new TextView(mContext);
            view1.setTextSize(14);
            view1.setTextColor(mContext.getResources().getColor(R.color.text_6));
            view1.setGravity(Gravity.CENTER);
            view1.setWidth(CommonUtils.px2dip(mContext, 120));
            view1.setHeight(CommonUtils.px2dip(mContext, 40));
            view = view1;
        }
        TextView tv = (TextView) view;
        tv.setText(getItem(i).replaceAll("\\_\\d", ""));
        if (i == pos) {
            tv.setTextColor(mContext.getResources().getColor(R.color.primaryBg));
        } else {
            tv.setTextColor(mContext.getResources().getColor(R.color.text_3));
        }
        return view;
    }

    public void setPos(int position) {
        this.pos = position;
        notifyDataSetChanged();
    }
}

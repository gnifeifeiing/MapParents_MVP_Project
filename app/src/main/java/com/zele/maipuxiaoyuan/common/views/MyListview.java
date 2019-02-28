package com.zele.maipuxiaoyuan.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lwz on 2017/12/15.
 */

public class MyListview extends ListView {
    public MyListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public MyListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyListview(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //listview 嵌套gridview 时候必须加入这句，否则使用自适应高度的时候无效，原因是什么？http://blog.csdn.net/vae1314chuanchen/article/details/51629267
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

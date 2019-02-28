package com.zele.maipuxiaoyuan.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author
 * @version 1.0
 * @date 2017/8/3
 */

public class MyGridview extends GridView {
    public MyGridview(Context context) {
        super(context);
        // TODO Auto-generated constructor stub  
    }

    public MyGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub  
    }

    public MyGridview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub  
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        //listview 嵌套gridview 时候必须加入这句，否则使用自适应高度的时候无效，原因是什么？http://blog.csdn.net/vae1314chuanchen/article/details/51629267
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

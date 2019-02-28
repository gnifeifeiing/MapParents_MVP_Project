package com.zele.maipuxiaoyuan.common.patriarch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zele.maipuxiaoyuan.R;

import java.util.ArrayList;
import java.util.List;


public abstract class NineGridLayout extends ViewGroup {

    private static final float DEFUALT_SPACING = 10f;//控制间距
    private static final int MAX_COUNT = 9;

    protected Context mContext;
    private float mSpacing = DEFUALT_SPACING;
    private int mColumns;
    private int mRows;
    private int mTotalWidth;
    private int mSingleWidth;

    private boolean mIsShowAll = false;
    private boolean mIsFirst = true;
    private List<String> mUrlList = new ArrayList<String>();

    public NineGridLayout(Context context) {
        super(context);
        init(context);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NineGridLayout);

        mSpacing = typedArray.getDimension(R.styleable.NineGridLayout_sapcing, DEFUALT_SPACING);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        if (getListSize(mUrlList) == 0) {
            setVisibility(GONE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
   int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
    View.MeasureSpec.AT_MOST);
    	
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
       
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mTotalWidth = right - left;
        mSingleWidth = (int) ((mTotalWidth - mSpacing * (6 - 1)) / 6);
        if (mIsFirst) {
            notifyDataSetChanged();
            mIsFirst = false;
        }
    }

    /**
     * 设置间隔
     *
     * @param spacing
     */
    public void setSpacing(float spacing) {
        mSpacing = spacing;
    }

    /**
     * 设置是否显示所有图片（超过最大数时）
     *
     * @param isShowAll
     */
    public void setIsShowAll(boolean isShowAll) {
        mIsShowAll = isShowAll;
    }

    public void setUrlList(List<String> urlList) {
        if (getListSize(urlList) == 0) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);

        mUrlList.clear();
        mUrlList.addAll(urlList);

        if (!mIsFirst) {
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        removeAllViews();
        int size = getListSize(mUrlList);
        if (size > 0) {
            setVisibility(VISIBLE);
        } else {
            setVisibility(GONE);
        }
        
        Log.d("chris","==============0001");
//         if (size == 1) {
//             Log.d("chris","==============0002");
//             String url = mUrlList.get(0);
//             RatioImageView imageView = createImageView(0, url);
//
//             Log.d("chris","==============0003");
//             //避免在ListView中一张图未加载成功时，布局高度受其他item影响
//             LayoutParams params = getLayoutParams();
//             params.height = mSingleWidth;
//             setLayoutParams(params);
//             imageView.layout(0, 0, mSingleWidth, mSingleWidth);
//
//             boolean isShowDefualt = displayOneImage(imageView, url, mTotalWidth);
//             if (isShowDefualt) {
//                 layoutImageView(imageView, 0, url, false);
//             } else {
//                 addView(imageView);
//             }
//             generateChildrenLayout(size);
//             layoutParams();
//             return;
//         }

         Log.d("chris","========0000==size="+size);
         for (int i = 0; i < size; i++) {

             Log.d("chris","========0000==="+i);

             String url = mUrlList.get(i);
             RatioImageView imageView;
             if (!mIsShowAll) {
                 if (i < MAX_COUNT - 1) {
                     imageView = createImageView(i, url);
                     layoutImageView(imageView, i, url, false);
                 } else { //第9张时
                     if (size <= MAX_COUNT) {//刚好第9张
                         imageView = createImageView(i, url);
                         layoutImageView(imageView, i, url, false);
                     } else {//超过9张
                         imageView = createImageView(i, url);
                         layoutImageView(imageView, i, url, true);
                         break;
                     }
                 }
                 generateChildrenLayout(size);
                 layoutParams();
                 Log.d("chris","========0001==="+i);
             } else {

                 Log.d("chris","========0010==="+i);
                 imageView = createImageView(i, url);
                 Log.d("chris","========0011==="+i);
                 layoutImageView(imageView, i, url, false);
                 Log.d("chris","========0012==="+i);
                 generateChildrenLayout(size);
                 layoutParams();
             }
           

         }


       
    }

    private void layoutParams() {
        int singleHeight = mSingleWidth;

        //根据子view数量确定高度
        LayoutParams params = getLayoutParams();
        params.height = (int) (singleHeight * mRows + mSpacing * (mRows - 1));
        setLayoutParams(params);
    }

    private RatioImageView createImageView(final int i, final String url) {
      /*  RatioImageView imageView = new RatioImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickImage(i, url, mUrlList);
            }
        });
        return imageView;*/
    	
    	   Log.d("chris","==============0000");
           RatioImageView imageView = new RatioImageView(mContext);
           Log.d("chris","==============1111");
           imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
           Log.d("chris","==============2222");
           imageView.setOnClickListener(new OnClickListener() {

               @Override
               public void onClick(View v) {
                   Log.d("chris","==============3333");
                   onClickImage(i, url, mUrlList);
               }
           });
           Log.d("chris","==============4444");
           return imageView;
    }

    /**
     * @param imageView
     * @param url
     * @param showNumFlag 是否在最大值的图片上显示还有未显示的图片张数
     */
    private void layoutImageView(RatioImageView imageView, int i, String url, boolean showNumFlag) {
        final int singleWidth = (int) ((mTotalWidth - mSpacing * (6 - 1)) / 6);
        int singleHeight = singleWidth;

        int[] position = findPosition(i);
        int left = (int) ((singleWidth + mSpacing) * position[1]);
        int top = (int) ((singleHeight + mSpacing) * position[0]);
        int right = left + singleWidth;
        int bottom = top + singleHeight;

        imageView.layout(left, top, right, bottom);

        addView(imageView);
        if (showNumFlag) {//添加超过最大显示数量的文本
            int overCount = getListSize(mUrlList) - MAX_COUNT;
            if (overCount > 0) {
                float textSize = 30;
                final TextView textView = new TextView(mContext);
                textView.setText("+" + String.valueOf(overCount));
                textView.setTextColor(Color.WHITE);
                textView.setPadding(0, singleHeight / 2 - getFontHeight(textSize), 0, 0);
                textView.setTextSize(textSize);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundColor(Color.BLACK);
                textView.getBackground().setAlpha(120);

                textView.layout(left, top, right, bottom);
                addView(textView);
            }
        }
        displayImage(imageView, url);
    }

    private int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < mRows; i++) {
            for (int j = 0; j < mColumns; j++) {
                if ((i * mColumns + j) == childNum) {
                    position[0] = i;//行
                    position[1] = j;//列
                    break;
                }
            }
        }
        return position;
    }

    /**
     * 根据图片个数确定行列数量
     *
     * @param length
     */
    private void generateChildrenLayout(int length) {
        if (length <= 6) {
            mRows = 1;
            mColumns = length;
        } else if (length <= 12) {
            mRows = 2;
            mColumns = 6;
            if (length == 7) {
                mColumns = 6;
            }
        } else {
            mColumns = 3;
            if (mIsShowAll) {
                mRows = length / 6;
                int b = length % 3;
                if (b > 0) {
                    mRows++;
                }
            } else {
                mRows = 3;
            }
        }
    }


    protected void setOneImageLayoutParams(RatioImageView imageView, int width, int height) {
    	 int singleWidth = (int) ((mTotalWidth - mSpacing * (6 - 1)) / 6);

         imageView.setLayoutParams(new LayoutParams(singleWidth, singleWidth));
         imageView.layout(0, 0, singleWidth, singleWidth);

         LayoutParams params = getLayoutParams();
//         params.width = width;
         params.height = singleWidth;
         setLayoutParams(params);
    }

    private int getListSize(List<String> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }

    private int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    /**
     * @param imageView
     * @param url
     * @param parentWidth 父控件宽度
     * @return true 代表按照九宫格默认大小显示，false 代表按照自定义宽高显示
     */
    protected abstract boolean displayOneImage(RatioImageView imageView, String url, int parentWidth);

    protected abstract void displayImage(RatioImageView imageView, String url);

    protected abstract void onClickImage(int position, String url, List<String> urlList);
}

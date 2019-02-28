package com.zele.maipuxiaoyuan.common.views;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zele.maipuxiaoyuan.R;
import com.zele.maipuxiaoyuan.bean.TitleValueEntity;

import java.util.ArrayList;
import java.util.List;


public class SpiderWebChart extends View {

	/**
	 * 默认蛛网背景色
	 */
	public static final String DEFAULT_TITLE = "Spider Web Chart";

	/**
	 * 默认是否显示蛛网经线
	 */
	public static final boolean DEFAULT_DISPLAY_LONGTITUDE = true;

	/**
	 * 默认蛛网经线数
	 */
	public static final int DEFAULT_LONGTITUDE_NUM = 5;

	/**
	 * 默认蛛网经线颜色
	 */
	public static final int DEFAULT_LONGTITUDE_COLOR = Color.BLACK;

	/**
	 * 默认是否显示蛛网纬线
	 */
	public static final boolean DEFAULT_DISPLAY_LATITUDE = true;

	/**
	 * 默认蛛网显示纬线数
	 */
	public static final int DEFAULT_LATITUDE_NUM = 3;

	/**
	 * 默认蛛网纬线颜色
	 */
	public static final int DEFAULT_LATITUDE_COLOR = Color.BLACK;

	/**
	 * 默认蛛网位置
	 */
	public static final Point DEFAULT_POSITION = new Point(0, 0);

	/**
	 * 默认蛛网背景色
	 */
	public static final int DEFAULT_BACKGROUD_COLOR = Color.BLACK;

	/**
	 * 图表数据
	 */
	private List<TitleValueEntity> data;

	private String title = DEFAULT_TITLE;

	/**
	 * 绘图位置
	 */
	private Point position = DEFAULT_POSITION;

	/**
	 * 是否显示蛛网经线
	 */
	private boolean displayLongtitude = DEFAULT_DISPLAY_LONGTITUDE;

	/**
	 * 蛛网线维数
	 */
	private int longtitudeNum = DEFAULT_LONGTITUDE_NUM;

	/**
	 * 蛛网经线颜色
	 */
	private int longtitudeColor = DEFAULT_LONGTITUDE_COLOR;

	/**
	 * 蛛网图经线长度
	 */
	private int longtitudeLength = 0;

	/**
	 * 是否显示蛛网纬线
	 */
	private boolean displayLatitude = DEFAULT_DISPLAY_LATITUDE;

	/**
	 * 蛛网纬线数
	 */
	private int latitudeNum = DEFAULT_LATITUDE_NUM;

	/**
	 * 蛛网纬线颜色
	 */
	private int latitudeColor = DEFAULT_LATITUDE_COLOR;

	/**
	 * 蛛网背景色
	 */
	private int backgroudColor = DEFAULT_BACKGROUD_COLOR;

	private float textWide;

	private Paint mPaintFontzhihui;

	private Paint mPaintFontqinlao;

	private Paint mPaintFonttijian;

	private Paint mPaintFontwenyu;

	private Paint mPaintFontmeide;

	private int total;
	private int remainder;
	float score[]=new float[5];


	public SpiderWebChart(Context context) {
		super(context);
	}

	public SpiderWebChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SpiderWebChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (data!=null){
			for (int i = 0; i < data.size(); i++) {
				score[i]=data.get(i).getValue();
			}

			for (int i = 0; i < score.length; i++)
			{
				for (int j = i; j < score.length; j++)
				{
					if (score[i] > score[j])
					{
						float temp = score[i];
						score[i] = score[j];
						score[j] = temp;
					}
				}
			}
		}else {
			score[4]=0;
		}

		int rect = super.getWidth();

		// 绘图高宽度
		longtitudeLength = (int) ((rect / 2f) * 0.4);
		// 绘制点
		position = new Point((int) (super.getWidth() / 2f),
				(int) (super.getHeight() / 2f));
		// 绘制图表
		drawSpiderWeb(canvas);
		drawData(canvas);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			result = Math.min(result, specSize);
		}
		return result;
	}

	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			result = Math.min(result, specSize);
		}
		return result;
	}

	/**
	 * 获得在经线上的坐标点
	 *
	 * @param pos
	 * @return
	 */
	protected List<PointF> getWebAxisPoints(float pos) {
		List<PointF> points = new ArrayList<PointF>();
		for (int i = 0; i < longtitudeNum; i++) {
			PointF pt = new PointF();
			float offsetX = (float) (position.x - longtitudeLength * pos
					* Math.sin(i * 2 * Math.PI / longtitudeNum));
			float offsetY = (float) (position.y - longtitudeLength * pos
					* Math.cos(i * 2 * Math.PI / longtitudeNum));
			pt.set(offsetX, offsetY);

			points.add(pt);
		}
		return points;
	}

	/**
	 * 获取数据对应的路径
	 *
	 * @param data
	 * @return
	 */
	protected List<PointF> getDataPoints(List<TitleValueEntity> data) {
		List<PointF> points = new ArrayList<PointF>();

		for (int i = 0; i < longtitudeNum; i++) {
			PointF pt = new PointF();
			if (score[4]!=0){
				float offsetX = (float) (position.x - data.get(i).getValue() / score[4]
						* longtitudeLength
						* Math.sin(i * 2 * Math.PI / longtitudeNum));
				float offsetY = (float) (position.y - data.get(i).getValue() / score[4]
						* longtitudeLength
						* Math.cos(i * 2 * Math.PI / longtitudeNum));
				pt.set(offsetX, offsetY);

				points.add(pt);
			}else {
				float offsetX = (float) (position.x - data.get(i).getValue() / 1f
						* longtitudeLength
						* Math.sin(i * 2 * Math.PI / longtitudeNum));
				float offsetY = (float) (position.y - data.get(i).getValue() / 1f
						* longtitudeLength
						* Math.cos(i * 2 * Math.PI / longtitudeNum));
				pt.set(offsetX, offsetY);

				points.add(pt);
			}

		}
		return points;
	}

	/**
	 * 绘制蛛线
	 *
	 * @param canvas
	 */
	protected void drawSpiderWeb(Canvas canvas) {
		int out = getResources().getColor(R.color.primaryBg);//cbeed8

		Paint mPaintWebBorder = new Paint();
		mPaintWebBorder.setColor(out);
		// 最外面蜘蛛线的样式
		mPaintWebBorder.setStyle(Style.STROKE);
		mPaintWebBorder.setStrokeWidth(2);
		mPaintWebBorder.setAntiAlias(true);

		// 内层蜘蛛线
		Paint mPaintWebInnerBorder = new Paint();
		mPaintWebInnerBorder.setColor(out);
		mPaintWebInnerBorder.setStyle(Style.STROKE);
		mPaintWebInnerBorder.setAntiAlias(true);
		//虚线
        /*mPaintWebInnerBorder.setPathEffect(new DashPathEffect(new float[]{5,
                5, 5, 5}, 1));*/

		Paint mPaintLine = new Paint();
		mPaintLine.setColor(out);
		mPaintLine.setStyle(Style.STROKE);
		mPaintLine.setStrokeWidth(2);

		// 总分画笔
		mPaintFont = new Paint();
		mPaintFont.setColor(Color.parseColor("#8fc320"));
		mPaintFont.setTextSize(dp2px(24));
		mPaintFont.setAntiAlias(true);
		// 智慧星画笔
		mPaintFontzhihui = new Paint();
		mPaintFontzhihui.setColor(Color.parseColor("#eea500"));
		mPaintFontzhihui.setTextSize(dp2px(12));
		mPaintFontzhihui.setAntiAlias(true);
		// 勤劳星
		mPaintFontqinlao = new Paint();
		mPaintFontqinlao.setColor(Color.parseColor("#3884f8"));
		mPaintFontqinlao.setTextSize(dp2px(12));
		mPaintFontqinlao.setAntiAlias(true);
		// 体健星
		mPaintFonttijian = new Paint();
		mPaintFonttijian.setColor(Color.parseColor("#57cfc7"));
		mPaintFonttijian.setTextSize(dp2px(12));
		mPaintFonttijian.setAntiAlias(true);
		// 文娱星
		mPaintFontwenyu = new Paint();
		mPaintFontwenyu.setColor(Color.parseColor("#ec78a0"));
		mPaintFontwenyu.setTextSize(dp2px(12));
		mPaintFontwenyu.setAntiAlias(true);
		// 美德星
		mPaintFontmeide = new Paint();
		mPaintFontmeide.setColor(Color.parseColor("#e95257"));
		mPaintFontmeide.setTextSize(dp2px(12));
		mPaintFontmeide.setAntiAlias(true);
		textWide = mPaintFont.measureText("500");

		Path mPath = new Path();
		pointList = getWebAxisPoints(1);

		Resources res = getResources();
		Bitmap[] popbitmap = {
				BitmapFactory.decodeResource(res, R.mipmap.ic_meide_icon),
				BitmapFactory.decodeResource(res, R.mipmap.ic_qinlao_icon),
				BitmapFactory.decodeResource(res, R.mipmap.ic_wenyu_icon),
				BitmapFactory.decodeResource(res, R.mipmap.ic_tijian_icon),
				BitmapFactory.decodeResource(res, R.mipmap.ic_zhihui_icon)};

		// 绘制蛛网图外围边数据
		if (null != data) {
			for (int i = 0; i < pointList.size(); i++) {
				PointF pt = pointList.get(i);
				if (i == 0) {
					mPath.moveTo(pt.x, pt.y);
				} else {
					mPath.lineTo(pt.x, pt.y);
				}
				// Log.d("sxx", "----------"+pt.x+"-----"+pt.y);
				int score = (int) data.get(i).getValue();
				float realx = 0;//图标的x坐标
				float realy = 0;//图标的y坐标
				float scoreX = 0;//文字的x坐标
				float scoreY = 0;//文字的y坐标
				total += score;
				width = popbitmap[i].getWidth()-6;
				heigh = popbitmap[i].getHeight()-6;
				if (pt.x == position.x && pt.y < position.y) {
					realx = pt.x - width;
					realy = pt.y - heigh;
					scoreX = pt.x + 8;
					scoreY = pt.y - heigh / 4;
				} else if (pt.x < position.x && pt.y < position.y) {
					realx = pt.x - 5 * width / 2 - 8;
					realy = pt.y - heigh / 2;
					scoreX = pt.x - 3 * width / 2;
					scoreY = pt.y + heigh / 4;
				} else if (pt.x < position.x && pt.y > position.y) {
					realx = pt.x - 5 * width / 2 - 8;
					realy = pt.y - heigh / 2;
					scoreX = pt.x - 3 * width / 2;
					scoreY = pt.y + heigh / 4;
				} else if (pt.x > position.x && pt.y > position.y) {
					realx = pt.x + 16;
					realy = pt.y - heigh / 2;
					scoreX = pt.x + width + 24;
					scoreY = pt.y + heigh / 4;
				} else if (pt.x > position.x && pt.y < position.y) {
					realx = pt.x + 5;
					realy = pt.y - heigh / 2;
					scoreX = pt.x + width + 16;
					scoreY = pt.y + heigh / 4;
				}
				if (0 == i) {
					//换行用Textpaint
                    TextPaint tp = new TextPaint();
                    tp.setColor(Color.parseColor("#e85257"));
                    tp.setAntiAlias(true);
                    tp.setTextSize(dp2px(10));
                    String message = "美德星\r\n"+"   "+score;
                    StaticLayout myStaticLayout = new StaticLayout(message, tp, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
					canvas.save();
					canvas.translate(scoreX-50, scoreY-63);//从20，20开始画
					myStaticLayout.draw(canvas);
					canvas.restore();

					canvas.drawBitmap(popbitmap[i], realx-55, scoreY-73,
							mPaintFontmeide);
					//canvas.drawText("美德星\n"+score , scoreX-60, scoreY, mPaintFontmeide);*/
					int date1 = (int) data.get(0).getValue();
					remainder = date1 / 100;
				} else if (1 == i) {

					TextPaint tp = new TextPaint();
					tp.setColor(Color.parseColor("#29a4d5"));
					tp.setAntiAlias(true);
					tp.setTextSize(dp2px(10));
					String message = "勤劳星\r\n"+"   "+score;
					StaticLayout myStaticLayout = new StaticLayout(message, tp, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
					canvas.save();
					canvas.translate(scoreX-25, scoreY-40);//从20，20开始画
					myStaticLayout.draw(canvas);
					canvas.restore();

					canvas.drawBitmap(popbitmap[i], realx-30, realy-10,
							mPaintFontzhihui);
					//canvas.drawText( "勤劳星\n"+score, scoreX-80, scoreY, mPaintFontqinlao);
					int date2 = (int) data.get(4).getValue();
					remainder = date2 / 100;
				} else if (2 == i) {
					TextPaint tp = new TextPaint();
					tp.setColor(Color.parseColor("#eb789f"));
					tp.setAntiAlias(true);
					tp.setTextSize(dp2px(10));
					String message = "文娱星\r\n"+"   "+score;
					StaticLayout myStaticLayout = new StaticLayout(message, tp, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
					canvas.save();
					canvas.translate(scoreX-45, scoreY-35);//从20，20开始画
					myStaticLayout.draw(canvas);
					canvas.restore();

					canvas.drawBitmap(popbitmap[i], realx-50, realy,
							mPaintFontzhihui);
					//canvas.drawText("文娱星\n"+score + "", scoreX-80, scoreY, mPaintFontwenyu);
					int date3 = (int) data.get(3).getValue();
					remainder = date3 / 100;
				} else if (3 == i) {
					TextPaint tp = new TextPaint();
					tp.setColor(Color.parseColor("#57cec6"));
					tp.setAntiAlias(true);
					tp.setTextSize(dp2px(10));
					String message = "体健星\r\n"+"   "+score;
					StaticLayout myStaticLayout = new StaticLayout(message, tp, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
					canvas.save();
					canvas.translate(scoreX+15, scoreY-30);//从20，20开始画
					myStaticLayout.draw(canvas);
					canvas.restore();
					canvas.drawBitmap(popbitmap[i], realx+10, realy,
							mPaintFontzhihui);
					//canvas.drawText("体健星\n"+score + "", scoreX, scoreY, mPaintFonttijian);
					int date4 = (int) data.get(2).getValue();
					remainder = date4 / 100;
				} else {
					TextPaint tp = new TextPaint();
					tp.setColor(Color.parseColor("#ebad32"));
					tp.setAntiAlias(true);
					tp.setTextSize(dp2px(10));
					String message = "智慧星\r\n"+"   "+score;
					StaticLayout myStaticLayout = new StaticLayout(message, tp, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
					canvas.save();
					canvas.translate(scoreX+15, scoreY-30);//从20，20开始画
					myStaticLayout.draw(canvas);
					canvas.restore();
					canvas.drawBitmap(popbitmap[i], realx+10, realy,
							mPaintFontzhihui);
					//canvas.drawText("智慧星\n"+score + "", scoreX+10, scoreY, mPaintFontzhihui);
					int date5 = (int) data.get(1).getValue();
					remainder = date5 / 100;
				}
//                Log.d("sxxxxx", "drawSpiderWeb: ----------"+remainder);
               /* try {
                    //报异常
                    canvas.drawBitmap(starbitmap[remainder],
                            position.x - starbitmap[remainder].getWidth() * 5 / 2
                                    + starbitmap[remainder].getWidth() * i, position.y
                                    + longtitudeLength * 13 / 11, mPaintFont);
                }catch (Exception e){

                }*/

			}
			//总分
           /* canvas.drawText("" + total, position.x - width, position.y
                    + longtitudeLength * 11 / 10, mPaintFont);
            total = 0;*/
		}
		mPath.close();
		canvas.drawPath(mPath, mPaintWebBorder);

		// // 绘制圆圈
		// float r = (float) longtitudeLength / (DEFAULT_LATITUDE_NUM + 1);
		// for (int j = 1; j <= latitudeNum; j++) {
		// curR = r * (j + 1);
		// canvas.drawCircle(position.x, position.y, curR,
		// mPaintWebInnerBorder);
		// }
		// 绘制经线
		for (int i = 0; i < pointList.size(); i++) {
			PointF pt = pointList.get(i);
			// 绘制经线
			canvas.drawLine(position.x, position.y, pt.x, pt.y, mPaintLine);
		}
		// 绘制纬线
		for (int j = 1; j < latitudeNum; j++) {

			Path mPathInner = new Path();
			List<PointF> pointListInner = getWebAxisPoints(j * 1f / latitudeNum);

			// 绘制Web图
			for (int i = 0; i < pointListInner.size(); i++) {
				PointF pt = pointListInner.get(i);
				if (i == 0) {
					mPathInner.moveTo(pt.x, pt.y);
				} else {
					mPathInner.lineTo(pt.x, pt.y);
				}
			}
			mPathInner.close();
			canvas.drawPath(mPathInner, mPaintWebInnerBorder);
		}


	}

	/**
	 * 绘制数据线条
	 *
	 * @param canvas
	 */
	protected void drawData(Canvas canvas) {
		if (null != data) {
			List<TitleValueEntity> list = data;

			Paint mPaintFill = new Paint();
			mPaintFill.setColor(Color.parseColor("#ccefd9"));
			mPaintFill.setStyle(Style.FILL);
			mPaintFill.setAntiAlias(true);
			mPaintFill.setAlpha(100);

			Paint mPaintFont = new Paint();
			mPaintFont.setColor(Color.WHITE);

			// 绘制数据点
			Paint mPaintPoint = new Paint();
			mPaintPoint.setColor(Color.parseColor("#ccefd9"));

			Path mPath = new Path();

			// 获取数据点列表
			List<PointF> pointList = getDataPoints(list);
			// 获取Path
			for (int i = 0; i < pointList.size(); i++) {
				PointF pt = pointList.get(i);
				if (i == 0) {
					mPath.moveTo(pt.x, pt.y);
				} else {
					mPath.lineTo(pt.x, pt.y);
				}
				canvas.drawCircle(pt.x, pt.y, 0, mPaintPoint);
			}
			mPath.close();
			canvas.drawPath(mPath, mPaintFill);

		}
		for (int i = 0; i <5; i++) {
			invalidate();
		}
	}

	// 定义一个接口对象listerner
	private OnItemSelectListener listener;

	private Paint mPaintFont;

	private List<PointF> pointList;

	private int width;

	private int heigh;

	// 获得接口对象的方法。
	public void setOnItemSelectListener(OnItemSelectListener listener) {
		this.listener = listener;
	}

	// 定义一个接口
	public interface OnItemSelectListener {
		public void onItemSelect(int index);
	}


	public List<TitleValueEntity> getData() {
		return data;
	}

	public void setData(List<TitleValueEntity> data) {
		this.data = data;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public boolean isDisplayLongtitude() {
		return displayLongtitude;
	}

	public void setDisplayLongtitude(boolean displayLongtitude) {
		this.displayLongtitude = displayLongtitude;
	}

	public int getLongtitudeNum() {
		return longtitudeNum;
	}

	public void setLongtitudeNum(int longtitudeNum) {
		this.longtitudeNum = longtitudeNum;
	}

	public int getLongtitudeColor() {
		return longtitudeColor;
	}

	public void setLongtitudeColor(int longtitudeColor) {
		this.longtitudeColor = longtitudeColor;
	}

	public int getLongtitudeLength() {
		return longtitudeLength;
	}

	public void setLongtitudeLength(int longtitudeLength) {
		this.longtitudeLength = longtitudeLength;
	}

	public boolean isDisplayLatitude() {
		return displayLatitude;
	}

	public void setDisplayLatitude(boolean displayLatitude) {
		this.displayLatitude = displayLatitude;
	}

	public int getLatitudeNum() {
		return latitudeNum;
	}

	public void setLatitudeNum(int latitudeNum) {
		this.latitudeNum = latitudeNum;
	}

	public int getLatitudeColor() {
		return latitudeColor;
	}

	public void setLatitudeColor(int latitudeColor) {
		this.latitudeColor = latitudeColor;
	}

	public int getBackgroudColor() {
		return backgroudColor;
	}

	public void setBackgroudColor(int backgroudColor) {
		this.backgroudColor = backgroudColor;
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
}

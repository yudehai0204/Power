package com.power;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 于德海 on 2018/8/15.
 * package inter.baisong.widgets
 * email : yudehai0204@163.com
 *
 * @describe
 */
public class PowerView extends View {
    private Paint mLine_Paint,mOval_Paint_Grey,mOva_Paint_Blue,mLine_Paint_White,mTextPaint;

    private final int DefaultColor = Color.parseColor("#eeeeee");//默认色彩
    private final int[] colors = new int[]{Color.parseColor("#4de4f6"),
            Color.parseColor("#337fdd"),Color.parseColor("#3347dd")};
    private int dip_1,dip_18;
    private LinearGradient mLinearGradient;//渐变色
    private int mCircle_angle= 240;//内环总度数 180 +60
    private final  float MAX_POWER =100;//最大算力
    private int nowPower;//当前数值
    private int needEndPower;//终点数值
    private  Path [] mPath;//文字的path
    private TextView tv_value;
    private Context mContext;
    private int margin_circle_1,margin_circle_2,margin_circle_3;//圆环环距离外部的边距
    public PowerView(Context context) {
        this(context,null);
    }

    public PowerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public void setPower(int power, @NonNull TextView tv_value){
        this.tv_value = tv_value;
        if(power==0||power==needEndPower)
            return;
        needEndPower = power;
        invalidate();
    }
    public PowerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        nowPower = 0;

        mContext = context;
        dip_1 = DisplayUtils.dip2px(context,1);//线宽度
        dip_18 = DisplayUtils.dip2px(context,18);//圆环宽度
        mLine_Paint= new Paint();
        mLine_Paint.setColor(DefaultColor);
        mLine_Paint.setStyle(Paint.Style.STROKE);
        mLine_Paint.setAntiAlias(true);
        mLine_Paint.setStrokeWidth(dip_1);
        mLine_Paint_White= new Paint();
        mLine_Paint_White.setColor(Color.WHITE);
        mLine_Paint_White.setStyle(Paint.Style.STROKE);
        mLine_Paint_White.setStrokeWidth(dip_18);
        //背景环
        mOval_Paint_Grey = new Paint();
        mOval_Paint_Grey.setColor(DefaultColor);
        mOval_Paint_Grey.setStyle(Paint.Style.STROKE);
        mOval_Paint_Grey.setStrokeWidth(dip_18);
        mOval_Paint_Grey.setStrokeCap(Paint.Cap.ROUND);
        mOval_Paint_Grey.setAntiAlias(true);
        //渐变环
        mOva_Paint_Blue = new Paint();
        mOva_Paint_Blue.setColor(colors[0]);
        mOva_Paint_Blue.setStyle(Paint.Style.STROKE);
        mOva_Paint_Blue.setAntiAlias(true);
        mOva_Paint_Blue.setStrokeCap(Paint.Cap.ROUND);
        mOva_Paint_Blue.setStrokeWidth(dip_18);
        //字体
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(DisplayUtils.sp2px(context,12));
        mTextPaint.setColor(Color.parseColor("#a7a7a7"));
        mTextPaint.setAntiAlias(true);

        margin_circle_1 = DisplayUtils.dip2px(context,20);
        margin_circle_2 = DisplayUtils.dip2px(context,35);
        margin_circle_3 = DisplayUtils.dip2px(context,50);

    }

    private RectF rect_1,rect_2,rect_3;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(rect_1==null)
            rect_1 = new RectF(dip_1/2,dip_1/2,getWidth()-dip_1/2,getHeight()-dip_1/2);
        if(rect_2==null){
            rect_2 = new RectF(margin_circle_1,margin_circle_1,getWidth()-margin_circle_1,getHeight()-margin_circle_1);
            mLinearGradient = new LinearGradient(margin_circle_1,margin_circle_1,getWidth()-margin_circle_1,margin_circle_1,colors,null,LinearGradient.TileMode.CLAMP);
            mOva_Paint_Blue.setShader(mLinearGradient);
        }
        if(rect_3==null)
            rect_3 = new RectF(margin_circle_2,margin_circle_2,getWidth()-margin_circle_2,getHeight()-margin_circle_2);
        //绘制圆环相关
        canvas.drawArc(rect_1,160,70,false,mLine_Paint);
        canvas.drawArc(rect_1,310,70,false,mLine_Paint);
        canvas.drawArc(rect_2,150,mCircle_angle,false,mOval_Paint_Grey);
        canvas.drawArc(rect_2,150,mCircle_angle*(nowPower/MAX_POWER),false,mOva_Paint_Blue);
        canvas.drawArc(rect_3,160,230,false,mLine_Paint);
        //绘制白线
        drawWhiteLine(canvas);
        //绘制文字
        drawText(canvas);
        if(tv_value!=null){
            tv_value.setText(nowPower+"");
        }
        if(nowPower<needEndPower){
            Log.e("text",nowPower+"");
            nowPower++;
            invalidate();
        }else if(nowPower>needEndPower){
            nowPower--;
            invalidate();
        }else if(nowPower==100){
            needEndPower = 60;
            invalidate();
        }

    }
    private String strs[] = new String[]{"·","20","·","40","·","60","·","80","·"};
    private RectF rect_4;
    private void drawText(Canvas canvas) {
        if(rect_4==null){
            rect_4 = new RectF(margin_circle_3,margin_circle_3,getWidth()-margin_circle_3,getWidth()-margin_circle_3);
        }
        if(mPath==null){
            mPath = new Path[9];
            for(int i=0;i<9;i++){
                mPath[i] = new Path();
                if(i%2==1){
                    mPath[i].addArc(rect_4,150+mCircle_angle/10*(i+1)-4,mCircle_angle/10);
                }else if(i==0){
                    mPath[i].addArc(rect_4,150+mCircle_angle/10*(i+1)-2,mCircle_angle/10);
                }else if(i==8){
                    mPath[i].addArc(rect_4,150+mCircle_angle/10*(i+1)+2,mCircle_angle/10);
                }else {
                    mPath[i].addArc(rect_4,150+mCircle_angle/10*(i+1),mCircle_angle/10);
                }
            }
        }

        for(int i=0;i<mPath.length;i++){
            canvas.drawTextOnPath(strs[i],mPath[i],0,0,mTextPaint);
        }

    }

    private void drawWhiteLine(Canvas canvas){
        canvas.drawArc(rect_2,150+mCircle_angle/5,1,false,mLine_Paint_White);
        canvas.drawArc(rect_2,150+mCircle_angle/5*2,1,false,mLine_Paint_White);
        canvas.drawArc(rect_2,150+mCircle_angle/5*3,1,false,mLine_Paint_White);
        canvas.drawArc(rect_2,150+mCircle_angle/5*4,1,false,mLine_Paint_White);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = DisplayUtils.getWindow_Width((Activity) mContext)/5*3;
        setMeasuredDimension(width,width);
    }
}

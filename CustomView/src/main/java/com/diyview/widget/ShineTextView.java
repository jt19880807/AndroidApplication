package com.diyview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Devin.Jiang on 2016-06-24.
 */
public class ShineTextView extends TextView {
    private final String TAG="TAG";
    private LinearGradient linearGradient;
    private Matrix matrix;//矩阵
    private Paint paint;//画笔
    private int mViewWidth=0;
    private int mViewHeight=0;
    private int mTranslate=0;//平移量
    private boolean mAnimating = true;//是否动画
    private int delda=15;//移动增量
    private PorterDuffXfermode xFermode;
    private float mTextHeith;//文字高度
    private float mTextWidth;//文字宽度
    private int index=0;


    public ShineTextView(Context context) {
        this(context,null);
    }

    public ShineTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        xFermode=new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        paint.setColor(Color.CYAN);
        paint.setTextSize(20.0f);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setXfermode(null);
        paint.setTextAlign(Paint.Align.LEFT);
        Paint.FontMetrics fontMetrics=paint.getFontMetrics();
        mTextHeith=fontMetrics.bottom-fontMetrics.descent-fontMetrics.ascent;
        mTextWidth=paint.measureText(getText().toString());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int mWidth;
        final int mHeight;
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode==MeasureSpec.EXACTLY){
            mWidth=widthSize;
        }
        else {
            int desireByImg=getPaddingLeft()+getPaddingRight()+getMeasuredWidth();
            if(widthMode==MeasureSpec.AT_MOST){
                mWidth=Math.min(widthSize,desireByImg);
            } else {
                mWidth=desireByImg;
            }
        }
        /***
         * 设置高度
         */
        int   heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int   heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY)// match_parent , accurate
            mHeight = heightSize;
        else
        {
            int desire = getPaddingTop() + getPaddingBottom()
                    + getMeasuredHeight();
            if (heightMode == MeasureSpec.AT_MOST)// wrap_content
                mHeight = Math.min(desire, heightSize);
            else
                mHeight = desire;
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Bitmap srcBitmap=Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(),Bitmap.Config.ARGB_8888);
        Canvas srcCanvas=new Canvas(srcBitmap);
        srcCanvas.drawText(getText().toString(),0,mTextHeith,paint);
        paint.setXfermode(xFermode);
        paint.setColor(Color.RED);
        RectF rect=new RectF(0,0,index,getMeasuredHeight());
        srcCanvas.drawRect(rect,paint);
        canvas.drawBitmap(srcBitmap,0,0,null);
       // init();
        if(index<mTextWidth)
        {
            index+=10;
        }else{
            index=0;
        }
        paint.setXfermode(null);
        paint.setColor(Color.CYAN);
        postInvalidateDelayed(300);
    }
}

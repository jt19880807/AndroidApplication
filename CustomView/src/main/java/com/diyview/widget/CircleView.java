package com.diyview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.diyview.R;

/**
 * Created by Devin.Jiang on 2016-06-21.
 */
public class CircleView extends View {
    //默认颜色
    private final int DEFAULT_COLOR= Color.LTGRAY;
    //默认半径
    private final float DEFAULT_RADIUS=32;
    //自定义颜色
    private int mColor;
    private Paint mPaint;
    private float mRadius;
    private float mCenterX;
    private float mCenterY;

    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.PieChar);
        try {
            mColor=array.getColor(R.styleable.PieChar_circle_color,DEFAULT_COLOR);
        }
        finally {
            array.recycle();//销毁TypedArray，防止内存泄露
        }
        mPaint=new Paint();
        mPaint.setColor(mColor);
        mCenterX=mCenterY=mRadius=getMeasuredWidth()==0?DEFAULT_RADIUS/2:getMeasuredWidth()/2;
    }

    public float getRadius(){
        Log.d("TAG","Radius="+mRadius);
        return mRadius;
    }
    public int getColor(){
        return mColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mCenterX,mCenterY,mRadius,mPaint);
    }
}

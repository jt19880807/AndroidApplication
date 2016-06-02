package com.devinjiang.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by Devin.Jiang on 2016/3/10.
 */
public class PickerView extends View {


    private List<String> mDataList;
    private int mCurrentSelected;
    private Paint mPaint;
    private boolean isInit=false;
   private Timer timer;
    private int mColorText=0X333333;
    private static final float SPEED=2;
    private onSelectListener mSelectListener;

    private int mViewHeght;
    private int mViewWidth;

    /**
     * text之间间距和minTextSize之比
     */
    private static final float MARGIN_ALPHA = 2.8f;
    private float mMaxTextSize=80;
    private float mMinTextSize=40;
    private float mMaxTextAlpha=255;
    private float mMinTextAlpha=120;

    private float mMovelen=0;

    private MyTimerTask mMyTimerTask;

    Handler updateHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if (Math.abs(mMovelen)<SPEED)
            {
                mMovelen=0;
                if (mMyTimerTask!=null)
                {
                    mMyTimerTask.cancel();
                    mMyTimerTask=null;
                    performSelect();
                }

            }
            else
                mMovelen=mMovelen-mMovelen/Math.abs(mMovelen)*SPEED;
            invalidate();
        }
    };

    float mLastDownY;

    public PickerView(Context context) {
        super(context);
        init();
    }
    public PickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void performSelect()
    {
        if(mSelectListener!=null)
        {
            mSelectListener.onSelect(mDataList.get(mCurrentSelected));
        }
    }

    private void init()
    {
        timer=new Timer();
        mDataList=new ArrayList<String>();
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(mColorText);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeght=getMeasuredHeight();
        mViewWidth=getMeasuredWidth();
        //根据View的高度计算字体大小
        mMaxTextSize=mViewHeght/ 4.0f;
        mMinTextSize=mMaxTextSize/2.0f;
        isInit=true;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInit)
            drawData(canvas);
    }

    private void drawData(Canvas canvas) {

        float scale=parabola(mViewHeght/4.0f,mMovelen);
        float size=(mMaxTextSize-mMinTextSize)*scale+mMinTextSize;
        mPaint.setTextSize(size);
        mPaint.setAlpha((int)((mMaxTextAlpha-mMinTextAlpha)*scale+mMinTextAlpha));
        float x=(float)(mViewWidth/2.0);
        float y=(float)(mViewHeght/2.0+mMovelen);
        Paint.FontMetricsInt fmi=mPaint.getFontMetricsInt();
        float baseline = y - (fmi.bottom + fmi.top)/2;
        canvas.drawText(mDataList.get(mCurrentSelected),x,baseline,mPaint);
       // drawOtherText(canvas, 0, 0);//绘制当前选中的数值
        //绘制上方的数字
        for (int i=1;(mCurrentSelected-i)>=0;i++)
        {
            drawOtherText(canvas,i,-1);
        }
        //绘制下方的数字
        for (int i=1;(mCurrentSelected+i)<mDataList.size();i++)
        {
            drawOtherText(canvas,i,1);
        }

    }

    /**
     * 绘制上方或者下方的data
     * @param canvas 画布
     * @param position  距离中心Text的位置
     * @param type 1:绘制下方的data -1：绘制上方的data
     */
    private void drawOtherText(Canvas canvas,int position,int type)
    {
        float d=(MARGIN_ALPHA*mMinTextSize*position+type*mMovelen);
        float scale=parabola(mViewHeght/4.0f,d);
        float size=(mMaxTextSize-mMinTextSize)*scale+mMinTextSize;
        mPaint.setTextSize(size);
        mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
        float y=mViewHeght/2.0f+type*d;
        Paint.FontMetrics fmi=mPaint.getFontMetrics();
        float baseline=y-(fmi.top+fmi.bottom)/2;
        canvas.drawText(mDataList.get(mCurrentSelected + type * position), (float) (mViewWidth / 2), baseline, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
                doDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                doMove(event);
                break;
            case MotionEvent.ACTION_UP:
                doUp(event);
                break;
        }
        return true;
    }


    private void doUp(MotionEvent event) {
        if(Math.abs(mMovelen)<0.001)
        {
            mMovelen=0;
            return;
        }
        if (mMyTimerTask!=null)
        {
            mMyTimerTask.cancel();
            mMyTimerTask=null;
        }
        mMyTimerTask=new MyTimerTask(updateHandler);
        timer.schedule(mMyTimerTask,0,10);
    }

    private void doMove(MotionEvent event) {
        mMovelen+=(event.getY()-mLastDownY);

        mLastDownY=getY();
        invalidate();

    }

    private void doDown(MotionEvent event) {

        if (mMyTimerTask!=null)
        {
            mMyTimerTask.cancel();
            mMyTimerTask=null;
        }

//        if (mMoveLen > MARGIN_ALPHA * mMinTextSize / 2)
//        {
//            // 往下滑超过离开距离
//            moveTailToHead();
//            mMoveLen = mMoveLen - MARGIN_ALPHA * mMinTextSize;
//        } else if (mMoveLen < -MARGIN_ALPHA * mMinTextSize / 2)
//        {
//            // 往上滑超过离开距离
//            moveHeadToTail();
//            mMoveLen = mMoveLen + MARGIN_ALPHA * mMinTextSize;
//        }

        mLastDownY = event.getY();
    }

    public void setData(List<String> datas)
    {
        this.mDataList=datas;
        mCurrentSelected=datas.size()/2;
        invalidate();
    }

    /**
     * 抛物线
     * @param zero 原点
     * @param x 偏移量
     * @return scale
     */
    private float parabola(float zero,float x)
    {
        double aa=Math.pow(x/zero,2);
        float f=(float)(1-Math.pow(x/zero,2));
        return f<0?0:f;
    }

    public void setOnSelectListener(onSelectListener selectListener)
    {
        this.mSelectListener=selectListener;
    }

    class MyTimerTask extends TimerTask
    {
        Handler handler;

        public MyTimerTask(Handler handler)
        {
            this.handler=handler;
        }
        @Override
        public void run() {
            handler.sendMessage(handler.obtainMessage());
        }
    }
    public interface onSelectListener{
        void onSelect(String text);
    }
}

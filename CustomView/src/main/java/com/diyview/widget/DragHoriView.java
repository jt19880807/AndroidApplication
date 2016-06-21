package com.diyview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diyview.R;

import java.text.AttributedCharacterIterator;
import java.util.jar.Attributes;

/**
 * Created by Devin.Jiang on 2016-06-21.
 */
public class DragHoriView extends RelativeLayout implements View.OnTouchListener {
    private final String TAG=DragHoriView.class.getSimpleName();
    //默认字体大小
    private final float DEFAULT_TEXT_SIZE=16;
    //默认字体颜色
    private final int DEFAULT_TEXT_COLOR= Color.GRAY;
    //横向字节数量
    private int mHorNumber;
    //字体大小
    private float mTextSize;
    //字体颜色
    private int mTextColor;
    //每个节点的问题
    private String[] mTexts;
    //当前X坐标
    private float mCurX;
    //图片
    private Drawable mIcon;
    //Title底部的图片
    private Drawable mTypeIcon;
    private float mStartX;
    //可移动最小值
    private float mMinX;
    //可移动最大值
    private float mMaxX;
    //横线的宽度
    private int mHorWidth;
    //横线高度
    private int mHorHeight;
    //圆的半径
    private float mCRadius;
    //拖动控件的宽度(此处为直径)
    private float mDragWidth;
    //文字宽度
    private float mTextWidth;
    //文字高度
    private float mTextHeight;
    //控件的宽度
    private float mWidth;
    //文字距离顶部的距离
    private float mTextTop;
    //文字和拖动View的距离
    private float mCenterSpace;

    private int mSelectPositon;
    private int[] mRanges;

    private ImageView mDragView;


    public DragHoriView(Context context) {
        super(context);
    }

    public DragHoriView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragHoriView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.PieChar);
        try {
            mTextColor=array.getColor(R.styleable.PieChar_text_color,DEFAULT_TEXT_COLOR);
            mTextSize=array.getFloat(R.styleable.PieChar_text_size,DEFAULT_TEXT_SIZE);
            mIcon=array.getDrawable(R.styleable.PieChar_drag_icon);
            mTypeIcon=array.getDrawable(R.styleable.PieChar_type_icon);
            String texts=array.getString(R.styleable.PieChar_nodestext);
            if(texts!=null&&!texts.equals("")){
                mTexts=texts.split("，");
                mHorNumber=mTexts.length;
            }
        }
        finally {
            array.recycle();
        }
        initView(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        int count=getChildCount();
        mCenterSpace=dp2px(getContext(),8);


    }

    public int dp2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    private void initView(AttributeSet attrs){
        CircleView circleView=null;
        for (int i=0;i<mHorNumber;i++){
            //文字
            TextView textView=insTextView();
            textView.setText(mTexts[i]);
            //小圆点
            circleView=new CircleView(getContext(),attrs);
            addView(circleView);
            addView(textView);
            textView.setOnTouchListener(this);
            circleView.setOnTouchListener(this);
        }
        //横线
        View view=new View(getContext());
        view.setBackgroundColor(circleView.getColor());
        view.setOnTouchListener(this);
        addView(view);
        //可拖动view
        mDragView=new ImageView(getContext());
        mDragView.setImageDrawable(mIcon);
        mDragView.setOnTouchListener(this);
        addView(mDragView);
    }

    private TextView insTextView(){
        TextView textView=new TextView(getContext());
        textView.setTextColor(mTextColor);
        textView.setTextSize(mTextSize);
        return textView;
    }

}

package com.diyview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diyview.R;

/**
 * Created by Devin.Jiang on 2016-06-21.
 */
public class DragHoriView extends RelativeLayout implements View.OnTouchListener {
    private final String TAG="TAG";
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
    private int mCRadius;
    //拖动控件的宽度(此处为直径)
    private int mDragWidth;
    private int mDragHeight;
    //文字宽度
    private int mTextWidth;
    //文字高度
    private int mTextHeight;
    //控件的宽度
    private int mWidth;
    //文字距离顶部的距离
    private int mTextTop;
    //文字和拖动View的距离
    private int mCenterSpace;

    private int mSelectPosition;
    private int[] mRanges;

    private ImageView mDragView;


    public DragHoriView(Context context) {
        this(context,null);
    }

    public DragHoriView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
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
        for(int i=0;i<count;i++){
            View view=getChildAt(i);
            LayoutParams rParams;
            if (view instanceof CircleView){
                if(mCRadius==0){
                    mCRadius=(int)((CircleView)view).getRadius();
                }
            }
            else if(view instanceof TextView){
                if(mTextHeight==0){
                    mTextHeight=dp2px(getContext(),mTextSize+2);
                    String text=((TextView)view).getText().toString();
                    mTextWidth=dp2px(getContext(),mTextSize*text.length());
                }
            }
            else if(view instanceof ImageView){
                if ( mDragHeight== 0) {
                    mDragHeight = mDragWidth = dp2px(getContext(), 50);
                    rParams =new LayoutParams(mDragWidth,mDragHeight);
                    view.setLayoutParams(rParams);
                }
            }
            else {
                if (mHorWidth == 0) {
                    //横线
                    mHorWidth = mWidth * 3 / 8;
                    mHorHeight = mCRadius * 2 / 3;
                    rParams = new LayoutParams(mHorWidth, mHorHeight);
                    view.setLayoutParams(rParams);
                }
            }
            mTextTop = (height - (mTextHeight + mCenterSpace + mDragHeight)) / 2;

        }


    }

    public int dp2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public  int spTopx(Context context, float spValue)
    {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(changed){
            int count=getChildCount();
            int width=r-l;
            int space=mHorWidth/(mHorNumber-1);
            int dTop = mTextTop + mTextHeight + mCenterSpace;
            int cTop = dTop + mDragHeight / 2 - mCRadius;
            int hTop = dTop + mDragHeight / 2 - mHorHeight / 2;

            int hLeft = (mWidth - mHorWidth) / 2;
            int cLeft = hLeft - mCRadius;
            int tLeft = hLeft - mTextWidth / 2;
            int dLeft = hLeft - mDragWidth / 2;
            if (mRanges == null) {
                mRanges = new int[mHorNumber];
            }
            int j = 0;
            for (int i = 0; i < count; i++) {
                View view = getChildAt(i);

                if (view instanceof CircleView) {
                    cLeft = cLeft + space * j;
                    int cRight = cLeft + mCRadius * 2;
                    int cBottom = cTop + mCRadius * 2;

                    view.layout(cLeft, cTop, cRight, cBottom);

                    //赋值最小值为拖动空间本身的左边
                    mMinX = dLeft;
                    Log.v(TAG, "mMinX = " + mMinX);

                    mRanges[j] = (cLeft + cRight) / 2;
                    j++;
                } else if (view instanceof TextView) {
                    String text = ((TextView) view).getText().toString();
                    mTextWidth = spTopx(getContext(), mTextSize * text.length());
                    int tBottom = mTextTop + mTextHeight;
                    tLeft = tLeft + space * (j - 1);
                    int tRight = tLeft + mTextWidth;
                    view.layout(tLeft, mTextTop, tRight, tBottom);

                } else if (view instanceof ImageView) {

                    view.layout(dLeft, dTop, dLeft + mDragWidth, dTop + mDragHeight);

                } else {

                    //横线
                    view.layout(hLeft, hTop, hLeft + mHorWidth, hTop + mHorHeight);
                    //赋值最大值为横线的右边加上拖动控件的宽度的一半
                    mMaxX = width - hLeft - mDragWidth / 2;
                    Log.v(TAG, "mMaxX = " + mMaxX);
                }
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v instanceof ImageView) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    mStartX = (int) event.getRawX();
                    mCurX = v.getTranslationX();
                    Log.v(TAG, "mStartX = " + mStartX);
                    v.setPressed(true);
                    break;

                case MotionEvent.ACTION_MOVE:
                    float x = mCurX + event.getRawX() - mStartX;
                    if (x >= 0 && x <= mMaxX - mMinX) {
                        v.setTranslationX(mCurX + event.getRawX() - mStartX);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:

                    int max;
                    int min;
                    int length = mRanges.length;
                    float curX = v.getTranslationX();
                    for (int i = 0; i < length; i++) {
                        if (i > 0 && mRanges[i] - mMinX > curX) {
                            max = mRanges[i];
                            min = mRanges[i - 1];
                            float center = (max + min) / 2 - mMinX - mDragWidth / 2;
                            if (curX >= center) {
                                //超过一半
                                setAnim(max - mMinX - mDragWidth / 2, i);
                            } else if (curX < center) {
                                setAnim(min - mMinX - mDragWidth / 2, i - 1);
                            } else {
                                //刚好拖到节点
                                if (mOnNodeSelect != null) {
                                    if (mSelectPosition != i) {
                                        mOnNodeSelect.onNodeSelect(mSelectPosition);
                                    }
                                }
                            }
                            break;
                        }
                    }
                    v.setPressed(false);
                    break;
            }
        } else {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    int length = mRanges.length;

                    int nodeX = (v.getLeft() + v.getRight()) / 2;
                    for (int i = 0; i < length; i++) {
                        if (nodeX == mRanges[i]) {
                            setAnim(mRanges[i] - mMinX - mDragWidth / 2, i);
                            break;
                        }
                    }

                    break;
            }
        }
        return true;
    }

    private void setAnim(float moveX, final int scrollPosition) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mDragView, "translationX", mDragView.getTranslationX(), moveX);
        animator.setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mSelectPosition != scrollPosition) {
                    mSelectPosition = scrollPosition;
                    if (mOnNodeSelect != null) {
                        mOnNodeSelect.onNodeSelect(mSelectPosition);
                    }
                }
            }
        });
        animator.start();
    }


    private void initView(AttributeSet attrs){
        Log.d(TAG,"mHorNumber="+mHorNumber);
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

    private OnNodeSelect mOnNodeSelect;

    public void setNodeSelectListener(OnNodeSelect onNodeSelect) {
        mOnNodeSelect = onNodeSelect;
    }

    public interface OnNodeSelect {
        void onNodeSelect(int position);
    }

}

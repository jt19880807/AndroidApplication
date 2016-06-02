package com.diyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Devin.Jiang on 2016/3/8.
 */
public class PieChar extends View {
    boolean mShowText;
    int mTextPosition;

    public PieChar(Context context) {
        super(context);
    }

    public PieChar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.PieChar);
        try {
            mShowText=a.getBoolean(R.styleable.PieChar_custom_showText,false);
            mTextPosition=a.getInt(R.styleable.PieChar_lablePosition,0);
        }
        finally {
            a.recycle();//销毁TypeArray   防止内存泄露
        }
    }

}

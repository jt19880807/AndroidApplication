package jt.com.douban.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by JiangTao on 2016/6/26.
 */
public class Record extends ImageView {
    private Paint mOutPaint;
    private Paint mInnerPaint;
    private int mWidth;
    private int mheight;
    private int outRingWidth;//外环宽度
    private int innerRingWidth;//内环宽度
    private Context mContext;
    public Record(Context context) {
        this(context,null);
    }
    public Record(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public Record(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        mContext=context;
    }

    private void init() {
        mOutPaint=new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setColor(Color.BLACK);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setStrokeWidth(outRingWidth);
        mOutPaint.setFilterBitmap(true);
        mOutPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSpec(widthMeasureSpec),measureSpec(heightMeasureSpec));
    }

    private int measureSpec(int measureSpec){
        int mode=MeasureSpec.getMode(measureSpec);
        int size=MeasureSpec.getSize(measureSpec);
        int result=0;
        if (mode==MeasureSpec.EXACTLY) {//精确值
            result=size;
        }
        else {
            WindowManager manager = (WindowManager)mContext
                    .getSystemService(Context.WINDOW_SERVICE);
            int screenWidth=manager.getDefaultDisplay().getWidth();
            result=screenWidth*2/5;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=getWidth();
        mheight=getHeight();
        outRingWidth=mWidth/10;
        innerRingWidth=outRingWidth*3/4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Drawable drawable=getDrawable();
        Bitmap b=((BitmapDrawable)drawable).getBitmap();
        Bitmap bitmap=b.copy(Bitmap.Config.ARGB_8888,true);
       // Log.d("TAG","outRingWidth="+outRingWidth);
        mOutPaint.setStrokeWidth(outRingWidth);
        canvas.drawCircle(mWidth / 2, mWidth / 2,mWidth/ 2-outRingWidth/2, mOutPaint);//画外部圆环
        Bitmap roundBitmap =  getCroppedBitmap(bitmap, mWidth-2*outRingWidth);
        canvas.drawBitmap(roundBitmap, outRingWidth,outRingWidth, null);
        mOutPaint.setStrokeWidth(innerRingWidth);
        canvas.drawCircle(mWidth / 2, mWidth / 2,(float)innerRingWidth*3/2, mOutPaint);
    }

    private  Bitmap getCroppedBitmap(Bitmap bmp, int width) {
        Bitmap sbmp;
        if(bmp.getWidth() != width || bmp.getHeight() != width)
            sbmp = Bitmap.createScaledBitmap(bmp, width, width, false);
        else
            sbmp = bmp;
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
                sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        //final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());
        mInnerPaint=new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setFilterBitmap(true);
        mInnerPaint.setDither(true);
        mInnerPaint.setStrokeWidth(sbmp.getWidth()/2-innerRingWidth);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setColor(Color.BLACK);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2,
                (float)(sbmp.getWidth()/4+innerRingWidth/2), mInnerPaint);
        mInnerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, mInnerPaint);
        return output;
    }
}

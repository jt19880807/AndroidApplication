package jt.com.douban.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import jt.com.douban.ui.utils.FastBlur;

/**
 * Created by Devin.Jiang on 2016-07-07.
 */
public class BlurImageView extends ImageView {
    private int screenWidth;
    private int screenHeight;
    private Context mContext;
    public BlurImageView(Context context) {
        this(context,null);
    }

    public BlurImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BlurImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        WindowManager manager = (WindowManager)mContext
                .getSystemService(Context.WINDOW_SERVICE);
        screenWidth=manager.getDefaultDisplay().getWidth();
        screenHeight=manager.getDefaultDisplay().getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable=getDrawable();
        Bitmap b=((BitmapDrawable)drawable).getBitmap();
        Bitmap bitmap=b.copy(Bitmap.Config.ARGB_8888,true);

        long startMs = System.currentTimeMillis();
        float scaleFactor = 8;//图片缩放比例；
        float radius = 80;//模糊程度
        Bitmap overlay = Bitmap.createBitmap(
                (int) (getMeasuredWidth()/scaleFactor),
                (int) (getMeasuredHeight()/scaleFactor),
                Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        bitmap = FastBlur.doBlur(bitmap, (int) radius, true);
        Log.d("TAG","用时="+(System.currentTimeMillis()-startMs));
        bitmap=FitTheScreenSizeImage(bitmap,screenWidth,screenHeight);
        canvas.drawBitmap(bitmap, 0, 0, paint);

    }

    public static Bitmap FitTheScreenSizeImage(Bitmap m,int ScreenWidth, int ScreenHeight)
    {
        float width  = (float)ScreenWidth/m.getWidth();
        float height = (float)ScreenHeight/m.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(width,height);
        return Bitmap.createBitmap(m, 0, 0, m.getWidth(), m.getHeight(), matrix, true);
    }

//    http://www.douban.com/j/app/radio/channels
//    http://douban.fm/j/mine/playlist?type=n&channel=0&from=mainsite
//    http://douban.com/j/app/radio/people?app_name=radio_android&version=100&channel=1
}

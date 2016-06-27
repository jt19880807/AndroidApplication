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
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by JiangTao on 2016/6/26.
 */
public class Record extends ImageView {
    private Paint paint;
    public Record(Context context) {
        this(context,null);
    }
    public Record(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public Record(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(14);
        paint.setFilterBitmap(true);
        paint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Drawable drawable=getDrawable();
        Bitmap b=((BitmapDrawable)drawable).getBitmap();
        Bitmap bitmap=b.copy(Bitmap.Config.ARGB_8888,true);
        int w=getWidth(),h=getHeight();
//        canvas.drawCircle(w / 2, h / 2,
//                w/ 2-7, paint);
        Bitmap roundBitmap =  getCroppedBitmap(bitmap, w-14);

        canvas.drawBitmap(roundBitmap, 7,7, null);
    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if(bmp.getWidth() != radius || bmp.getHeight() != radius)
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        else
            sbmp = bmp;
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
                sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setStrokeWidth(sbmp.getWidth()/2-15);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2,
                sbmp.getWidth() / 2-7, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);
        //paint.setStrokeWidth(10);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.BLACK);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
//        canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2,
//                24, paint);
//        paint.setColor(Color.parseColor("#009688"));
//        canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2,
//                17, paint);

        return output;
    }
}

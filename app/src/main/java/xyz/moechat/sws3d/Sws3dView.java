package xyz.moechat.sws3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.List;


/**
 * Created by timeloveboy on 16/5/1.
 */
public class Sws3dView extends TextView {
    public Sws3dView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setWillNotDraw(false);
    }

    public int getRotate_X() {
        return rotate_X;
    }

    public void setRotate_X(int rotate_X) {
        this.rotate_X = rotate_X;
    }

    public int getRotate_Y() {
        return rotate_Y;
    }

    public void setRotate_Y(int rotate_Y) {
        this.rotate_Y = rotate_Y;
    }

    public int getRotate_Z() {
        return rotate_Z;
    }

    public void setRotate_Z(int rotate_Z) {
        this.rotate_Z = rotate_Z;
    }

    int rotate_X=80,rotate_Y=0,rotate_Z=0;
    float translate_X=0;
    float translate_Y=1000f;

    public float getTranslate_Z() {
        return translate_Z;
    }

    public void setTranslate_Z(float translate_Z) {
        this.translate_Z = translate_Z;
    }

    public float getTranslate_X() {
        return translate_X;
    }

    public void setTranslate_X(float translate_X) {
        this.translate_X = translate_X;
    }

    public float getTranslate_Y() {
        return translate_Y;
    }

    public void setTranslate_Y(float translate_Y) {
        this.translate_Y = translate_Y;
    }

    float translate_Z=1000f;
    protected void onDraw(Canvas canvas) {
        Camera c = new Camera();
        c.save();
        c.rotate(rotate_X, rotate_Y, rotate_Z);
        c.translate(translate_X, translate_Y, translate_Z);

        c.applyToCanvas(canvas);
        c.restore();
        super.onDraw(canvas);
    }

    //// TODO: 16/5/1  
    //region 滚动
    int Duration=50;

/*

* override the computeScroll to restart scrolling when finished so as that

* the text is scrolled forever

*/
    // scrolling feature
    private Scroller mSlr;
    private boolean mPaused = true;
    @Override
    public void computeScroll() {

        super.computeScroll();

        if (null == mSlr) return;

        if (mSlr.isFinished() && (!mPaused)) {

            this.startScroll();

        }

    }

    public void startScroll() {
        mPaused = true;
        resumeScroll();
    }
    int x,y=0;
    public void resumeScroll() {

        if (!mPaused)
            return;

        setVerticalScrollBarEnabled(true);
        mSlr = new Scroller(this.getContext(), new LinearInterpolator());
        setScroller(mSlr);

        setVisibility(VISIBLE);
        int distance=10;
        x=0;
        y+=distance;
        mSlr.startScroll(x,y,distance / 2,distance, Duration);

        mPaused = false;
    }
    //endregion
}


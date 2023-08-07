package com.data.fzplayer.main.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.data.fzplayer.R;

public class SoundViewModel extends View {
    private Paint paint;
    private RectF emptyrectf;
    private RectF fillrectf;
    private int currentprogess = 0;
    private int maxprogess = 100;
    private SoundProgressModel soundProgressChangeListner;

    public int getMaxprogess() {
        return maxprogess;
    }
    public void setOnsoundProgressChangeListner(SoundProgressModel soundProgressChangeListner){
        this.soundProgressChangeListner=soundProgressChangeListner;
    }



    public void setmRoundedCorners(int mRoundedCorners) {
        this.mRoundedCorners = mRoundedCorners;
        invalidate();
    }

    public void setProgesscolor(int progesscolor) {
        this.progesscolor = progesscolor;
        invalidate();
    }

    public void setCurrentblockprogesscolor(int currentblockprogesscolor) {
        this.currentblockprogesscolor = currentblockprogesscolor;
        invalidate();
    }

    public void setStepcolor(int stepcolor) {
        this.stepcolor = stepcolor;
        invalidate();
    }

    private float noofpart = 0;
    private int viewbackgroundcolor;
    private int mRoundedCorners;
    private int progesscolor;
    private int currentblockprogesscolor;
    private int stepcolor;
    public int getProgress() {
        return currentprogess;
    }

    public SoundViewModel(Context context) {
        this(context, null);
    }

    public SoundViewModel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public SoundViewModel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(attrs == null){
            return;
        }

      TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SoundView);
        mRoundedCorners = ta.getDimensionPixelSize(R.styleable.SoundView_cornerRadius,0);
//       noofpart=ta.getInteger(R.styleable.MyCustomView_noofpart,0);
       currentprogess=ta.getInteger(R.styleable.SoundView_progress,0);
      maxprogess=ta.getInteger(R.styleable.SoundView_maxprogress,100);
       viewbackgroundcolor=ta.getColor(R.styleable.SoundView_viewbackgroundcolor,Color.GRAY);
//       currentblockprogesscolor=ta.getColor(R.styleable.MyCustomView_currentblockprogesscolor,Color.parseColor("#fd6376"));
       progesscolor=ta.getColor(R.styleable.SoundView_progesscolor,Color.parseColor("#ff8598"));
//       stepcolor=ta.getColor(R.styleable.MyCustomView_stepcolor,Color.WHITE);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
    }

    public void progessDevide(float part) {
        noofpart = part;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {


        if (emptyrectf == null) emptyrectf = new RectF(0, 0, getWidth(), getHeight());

        Path clipPath = new Path();
        clipPath.addRoundRect(emptyrectf,mRoundedCorners,mRoundedCorners, Path.Direction.CW);
        canvas.clipPath(clipPath);
        paint.setColor(viewbackgroundcolor);
        canvas.drawRoundRect(emptyrectf,mRoundedCorners,mRoundedCorners,paint);
        paint.setColor(progesscolor);
        int currentprog=(currentprogess*getHeight())/maxprogess;
        canvas.drawRoundRect(0,getHeight(),getWidth(),getHeight()-currentprog,0,0,paint);


//        Path clipPath = new Path();
//        float radius=mRoundedCorners?emptyrectf.height()/2.0f:0;
//        clipPath.addRoundRect(emptyrectf,radius,radius,Path.Direction.CW);
//        canvas.clipPath(clipPath);
//        paint.setColor(viewbackgroundcolor);
//        canvas.drawRect(emptyrectf, paint);
//        float partwidth = (getWidth()) / noofpart;
//        float start = partwidth;
//        paint.setColor(progesscolor);
//        float fillprogess = (float) ((getWidth() * currentprogess) / maxprogess);
//        fillprogess= (float) (Math.round(fillprogess*1000.0)/1000.0);
//        if (fillprogess > partwidth) {
//            int fill = (int) (fillprogess / partwidth);
//            float quard = fill * partwidth;
//
//            fillrectf = new RectF(0, 0, quard, getHeight());
//            canvas.drawRect(fillrectf, paint);
//            float remain = (float) (fillprogess % partwidth);
//
//            paint.setColor(currentblockprogesscolor);
//            RectF rectF = new RectF(quard, 0, quard + remain, getHeight());
//            canvas.drawRect(rectF, paint);
//        } else {
//
//            fillrectf = new RectF(0, 0, fillprogess, getHeight());
//            canvas.drawRect(fillrectf, paint);
//        }
//        paint.setColor(stepcolor);
//        for (int i = 0; i < noofpart; i++) {
//            RectF rectF = new RectF(start, 0, start + 5, getHeight());
//            start = start + partwidth;
//            canvas.drawRect(rectF, paint);
//        }
        super.onDraw(canvas);

    }

    public void setProgress(int currentprogess) {
//        ValueAnimator valueAnimator=ValueAnimator.ofInt(this.currentprogess,currentprogess);
//        valueAnimator.setDuration(400);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                SoundView.this.currentprogess= (int) animation.getAnimatedValue();
//                invalidate();
//            }
//        });
//        valueAnimator.start();
        this.currentprogess=currentprogess;
        invalidate();
        soundProgressChangeListner.onchange(this.currentprogess);

    }

    public void setViewbackgroundcolor(int viewbackgroundcolor) {
        this.viewbackgroundcolor = viewbackgroundcolor;
        invalidate();
    }

    public void setMaxprogress(int maxprogess) {
        this.maxprogess = maxprogess;
        invalidate();
    }

}
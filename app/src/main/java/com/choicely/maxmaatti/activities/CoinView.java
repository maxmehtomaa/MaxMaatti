package com.choicely.maxmaatti.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class CoinView extends View {

    private static final String TAG = "CoinView";
    private final Paint paint = new Paint();
    private final Random random = new Random();
    private final List<PointF> list = new ArrayList<>();
    private boolean isRunning = false;

    public CoinView(Context context) {
        super(context);
        init();
    }

    public CoinView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createPoints(w, h);
    }

    public void createPoints(int width, int height) {
//        if(!list.isEmpty()) {
        // TODO: if we want to create points only once, then return this commented out part
//            return;
//        }
        int offset = 10*50;
        for (int i = 0; i < 10; i++) {
            PointF p = new PointF();
            list.add(p);
            int x = random.nextInt(width);
            int y = -random.nextInt(offset);
//            int y = 0;
            p.set(x, y);
        }
        Log.d(TAG, String.format("created %d items for size[%d, %d]", list.size(), width, height));
        startAnimation();
    }

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            postInvalidate();
            if (isRunning) {
                CoinView.this.postDelayed(update, 1000 / 60);
            }
        }
    };

    public void startAnimation() {
        isRunning = true;
        post(update);
    }

    public void stopAnimation() {
        isRunning = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (PointF p : list) {
            p.offset(0, 1);
            canvas.drawCircle(p.x, p.y, 50, paint);
        }
    }
}

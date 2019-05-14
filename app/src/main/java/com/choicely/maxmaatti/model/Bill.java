package com.choicely.maxmaatti.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.Gravity;

public class Bill {

    private Bitmap bitmap;
    private PointF point;

    public Bill() {
        this.point = new PointF();
    }


    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public PointF getPoint() {
        return point;
    }


    public void move(int gravity, int change) {
        if (gravity == Gravity.BOTTOM) {
            point.offset(0, change);
        } else if (gravity == Gravity.TOP) {
            point.offset(0, -change);
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, point.x, point.y, null);

    }
}

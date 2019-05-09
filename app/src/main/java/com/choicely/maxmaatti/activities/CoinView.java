package com.choicely.maxmaatti.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class CoinView extends View {

    Paint paint = new Paint();

    public CoinView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(10);
        canvas.drawRect(100, 100, 200, 200, paint);
    }
}

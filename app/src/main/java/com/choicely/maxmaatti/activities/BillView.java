package com.choicely.maxmaatti.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;

import com.choicely.maxmaatti.R;
import com.choicely.maxmaatti.model.Bill;

import java.util.ArrayList;
import java.util.Random;

public class BillView extends View {

    private Bitmap resized10Euros;
    private Bitmap resized20Euros;
    private Bitmap resized50Euros;
    private Bitmap resized100Euros;

    private final Paint paint = new Paint();
    private final Random random = new Random();
    private boolean isRunning = false;

    private final ArrayList<Bill> bills = new ArrayList<>();

    public BillView(Context context) {
        super(context);
        init();
    }

    public BillView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Bitmap tenEuros = BitmapFactory.decodeResource(getResources(), R.drawable.kisspng_10_euro);
        Bitmap twentyEuros = BitmapFactory.decodeResource(getResources(), R.drawable.kisspng_20_euro);
        Bitmap fiftyEuros = BitmapFactory.decodeResource(getResources(), R.drawable.kisspng_50_euro);
        Bitmap hundredEuros = BitmapFactory.decodeResource(getResources(), R.drawable.hundred_euros_bill);

        resized10Euros = Bitmap.createScaledBitmap(tenEuros, 350, 200, false);
        resized20Euros = Bitmap.createScaledBitmap(twentyEuros, 350, 200, false);
        resized50Euros = Bitmap.createScaledBitmap(fiftyEuros, 350, 200, false);
        resized100Euros = Bitmap.createScaledBitmap(hundredEuros, 350, 200, false);

        paint.setColor(Color.YELLOW);
        setOnClickListener(onGravityChangeListener);
        setClickable(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createBills(w, h);
    }

    private void createBills(int width, int height) {
        int offset = 10 * 50;

        for (int i = 0; i < 10; i++) {
            Bill bill = new Bill();
            bill.setBitmap(getRandomBitmap());
            bills.add(bill);
            int x = random.nextInt(width);
            int y = -random.nextInt(offset);
            bill.getPoint().set(x, y);
        }

        startAnimation();
    }

    private Bitmap getRandomBitmap() {
        int r = random.nextInt(4);
        Bitmap bm;

        switch (r) {
            default:
            case 0:
                bm = resized10Euros;
                break;
            case 1:
                bm = resized20Euros;
                break;
            case 2:
                bm = resized50Euros;
                break;
            case 3:
                bm = resized100Euros;
                break;
        }

        return bm;
    }

    int gravity = Gravity.BOTTOM;

    private OnClickListener onGravityChangeListener = v -> {
        if (gravity == Gravity.BOTTOM) {
            gravity = Gravity.TOP;
        } else {
            gravity = Gravity.BOTTOM;
        }
    };

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            for (Bill b : bills) {
                b.move(gravity, 3);
            }

            postInvalidate();
            if (isRunning) {
                BillView.this.postDelayed(update, 1000 / 60);
            }
        }
    };

    private void startAnimation() {
        isRunning = true;
        post(update);

    }

    private void stopAnimation() {
        isRunning = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Bill b : bills) {
            b.draw(canvas);
        }
    }
}

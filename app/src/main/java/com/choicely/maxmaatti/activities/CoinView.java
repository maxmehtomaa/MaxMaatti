package com.choicely.maxmaatti.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;

import com.choicely.maxmaatti.R;
import com.choicely.maxmaatti.model.Coin;

import java.util.ArrayList;
import java.util.Random;

public class CoinView extends View {

    private static final String TAG = "CoinView";

    private final Paint paint = new Paint();
    private final Random random = new Random();

    private boolean isRunning = false;

    private Bitmap resized5Cents;
    private Bitmap resized10Cents;
    private Bitmap resized20Cents;
    private Bitmap resized50Cents;
    private Bitmap resized1Euro;
    private Bitmap resized2Euro;

    private final ArrayList<Coin> coins = new ArrayList<>();

    public CoinView(Context context) {
        super(context);
        init();
    }

    public CoinView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Bitmap fiveCents = BitmapFactory.decodeResource(getResources(), R.drawable.kisspng_5_cent);
        Bitmap tenCents = BitmapFactory.decodeResource(getResources(), R.drawable.kisspng_10_cent);
        Bitmap twentyCents = BitmapFactory.decodeResource(getResources(), R.drawable.kisspng_20_cent);
        Bitmap fiftyCents = BitmapFactory.decodeResource(getResources(), R.drawable.kisspng_50cnt_euro);
        Bitmap oneEuro = BitmapFactory.decodeResource(getResources(), R.drawable.kisspng_1_euro);
        Bitmap twoEuro = BitmapFactory.decodeResource(getResources(), R.drawable.kisspng_2_euro);

        resized5Cents = Bitmap.createScaledBitmap(fiveCents, 200, 200, false);
        resized10Cents = Bitmap.createScaledBitmap(tenCents, 300, 300, false);
        resized20Cents = Bitmap.createScaledBitmap(twentyCents, 200, 200, false);
        resized50Cents = Bitmap.createScaledBitmap(fiftyCents, 200, 200, false);
        resized1Euro = Bitmap.createScaledBitmap(oneEuro, 200, 200, false);
        resized2Euro = Bitmap.createScaledBitmap(twoEuro, 200, 200, false);

        paint.setColor(Color.YELLOW);

        setOnClickListener(onGravityChangeListener);
        setClickable(true);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createCoins(w, h);
    }

    public void createCoins(int width, int height) {
        //if(!list.isEmpty()) {
        // TODO: if we want to create points only once, then return this commented out part
        //  return;
        //}
        int offset = 10 * 50;
        for (int i = 0; i < 10; i++) {
            Coin c = new Coin();
            c.setBitmap(getRandomBitmap());
            coins.add(c);
            int x = random.nextInt(width);
            int y = -random.nextInt(offset);
//            int y = 0;
            c.getPoint().set(x, y);
        }
        Log.d(TAG, String.format("created %d items for size[%d, %d]", coins.size(), width, height));
        startAnimation();
    }

    private Bitmap getRandomBitmap() {
        int r = random.nextInt(6);
        Bitmap bm;
        switch (r) {
            default:
            case 0:
                bm = resized1Euro;
                break;
            case 1:
                bm = resized2Euro;
                break;
            case 2:
                bm = resized50Cents;
                break;
            case 3:
                bm = resized20Cents;
                break;
            case 4:
                bm = resized10Cents;
                break;
            case 5:
                bm = resized5Cents;
                break;

        }
        return bm;
    }

    int gravity = Gravity.BOTTOM;

    private OnClickListener onGravityChangeListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (gravity == Gravity.BOTTOM) {
                gravity = Gravity.TOP;
            } else {
                gravity = Gravity.BOTTOM;
            }
        }
    };

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            for (Coin c : coins) {
                c.move(gravity, 2);
            }

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
        for (Coin c : coins) {
            c.draw(canvas);
            //canvas.drawCircle(p.x, p.y, 50, paint);
        }
    }
}

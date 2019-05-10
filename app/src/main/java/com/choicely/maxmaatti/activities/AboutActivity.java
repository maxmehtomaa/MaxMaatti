package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.choicely.maxmaatti.R;

public class AboutActivity extends AppCompatActivity {

    private TextView appNameTextView;
    private TextView appCreatorTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("About");
        setContentView(R.layout.activity_about);

        appNameTextView = findViewById(R.id.about_app_name_text_view);
        appCreatorTextView = findViewById(R.id.about_app_creator_text_view);
        appNameTextView.setTextColor(Color.RED);
        appCreatorTextView.setTextColor(Color.CYAN);

        blinkText();

    }

    public void blinkText() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        appNameTextView.startAnimation(anim);
        appCreatorTextView.startAnimation(anim);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}

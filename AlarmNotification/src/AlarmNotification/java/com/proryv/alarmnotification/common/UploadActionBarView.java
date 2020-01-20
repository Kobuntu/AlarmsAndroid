package com.proryv.alarmnotification.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.proryv.alarmnotification.R;

/**
 * Created by Ig on 27.06.13.
 */
public class UploadActionBarView extends FrameLayout {

    private final Animation mFadeAnimation;

    public UploadActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mFadeAnimation = AnimationUtils.loadAnimation(context, R.anim.fade);
    }

    public void animateBackground() {
        if (mFadeAnimation!=null)
        {
           // mFadeAnimation.ani

        }
    }

    public void stopAnimatingBackground() {

    }

    public void startProgress() {
        ((ImageView)findViewById(R.id.iv_action_upload)).setVisibility(GONE);
        ((ProgressBar) findViewById(R.id.pb_uploads_action)).setVisibility(VISIBLE);
    }

    public void stopProgress() {
        ((ProgressBar) findViewById(R.id.pb_uploads_action)).setVisibility(GONE);
        ((ImageView)findViewById(R.id.iv_action_upload)).setVisibility(VISIBLE);
    }

    //public stopProgress
}

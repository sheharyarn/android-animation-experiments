package me.sheharyar.animationexperiments;

import android.animation.Animator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.melnykov.fab.FloatingActionButton;


public class FabRotateExpand extends ActionBarActivity {
    private FloatingActionButton fab;
    private RelativeLayout secret;
    private RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_rotate_expand);

        secret = (RelativeLayout)       findViewById (R.id.secretView);
        root   = (RelativeLayout)       findViewById (R.id.rootView);
        fab    = (FloatingActionButton) findViewById (R.id.fab);

    }

    public void fabPressed(View view) {
        AnimationSet anims = new AnimationSet(true);
        final int ANIM_TIME = 700;


        // Rotate Anim
        RotateAnimation rotate = new RotateAnimation(0,360*2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


        // Translate Anim
        int fabOrig[] = new int[2];
        fab.getLocationOnScreen(fabOrig);

        int fabDiffX = (fab.getMeasuredWidth()/2);
        final int fabDestX = root.getLeft() + root.getMeasuredWidth()/2;
        final int fabDestY = root.getTop() + root.getMeasuredHeight()/2;

        TranslateAnimation translate = new TranslateAnimation(0, fabDestX - fabOrig[0] - fabDiffX, 0, fabDestY - fabOrig[1]);

        anims.addAnimation(rotate);
        anims.addAnimation(translate);
        anims.setDuration(ANIM_TIME);
        anims.setFillAfter(false);

        anims.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... unused) {
                        SystemClock.sleep(ANIM_TIME - 250);
                        return null;
                    }
                    protected void onPostExecute(Void unused) {
                        secret.setVisibility(View.VISIBLE);
                        fab.setVisibility(View.INVISIBLE);
                        fab.setEnabled(false);

                        float radius = Math.max(secret.getWidth(), secret.getHeight()) * 2.0f;
                        Animator reveal = ViewAnimationUtils.createCircularReveal(secret, fabDestX, fabDestY, 0, radius);
                        reveal.setInterpolator(new AccelerateInterpolator(2.0f));
                        reveal.setDuration(ANIM_TIME - 200);
                        reveal.start();
                    }
                }.execute();
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {}
        });



        fab.startAnimation(anims);
    }

}

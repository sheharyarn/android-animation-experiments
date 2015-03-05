package me.sheharyar.animationexperiments;

import android.animation.Animator;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;


public class FabRotateExpand extends ActionBarActivity {
    private static int VIBRATOR_VALUE = 20;
    private FloatingActionButton fab;
    private RelativeLayout secret;
    private RelativeLayout root;
    private Vibrator vibrator;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_rotate_expand);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        message = (TextView)             findViewById (R.id.mainMessage);
        secret  = (RelativeLayout)       findViewById (R.id.secretView);
        root    = (RelativeLayout)       findViewById (R.id.rootView);
        fab     = (FloatingActionButton) findViewById (R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabShortPress();
                vibrate();
                message.setText("Hold Me");
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fabLongPress();
                vibrate();
                return false;
            }
        });

    }

    private void fabShortPress() {
//        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
//        fab.startAnimation(shake);

        final int deltaY = 200;

        TranslateAnimation jump = new TranslateAnimation(0,0,0, -deltaY);
        jump.setFillAfter(true);
        jump.setDuration(400);
        jump.setInterpolator(new DecelerateInterpolator(2.0f));

        jump.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation)  {}
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {
                TranslateAnimation bounceBack = new TranslateAnimation(0,0,-deltaY, 0);
                bounceBack.setFillAfter(true);
                bounceBack.setDuration(500);
                bounceBack.setInterpolator(new BounceInterpolator());

                fab.startAnimation(bounceBack);
            }
        });

        fab.startAnimation(jump);
    }

    private void fabLongPress() {
        fab.setEnabled(false);
        AnimationSet anims = new AnimationSet(true);
        final int ANIM_TIME = 500;


        // Rotate Anim
        RotateAnimation rotate = new RotateAnimation(0, 360*2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


        // Translate Anim
        int fabOrig[] = new int[2];
        fab.getLocationOnScreen(fabOrig);

        final int fabDiffX = (fab.getMeasuredWidth()/2);
        final int fabDestX = root.getLeft() + root.getMeasuredWidth()/2;
        final int fabDestY = root.getTop() + root.getMeasuredHeight()/2;

        TranslateAnimation translate = new TranslateAnimation(0, fabDestX - fabOrig[0] - fabDiffX, 0, fabDestY - fabOrig[1] - fabDiffX);

        anims.addAnimation(rotate);
        anims.addAnimation(translate);
        anims.setDuration(ANIM_TIME);
        anims.setFillAfter(false);

        anims.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... unused) {
                        SystemClock.sleep(ANIM_TIME - 125);
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


    private void vibrate() {
        vibrator.vibrate(VIBRATOR_VALUE);
    }

}

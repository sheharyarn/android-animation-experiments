package me.sheharyar.animationexperiments;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.melnykov.fab.FloatingActionButton;


public class FabRotateExpand extends ActionBarActivity {
    private FloatingActionButton fab;
    private RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_rotate_expand);

        root = (RelativeLayout)       findViewById (R.id.rootView);
        fab  = (FloatingActionButton) findViewById (R.id.fab);

    }

    public void fabPressed(View view) {
        AnimationSet anims = new AnimationSet(true);


        // Rotate Anim
        RotateAnimation rotate = new RotateAnimation(0,360*3, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


        // Translate Anim
        int fabOrig[] = new int[2];
        fab.getLocationOnScreen(fabOrig);

        int fabDiffX = (fab.getMeasuredWidth()/2);
        int fabDestX = root.getLeft() + root.getMeasuredWidth()/2;
        int fabDestY = root.getTop() + root.getMeasuredHeight()/2;

        TranslateAnimation translate = new TranslateAnimation(0, fabDestX - fabOrig[0] - fabDiffX, 0, fabDestY - fabOrig[1]);

        anims.addAnimation(rotate);
        anims.addAnimation(translate);
        anims.setDuration(700);
        anims.setFillAfter(true);
        anims.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {}
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationEnd(Animation animation) {

            }
        });


        fab.startAnimation(anims);
    }

}

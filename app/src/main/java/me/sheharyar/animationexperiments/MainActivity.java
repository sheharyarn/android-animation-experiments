package me.sheharyar.animationexperiments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void experiment1(View view) {
        // Fab Rotate Expand
        Intent intent = new Intent(this, FabRotateExpand.class);
        startActivity(intent);
    }

}

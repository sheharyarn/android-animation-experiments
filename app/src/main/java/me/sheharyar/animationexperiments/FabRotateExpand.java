package me.sheharyar.animationexperiments;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;


public class FabRotateExpand extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_rotate_expand);
    }

    public void fabPressed(View view) {
        Toast.makeText(getApplicationContext(), "FAB PRESSED", Toast.LENGTH_SHORT).show();
    }

}

package com.altsoft.framework.module;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.altsoft.framework.Global;

import java.util.ArrayList;

@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity {

    public static ArrayList<Activity> actList = new ArrayList<Activity>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Global.setCurrentActivity(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Global.setCurrentActivity(this);
      /*  Global.setFragmentManager(getSupportFragmentManager());
        if (Global.getCommon().getComponentName(this).toLowerCase() != "mainactivity") {
            actList.add(this);
        }*/
    }



    public void onInitToolbar(Toolbar toolBar, String title, int icon, boolean displayHome) {

        if (toolBar != null) {
            setSupportActionBar(toolBar);
            ActionBar actionBar = getSupportActionBar();

            if (actionBar != null) {
                actionBar.setTitle(title);
                actionBar.setDisplayShowHomeEnabled(displayHome);
                actionBar.setDisplayHomeAsUpEnabled(displayHome);
                if (icon != -1 && displayHome) {
                    toolBar.setNavigationIcon(ContextCompat.getDrawable(this, icon));
                }
            }
        }
    }

    public void appBarInit_titleOnly(String title) {
        appBarInit(title, true, false);
    }

    /// AppBar 셋팅
    public void appBarInit(String title, Boolean bTitle, Boolean bBookmark) {
        /*TextView tvTitle = findViewById(R.id.tvTitle);
        if(title != null) tvTitle.setText(title);

        if(!(bTitle == null || bTitle == true))
            tvTitle.setVisibility(View.GONE);
        if(!(bBookmark == null || bBookmark == true))
            findViewById(R.id.btnBookmark).setVisibility(View.GONE);*/
    }
}

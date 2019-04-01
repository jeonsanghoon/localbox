package com.altsoft.framework.DataInfo;

import android.widget.Toast;

import com.altsoft.framework.Global;

public class StringInfo {
    public String padRight(String s, int n, char padding){
        StringBuilder builder = new StringBuilder(s.length() + n);
        builder.append(s);
        for(int i = 0; i < n- s.length(); i++){
            builder.append(padding);
        }
        return builder.toString();
    }

    public String padLeft(String s, int n,  char padding) {
        StringBuilder builder = new StringBuilder(s.length() + n);
        for(int i = 0; i < n - s.length(); i++){
            builder.append(Character.toString(padding));
        }
        return builder.append(s).toString();
    }

    public void MessageShow(String text)
    {
        Toast.makeText(Global.getCurrentActivity(),text, Toast.LENGTH_LONG).show();
    }
}

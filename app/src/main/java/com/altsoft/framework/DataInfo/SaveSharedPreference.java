package com.altsoft.framework.DataInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.altsoft.framework.Global;
import com.altsoft.localbox.MainActivity;

/**
* SharedPreferences 데이터저장
* @author 전상훈
* @version 1.0.0
* @since 2019-03-22 오후 1:27
**/

public class SaveSharedPreference {
    private SharedPreferences getSharedPreferences(Context ctx) {

        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public  void setData( String Key, String value) {
        this.setData(Global.getCurrentActivity(), Key,value);
    }
    // 계정 정보 저장
    public  void setData(Context ctx, String Key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Key, value);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public  String getData( String Key) {
        return getData(Global.getCurrentActivity(), Key);
    }
    // 저장된 정보 가져오기
    public  String getData(Context ctx, String Key) {
        return getSharedPreferences(ctx).getString(Key, "");
    }
    // 로그아웃
    public  void clearData() {
        this.clearData(Global.getCurrentActivity());
    }
    // 로그아웃
    public  void clearData(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}

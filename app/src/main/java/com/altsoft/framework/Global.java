package com.altsoft.framework;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.altsoft.Interface.ServiceInfo;
import com.altsoft.Interface.localboxService;
import com.altsoft.framework.DataInfo.AuthInfo;
import com.altsoft.framework.DataInfo.DotNetDateConverter;
import com.altsoft.framework.DataInfo.SaveSharedPreference;
import com.altsoft.framework.DataInfo.StringInfo;
import com.altsoft.model.LOGGAL_BOX_AUTH;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Global {

    static Common _common;
    public static Common getCommon ( ) {
        if (_common == null) {
            _common = new Common();
        }
        return _common;
    }
    static StringInfo _stringInfo;
    public static StringInfo getStringInfo ( ) {
        if (_stringInfo == null) {
            _stringInfo = new StringInfo();
        }
        return _stringInfo;
    }

    static SaveSharedPreference _saveSharedPreference;
    /// 안드로이드 정보저장
    public static SaveSharedPreference getSaveSharedPreference ( ) {
        if (_saveSharedPreference == null) {
            _saveSharedPreference = new SaveSharedPreference();
        }
        return _saveSharedPreference;
    }

    private static LOGGAL_BOX_AUTH loggal_box_auth;
    public static void setDeviceInfo(LOGGAL_BOX_AUTH data){
        loggal_box_auth = data;
        Global.getSaveSharedPreference().setData(Global.getCurrentActivity(), "LOGGAL_BOX_AUTH", new Gson().toJson(data));

    }
    public static LOGGAL_BOX_AUTH getDeviceInfo(){

        try {
            if (Global.getSaveSharedPreference().getData(Global.getCurrentActivity(), "LOGGAL_BOX_AUTH") != null) {
                loggal_box_auth = new Gson().fromJson(Global.getSaveSharedPreference().getData(Global.getCurrentActivity(), "LOGGAL_BOX_AUTH"), LOGGAL_BOX_AUTH.class);
            }
        }catch(Exception ex){}
        if(loggal_box_auth == null) loggal_box_auth = new LOGGAL_BOX_AUTH();
        return loggal_box_auth;
    }

    static ServiceInfo _CallService;
    public static ServiceInfo getCallService ( ) {
        if (_CallService == null) {
            _CallService = new ServiceInfo();
        }
        return _CallService;
    }
    static localboxService _apiservice;
    public static localboxService getAPIService()
    {
        if(_apiservice == null)
        {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new DotNetDateConverter());

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Accept", "application/vnd.github.v3.full+json")
                            .addHeader("User-Agent", "Retrofit-MobileService")
                            ;

                    Request request = requestBuilder.build();

                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.build();
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.altsoft.ze.am")
                    .client(client)

                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            _apiservice = retrofit.create(localboxService.class);
        }
        return _apiservice;
    }

    static Activity _currentactivity;
    public static void setCurrentActivity(Activity activity) {
        _currentactivity = activity;
    }
    public static Activity getCurrentActivity() {
        return _currentactivity;
    }
    static AuthInfo _authInfo;
    public static AuthInfo getAuthInfo ( ) {
        if (_authInfo == null) {
            _authInfo = new AuthInfo();
        }
        return _authInfo;
    }


    public static String GetDevicesUUID(Activity activity) {
        boolean isPermission = false;
        String tmDevice, tmSerial, androidId;
        // 마시멜로우 이상
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            // 권학을 획득하지 않았다면
            if (activity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            {
                // 권한 요청
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 123);
                Toast.makeText(activity,"기기권한이 없어 종료합니다.", Toast.LENGTH_LONG);
                activity.finish();;
                return "";

            }

        }

        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);

        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(activity.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();

        return deviceId;

    }

}

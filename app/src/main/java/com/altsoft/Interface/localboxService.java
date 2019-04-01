package com.altsoft.Interface;

import com.altsoft.model.LOGGAL_BOX_AUTH;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface localboxService {
    
    /**
    * 기기정보가져오기
    * @author 전상훈
    * @version 1.0.0
    * @since 2019-03-20 오전 11:36
    **/
    
    @POST("/api/loggalBox/GetLocalboxAuthNumber")
    Call<List<LOGGAL_BOX_AUTH>> GetLocalboxAuth(@Body LOGGAL_BOX_AUTH Cond);
    
    /**
    * 기기인증번호 채번
    * @author 전상훈
    * @version 1.0.0
    * @since 2019-03-20 오전 11:35
    **/
    @POST("/api/loggalBox/GetloggalBoxAuthNumber")
    Call<Long> GetLoggalBoxAuthAutoNumber(@Body LOGGAL_BOX_AUTH Cond);
}

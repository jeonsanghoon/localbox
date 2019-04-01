package com.altsoft.model;

public class LOGGAL_BOX_AUTH {
    public Long AUTH_NUMBER;
    /// 인증 유형(2:로컬박스 3:로컬사인), T_COMMON 테이블의 L003
    public Integer AUTH_TYPE = 2;
    public String DEVICE_NUMBER ;
    public Long DEVICE_CODE ;
    public Long SIGN_CODE ;
}

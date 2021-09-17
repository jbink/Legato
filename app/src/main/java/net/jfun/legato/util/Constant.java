package net.jfun.legato.util;

import android.Manifest;

public class Constant {

    public static final String[] PERMISSION_LIST = new String[]{
            Manifest.permission.CAMERA};


    public static final int APP_TITLE_LEFT_EMPTY            = 0;
    public static final int APP_TITLE_LEFT_BACK             = 1;
    public static final int APP_TITLE_LEFT_CLOSE            = 2;

    public static final String DEVICE_NAME = "MyRoaster";

    public static final String PREF_ID = "login_id";
    public static final String PREF_PW = "login_pw";
    public static final String PREF_LANGUAGE = "set_language";
    public static final String PREF_INTERVAL = "refresh_interval";
    public static final String PREF_INTERVAL_TEXT = "refresh_interval_text";
    public static final String PREF_PUSH_TOKEN = "push_token";
    public static final String PREF_CHANGE_PART = "change_part";

    public static final int API_RESPONSE_SUCCESS_3 = 3;
    public static final int API_RESPONSE_SUCCESS_2 = 2;
    public static final int API_RESPONSE_SUCCESS = 1;
    public static final int API_RESPONSE_ERROR_0 = 0;
    public static final int API_RESPONSE_ERROR_1 = -1;
    public static final int API_RESPONSE_ERROR_2 = -2;
    public static final int API_RESPONSE_ERROR_3 = -3;


    public static final int REQ_WARNING_ACTIVITY = 8887;
    public static final int REQ_MAKE_PROFILE_PRO = 8888;
    public static final int REQ_MAKE_PROFILE_QR = 8889;
    public static final int REQ_WITHDRAW = 8890;
    public static final int REQ_SET_MYPAGE = 8891;
    public static final int REQ_SET_SETTING = 8892;
    public static final int REQ_SAVE_DATA = 8893;

    public static final int UPLOAD_OBJECT_TYPE_FOOD_MATERIAL = 0;
    public static final int UPLOAD_OBJECT_TYPE_STORE         = 1;
    public static final int UPLOAD_OBJECT_TYPE_MENU          = 2;

    public static final String API_RESPONSE_YES = "Y";
    public static final String API_RESPONSE_NO = "N";

    public static final String FD = "-3";
    public static final String FE = "-2";
    public static final String KR = "kr";
    public static final String EN = "en";


    public static final String PROFILE_TYPE_BASIC  = "basic"; //BASIC
    public static final String PROFILE_TYPE_PRO    = "pro"; //PRO
    public static final String PROFILE_TYPE_QR     = "qr"; //QR
    public static final String PROFILE_TYPE_SELECT = "select"; //SELECT

    public static final String PROFILE_TERM_SHORTTIME  = "short time"; //SHORTTIME
    public static final String PROFILE_TERM_LONGTIME   = "long time"; //LONGTIME
    public static final String PROFILE_TERM_EXTREME_LONGTIME   = "extreme long time"; //extreme LONGTIME


    //10분
//    public static final int MINUTE_10               = 600000; // 1000(1초) * 60(1분) * 10(10분)
    public static final int MIN_CLICK_INTERVAL               = 500; // 더블 클릭 방지 시간


    public static final String EXTRA_TUTORIAL = "tutorial";
    public static final String EXTRA_PROFILE_TUTORIAL_BASIC = "tutorial_profile_basic";
    public static final String EXTRA_TUTORIAL_BASIC = "tutorial_basic";
    public static final String EXTRA_TUTORIAL_PRO = "tutorial_pro";
    public static final String EXTRA_TUTORIAL_HISTORY = "tutorial_history";
    public static final String EXTRA_TUTORIAL_QR = "tutorial_qr";
    public static final String EXTRA_TUTORIAL_SETTING = "tutorial_setting";
    public static final String EXTRA_TUTORIAL_BASIC_INFO = "tutorial_basic_info";
    public static final String EXTRA_TUTORIAL_BASIC_INFO_I = "tutorial_basic_info_index";
    public static final String EXTRA_PROFILE_UID = "profile_uid";
    public static final String EXTRA_PROFILE_TYPE = "profile_type";
    public static final String EXTRA_PROFILE_QR = "profile_qr";
    public static final String EXTRA_SAVE_DATA_NAME = "save_data_name";
    public static final String EXTRA_URL = "url";

    public static final String EXTRA_TUTORIAL_MAIN = "tutorial_main";
}

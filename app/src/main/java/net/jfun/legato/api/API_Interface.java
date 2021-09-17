package net.jfun.legato.api;

import net.jfun.legato.history.HistoryListApiDTO;
import net.jfun.legato.history.mode.ModeListDTO;
import net.jfun.legato.login.SignupDTO;
import net.jfun.legato.roast.profile.ProfileDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API_Interface {

    //회원가입
    @FormUrlEncoded
    @POST("/api/Member/Join")
    Call<SignupDTO> join(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("birth") String birth,
            @Field("phone") String phone,
            @Field("deviceid") String deviceid,
            @Field("macaddress") String macaddress,
            @Field("is14YearsOlder") boolean is14YearsOlder,
            @Field("isAcountTerms") boolean isAcountTerms,
            @Field("isIntegService") boolean isIntegService,
            @Field("isAddChannel") boolean isAddChannel,
            @Field("isPrivacy") boolean isPrivacy,
            @Field("isLocation") boolean isLocation,
            @Field("isAddProfile") boolean isAddProfile,
            @Field("addressType") String addressType,
            @Field("basicAddress") String basicAddress,
            @Field("detailAddress") String detailAddress,
            @Field("zipCode") String zipCode);

    //로그인
    @FormUrlEncoded
    @POST("/api/Member/Login")
    Call<SignupDTO> login(
            @Field("email") String email,
            @Field("password") String password);

    //아이디 찾기
    @FormUrlEncoded
    @POST("/api/Member/FindEmail")
    Call<BaseDTO> findEmail(
            @Field("Phone") String phone);

    //비밀번호 찾기
    @FormUrlEncoded
    @POST("/api/Member/FindPw")
    Call<BaseDTO> findPw(
            @Field("email") String email,
            @Field("phone") String phone);

    //인증번호 요청
    @FormUrlEncoded
    @POST("/api/Member/SendAuthCode")
    Call<BaseDTO> sendAuthCode(
            @Field("Receiver") String Receiver);

    //인증번호 확인
    @FormUrlEncoded
    @POST("/api/Member/ConfirmAuthCode")
    Call<BaseDTO> confirmAuthCode(
            @Field("Email") String email,
            @Field("Code") String code);

    //프로필 등록/수정 - 등록 할 때는 uid 값을 0으로 셋팅
    @FormUrlEncoded
    @POST("/api/Profile/SetProfilePro")
    Call<BaseDTO> setProfilePro(
            @Field("uid") String uid, // 등록 시 0
            @Field("memberUid") String memberUid,
            @Field("profileName") String profileName,
            @Field("profileType") String profileType,
            @Field("targetTemperature") String targetTemperature,
            @Field("Startbit_0") String Startbit_0,
            @Field("Startbit_1") String Startbit_1,
            @Field("Pz_2") String Pz_2,
            @Field("Nyz_3") String Nyz_3,
            @Field("Gxyz_4") String Gxyz_4,
            @Field("Gxyz_5") String Gxyz_5,
            @Field("Ayz_6") String Ayz_6,
            @Field("Byz_7") String Byz_7,
            @Field("Cyz_8") String Cyz_8,
            @Field("Dyz_9") String Dyz_9,
            @Field("Uwxyz_10") String Uwxyz_10,
            @Field("Rz_11") String Rz_11,
            @Field("Rz_12") String Rz_12,
            @Field("XOR_13") String XOR_13,
            @Field("Stopbit_14") String Stopbit_14,
            @Field("Stopbit_15") String Stopbit_15,
            @Field("inputQuantity") String inputQuantity,
            @Field("beanName") String beanName,
            @Field("notes") String notes,
            @Field("expectedEndTime") String expectedEndTime);

    //프로필 목록
    @FormUrlEncoded
    @POST("/api/Profile/GetProfileList")
    Call<ModeListDTO> getProfileList(
            @Field("memberUid") String memberUid);

    //Profile Pro 등록/수정
    @FormUrlEncoded
    @POST("/api/Profile/SetProfilePro")
    Call<BaseDTO> setProfilePro(
            @Field("profileUid") String profileUid,
            @Field("memberUid") String memberUid);


    //히스토리 데이터 목록
    @FormUrlEncoded
    @POST("/api/History/GetHistoryDataList")
    Call<HistoryListApiDTO> getHistoryDataList(
            @Field("profileUid") String profileUid,
            @Field("profileType") String profileType,
            @Field("memberUid") String memberUid);

    //비밀번호 변경
    @FormUrlEncoded
    @POST("/api/Member/SetPassword")
    Call<BaseDTO> setPassword(
            @Field("password") String password,
            @Field("memberUid") String memberUid);

    //프로필 찜하기
    @FormUrlEncoded
    @POST("/api/Profile/UpdateProfileIsWish")
    Call<BaseDTO> updateProfileIsWish(
            @Field("uid") String uid,
            @Field("memberUid") String memberUid,
            @Field("profileType") String profileType,
            @Field("isWish") boolean isWish);

    //프로필 삭제
    @FormUrlEncoded
    @POST("/api/Profile/DeleteProfile")
    Call<BaseDTO> deleteProfile(
            @Field("uid") String uid,
            @Field("memberUid") String memberUid,
            @Field("profileType") String profileType);

    //프로필 상세
    @FormUrlEncoded
    @POST("/api/Profile/GetProfile")
    Call<ProfileDTO> getProfile(
            @Field("profileUid") String uid,
            @Field("memberUid") String memberUid,
            @Field("profileType") String profileType);

    //Mac Address 체크
    @FormUrlEncoded
    @POST("/api/Member/CheckMacAddress")
    Call<BaseDTO> checkMacAddress(
            @Field("macaddress") String macaddress,
            @Field("deviceid") String deviceid,
            @Field("email") String email);

    //Mac Address 변경
    @FormUrlEncoded
    @POST("/api/Member/SetMacAddress")
    Call<BaseDTO> setMacAddress(
            @Field("macaddress") String macaddress,
            @Field("deviceid") String deviceid,
            @Field("email") String email);

    ///프로필 정보 QR
    @FormUrlEncoded
    @POST("/api/Profile/GetProfileQrInfo")
    Call<ProfileDTO> getProfileQrInfo(
            @Field("qrCode") String qrCode,
            @Field("memberUid") String memberUid);

    ///히스토리 저장
    @FormUrlEncoded
    @POST("/api/History/SetHistoryData")
    Call<BaseDTO> setHistoryData(
            @Field("historydata") String historydata,
            @Field("memberUid") String memberUid);

    ///히스토리 저장
    @FormUrlEncoded
    @POST("/api/Profile/SetProfileQr")
    Call<BaseDTO> setProfileQr(
            @Field("profileName") String profileName,
            @Field("memberUid") String memberUid);

    ///로스터기 사용횟수 업데이트
    @FormUrlEncoded
    @POST("/api/Member/UpdateMemberUseCount")
    Call<BaseDTO> updateMemberUseCount(
            @Field("uid") String uid,
            @Field("useCount") String useCount);


    //회원탈퇴
    @FormUrlEncoded
    @POST("/api/Member/Withdraw")
    Call<BaseDTO> withdraw(
            @Field("memberUid") String memberUid);
}
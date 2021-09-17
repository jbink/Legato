package net.jfun.legato.login;

public class SignupDTO {


    /**
     * result : 1
     * message : 로그인되었습니다.
     * content : {"uid":5,"email":"test02@test.com","password":"123456","name":"테스터02","birth":"000102","phone":"01000000002","deviceID":"deviceId-0001","macAddress":"98:D3:C1:FD:86:68","is14YearsOlder":true,"isAcountTerms":true,"isIntegService":true,"isAddChannel":true,"isPrivacy":true,"isLocation":false,"isAddProfile":true,"addressBasic":"인천시","addressDetail":"부평구","zipCode":"0000","useCount":0}
     */

    private int result;
    private String message;
    private ContentBean content;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * uid : 5
         * email : test02@test.com
         * password : 123456
         * name : 테스터02
         * birth : 000102
         * phone : 01000000002
         * deviceID : deviceId-0001
         * macAddress : 98:D3:C1:FD:86:68
         * is14YearsOlder : true
         * isAcountTerms : true
         * isIntegService : true
         * isAddChannel : true
         * isPrivacy : true
         * isLocation : false
         * isAddProfile : true
         * addressBasic : 인천시
         * addressDetail : 부평구
         * zipCode : 0000
         * useCount : 0
         */

        private int uid;
        private String email;
        private String password;
        private String name;
        private String birth;
        private String phone;
        private String deviceID;
        private String macAddress;
        private boolean is14YearsOlder;
        private boolean isAcountTerms;
        private boolean isIntegService;
        private boolean isAddChannel;
        private boolean isPrivacy;
        private boolean isLocation;
        private boolean isAddProfile;
        private String addressBasic;
        private String addressDetail;
        private String zipCode;
        private int useCount;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBirth() {
            return birth;
        }

        public void setBirth(String birth) {
            this.birth = birth;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDeviceID() {
            return deviceID;
        }

        public void setDeviceID(String deviceID) {
            this.deviceID = deviceID;
        }

        public String getMacAddress() {
            return macAddress;
        }

        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }

        public boolean isIs14YearsOlder() {
            return is14YearsOlder;
        }

        public void setIs14YearsOlder(boolean is14YearsOlder) {
            this.is14YearsOlder = is14YearsOlder;
        }

        public boolean isIsAcountTerms() {
            return isAcountTerms;
        }

        public void setIsAcountTerms(boolean isAcountTerms) {
            this.isAcountTerms = isAcountTerms;
        }

        public boolean isIsIntegService() {
            return isIntegService;
        }

        public void setIsIntegService(boolean isIntegService) {
            this.isIntegService = isIntegService;
        }

        public boolean isIsAddChannel() {
            return isAddChannel;
        }

        public void setIsAddChannel(boolean isAddChannel) {
            this.isAddChannel = isAddChannel;
        }

        public boolean isIsPrivacy() {
            return isPrivacy;
        }

        public void setIsPrivacy(boolean isPrivacy) {
            this.isPrivacy = isPrivacy;
        }

        public boolean isIsLocation() {
            return isLocation;
        }

        public void setIsLocation(boolean isLocation) {
            this.isLocation = isLocation;
        }

        public boolean isIsAddProfile() {
            return isAddProfile;
        }

        public void setIsAddProfile(boolean isAddProfile) {
            this.isAddProfile = isAddProfile;
        }

        public String getAddressBasic() {
            return addressBasic;
        }

        public void setAddressBasic(String addressBasic) {
            this.addressBasic = addressBasic;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public int getUseCount() {
            return useCount;
        }

        public void setUseCount(int useCount) {
            this.useCount = useCount;
        }
    }
}

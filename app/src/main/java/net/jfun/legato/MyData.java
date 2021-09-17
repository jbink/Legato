package net.jfun.legato;

public class MyData {

    public static volatile MyData sInstance;

    public MyData() {
    }

    public static MyData getInstance(){
        if (sInstance == null){
            synchronized (MyData.class) {
                sInstance = new MyData();
            }
        }
        return sInstance;
    }

    private int uid;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String deviceID;
    private String macAddress;
    private String joinDate;
    private String address;
    private String birth;
    private int useCount;

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    private boolean is14YearsOlder;
    private boolean isAcountTerms;
    private boolean isIntegService;
    private boolean isAddChannel;
    private boolean isPrivacy;
    private boolean isLocation;
    private boolean isAddProfile;

    public MyData(int uid, String email, String password, String name, String phone, String deviceID, String macAddress, String joinDate, String address, String birth, boolean is14YearsOlder, boolean isAcountTerms, boolean isIntegService, boolean isAddChannel, boolean isPrivacy, boolean isLocation, boolean isAddProfile) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.deviceID = deviceID;
        this.macAddress = macAddress;
        this.joinDate = joinDate;
        this.address = address;
        this.birth = birth;
        this.is14YearsOlder = is14YearsOlder;
        this.isAcountTerms = isAcountTerms;
        this.isIntegService = isIntegService;
        this.isAddChannel = isAddChannel;
        this.isPrivacy = isPrivacy;
        this.isLocation = isLocation;
        this.isAddProfile = isAddProfile;
    }

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

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public boolean isIs14YearsOlder() {
        return is14YearsOlder;
    }

    public void setIs14YearsOlder(boolean is14YearsOlder) {
        this.is14YearsOlder = is14YearsOlder;
    }

    public boolean isAcountTerms() {
        return isAcountTerms;
    }

    public void setAcountTerms(boolean acountTerms) {
        isAcountTerms = acountTerms;
    }

    public boolean isIntegService() {
        return isIntegService;
    }

    public void setIntegService(boolean integService) {
        isIntegService = integService;
    }

    public boolean isAddChannel() {
        return isAddChannel;
    }

    public void setAddChannel(boolean addChannel) {
        isAddChannel = addChannel;
    }

    public boolean isPrivacy() {
        return isPrivacy;
    }

    public void setPrivacy(boolean privacy) {
        isPrivacy = privacy;
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }

    public boolean isAddProfile() {
        return isAddProfile;
    }

    public void setAddProfile(boolean addProfile) {
        isAddProfile = addProfile;
    }

    public void initMyData() {
        uid = -1;
        email = null;
        password = null;
        name = null;
        phone = null;
        deviceID = null;
        joinDate = null;
        macAddress = null;
        is14YearsOlder = false;
        isAcountTerms = false;
        isIntegService = false;
        isAddChannel = false;
        isPrivacy = false;
        isLocation = false;
        isAddProfile = false;
    }
}

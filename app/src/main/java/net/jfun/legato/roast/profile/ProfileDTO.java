package net.jfun.legato.roast.profile;

public class ProfileDTO {


    /**
     * result : 1
     * message :
     * content : {"uid":1,"profileName":"F00","term":"shorttime","targetTemperature":100,"startbit_0":"0xFD","startbit_1":"0xFD","pz_2":"0x00","nyz_3":"0x00","gxyz_4":"0x07","gxyz_5":"0xE4","ayz_6":"0x3C","byz_7":"0x3C","cyz_8":"0x44","dyz_9":"0x44","uwxyz_10":"0x03","rz_11":"0xE8","rz_12":"0x01","xoR_13":"0x09","stopbit_14":"0xFE","stopbit_15":"0xFE","beanName":null,"notes":null,"inputQuantity":0,"expectedEndTime":null}
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
         * uid : 1
         * profileName : F00
         * term : shorttime
         * targetTemperature : 100
         * startbit_0 : 0xFD
         * startbit_1 : 0xFD
         * pz_2 : 0x00
         * nyz_3 : 0x00
         * gxyz_4 : 0x07
         * gxyz_5 : 0xE4
         * ayz_6 : 0x3C
         * byz_7 : 0x3C
         * cyz_8 : 0x44
         * dyz_9 : 0x44
         * uwxyz_10 : 0x03
         * rz_11 : 0xE8
         * rz_12 : 0x01
         * xoR_13 : 0x09
         * stopbit_14 : 0xFE
         * stopbit_15 : 0xFE
         * beanName : null
         * notes : null
         * inputQuantity : 0
         * expectedEndTime : null
         */

        private int uid;
        private String profileName;
        private String term;
        private String targetTemperature;
        private String startbit_0;
        private String startbit_1;
        private String pz_2;
        private String nyz_3;
        private String gxyz_4;
        private String gxyz_5;
        private String ayz_6;
        private String byz_7;
        private String cyz_8;
        private String dyz_9;
        private String uwxyz_10;
        private String rz_11;
        private String rz_12;
        private String xoR_13;
        private String stopbit_14;
        private String stopbit_15;
        private Object beanName;
        private Object notes;
        private int inputQuantity;
        private Object expectedEndTime;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getProfileName() {
            return profileName;
        }

        public void setProfileName(String profileName) {
            this.profileName = profileName;
        }

        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public String getTargetTemperature() {
            return targetTemperature;
        }

        public void setTargetTemperature(String targetTemperature) {
            this.targetTemperature = targetTemperature;
        }

        public String getStartbit_0() {
            return startbit_0;
        }

        public void setStartbit_0(String startbit_0) {
            this.startbit_0 = startbit_0;
        }

        public String getStartbit_1() {
            return startbit_1;
        }

        public void setStartbit_1(String startbit_1) {
            this.startbit_1 = startbit_1;
        }

        public String getPz_2() {
            return pz_2;
        }

        public void setPz_2(String pz_2) {
            this.pz_2 = pz_2;
        }

        public String getNyz_3() {
            return nyz_3;
        }

        public void setNyz_3(String nyz_3) {
            this.nyz_3 = nyz_3;
        }

        public String getGxyz_4() {
            return gxyz_4;
        }

        public void setGxyz_4(String gxyz_4) {
            this.gxyz_4 = gxyz_4;
        }

        public String getGxyz_5() {
            return gxyz_5;
        }

        public void setGxyz_5(String gxyz_5) {
            this.gxyz_5 = gxyz_5;
        }

        public String getAyz_6() {
            return ayz_6;
        }

        public void setAyz_6(String ayz_6) {
            this.ayz_6 = ayz_6;
        }

        public String getByz_7() {
            return byz_7;
        }

        public void setByz_7(String byz_7) {
            this.byz_7 = byz_7;
        }

        public String getCyz_8() {
            return cyz_8;
        }

        public void setCyz_8(String cyz_8) {
            this.cyz_8 = cyz_8;
        }

        public String getDyz_9() {
            return dyz_9;
        }

        public void setDyz_9(String dyz_9) {
            this.dyz_9 = dyz_9;
        }

        public String getUwxyz_10() {
            return uwxyz_10;
        }

        public void setUwxyz_10(String uwxyz_10) {
            this.uwxyz_10 = uwxyz_10;
        }

        public String getRz_11() {
            return rz_11;
        }

        public void setRz_11(String rz_11) {
            this.rz_11 = rz_11;
        }

        public String getRz_12() {
            return rz_12;
        }

        public void setRz_12(String rz_12) {
            this.rz_12 = rz_12;
        }

        public String getXoR_13() {
            return xoR_13;
        }

        public void setXoR_13(String xoR_13) {
            this.xoR_13 = xoR_13;
        }

        public String getStopbit_14() {
            return stopbit_14;
        }

        public void setStopbit_14(String stopbit_14) {
            this.stopbit_14 = stopbit_14;
        }

        public String getStopbit_15() {
            return stopbit_15;
        }

        public void setStopbit_15(String stopbit_15) {
            this.stopbit_15 = stopbit_15;
        }

        public Object getBeanName() {
            return beanName;
        }

        public void setBeanName(Object beanName) {
            this.beanName = beanName;
        }

        public Object getNotes() {
            return notes;
        }

        public void setNotes(Object notes) {
            this.notes = notes;
        }

        public int getInputQuantity() {
            return inputQuantity;
        }

        public void setInputQuantity(int inputQuantity) {
            this.inputQuantity = inputQuantity;
        }

        public Object getExpectedEndTime() {
            return expectedEndTime;
        }

        public void setExpectedEndTime(Object expectedEndTime) {
            this.expectedEndTime = expectedEndTime;
        }
    }
}

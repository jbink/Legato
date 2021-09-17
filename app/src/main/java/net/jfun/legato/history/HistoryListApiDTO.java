package net.jfun.legato.history;

import java.util.List;

public class HistoryListApiDTO {


    /**
     * result : 1
     * message :
     * content : {"dataList":[{"historyUid":20,"profileUid":1,"profileRunDate":"2021-08-13","profileRunTime":"01:03","profileName":"F00","ad":[{"x":21,"y":21},{"x":22,"y":22}],"roasting":[{"x":21,"y":21},{"x":22,"y":22}],"ror":[{"x":21,"y":21},{"x":22,"y":22}]}]}
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
        private List<DataListBean> dataList;

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * historyUid : 20
             * profileUid : 1
             * profileRunDate : 2021-08-13
             * profileRunTime : 01:03
             * profileName : F00
             * ad : [{"x":21,"y":21},{"x":22,"y":22}]
             * roasting : [{"x":21,"y":21},{"x":22,"y":22}]
             * ror : [{"x":21,"y":21},{"x":22,"y":22}]
             */

            private int historyUid;
            private int profileUid;
            private int colorIndex = -1;
            private boolean selected = false;
            private String profileRunDate;
            private String profileRunTime;
            private String profileName;
            private String saveDataName;
            private List<AdBean> ad;
            private List<RoastingBean> roasting;
            private List<RorBean> ror;

            public String getSaveDataName() {
                return saveDataName;
            }

            public void setSaveDataName(String saveDataName) {
                this.saveDataName = saveDataName;
            }

            public int getColorIndex() {
                return colorIndex;
            }

            public void setColorIndex(int colorIndex) {
                this.colorIndex = colorIndex;
            }

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public int getHistoryUid() {
                return historyUid;
            }

            public void setHistoryUid(int historyUid) {
                this.historyUid = historyUid;
            }

            public int getProfileUid() {
                return profileUid;
            }

            public void setProfileUid(int profileUid) {
                this.profileUid = profileUid;
            }

            public String getProfileRunDate() {
                return profileRunDate;
            }

            public void setProfileRunDate(String profileRunDate) {
                this.profileRunDate = profileRunDate;
            }

            public String getProfileRunTime() {
                return profileRunTime;
            }

            public void setProfileRunTime(String profileRunTime) {
                this.profileRunTime = profileRunTime;
            }

            public String getProfileName() {
                return profileName;
            }

            public void setProfileName(String profileName) {
                this.profileName = profileName;
            }

            public List<AdBean> getAd() {
                return ad;
            }

            public void setAd(List<AdBean> ad) {
                this.ad = ad;
            }

            public List<RoastingBean> getRoasting() {
                return roasting;
            }

            public void setRoasting(List<RoastingBean> roasting) {
                this.roasting = roasting;
            }

            public List<RorBean> getRor() {
                return ror;
            }

            public void setRor(List<RorBean> ror) {
                this.ror = ror;
            }

            public static class AdBean {
                /**
                 * x : 21
                 * y : 21
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class RoastingBean {
                /**
                 * x : 21
                 * y : 21
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }

            public static class RorBean {
                /**
                 * x : 21
                 * y : 21
                 */

                private int x;
                private int y;

                public int getX() {
                    return x;
                }

                public void setX(int x) {
                    this.x = x;
                }

                public int getY() {
                    return y;
                }

                public void setY(int y) {
                    this.y = y;
                }
            }
        }
    }
}

package net.jfun.legato.api;

public class LoginDTO {


    /**
     * RESULT : SUCCESS
     * DATA : {"user_id":"cadmin","user_name":"관리자 ","login_term":"10"}
     */

    private String RESULT;
    private DATABean DATA;

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public DATABean getDATA() {
        return DATA;
    }

    public void setDATA(DATABean DATA) {
        this.DATA = DATA;
    }

    public static class DATABean {
        /**
         * user_id : cadmin
         * user_name : 관리자
         * login_term : 10
         */

        private String user_id;
        private String user_name;
        private String login_term;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getLogin_term() {
            return login_term;
        }

        public void setLogin_term(String login_term) {
            this.login_term = login_term;
        }
    }
}

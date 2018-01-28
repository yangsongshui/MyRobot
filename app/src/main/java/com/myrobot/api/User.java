package com.myrobot.api;

/**
 * Created by yangsong on 2017/5/25.
 */

public class User {


    /**
     * code : 1
     * msg : success
     * data : {"id":30,"phone":"18664570155","name":"","sex":0,"company_name":null,"fingerprint":null,"robot_mac":null,"parent_id":0,"department_id":0,"department_name":null,"role_id":0,"role_name":null}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 30
         * phone : 18664570155
         * name :
         * sex : 0
         * company_name : null
         * fingerprint : null
         * robot_mac : null
         * parent_id : 0
         * department_id : 0
         * department_name : null
         * role_id : 0
         * role_name : null
         */

        private int id;
        private String phone;
        private String name;
        private int sex;
        private Object company_name;
        private Object fingerprint;
        private Object robot_mac;
        private int parent_id;
        private int department_id;
        private Object department_name;
        private int role_id;
        private Object role_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public Object getCompany_name() {
            return company_name;
        }

        public void setCompany_name(Object company_name) {
            this.company_name = company_name;
        }

        public Object getFingerprint() {
            return fingerprint;
        }

        public void setFingerprint(Object fingerprint) {
            this.fingerprint = fingerprint;
        }

        public Object getRobot_mac() {
            return robot_mac;
        }

        public void setRobot_mac(Object robot_mac) {
            this.robot_mac = robot_mac;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public int getDepartment_id() {
            return department_id;
        }

        public void setDepartment_id(int department_id) {
            this.department_id = department_id;
        }

        public Object getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(Object department_name) {
            this.department_name = department_name;
        }

        public int getRole_id() {
            return role_id;
        }

        public void setRole_id(int role_id) {
            this.role_id = role_id;
        }

        public Object getRole_name() {
            return role_name;
        }

        public void setRole_name(Object role_name) {
            this.role_name = role_name;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", phone='" + phone + '\'' +
                    ", name='" + name + '\'' +
                    ", sex=" + sex +
                    ", company_name=" + company_name +
                    ", fingerprint=" + fingerprint +
                    ", robot_mac=" + robot_mac +
                    ", parent_id=" + parent_id +
                    ", department_id=" + department_id +
                    ", department_name=" + department_name +
                    ", role_id=" + role_id +
                    ", role_name=" + role_name +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

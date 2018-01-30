package com.myrobot.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangsong on 2018/1/28.
 */

public class Zu implements Serializable{

    /**
     * code : 1
     * msg :
     * data : [{"department_id":2,"department_name":"财务部","users":[{"id":1,"name":"22222","parent_id":0,"phone":"18017050138"}]},{"department_id":4,"department_name":"财务部3","users":[]}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        public DataBean() {
        }

        public DataBean(String department_name, List<UsersBean> users) {
            this.department_name = department_name;
            this.users = users;
        }

        /**
         * department_id : 2
         * department_name : 财务部
         * users : [{"id":1,"name":"22222","parent_id":0,"phone":"18017050138"}]
         */


        private int department_id;
        private String department_name;
        private List<UsersBean> users;

        public int getDepartment_id() {
            return department_id;
        }

        public void setDepartment_id(int department_id) {
            this.department_id = department_id;
        }

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public List<UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<UsersBean> users) {
            this.users = users;
        }

        public static class UsersBean {
            public UsersBean() {
            }

            public UsersBean(String name) {
                this.name = name;
            }

            /**
             * id : 1
             * name : 22222
             * parent_id : 0
             * phone : 18017050138
             */

            private int id;
            private String name;
            private int parent_id;
            private String phone;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            @Override
            public String
            toString() {
                return "UsersBean{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", parent_id=" + parent_id +
                        ", phone='" + phone + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "department_id=" + department_id +
                    ", department_name='" + department_name + '\'' +
                    ", users=" + users +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Zu{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

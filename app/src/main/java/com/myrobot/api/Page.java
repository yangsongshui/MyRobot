package com.myrobot.api;

import java.util.List;

/**
 * Created by yangsong on 2017/12/17.
 */

public class Page {

    /**
     * code : 1
     * msg :
     * data : [{"id":1,"name":"企业如何实现自己的价值","path":"http://api.jian-tec.com/uploads/users/uploads/企业如何实现自己的价值.doc"},{"id":2,"name":"企业管理","path":"http://api.jian-tec.com/uploads/users/uploads/企业管理.doc"},{"id":4,"name":"恶臭擦擦","path":null}]
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
        /**
         * id : 1
         * name : 企业如何实现自己的价值
         * path : http://api.jian-tec.com/uploads/users/uploads/企业如何实现自己的价值.doc
         */

        private int id;
        private String name;
        private String path;

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

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}

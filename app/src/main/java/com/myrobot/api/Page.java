package com.myrobot.api;

import java.util.List;

/**
 * Created by yangsong on 2017/12/17.
 */

public class Page {


    /**
     * code : 1
     * msg :
     * data : [{"id":4,"name":"公司规章制度","path":"http://112.74.196.237:81/robot_admin/public/uploads/20171218/0378d5432473fba9aa1f151f34897cf9.docx"},{"id":5,"name":"公司发展规划","path":"http://112.74.196.237:81/robot_admin/public/uploads/20171218/41de9048b72e729844b342719d112ec1.docx"},{"id":6,"name":"企业激励制度","path":"http://112.74.196.237:81/robot_admin/public/uploads/20171218/77a24bb5e2185df4a539bf5d80c03df2.docx"},{"id":7,"name":"泡妞","path":"http://112.74.196.237:81/robot_admin/public/uploads/20171218/80ac17dd3b4d5bbf21c1f4adfb0f159b.mp4"}]
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
         * id : 4
         * name : 公司规章制度
         * path : http://112.74.196.237:81/robot_admin/public/uploads/20171218/0378d5432473fba9aa1f151f34897cf9.docx
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

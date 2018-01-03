package com.myrobot.api;

import java.util.List;

/**
 * Created by yangsong on 2017/12/17.
 */

public class Page {


    /**
     * code : 1
     * msg :
     * data : [{"id":4,"name":"公司规章制度","path":"http://112.74.196.237:81/robot_admin/public/uploads/20171218/0378d5432473fba9aa1f151f34897cf9.docx","mime_type":"video/mp4","thumbnail":"http://112.74.196.237:81/robot_admin/public"},{"id":5,"name":"公司发展规划","path":"http://112.74.196.237:81/robot_admin/public/uploads/20171218/41de9048b72e729844b342719d112ec1.docx","mime_type":"video/mp4","thumbnail":"http://112.74.196.237:81/robot_admin/public"},{"id":6,"name":"企业激励制度","path":"http://112.74.196.237:81/robot_admin/public/uploads/20171218/77a24bb5e2185df4a539bf5d80c03df2.docx","mime_type":"image/jpg","thumbnail":"http://112.74.196.237:81/robot_admin/public/uploads/20171217/47987cdfa00feea4e75d42e7bbc38734.jpg"},{"id":7,"name":"泡妞","path":"http://112.74.196.237:81/robot_admin/public/uploads/20171218/80ac17dd3b4d5bbf21c1f4adfb0f159b.mp4","mime_type":"image/jpg","thumbnail":"http://112.74.196.237:81/robot_admin/public/uploads/20171217/47987cdfa00feea4e75d42e7bbc38734.jpg"},{"id":13,"name":"3432413234","path":"http://112.74.196.237:81/robot_admin/public/uploads/20180101/a61c5cd578c71e56ba51aade1bcb1a84.jpeg","mime_type":"image/jpeg","thumbnail":"http://112.74.196.237:81/robot_admin/public/uploads/20180101/a61c5cd578c71e56ba51aade1bcb1a84.jpeg"},{"id":14,"name":"56567","path":"http://112.74.196.237:81/robot_admin/public/uploads/20180101/1edd8adc3dca9148beb765d43adf2d34.jpg","mime_type":"image/jpg","thumbnail":"http://112.74.196.237:81/robot_admin/public/uploads/20180101/1edd8adc3dca9148beb765d43adf2d34.jpg"}]
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
         * mime_type : video/mp4
         * thumbnail : http://112.74.196.237:81/robot_admin/public
         */

        private int id;
        private String name;
        private String path;
        private String mime_type;
        private String thumbnail;

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

        public String getMime_type() {
            return mime_type;
        }

        public void setMime_type(String mime_type) {
            this.mime_type = mime_type;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}

package com.hoolai.huizhen.model;

/**
 * Author: lilei
 * Date: 2018/5/22
 * Comment: //结束音频
 */

public class ModelFinishAudio {

    /**
     * name : 训练完成，拍照打卡记录一下吧
     * url : http://source.hotbody.cn/xIWmc8gH-wZqm-bUf8-vcVd-T2famn3enEQ6.mp3
     * due : 3213
     * size : 129637
     * md5 : e55cfd2ca8e3f8d32b08d5803b8c6b2a
     */

    private String name;
    private String url;
    private int due;
    private String size;
    private String md5;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDue() {
        return due;
    }

    public void setDue(int due) {
        this.due = due;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}

package com.hoolai.huizhen.model;

/**
 * Author: lilei
 * Date: 2018/5/22
 * Comment: //音频实体类
 */

public class ModelAudio {

    /**
     * name : 第一个动作 long
     * url : http://source.hotbody.cn/wRlnNEm2-Ix91-eicp-4fP1-6GSkPTlfQV4w.m4a
     * start_at : 0
     * due : 1509
     * size : 10426
     * md5 : 0f1d8b66e802ba64c51f7c390044cca2
     * type : init
     * stop_bg_music : false
     * debug_type : init
     */

    private String name;
    private String url;
    private int start_at;
    private int due;
    private String size;
    private String md5;
    private String type;
    private boolean stop_bg_music;
    private String debug_type;
    /**
     * is_rest : true
     */

    private boolean is_rest;

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

    public int getStart_at() {
        return start_at;
    }

    public void setStart_at(int start_at) {
        this.start_at = start_at;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStop_bg_music() {
        return stop_bg_music;
    }

    public void setStop_bg_music(boolean stop_bg_music) {
        this.stop_bg_music = stop_bg_music;
    }

    public String getDebug_type() {
        return debug_type;
    }

    public void setDebug_type(String debug_type) {
        this.debug_type = debug_type;
    }


    public boolean isIs_rest() {
        return is_rest;
    }

    public void setIs_rest(boolean is_rest) {
        this.is_rest = is_rest;
    }
}

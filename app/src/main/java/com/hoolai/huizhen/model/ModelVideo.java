package com.hoolai.huizhen.model;

/**
 * Author: lilei
 * Date: 2018/5/22
 * Comment: //视频类
 */

public class ModelVideo {

    /**
     * name : 快速出拳
     * url : http://source.hotbody.cn/KVTnPV6i-6BfI-EnRm-PdOD-TnQOIZ21u382.mp4
     * start_at : 0
     * due : 9560
     * calorie : 0
     * md5 : 2431eeafeffa53faa18cdde999d8b729
     * action_video_due : 13920
     * size : 1127902
     * action_id : 21
     * type : preview
     * debug_type : preview
     */

    private String name;
    private String url;
    private int start_at;
    private int due;
    private int calorie;
    private String md5;
    private String action_video_due;
    private String size;
    private String action_id;
    private String type;
    private String debug_type;

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

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getAction_video_due() {
        return action_video_due;
    }

    public void setAction_video_due(String action_video_due) {
        this.action_video_due = action_video_due;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAction_id() {
        return action_id;
    }

    public void setAction_id(String action_id) {
        this.action_id = action_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDebug_type() {
        return debug_type;
    }

    public void setDebug_type(String debug_type) {
        this.debug_type = debug_type;
    }
}

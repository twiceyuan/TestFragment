package com.max.testfragment.model;

/**
 * Created by Max on 2017-6-14.
 */

public class OperationItem {


    /**
     * id : 11
     * title :
     * icon : 2017/0802/43266858a74d44ab92b0c30592fe2b39.jpg
     * status : 1
     * pagetype : 4
     * cityid : 102
     * rule :
     * weights : 100
     * ct : 1501644482000
     * cityStr :
     * iconURL : 2017/0802/43266858a74d44ab92b0c30592fe2b39.jpg
     *
     */

    private int id;
    private String title;
    private String icon;
    private int status;
    private int pagetype;
    private int cityid;
    private String rule;
    private int weights;
    private long ct;
    private String cityStr;
    private String iconURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPagetype() {
        return pagetype;
    }

    public void setPagetype(int pagetype) {
        this.pagetype = pagetype;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public int getWeights() {
        return weights;
    }

    public void setWeights(int weights) {
        this.weights = weights;
    }

    public long getCt() {
        return ct;
    }

    public void setCt(long ct) {
        this.ct = ct;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }
}

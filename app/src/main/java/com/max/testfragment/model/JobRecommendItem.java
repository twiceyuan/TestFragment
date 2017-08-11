package com.max.testfragment.model;

/**
 * Created by Daniel on 2017/1/13.
 */
public class JobRecommendItem {

    /**
     * {
     * "jobId": 5640,
     * "laborId": 2701,
     * "name": "111",
     * "district": "东城区",
     * "validateTime": "长期用工",
     * "salary": "1元/小时",
     * "hot": false,
     * "feature": null,
     * "distance": 123459679
     * "logoURL": null
     * "labor":false
     * "redEnvelope": true,
     * }
     */
    private int jobId;
    private int laborId;
    private String name;
    private String district;
    private String validateTime;
    private String salary;
    private boolean hot;
    private String feature;
    private String distance;
    private String logoURL;
    private boolean labor;
    private boolean redEnvelope;

    private String uuid;
    private String redenvelopTips;
    private String welfareTips;
    private String customerLevelTips;
    private String billingCycle;
    private int customerId;
    private int packageId;

    private boolean emptyType;//空布局专用

    public JobRecommendItem(){
    }

    public JobRecommendItem(String name){
        this.name = name;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getLaborId() {
        return laborId;
    }

    public void setLaborId(int laborId) {
        this.laborId = laborId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(String validateTime) {
        this.validateTime = validateTime;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public boolean isLabor() {
        return labor;
    }

    public void setLabor(boolean labor) {
        this.labor = labor;
    }

    public boolean isRedEnvelope() {
        return redEnvelope;
    }

    public void setRedEnvelope(boolean redEnvelope) {
        this.redEnvelope = redEnvelope;
    }

    @Override
    public String toString() {
        return "JobRecommendItem{" +
                "jobId=" + jobId +
                ", laborId=" + laborId +
                ", name='" + name + '\'' +
                ", district='" + district + '\'' +
                ", validateTime='" + validateTime + '\'' +
                ", salary='" + salary + '\'' +
                ", hot=" + hot +
                ", feature=" + feature +
                ", logoURL='" + logoURL + '\'' +
                '}';
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRedenvelopTips() {
        return redenvelopTips;
    }

    public void setRedenvelopTips(String redenvelopTips) {
        this.redenvelopTips = redenvelopTips;
    }

    public String getWelfareTips() {
        return welfareTips;
    }

    public void setWelfareTips(String welfareTips) {
        this.welfareTips = welfareTips;
    }

    public String getCustomerLevelTips() {
        return customerLevelTips;
    }

    public void setCustomerLevelTips(String customerLevelTips) {
        this.customerLevelTips = customerLevelTips;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public boolean isEmptyType() {
        return emptyType;
    }

    public void setEmptyType(boolean emptyType) {
        this.emptyType = emptyType;
    }
}

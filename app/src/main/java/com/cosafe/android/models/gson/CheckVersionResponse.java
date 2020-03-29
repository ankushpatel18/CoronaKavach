package com.cosafe.android.models.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckVersionResponse {
    @SerializedName("deviceType")
    @Expose
    private String deviceType;
    @SerializedName("id")
    @Expose

    /* renamed from: id */
    private Integer f98id;
    @SerializedName("isItMandatory")
    @Expose
    private Boolean isItMandatory;
    @SerializedName("latestVersion")
    @Expose
    private String latestVersion;
    @SerializedName("mandatoryPoster")
    @Expose
    private Object mandatoryPoster;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("noOfUpdatesAvailable")
    @Expose
    private Object noOfUpdatesAvailable;

    public Integer getId() {
        return this.f98id;
    }

    public void setId(Integer num) {
        this.f98id = num;
    }

    public String getLatestVersion() {
        return this.latestVersion;
    }

    public void setLatestVersion(String str) {
        this.latestVersion = str;
    }

    public Object getNoOfUpdatesAvailable() {
        return this.noOfUpdatesAvailable;
    }

    public void setNoOfUpdatesAvailable(Object obj) {
        this.noOfUpdatesAvailable = obj;
    }

    public Boolean getIsItMandatory() {
        return this.isItMandatory;
    }

    public void setIsItMandatory(Boolean bool) {
        this.isItMandatory = bool;
    }

    public Object getMessage() {
        return this.message;
    }

    public void setMessage(Object obj) {
        this.message = obj;
    }

    public Object getMandatoryPoster() {
        return this.mandatoryPoster;
    }

    public void setMandatoryPoster(Object obj) {
        this.mandatoryPoster = obj;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String str) {
        this.deviceType = str;
    }
}

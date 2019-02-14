package com.cisco.metadata.model;

import java.io.Serializable;

public class CameraMetadata implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String deviceName;
    private String type;
    private String deviceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "CameraMetadata{" +
                "id='" + id + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", type='" + type + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}

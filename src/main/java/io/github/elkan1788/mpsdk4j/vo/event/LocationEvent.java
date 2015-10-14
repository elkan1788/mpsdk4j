package io.github.elkan1788.mpsdk4j.vo.event;

import java.util.Map;

/**
 * 地理位置事件
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class LocationEvent extends BasicEvent {

    /**
     * 地理位置纬度
     */
    private String latitude;
    /**
     * 地理位置经度
     */
    private String longitude;
    /**
     * 地理位置精度
     */
    private String precision;

    public LocationEvent() {
        this.event = "LOCATION";
    }

    public LocationEvent(Map<String, String> values) {
        super(values);
        this.latitude = values.get("latitude");
        this.longitude = values.get("longitude");
        this.precision = values.get("precision");
        this.event = "LOCATION";
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    @ Override
    public String toString() {
        return "LocationEvent [toUserName="
               + toUserName
               + ", fromUserName="
               + fromUserName
               + ", createTime="
               + createTime
               + ", msgType="
               + msgType
               + ", event="
               + event
               + ", latitude="
               + latitude
               + ", longitude="
               + longitude
               + ", precision="
               + precision
               + "]";
    }

}

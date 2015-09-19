package io.github.elkan1788.mpsdk4j.vo.message;

import java.util.Map;

/**
 * 地理位置消息
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class LocationMsg extends BasicMsg {

    /**
     * 地理位置维度
     */
    private String x;
    /**
     * 地理位置经度
     */
    private String y;
    /**
     * 地图缩放大小
     */
    private int scale;
    /**
     * 地理位置信息
     */
    private String label;

    public LocationMsg() {
        super();
        this.msgType = "location";
    }

    public LocationMsg(Map<String, String> values) {
        super(values);
        this.x = values.get("locationX");
        this.y = values.get("locationY");
        this.scale = Integer.parseInt(values.get("scale"));
        this.label = values.get("label");
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "LocationMsg [toUserName="
               + toUserName
               + ", fromUserName="
               + fromUserName
               + ", createTime="
               + createTime
               + ", msgType="
               + msgType
               + ", msgId="
               + msgId
               + ", x="
               + x
               + ", y="
               + y
               + ", scale="
               + scale
               + ", label="
               + label
               + "]";
    }
}

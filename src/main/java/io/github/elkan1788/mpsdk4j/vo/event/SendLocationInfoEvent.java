package io.github.elkan1788.mpsdk4j.vo.event;

import java.util.Map;

/**
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class SendLocationInfoEvent extends MenuEvent {

    /**
     * X坐标信息
     */
    private String locationX;
    /**
     * Y坐标信息
     */
    private String locationY;
    /**
     * 精度,可理解为精度或者比例尺,越精细的话 scale越高
     */
    private int scale;
    /**
     * 地理位置的字符串信息
     */
    private String label;
    /**
     * 朋友圈POI的名字,可能为空
     */
    private String poiname;

    public SendLocationInfoEvent() {}

    public SendLocationInfoEvent(Map<String, String> values) {
        super(values);
        this.locationX = values.get("locationX");
        this.locationY = values.get("locationY");
        this.scale = Integer.parseInt(values.get("scale"));
        this.label = values.get("label");
        this.poiname = values.get("poiname");
    }

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
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

    public String getPoiname() {
        return poiname;
    }

    public void setPoiname(String poiname) {
        this.poiname = poiname;
    }

    @Override
    public String toString() {
        return "SendLocationInfoEvent [toUserName="
               + toUserName
               + ", fromUserName="
               + fromUserName
               + ", createTime="
               + createTime
               + ", msgType="
               + msgType
               + ", event="
               + event
               + ", eventKey="
               + eventKey
               + ", locationX="
               + locationX
               + ", locationY="
               + locationY
               + ", scale="
               + scale
               + ", label="
               + label
               + ", poiname="
               + poiname
               + "]";
    }

}

package io.github.elkan1788.mpsdk4j.vo.event;

/**
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class SendLocationInfo {

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

    public SendLocationInfo() {}

    public SendLocationInfo(String locationX,
                            String locationY,
                            int scale,
                            String label,
                            String poiname) {
        super();
        this.locationX = locationX;
        this.locationY = locationY;
        this.scale = scale;
        this.label = label;
        this.poiname = poiname;
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

    @ Override
    public String toString() {
        return "SendLocationInfo [locationX="
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

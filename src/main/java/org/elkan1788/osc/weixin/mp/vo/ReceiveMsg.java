package org.elkan1788.osc.weixin.mp.vo;

/**
 * 接收消息实体
 *
 * @author 凡梦星尘(senhuili@mdc.cn)
 * @since 2014/11/7
 * @version 1.0.0
 */
public class ReceiveMsg extends BaseMsg {

	/**
	 * 图片链接
	 */
	private String picUrl;

	/**
	 * 音频格式(arm,wav,mp3)
	 */
	private String format;

	/**
	 * 语音识别结果,UTF-8编码
	 */
	private String recognition;

	/**
	 * 地图缩放大小
	 */
	private int scale;

    /**
     * 地理位置信息
     */
	private String label;

	/**
	 * 链接地址
	 */
	private String url;

	/**
	 * 事件KEY值
	 */
	private String eventKey;

	/**
	 * 二维码的ticket
	 */
	private String ticket;

	/**
	 * 地理位置纬度
	 */
	private double latitude;

	/**
	 * 地理位置经度 
	 */
	private double longitude;

	/**
	 * 地理位置精度
	 */
	private double precision;

	/**
	 * 微信发送消息状态（模板消息）
	 */
	private String status;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReceiveMsg{" +
                "msgId='" + msgId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", msgType='" + msgType + '\'' +
                ", event='" + event + '\'' +
                ", content='" + content + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", thumbMediaId='" + thumbMediaId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", format='" + format + '\'' +
                ", recognition='" + recognition + '\'' +
                ", scale=" + scale +
                ", label='" + label + '\'' +
                ", url='" + url + '\'' +
                ", eventKey='" + eventKey + '\'' +
                ", ticket='" + ticket + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", precision=" + precision +
                ", status='" + status + '\'' +
                "} ";
    }
}

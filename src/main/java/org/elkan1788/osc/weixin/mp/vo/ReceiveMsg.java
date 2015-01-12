package org.elkan1788.osc.weixin.mp.vo;

import java.util.List;

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
	 * 二维码的ticket或是开放平台中服务方的ComponentVerifyTicket
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
     * 扫描类型,一般是qcode
     */
    private String scanType;

    /**
     * 扫描结果，即二维码对应的字符串信息
     */
    private String scanResult;

    /**
     * 发送的图片数量
     */
    private int count;

    /**
     * 发送的图片列表
     */
    private List<PicInfo> picList;

    /**
     * 朋友圈POI的名字，可能为空
     */
    private String poiName;

	/**
	 * 微信发送消息状态（模板消息,群发消息）<pre/>
     * 群发的结构，为“send success”或“send fail”或“err(num)”。但send success时，<pre/>
     * 也有可能因用户拒收公众号的消息、系统错误等原因造成少量用户接收失败。err(num)是审核失败的具体原因，<pre/>
     * 可能的情况如下：<pre/>
     * err(10001), //涉嫌广告 <pre/>
     * err(20001), //涉嫌政治 <pre/>
     * err(20004), //涉嫌社会 <pre/>
     * err(20002), //涉嫌色情 <pre/>
     * err(20006), //涉嫌违法犯罪 <pre/>
     * err(20008), //涉嫌欺诈 <pre/>
     * err(20013), //涉嫌版权 <pre/>
     * err(22000), //涉嫌互推(互相宣传) <pre/>
     * err(21000), //涉嫌其他
	 */
	private String status;

    /**
     * group_id下粉丝数；或者openid_list中的粉丝数
     */
    private int totalCnt;

    /**
     * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，
     * 用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，
     * FilterCount = SentCount + ErrorCount
     */
    private int filterCnt;

    /**
     * 发送成功的粉丝数
     */
    private int sentCnt;

    /**
     * 发送失败的粉丝数
     */
    private int errorCnt;

    /**
     * 服务appid
     */
    private String appId;

    /**
     * none,代表该消息推送给服务
     * unauthorized,代表公众号取消授权
     */
    private String infoType;

    /**
     * 取消授权的公众号
     */
    private String unAuthAppid;

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

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PicInfo> getPicList() {
        return picList;
    }

    public void setPicList(List<PicInfo> picList) {
        this.picList = picList;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    public int getFilterCnt() {
        return filterCnt;
    }

    public void setFilterCnt(int filterCnt) {
        this.filterCnt = filterCnt;
    }

    public int getSentCnt() {
        return sentCnt;
    }

    public void setSentCnt(int sentCnt) {
        this.sentCnt = sentCnt;
    }

    public int getErrorCnt() {
        return errorCnt;
    }

    public void setErrorCnt(int errorCnt) {
        this.errorCnt = errorCnt;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getUnAuthAppid() {
        return unAuthAppid;
    }

    public void setUnAuthAppid(String unAuthAppid) {
        this.unAuthAppid = unAuthAppid;
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
                ", totalCnt='" + totalCnt + '\'' +
                ", filterCnt='" + filterCnt + '\'' +
                ", sentCnt='" + sentCnt + '\'' +
                ", errorCnt='" + errorCnt + '\'' +
                ", appId='" + appId + '\'' +
                ", infoType='" + infoType + '\'' +
                ", unAuthAppid='" + unAuthAppid + '\'' +
                "} ";
    }
}

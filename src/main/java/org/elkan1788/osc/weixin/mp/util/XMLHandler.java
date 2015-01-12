package org.elkan1788.osc.weixin.mp.util;

import org.elkan1788.osc.weixin.mp.vo.PicInfo;
import org.elkan1788.osc.weixin.mp.vo.ReceiveMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信消息内容处理器[更新JDK7特性]
 *
 * @author 凡梦星尘(senhuili@mdc.cn)
 * @since 2014/11/7
 * @version 1.0.4
 */
public class XMLHandler extends DefaultHandler2 {

    private static final Logger log = LoggerFactory.getLogger(XMLHandler.class);

	/**
	 * 消息实体定义
	 */
	private ReceiveMsg msg = new ReceiveMsg();

    /**
     * 图片信息
     */
    private PicInfo picInfo;

    private List<PicInfo> picList;

    /**
     * 节点属性值
     */
    private String attrVal;

	/**
	 * 获取消息实体对象
     *
     * @return 带数据的消息实体
	 */
	public ReceiveMsg getMsgVO() {
		return this.msg;
	}

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {

        switch (qName) {
            case "PicList":
                this.picList = new ArrayList<>();
                break;
            case "item":
                this.picInfo = new PicInfo();
                break;
            default:
                break;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {

        if (log.isInfoEnabled()) {
            if (!"xml".equals(qName)) {
                log.info("当前节点值[{}]: {}", qName, attrVal);
            }
        }

        // 本想用反射实现,但耗时长,所以还是手动编码吧,累(更新JDK7特性)
        switch (qName) {
            case "MsgId":
            case "MsgID":
                msg.setMsgId(Long.valueOf(attrVal));
                break;
            case "CreateTime":
                msg.setCreateTime(Long.valueOf(attrVal));
                break;
            case "MsgType":
                msg.setMsgType(attrVal);
                break;
            case "Event":
                msg.setEvent(attrVal);
                break;
            case "ToUserName":
                msg.setToUserName(attrVal);
                break;
            case "FromUserName" :
                msg.setFromUserName(attrVal);
                break;
            case "Content":
                msg.setContent(attrVal);
                break;
            case "PicUrl":
                msg.setPicUrl(attrVal);
                break;
            case "MediaId":
                msg.setMediaId(attrVal);
                break;
            case "Format":
                msg.setFormat(attrVal);
                break;
            case "Recognition":
                msg.setRecognition(attrVal);
                break;
            case "ThumbMediaId":
                msg.setThumbMediaId(attrVal);
                break;
            case "Location_X":
            case "Latitude":
                msg.setLatitude(Double.valueOf(attrVal));
                break;
            case "Location_Y":
            case "Longitude":
                msg.setLongitude(Double.valueOf(attrVal));
                break;
            case "Scale":
                msg.setScale(Integer.parseInt(attrVal));
                break;
            case "Label":
                msg.setLabel(attrVal);
                break;
            case "Title":
                msg.setTitle(attrVal);
                break;
            case "Description":
                msg.setDescription(attrVal);
                break;
            case "Url":
                msg.setUrl(attrVal);
                break;
            case "EventKey":
                msg.setEventKey(attrVal);
                break;
            case "Ticket":
            case "ComponentVerifyTicket":
                msg.setTicket(attrVal);
                break;
            case "Precision":
                msg.setPrecision(Double.valueOf(attrVal));
                break;
            case "ScanType":
                msg.setScanType(attrVal);
                break;
            case "ScanResult":
                msg.setScanResult(attrVal);
                break;
            case "Count":
                msg.setCount(Integer.parseInt(attrVal));
                break;
            case "PicMd5Sum":
                picInfo.setPicMd5Sum(attrVal);
                break;
            case "item":
                picList.add(picInfo);
                break;
            case "PicList":
                msg.setPicList(picList);
                break;
            case "Poiname":
                msg.setPoiName(attrVal);
                break;
            case "Status":
                msg.setStatus(attrVal);
                break;
            case "TotalCount":
                msg.setTotalCnt(Integer.parseInt(attrVal));
                break;
            case "FilterCount":
                msg.setFilterCnt(Integer.parseInt(attrVal));
                break;
            case "SentCount":
                msg.setSentCnt(Integer.parseInt(attrVal));
                break;
            case "ErrorCount":
                msg.setErrorCnt(Integer.parseInt(attrVal));
                break;
            case "AppId":
                msg.setAppId(attrVal);
                break;
            case "InfoType":
                msg.setInfoType(attrVal);
                break;
            case "AuthorizerAppid":
                msg.setUnAuthAppid(attrVal);
                break;
            default:
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.attrVal = new String(ch, start, length);
    }

    /**
     * 清除当前VO对象缓存数据
     */
    public void clear() {
        this.picInfo = null;
        this.msg = null;
        this.msg = new ReceiveMsg();
    }
}

package org.elkan1788.osc.weixin.mp.util;

import org.elkan1788.osc.weixin.mp.vo.ReceiveMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

/**
 * 微信消息内容处理器
 *
 * @author 凡梦星尘(senhuili@mdc.cn)
 * @since 2014/11/7
 * @version 1.0.0
 */
public class XMLHandler extends DefaultHandler2 {

    private static final Logger log = LoggerFactory.getLogger(XMLHandler.class);

	/**
	 * 消息实体定义
	 */
	private ReceiveMsg msg = new ReceiveMsg();

    /**
     * 节点属性值
     */
    private String arrtVal;

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
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException {

        // 本想用反射实现,但耗时长,所以还是手动编码吧,累
        if ("MsgId".equals(qName)) {
            msg.setMsgId(Long.valueOf(arrtVal));
            return ;
        }

        if ("CreateTime".equals(qName)) {
            msg.setCreateTime(Long.valueOf(arrtVal));
            return ;
        }

        if ("MsgType".equals(qName)) {
            msg.setMsgType(arrtVal);
            return ;
        }

        if ("Event".equals(qName)) {
            msg.setEvent(arrtVal);
            return ;
        }

        if ("ToUserName".equals(qName)) {
            msg.setToUserName(arrtVal);
            return ;
        }

        if ("FromUserName".equals(qName)) {
            msg.setFromUserName(arrtVal);
            return ;
        }

        if ("Content".equals(qName)) {
            msg.setContent(arrtVal);
            return ;
        }

        if ("PicUrl".equals(qName)) {
            msg.setPicUrl(arrtVal);
            return ;
        }

        if ("MediaId".equals(qName)) {
            msg.setMediaId(arrtVal);
            return ;
        }

        if ("Format".equals(qName)) {
            msg.setFormat(arrtVal);
            return ;
        }

        if ("Recognition".equals(qName)) {
            msg.setRecognition(arrtVal);
            return ;
        }

        if ("ThumbMediaId".equals(qName)) {
            msg.setThumbMediaId(arrtVal);
            return ;
        }

        if ("Location_X".equals(qName)) {
            msg.setLatitude(Double.valueOf(arrtVal));
            return ;
        }

        if ("Location_Y".equals(qName)) {
            msg.setLongitude(Double.valueOf(arrtVal));
            return ;
        }

        if ("Scale".equals(qName)) {
            msg.setScale(Integer.parseInt(arrtVal));
            return ;
        }

        if ("Label".equals(qName)) {
            msg.setLabel(arrtVal);
            return ;
        }

        if ("Title".equals(qName)) {
            msg.setTitle(arrtVal);
            return ;
        }

        if ("Description".equals(qName)) {
            msg.setDescription(arrtVal);
            return ;
        }

        if ("Url".equals(qName)) {
            msg.setUrl(arrtVal);
            return ;
        }

        if ("EventKey".equals(qName)) {
            msg.setEventKey(arrtVal);
            return ;
        }

        if ("Ticket(".equals(qName)) {
            msg.setTicket(arrtVal);
            return ;
        }

        if ("Latitude".equals(qName)) {
            msg.setLatitude(Double.valueOf(arrtVal));
            return ;
        }

        if ("Longitude".equals(qName)) {
            msg.setLongitude(Double.valueOf(arrtVal));
            return ;
        }

        if ("Precision".equals(qName)) {
            msg.setPrecision(Double.valueOf(arrtVal));
            return ;
        }

        if ("Status".equals(qName)) {
            msg.setStatus(arrtVal);
            return ;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        this.arrtVal = new String(ch, start, length);
    }

}

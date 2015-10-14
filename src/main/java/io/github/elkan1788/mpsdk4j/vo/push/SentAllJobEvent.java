package io.github.elkan1788.mpsdk4j.vo.push;

import java.util.Map;

/**
 * 群发消息处理事件
 * </p>
 * 微信发送消息状态（模板消息,群发消息）
 * 
 * <pre/>
 * 群发的结构，为“send success”或“send fail”或“err(num)”。但send success时，
 * 
 * <pre/>
 * 也有可能因用户拒收公众号的消息、系统错误等原因造成少量用户接收失败。err(num)是审核失败的具体原因，
 * 
 * <pre/>
 * 可能的情况如下：
 * 
 * <pre/>
 * err(10001), //涉嫌广告
 * 
 * <pre/>
 * err(20001), //涉嫌政治
 * 
 * <pre/>
 * err(20004), //涉嫌社会
 * 
 * <pre/>
 * err(20002), //涉嫌色情
 * 
 * <pre/>
 * err(20006), //涉嫌违法犯罪
 * 
 * <pre/>
 * err(20008), //涉嫌欺诈
 * 
 * <pre/>
 * err(20013), //涉嫌版权
 * 
 * <pre/>
 * err(22000), //涉嫌互推(互相宣传)
 * 
 * <pre/>
 * err(21000), //涉嫌其他
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class SentAllJobEvent extends SentTmlJobEvent {

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

    public SentAllJobEvent() {
        super();
    }

    public SentAllJobEvent(Map<String, String> values) {
        super(values);
        this.totalCnt = Integer.parseInt(values.get("totalCount"));
        this.filterCnt = Integer.parseInt(values.get("filterCount"));
        this.sentCnt = Integer.parseInt(values.get("sentCount"));
        this.errorCnt = Integer.parseInt(values.get("errorCount"));
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

    @ Override
    public String toString() {
        return "SenAllJobEvent [toUserName="
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
               + ", totalCnt="
               + totalCnt
               + ", filterCnt="
               + filterCnt
               + ", sentCnt="
               + sentCnt
               + ", errorCnt="
               + errorCnt
               + "]";
    }

}

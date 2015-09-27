package io.github.elkan1788.mpsdk4j.vo.event;

import java.util.Map;

/**
 * 模板消息推送事件
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class TmplFinshJobEvent extends BasicEvent {

    /**
     * 推送状态: 成功,用户拒收,系统繁忙
     */
    public static enum Status {
        success, userblock, systemfailed;
    }

    private String status;

    public TmplFinshJobEvent() {
        super();
    }

    public TmplFinshJobEvent(Map<String, String> values) {
        super(values);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TmpFinshJobEvent [toUserName="
               + toUserName
               + ", fromUserName="
               + fromUserName
               + ", createTime="
               + createTime
               + ", msgType="
               + msgType
               + ", event="
               + event
               + ", status="
               + status
               + "]";
    }

}

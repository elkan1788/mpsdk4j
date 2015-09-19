package io.github.elkan1788.mpsdk4j.vo.event;

import java.util.Map;

/**
 * 菜单事件
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class MenuEvent extends BasicEvent {

    public MenuEvent() {
        super();
    }

    public MenuEvent(Map<String, String> values) {
        super(values);
    }

    @ Override
    public String toString() {
        return "MenuEvent [toUserName="
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
               + "]";
    }

}

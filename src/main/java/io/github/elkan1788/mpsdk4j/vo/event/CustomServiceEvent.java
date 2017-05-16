package io.github.elkan1788.mpsdk4j.vo.event;

import java.util.Map;

/**
 * 多客服消息转发
 *
 * @author 凡梦星尘(elkan1788@gmail.com
 * @since 2.b.2
 */
public class CustomServiceEvent extends BasicEvent {

    /**
     * 客服账号
     */
    private String kfAccount;

    /**
     * 转出客服账号
     */
    private String fromKfAccount;

    /**
     * 接入客服账号
     */
    private String toKfAccount;

    public CustomServiceEvent(Map<String, String> values) {
        super(values);
        this.kfAccount = values.get("kfAccount");
        this.fromKfAccount = values.get("fromKfAccount");
        this.toKfAccount = values.get("toKfAccount");
    }

    public String getKfAccount() {
        return kfAccount;
    }

    public void setKfAccount(String kfAccount) {
        this.kfAccount = kfAccount;
    }

    public String getFromKfAccount() {
        return fromKfAccount;
    }

    public void setFromKfAccount(String fromKfAccount) {
        this.fromKfAccount = fromKfAccount;
    }

    public String getToKfAccount() {
        return toKfAccount;
    }

    public void setToKfAccount(String toKfAccount) {
        this.toKfAccount = toKfAccount;
    }

    @Override
    public String toString() {
        return "CustomServiceEvent [toUserName="
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
               + ", kfAccount="
               + kfAccount
               + ", fromKfAccount="
               + fromKfAccount
               + ", toKfAccount="
               + toKfAccount
               + "]";
    }

}

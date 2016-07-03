package io.github.elkan1788.mpsdk4j.vo.api;

import org.nutz.json.JsonField;

/**
 * 客服会话
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.b.2
 */
public class KfSession {

    @JsonField("openid")
    private String openId;

    @JsonField("kf_account")
    private String kfAccount;

    @JsonField("createtime")
    private Long createTime;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getKfAccount() {
        return kfAccount;
    }

    public void setKfAccount(String kfAccount) {
        this.kfAccount = kfAccount;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "KfSession [openId="
               + openId
               + ", kfAccount="
               + kfAccount
               + ", createTime="
               + createTime
               + "]";
    }
   
}

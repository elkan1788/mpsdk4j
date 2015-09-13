package io.github.elkan1788.mpsdk4j.vo.api;

import org.nutz.json.JsonField;

/**
 * 二维码Ticket
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class QRTicket {

    /**
     * 获取的二维码ticket,凭借此ticket可以在有效时间内换取二维码
     */
    private String ticket;

    /**
     * 维码的有效时间,以秒为单位,最大不超过1800
     */
    @JsonField(value = "expire_seconds")
    private int expireSeconds;

    /**
     * 二维码图片解析后的地址,开发者可根据该地址自行生成需要的二维码图片
     */
    private String url;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "QRTicket [ticket="
               + ticket
               + ", expireSeconds="
               + expireSeconds
               + ", url="
               + url
               + "]";
    }
}

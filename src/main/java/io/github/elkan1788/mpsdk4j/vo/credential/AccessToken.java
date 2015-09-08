package io.github.elkan1788.mpsdk4j.vo.credential;

import org.nutz.json.JsonField;
import org.nutz.lang.Lang;

/**
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class AccessToken {

    @JsonField(value = "access_token")
    private String accessToken;

    @JsonField(value = "expires_in")
    private long expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = (expiresIn - 30) * 1000;
    }

    public boolean isAvailable() {
        if (!Lang.isEmpty(accessToken) || this.expiresIn >= System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}

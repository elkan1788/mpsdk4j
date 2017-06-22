package io.github.elkan1788.mpsdk4j.exception;

/**
 * 微信高级API请求异常
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class WechatAPIException extends Exception {

    private static final long serialVersionUID = -303278319021435258L;

    public WechatAPIException() {
        super();
    }

    public WechatAPIException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatAPIException(String message) {
        super(message);
    }

    public WechatAPIException(Throwable cause) {
        super(cause);
    }

}

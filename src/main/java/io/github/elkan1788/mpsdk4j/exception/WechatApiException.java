package io.github.elkan1788.mpsdk4j.exception;

/**
 * 微信高级API请求异常
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class WechatApiException extends Exception {

    private static final long serialVersionUID = -303278319021435258L;

    public WechatApiException() {
        super();
    }

    public WechatApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatApiException(String message) {
        super(message);
    }

    public WechatApiException(Throwable cause) {
        super(cause);
    }

}

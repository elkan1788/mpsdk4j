package io.github.elkan1788.mpsdk4j.exception;

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

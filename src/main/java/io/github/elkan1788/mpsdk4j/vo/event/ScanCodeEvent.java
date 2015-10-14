package io.github.elkan1788.mpsdk4j.vo.event;

import java.util.Map;

/**
 * 扫码事件
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class ScanCodeEvent extends BasicEvent {

    /**
     * 扫描类型,一般是qrcode
     */
    private String scanType;
    /**
     * 扫描结果,即二维码对应的字符串信息
     */
    private String scanResult;

    public ScanCodeEvent() {
        super();
    }

    public ScanCodeEvent(Map<String, String> values) {
        super(values);
        this.scanType = values.get("scanType");
        this.scanResult = values.get("scanResult");
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getScanResult() {
        return scanResult;
    }

    public void setScanResult(String scanResult) {
        this.scanResult = scanResult;
    }

    @ Override
    public String toString() {
        return "ScanCodeEvent [toUserName="
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
               + ", scanType="
               + scanType
               + ", scanResult="
               + scanResult
               + "]";
    }

}

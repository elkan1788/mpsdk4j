package io.github.elkan1788.mpsdk4j.vo.event;

import java.util.List;

/**
 * 扫码发图像信息
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class SendPicsInfo {

    /**
     * 发送的图片数量
     */
    private int count;
    /**
     * 图片列表
     */
    private List<PicItem> picList;

    public SendPicsInfo() {
        super();
    }

    public SendPicsInfo(int count, List<PicItem> picList) {
        this();
        this.count = count;
        this.picList = picList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PicItem> getPicList() {
        return picList;
    }

    public void setPicList(List<PicItem> picList) {
        this.picList = picList;
    }

    @ Override
    public String toString() {
        return "SendPicsInfo [count=" + count + ", picList=" + picList + "]";
    }

}

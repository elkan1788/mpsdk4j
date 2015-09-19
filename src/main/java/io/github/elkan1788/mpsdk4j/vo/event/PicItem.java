package io.github.elkan1788.mpsdk4j.vo.event;

/**
 * 相片MD5信息
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class PicItem {

    /**
     * 图片的MD5值,开发者若需要,可用于验证接收到图片
     */
    private String picMd5Sum;

    public PicItem() {
        super();
    }

    public PicItem(String picMd5Sum) {
        this.picMd5Sum = picMd5Sum;
    }

    public String getPicMd5Sum() {
        return picMd5Sum;
    }

    public void setPicMd5Sum(String picMd5Sum) {
        this.picMd5Sum = picMd5Sum;
    }

    @ Override
    public String toString() {
        return "PicItem [picMd5Sum=" + picMd5Sum + "]";
    }
}

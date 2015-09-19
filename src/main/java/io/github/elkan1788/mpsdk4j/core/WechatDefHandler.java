package io.github.elkan1788.mpsdk4j.core;

import org.nutz.lang.Strings;

import io.github.elkan1788.mpsdk4j.vo.event.BasicEvent;
import io.github.elkan1788.mpsdk4j.vo.event.LocationEvent;
import io.github.elkan1788.mpsdk4j.vo.event.LocationSelectEvent;
import io.github.elkan1788.mpsdk4j.vo.event.MenuEvent;
import io.github.elkan1788.mpsdk4j.vo.event.ScanCodeEvent;
import io.github.elkan1788.mpsdk4j.vo.event.SendLocationInfo;
import io.github.elkan1788.mpsdk4j.vo.event.SendPhotosEvent;
import io.github.elkan1788.mpsdk4j.vo.message.BasicMsg;
import io.github.elkan1788.mpsdk4j.vo.message.ImageMsg;
import io.github.elkan1788.mpsdk4j.vo.message.LinkMsg;
import io.github.elkan1788.mpsdk4j.vo.message.TextMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VideoMsg;
import io.github.elkan1788.mpsdk4j.vo.message.VoiceMsg;
import io.github.elkan1788.mpsdk4j.vo.push.SentAllJobEvent;
import io.github.elkan1788.mpsdk4j.vo.push.SentTmlJobEvent;

/**
 * 微信消息,事件处理器(实际生产中需要重写)
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public class WechatDefHandler implements WechatHandler {

    @ Override
    public BasicMsg defMsg(BasicMsg bm) {
        TextMsg tm = new TextMsg(bm);
        tm.setContent(bm.getMsgType());
        return bm;
    }

    @ Override
    public BasicMsg defEvent(BasicEvent be) {
        TextMsg tm = new TextMsg(be);
        tm.setContent(Strings.join("\n", be.getEvent(), be.getEventKey()));
        return tm;
    }

    @ Override
    public BasicMsg text(TextMsg tm) {
        return tm;
    }

    @ Override
    public BasicMsg image(ImageMsg im) {
        return im;
    }

    @ Override
    public BasicMsg voice(VoiceMsg vm) {
        return vm;
    }

    @ Override
    public BasicMsg video(VideoMsg vm) {
        return vm;
    }

    @ Override
    public BasicMsg link(LinkMsg lm) {
        TextMsg tm = new TextMsg(lm);
        tm.setContent(Strings.join("\n", lm.getTitle(), lm.getUrl()));
        return tm;
    }

    @ Override
    public BasicMsg eClick(MenuEvent me) {
        TextMsg tm = new TextMsg(me);
        tm.setContent(me.getEventKey());
        return tm;
    }

    @ Override
    public void eView(MenuEvent me) {}

    @ Override
    public BasicMsg eSub(BasicEvent be) {
        TextMsg tm = new TextMsg(be);
        tm.setContent("Welcom, wechat with use mpsdk4j!");
        return tm;
    }

    @ Override
    public void eUnSub(BasicEvent be) {}

    @ Override
    public BasicMsg eScan(BasicEvent be) {
        TextMsg tm = new TextMsg(be);
        tm.setContent(be.getEventKey());
        return tm;
    }

    @ Override
    public void eLocation(LocationEvent lm) {}

    @ Override
    public BasicMsg eScanCodePush(ScanCodeEvent sce) {
        TextMsg tm = new TextMsg(sce);
        tm.setContent(Strings.join("\n",
                                   sce.getEventKey(),
                                   sce.getScanType(),
                                   sce.getScanResult()));
        return tm;
    }

    @ Override
    public BasicMsg eScanCodeWait(ScanCodeEvent sce) {
        return this.eScanCodePush(sce);
    }

    @ Override
    public BasicMsg ePicSysPhoto(SendPhotosEvent spe) {
        TextMsg tm = new TextMsg(spe);
        tm.setContent(Strings.join("\n",
                                   spe.getEventKey(),
                                   String.valueOf(spe.getSendPicsInfo().getCount())));
        return tm;
    }

    @ Override
    public BasicMsg ePicPhotoOrAlbum(SendPhotosEvent spe) {
        return this.ePicSysPhoto(spe);
    }

    @ Override
    public BasicMsg ePicWeixin(SendPhotosEvent spe) {
        return this.ePicSysPhoto(spe);
    }

    @ Override
    public BasicMsg eLocationSelect(LocationSelectEvent lse) {
        TextMsg tm = new TextMsg(lse);
        SendLocationInfo sli = lse.getSendLocationInfo();
        tm.setContent(Strings.join("\n",
                                   sli.getLocationX(),
                                   sli.getLocationY(),
                                   sli.getLabel(),
                                   String.valueOf(sli.getScale()),
                                   sli.getPoiname()));
        return tm;
    }

    @ Override
    public void eTemplateFinish(SentTmlJobEvent stje) {}

    @ Override
    public void eSendJobFinish(SentAllJobEvent saje) {}

}

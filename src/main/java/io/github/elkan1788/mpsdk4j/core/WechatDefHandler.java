package io.github.elkan1788.mpsdk4j.core;

import java.util.Arrays;

import org.nutz.lang.Strings;

import io.github.elkan1788.mpsdk4j.vo.event.BasicEvent;
import io.github.elkan1788.mpsdk4j.vo.event.LocationEvent;
import io.github.elkan1788.mpsdk4j.vo.event.MenuEvent;
import io.github.elkan1788.mpsdk4j.vo.event.ScanCodeEvent;
import io.github.elkan1788.mpsdk4j.vo.event.ScanEvent;
import io.github.elkan1788.mpsdk4j.vo.event.SendLocationInfoEvent;
import io.github.elkan1788.mpsdk4j.vo.event.SendPhotosEvent;
import io.github.elkan1788.mpsdk4j.vo.message.Article;
import io.github.elkan1788.mpsdk4j.vo.message.BasicMsg;
import io.github.elkan1788.mpsdk4j.vo.message.ImageMsg;
import io.github.elkan1788.mpsdk4j.vo.message.LinkMsg;
import io.github.elkan1788.mpsdk4j.vo.message.LocationMsg;
import io.github.elkan1788.mpsdk4j.vo.message.NewsMsg;
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

    @Override
    public BasicMsg defMsg(BasicMsg bm) {
        TextMsg tm = new TextMsg(bm);
        tm.setContent(bm.getMsgType());
        return tm;
    }

    @Override
    public BasicMsg defEvent(BasicEvent be) {
        TextMsg tm = new TextMsg(be);
        tm.setContent(Strings.join("\n", be.getEvent(), be.getEventKey()));
        return tm;
    }

    @Override
    public BasicMsg text(TextMsg tm) {
        return tm;
    }

    @Override
    public BasicMsg image(ImageMsg im) {
        return im;
    }

    @Override
    public BasicMsg voice(VoiceMsg vm) {
        TextMsg tm = new TextMsg(vm);
        tm.setContent(Strings.join("\n", vm.getMediaId(), vm.getFormat(), vm.getRecognition()));
        return tm;
    }

    @Override
    public BasicMsg video(VideoMsg vm) {
        TextMsg tm = new TextMsg(vm);
        tm.setContent(Strings.join("\n", vm.getMsgType(), vm.getMediaId(), vm.getThumbMediaId()));
        return tm;
    }

    @Override
    public BasicMsg location(LocationMsg lm) {
        TextMsg tm = new TextMsg(lm);
        tm.setContent(Strings.join("\n",
                                   lm.getX(),
                                   lm.getY(),
                                   String.valueOf(lm.getScale()),
                                   lm.getLabel()));
        return tm;
    }

    @Override
    public BasicMsg link(LinkMsg lm) {
        NewsMsg news = new NewsMsg(lm);
        Article art = new Article();
        art.setTitle(lm.getTitle());
        art.setDescription(lm.getDescription());
        art.setPicUrl("http://j2ee.u.qiniudn.com/mpsdk4j-logo.png-aliassmall");
        art.setUrl(lm.getUrl());
        news.setArticles(Arrays.asList(art));
        return news;
    }

    @Override
    public BasicMsg eClick(MenuEvent me) {
        TextMsg tm = new TextMsg(me);
        tm.setContent(me.getEventKey());
        return tm;
    }

    @Override
    public void eView(MenuEvent me) {}

    @Override
    public BasicMsg eSub(BasicEvent be) {
        TextMsg tm = new TextMsg(be);
        tm.setContent("Welcom, wechat develop with use mpsdk4j!");
        return tm;
    }

    @Override
    public void eUnSub(BasicEvent be) {}

    @Override
    public BasicMsg eScan(ScanEvent se) {
        TextMsg tm = new TextMsg(se);
        tm.setContent(se.getEventKey() + se.getTicket());
        return tm;
    }

    @Override
    public void eLocation(LocationEvent le) {}

    @Override
    public BasicMsg eScanCodePush(ScanCodeEvent sce) {
        TextMsg tm = new TextMsg(sce);
        tm.setContent(Strings.join("\n",
                                   sce.getEventKey(),
                                   sce.getScanType(),
                                   sce.getScanResult()));
        return tm;
    }

    @Override
    public BasicMsg eScanCodeWait(ScanCodeEvent sce) {
        return this.eScanCodePush(sce);
    }

    @Override
    public BasicMsg ePicSysPhoto(SendPhotosEvent spe) {
        TextMsg tm = new TextMsg(spe);
        tm.setContent(Strings.join("\n",
                                   spe.getEventKey(),
                                   String.valueOf(spe.getSendPicsInfo().getCount())));
        return tm;
    }

    @Override
    public BasicMsg ePicPhotoOrAlbum(SendPhotosEvent spe) {
        return this.ePicSysPhoto(spe);
    }

    @Override
    public BasicMsg ePicWeixin(SendPhotosEvent spe) {
        return this.ePicSysPhoto(spe);
    }

    @Override
    public BasicMsg eLocationSelect(SendLocationInfoEvent slie) {
        TextMsg tm = new TextMsg(slie);
        tm.setContent(Strings.join("\n",
                                   slie.getLocationX(),
                                   slie.getLocationY(),
                                   slie.getLabel(),
                                   String.valueOf(slie.getScale()),
                                   slie.getPoiname()));
        return tm;
    }

    @Override
    public void eTemplateFinish(SentTmlJobEvent stje) {}

    @Override
    public void eSendJobFinish(SentAllJobEvent saje) {}

}

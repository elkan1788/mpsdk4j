package org.elkan1788.osc.weixin.mp.core;

import org.elkan1788.osc.testunit.TestSupport;
import org.elkan1788.osc.weixin.mp.vo.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 微信API接口测试
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/12
 */
public class WxApiImplTest extends TestSupport {

    private WxApi wxApi;

    @Before
    public void init() throws Exception {
        super.init();

        wxApi = new WxApiImpl(mpAct);
        if ("NOT".equals(accessToken)
                || "".equals(accessToken)
                || accessToken.isEmpty()) {
            mpAct.setToken(wxApi.getAccessToken());
            mpAct.setExpiresIn(7000 * 1000 + System.currentTimeMillis());
        }
        System.out.println("ACCESS_TOKEN: " + mpAct.getAccessToken());
    }

    @Test
    public void testGetAccessToken() throws Exception {
        String token = wxApi.getAccessToken();
        System.out.println("token:"+token);
        System.out.println("create time:"+System.currentTimeMillis()+60*1000);
    }

    @Test
    public void testGetServerIp() throws Exception {
        List<String> ips = wxApi.getServerIp();
        for (String ip : ips) {
            System.out.println(ip);
        }
    }

    @Test
    public void testGetMenu() throws Exception {
        List<Menu> menus = wxApi.getMenu();
        System.out.println("拉取到的菜单: " + menus);
    }

    @Test
    public void testCreateMenu() throws Exception {
        Menu m1 = new Menu("菜单一", Menu.VIEW, "http://www.csdn.net");
        Menu m2 = new Menu("菜单二", Menu.VIEW, "http://www.iteye.com");
        Menu m3 = new Menu("菜单三", Menu.VIEW, "http://www.oschina.net");
        boolean flag = wxApi.createMenu(m1, m2, m3);
        System.out.println("创建菜单: " + flag);
    }

    @Test
    public void testDeleteMenu() throws Exception {
        boolean flag = wxApi.deleteMenu();
        System.out.println("删除菜单: " + flag);
    }

    @Test
    public void testCreatGroup() throws Exception {
        int groupId = wxApi.creatGroup("公司员工");
        System.out.println("创建分组: " + groupId);
    }

    @Test
    public void testGetGroups() throws Exception {
        List<Group> groups = wxApi.getGroups();
        System.out.println("拉取到的分组: " + groups);
    }

    @Test
    public void testRenGroup() throws Exception {
        boolean flag = wxApi.renGroup(104, "内部测试人员");
        System.out.println("修改分组: " + flag);
    }

    @Test
    public void testGetGroupId() throws Exception {
        int groupId = wxApi.getGroupId(openId);
        System.out.println("拉取到用户分组: " + groupId);
    }

    @Test
    public void testMove2Group() throws Exception {
        boolean flag = wxApi.move2Group("oMIXfjjEB8ifmUpjauItMXpGAik0", 104);
        System.out.println("移动用户分组: " + flag);
    }

    @Test
    public void testGetFollowerList() throws Exception {
        FollowList follows = wxApi.getFollowerList("");
        System.out.println("拉取用户关注列表: " + follows);
    }

    @Test
    public void testGetFollower() throws Exception {
        Follower follower = wxApi.getFollower(openId, "zh_CN");
        System.out.println("拉取用户的信息: " + follower);
    }

    @Test
    public void testSendCustomerMsg() throws Exception {
        // 文本客服消息
        OutPutMsg msg = new OutPutMsg();
        msg.setToUserName(openId);
        msg.setMsgType(WxApi.TEXT);
        msg.setContent("感谢你持续关注我们的公众号[微笑].");
        boolean flag = wxApi.sendCustomerMsg(msg);
        System.out.println("发送文本客服消息: " + flag);
        // 图像客服消息
        /*msg.setMsgType(WxApi.IMAGE);
        msg.setMediaId(mediaId);
        flag = wxApi.sendCustomerMsg(msg);
        System.out.println("发送图像客服消息: " + flag);*/
    }

    @Test
    public void testSendTemplateMsg() throws Exception {
        Template first = new Template("first", "#fffff", "faithzhang回复了您的评论");
        Template keyword = new Template("keyword", "#cccccc", "请问腾讯的妈妈们都几点下班？");
        Template time = new Template("time", "#000000", "2014/11/11 11:11:11");
        boolean flag = wxApi.sendTemplateMsg(openId, templateId, "#FF0000", "", first, keyword, time);
        System.out.println("发送模板消息: " + flag);
    }

    @Test
    public void testUpMedia() throws Exception {
        File image = new File("D:/upload.jpg");
        String mediaId = wxApi.upMedia("image", image);
        System.out.println("上传多媒体文件: " + mediaId);
    }

    @Test
    public void testDlMedia() throws Exception {
        File image = new File("D:/dl/" + mediaId + ".jpg");
        wxApi.dlMedia(mediaId, image);
    }

    @Test
    public void testUpNews() throws Exception {
        File image1 = new File("D:/art1.jpg");
        String mediaId1 = wxApi.upMedia(WxApi.IMAGE, image1);
        File image2 = new File("D:/art2.jpg");
        String mediaId2 = wxApi.upMedia(WxApi.IMAGE, image2);
        Article2 art1 = new Article2();
        art1.setTitle("不差钱！玩酷4G上线送话费啦！");
        art1.setDigest("不差钱！玩酷4G上线送话费啦！!");
        art1.setAuthor("凡梦星尘");
        art1.setContent("<div class=\"rich_media_content\" id=\"js_content\"><p><strong style=\"\"></strong></p><p><img src=\"http://mmbiz.qpic.cn/mmbiz/ep7j3xpY7tWjcshJibxiarWSWBFRLVPZHiaoguCiaTzcyD4qLJc28VVhV2AfjhXKXj8FLXgZdEuibQrw4Rek8uAkVqw/0\" data-src=\"http://mmbiz.qpic.cn/mmbiz/ep7j3xpY7tWjcshJibxiarWSWBFRLVPZHiaoguCiaTzcyD4qLJc28VVhV2AfjhXKXj8FLXgZdEuibQrw4Rek8uAkVqw/0\" data-ratio=\"0.11857707509881422\" data-w=\"\" style=\"font-size: 14px; width: auto ! important; visibility: visible ! important; height: auto ! important;\"><br></p><p><strong><span style=\"color: white; background-color: olive;\">就是那么有钱！就是那么任性！</span></strong></p><p><strong><span style=\"color: white; background-color: olive;\"><br></span></strong></p><p><strong><span style=\"font-size: 16px;color: white;background-color: olive\"><img src=\"http://mmbiz.qpic.cn/mmbiz/ep7j3xpY7tVlGHefcbrGvdyFSIwSC8ryDG57De56FyaLIAvCASohRwS0cQ21pxfa8oKha1JwjotJPkr4wm4j5g/640\" style=\"width: auto ! important; visibility: visible ! important; height: auto ! important;\" data-s=\"300,640\" data-src=\"http://mmbiz.qpic.cn/mmbiz/ep7j3xpY7tVlGHefcbrGvdyFSIwSC8ryDG57De56FyaLIAvCASohRwS0cQ21pxfa8oKha1JwjotJPkr4wm4j5g/0\" data-ratio=\"0.744\" data-w=\"500\"></span></strong></p><p><strong><span style=\"font-size: 16px;color: white;background-color: olive\"></span></strong></p><p style=\"\">这个季节，谁最风靡，是的，是我们玩酷4G!</p><p style=\"\">这个年龄段，谁能带领互联网潮流，是的，是我们玩酷4G！</p><p style=\"\">玩酷4G新上线啦，这里有新鲜、时尚的IT资讯，新潮、免费的产品体验，我们的福利多多！就是那么有钱，就是那么任性，就是那么时尚时尚最时尚！</p><p><strong><span style=\"font-size: 16px;color: white;background-color: olive\"><br></span></strong></p><p><strong><span style=\"font-size: 16px;color: white;background-color: olive\"><img src=\"http://mmbiz.qpic.cn/mmbiz/ep7j3xpY7tVlGHefcbrGvdyFSIwSC8ryV1UoQbTlqwjN6gXHfXibsxXYW5YzMd3vvd8ysknvaAmWgm69dMmIFCQ/640\" style=\"width: auto ! important; visibility: visible ! important; height: auto ! important;\" data-s=\"300,640\" data-src=\"http://mmbiz.qpic.cn/mmbiz/ep7j3xpY7tVlGHefcbrGvdyFSIwSC8ryV1UoQbTlqwjN6gXHfXibsxXYW5YzMd3vvd8ysknvaAmWgm69dMmIFCQ/0\" data-ratio=\"0.5553359683794467\" data-w=\"\"></span></strong></p><p style=\"\"><span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; color: rgb(255, 255, 255); background-color: rgb(118, 146, 60);\"><strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\">玩酷4G公共账号上线第一波活动！咱们年轻人！不差钱！</strong></span></p><p style=\"\"><span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; color: rgb(79, 97, 40);\"><strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\">活动一</strong></span>：<strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\">关注玩酷4G公共账号，就有机会获赠</strong><strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\"><span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; background-color: rgb(255, 255, 0);\">电信充值卡</span></strong>。</p><p style=\"\">1、小伙伴们只要关注玩酷4G，就自动拥有抽奖资格了。</p><p style=\"\">2、等着好运降临！</p><p style=\"\">3、奖品：<span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; background-color: rgb(255, 255, 0);\"><strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\">11888话费充值卡</strong></span>。每期活动有：<span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; background-color: rgb(255, 255, 0);\"><strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\">100元2名</strong></span>！<span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; background-color: rgb(255, 255, 0);\"><strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\">50元4名</strong></span>！<span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; background-color: rgb(255, 255, 0);\"><strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\">10元40名</strong></span>！ 11888话费卡可以充值话费、宽带费、翼支付，绝对的生活好帮手！</p><p style=\"\">4、活动从11月21日起正式开启！每周三公布获奖的小伙伴名单！共持续四期！</p><p style=\"\">5、获奖查询可以点击“中奖名单”，小伙伴们记得保持关注哦！</p><p style=\"\"><br style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\"></p><p style=\"\"><span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; color: rgb(79, 97, 40);\"><strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\">活动二</strong></span>：<strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\">关注玩酷4G公共账号，就有机会免费体验最新的互联网单品！</strong><strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\"><span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; background-color: rgb(255, 255, 0);\">华为荣耀立方智能路由器</span></strong>、<strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\"><span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; background-color: rgb(255, 255, 0);\">Bong手环</span></strong>、<span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; background-color: rgb(255, 255, 0);\"><strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\">天翼手机交通卡</strong></span>，一网打尽！</p><p style=\"\">1、关注玩酷4G账号，在“玩酷爆点”中点击你喜欢的互联网单品按要求报名参加体验活动。</p><p style=\"\">2、<span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; color: rgb(79, 97, 40);\"><strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\">首批互联网单品有</strong></span>：</p><p style=\"\">超越小米手环的<span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; background-color: rgb(255, 255, 0);\"><strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\">手环Bong</strong></span>！11月21日起，每周四公布获得Bong的小伙伴，连续三期，每期1名。</p><p><img src=\"http://mmbiz.qpic.cn/mmbiz/ep7j3xpY7tVlGHefcbrGvdyFSIwSC8rygLg4oDU8jfEqrUSicteLWThzMnvBOCXv6q7iaYsmOJ6GvECQAC71DXIg/640\" style=\"width: auto ! important; visibility: visible ! important; height: auto ! important;\" data-s=\"300,640\" data-src=\"http://mmbiz.qpic.cn/mmbiz/ep7j3xpY7tVlGHefcbrGvdyFSIwSC8rygLg4oDU8jfEqrUSicteLWThzMnvBOCXv6q7iaYsmOJ6GvECQAC71DXIg/0\" data-ratio=\"0.6699604743083004\" data-w=\"\"><span style=\"\">让生活无比简单的</span><span style=\"\"><strong style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important;\">翼支付手机交通卡</strong></span><span style=\"\">！没有名额限制。只要你报名，就让你体验！11月29日将公布全部体验成功的小伙伴名单。<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAIAAACQd1PeAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyBpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYwIDYxLjEzNDc3NywgMjAxMC8wMi8xMi0xNzozMjowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNSBXaW5kb3dzIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkJDQzA1MTVGNkE2MjExRTRBRjEzODVCM0Q0NEVFMjFBIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkJDQzA1MTYwNkE2MjExRTRBRjEzODVCM0Q0NEVFMjFBIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6QkNDMDUxNUQ2QTYyMTFFNEFGMTM4NUIzRDQ0RUUyMUEiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6QkNDMDUxNUU2QTYyMTFFNEFGMTM4NUIzRDQ0RUUyMUEiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz6p+a6fAAAAD0lEQVR42mJ89/Y1QIABAAWXAsgVS/hWAAAAAElFTkSuQmCC\" style=\"width: 327px ! important; visibility: visible ! important; height: 219px ! important;\" data-s=\"300,640\" data-src=\"http://mmbiz.qpic.cn/mmbiz/ep7j3xpY7tVlGHefcbrGvdyFSIwSC8rybVMmtq3x2H5oKVrvDfIBsAfBC2C1X7voXrfha8xBbGrsDuqOfDzMHQ/0\" data-ratio=\"0.6697247706422018\" data-w=\"327\"></span></p><p><span style=\"\"><span style=\"\">新时代的智能路由器</span><strong style=\"\"><span style=\"max-width: 100%; word-wrap: break-word !important; box-sizing: border-box !important; background-color: rgb(255, 255, 0);\">华为荣耀立方</span></strong><span style=\"\">！价值800！畅想精彩互联家庭生活！12月6日公布，只有一台，不要错过哦！</span></span></p><p><span style=\"\"><span style=\"\"><img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAIAAACQd1PeAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyBpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYwIDYxLjEzNDc3NywgMjAxMC8wMi8xMi0xNzozMjowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNSBXaW5kb3dzIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkJDQzA1MTVGNkE2MjExRTRBRjEzODVCM0Q0NEVFMjFBIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkJDQzA1MTYwNkE2MjExRTRBRjEzODVCM0Q0NEVFMjFBIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6QkNDMDUxNUQ2QTYyMTFFNEFGMTM4NUIzRDQ0RUUyMUEiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6QkNDMDUxNUU2QTYyMTFFNEFGMTM4NUIzRDQ0RUUyMUEiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz6p+a6fAAAAD0lEQVR42mJ89/Y1QIABAAWXAsgVS/hWAAAAAElFTkSuQmCC\" style=\"width: 698px ! important; visibility: visible ! important; height: 464.874px ! important;\" data-s=\"300,640\" data-src=\"http://mmbiz.qpic.cn/mmbiz/ep7j3xpY7tVlGHefcbrGvdyFSIwSC8ryruUTS6Sdq1OwcM42zowUdlQ3zsf1M5qapNvKFa6fA0ngPKqkqmm09A/0\" data-ratio=\"0.66600790513834\" data-w=\"\"></span></span></p><p><span style=\"\"><span style=\"\"></span></span></p><p style=\"\"><br></p><p><br></p></div>");
        art1.setMediaId(mediaId1);
        art1.setShowCover(1);
        art1.setSourceUrl("http://mp.weixin.qq.com/s?__biz=MzA3MTUyMTMyNg==&mid=201733564&idx=1&sn=ad92ead6499c322b4273cfb39708d40a&key=475e040e205b91d666e07d715eedc09b47d3c4631448d3cf4c229fec511f83e55cebe78da209f0aa51fcd1d8a82302e2&ascene=1&uin=MTU1ODU2MzgwMg%3D%3D&devicetype=webwx&version=70000001&pass_ticket=JG3EHTQ5Idcfm%2Bqt%2BhbFaY8U%2BKx3wCm%2FS6JZomsTeS10EjFBAsYWqTWOctZiAl5C");
    
        Article2 art2 = new Article2();
        art2.setTitle("【实用攻略】火车票预售期将延长至60天，买票攻略get！火速收藏~ ");
        art2.setDigest("【实用攻略】火车票预售期将延长至60天，买票攻略get！火速收藏~ ");
        art2.setAuthor("凡梦星尘");
        art2.setContent("自2014年12月1日起，中国铁路总公司将用一周时间，逐步将铁路互联网售票、电话订票的预售期，由目前的20天延长至60天。具体实施方法为：");
        art2.setMediaId(mediaId2);
        art2.setShowCover(1);
        art2.setSourceUrl("http://mp.weixin.qq.com/s?__biz=MzA4OTEwNDkyMw==&mid=201489837&idx=2&sn=a86b67dd34894ee1f124b2ddcd02c35b#rd");

        String[] results = wxApi.upNews(art1, art2);
        System.out.println("msgtype:"+results[0]);
        System.out.println("mediaid:"+results[1]);//q2t8zU4BGSH58YsBkL_WCdjdvVDZG1oyxe3NRgioLQp0gGjolztVjP6B4mugLfhE
        System.out.println("createat:"+results[2]);

    }

    @Test
    public void testUpVideo() throws Exception {
        File mp4 = new File("D:/kajiaren_clip.mp4");
        String mediaId = "KIV7QppP1LEwsXcUiSHx5BZEV4sIW53a_iX7uHWtU8QyG4maRvPiodiR_6t4JkX3"; //wxApi.upMedia(WxApi.VIDEO, mp4);
        System.out.println(mediaId);//KIV7QppP1LEwsXcUiSHx5BZEV4sIW53a_iX7uHWtU8QyG4maRvPiodiR_6t4JkX3
        String[] results  = wxApi.upVideo(mediaId,"歌曲《客家人系有料》","歌曲《客家人系有料》");
        System.out.println("msgtype:"+results[0]);
        System.out.println("mediaid:"+results[1]);//-h6ETcIv_vOeS_X3Wjf2VlSuwWiPfZSJBelgKlNffqDB8pgg7QWWPG986vOCmG3G
        System.out.println("createat:"+results[2]);
    }

    @Test
    public void testSendAll() throws Exception {
        OutPutMsg outmsg = new OutPutMsg();
        outmsg.setToUsers(Arrays.asList(openId));
        outmsg.setMsgType(WxApi.MPNEWS);
        outmsg.setMediaId("-ClbTNHmkAYlBmuY0lK-_GMipA7rDQvlekihc8u6oCr3AcQkemGcI1Nxj8Xtma_9");
        String msgid = wxApi.sendAll(outmsg);
        System.out.println("news msgid:"+msgid);//2348282442

      /*  outmsg.setMediaId("-h6ETcIv_vOeS_X3Wjf2VlSuwWiPfZSJBelgKlNffqDB8pgg7QWWPG986vOCmG3G");
        outmsg.setMsgType(WxApi.MPVIDEO);
        outmsg.setGroupId("104");
        String msgid = wxApi.sendAll(outmsg);
        System.out.println("video msgid:"+msgid);*///2348282861
    }

    @Test
    public void testDelSendAll() throws Exception {
        boolean flag = wxApi.delSendAll("2348282861");
        System.out.println("delsendall:"+flag);
    }
}
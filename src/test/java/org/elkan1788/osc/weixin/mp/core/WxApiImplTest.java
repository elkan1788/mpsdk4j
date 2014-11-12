package org.elkan1788.osc.weixin.mp.core;

import org.elkan1788.osc.testunit.TestSupport;
import org.elkan1788.osc.weixin.mp.vo.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
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
        if(!mpAct.getAccessToken().equals("NOT")) {
            System.out.println("ACCESS_TOKEN: "+wxApi.getAccessToken());
        }
    }

    @Test
    public void testGetAccessToken() throws Exception {
        String token = wxApi.getAccessToken();
        System.out.println(token);
    }

    @Test
    public void testGetMenu() throws Exception {
        List<Menu> menus = wxApi.getMenu();
        System.out.println("拉取到的菜单: "+menus);
    }

    @Test
    public void testCreateMenu() throws Exception {
        Menu m1 = new Menu("菜单一",Menu.VIEW, "http://www.csdn.net");
        Menu m2 = new Menu("菜单二",Menu.VIEW, "http://www.iteye.com");
        Menu m3 = new Menu("菜单三",Menu.VIEW, "http://www.oschina.net");
        boolean flag = wxApi.createMenu(m1, m2, m3);
        System.out.println("创建菜单: "+flag);
    }

    @Test
    public void testDeleteMenu() throws Exception {
        boolean flag = wxApi.deleteMenu();
        System.out.println("删除菜单: "+flag);
    }

    @Test
    public void testCreatGroup() throws Exception {
        int groupId = wxApi.creatGroup("公司员工");
        System.out.println("创建分组: "+groupId);
    }

    @Test
    public void testGetGroups() throws Exception {
        List<Group> groups = wxApi.getGroups();
        System.out.println("拉取到的分组: "+groups);
    }

    @Test
    public void testRenGroup() throws Exception {
        boolean flag = wxApi.renGroup(104, "内部测试人员");
        System.out.println("修改分组: "+flag);
    }

    @Test
    public void testGetGroupId() throws Exception {
        int groupId = wxApi.getGroupId(openId);
        System.out.println("拉取到用户分组: "+groupId);
    }

    @Test
    public void testMove2Group() throws Exception {
        boolean flag = wxApi.move2Group(openId, 104);
        System.out.println("移动用户分组: "+flag);
    }

    @Test
    public void testGetFollowerList() throws Exception {
        FollowList follows = wxApi.getFollowerList("");
        System.out.println("拉取用户关注列表: "+follows);
    }

    @Test
    public void testGetFollower() throws Exception {
        Follower follower = wxApi.getFollower(openId,"zh_CN");
        System.out.println("拉取用户的信息: "+follower);
    }

    @Test
    public void testSendCustomerMsg() throws Exception {
        // 文本客服消息
        OutPutMsg msg = new OutPutMsg();
        msg.setToUserName(openId);
        msg.setMsgType(WxApi.TEXT);
        msg.setContent("感谢你持续关注我们的公众号[微笑].");
        boolean flag = wxApi.sendCustomerMsg(msg);
        System.out.println("发送文本客服消息: "+flag);
        // 图像客服消息
        msg.setMsgType(WxApi.IMAGE);
        msg.setMediaId(mediaId);
        flag = wxApi.sendCustomerMsg(msg);
        System.out.println("发送图像客服消息: "+flag);
    }

    @Test
    public void testSendTemplateMsg() throws Exception {
        Template first = new Template("first","#fffff","faithzhang回复了您的评论");
        Template keyword = new Template("keyword","#cccccc","请问腾讯的妈妈们都几点下班？");
        Template time = new Template("time","#000000","2014/11/11 11:11:11");
        boolean flag = wxApi.sendTemplateMsg(openId, templateId, "#FF0000", "", first, keyword, time);
        System.out.println("发送模板消息: "+flag);
    }

    @Test
    public void testUpMedia() throws Exception {
        File image = new File("D:/upload.jpg");
        String mediaId = wxApi.upMedia("image", image);
        System.out.println("上传多媒体文件: "+mediaId);
    }

    @Test
    public void testDlMedia() throws Exception {
        File image = new File("D:/dl/"+mediaId+".jpg");
        wxApi.dlMedia(mediaId, image);
    }
}
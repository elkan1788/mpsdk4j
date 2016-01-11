package io.github.elkan1788.mpsdk4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.nutz.json.Json;
import org.testng.annotations.BeforeClass;

import io.github.elkan1788.mpsdk4j.api.WechatAPI;
import io.github.elkan1788.mpsdk4j.api.WechatAPIImpl;
import io.github.elkan1788.mpsdk4j.util.HttpTool;
import io.github.elkan1788.mpsdk4j.vo.MPAccount;
import io.github.elkan1788.mpsdk4j.vo.api.AccessToken;
import io.github.elkan1788.mpsdk4j.vo.api.Groups;
import io.github.elkan1788.mpsdk4j.vo.message.Article;
import io.github.elkan1788.mpsdk4j.vo.message.MusicMsg;
import mockit.Mock;
import mockit.MockUp;

/**
 * 所有测试的基类
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.b.2
 */
public class RunTestSupport {

    // 测试所需要的参数
    protected String mpId = "gh_20e95b3b4b6d";
    protected String appId = "wxa822bd8795de1655";
    protected String appSecret = "613d3ceggf98f71a875d1342c8325f9i";
    protected String aesKey = "EJpm8tCsb0un6O3tIcdGhohGeWienX";
    protected String token = "mpdevelop";

    protected String openId = "ouXj-svl7dwexzl1azI9nvOKrl6c";
    protected int groupId = 100;
    protected int groupCount = 1000;
    protected String groupName = "测试分组";
    protected String mediaId = "YhVfo1ZKHxMZ3WuC8fCRAGi4KaWfMQ";
    protected String tmplId = "ae2xB7AMQIpOBZNhiPNJgUF9VkFe1WDsc7v6VDqUM2c";
    protected List<Article> articles = new ArrayList<Article>();
    protected MusicMsg musicMsg;

    protected File imgMedia;

    protected String url = "https://mp.weixin.qq.com/wiki/home/index.html";

    protected String success = "{\"errcode\": 0, \"errmsg\": \"ok\"}";

    protected MPAccount mpAccount = new MPAccount();
    protected AccessToken accessToken =  new AccessToken();
    protected Groups groups = new Groups();
    protected String uuid = UUID.randomUUID().toString();

    protected WechatAPI wechatAPI;

    @BeforeClass
    public void initData() {
        this.mpAccount.setMpId(this.mpId);
        this.mpAccount.setAppId(this.appId);
        this.mpAccount.setAppSecret(this.appSecret);
        this.mpAccount.setAESKey(this.aesKey);

        this.accessToken.setAccessToken(this.uuid);
        this.accessToken.setExpiresIn(7200);

        this.groups.setId(this.groupId);
        this.groups.setCount(this.groupCount);
        this.groups.setName(this.groupName);

        this.imgMedia = new File(this.getClass().getResource("/mpsdk4j-logo.png").getFile());

        this.musicMsg = new MusicMsg();
        this.musicMsg.setFromUserName(this.mpId);
        this.musicMsg.setToUserName(this.openId);
        this.musicMsg.setThumbMediaId(this.mediaId);
        this.musicMsg.setTitle("致爱 Your Song");
        this.musicMsg.setDescription("致爱 Your Song");
        this.musicMsg.setMusicUrl("http://y.qq.com/#type=song&mid=002IVyIU4093Xr&play=0");
        this.musicMsg.setHQMusicUrl("http://y.qq.com/#type=song&mid=002IVyIU4093Xr&play=0");

        this.articles.add(
                new Article("开源中国 - 找到您想要的开源项目，分享和交流",
                        "多图文消息",
                        "http://www.oschina.net/img/logo.svg",
                        "http://www.oschina.net"));
        this.articles.add(
                new Article("ITeye做最棒的软件开发交流社区",
                        "多图文消息",
                        "http://www.iteye.com/images/logo.gif",
                        "http://www.iteye.cn"));

        //
        wechatAPI = WechatAPIImpl.create(mpAccount);
        createAccessToken();
        wechatAPI.getAccessToken();

    }

    public void invalidAppIdException() {
        MockUpHttpGet("{\"errcode\":40013,\"errmsg\":\"invalid appid\"}");
    }

    public void getMethodLostATException() {
        MockUpHttpGet("{\"errcode\":41001,\"errmsg\":\"lack parameter for access token.\"}");
    }

    public void postMethodLostATException() {
        MockUpHttpPost("{\"errcode\":41001,\"errmsg\":\"lack parameter for access token.\"}");
    }

    public void createAccessToken() {
        MockUpHttpGet(Json.toJson(accessToken));
    }

    public void getMethodSuccess() {
        MockUpHttpGet(success);
    }

    public void postMethodSuccess(){
        MockUpHttpPost(success);
    }

    public void MockUpHttpGet(final String result){

        new MockUp<HttpTool>(){

            @Mock
            public String get(String url) {
                return result;
            }

        };
    }

    public void MockUpHttpPost(final String result){

        new MockUp<HttpTool>(){

            @Mock
            public String post(String url, String body) {
                return result;
            }

        };
    }

    public void MockUpHttpUpload(final String result){

        new MockUp<HttpTool>(){

            @Mock
            public String upload(String url, File file) {
                return result;
            }

        };
    }

    public void MockUpHttpDownload(final Object result){

        new MockUp<HttpTool>(){

            @Mock
            public Object download(String url) {
                return result;
            }

        };
    }
}

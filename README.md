!["MPSDK4J"](http://j2ee.u.qiniudn.com/mpsdk4j-logo.png-aliassmall "MPSDK4J")
## MPSDK4J
### 目录
*  [1.引言](#引言)
*  [2.介绍](#介绍)
  * [2.1.结构设计](#结构设计)
  * [2.2.交互时序](#交互时序)
*  [3.项目](#项目)
  * [3.1.最新源码](#最新源码)
  * [3.2.Maven库引用](#Maven库引用)
*  [4.示例代码](#示例代码)
  * [4.1.HttpServlet环境](#HttpServlet环境)
  * [4.2.Struts2环境](#Struts2环境)
  * [4.3SpringMVC环境](#SpringMVC环境)
*  [5.Issue](#Issue)
*  [6.联系](#联系)
*  [7.其它](#其它)

<a name="引言"></a>
#### 1.引言
双11是一个令不少人狂欢的日子，今天你买了么？或许在那XXX亿的曲线中能找到你的影子哟，呵~，不过这与俺无关了，只是借用这个双11来纪念一下而已。从事微信公众平台开发也有一段时间了，算是积累了不少经验吧，趁这些天空闲的时间，把这些经验重构一遍拿出来分享一下。特此声明本人并非技术大牛（纯粹的技术渣），只是用最简单的流程与编码实现微信公众平台交互SDK，有什么不满意的话，可以尽情的吐槽。目前已经实现微信API所有的功能且同步至官方最新发布（如最新的AES消息加密），后续还会不断的扩展（会做一个多微信号管理平台），欢迎关注加入，谢谢。

<a name="介绍"></a>
#### 2.介绍
**MPSDK4J**，非常直观的阐述了此项目的意义所在。没错，它就是JAVA语言环境下的微信公众平台开发SDK。其中MP代表的是微信公众平台的域名前缀，SDK表示开发工具包，4同音英文“for”，J代表了JAVA。虽然现网络上已经有不少JAVA版本的SDK现身，但是***[MPSDK4J]*** 的出现也并非只是造轮子的重复工作。它遵循单一设计模式规则，所有的设计与功能都是源于微信公众平台API，一切都是为了追求简单与速度。

>**a.设计简单**：整体设计非常的简单，仅有7个包39个类对象（其中VO对象占据一半之多，详见结构设计图），核心功能部分就4类（WxBase，WxApi，WxOpenApi，WxHandler）；

>**b.解析速度**：基本SAX驱动式XML处理,结合JDK7的新特性，能够快速的解析收到用户发送的微信消息，放弃JAVA反射功能直接编码生成VO对象更加快速；

>**c.敏捷开发**：微信交互信息全都统一封装VO对象，所有VO的属性都是微信公众平台API原生状态。开发者无须再关心它来源是XML还JSON格式，其中消息的收发只需掌握2个VO（ReceiveMsg，OutPutMsg）即可；

>**d.支持力度**：API功能分为三个部分（后续会不断更新升级），微信基本消息的交互，高级接口（Token，自定义菜单，模板消息，群发消息等等）及开放平台功能接口的调用。

<a name="结构设计"></a>
##### 2.1结构设计
!["MPSDK4J设计图"](http://j2ee.u.qiniudn.com/mpsdk4j-class-design.png-alias "MPSDK4J设计图")

<a name="交互时序"></a>
##### 2.2交互时序
!["MPSDK4J交互时序图"](http://j2ee.u.qiniudn.com/mpsdk4j-sequence.png-alias "MPSDK4J交互时序图")

<a name="项目"></a>
#### 3.项目

<a name="最新源码"></a>
##### 3.1最新源码
* OSChina项目主页: <https://git.oschina.net/lisenhui/mpsdk4j>
* 开源协议：[Apache Licenses 2.0](http://www.apache.org/licenses/LICENSE-2.0)

<a name="Maven库引用"></a>
##### 3.2Maven库引用
另外你也可以通过OSChina的Maven库获取依赖

*  1.加入OSC仓库
```xml
<repositories>
   <repository>
       <id>nexus</id>
       <name>local private nexus</name>
       <url>http://maven.oschina.net/content/groups/public/</url>
       <releases>
          <enabled>true</enabled>
       </releases>
       <snapshots>
          <enabled>false</enabled>
       </snapshots>
     </repository>
</repositories> 
```

* 2.添加依赖坐标
```xml
<dependency>
  <groupId>org.elkan1788.osc</groupId>
  <artifactId>mpsdk4j</artifactId>
  <version>1.a.19</version>
</dependency>
```

或者自己编译jar包。
```
mvn clean package
```

<a name="示例代码"></a>
#### 4.示例代码
MPSDK4J在Web环境中暂时提供了以下三种支持，欢迎提交其它环境扩展。在实际的使用过程中只需要继承相应环境的Wx***Support父类，重写init初始化方法修改其中的公众号信息及微信消息处理器，添加环境的入口（Servlet环境无需此步骤），调用wxInteract方法，最后发布上线即可。
<a name="HttpServlet环境"></a>
##### 4.1.HttpServlet环境：
```java
@WebServlet(name = "weixinServlet", urlPatterns = "/weixin/mp/core.ser")
public class WeiXinServlet extends WxServletSupport {

    @Override
    public void init() throws ServletException {
        super.init();
        MPAct mpAct = new MPAct();
        // 修改为实际的公众号信息,可以在开发者栏目中查看
        mpAct.setAppId("wx****");
        mpAct.setAppSecert("***");
        mpAct.setToken("***");
        mpAct.setAESKey("******");
        this.setMpAct(mpAct);
        // 可实现自己的WxHandler
        this.setWxHandler(new WxDefaultHandler());
    }
}
```
<a name="SpringMVC环境"></a>
##### 4.2.SpringMVC环境：

```java
@Controller
@RequestMapping("/mp")
public class WeiXinController extends WxSpringSupport {

    @Override
    protected void init() {
        MPAct mpAct = new MPAct();
        // 修改为实际的公众号信息,可以在开发者栏目中查看
        mpAct.setAppId("wx****");
        mpAct.setAppSecert("***");
        mpAct.setToken("***");
        mpAct.setAESKey("******");
        this.setMpAct(mpAct);
        // 可实现自己的WxHandler
        this.setWxHandler(new WxDefaultHandler());
    }

    @RequestMapping(value = "/core",produces = {"text/plain;charset=UTF-8"})
    @ResponseBody
    public String wxCore(HttpServletRequest req) {
        String reply = "";
        try {
            reply = wxInteract(req);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return reply;
    }
}
```

<a name="Struts2环境"></a>
##### 4.3.Struts2环境:
```java
public class WeiXinAction extends WxStruts2Support {

    @Override
    protected void init() {
        super.init();
        MPAct mpAct = new MPAct();
        // 修改为实际的公众号信息,可以在开发者栏目中查看
        mpAct.setAppId("wx****");
        mpAct.setAppSecert("***");
        mpAct.setToken("***");
        mpAct.setAESKey("******");
        this.setMpAct(mpAct);
        // 可实现自己的WxHandler
        this.setWxHandler(new WxDefaultHandler());
    }
	
	public void wxCore() throws IOException {
        wxInteract();
    }
}
```

<a name="Issue"></a>
##### 5.Issue
BUG提交：<https://git.oschina.net/lisenhui/mpsdk4j/issues>

<a name="联系"></a>
##### 6.联系
特别希望看到该项目对您哪怕一点点的帮助。你有任何的想法和建议，除以上Issue提交外，也随时欢迎与我沟通，联系方式：

*  Email: elkan1788@gmail.com
*  QQ: 2292706174
*  微信：

<a name="其它"></a>
##### 7.其它
目前正在尝试微信开放平台探究，已初步实现授权管理功能，后续会不断完善成一个管理平台，期待你的加入。

!["MPSDK4J公众号开发服务"](http://j2ee.u.qiniudn.com/weixn-open-demo.png-alias "MPSDK4J公众号开发服务")
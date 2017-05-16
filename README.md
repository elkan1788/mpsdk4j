# MPSDK4J v2

[![Join the chat at https://gitter.im/elkan1788/mpsdk4j](https://badges.gitter.im/elkan1788/mpsdk4j.svg)](https://gitter.im/elkan1788/mpsdk4j?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/elkan1788/mpsdk4j.svg?branch=master)](https://travis-ci.org/elkan1788/mpsdk4j)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.elkan1788/mpsdk4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.elkan1788/mpsdk4j)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

!["MPSDK4J"](http://j2ee.u.qiniudn.com/mpsdk4j-logo.png-aliassmall "MPSDK4J")

## 目录
*  [1.引言](#引言)
*  [2.介绍](#介绍)
  * [2.1.结构设计](#结构设计)
  * [2.2.交互时序](#时序图)
  * [2.3.测试覆盖](#测试覆盖)
*  [3.项目](#项目)
  * [3.1.最新源码](#最新源码)
  * [3.2.Maven库引用](#Maven库引用)
*  [4.示例代码](#示例代码)
  * [4.1.HttpServlet环境](#HttpServlet环境)  
  * [4.2.SpringMVC环境](#SpringMVC环境)
  * [4.3.Nutz环境](#Nutz环境)
  * [4.4.示例代码](#示例)
*  [5.Issue](#Issue)
*  [6.联系](#联系)
*  [7.其它](#其它)

<a name="引言"></a>
## 1.引言

"十一"黄金周假期已悄然逝去,_MPSDK4J_在不知不觉中也经历过一个年头啦,非常感谢这段时间里网友们的支持.有了小伙伴们的支持_MPSDK4J_成长更加的迅速,当前版本也确实存在问题诸多的问题.如: 交互中单,多例问题,多公众号混淆问题,微信公众号API更新等等问题.所以趁最近空闲时间把_MPSDK4J_重构,需要注意**此次升级是全新的改造不往下兼容**.新版本的_MPSDK4J_结构上更加清晰,使用同样的简单,依然不忘追求速度与简单的初心.

<a name="介绍"></a>
## 2.介绍

_MPSDK4J_,非常直观的阐述了此项目的意义所在.没错,它就是JAVA语言环境下的微信公众平台开发SDK.其中MP代表的是微信公众平台的域名前缀,SDK表示开发工具包,4同音英文"for",J代表了JAVA.虽然现网络上已经有不少JAVA版本的SDK现身,但是[_MPSDK4J_]的出现也并非只是造轮子的重复工作.它遵循单一设计模式规则,所有的设计与功能都是源于微信公众平台API,一切都是为了追求简单与速度.

>**a.设计简单**：整体设计非常的简单,仅有9个大包11个接口,3个枚举,55类,1个配置文件(其中API交互实体对象占据一半之多),核心功能部分就4类(WechatKeneral,MessageHandler,WechatHandler,WechatAPI);

>**b.解析速度**：基本SAX驱动式XML处理,能够快速的解析收到用户发送的微信消息,放弃JAVA反射功能,采用直接编码生成VO对象,加快解释速度;

>**c.敏捷开发**：微信交互信息全都统一封装VO对象,所有VO的属性都是微信公众平台API原生状态.但开发者无须再关心它来源是XML还JSON格式,只需掌握其中消息的收发对应VO对象即可;

>**d.支持力度**：API功能分类与微信公众平台保持一致,分类信息详见下表(后续会不断更新升级),微信基本消息的交互,高级接口(Token,自定义菜单,模板消息,群发消息等等).

| API名称 | 描述 |
| ------ | ------ |
| CredentialAPI | 微信服务器IP列表,access_token,jssdk_ticket,短链接生成 |
| GroupsAPI | 用户分组接口: 创建,查询,移动用户等 |
| MediaAPI | 多媒体文件接口: 上传多媒体素材(临时/永久),下载,删除 |
| MenuAPI | 自定义菜单接口: 查询,创建,删除 |
| MessageAPI | 高级消息接口: 发送模板消息,客服消息,群发消息 |
| QRCodeAPI | 二维码接口: 创建,获取 | 
| UserAPI | 用户管理接口: 用户信息,订阅列表等 |
| WechatAPIImpl | 上述接口统一实现 |

<a name="结构设计"></a>
### 2.1.整体结构设计
!["MPSDK4J-V2"](http://j2ee.u.qiniudn.com/mpsdk4j-2.png-alias "MPSDK4J-V2")

<a name="时序图"></a>
### 2.2.交互时序图
!["MPSDK4J-seq"](http://j2ee.u.qiniudn.com/mpsdk4j-v2-seq.png-alias "MPSDK4J-seq")

<a name="测试覆盖"></a>
### 2.3.测试覆盖
!["MPSDK4J-test"](http://j2ee.u.qiniudn.com/mpsdk4j-v2-test-coverage.png-alias "MPSDK4J-test")

<a name="项目"></a>
## 3.项目

<a name="最新源码"></a>
### 3.1最新源码
* OSChina项目主页: <https://git.oschina.net/lisenhui/mpsdk4j>
* Github项目主页: <https://github.com/elkan1788/mpsdk4j>
* 开源协议：[Apache Licenses 2.0](http://www.apache.org/licenses/LICENSE-2.0)

<a name="Maven库引用"></a>
### 3.2Maven库引用

```xml

<dependency>
    <groupId>io.github.elkan1788</groupId>
    <artifactId>mpsdk4j</artifactId>
    <version>2.b.1</version>
</dependency>

```

或者自己编译jar包.

```

mvn clean package

```

<a name="示例代码"></a>
## 4.示例代码

_MPSDK4J_在`Web`环境中暂时提供了以下环境支持,欢迎提交其它环境扩展.在实际的使用过程中只需要继承相应环境的`WechatWebSupport`父类,重写`init`初始化方法修改其中的公众号信息及微信消息处理器(实现`WechatHandler`接口或是继承`WechatDefHandler`基类),添加环境的入口（`Servlet`环境无需此步骤）,调用`interact`方法,最后发布上线即可.

<a name="HttpServlet环境"></a>
### 4.1.HttpServlet环境：

```java

@WebServlet(name = "wechatCoreServlet", urlPatterns = "/servlet/wechatcore.ser")
public class WechatCoreServlet extends HttpServletSupport {

    private static final long serialVersionUID = 4370883777170946295L;

    private static final Logger log = LoggerFactory.getLogger(WechatCoreServlet.class);

    private static final ConfigReader _cr = new ConfigReader("/mp.properties");

    @Override
    public void init() throws ServletException {
        log.info("====== Servlet环境 =======");
        MPAccount mpact = new MPAccount();
         // 修改为实际的公众号信息,可以在开发者栏目中查看
        mpAct.setAppId("wx****");
        mpAct.setAppSecert("***");
        mpAct.setToken("***");
        mpAct.setAESKey("******");
        _wk.setMpAct(mpact);        
        _wk.setWechatHandler(new WechatDefHandler());
    }

}

```

<a name="SpringMVC环境"></a>
### 4.2.SpringMVC环境：

```java

/**
 * SpringMVC环境接入
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@RequestMapping("/springmvc")
@Controller
public class WechatCoreController extends WechatWebSupport {

    private static final Logger log = LoggerFactory.getLogger(WechatCoreController.class);

    private static final ConfigReader _cr = new ConfigReader("/mp.properties");

    @Override
    public void init() {
        log.info("====== SpringMVC环境 =======");
        MPAccount mpact = new MPAccount();
        // 修改为实际的公众号信息,可以在开发者栏目中查看
        mpAct.setAppId("wx****");
        mpAct.setAppSecert("***");
        mpAct.setToken("***");
        mpAct.setAESKey("******");
        _wk.setMpAct(mpact);
        _wk.setWechatHandler(new WechatDefHandler());
    }

    @RequestMapping("/wechatcore")
    public void wechatCore(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        this.interact(req, resp);
    }

}

```

<a name="Nutz环境"></a>
### 4.3.Nutz环境：

```java

/**
 * Nutz环境接入
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
@At("/nutz")
@IocBean
public class WechatCoreModule extends WechatWebSupport {

    private static final Log log = Logs.get();

    private static final ConfigReader _cr = new ConfigReader("/mp.properties");

    @Override
    public void init() {
        log.info("====== Nutz环境 =======");
       // 修改为实际的公众号信息,可以在开发者栏目中查看
        mpAct.setAppId("wx****");
        mpAct.setAppSecert("***");
        mpAct.setToken("***");
        mpAct.setAESKey("******");
        _wk.setMpAct(mpact);
        _wk.setWechatHandler(new WechatDefHandler());
    }

    @At("/wechatcore")
    public void wechatCore(HttpServletRequest req, HttpServletResponse resp) {
        try {
            this.interact(req, resp);
        }
        catch (IOException e) {
            throw Lang.wrapThrow(e);
        }
    }
}

```

<a name="示例"></a>
### 4.4.示例项目源码

* OSChina仓库:  <https://git.oschina.net/lisenhui/mpsdk4j-demo>

<a name="Issue"></a>
## 5.Issue
BUG提交：<https://git.oschina.net/lisenhui/mpsdk4j/issues>

<a name="联系"></a>
## 6.联系
特别希望看到该项目对您哪怕一点点的帮助。你有任何的想法和建议，除以上Issue提交外，也随时欢迎与我沟通，联系方式：

*  Email: elkan1788@gmail.com
*  QQ群：486192816

!["MPSDK4J-qq"](http://j2ee.u.qiniudn.com/MPSDK4J-qq.png-noalias "MPSDK4J-qq")

<a name="其它"></a>
## 7.其它: JCE 使用说明

| 文件名称 | JDK版本 |
| ------ | ------ |
| jce_policy-6.zip | 1.6 |
| UnlimitedJCEPolicyJDK7.zip | 1.7 |
| jce_policy-8.zip | 1.8 |

选择相应的`J2EE`版本下载后解压,可以看到`local_policy.jar`和`US_export_policy.jar`以及`readme.txt`
* 如果安装了`JRE`,将两个`jar`文件放到`%JRE_HOME%\lib\security`目录下覆盖原来的文件;
* 如果安装了`JDK`,将两个`jar`文件放到`%JDK_HOME%\jre\lib\security`目录下覆盖原来文件.
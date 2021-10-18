　　﻿作者：qlslylq (浅蓝深蓝叶落秋，https://www.bmob.cn/shop/detail/396)

　　联系：13297089301 QQ：2939143482

　　QQ讨论群：326550832(AndroidDesign)

　　名下开源框架：

　　1.AndroidDesign(eclipse，in 2014)

　　2.AndroidDesign(AndroidStudio，in 2016)

　　3.AdmxSDK及AdmxSDK_v2(AndroidStudio，in 2017)

　　4.UnityDesign(VisualStudio,C#，in 2017)

　　5.FlutterDesign(AndroidStudio，in 2019)

　　6.JavaDesign(Intellij IDEA，in 2020)：[输入链接说明](https://gitee.com/qlslylq/JavaDesign)

　　名下待开源项目：

　　1.AndroidDesignQtfy (https://www.bmob.cn/shop/detail/396)

　　2.UnityDesignEasyAR (AR纹理识别demo)

　　3.UnityDesignThreeInOne (三合一游戏demo)


﻿一.AndroidDesign框架特点：

自动化高，层级分明；窗口职责化，查错效率高；各级结构标准化，混乱代码易优化；勿须重构智能化，各司其职模块化。

二.AndroidDesign框架包含的主要模块

1.中转站(网络+数据库+其它)
  
  框架核心模块，重要级别为最高。外异内同方式搭建。自动输出网络信息[url,param,status]到手机日志，点击url可查看原Json数据，点击Json中的url[如图片链接]即可查看原图。错误自动定位[错误信息自动定位到异常窗口-->异常网络method并输出]。

2.标题栏函数化

  使用静态布局[仅编辑内容栏]+动态布局[核心]方式搭建。框架自动注入标题栏与进度栏，免include引用。

3.中转站进度条自动化

  中转站自动处理进度[网络进度，数据库进度，耗时进度]的显示与隐藏。也可手动控制，适合在网络子层首句重置进度文本。

  eg.[**Service:query**:functionView.setProgressBar("正在查询圈子")]。

4.内容栏include公用简化

  常用的AdapterView布局，刷新布局，空间布局，分界线布局，搜索布局，组合布局等均公用化，并以common_***.xml规范命名。

5.ids文件公用化常用id

  重用需要自动化控制的id,常用的id。包含框架所需的id。

6.值(尺寸，颜色)级别公用化

  颜色值与尺寸值统一到独立文件中修改。具体布局中不出现具体的颜色值，尺寸值。设计图初始分析各值，统一修改，清晰呈现资源。

7.事件处理逐层化

  中转站事件：聚合分发式。先统一聚合到中转站按照不同状态预处理，之后分发到子窗口Activity或子碎片窗口Fragment中。按需实现。不实现时由(碎片)窗口上层默认处理(Toast显示数据或定位错误)。

  窗口事件：由窗口自动传递到functionView中。选择性分发到functionView,adapter,listener中职责化处理，最大程度减少窗口冗余。

8.上下拉刷新分页

  框架集成了开源的PullToRefresh源码。修缮了分页显示问题。刷新头尾智能化提示页数信息。增加了左滑删除控件的刷新分页。增加了获取两种adapter的函数[分别用于通知刷新数据与获取item数据]。

9.异步图片加载

  使用免配置的Picasso库。公用工具包ImageUtils类中封装了使用Picasso加载矩形，圆角，圆形等图片的重载函数。工具包中也包含了TimeFormatUtils[时间友好化工具]等等种种实用的工具。

10.对象关系映射

  框架使用ormlite对象关系映射框架。分别用dao包，daoimpl包，db包，table包来深化sqlite的层级结构。并包含示范写法。basetable包一般装入需要与后台同步数据的表实体。

11.登录过程安全认证

  当使用session+token方式开发登录模块时，需配合MemberService,UserMethod与BaseService中的数据失效处理协作开发。

12.错误处理

  使用后台发送邮件方式通知错误。handler包CrashHandler类中发送邮件函数中，可以配置多个接收者邮箱。日志管理中可以配置邮箱并发送完整日志。  

13.json解析

  使用FastJson解析接口数据。引用的FastJson库是修缮过源码的库。主要是当解析时在FastJson层将null容器转为了非null的0大小容器。避免解析后容器null判断的烦扰操作，以应对C#作后台时将Json数据转为字符串时不方便根据数据类型进行配置的问题。

14.adapter处理逐层化

  上层adapter充分分担子adapter共有的处理。使子adapter仅需重写getView函数。并进一步将ViewHolder机制简化，减少适配层冗余。

15.界面配置

  在不修改代码的前提下，支持在软件内部通过系统菜单键进入基本设置页面中重新配置界面。配置对应constant包BasicSettingConstant类。配置包括：是否显示左，右侧滑菜单；是否显示顶部，底部导航栏；是否显示通知栏，系统标题栏；屏幕方向是否固定为竖直等等。

16.调试模式配置

  软件中间出现的蜜蜂图标为调试所用，调试模式下可见，为可滑动的悬浮窗，包含日志，内存，进程三大调试模块。在公用工具包Log类中统一配置应用程序的调试/上线模式，手机/控制台模式。Log类使用开源Log4j库将日志持久化输出到本地文件，并在手机中即时显示。各种状态使用不同的颜色。如日志中出现了红色段落则代表程序出现了异常。此时，测试人员或其他持有终端的人员可以将完整日志发送到任意指定的邮箱。进程列表通过listitem上下文菜单可进入进程详情。

  除了可见的蜜蜂调试外，另包括默认不可见的控件调试模块。控件调试暂兼容TextView，Button与EditText，需要使用commonview中对应的自定义widget控件。通过长按某个控件弹出控件调试窗，可查看和实时改变当前的尺寸和颜色，以方便设计人员检查，修改和实时查看效果。后期会细化此调试功能，如手机端建立自己的颜色，尺寸库；修改之后保存本地并将要改的控件尺寸颜色和改变成的尺寸颜色发送给开发者修改等等。

17.艺术字及语音模块配置

  框架自带显示艺术字及自动播放语音的功能。如点击文字时，显示Toast时可自动播放对应文字，需要网络支持和调高音量。数据库管理类中可以调整默认的文字字体显示和语音是否自动播放等功能。公用子窗口ToolColorActivity及ToolSpeechActivity中实现了对文字及语音的配置，显示该窗口即可动态调整。

18.AndroidDesign交流群

  欢迎加QQ群：326550832进行交流

19.支付宝服务窗

  服务窗名称：AndroidDesign

20.微信小程序开发交流群

  欢迎加QQ群：155295967进行交流

21.觉得框架对自己有帮助的朋友，可以使用支付宝扫一扫下面的二维码以捐赠项目组

  ![支持项目的朋友，请使用支付宝扫一扫以捐赠项目组](https://images.gitee.com/uploads/images/2019/0331/144159_cad622fb_452019.png "捐赠二维码")

22.使用AndroidDesign框架开发的且听风吟App

  官网下载地址   ： http://qlslylq.bmob.site

  应用宝下载地址 ： http://sj.qq.com/myapp/detail.htm?apkName=com.qlsl.androiddesign.freeapp

三.现放出手机查看即时请求接口数据的截图(使用本AndroidDesign框架支持的功能)，商店项目截图，及框架自带调试模块截图(相关框架更新可能未提交到本仓库，需要的朋友可以联系我私要；另外，框架调试模块(蜜蜂图标标志)未集成到AndroidStudio版的AndroidDesign中，有需要的朋友将相关代码复制过去即可)

![日志(接口链接可点击查看其数据，由框架自动处理)](https://gitee.com/uploads/images/2019/0405/181028_b6f24567_452019.png "Screenshot_2019-04-05-17-35-41-717_com.qlsl.andro.png")

![接口数据实时查看](https://gitee.com/uploads/images/2019/0405/181157_abb872a9_452019.png "Screenshot_2019-04-05-17-36-06-388_com.qlsl.andro.png")

![接口数据实时查看](https://gitee.com/uploads/images/2019/0405/182617_70a08f0a_452019.png "Screenshot_2019-04-05-17-43-06-623_com.qlsl.andro.png")

![调试模块(日志，内存，进程及控件实时调试)](https://gitee.com/uploads/images/2019/0405/181343_dc67b233_452019.png "Screenshot_2019-04-05-17-39-03-765_com.qlsl.andro.png")

![内存调试](https://gitee.com/uploads/images/2019/0405/181750_e719a1c9_452019.png "Screenshot_2019-04-05-17-39-21-634_com.qlsl.andro.png")

![进程调试](https://gitee.com/uploads/images/2019/0405/181810_87b3af55_452019.png "Screenshot_2019-04-05-17-39-58-698_com.qlsl.andro.png")

![进程排序](https://gitee.com/uploads/images/2019/0405/181604_39679ea1_452019.png "Screenshot_2019-04-05-17-40-05-662_com.qlsl.andro.png")

![控件调试](https://gitee.com/uploads/images/2019/0405/181659_8d9fc3f4_452019.png "Screenshot_2019-04-05-18-01-20-295_com.qlsl.andro.png")

![控件调试](https://gitee.com/uploads/images/2019/0405/181844_b7022e19_452019.png "Screenshot_2019-04-05-18-01-44-093_com.qlsl.andro.png")

![控件调试](https://gitee.com/uploads/images/2019/0405/181903_aad1d528_452019.png "Screenshot_2019-04-05-18-02-10-324_com.qlsl.andro.png")

![控件调试](https://gitee.com/uploads/images/2019/0405/181921_fe5a66d6_452019.png "Screenshot_2019-04-05-18-02-16-300_com.qlsl.andro.png")

![日志调试](https://gitee.com/uploads/images/2019/0405/182016_68769664_452019.png "Screenshot_2019-04-05-18-00-13-687_com.qlsl.andro.png")

![日志调试](https://gitee.com/uploads/images/2019/0405/182039_d2672deb_452019.png "Screenshot_2019-04-05-17-59-39-319_com.qlsl.andro.png")

![日志调试](https://gitee.com/uploads/images/2019/0405/182259_fc783cb1_452019.png "Screenshot_2019-04-05-17-58-47-716_com.qlsl.andro.png")

![日志调试](https://gitee.com/uploads/images/2019/0405/182321_648eecb5_452019.png "Screenshot_2019-04-05-17-58-57-950_com.qlsl.andro.png")

![日志调试](https://gitee.com/uploads/images/2019/0405/182358_e7447430_452019.png "Screenshot_2019-04-05-17-59-06-752_com.qlsl.andro.png")

![日志调试](https://gitee.com/uploads/images/2019/0405/182425_0c99a54e_452019.png "Screenshot_2019-04-05-17-59-18-647_com.qlsl.andro.png")

![关联项目-快挖(酷挖)](https://gitee.com/uploads/images/2019/0405/182706_d69ee07a_452019.png "Screenshot_2019-04-05-17-41-13-025_com.qlsl.andro.png")

![关联项目-快挖(酷挖)](https://gitee.com/uploads/images/2019/0405/182815_7fa21e0b_452019.png "Screenshot_2019-04-05-17-41-32-798_com.qlsl.andro.png")

![关联项目-快挖(酷挖)](https://gitee.com/uploads/images/2019/0405/182900_959ae19d_452019.png "Screenshot_2019-04-05-17-41-45-527_com.qlsl.andro.png")

![关联项目-快挖(酷挖)](https://gitee.com/uploads/images/2019/0405/183049_49a4d517_452019.png "Screenshot_2019-04-05-17-41-49-832_com.qlsl.andro.png")

![关联项目-快挖(酷挖)](https://gitee.com/uploads/images/2019/0405/183127_bea339e1_452019.png "Screenshot_2019-04-05-17-41-56-314_com.qlsl.andro.png")

![关联项目-且听风吟(已开源)](https://gitee.com/uploads/images/2019/0405/183311_153dd04b_452019.png "1.png")

![关联项目-且听风吟(已开源)](https://gitee.com/uploads/images/2019/0405/183407_46118872_452019.png "2.png")

![关联项目-且听风吟(已开源)](https://gitee.com/uploads/images/2019/0405/183421_1b031902_452019.png "3.png")

![关联项目-且听风吟(已开源)](https://images.gitee.com/uploads/images/2019/0331/152951_ba720917_452019.png "4.png")

![关联项目-且听风吟(已开源)](https://images.gitee.com/uploads/images/2019/0331/153004_84ddb690_452019.png "5.png")

![关联项目-且听风吟(已开源)](https://images.gitee.com/uploads/images/2019/0331/153016_0e1e2d01_452019.png "6.png")
  
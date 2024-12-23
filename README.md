## kivihub-blog#一个私人博客仓库

>  非学无以广才，非志无以成学。     —— 诫子书

#### GIT钩子配置
使用pre-commit钩子，每次提交前会自动更新README.md
```shell
cp hooks/pre-commit .git/hooks/
```
#### 仓库目录

| 目录                          | 分类           | 介绍                                        |
| ----------------------------- | -------------- | ------------------------------------------- |
| */shared*                     | **总结分享**   | 汇总性文章，对某个知识的全局性总结          |
| */blog*                       | **博客目录**   | 较零散知识，偏某个知识细节                  |
| */reference*                  | **引用目录**   | 博客中引用的文章存放目录                    |
| */src/main/resources/picture* | **图片目录**   | 文章引用图片存放目录                        |
| */src/main/java/repo/tools*   | **仓库工具**   | 自动生成README.md                           |
| */src/main/resources/readme*  | **README片段** | 用于生成README.md的片段，如head.md，tail.md |
---
#### 总结分享(1篇/3,279字)
- [Git分享 (3,279字)](shared/Git分享.md)

---
#### 博客目录(264篇/226,138字)
- [1.TroubleShooting (43篇)](blog/1.TroubleShooting)
    - [0.TroubleShooting方法论 (1,229字)](blog/1.TroubleShooting/0.TroubleShooting方法论.md)
    - [1.观察日志 (16篇)](blog/1.TroubleShooting/1.观察日志)
        - [1.为什么我的EL在测试和线上环境结果不同 (390字)](blog/1.TroubleShooting/1.观察日志/1.为什么我的EL在测试和线上环境结果不同.md)
        - [2.fastjson升级不兼容的问题 (328字)](blog/1.TroubleShooting/1.观察日志/2.fastjson升级不兼容的问题.md)
        - [3.这不是我想要的日志 (350字)](blog/1.TroubleShooting/1.观察日志/3.这不是我想要的日志.md)
        - [4.模糊的异常信息让人蒙圈 (229字)](blog/1.TroubleShooting/1.观察日志/4.模糊的异常信息让人蒙圈.md)
        - [5.刚发现的虫子原来很早之前就有了 (405字)](blog/1.TroubleShooting/1.观察日志/5.刚发现的虫子原来很早之前就有了.md)
        - [6.发现冲突，解决冲突 (207字)](blog/1.TroubleShooting/1.观察日志/6.发现冲突，解决冲突.md)
        - [7.小心JS中数字精度损失 (252字)](blog/1.TroubleShooting/1.观察日志/7.小心JS中数字精度损失.md)
        - [8.STFP报错Connection_reset (249字)](blog/1.TroubleShooting/1.观察日志/8.STFP报错Connection_reset.md)
        - [9.返回值里藏着异常信息 (319字)](blog/1.TroubleShooting/1.观察日志/9.返回值里藏着异常信息.md)
        - [10.看堆栈，勿急躁 (537字)](blog/1.TroubleShooting/1.观察日志/10.看堆栈，勿急躁.md)
        - [11.归约问题定位思路 (261字)](blog/1.TroubleShooting/1.观察日志/11.归约问题定位思路.md)
        - [12.CA认证的证书为什么还需要手动导入？ (855字)](blog/1.TroubleShooting/1.观察日志/12.CA认证的证书为什么还需要手动导入？.md)
        - [13.Http上传文件的Content-Type格式 (332字)](blog/1.TroubleShooting/1.观察日志/13.Http上传文件的Content-Type格式.md)
        - [14.nginx反向代理自调用时小心Header里的host (726字)](blog/1.TroubleShooting/1.观察日志/14.nginx反向代理自调用时小心Header里的host.md)
        - [15.Webmethod的IS加入Cluster异常 (1,112字)](blog/1.TroubleShooting/1.观察日志/15.Webmethod的IS加入Cluster异常.md)
        - [16.proxool的log4j冲突 (630字)](blog/1.TroubleShooting/1.观察日志/16.proxool的log4j冲突.md)
    - [2.Debug (5篇)](blog/1.TroubleShooting/2.Debug)
        - [1.el中函数调用结果始终为null (270字)](blog/1.TroubleShooting/2.Debug/1.el中函数调用结果始终为null.md)
        - [2.Debug时科学观察变量 (201字)](blog/1.TroubleShooting/2.Debug/2.Debug时科学观察变量.md)
        - [3.Debug时加的观察变量影响了debug (751字)](blog/1.TroubleShooting/2.Debug/3.Debug时加的观察变量影响了debug.md)
        - [4.ClassNotFound与类加载的全盘委托机制 (218字)](blog/1.TroubleShooting/2.Debug/4.ClassNotFound与类加载的全盘委托机制.md)
        - [5.日志先行，Debug其次 (265字)](blog/1.TroubleShooting/2.Debug/5.日志先行，Debug其次.md)
    - [3.思维定势 (1篇)](blog/1.TroubleShooting/3.思维定势)
        - [1.对Http响应码“302”的误判 (337字)](blog/1.TroubleShooting/3.思维定势/1.对Http响应码“302”的误判.md)
    - [4.流程分析 (17篇)](blog/1.TroubleShooting/4.流程分析)
        - [1.原来是IDE和Rest版本不同步 (326字)](blog/1.TroubleShooting/4.流程分析/1.原来是IDE和Rest版本不同步.md)
        - [2.FTP和sshuttle的故事 (309字)](blog/1.TroubleShooting/4.流程分析/2.FTP和sshuttle的故事.md)
        - [3.方法返回集合的副本，避免污染 (788字)](blog/1.TroubleShooting/4.流程分析/3.方法返回集合的副本，避免污染.md)
        - [4.签名验证异常 (435字)](blog/1.TroubleShooting/4.流程分析/4.签名验证异常.md)
        - [5.HttpClient未设置connectTimeout导致线程池耗尽 (456字)](blog/1.TroubleShooting/4.流程分析/5.HttpClient未设置connectTimeout导致线程池耗尽.md)
        - [6.用tcpdump、wireShark分析sftp连接时readtimeout (693字)](blog/1.TroubleShooting/4.流程分析/6.用tcpdump、wireShark分析sftp连接时readtimeout.md)
        - [7.MQ积压2亿了 (656字)](blog/1.TroubleShooting/4.流程分析/7.MQ积压2亿了.md)
        - [8.MQ消费出现陡增和断崖 (386字)](blog/1.TroubleShooting/4.流程分析/8.MQ消费出现陡增和断崖.md)
        - [9.抓包分析http请求超时 (874字)](blog/1.TroubleShooting/4.流程分析/9.抓包分析http请求超时.md)
        - [10.CPU飚高排查 (1,830字)](blog/1.TroubleShooting/4.流程分析/10.CPU飚高排查.md)
        - [11.commons-io依赖冲突 (910字)](blog/1.TroubleShooting/4.流程分析/11.commons-io依赖冲突.md)
        - [12.wsdl获取逻辑修改事故的复盘 (949字)](blog/1.TroubleShooting/4.流程分析/12.wsdl获取逻辑修改事故的复盘.md)
        - [13.Maven间接依赖未下载 (384字)](blog/1.TroubleShooting/4.流程分析/13.Maven间接依赖未下载.md)
        - [14.内存飚高排查（一） (717字)](blog/1.TroubleShooting/4.流程分析/14.内存飚高排查（一）.md)
        - [15.内存飚高排查（二） (389字)](blog/1.TroubleShooting/4.流程分析/15.内存飚高排查（二）.md)
        - [16.内存飚高排查（三） (768字)](blog/1.TroubleShooting/4.流程分析/16.内存飚高排查（三）.md)
        - [17.内存飚高排查（四） (214字)](blog/1.TroubleShooting/4.流程分析/17.内存飚高排查（四）.md)
    - [5.单元测试 (3篇)](blog/1.TroubleShooting/5.单元测试)
        - [1.单元测试才是绩效的保命符 (425字)](blog/1.TroubleShooting/5.单元测试/1.单元测试才是绩效的保命符.md)
        - [2.numVar=2，但${numVar除1000}为什么是0？ (321字)](blog/1.TroubleShooting/5.单元测试/2.numVar=2，但${numVar除1000}为什么是0？.md)
        - [3.因浮点精度损失了1分钱 (2,544字)](blog/1.TroubleShooting/5.单元测试/3.因浮点精度损失了1分钱.md)
- [2.Golang (4篇)](blog/2.Golang)
    - [0.环境准备 (24字)](blog/2.Golang/0.环境准备.md)
    - [1.go包和模块 (389字)](blog/2.Golang/1.go包和模块.md)
    - [2.共享变量 (646字)](blog/2.Golang/2.共享变量.md)
    - [3.依赖树查看 (117字)](blog/2.Golang/3.依赖树查看.md)
- [3.Java (89篇)](blog/3.Java)
    - [1.JavaSE (16篇)](blog/3.Java/1.JavaSE)
        - [1.包冲突 (1,291字)](blog/3.Java/1.JavaSE/1.包冲突.md)
        - [2.String (270字)](blog/3.Java/1.JavaSE/2.String.md)
        - [3.Map (1,014字)](blog/3.Java/1.JavaSE/3.Map.md)
        - [4.断言 (59字)](blog/3.Java/1.JavaSE/4.断言.md)
        - [5.工厂类中使用ThreadLocal的陷阱 (500字)](blog/3.Java/1.JavaSE/5.工厂类中使用ThreadLocal的陷阱.md)
        - [6.误用BlockingQueue方法导致日志丢失 (556字)](blog/3.Java/1.JavaSE/6.误用BlockingQueue方法导致日志丢失.md)
        - [7.SLF4J日志框架 (1,346字)](blog/3.Java/1.JavaSE/7.SLF4J日志框架.md)
        - [8.System.currentTimeMillis()与GMT (256字)](blog/3.Java/1.JavaSE/8.System.currentTimeMillis()与GMT.md)
        - [9.StringEscapeUtils (81字)](blog/3.Java/1.JavaSE/9.StringEscapeUtils.md)
        - [10.文件正确写入bom (577字)](blog/3.Java/1.JavaSE/10.文件正确写入bom.md)
        - [11.echo无法清空日志 (779字)](blog/3.Java/1.JavaSE/11.echo无法清空日志.md)
        - [12.Gson如何实例化类 (397字)](blog/3.Java/1.JavaSE/12.Gson如何实例化类.md)
        - [13.Java异常堆栈丢失仅剩一行 (661字)](blog/3.Java/1.JavaSE/13.Java异常堆栈丢失仅剩一行.md)
        - [14.SFTP和FTPS，HTTPS (207字)](blog/3.Java/1.JavaSE/14.SFTP和FTPS，HTTPS.md)
        - [15.使用hsdb查看运行时类 (185字)](blog/3.Java/1.JavaSE/15.使用hsdb查看运行时类.md)
        - [16.ServiceLoader (548字)](blog/3.Java/1.JavaSE/16.ServiceLoader.md)
    - [2.Java并发 (9篇)](blog/3.Java/2.Java并发)
        - [1.JMM (2,129字)](blog/3.Java/2.Java并发/1.JMM.md)
        - [2.CPU内存模型和LOCK指令 (2,288字)](blog/3.Java/2.Java并发/2.CPU内存模型和LOCK指令.md)
        - [3.volatile (1,095字)](blog/3.Java/2.Java并发/3.volatile.md)
        - [4.synchronized (1,521字)](blog/3.Java/2.Java并发/4.synchronized.md)
        - [5.AQS及其实现 (4,056字)](blog/3.Java/2.Java并发/5.AQS及其实现.md)
        - [6.线程池剖析 (4,145字)](blog/3.Java/2.Java并发/6.线程池剖析.md)
        - [7.ThreadLocal内存泄漏 (193字)](blog/3.Java/2.Java并发/7.ThreadLocal内存泄漏.md)
        - [8.线程同步 (265字)](blog/3.Java/2.Java并发/8.线程同步.md)
        - [9.FutureTask构建高效缓存 (177字)](blog/3.Java/2.Java并发/9.FutureTask构建高效缓存.md)
    - [3.Java虚拟机 (7篇)](blog/3.Java/3.Java虚拟机)
        - [1.类加载机制 (2,307字)](blog/3.Java/3.Java虚拟机/1.类加载机制.md)
        - [2.Java运行时内存区域 (621字)](blog/3.Java/3.Java虚拟机/2.Java运行时内存区域.md)
        - [3.垃圾回收 (1,472字)](blog/3.Java/3.Java虚拟机/3.垃圾回收.md)
        - [4.垃圾回收器 (2,402字)](blog/3.Java/3.Java虚拟机/4.垃圾回收器.md)
        - [5.JDK自带的性能监控工具 (305字)](blog/3.Java/3.Java虚拟机/5.JDK自带的性能监控工具.md)
        - [6.字节码解释器和JIT (226字)](blog/3.Java/3.Java虚拟机/6.字节码解释器和JIT.md)
        - [7.JVM运行参数 (233字)](blog/3.Java/3.Java虚拟机/7.JVM运行参数.md)
    - [4.Spring (12篇)](blog/3.Java/4.Spring)
        - [0.SpringIOC (236字)](blog/3.Java/4.Spring/0.SpringIOC.md)
        - [1.Spring循环依赖 (853字)](blog/3.Java/4.Spring/1.Spring循环依赖.md)
        - [2.SpingAop (340字)](blog/3.Java/4.Spring/2.SpingAop.md)
        - [3.Spring事务 (631字)](blog/3.Java/4.Spring/3.Spring事务.md)
        - [4.SpingAopProxy (722字)](blog/3.Java/4.Spring/4.SpingAopProxy.md)
        - [5.Spring注解Import (164字)](blog/3.Java/4.Spring/5.Spring注解Import.md)
        - [6.Spring集成第三方组件-JSF (1,227字)](blog/3.Java/4.Spring/6.Spring集成第三方组件-JSF.md)
        - [7.Spring集成第三方组件-Mybatis (1,291字)](blog/3.Java/4.Spring/7.Spring集成第三方组件-Mybatis.md)
        - [8.Spring集成示例组件实战 (987字)](blog/3.Java/4.Spring/8.Spring集成示例组件实战.md)
        - [9.Springboot配置文件读取顺序 (566字)](blog/3.Java/4.Spring/9.Springboot配置文件读取顺序.md)
        - [10.扩展接口SmartInitializingSingleton (941字)](blog/3.Java/4.Spring/10.扩展接口SmartInitializingSingleton.md)
        - [11.Springboot自动配置 (1,286字)](blog/3.Java/4.Spring/11.Springboot自动配置.md)
    - [5.Maven (20篇)](blog/3.Java/5.Maven)
        - [1.mvn_clean_deploy出错 (159字)](blog/3.Java/5.Maven/1.mvn_clean_deploy出错.md)
        - [2.mvn_dependency_tree和assembly不一致 (488字)](blog/3.Java/5.Maven/2.mvn_dependency_tree和assembly不一致.md)
        - [3.maven插件的依赖的查找顺序 (244字)](blog/3.Java/5.Maven/3.maven插件的依赖的查找顺序.md)
        - [4.Maven仓库类型 (694字)](blog/3.Java/5.Maven/4.Maven仓库类型.md)
        - [5.Maven仓库更新策略 (556字)](blog/3.Java/5.Maven/5.Maven仓库更新策略.md)
        - [6.DependencyManagment作用 (103字)](blog/3.Java/5.Maven/6.DependencyManagment作用.md)
        - [7.Maven属性替换 (792字)](blog/3.Java/5.Maven/7.Maven属性替换.md)
        - [8.Maven常用命令 (195字)](blog/3.Java/5.Maven/8.Maven常用命令.md)
        - [9.Maven依赖协调原则及依赖顺序的影响 (518字)](blog/3.Java/5.Maven/9.Maven依赖协调原则及依赖顺序的影响.md)
        - [10.打包后-JAR包名为时间戳orSNAPSHOT (463字)](blog/3.Java/5.Maven/10.打包后-JAR包名为时间戳orSNAPSHOT.md)
        - [11.Maven生命周期和插件MOJO (602字)](blog/3.Java/5.Maven/11.Maven生命周期和插件MOJO.md)
        - [12.Maven分类classifier使用 (368字)](blog/3.Java/5.Maven/12.Maven分类classifier使用.md)
        - [13.Maven源码-模块说明 (325字)](blog/3.Java/5.Maven/13.Maven源码-模块说明.md)
        - [14.Maven源码-调试方法 (169字)](blog/3.Java/5.Maven/14.Maven源码-调试方法.md)
        - [15.Maven源码-主流程 (1,053字)](blog/3.Java/5.Maven/15.Maven源码-主流程.md)
        - [16.Maven源码-依赖解析 (2,137字)](blog/3.Java/5.Maven/16.Maven源码-依赖解析.md)
        - [17.Maven并行参数加快编译 (382字)](blog/3.Java/5.Maven/17.Maven并行参数加快编译.md)
        - [18.Maven如何处理循环依赖 (159字)](blog/3.Java/5.Maven/18.Maven如何处理循环依赖.md)
        - [19.Maven查看模块依赖图 (187字)](blog/3.Java/5.Maven/19.Maven查看模块依赖图.md)
        - [20.Maven3.x兼容笔记 (407字)](blog/3.Java/5.Maven/20.Maven3.x兼容笔记.md)
    - [6.Netty (6篇)](blog/3.Java/6.Netty)
        - [1.IO (2,621字)](blog/3.Java/6.Netty/1.IO.md)
        - [2.零拷贝 (1,854字)](blog/3.Java/6.Netty/2.零拷贝.md)
        - [3.Netty聊天室Demo (991字)](blog/3.Java/6.Netty/3.Netty聊天室Demo.md)
        - [4.Netty核心组件源码分析 (262字)](blog/3.Java/6.Netty/4.Netty核心组件源码分析.md)
        - [5.Netty高性能分析 (785字)](blog/3.Java/6.Netty/5.Netty高性能分析.md)
        - [6.Scalable_IO_in_Java (2,516字)](blog/3.Java/6.Netty/6.Scalable_IO_in_Java.md)
    - [7.Dubbo (8篇)](blog/3.Java/7.Dubbo)
        - [1.Dubbo速览 (710字)](blog/3.Java/7.Dubbo/1.Dubbo速览.md)
        - [2.Dubbo扩展点 (1,040字)](blog/3.Java/7.Dubbo/2.Dubbo扩展点.md)
        - [3.Dubbo服务发布源码分析 (1,590字)](blog/3.Java/7.Dubbo/3.Dubbo服务发布源码分析.md)
        - [4.Dubbo服务引入源码分析 (3,061字)](blog/3.Java/7.Dubbo/4.Dubbo服务引入源码分析.md)
        - [5.Dubbo服务调用源码分析 (41字)](blog/3.Java/7.Dubbo/5.Dubbo服务调用源码分析.md)
        - [6.RPC服务线程池大小及集群规模评估 (803字)](blog/3.Java/7.Dubbo/6.RPC服务线程池大小及集群规模评估.md)
        - [7.关于Dubbo的重试机制 (772字)](blog/3.Java/7.Dubbo/7.关于Dubbo的重试机制.md)
        - [8.使用msgpack实体增加字段序列异常 (664字)](blog/3.Java/7.Dubbo/8.使用msgpack实体增加字段序列异常.md)
    - [8.源码 (11篇)](blog/3.Java/8.源码)
        - [0.看源码的姿势 (498字)](blog/3.Java/8.源码/0.看源码的姿势.md)
        - [1.while(true)和for(;;)分析 (178字)](blog/3.Java/8.源码/1.while(true)和for(;;)分析.md)
        - [2.Junit源码分析 (741字)](blog/3.Java/8.源码/2.Junit源码分析.md)
        - [3.URLClassPath源码分析 (919字)](blog/3.Java/8.源码/3.URLClassPath源码分析.md)
        - [4.Tomcat类加载器 (1,875字)](blog/3.Java/8.源码/4.Tomcat类加载器.md)
        - [5.从SocketTimeout，ReadTimeout来看源码 (122字)](blog/3.Java/8.源码/5.从SocketTimeout，ReadTimeout来看源码.md)
        - [6.Camel的Exchange分析 (772字)](blog/3.Java/8.源码/6.Camel的Exchange分析.md)
        - [7.slf4j加载实现 (477字)](blog/3.Java/8.源码/7.slf4j加载实现.md)
        - [8.HttpClient（一）连接池 (678字)](blog/3.Java/8.源码/8.HttpClient（一）连接池.md)
        - [9.HttpClient（二）结构 (450字)](blog/3.Java/8.源码/9.HttpClient（二）结构.md)
        - [10.HttpClient（三）连接池获取连接 (1,280字)](blog/3.Java/8.源码/10.HttpClient（三）连接池获取连接.md)
- [4.Mysql (15篇)](blog/4.Mysql)
    - [1.时区与时间：Mysql，JDBC，JVM (1,247字)](blog/4.Mysql/1.时区与时间：Mysql，JDBC，JVM.md)
    - [2.MySql和B+树 (2,666字)](blog/4.Mysql/2.MySql和B+树.md)
    - [3.Mysql_InnoDB锁 (1,373字)](blog/4.Mysql/3.Mysql_InnoDB锁.md)
    - [4.Mysql事务及其隔离级别 (1,353字)](blog/4.Mysql/4.Mysql事务及其隔离级别.md)
    - [5.MVCC的InnoDB实现 (825字)](blog/4.Mysql/5.MVCC的InnoDB实现.md)
    - [6.explain (1,037字)](blog/4.Mysql/6.explain.md)
    - [7.Mysql优化 (1,233字)](blog/4.Mysql/7.Mysql优化.md)
    - [8.InnoDb死锁分析 (978字)](blog/4.Mysql/8.InnoDb死锁分析.md)
    - [9.MySQL_Workbench常用操作 (72字)](blog/4.Mysql/9.MySQL_Workbench常用操作.md)
    - [10.left_join时on后多条件AND (562字)](blog/4.Mysql/10.left_join时on后多条件AND.md)
    - [11.Mysql索引优化 (641字)](blog/4.Mysql/11.Mysql索引优化.md)
    - [12.Mysql对Null的判断 (127字)](blog/4.Mysql/12.Mysql对Null的判断.md)
    - [13.JDBC的时区调停 (459字)](blog/4.Mysql/13.JDBC的时区调停.md)
    - [14.分库分表 (517字)](blog/4.Mysql/14.分库分表.md)
    - [15.Mysql如何保证数据不丢失 (789字)](blog/4.Mysql/15.Mysql如何保证数据不丢失.md)
- [5.Git (25篇)](blog/5.Git)
    - [0.序言 (150字)](blog/5.Git/0.序言.md)
    - [1.VCS简介 (1,556字)](blog/5.Git/1.VCS简介.md)
    - [2.Git内部原理 (2,361字)](blog/5.Git/2.Git内部原理.md)
    - [3.Git操作清单 (641字)](blog/5.Git/3.Git操作清单.md)
    - [4.Git配置 (1,012字)](blog/5.Git/4.Git配置.md)
    - [5.Git基础操作之正常提交 (1,578字)](blog/5.Git/5.Git基础操作之正常提交.md)
    - [6.Git基础操作之撤销操作 (1,959字)](blog/5.Git/6.Git基础操作之撤销操作.md)
    - [7.Git远程仓库 (230字)](blog/5.Git/7.Git远程仓库.md)
    - [8.Git标签和分支 (3,304字)](blog/5.Git/8.Git标签和分支.md)
    - [9.Git补充内容 (1,432字)](blog/5.Git/9.Git补充内容.md)
    - [10..gitindex文件 (1,752字)](blog/5.Git/10..gitindex文件.md)
    - [11.gitconfig文件详解 (156字)](blog/5.Git/11.gitconfig文件详解.md)
    - [12.查看谁修改了我的文件 (217字)](blog/5.Git/12.查看谁修改了我的文件.md)
    - [13.git_log_--follow (159字)](blog/5.Git/13.git_log_--follow.md)
    - [14.深入git_stash (237字)](blog/5.Git/14.深入git_stash.md)
    - [15.hash碰撞 (169字)](blog/5.Git/15.hash碰撞.md)
    - [16.手动合并分支的技巧 (310字)](blog/5.Git/16.手动合并分支的技巧.md)
    - [17.检出文件夹时注意事项 (247字)](blog/5.Git/17.检出文件夹时注意事项.md)
    - [18.非本地私有分支恢复至某个版本 (320字)](blog/5.Git/18.非本地私有分支恢复至某个版本.md)
    - [19.git工作流 (606字)](blog/5.Git/19.git工作流.md)
    - [20.sparse_checkout (87字)](blog/5.Git/20.sparse_checkout.md)
    - [21.git提交规范 (195字)](blog/5.Git/21.git提交规范.md)
    - [22.常见问题 (1,141字)](blog/5.Git/22.常见问题.md)
    - [23.git分析工具 (56字)](blog/5.Git/23.git分析工具.md)
    - [24.合并两个仓库 (547字)](blog/5.Git/24.合并两个仓库.md)
- [6.Linux (8篇)](blog/6.Linux)
    - [1.CPU使用率和负载 (1,464字)](blog/6.Linux/1.CPU使用率和负载.md)
    - [2.Debian包管理 (997字)](blog/6.Linux/2.Debian包管理.md)
    - [3.同步异步和阻塞非阻塞 (355字)](blog/6.Linux/3.同步异步和阻塞非阻塞.md)
    - [4.User_vs_Kernel (552字)](blog/6.Linux/4.User_vs_Kernel.md)
    - [5.Vim笔记 (1,369字)](blog/6.Linux/5.Vim笔记.md)
    - [6.OOM_Killer (259字)](blog/6.Linux/6.OOM_Killer.md)
    - [7.进程运行信息 (109字)](blog/6.Linux/7.进程运行信息.md)
    - [8.shell脚本编写 (396字)](blog/6.Linux/8.shell脚本编写.md)
- [7.分布式中间件 (19篇)](blog/7.分布式中间件)
    - [1.共识协议 (3篇)](blog/7.分布式中间件/1.共识协议)
        - [1.分布式网络及共识协议 (2,296字)](blog/7.分布式中间件/1.共识协议/1.分布式网络及共识协议.md)
        - [2.常见分布式中间件的共识协议 (3,740字)](blog/7.分布式中间件/1.共识协议/2.常见分布式中间件的共识协议.md)
        - [3.落盘机制 (494字)](blog/7.分布式中间件/1.共识协议/3.落盘机制.md)
    - [2.Elasticsearch (9篇)](blog/7.分布式中间件/2.Elasticsearch)
        - [1.ES索引分片数设置原则 (1,075字)](blog/7.分布式中间件/2.Elasticsearch/1.ES索引分片数设置原则.md)
        - [2.ES倒排索引 (508字)](blog/7.分布式中间件/2.Elasticsearch/2.ES倒排索引.md)
        - [3.ES的乐观锁 (165字)](blog/7.分布式中间件/2.Elasticsearch/3.ES的乐观锁.md)
        - [4.ES分片和副本 (1,765字)](blog/7.分布式中间件/2.Elasticsearch/4.ES分片和副本.md)
        - [5.ES脑裂问题 (754字)](blog/7.分布式中间件/2.Elasticsearch/5.ES脑裂问题.md)
        - [6.ES分页 (374字)](blog/7.分布式中间件/2.Elasticsearch/6.ES分页.md)
        - [7.ES_CPU飚高 (319字)](blog/7.分布式中间件/2.Elasticsearch/7.ES_CPU飚高.md)
        - [8.索引备份实践 (1,313字)](blog/7.分布式中间件/2.Elasticsearch/8.索引备份实践.md)
        - [9.ES磁盘不足拒绝写 (507字)](blog/7.分布式中间件/2.Elasticsearch/9.ES磁盘不足拒绝写.md)
    - [3.Redis (4篇)](blog/7.分布式中间件/3.Redis)
        - [0.Consistent_Hashing_and_Random_Trees翻译 (4,949字)](blog/7.分布式中间件/3.Redis/0.Consistent_Hashing_and_Random_Trees翻译.md)
        - [1.一致性hash (1,435字)](blog/7.分布式中间件/3.Redis/1.一致性hash.md)
        - [2.缓存常见问题 (481字)](blog/7.分布式中间件/3.Redis/2.缓存常见问题.md)
        - [3.Redis总结 (2,133字)](blog/7.分布式中间件/3.Redis/3.Redis总结.md)
    - [4.Zookeeper (1篇)](blog/7.分布式中间件/4.Zookeeper)
        - [1.Zookeeper概念 (706字)](blog/7.分布式中间件/4.Zookeeper/1.Zookeeper概念.md)
    - [5.Kafka (1篇)](blog/7.分布式中间件/5.Kafka)
        - [1.顺序消费 (173字)](blog/7.分布式中间件/5.Kafka/1.顺序消费.md)
    - [6.数据密集型系统设计 (7字)](blog/7.分布式中间件/6.数据密集型系统设计.md)
- [8.代码架构 (7篇)](blog/8.代码架构)
    - [1.从一个pojo类来看单一性原则 (245字)](blog/8.代码架构/1.从一个pojo类来看单一性原则.md)
    - [2.从Camel中学习FluntApi设计 (699字)](blog/8.代码架构/2.从Camel中学习FluntApi设计.md)
    - [3.扩展点的设计 (468字)](blog/8.代码架构/3.扩展点的设计.md)
    - [4.装饰器和代理的区别 (195字)](blog/8.代码架构/4.装饰器和代理的区别.md)
    - [5.拦截器实现的细节 (182字)](blog/8.代码架构/5.拦截器实现的细节.md)
    - [6.从Filter和Interceptor看责任链模式 (1,771字)](blog/8.代码架构/6.从Filter和Interceptor看责任链模式.md)
    - [7.代码整洁之道 (17字)](blog/8.代码架构/7.代码整洁之道.md)
- [9.算法 (2篇)](blog/9.算法)
    - [1.排序 (1,006字)](blog/9.算法/1.排序.md)
    - [2.算法题型总结 (8,603字)](blog/9.算法/2.算法题型总结.md)
- [10.工具 (8篇)](blog/10.工具)
    - [1.日常使用-Ubuntu (2,388字)](blog/10.工具/1.日常使用-Ubuntu.md)
    - [2.日常使用-Mac (550字)](blog/10.工具/2.日常使用-Mac.md)
    - [3.日常使用-IDEA&Goland (796字)](blog/10.工具/3.日常使用-IDEA&Goland.md)
    - [4.日常使用-Chrome (430字)](blog/10.工具/4.日常使用-Chrome.md)
    - [5.日常使用-杂 (63字)](blog/10.工具/5.日常使用-杂.md)
    - [6.sshuttle使用 (211字)](blog/10.工具/6.sshuttle使用.md)
    - [7.openfortivpn使用 (349字)](blog/10.工具/7.openfortivpn使用.md)
    - [8.Julia (42字)](blog/10.工具/8.Julia.md)
- [11.网络 (8篇)](blog/11.网络)
    - [1.网络上行和下行 (124字)](blog/11.网络/1.网络上行和下行.md)
    - [2.负载均衡4层和7层区别 (301字)](blog/11.网络/2.负载均衡4层和7层区别.md)
    - [3.session_cookie_token (192字)](blog/11.网络/3.session_cookie_token.md)
    - [4.笔记本通过网线分享网络给台式机 (705字)](blog/11.网络/4.笔记本通过网线分享网络给台式机.md)
    - [5.DNS和URL重定向 (279字)](blog/11.网络/5.DNS和URL重定向.md)
    - [6.URI格式 (299字)](blog/11.网络/6.URI格式.md)
    - [7.Http_trailing_slashes (125字)](blog/11.网络/7.Http_trailing_slashes.md)
    - [8.Http_Content-Length (144字)](blog/11.网络/8.Http_Content-Length.md)
- [12.工程实践 (17篇)](blog/12.工程实践)
    - [1.多应用混合部署 (258字)](blog/12.工程实践/1.多应用混合部署.md)
    - [2.尽早抽象和持续重构 (512字)](blog/12.工程实践/2.尽早抽象和持续重构.md)
    - [3.工程命名与职责 (430字)](blog/12.工程实践/3.工程命名与职责.md)
    - [4.写蹩脚代码的人跟你一样smart (183字)](blog/12.工程实践/4.写蹩脚代码的人跟你一样smart.md)
    - [5.项目交接时心态很重要 (491字)](blog/12.工程实践/5.项目交接时心态很重要.md)
    - [6.由日志框架的异常处理引发的思考 (381字)](blog/12.工程实践/6.由日志框架的异常处理引发的思考.md)
    - [7.替换日志框架过程中对重构的思考 (809字)](blog/12.工程实践/7.替换日志框架过程中对重构的思考.md)
    - [8.第三方组件的中间层 (310字)](blog/12.工程实践/8.第三方组件的中间层.md)
    - [9.科学的引用类名和包名 (492字)](blog/12.工程实践/9.科学的引用类名和包名.md)
    - [10.使用CPU百分位作容器缩容的参考指标 (558字)](blog/12.工程实践/10.使用CPU百分位作容器缩容的参考指标.md)
    - [11.大促扩容要有数据支撑 (562字)](blog/12.工程实践/11.大促扩容要有数据支撑.md)
    - [12.关于重构时信息量的思考 (259字)](blog/12.工程实践/12.关于重构时信息量的思考.md)
    - [13.你的压测结果真的符合预期吗 (278字)](blog/12.工程实践/13.你的压测结果真的符合预期吗.md)
    - [14.关于配置中心设计的思考 (408字)](blog/12.工程实践/14.关于配置中心设计的思考.md)
    - [15.蓝绿发布vs灰度发布vs滚动发布 (330字)](blog/12.工程实践/15.蓝绿发布vs灰度发布vs滚动发布.md)
    - [16.科学的剥离其他中间件 (414字)](blog/12.工程实践/16.科学的剥离其他中间件.md)
    - [17.版本管理 (2,679字)](blog/12.工程实践/17.版本管理.md)
- [13.安全 (6篇)](blog/13.安全)
    - [1.XSRF和XSS (759字)](blog/13.安全/1.XSRF和XSS.md)
    - [2.浏览器跨域 (849字)](blog/13.安全/2.浏览器跨域.md)
    - [3.非对称密码的私钥包含了公钥信息 (449字)](blog/13.安全/3.非对称密码的私钥包含了公钥信息.md)
    - [4.RSA与Padding模式 (310字)](blog/13.安全/4.RSA与Padding模式.md)
    - [5.ssh-keygen生成PEM格式密钥 (541字)](blog/13.安全/5.ssh-keygen生成PEM格式密钥.md)
    - [6.cacert证书查看和导入 (749字)](blog/13.安全/6.cacert证书查看和导入.md)
- [14.容器 (1篇)](blog/14.容器)
    - [1.Nginx架构翻译 (3,330字)](blog/14.容器/1.Nginx架构翻译.md)
- [15.杂谈 (3篇)](blog/15.杂谈)
    - [1.全脑记忆 (13字)](blog/15.杂谈/1.全脑记忆.md)
    - [2.工作和学习 (1,622字)](blog/15.杂谈/2.工作和学习.md)
    - [3.央行的基础货币怎么流入市场 (226字)](blog/15.杂谈/3.央行的基础货币怎么流入市场.md)
- [16.工作那些事 (9篇)](blog/16.工作那些事)
    - [1.关于会议 (1,940字)](blog/16.工作那些事/1.关于会议.md)
    - [2.关于信息同步 (1,397字)](blog/16.工作那些事/2.关于信息同步.md)
    - [3.关于规划 (1,289字)](blog/16.工作那些事/3.关于规划.md)
    - [4.关于项目目标 (416字)](blog/16.工作那些事/4.关于项目目标.md)
    - [5.架构治理 (4,501字)](blog/16.工作那些事/5.架构治理.md)
    - [6.一般的分析问题 (2,846字)](blog/16.工作那些事/6.一般的分析问题.md)
    - [7.小数决策和审查数据 (2,287字)](blog/16.工作那些事/7.小数决策和审查数据.md)
    - [8.三省吾身 (1,790字)](blog/16.工作那些事/8.三省吾身.md)
    - [9.书籍 (5,180字)](blog/16.工作那些事/9.书籍.md)

---
*...我也是有底线的...*
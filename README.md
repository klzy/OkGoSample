okserver模块里， 项目对象类与DownloadInfo对象类接合的方法

起因：
原项目使用DataBinding框架进行数据展示，需要用到OkGo的下载管理模块的功能
在[https://github.com/jeasonlzy/okhttp-OkGo](https://github.com/jeasonlzy/okhttp-OkGo)找到了okserver模块，研究发现okserver使用了自己的DownloadInfo对象进行数据存贮、展示
但本项目需要使用项目中的数据对象进行存贮、展示，这就有了项目对象类与DownloadInfo对象类接合的问题

失败方案：

起初使用项目对象类的url与DownloadInfo对象类的taskKey进行关联，这样做的问题是DownloadInfo对象类的下载监听无法有效绑定
放弃此方案

之后让项目对象类继承DownloadInfo对象类，这样还是不能实现下载监听功能，而且还需要修改项目对象类的属性名以避免重复
放弃此方案

解决方法：

最后通过在项目对象类中增加DownloadInfo对象类属性的方法，解决了问题

public class ApkEntity {

    private String name;
    private String icon;
    private String url;
    private DownloadInfo downInfo;
}


目前这个项目就是对okhttp-OkGo项目中的okserver模块进行修改，以展示项目对象类与DownloadInfo对象类接合的方法

欢迎各位开发者与我交流，QQ:2975273
github链接 [https://github.com/klzy/OkGoSample](https://github.com/klzy/OkGoSample)

#### ListenRain/听雨


『听雨』前段时间学习python爬虫练手的一个项目，通过python抓取网站[素锦](http://guo.lu/)上的内容，上传到LeanCloud后端云上，然后再通过app访问服务器获得数据，在客户端上显示文章内容。

目前App完成一半多，后面还会继续开发，定位为一个伪文艺的App。：）

---
技术细节：

1. 后端使用[LeanCloud](https://leancloud.cn/)后端服务，省去了服务器端的开发，具体使用细节请访问官网阅读文档。
2. 图片显示使用的是facebook的开源图片加载库[fresco](https://github.com/facebook/fresco)，文档请访问:[英文](http://frescolib.org/)、[中文](http://fresco-cn.org/)。(需要科学上网)
3. 下拉刷新和上拉加载是自定义的一个刷新控件，具体请查看源码。

暂时就这么多，后期开发再更新，python爬虫代码等我整理后再发出来。：）
## Automate2
持续集成、项目发布管理

![Screenshot](http://47.100.63.232/resources/images/1.png)

![Screenshot](http://47.100.63.232/resources/images/2.png)

![Screenshot](http://47.100.63.232/resources/images/3.png)

#### 开发环境配置
 推荐使用 jvm启动参数中 指定config.properties文件地址  
 在本项目的设计中没有对构建做环境分离,推荐大家使用 jvm参数,保证不同环境的代码一致性
~~~~
-Dconfig.location=file:{userPath}/dev/config.properties
~~~~




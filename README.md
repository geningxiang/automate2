## Automate2
持续集成、项目发布管理

![Screenshot](http://automate.ink:8080/resources/images/1.png)

![Screenshot](http://automate.ink:8080/resources/images/2.png)

![Screenshot](http://automate.ink:8080/resources/images/3.png)

#### 开发环境配置
 使用jvm启动参数 指定config.properties文件地址  
 在本项目的中没有设计构建时的环境分离,推荐使用jvm参数,同一份代码可以应用于不同环境
~~~~
-Dconfig.location=file:{userPath}/dev/config.properties
~~~~

还未完成~~~  
正在开发服务器管理、应用容器管理
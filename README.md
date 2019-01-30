## 自动化部署2.0 #

#### 开发环境配置
 请在项目的默认 jvm启动参数中 配置一下 config.properties的路径
~~~~
-Dconfig.location=file:{userPath}/dev/config.properties
~~~~

#### 生产环境也推荐设置配置文件路径 来达到不同环境相同代码的效果
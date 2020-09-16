
FROM centos:6.10


# 维护者信息
MAINTAINER geningxiang

# 上传 jdk
ADD env/jdk/jdk-8u211-linux-x64.tar.gz /usr/local

# 上传 maven
ADD env/mvn/apache-maven-3.6.3-bin.tar.gz /usr/local

# 上传自定义的 maven 配置
COPY env/mvn/settings.xml /usr/local/apache-maven-3.6.3/conf/settings.xml

# 安装 git
#RUN yum install -y git

# 设置环境变量
ENV JAVA_HOME /usr/local/jdk1.8.0_211
ENV JRE_HOME ${JAVA_HOME}/jre
ENV CLASSPATH .:${JAVA_HOME}/lib:${JRE_HOME}/lib

ENV MAVEN_HOME /usr/local/apache-maven-3.6.3

ENV PATH ${JAVA_HOME}/bin:$MAVEN_HOME/bin:$PATH

ENV PATH $PATH:$JAVA_HOME/bin:$MAVEN_HOME/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/root/bin
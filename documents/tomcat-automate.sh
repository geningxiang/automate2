#!/bin/bash

#Tomcat名称
TOMCAT_NAME=tomcat-automate

#Tomcat根目录
TOMCAT_HOME=/var/web-server/tomcat-automate


#检查当前程序是否运行
FIND_STRING="${TOMCAT_HOME}"

# 注入环境变量
export JAVA_HOME=/usr/local/jdk1.8.0_211
export JAVA_OPTS="-server -Ddefault.client.encoding=GBK -Dfile.encoding=GBK -Duser.language=Zh -Dconfig.location=file:/var/caimao-server/tomcat-automate/bin/automate.properties"


psid=0
STATUS=1

#启动
start(){
	echo "  ______                           __   "

	echo " /_  __/___  ____ ___  _________ _/ /_  "

	echo "  / / / __ \/ __  __ \/ ___/ __  / __/  "

	echo " / / / /_/ / / / / / / /__/ /_/ / /_    "

	echo "/_/  \____/_/ /_/ /_/\___/\__,_/\__/    "

	echo "                                        "
	echo "start ${TOMCAT_HOME} ..............."
	${TOMCAT_HOME}/bin/startup.sh;

	sleep 2

	#检查是否Kill成功
	psid=`ps -ef |grep java | grep ${FIND_STRING} | awk '{print $2}'`
	if [[ ! -z ${psid} ]];then
		echo "============start success : ${TOMCAT_HOME} (pid=$psid)============"
		STATUS=1
	else
		echo "============start fail : ${TOMCAT_HOME} ============"
		STATUS=0
	fi
}

#重启
restart(){
    #停止
	stop
	if [[ ${psid} -eq 0 ]]; then
		sleep 1
		#启动
		start
	fi
}

#停止
stop(){
	echo "go to stop ${TOMCAT_HOME} ..............."
	psid=`ps -ef |grep java | grep ${FIND_STRING} | awk '{print $2}'`
	if [[ ! -z ${psid} ]];then
		echo "notify to stop ..............."
		#通知 tomcat 结束，  注意 这里不加 -9
		kill ${psid}

		sleep 2

		echo "start countdown ..............."

		countDown=10
		while [[ ${countDown} -ge 0 ]]
		do
			echo "[countdown] ${countDown}  ..............."
			sleep 1

			#每隔 1秒 查看一下 tomcat 是否已关闭
			psid=`ps -ef |grep java | grep ${FIND_STRING} | awk '{print $2}'`
			if [[ -z ${psid} ]];then
				# tomcat 已关闭
				stoped
				return 0
			elif [[ ${countDown} == 0 ]];then
				#10秒后 仍然未关闭， 强制结束进程
				kill -9 ${psid}
				break
			fi
			countDown=$[$countDown-1]
		done
	else
		echo "============the server is not run : ${TOMCAT_HOME} ============"
		STATUS=0
		return
	fi

	sleep 1

	#检查是否Kill成功
	psid=`ps -ef |grep java | grep ${FIND_STRING} | awk '{print $2}'`
	if [[ ! -z ${psid} ]];then
		echo "============stop fail : ${TOMCAT_HOME} (pid=$psid)============"
		STATUS=0
	else
		stoped
	fi
}

#tomcat已关闭， 清理work下的缓存文件
stoped(){
	psid=0
	echo "============stop success : ${TOMCAT_HOME}============"
	rm -rf ${TOMCAT_HOME}/work/Catalina/*
	STATUS=0
}

#控制台持续输出日志
showTail(){
	tail -f ${TOMCAT_HOME}/logs/catalina.out
}

check(){
    echo "check ${TOMCAT_HOME} ..............."
	psid=`ps -ef |grep java | grep ${FIND_STRING} | awk '{print $2}'`
	if [[ ! -z ${psid} ]];then
		echo "============the server is run : ${TOMCAT_HOME} (pid=$psid)============"
		STATUS=0
	else
		echo "============the server is not run : ${TOMCAT_HOME} ============"
		STATUS=1
	fi
}

case $1 in
	'start')
		start
		exit ${STATUS};;

	'restart')
		restart
		exit ${STATUS};;

	'stop')
		stop
		exit ${STATUS};;

	'tail')
		showTail;;

    'check')
        check
        exit ${STATUS};;

	*)
		echo "请填写启动参数: $0 {start|restart|stop|tail|check}"
		exit ${STATUS};;
esac

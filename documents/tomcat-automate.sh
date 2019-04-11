#!/bin/bash


#Tomcat根目录
TOMCAT_HOME=/var/webapps/tomcat-automate

export JAVA_HOME="/usr/local/jdk1.8.0_191"

export JAVA_OPTS="-server -Xms128m -Xmx512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m"

psid=0


#启动
start(){
	psid=`ps -ef |grep java | grep ${TOMCAT_HOME} | awk '{print $2}'`
	if [ ! -z $psid ];then
		echo "find pid=${psid} is running..............."
		echo "Please do not start again..............."
		exit 2
	fi

	echo "start ${TOMCAT_HOME} ..............."
	rm -rf ${TOMCAT_HOME}/logs/*
	sleep 2
	cd ${TOMCAT_HOME}/bin
	${TOMCAT_HOME}/bin/startup.sh;
	exit 0
}

#重启
restart(){
	stop
	if [ $psid -eq 0 ]; then
		sleep 1
		start
	fi
}

#停止
stop(){
	echo "stop ${TOMCAT_HOME} ..............."
	psid=`ps -ef |grep java | grep ${TOMCAT_HOME} | awk '{print $2}'`
	if [ ! -z $psid ];then
		echo "find pid=${psid} ..............."

		#通知 tomcat 结束，  注意 这里不加 -9
		kill $psid
		sleep 1

		#倒计时 10秒
		countDown=10
		while [ $countDown -ge 0 ]
		do
			#每隔 1秒 查看一下 tomcat 是否已关闭
			psid=`ps -ef |grep java | grep ${FIND_STRING} | awk '{print $2}'`
			if [ -z $psid ];then
				# tomcat 已关闭
				stoped
				break
			elif [ $countDown == 0 ];then
				echo "kill -9 ${psid} ..............."
				#10秒后 仍然未关闭， 强制结束进程
				kill -9 $psid
				sleep 2
				break
			fi

			echo "countdown ${countDown}  ..............."
			sleep 1

			countDown=$[$countDown-1]
		done


		psid=0
	else
		psid=0
	fi

	#检查是否Kill成功
	psid=`ps -ef |grep java | grep ${TOMCAT_HOME} | awk '{print $2}'`
	if [ ! -z $psid ];then
		echo "============stop fail ${TOMCAT_HOME} (pid=$psid)============"
		exit -1
	else
		psid=0
		echo "============stoped : ${TOMCAT_HOME}============"
	fi
}

checkStatus(){
	psid=`ps -ef |grep java | grep ${TOMCAT_HOME} | awk '{print $2}'`
	if [ ! -z $psid ];then
		echo "============is running ${TOMCAT_HOME} (pid=$psid)============"
		exit 0
	else
		psid=0
		echo "============not running : ${TOMCAT_HOME}============"
		exit 3
	fi
}

#tomcat已关闭， 清理work下的缓存文件
stoped(){
	psid=0
	echo "============stoped: ${TOMCAT_HOME}============"
	rm -rf ${TOMCAT_HOME}/work/Catalina/*
}

showTail(){
	tail -f ${TOMCAT_HOME}/logs/catalina.out
}

case $1 in
	'start')
		start
	;;

	'restart')
		restart
	;;

	'stop')
		stop
		exit 0
	;;

	'status')
		checkStatus
	;;

	'tail')
		showTail
	;;

	*)
		echo "请填写启动参数: $0 {start|restart|stop|tail|status}"
		exit 1
esac
exit 1

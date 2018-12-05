#!/bin/sh

export EUREKA=jar/ly-eureka.jar
export CONFIG=jar/ly-config.jar
export AUTH=jar/ly-auth.jar
export GATEWAY=jar/ly-gateway.jar
export UPMS=jar/ly-upms-service.jar
export MC=jar/ly-mc-service.jar
export DAEMON=jar/ly-daemon-service.jar

export prd=--spring.profiles.active=prd

export EUREKA_free=-Xmx200m
export GATEWAY_free=-Xmx500m
export AUTH_free=-Xmx500m
export CONFIG_free=-Xmx200m
export UPMS_free=-Xmx1000m
export MC_free=-Xmx500m
export DAEMON_free=-Xmx500m


export EUREKA_port=11000
export CONFIG_port=12000
export AUTH_port=13000
export GATEWAY_port=14000
export UPMS_port=15001
export MC_port=15002
export DAEMON_port=15003


case "$1" in

start)
        ## 启动eureka
        echo "--------开始启动 eureka--------------"
        nohup java $EUREKA_free -jar $EUREKA $prd >/dev/null 2>&1 &
        EUREKA_pid=`lsof -i:$EUREKA_port|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$EUREKA_pid" ]
            do
              EUREKA_pid=`lsof -i:$EUREKA_port|grep "LISTEN"|awk '{print $2}'`
            done
        echo "EUREKA pid is $EUREKA_pid"
        echo "--------eureka success 启动成功--------------"

        ## 启动config
        echo "--------开始启动 CONFIG---------------"
        nohup java $CONFIG_free -jar $CONFIG $prd >/dev/null 2>&1 &
        CONFIG_pid=`lsof -i:$CONFIG_port|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$CONFIG_pid" ]
            do
              CONFIG_pid=`lsof -i:$CONFIG_port|grep "LISTEN"|awk '{print $2}'`
            done
        echo "CONFIG pid is $CONFIG_pid"
        echo "---------CONFIG success 启动成功-----------"


        echo "===startAll success==="
        ;;

 stop)
        P_ID=`ps -ef | grep -w $EUREKA | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===EUREKA process not exists or stop success"
        else
            kill -9 $P_ID
            echo "EUREKA killed success"
        fi
		P_ID=`ps -ef | grep -w $CONFIG | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===CONFIG process not exists or stop success"
        else
            kill -9 $P_ID
            echo "CONFIG killed success"
        fi

        echo "===stop success==="
        ;;

restart)
        $0 stop
        sleep 2
        $0 start
        echo "===restart success==="
        ;;
esac
exit 0
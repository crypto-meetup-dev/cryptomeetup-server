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
        ## 启动auth
        echo "--------开始启动 AUTH---------------"
        nohup java $AUTH_free -jar $AUTH $prd >/dev/null 2>&1 &
        AUTH_pid=`lsof -i:$AUTH_port|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$AUTH_pid" ]
            do
              AUTH_pid=`lsof -i:$AUTH_port|grep "LISTEN"|awk '{print $2}'`
            done
        echo "AUTH pid is $AUTH_pid"
        echo "---------AUTH success 启动成功-----------"

        ## 启动upms
        echo "--------开始启动 UPMS---------------"
        nohup java $UPMS_free -jar $UPMS $prd >/dev/null 2>&1 &
        UPMS_pid=`lsof -i:$UPMS_port|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$UPMS_pid" ]
            do
              UPMS_pid=`lsof -i:$UPMS_port|grep "LISTEN"|awk '{print $2}'`
            done
        echo "UPMS pid is $UPMS_pid"
        echo "---------UPMS success 启动成功-----------"

        ## 启动gateway
        echo "--------开始启动 GATEWAY---------------"
        nohup java $GATEWAY_free -jar $GATEWAY $prd >/dev/null 2>&1 &
        GATEWAY_pid=`lsof -i:$GATEWAY_port|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$GATEWAY_pid" ]
            do
              GATEWAY_pid=`lsof -i:$GATEWAY_port|grep "LISTEN"|awk '{print $2}'`
            done
        echo "GATEWAY pid is $GATEWAY_pid"
        echo "---------GATEWAY success 启动成功-----------"

        echo "===startAll success==="
        ;;

 stop)
		P_ID=`ps -ef | grep -w $AUTH | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===AUTH process not exists or stop success"
        else
            kill -9 $P_ID
            echo "AUTH killed success"
        fi
		 P_ID=`ps -ef | grep -w $GATEWAY | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===GATEWAY process not exists or stop success"
        else
            kill -9 $P_ID
            echo "GATEWAY killed success"
        fi
		 P_ID=`ps -ef | grep -w $UPMS | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===UPMS process not exists or stop success"
        else
            kill -9 $P_ID
            echo "UPMS killed success"
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
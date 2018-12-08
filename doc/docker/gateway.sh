#!/bin/sh

export GATEWAY=jar/ly-gateway.jar

export prd=--spring.profiles.active=prd

export GATEWAY_free=-Xmx1000m


export GATEWAY_port=14000


case "$1" in

start)
        ## 启动GATEWAY
        echo "--------开始启动GATEWAY---------------"
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
        P_ID=`ps -ef | grep -w $GATEWAY | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===GATEWAY process not exists or stop success"
        else
            kill -9 $P_ID
            echo "GATEWAY killed success"
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
#!/bin/sh

export EUREKA=jar/ly-eureka.jar

export prd=--spring.profiles.active=prd

export EUREKA_free=-Xmx500m


export EUREKA_port=11000


case "$1" in

start)
        ## 启动EUREKA
        echo "--------开始启动EUREKA---------------"
        nohup java $EUREKA_free -jar $EUREKA $prd >/dev/null 2>&1 &
        EUREKA_pid=`lsof -i:$EUREKA_port|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$EUREKA_pid" ]
            do
              EUREKA_pid=`lsof -i:$EUREKA_port|grep "LISTEN"|awk '{print $2}'`
            done
        echo "EUREKA pid is $EUREKA_pid"
        echo "---------EUREKA success 启动成功-----------"

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
        echo "===stop success==="
        ;;

restart)
        $0 stop
        sleep 2
        $0 start
        echo "===restart success==="
        ;;

restart)
        $0 stop
        sleep 2
        $0 start
        echo "===restart success==="
        ;;
esac
exit 0
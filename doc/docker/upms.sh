#!/bin/sh

export UPMS=jar/ly-upms-service.jar

export prd=--spring.profiles.active=prd

export UPMS_free=-Xmx1000m


export UPMS_port=15001


case "$1" in

start)
        ## 启动UPMS
        echo "--------开始启动UPMS---------------"
        nohup java $UPMS_free -jar $UPMS $prd >/dev/null 2>&1 &
        UPMS_pid=`lsof -i:$UPMS_port|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$UPMS_pid" ]
            do
              UPMS_pid=`lsof -i:$UPMS_port|grep "LISTEN"|awk '{print $2}'`
            done
        echo "UPMS pid is $UPMS_pid"
        echo "---------UPMS success 启动成功-----------"

        echo "===startAll success==="
        ;;

 stop)
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
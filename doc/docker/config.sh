#!/bin/sh

export CONFIG=jar/ly-config.jar

export prd=--spring.profiles.active=prd

export CONFIG_free=-Xmx500m


export CONFIG_port=12000


case "$1" in

start)
        ## 启动CONFIG
        echo "--------开始启动CONFIG---------------"
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

restart)
        $0 stop
        sleep 2
        $0 start
        echo "===restart success==="
        ;;
esac
exit 0